# build stage (gradle build)
FROM openjdk:8-jdk-alpine as builder 
COPY gradlew . 
COPY gradle gradle 
COPY build.gradle . 
COPY settings.gradle . 
COPY src src 
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# production stage 내부 tomcat 이용 실행
FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring 
USER spring:spring 
COPY --from=builder build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]