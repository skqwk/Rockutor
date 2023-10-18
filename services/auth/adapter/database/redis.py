import redis

from settings import config


class RedisDB:
    def __init__(self):
        self._host = config.redis_host
        self._port = config.redis_port
        #self._redis_client = None
        self._redis_client = self.connect()

    def connect(self):
        # self._redis_client = redis.Redis(host=self._host, port=self._port)
        return redis.Redis(host=self._host, port=self._port)

    def set(self, key, value):
        if self._redis_client:
            self._redis_client.set(key, value)

    def get(self, key):
        if self._redis_client:
            return self._redis_client.get(key)

    def delete(self, key):
        if self._redis_client:
            self._redis_client.delete(key)

    def extend_token_lifetime(self, key: str):
        if self._redis_client:
            self._redis_client.expire(key, int(config.REFRESH_TOKEN_EXPIRE_MINUTES * 60))

    def close(self):
        if self._redis_client:
            self._redis_client.close()

    def set_expired_data(self, key: str, value: str):
        self._redis_client.setex(key, int(config.REFRESH_TOKEN_EXPIRE_MINUTES * 60), value)

    def get_expired_data(self, key: str):
        value = self._redis_client.get(key)
        if value:
            return value.decode('utf-8')
        return None
