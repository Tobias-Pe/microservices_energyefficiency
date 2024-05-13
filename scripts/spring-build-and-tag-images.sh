#!/bin/bash

# exclude servicediscovery as of https://github.com/spring-cloud/spring-cloud-release/wiki/AOT-transformations-and-native-image-support#spring-cloud-netflix
mvn -Pnative spring-boot:build-image -DskipTests -pl "!servicediscovery" &&
mvn spring-boot:build-image -DskipTests -pl "servicediscovery" || exit 1
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

image_names=($(cat pom.xml | grep "<module>" | sed 's/\s*<.*>\(.*\)<.*>/\1/' | tr -d '\r'))

for image_name in "${image_names[@]}"; do
    docker tag ${image_name}:${VERSION} ghcr.io/tobias-pe/microservices_energyefficiency/${image_name}:latest
done
