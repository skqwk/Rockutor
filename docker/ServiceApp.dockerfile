# THREE-STAGE BUILD DOCKER FILE

ARG BUILD_IMAGE=maven:3.8.3-openjdk-17
ARG RUNTIME_IMAGE=openjdk:18

#############################################################################################
###                Stage where Docker is shared local library                             ###
#############################################################################################
FROM ${BUILD_IMAGE} as local
ARG SERVICE_PATH

COPY "$SERVICE_PATH/common/pom.xml" ./
RUN mvn -B dependency:go-offline

COPY "$SERVICE_PATH/common/src" ./src
RUN mvn -B clean install -DskipTests

#############################################################################################
###                Stage where Docker is pulling all maven dependencies                   ###
#############################################################################################

FROM local as dependencies
ARG SERVICE_PATH
ARG APP_NAME

COPY "$SERVICE_PATH/$APP_NAME/pom.xml" ./
RUN mvn -B dependency:go-offline
#############################################################################################


#############################################################################################
###              Stage where Docker is building spring boot app using maven               ###
#############################################################################################
FROM dependencies as build
ARG SERVICE_PATH
ARG APP_NAME

COPY "$SERVICE_PATH/$APP_NAME/src" ./src

RUN mvn -B clean package -DskipTests
#############################################################################################


#############################################################################################
### Stage where Docker is running a java process to run a service built in previous stage ###
#############################################################################################
FROM ${RUNTIME_IMAGE}
ARG APP_NAME=unknown
ARG JAR_FILE=target/$APP_NAME-0.0.1-SNAPSHOT.jar
COPY --from=build ${JAR_FILE} app.jar
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar .
ENV JAVA_TOOL_OPTIONS "-javaagent:./opentelemetry-javaagent.jar"
RUN echo "#!/bin/bash" >> entrypoint.sh && \
    echo "sleep \$ENTRY_DELAY" >> entrypoint.sh && \
    echo "java -jar app.jar" >> entrypoint.sh   && \
    chmod +x entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
#############################################################################################