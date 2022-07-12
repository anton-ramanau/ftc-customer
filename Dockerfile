FROM centos:7

RUN yum install -y java-1.8.0-openjdk

WORKDIR /app

COPY target/ftc-customer-v.1.0.jar ./ftc-customer-v.1.0.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "ftc-customer-v.1.0.jar"]


