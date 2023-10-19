import json
import time
from datetime import datetime, timedelta
from typing import Union

import jose
from jose import jwt, jws


from adapter.database.redis import RedisDB
from domain.models.jwt import JWTokenStatus
from domain.models.token import Token, VerifyAnswer
from settings import config, logger


class TokenService:
    def __init__(self, db: RedisDB):
        self.redis_db = db

    def save_user_token(self, username: str, refresh_token: str):
        self.redis_db.set_expired_data(key=username, value=refresh_token)

    def check_refresh_token(self, username: str, refresh_token: str):
        result_check = self.redis_db.get_expired_data(key=username)
        if result_check and result_check == refresh_token:
            return True
        return False

    @staticmethod
    def create_access_token(data: dict, expires_delta: Union[timedelta, None] = None):
        to_encode = data.copy()
        if expires_delta:
            expire = datetime.utcnow() + expires_delta
        else:
            expire = datetime.utcnow() + timedelta(minutes=config.ACCESS_TOKEN_EXPIRE_MINUTES)
        to_encode.update({"exp": expire})
        encoded_jwt = jwt.encode(to_encode, config.SECRET_KEY, algorithm=config.ALGORITHM)
        return encoded_jwt

    def create_refresh_token(self, data: dict, expires_delta: timedelta = None):
        to_encode = data.copy()
        if expires_delta:
            expire = datetime.utcnow() + expires_delta
        else:
            expire = datetime.utcnow() + timedelta(minutes=config.REFRESH_TOKEN_EXPIRE_MINUTES)
        to_encode.update({"exp": expire})
        encoded_jwt = jwt.encode(to_encode, config.SECRET_KEY, algorithm=config.ALGORITHM)

        self.save_user_token(username=data.get("sub", None), refresh_token=encoded_jwt)
        return encoded_jwt

    @staticmethod
    def verify_token(signed: str):
        try:
            result = jws.verify(signed,
                                config.SECRET_KEY,
                                algorithms=config.ALGORITHM)

            expiration_time = json.loads(result.decode('utf-8')).get("exp", None)
            username = json.loads(result.decode('utf-8')).get("sub", None)
            role = json.loads(result.decode('utf-8')).get("role", None)
            current_time = int(time.time())

            if expiration_time is not None and current_time <= expiration_time:
                return VerifyAnswer(username=username,
                                    role=role,
                                    message=JWTokenStatus.VALID.value)
            else:
                return JWTokenStatus.EXPIRED

        except jose.exceptions.JWSError as e:
            logger.error(f'{e.with_traceback(e.__traceback__)}')
            return JWTokenStatus.ERROR

    def refresh_refresh_token(self, refresh_token: str) -> Union[Token, JWTokenStatus]:
        result_check_refresh = self.verify_token(signed=refresh_token)
        if isinstance(result_check_refresh, VerifyAnswer):
            result = jws.verify(refresh_token,
                                config.SECRET_KEY,
                                algorithms=config.ALGORITHM)
            username = json.loads(result.decode('utf-8')).get("sub", None)
            role = json.loads(result.decode('utf-8')).get("role", None)

            result_check_in_redis = self.check_refresh_token(username=username, refresh_token=refresh_token)

            if not result_check_in_redis:
                return JWTokenStatus.EXPIRED

            # self.redis_db.extend_token_lifetime(key=username)
            access_token_expires = timedelta(minutes=config.ACCESS_TOKEN_EXPIRE_MINUTES)
            new_access_token = self.create_access_token(
                data={"sub": username, "role": role}, expires_delta=access_token_expires
            )

            refresh_token_expires = timedelta(minutes=config.REFRESH_TOKEN_EXPIRE_MINUTES)
            new_refresh_token = self.create_refresh_token(
                data={"sub": username, "role": role}, expires_delta=refresh_token_expires
            )

            return Token(access_token=new_access_token,
                         token_type='bearer',
                         refresh_token=new_refresh_token)

        return JWTokenStatus.EXPIRED

# print(TokenService.verify_token('eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyXzEiLCJleHAiOjE2OTc0ODcwMzl9.u5k1UodMMY5Kloq_-xPPIsDX2e-2pJ1rJ94qHYf-k-Y'))
