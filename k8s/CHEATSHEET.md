### Подсказки по K8S

Получить логи пода
```bash
kubectl logs -f <pod>
```

Выключить все поды
```bash
kubectl delete deployment broker 
kubectl delete deployment zookeeper 
kubectl delete deployment editor-db 
kubectl delete deployment editor-app 
kubectl delete hpa editor-app-hpa 
kubectl delete deployment signer-app 
kubectl delete hpa signer-app-hpa 
kubectl delete deployment signer-db 
kubectl delete deployment config-app
kubectl delete deployment auth-app
kubectl delete hpa auth-app-hpa 
kubectl delete deployment auth-db
kubectl delete deployment auth-redis
kubectl delete deployment graylog
kubectl delete deployment jaeger
kubectl delete deployment krakend
kubectl delete deployment editor-db-exporter
kubectl delete deployment signer-db-exporter
kubectl delete deployment auth-db-exporter
kubectl delete deployment auth-redis-exporter
kubectl delete deployment grafana
kubectl delete deployment monitoring-db-prometheus
```

Включить всё
```bash
cd k8s/auth
kubectl apply -f auth-app.yaml
kubectl apply -f auth-db.yaml
kubectl apply -f auth-redis.yaml
cd ../config
kubectl apply -f config-app.yaml
cd ../kafka
kubectl apply -f kafka.yaml
cd ../jaeger
kubectl apply -f jaeger.yaml
cd ../editor
kubectl apply -f editor-db.yaml 
kubectl apply -f editor-app.yaml
cd ../signer
kubectl apply -f signer-db.yaml 
kubectl apply -f signer-app.yaml
cd ../krakend
kubectl apply -f krakend.yaml
cd ../graylog
kubectl apply -f graylog.yaml
cd ../monitoring
kubectl apply -f auth-db-exporter.yml
kubectl apply -f auth-redis-exporter.yml
kubectl apply -f signer-db-exporter.yml
kubectl apply -f editor-db-exporter.yml
kubectl apply -f prometheus-deploy.yml
kubectl apply -f grafana.yml
```

Для мониторинга потребления подов с частотой 1 секунда. `Powershell`
```bash
while (1) {kubectl top pod; sleep 1; clear;}
```

Просмотр auto-scaler'ов
```bash
kubectl get hpa
kubectl describe hpa
```

