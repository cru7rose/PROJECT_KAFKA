# Użyj oficjalnego obrazu Javy
FROM eclipse-temurin:17-jdk

# Ustaw katalog roboczy
WORKDIR /app

RUN mkdir -p /app/data

# Skopiuj pliki JAR (aplikacja TES) do kontenera
COPY target/TES-0.0.1-SNAPSHOT.jar app.jar

# Ustaw komendę uruchamiającą aplikację
ENTRYPOINT ["java", "-jar", "app.jar"]
