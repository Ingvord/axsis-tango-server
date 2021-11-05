FROM openjdk:11

RUN apt update && apt install -y dumb-init wait-for-it
MAINTAINER mail@ingvord.ru

ARG JAR_FILE
ADD ${JAR_FILE} /app/bin/run.jar

RUN addgroup --system javauser && adduser --disabled-password --no-create-home --shell /bin/false --ingroup javauser --gecos "" javauser
RUN chown -R javauser /app

USER javauser

WORKDIR /app

ENV TANGO_HOST=localhost:10000
ENV MAGIX_HOST=localhost:8080
ENV OAPORT=10100
ENV CTRL1=1.1.1.1:1234
ENV CTRL2=1.1.1.2:1234


ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["/bin/bash", "-c", "java -server -DOAPort=$OAPORT -DTANGO_HOST=$TANGO_HOST -DMAGIX_HOST=$MAGIX_HOST -DCTRL1=$CTRL1 -DCTRL2=$CTRL2 -jar /app/bin/run.jar virtual"]