FROM eclipse-temurin:21

LABEL authors="Santiago"

WORKDIR /app

COPY target/sgpc-api-final-0.0.1-SNAPSHOT.jar app.jar

RUN chmod 755 app.jar

EXPOSE 8081

RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]