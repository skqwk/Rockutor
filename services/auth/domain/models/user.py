from typing import Optional

from pydantic import BaseModel


class User(BaseModel):
    username: str
    password: str
    role: Optional[str]


class UserDTO(BaseModel):
    id: int
    username: str
    password: str
    role: str
    client_id: str
    client_secret: str

    @staticmethod
    def from_list(list_param: list) -> User:
        return User(username=list_param[0][1],
                    password=list_param[0][2],
                    role=list_param[0][3])
