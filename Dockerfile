FROM openjdk:16

MAINTAINER mail@ingvord.ru

ARG JAR_FILE
ADD ${JAR_FILE} /app/bin/run.jar

COPY elastic-apm-agent-1.32.0.jar /app/

WORKDIR /app

ENV JAVA_TOOL_OPTIONS="-javaagent:/app/elastic-apm-agent-1.32.0.jar \
                       -Delastic.apm.service_name=axsis-tango \
                       -Delastic.apm.server_urls=$APM_SERVER_URL \
                       -Delastic.apm.secret_token= \
                       -Delastic.apm.environment=production \
                       -Delastic.apm.application_packages=com.github.ingvord.axsis"
ENV TANGO_HOST=localhost:10000
ENV MAGIX_HOST=localhost:8080
ENV OAPORT=30100
ENV CTRL1=1.1.1.1:1234
ENV CTRL2=1.1.1.2:1234

CMD java -server -DOAPort=$OAPORT -DTANGO_HOST=$TANGO_HOST -DMAGIX_HOST=$MAGIX_HOST -DCTRL1=$CTRL1 -DCTRL2=$CTRL2 -jar /app/bin/run.jar virtual