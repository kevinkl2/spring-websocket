#!/bin/bash
rm -f ./services/*/*.jar
cd ../
./gradlew clean bootJar
cp build/libs/*.jar dev/services/websocket
cd dev

docker-compose up

#if [ $b == true ]
#then
#  docker rmi $(docker images --filter=reference="dev_*" -q)
#  rm ./services/*/*.jar
#  cd ..
#  ./gradlew goJF
#  if [ $c == true ]
#  then
#    if [ $o == false ]
#    then
#      ./gradlew clean bootJar --offline
#    else
#      ./gradlew clean bootJar
#    fi
#  else
#    if [ $o == false ]
#    then
#      ./gradlew bootJar --offline
#    else
#      ./gradlew bootJar
#    fi
#  fi
#  cp ctp-data-aggregator-service/build/libs/*.jar dev/services/ctp-data-aggregator-service
#  cp ctp-tf-service/build/libs/*.jar dev/services/ctp-tf-service
#  cp tc-data-aggregator-service/build/libs/*.jar dev/services/tc-data-aggregator-service
#  cp report-worker-service/build/libs/*.jar dev/services/report-worker-service
#  cp tf-eig-proxy-service/build/libs/*.jar dev/services/tf-eig-proxy-service
#  cp tf-vehicle-imagery-service/build/libs/*.jar dev/services/tf-vehicle-imagery-service
#  cd dev
#fi

sh dev-env-stop.sh