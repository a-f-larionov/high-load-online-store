#!/bin/bash

mvn -T 1C clean package -Dmaven.test.skip -DskipTests -Dmaven.javadoc.skip=true
java -jar -Dspring.profiles.active=demo -Djava.security.egd=file:/dev/./urandom target/store-0.0.1-SNAPSHOT.jar