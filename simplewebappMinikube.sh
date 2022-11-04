#!/bin/bash
minikube start --driver=docker
eval $(minikube docker-env)
docker build --no-cache -t app .
alias kubectl="minikube kubectl --"
kubectl apply -f pv-volume.yml,pv-claim.yml,app-deployment.yaml,app-service.yaml,db-deployment.yaml,db-service.yaml,jmsbroker-deployment.yaml,jmsbroker-service.yaml
