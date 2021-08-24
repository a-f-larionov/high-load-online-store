#!/bin/bash
while inotifywait -e modify -r src/*; do
  nohup ./hot-reloader.sh &
done
