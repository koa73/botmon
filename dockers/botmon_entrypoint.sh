#!/bin/sh

cd /usr/local/botmon

SCRIPT="${0##*/}"
SERVICE="${SCRIPT%.*}"
SERVICE="${SERVICE/_docker/}"
SERVICE="${SERVICE/_entrypoint/}"

set -o allexport
source ./etc/config
LOG_FILE=$SERVICE
set +o allexport

BINDIR=$ROOTDIR/bin
OPTIONS="-Xmx400M"
PIDFILE=./run/$SERVICE.pid
rm $PIDFILE

if [ ! -d /var/run/$SERVICE_NAME ] ; then
mkdir /var/run/$SERVICE_NAME
fi

exec java $OPTIONS -jar $BINDIR/$SERVICE.jar $CONFIG 1 

