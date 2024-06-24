#!/bin/bash

USERNAME="fervo8564"
IMAGE_NAME="gualapop"
TAG="latest"

# delete image from local disk if exist
echo "Deleting previous image..."
docker image rm "$USERNAME/$IMAGE_NAME:$TAG" 2>/dev/null

# Ccompile the image
echo "Compiling the image..."
docker build -t "$USERNAME/$IMAGE_NAME:$TAG" .

# publish the image into dockerhub
echo "Publishing image into Docker Hub..."
docker push "$USERNAME/$IMAGE_NAME:$TAG"

echo "Succesfully uploaded the image in Docker Hub"


sleep 10
echo "Pasaron 10 segundos"

sleep 10
echo "Pasaron 20 segundos"

sleep 40
echo "Pasaron 60 segundos"

sleep 10
echo "Pasaron 70 segundos"
