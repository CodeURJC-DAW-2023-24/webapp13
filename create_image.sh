#!/bin/bash

# Login in dockerhub
echo "Login into Docker Hub..."
docker login

# image name and tag
IMAGE_NAME="fervo8564/gualapop"
TAG="latest"

# compilation of the image
docker build -t "$IMAGE_NAME:$TAG" .

# publish the image into dockerhub
echo "Publishing image into Docker Hub..."
docker push "$IMAGE_NAME:$TAG"

echo "Succesfully uploaded the image in Docker Hub"
