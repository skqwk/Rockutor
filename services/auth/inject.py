from adapter.database.postgresql import PostgresDB
from adapter.database.redis import RedisDB
from domain.services.token import TokenService
from domain.services.user import UserService
from settings import Settings


settings = Settings()

postgres_db = PostgresDB()
redis_db = RedisDB()


def get_user_service() -> UserService:
    return UserService(postgres_db)


def get_token_service() -> TokenService:
    return TokenService(redis_db)


def get_cors_address():
    return [data for data in settings.cors_data.split(',')]
