from enum import Enum


class JWTokenStatus(Enum):
    VALID: str = "JWT is valid and has not expired."
    EXPIRED: str = "JWT has expired."
    ERROR: str = "JWSError."
