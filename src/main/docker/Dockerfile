FROM openjdk:8

WORKDIR /var/www/store/

RUN apt update

# requirements for hot reloader script
RUN apt install inotify-tools -y
RUN apt install maven -y