FROM gradle:jdk17-jammy as cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle /home/gradle/java-code/
WORKDIR /home/gradle/java-code
RUN gradle clean build -i --stacktrace


FROM gradle:jdk17-jammy as build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY .. /usr/src/apps/kazan/backend/
WORKDIR /usr/src/apps/kazan/backend
RUN gradle bootJar -i --stacktrace


FROM openjdk:17 as runner
EXPOSE 8080
WORKDIR /usr/src/java-app
ENV PERSON_AVATAR_PATH "/avatars"
COPY --from=build /usr/src/apps/kazan/backend/build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
