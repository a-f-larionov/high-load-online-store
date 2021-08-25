#!/bin/bash

./hot-reloader.sh &

while inotifywait -e modify -r src/*; do

  pkill java

done