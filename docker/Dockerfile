FROM openjdk:17.0.1-jdk-buster
LABEL maintainer="max.boyar.a@gmail.com"
LABEL version="1.0"

COPY . /
WORKDIR /
RUN chmod +x /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]

EXPOSE 8080
