# Rockutor

### Описание
Микросервисное приложение для работы с документами

### Планируемая структура

- `editor` - сервис создания и редактирования документов
- `signer` - сервис для подписывания документов
- `auth` - сервис авторизации и аутентификации

### Список задач

(_порядок задач в списке условный_)
- ~~создание модуля сервиса `editor`~~
- ~~создание модуля сервиса `signer`~~
- ~~добавление `kafka`~~
- создание модуля сервиса `auth`
- интеграция `editor`, `signer` с `auth`
- добавление сервиса получения конфигурации через `Git`
- unit-тесты для `editor`
- unit-тесты для `signer`
- логирование в `editor`, `signer`, интеграция с `Graylog`
- добавление `API Gateway`
- мониторинг БД через `Prometheus`, `Grafana` + трассировка через `Jagger`
- перенос в `minikube`:
    - манифесты `minikube`:
      - для `editor`
      - для `signer`
      - для `auth`
      - для `kafka`
      - для `postgres`
      - для `graylog`
      - для `prometheus`
      - для `jagger`
      - для `grafana`
    - использование `autoscaling`
    - настройка лимитов, `Liveness`, `Readiness`, `Startup Probes`
    - использование `Persistent Volume` для БД


### Требования к реализации
![img.png](assets/requirements.png)
