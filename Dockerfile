FROM postgres:12

ADD ./init-db.sh /docker-entrypoint-initdb.d/
ADD ./db.sql /db-setup/