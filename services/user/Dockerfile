# Этап сборки проекта в jar-файл
FROM maven:3.6.3-openjdk-17 as maven
RUN mkdir user
WORKDIR user
COPY . .
RUN mvn package -Dmaven.test.skip=true

# Этап запуска проекта
FROM openjdk:17.0.2-jdk
WORKDIR user
COPY --from=maven /user/target/user-1.0.0.jar user.jar
CMD ["java", "-jar", "user.jar"]