#!/bin/bash

rm -Rf ./build
./gradlew build -x test
cp ./build/libs/wildcore.jar ./docker

docker build -t $1 ./docker
docker push $1
