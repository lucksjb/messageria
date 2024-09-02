# !/bin/bash
echo "################### Creating profile ###################"
aws configure set aws_access_key_id fake --profile=localstack
aws configure set aws_secret_access_key fake --profile=localstack
aws configure set region us-east-1 --profile=localstack
aws configure list --profile=localstack

echo *** create sqs / sns ***
aws sqs create-queue --profile localstack --queue-name SQS_FILA1 --endpoint-url=http://127.0.0.1:4566
aws sns create-topic --profile localstack --name SNS_TOPICO1 --endpoint-url=http://127.0.0.1:4566

aws sqs list-queues --profile localstack  --endpoint-url=http://localhost:4566      # lista as filas 
aws sqs receive-message --queue-url SQS_FILA1 --endpoint-url http://localhost:4566  # lista as mensagens 
