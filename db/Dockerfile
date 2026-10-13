# MySQL 8.4 LTS. Anything in /docker-entrypoint-initdb.d runs once, the first time the container starts.
FROM mysql:8.4
COPY world.sql /docker-entrypoint-initdb.d/
ENV MYSQL_ROOT_PASSWORD=example
