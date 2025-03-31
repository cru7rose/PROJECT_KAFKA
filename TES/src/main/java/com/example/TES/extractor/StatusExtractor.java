package com.example.TES.extractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class StatusExtractor {

    private final JdbcTemplate jdbcTemplate;
    private final KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static final Path LSN_FILE = Paths.get("/app/data/last_lsn.json");

    @Value("${kafka.topic.status}")
    private String topic;

    public StatusExtractor(JdbcTemplate jdbcTemplate, @Value("${SPRING_KAFKA_BOOTSTRAP_SERVERS}") String kafkaServers) {
        this.jdbcTemplate = jdbcTemplate;

        if (kafkaServers == null || kafkaServers.contains("${") || kafkaServers.isBlank()) {
            System.err.println("⚠️ Kafka bootstrap server not properly set. Falling back to localhost:9092");
            kafkaServers = "localhost:9092";
        } else {
            System.out.println("✅ Kafka bootstrap server configured: " + kafkaServers);
        }

        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
    }

    private byte[] loadLastLsn() {
        try {
            if (!Files.exists(LSN_FILE)) return null;
            return Files.readAllBytes(LSN_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveLastLsn(byte[] lsn) {
        try {
            Files.createDirectories(LSN_FILE.getParent());
            Files.write(LSN_FILE, lsn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 15000)
    public void extractStatuses() {
        byte[] lsnStart = loadLastLsn();
        if (lsnStart == null) {
            lsnStart = jdbcTemplate.queryForObject("SELECT sys.fn_cdc_get_min_lsn('dbo_StatusHistory_Log')", byte[].class);
        }
        final byte[] finalLsnStart = lsnStart;
        final byte[] lsnEnd = jdbcTemplate.queryForObject("SELECT sys.fn_cdc_get_max_lsn()", byte[].class);

        System.out.println("\n⏱ Rozpoczęto extractStatuses()");
        System.out.println("➡️ LSN START: " + bytesToHex(finalLsnStart));
        System.out.println("➡️ LSN END:   " + bytesToHex(lsnEnd));

        String sql = """
                    SELECT s.* FROM (
                        SELECT *,
                               ROW_NUMBER() OVER (PARTITION BY SendingID ORDER BY __$start_lsn DESC) AS rn
                        FROM cdc.fn_cdc_get_all_changes_dbo_StatusHistory_Log(?, ?, 'all')
                        WHERE sendingID IS NOT NULL AND DanxCode <> 'INFO'
                    ) s
                    WHERE s.rn = 1 AND SendingID = '300106548'
                    """;

        jdbcTemplate.query(con -> {
            var ps = con.prepareStatement(sql);
            ps.setBytes(1, finalLsnStart);
            ps.setBytes(2, lsnEnd);
            return ps;
        }, rs -> {
            try {
                System.out.println("🔍 Wiersz z CDC: SendingID = " + rs.getString("SendingID") + ", DanxCode = " + rs.getString("DanxCode"));
                Map<String, Object> statusUpdate = new HashMap<>();
                statusUpdate.put("Id", 0);
                statusUpdate.put("DeviceId", "");
                statusUpdate.put("UserName", rs.getString("UserName"));
                statusUpdate.put("ScanReportCode", rs.getString("DanxCode"));
                statusUpdate.put("BarCode", "");
                statusUpdate.put("OrderNo", rs.getString("SendingID"));
                statusUpdate.put("Recipient", "");
                statusUpdate.put("Sender", "");
                statusUpdate.put("Comments", "");
                statusUpdate.put("Parameters", "");

                Timestamp ts = rs.getTimestamp("LogTime");
                if (ts == null) System.out.println("⚠️ Brak LogTime dla SendingID = " + rs.getString("SendingID"));
                statusUpdate.put("TriggerTime", ts != null ? ts.toInstant() : null);
                statusUpdate.put("Longitude", "");
                statusUpdate.put("Latitude", "");
                statusUpdate.put("RouteCode", "");
                statusUpdate.put("RouteDate", ts != null ? ts.toInstant() : null);
                statusUpdate.put("HubExternalId", "");
                statusUpdate.put("SignatureGuid", "");
                statusUpdate.put("PictureGuid", "");
                statusUpdate.put("AddressId", "");
                statusUpdate.put("ManualScan", "");
                statusUpdate.put("LocationEstimated", true);
                statusUpdate.put("RouteId", 0);
                statusUpdate.put("RouteLegId", 0);
                statusUpdate.put("ClientName","");
                statusUpdate.put("TimeWindowFrom", null);
                statusUpdate.put("TimeWindowTo", null);
                statusUpdate.put("Duration", 0);
                statusUpdate.put("Distance", 0);

                String json = objectMapper.writeValueAsString(statusUpdate);
                System.out.println("✅ Kafka message: " + json);
                producer.send(new ProducerRecord<>(topic, json), (metadata, exception) -> {
                    if (exception != null) {
                        System.err.println("❌ Kafka send error: " + exception.getMessage());
                        exception.printStackTrace();
                    } else {
                        System.out.println("📤 Kafka OK: offset=" + metadata.offset() + ", partition=" + metadata.partition());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        saveLastLsn(lsnEnd);
    }

    private String bytesToHex(byte[] bytes) {
        if (bytes == null) return "null";
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    @PreDestroy
    public void close() {
        producer.flush();
        producer.close();
    }
}