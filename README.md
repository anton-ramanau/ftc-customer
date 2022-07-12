# ftc-customer

>##### Application for making transport orders

### Applictation is available by URL: http://rastafiz.ddns.net:9080

#### Docker instructions. *Example*

- Build your image for amd64 platform (you can choose other platform depending on your system):
>docker build --platform linux/amd64 -t antonramanau/ftc-customer:1.0-amd64 ./

- Run postgresql DB:
> docker run --name ftc-customer-postgres \
--network=ftc-customer \
-v /.***path for saving data on your local machine***:/var/lib/postgresql/data \
-e POSTGRES_PASSWORD=***"yourpassword"*** \
-e POSTGRES_USER=***"yourusername"*** \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-p ***"your free port"***:5432 \
--restart unless-stopped postgres:13.3 

> create data base with the name ***ftc_customer_db***

- Run container:
> docker run --name ftc-customer-app \
-p ***"your free port for app"***:8080 \
-e POSTGRES_URL=***"DB URL"*** \
-e POSTGRES_PORT=***"DB port"*** \
-e POSTGRES_USERNAME=***"DB username"*** \
-e POSTGRES_PASSWORD=***"DB user password"*** \
--network=***"Docker network. The same with DB"***  \
--restart unless-stopped \
antonramanau/ftc-customer:1.0-amd64
