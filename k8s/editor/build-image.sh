# Запускать из корневой директории

# Построение образа editor-app
minikube image build -f docker/ServiceApp.dockerfile -t editor-app --build-opt=build-arg=APP_NAME=editor --build-opt=build-arg=SERVICE_PATH=/services .
