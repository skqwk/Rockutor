import unittest
from unittest import TestCase
from unittest.mock import MagicMock

from adapter.database.redis import RedisDB
from settings import config


class TestRedisDB(TestCase):
    def setUp(self):
        self.redis_db = RedisDB()
        self.redis_db._redis_client = MagicMock()

    @unittest.skip("demonstrating skipping")
    def test_connect(self):
        self.fail()

    def test_set(self):
        key = "test_key"
        value = "test_value"
        self.redis_db.set(key, value)
        self.redis_db._redis_client.set.assert_called_once_with(key, value)

    def test_get(self):
        key = "test_key"
        self.redis_db.get(key)
        self.redis_db._redis_client.get.assert_called_once_with(key)

    def test_delete(self):
        key = "test_key"
        result = self.redis_db.delete(key)
        self.redis_db._redis_client.delete.assert_called_once_with(key)
        self.assertIn(result, [0, 1, None], "Delete result should be 0 or 1")

    def test_extend_token_lifetime(self):
        key = "test_key"
        self.redis_db.extend_token_lifetime(key)
        self.redis_db._redis_client.expire.assert_called_once_with(key, int(config.REFRESH_TOKEN_EXPIRE_MINUTES * 60))

    @unittest.skip("demonstrating skipping")
    def test_close(self):
        self.fail()

    def test_get_expired_data(self):
        key = "test_key"
        self.redis_db._redis_client.get.return_value = b"test_value"
        result = self.redis_db.get_expired_data(key)
        self.assertEqual(result, "test_value")

    def test_get_expired_data_nonexistent_key(self):
        key = "nonexistent_key"
        self.redis_db._redis_client.get.return_value = None
        result = self.redis_db.get_expired_data(key)
        self.assertIsNone(result)
