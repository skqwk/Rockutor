# Запускать из корневой директории

# Построение образа editor-app
docker build -f docker/ServiceApp.dockerfile --progress=plain -t editor-app --build-arg APP_NAME=editor --build-arg SERVICE_PATH=/services .

# Построение образа signer-app
docker build -f docker/ServiceApp.dockerfile --progress=plain -t signer-app --build-arg APP_NAME=signer --build-arg SERVICE_PATH=/services .

# Построение образа config-app
docker build -f docker/ConfigApp.dockerfile --progress=plain -t config-app --build-arg APP_NAME=config --build-arg SERVICE_PATH=/services .