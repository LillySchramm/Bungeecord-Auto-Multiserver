# For the Bungee Server

FROM ubuntu:18.04

ENV PASSWORD=""
ENV DEFAULT_SERVER="Hub"

ENV FTP_ADDRESS=""
ENV FTP_PORT=0
ENV FTP_USER=""
ENV FTP_PASSWORD=""

WORKDIR /server

RUN apt update
RUN apt-get install software-properties-common -y
RUN add-apt-repository ppa:openjdk-r/ppa
RUN apt update
RUN apt install openjdk-16-jre-headless wget -y

RUN wget https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar

RUN mkdir plugins

RUN cd plugins && wget https://ci.eps-dev.de/job/BungeecordAutoConfig-Bungee/lastSuccessfulBuild/artifact/Bungee/target/Bungee.jar
RUN mkdir plugins/Bungee

RUN echo "key: '${PASSWORD}'"                                   >> plugins/Bungee/config.yml
RUN echo "default_type: '${DEFAULT_SERVER}'"                    >> plugins/Bungee/config.yml 
RUN echo "ftp_server_address: '${FTP_ADDRESS}'"                 >> plugins/Bungee/config.yml
RUN echo "ftp_server_port: ${FTP_PORT}"                         >> plugins/Bungee/config.yml
RUN echo "ftp_server_user: '${FTP_USER}'"                       >> plugins/Bungee/config.yml
RUN echo "ftp_server_password: '${FTP_PASSWORD}'"               >> plugins/Bungee/config.yml


RUN echo "#Installed via script"                                                                    >> server.sh
RUN echo "while true; do"                                                                           >> server.sh
RUN echo "  /usr/lib/jvm/java-1.16.0-openjdk-amd64/bin/java -Xmx512M -Xms512M -jar BungeeCord.jar"  >> server.sh
RUN echo "done"                                                                                     >> server.sh

RUN chmod +x server.sh

EXPOSE 10101
EXPOSE 25565

CMD ./server.sh