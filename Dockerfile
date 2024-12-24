FROM gradle:jdk17 as cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY backend/build.gradle /home/gradle/java-code/
WORKDIR /home/gradle/java-code
RUN gradle clean build -i --stacktrace


FROM gradle:jdk17 as build
COPY . /usr/src/apps/kazan/
WORKDIR /usr/src/apps/kazan/backend
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
RUN gradle bootJar -i --stacktrace


FROM openjdk:17
EXPOSE 8080
WORKDIR /usr/src/java-app
COPY --from=build /usr/src/apps/kazan/backend/build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
