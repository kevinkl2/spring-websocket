#!/bin/sh
SCRIPT_PATH=${0%/*}
if [ "$0" != "$SCRIPT_PATH" ] && [ "$SCRIPT_PATH" != "" ]; then
    cd $SCRIPT_PATH
fi
docker-compose down
docker rmi -f $(docker images -a -q)
docker system prune
docker volume prune
rm -rf $HOME/data/springboot-mongo-tf-data/
rm -rf $HOME/data/springboot-mongo-tf-bkp/