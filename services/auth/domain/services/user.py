from adapter.database.postgresql import PostgresDB
from typing import Optional

from domain.models.user import User


class UserService:
    def __init__(self, db: PostgresDB):
        self.db = db

    def create_user(self, user: User):
        return self.db.create(table_name='rksp_users', data=[user.username, user.password])

    def find_user(self, user: User):
        return self.db.find_by_param(table_name='rksp_users',
                                     conditional_data={
                                         "data": user.username,
                                         "column": "username"}
                                     )
    # async def get_user_by_username(self, username: str) -> Optional[User]:
    #     async with self.db.connect() as conn:
    #         query = "SELECT * FROM rksp_users WHERE username = $1"
    #         record = await conn.fetchrow(query, username)
    #         if record:
    #             return User(**record)
    #         return None
