from pydantic import BaseModel

from domain.models.token import Token


class SuccessResponseRegister(BaseModel):
    status: str = "success"
    message: str = "Operation was successful"
    data: int


class SuccessResponseVerify(BaseModel):
    status: str = "success"
    message: str = "Operation was successful"
    data: int


class SuccessResponseToken(BaseModel):
    status: str = "success"
    message: str = "Operation was successful"
    data: Token


class ErrorResponse(BaseModel):
    status: str = "error"
    detail: str


class Message(BaseModel):
    message: str
