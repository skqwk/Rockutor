from datetime import datetime, timedelta

from fastapi import Depends, HTTPException, APIRouter, Form
from fastapi.responses import JSONResponse
from domain.models.jwt import JWTokenStatus
from domain.models.response_model import ErrorResponse, SuccessResponseRegister, SuccessResponseToken, \
    SuccessResponseVerify, Message
from domain.models.token import Token, VerifyAnswer
from domain.models.user import User, UserDTO
from domain.services.token import TokenService
from domain.services.user import UserService
from inject import get_user_service, get_token_service
from settings import config

router = APIRouter()


@router.post("/token",
             responses={
                 200: {"model": Token, "description": "Token is valid"},
                 400: {"model": Message, "description": "Incorrect username or password"},
                 500: {"model": Message, "description": "Internal server error"}
             })
async def login_for_access_token(
        user: User,
        user_service: UserService = Depends(get_user_service),
        token_service: TokenService = Depends(get_token_service)
):
    user_find_list = user_service.find_user(user=user)
    if user_find_list == []:
        return JSONResponse(status_code=400, content={"detail": "Incorrect username or password"})
        #raise HTTPException(status_code=400, detail="Incorrect username or password")

    print(user_find_list)
    user_find = UserDTO.from_list(list_param=user_find_list)

    if not user_find_list or user.password != user_find.password:
        return JSONResponse(status_code=400, content={"detail": "Incorrect username or password"})
        #raise HTTPException(status_code=400, detail="Incorrect username or password")

    # Здесь генерируйте и возвращайте токен OAuth 2.0
    access_token_expires = timedelta(minutes=config.ACCESS_TOKEN_EXPIRE_MINUTES)
    access_token = token_service.create_access_token(
        data={"sub": user.username, "role": user_find.role}, expires_delta=access_token_expires
    )
    refresh_token_expires = timedelta(minutes=config.REFRESH_TOKEN_EXPIRE_MINUTES)
    refresh_token = token_service.create_refresh_token(
        data={"sub": user.username, "role": user_find.role}, expires_delta=refresh_token_expires
    )
    return Token(access_token=access_token,
                 token_type='bearer',
                 refresh_token=refresh_token)


@router.post("/verify",
             responses={
                 200: {"model": SuccessResponseVerify, "description": "Token is valid"},
                 403: {"model": Message, "description": "JWT has expired or Signature verification failed"},
                 500: {"model": Message, "description": "Internal server error"}
             })
async def verify_access_token(
        token: str,
        user_service: UserService = Depends(get_user_service),
        token_service: TokenService = Depends(get_token_service)
):
    result_check = token_service.verify_token(signed=token)

    if isinstance(result_check, VerifyAnswer):
        return {"status": "success", "message": "Token is valid", "data": result_check}
        # return JSONResponse(content={"message": "Token is valid"}, status_code=200)
    elif result_check == JWTokenStatus.EXPIRED:
        return JSONResponse(status_code=403, content={"detail": "JWT has expired"})
        # raise HTTPException(status_code=403, detail="JWT has expired")
    else:
        return JSONResponse(status_code=403, content={"detail": "Signature verification failed"})
        # raise HTTPException(status_code=403, detail="Signature verification failed")


@router.post("/refresh",
             responses={
                 200: {"model": Token, "description": "Token is valid"},
                 403: {"model": Message, "description": "JWT has expired"},
                 500: {"model": Message, "description": "Internal server error"}
             })
async def verify_access_token(
        refresh_token: str,
        user_service: UserService = Depends(get_user_service),
        token_service: TokenService = Depends(get_token_service)
):
    result_check = token_service.refresh_refresh_token(refresh_token=refresh_token)

    if result_check == JWTokenStatus.EXPIRED:
        return JSONResponse(status_code=403, content={"detail": "JWT has expired"})
        # raise HTTPException(status_code=403, detail="JWT has expired")
    else:
        return result_check
