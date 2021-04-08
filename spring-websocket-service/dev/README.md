# Dev Environment Setup
## Overview
The files in the dev folder focus on spinning up a mocked local development
environment that emulates the production environment for the ctp-toyofax-services.
All external dependencies run as their own docker container on a local docker network
and are orchestrated using docker compose. The focus is to have an efficient local
development environment for testing and to reduce onboarding time for new developers.

## Required Software for Containers
#### MAC
1. Download homebrew and run these commands
```
brew tap AdoptOpenJDK/openjdk
brew cask install adoptopenjdk8
brew update
```
2. Download docker
```
https://www.docker.com/products/docker-desktop
```

# Dev Environment Scripts
All the following scripts are under dev/scripts and can be run outside the directory

## Starting the Dev Environment
The dev-env-start script will setup the application properties, spin up
the docker containers and create a network to bridge each container to one
another. When you stop the containers using Ctrl+C, the script should
also handle cleanup of the containers and files.

```sh dev-env-start.sh```

#### Configuration Flags
The -nobuild flag will create an executable file of the project to use in 
the dev environment. If you do not have a new version of the code to test,
please set the nobuild flag from the command line.

NOTE: The script will build the project by default

```sh dev-env-start.sh -nobuild```

The -online flag will build the jar file using online repositories. If you need
to pull from the Toyota artifactories and/or public repos, you can set this
flag from the command line.

NOTE: The script will build the project offline by default

```sh dev-env-start.sh -online```

The -noclean flag will not conduct a clean build of the jar file. If you do not
wish to remove previous compiled files, you can set this flag from the command line.

NOTE: The script will clean build the project by default

```sh dev-env-start.sh -noclean```

#### Running specific services
With the abundance of microservices in toyofax, you can spin up any specific service
using these flags:

```
# ctp-data-aggregator-service
sh dev-env-start.sh cas

# ctp-tf-service
sh dev-env-start.sh cts

# tc-data-aggregator-service
sh dev-env-start.sh tas

# report-worker-service
sh dev-env-start.sh rws

# tf-eig-proxy-service
sh dev-env-start.sh teps
```

#### Example of Multiple Configurations
```
sh dev-env-start.sh teps -noclean cas cts -online
```

## Restarting Specific Containers in the Dev Environment
The dev-env-restart script should be run if a running container needs to be
rebuilt for any reason. When run, this will restart the specified container.

NOTE: If no containers are specified, the script will RESTART ALL CONTAINERS

#### List of Containers that can be Restarted
These flags correspond to the containers listed in the docker-compose.yml.
Please use that file as a reference to the flags below.
```
# cas: ctp-aggregator-service
sh dev-env-restart.sh cas

# cts: ctp-tf-service
sh dev-env-restart.sh cts

# tas: tc-aggregator-service
sh dev-env-restart.sh tas

# rws: report-worker-service
sh dev-env-restart.sh rws

# teps: tf-eig-proxy-service
sh dev-env-restart.sh teps

# eig: dev.eig
sh dev-env-restart.sh eig

# ctp: dev.ctp
sh dev-env-restart.sh ctp

# tc: dev.tc
sh dev-env-restart.sh tc
```

#### Example of Multiple Configurations
```
sh dev-env-restart.sh tc tas cts teps
```

## Manually Stopping and Cleaning the Dev Environment
The dev-env-stop script should be run in case the start script is unable to
terminate cleanly. When run, this will manually cleanup the docker containers
and any unnecessary files.

NOTE: The stop script is ALREADY INCLUDED in the start script when spinning down.
Use this script only if the start script was interrupted before the environment
could shut down
```
# Press y when prompted to during the script
sh dev-env-stop.sh
```

## Removing the Dev Environment
####NOTE: Do not use this script to restart the dev environment. The dev-env-stop script handles the cleanup.
In case you need to start from scratch or if you need to delete
the entire environment completely, the dev-env-cleanup script will wipe out
all data and memory for docker. Using this script with the intention
of restarting the dev environment will force the re-installation of images 

```
# Press y for each prompt
sh dev-env-cleanup.sh
```

## Verifying the Dev Enviroment
If the application spins up correctly, you should be able to check each
endpoint via redis-cli, mongoDB Compass, and Postman

- config-server
    ```
    # Connect to this endpoint and verify that the properties are there
    http://localhost:8888/ctp-tf-service/dev
    ```
    
    
- mongoDB
    - Connect to localhost:27017 using Compass and check for a
    tf_vehicle_health_report database

- redis
    ```
    # Should receive a PONG
    redis-cli ping
    ```
  
- Wiremock
    ```
    # Check these endpoints see if you receive a pong
    ## dev.ctp
    http://localhost:8090/ping
  
    ## dev.tc
    http://localhost:8091/ping
  
    ## dev.eig
    http://localhost:8092/ping
    ```
  
#### Notes for existing devs
Make sure that redis and mongodb are not running as they'll interfere
with the docker container spinup. You can check this by using
```
redis-cli ping
mongod
```
- Shutdown redis
```
# Turns off auto-start if enabled
# For Mac
  sudo launchctl remove redis-server
# For Linux
  sudo systemctl disable redis-server

# Shutdown redis service
redis-cli shutdown
```
- Shutdown mongodb
```
# If installed using homebrew
brew services stop mongodb
# If not installed using homebrew, find and kill the mongodb PID
sudo lsof -iTCP -sTCP:LISTEN -n -P
sudo kill <mongo_command_pid>
```

If you have any data you want from a previous mongodb, transfer the
data into this folder
```
$HOME/data/springboot-mongo-tf-data
```