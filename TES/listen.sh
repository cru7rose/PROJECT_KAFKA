#!/bin/bash

TOPIC_NAME=status_updates_topic
KAFKA_POD=$(kubectl get pods -l app.kubernetes.io/name=kafka -o jsonpath="{.items[0].metadata.name}")

echo "📡 Nasłuchiwanie topicu Kafka: $TOPIC_NAME"

kubectl exec -it $KAFKA_POD -- \
  kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --topic $TOPIC_NAME \
    --from-beginning
