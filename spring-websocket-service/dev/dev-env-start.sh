#!/bin/bash
docker rmi $(docker images --filter=reference="dev_*" -q)
rm -f ./services/*/*.jar
cd ../
./gradlew clean bootJar
cp build/libs/*.jar dev/services/websocket
cd dev
docker-compose up
bash dev-env-stop.sh