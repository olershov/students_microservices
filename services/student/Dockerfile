# Этап сборки проекта в jar-файл
FROM maven:3.6.3-openjdk-17 as maven
RUN mkdir student
WORKDIR student
COPY . .
RUN mvn package -Dmaven.test.skip=true

# Этап запуска проекта
FROM openjdk:17.0.2-jdk
WORKDIR student
COPY --from=maven /student/target/student-1.0.0.jar student.jar
CMD ["java", "-jar", "student.jar"]