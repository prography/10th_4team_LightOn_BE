FROM eclipse-temurin:21-jre-jammy

COPY /build/libs/lighton-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
