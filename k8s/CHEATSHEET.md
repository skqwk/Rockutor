### Подсказки по K8S

Получить логи пода
```bash
kubectl logs -f <pod>
```

Выключить все поды
```bash
kubectl delete deployment broker zookeeper editor-app editor-db config-app
```