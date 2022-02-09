#!/usr/bin/env bash
if [ -z "$XMX" ]; then
  MEMORY="$(grep MemTotal /proc/meminfo | awk '{print $2}')"
  if [ -z "$MEMORY" ]; then
    MEMORY=4000000
  fi
  PERCENT=70
  XMX="$((MEMORY * PERCENT/100/1024))"
fi

if [ -z "$CONFIG_PATH" ]; then
  CONFIG_PATH="/etc/application.yml"
fi

if [ -z "$APP_NAME" ]; then
  APP_NAME="wildcore.jar"
fi

export JAVA_OPTS="${JAVA_OPTS} \
-Dlog4j.formatMsgNoLookups=true \
-Duser.timezone=Etc/UTC \
-Xms256M \
-Xmx"$XMX"M"

java $JAVA_OPTS -Dspring.config.location=${CONFIG_PATH} -jar /${APP_NAME}
