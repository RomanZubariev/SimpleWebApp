FROM maven:3.8-openjdk-11
COPY ./ .
ENTRYPOINT ["mvn", "spring-boot:run"]