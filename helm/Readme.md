### Для запуска exporters
    helm install db-realese-postgres-auth db-exporter/ --values db-exporter/values-postgres.yaml --values db-exporter/values-postgres-auth.yaml
    helm install db-realese-postgres-editor db-exporter/ --values db-exporter/values-postgres.yaml --values db-exporter/values-postgres-editor.yaml
    helm install db-realese-postgres-signer db-exporter/ --values db-exporter/values-postgres.yaml --values db-exporter/values-postgres-signer.yaml

    helm install db-realese-redis db-exporter/ --values db-exporter/values-redis.yaml