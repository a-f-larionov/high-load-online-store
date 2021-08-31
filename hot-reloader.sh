#!/bin/bash
#pgrep java | xargs kill
#pgrep chrome | xargs kill

#mvn clean

#mvn -Dtests.skip=true package

while true; do

  java -jar -Dspring.profiles.active=prod -Djava.security.egd=file:/dev/./urandom target/store-0.0.1-SNAPSHOT.jar

  sleep 1

done