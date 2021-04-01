FROM openjdk:14.0.2
COPY target/clientLimit-*.jar clientLimit.jar
CMD ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "/clientLimit.jar"]