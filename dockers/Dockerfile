FROM alpine:3.10

# Install OpenJDK-8
RUN apk --update add --no-cache openjdk8

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk/
RUN export JAVA_HOME


RUN mkdir /usr/local/botmon \
    && mkdir /usr/local/botmon/bin \
    && mkdir /usr/local/botmon/etc \
    && mkdir /usr/local/botmon/log \
    && mkdir /usr/local/botmon/sqlite \
    && mkdir /usr/local/botmon/run

COPY botmon_entrypoint.sh /usr/local/botmon/
COPY botmon.sh /usr/local/botmon/
COPY config /usr/local/botmon/etc/
COPY botmon.jar /usr/local/botmon/bin/

RUN chmod +x /usr/local/botmon/botmon.sh

ENTRYPOINT /usr/local/botmon/botmon_docker.sh

EXPOSE 8226



