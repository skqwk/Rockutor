### Для запуска exporters
    helm install db-realese-postgres-auth db-exporter/ --values db-exporter/values-postgres.yaml --values db-exporter/values-postgres-auth.yaml
    helm install db-realese-postgres-editor db-exporter/ --values db-exporter/values-postgres.yaml --values db-exporter/values-postgres-editor.yaml
    helm install db-realese-postgres-signer db-exporter/ --values db-exporter/values-postgres.yaml --values db-exporter/values-postgres-signer.yaml
    
    helm install db-realese-redis db-exporter/ --values db-exporter/values-redis.yaml

### Для запуска apps
    helm install auth-app-release rockutor-app/ --values rockutor-app/values-auth-app.yaml
    helm install editor-app-release rockutor-app/ --values rockutor-app/values-core.yaml --values rockutor-app/values-core-editor-app.yaml
    helm install signer-app-release rockutor-app/ --values rockutor-app/values-core.yaml --values rockutor-app/values-core-signer-app.yaml

### Для запуска БД
    helm install  editor-db-release rockutor-db/ --values rockutor-db/values-editor-db.yaml
    helm install  signer-db-release rockutor-db/ --values rockutor-db/values-signer-db.yaml