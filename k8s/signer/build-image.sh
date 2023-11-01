# Запускать из корневой директории

# Построение образа signer-app
minikube image build -f docker/ServiceApp.dockerfile -t signer-app --build-opt=build-arg=APP_NAME=signer --build-opt=build-arg=SERVICE_PATH=/services .
