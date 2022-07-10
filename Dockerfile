FROM centos:7

RUN yum install -y java-1.8.0-openjdk

WORKDIR /app

COPY target/ftc-customer-v.1.0.jar ./ftc-customer-v.1.0.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "ftc-customer-v.1.0.jar"]

#for running container to use:
#docker run -p 8080:8080 -e POSTGRES_URL=192.168.128.0 -e POSTGRES_PORT=5432 -e POSTGRES_USERNAME=username -e POSTGRES_PASSWORD=password --network=your_postgresql_network --name container_name image_name


