# Rockutor

### Описание
Микросервисное приложение для работы с документами

### Планируемая структура

- `editor` - сервис создания и редактирования документов
- `signer` - сервис для подписывания документов
- `auth` - сервис авторизации и аутентификации

### Список задач
<details>
<summary>Раскрыть задачи</summary>

### Требования к реализации

![img.png](assets/requirements.png)


(_порядок задач в списке условный_)
- ~~создание модуля сервиса `editor`~~
- ~~создание модуля сервиса `signer`~~
- ~~добавление `kafka`~~
- ~~создание модуля сервиса `auth`~~
- ~~интеграция `editor`, `signer` с `auth`~~
- ~~добавление сервиса получения конфигурации через `Git`~~
- ~~unit-тесты для `editor`~~
- ~~unit-тесты для `signer`~~
- ~~логирование в `editor`, `signer`, интеграция с `Graylog`~~
- ~~добавление `API Gateway`~~
- ~~мониторинг БД через `Prometheus`, `Grafana` + трассировка через `Jagger`~~
- ~~установка с помощью helm-чартов~~
- ~~перенос в `minikube`:~~
    - ~~манифесты `minikube`~~:
      - ~~для `editor`~~
      - ~~для `signer`~~
      - ~~для `auth`~~
      - ~~для `kafka`~~
      - ~~для `postgres`~~
      - ~~для `graylog`~~
      - ~~для `prometheus`~~
      - ~~для `jaeger`~~
      - ~~для `grafana`~~
    - ~~использование `autoscaling`~~
    - ~~настройка лимитов~~
    - ~~настройка `Liveness`, `Readiness`, `Startup Probes`~~
    - ~~использование `Persistent Volume` для БД~~
    - ~~конфигурация макс. кол-ва соединений~~
</details>

### Порядок запуска сервисов в Docker:
1. `auth`
2. `editor`, `signer`, `jaeger`, `kafka` (`rockutor`)
3. `db_monitoring`
4. `krakend`
5. `graylog`

### Данные по настройке/эксплуатации:

| Сервис     | Адрес                  | Логин / Пароль |
|------------|------------------------|----------------|
| Graylog    | http://localhost:9000  | admin / admin  |
| Jaeger     | http://localhost:16686 | -              |     
| Grafana    | http://localhost:3000  | admin / admin  |
| Prometheus | http://localhost:9090  | -              |

### Примерная архитектура

![img.png](assets/architecture.png)

### Структура проекта

![img.png](assets/structure.png)


