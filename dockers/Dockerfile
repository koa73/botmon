FROM alpine:3.10

# Default to UTF-8 file.encoding
#
ENV LANG C.UTF-8

# add a simple script that can auto-detect the appropriate JAVA_HOME value
# based on whether the JDK or only the JRE is installed
RUN { \
	echo '#!/bin/sh'; \
	echo 'set -e'; \
	echo; \
	echo 'dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"'; \
    } > /usr/local/bin/docker-java-home \
    && chmod +x /usr/local/bin/docker-java-home
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk/jre
ENV PATH $PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin


ENV JAVA_VERSION 8u222
ENV JAVA_ALPINE_VERSION 8.222.10-r0


RUN apk add --update \
curl \
openjdk8-jre-base="$JAVA_ALPINE_VERSION" \
&& rm /var/cache/apk/*  && echo securerandom.source=file:/dev/urandom >> /usr/lib/jvm/default-jvm/jre/lib/security/java.security \
&& [ "$JAVA_HOME" = "$(docker-java-home)" ]


RUN mkdir /usr/local/botmon \
    && mkdir /usr/local/botmon/bin \
    && mkdir /usr/local/botmon/etc \
    && mkdir /usr/local/botmon/log \
    && mkdir /usr/local/botmon/sqlite \
    && mkdir /usr/local/botmon/run

COPY botmon.sh /usr/local/botmon/
COPY config /usr/local/botmon/etc/
COPY botmon.jar /usr/local/botmon/bin/

RUN chmod +x /usr/local/botmon/botmon.sh

ENTRYPOINT /usr/local/botmon/botmon.sh start && tail -f /usr/local/botmon/log/botmon.log

EXPOSE 8226



