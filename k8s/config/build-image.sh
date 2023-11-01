# Запускать из корневой директории

# Построение образа config-app
minikube image build -f docker/ConfigApp.dockerfile -t config-app --build-opt=build-arg=APP_NAME=config --build-opt=build-arg=SERVICE_PATH=/services .
