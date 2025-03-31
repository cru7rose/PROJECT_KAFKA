package com.example.APS.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlgoPlannerClientTest {

    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;

    @InjectMocks
    private AlgoPlannerClient algoPlannerClient;

    @Mock
    private AlgoPlannerAuthService authService;

    @BeforeEach
    void setUp() {
        AlgoPlannerAuthService authService = new AlgoPlannerAuthService();
        ReflectionTestUtils.setField(authService, "authUrl", "https://ap.danxils.com/api/auth/login");
        ReflectionTestUtils.setField(authService, "username", "213");
        ReflectionTestUtils.setField(authService, "password", "darkelf91");

        restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
        mockServer = MockRestServiceServer.createServer(restTemplate);

        algoPlannerClient = new AlgoPlannerClient(authService, restTemplate);
        ReflectionTestUtils.setField(algoPlannerClient, "apiUrl", "https://ap.danxils.com/api");
    }

    @Test
    void testSendStatusToAlgoPlanner_Success() throws Exception {
        String statusJson = """
        {
          "Id": 123,
          "DeviceId": "",
          "UserName": "test_user",
          "ScanReportCode": "LOAD",
          "BarCode": "",
          "OrderNo": "300106548",
          "Recipient": "",
          "Sender": "",
          "Comments": "",
          "Parameters": "",
          "TriggerTime": "2025-03-22T11:33:25.811Z",
          "Longitude": 10.0,
          "Latitude": 20.0,
          "RouteCode": "",
          "RouteDate": "2025-03-22T11:33:25.811Z",
          "HubExternalId": "HUB01",
          "SignatureGuid": "00000000-0000-0000-0000-0000-000000000000",
          "PictureGuid": "00000000-0000-0000-0000-000000000000",
          "AddressId": 1234,
          "ManualScan": true,
          "LocationEstimated": true,
          "RouteId": 0,
          "RouteLegId": 0,
          "ClientName": "TrackIT",
          "Version": "",
          "TimeWindowFrom": null,
          "TimeWindowTo": null,
          "Duration": 0,
          "Distance": 0
        }
        """;


        boolean success = algoPlannerClient.sendStatusToAlgoPlanner(statusJson);

        assertTrue(success, "Status powinien zostać poprawnie wysłany");
    }
}