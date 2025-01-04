#!/bin/bash

kubectl apply -f sky-journey-db/sky-journey-db-deployment.yaml
kubectl apply -f sky-journey-db/sky-journey-db-service.yaml

kubectl apply -f sky-journey-core/sky-journey-core-deployment.yaml
kubectl apply -f sky-journey-core/sky-journey-core-service.yaml

kubectl apply -f sky-journey-payment/sky-journey-payment-deployment.yaml
kubectl apply -f sky-journey-payment/sky-journey-payment-service.yaml

kubectl apply -f sky-journey-auth/sky-journey-auth-deployment.yaml
kubectl apply -f sky-journey-auth/sky-journey-auth-service.yaml

kubectl apply -f sky-journey-ui/sky-journey-ui-deployment.yaml
kubectl apply -f sky-journey-ui/sky-journey-ui-service.yaml

kubectl apply -f sky-journey-dbadmin/sky-journey-dbadmin-deployment.yaml
kubectl apply -f sky-journey-dbadmin/sky-journey-dbadmin-service.yaml

kubectl apply -f sky-journey-portainer/portainer-sa.yaml
kubectl apply -f sky-journey-portainer/portainer-cluster.yaml
kubectl apply -f sky-journey-portainer/portainer-rbac.yaml
kubectl apply -f sky-journey-portainer/sky-journey-portainer-deployment.yaml
kubectl apply -f sky-journey-portainer/sky-journey-portainer-service.yaml
kubectl apply -f https://downloads.portainer.io/ce2-19/portainer-agent-k8s-nodeport.yaml

kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml
kubectl apply -f k8s-config/cluster-ingress.yaml
