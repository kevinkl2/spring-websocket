#!/bin/sh
#while ! nc -z config-server 8888; do
#    sleep 1
#done
java -jar -Dspring.profiles.active="dev" -Dspring.cloud.bootstrap.location=/config/bootstrap.yml /app.jar