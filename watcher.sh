#!/bin/bash

./hot-reloader.sh &

while inotifywait -e modify -r src/*; do

  mvn -T 1C clean install -Dmaven.test.skip -DskipTests -Dmaven.javadoc.skip=true

  pkill java

done
