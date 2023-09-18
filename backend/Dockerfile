FROM openjdk:17-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# RUN ./mvnw install -DskipTests -Dspring.profiles.active=dev
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


FROM openjdk:17-jdk-alpine
#VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
# I am using images from file sistem so I need to copy them to be available on docker
COPY ./src/main/resources/images /src/main/resources/images
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.lgm.demo.LGMApplication"]