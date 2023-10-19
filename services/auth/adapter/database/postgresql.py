import psycopg2

from settings import config, logger


class PostgresDB:
    def __init__(self):
        self._dbname = config.postgres_db
        self._user = config.postgres_user
        self._password = config.postgres_password
        self._host = config.postgres_host
        self._port = config.postgres_port
        self._connection = None
        self.dsn = "postgresql://" + self._user + ":" + self._password + "@" + self._host + "/" + self._port

    def __init__(self):
        self._dbname = config.postgres_db
        self._user = config.postgres_user
        self._password = config.postgres_password
        self._host = config.postgres_host
        self._port = config.postgres_port
        self._connection = None

    def _connect(self):
        try:
            if self._connection is None or self._connection.closed != 0:
                self._connection = psycopg2.connect(
                    dbname=self._dbname,
                    user=self._user,
                    password=self._password,
                    host=self._host,
                    port=self._port
                )
            logger.info(f"Database: Connect to database")
            return self._connection
        except Exception as error:
            logger.error(f"Database: {error.with_traceback()}")
            return None

    def _disconnect(self):
        try:
            if self._connection is not None and self._connection.closed == 0:
                self._connection.close()
                logger.info(f"Database: Disconnect from database")
        except Exception as error:
            logger.error(f"Database: {error.with_traceback()}")

    def create(self, table_name, data: list, column_names="username, password, user_role"):
        self._connection = self._connect()
        if self._connection:
            cur = self._connection.cursor()
            data = ", ".join(["'" + str(item) + "'" for item in data])
            data_column = ", ".join(["'" + str(item) + "'" for item in column_names])
            cur.execute(f'INSERT INTO {table_name} ({column_names}) VALUES ({data}) ON CONFLICT DO NOTHING')

            self._connection.commit()
            self._connection.close()
            self._disconnect()
            return cur.rowcount
        return None

    def find_by_param(self, table_name, conditional_data: dict):
        self._connection = self._connect()
        if self._connection:
            cur = self._connection.cursor()
            data = "'" + conditional_data['data'] + "'"
            cur.execute(f'SELECT * FROM {table_name} WHERE {conditional_data["column"]} = {data}')

            result = cur.fetchall()
            self._connection.close()
            self._disconnect()
            return result
        return None