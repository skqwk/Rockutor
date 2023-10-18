from fastapi import Depends, HTTPException, APIRouter
from starlette.responses import JSONResponse

from domain.models.response_model import SuccessResponseRegister, ErrorResponse, Message
from domain.services.user import UserService

from domain.models.user import User
from inject import get_user_service

router = APIRouter()


@router.post("/register",
             responses={
                 200: {"model": SuccessResponseRegister, "description": "Token is valid"},
                 404: {"model": Message, "description": "User with these credential already exist."},
                 500: {"model": Message, "description": "Internal server error"}
             }
             )
async def register_user(user: User,
                        user_service: UserService = Depends(get_user_service)
                        ):
    user_in_db = user_service.find_user(user=user)
    if user_in_db is not None and user_in_db != []:
        return JSONResponse(status_code=400, content={"detail": "User with these credential already exist"})
        # raise HTTPException(status_code=400, detail="User with these credential already exist")
    user_result = user_service.create_user(user)

    if user_result is not None:
        return SuccessResponseRegister(status="success",
                                       message="Token is valid",
                                       data=int(user_result))
    # raise HTTPException(status_code=500, detail="Some error on the server")
    return ErrorResponse(status_code=500, content={"detail": "Some error on the server"})
