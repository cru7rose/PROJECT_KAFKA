#!/bin/bash

set -e

APP_NAME=tes
IMAGE_NAME=cru7rose/tes:cdc-lsn
DEPLOYMENT_NAME=tes
TOPIC_NAME=status_updates_topic
KAFKA_POD=$(kubectl get pods -l app.kubernetes.io/name=kafka -o jsonpath="{.items[0].metadata.name}")

echo "📦 Budowanie aplikacji TES (mvnw clean package)..."
./mvnw clean package

echo "📁 Sprawdzam obecność pliku JAR..."
ls target/TES-0.0.1-SNAPSHOT.jar || { echo "❌ Brak pliku JAR. Przerywam."; exit 1; }

echo "🐳 Budowanie obrazu Dockera: $IMAGE_NAME"
docker build -t $IMAGE_NAME .

echo "⬆️  Push obrazu do Docker Hub..."
docker push $IMAGE_NAME

echo "🚀 Deploy do Kubernetesa..."
kubectl apply -f tes-deployment.yaml

echo "⏳ Czekam na uruchomienie poda TES..."
kubectl rollout status deployment/$DEPLOYMENT_NAME

echo "📄 Logi aplikacji TES:"
kubectl logs deployment/$DEPLOYMENT_NAME -f
