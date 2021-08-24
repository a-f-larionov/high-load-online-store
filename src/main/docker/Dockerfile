FROM openjdk:8

WORKDIR /var/www/store/

# requirements for hot reloader script
RUN apt update
RUN apt install inotify-tools -y
RUN apt install maven -y