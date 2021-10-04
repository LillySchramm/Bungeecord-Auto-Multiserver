# For the minecraft/client server

FROM ubuntu:18.04

WORKDIR /server

ENV VERSION="1.12.2"

ENV BUNGEE_ADRESS=""
ENV BUNGEE_PASSWORD=""

ENV SERVER_PORT=30000
ENV SERVER_TYPE="Hub"
ENV SERVER_PLAYER_MAX=40
ENV SERVER_VIEW_MAX=8
ENV SERVER_RAM=1500

ENV MONGO_URI="mongodb://"

ENV JAVA_8="/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre/bin/java"
ENV JAVA_16="/usr/lib/jvm/java-1.16.0-openjdk-amd64/bin/java"

COPY world world

RUN apt update
RUN apt-get install software-properties-common -y
RUN add-apt-repository ppa:openjdk-r/ppa
RUN apt update
RUN apt install openjdk-16-jre-headless -y
RUN apt install openjdk-8-jre-headless -y
RUN apt install git -y
RUN apt install wget -y

RUN wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar

RUN echo 'VERSIONS_V8=(1.12 1.12.1 1.12.2 1.13 1.13.1 1.13.2 1.14 1.14.1 1.14.2 1.14.3 1.14.4 1.15 1.15.1 1.15.2 1.16.1 1.16.2 1.16.3 1.16.4 1.16.5)' >> temp.sh
RUN echo 'VERSIONS_V16=(1.17)'                                                                                          >> temp.sh
RUN echo ''                                                                                                             >> temp.sh
RUN echo 'if [[ " ${VERSIONS_V8[@]} " =~ " ${VERSION} " ]]; then'                                                       >> temp.sh
RUN echo '    JAVA=$JAVA_8    '                                                                                         >> temp.sh
RUN echo 'fi'                                                                                                           >> temp.sh
RUN echo ''                                                                                                             >> temp.sh
RUN echo 'if [[ " ${VERSIONS_V16[@]} " =~ " ${VERSION} " ]]; then'                                                      >> temp.sh
RUN echo '    JAVA=$JAVA_16            '                                                                                >> temp.sh
RUN echo 'fi'                                                                                                           >> temp.sh
RUN echo ''                                                                                                             >> temp.sh
RUN echo 'echo "#Installed via script"                                                                    >> server.sh' >> temp.sh
RUN echo 'echo "sha512sum BungeecordAutoConfig.jar"                                                       >> server.sh' >> temp.sh
RUN echo 'echo "cd ${PWD}"                                                                                >> server.sh' >> temp.sh
RUN echo 'echo "while true; do"                                                                           >> server.sh' >> temp.sh
RUN echo 'echo "  ${JAVA} -Xmx${SERVER_RAM}M -Xms${SERVER_RAM}M -jar spigot-${VERSION}.jar"               >> server.sh' >> temp.sh
RUN echo 'echo "done"                                                                                     >> server.sh' >> temp.sh
RUN echo ''                                                                                                             >> temp.sh
RUN echo '$JAVA -Xmx1G -jar BuildTools.jar --rev $VERSION'                                                              >> temp.sh


RUN bash temp.sh
RUN rm temp.sh
RUN chmod +x server.sh

RUN echo "enable-jmx-monitoring=false"          >> server.properties
RUN echo "rcon.port=25575"                      >> server.properties
RUN echo "enable-command-block=false"           >> server.properties
RUN echo "gamemode=survival"                    >> server.properties
RUN echo "enable-query=false"                   >> server.properties
RUN echo "level-name=world"                     >> server.properties
RUN echo "motd=A Minecraft Server"              >> server.properties
RUN echo "query.port=${SERVER_PORT}"            >> server.properties
RUN echo "pvp=true"                             >> server.properties
RUN echo "difficulty=easy"                      >> server.properties
RUN echo "network-compression-threshold=256"    >> server.properties
RUN echo "max-tick-time=60000"                  >> server.properties
RUN echo "require-resource-pack=false"          >> server.properties
RUN echo "max-players=${SERVER_PLAYER_MAX}"     >> server.properties
RUN echo "use-native-transport=true"            >> server.properties
RUN echo "online-mode=false"                    >> server.properties
RUN echo "enable-status=true"                   >> server.properties
RUN echo "allow-flight=false"                   >> server.properties
RUN echo "broadcast-rcon-to-ops=true"           >> server.properties
RUN echo "view-distance=${SERVER_VIEW_MAX}"     >> server.properties
RUN echo "server-ip="                           >> server.properties
RUN echo "resource-pack-prompt="                >> server.properties
RUN echo "allow-nether=true"                    >> server.properties
RUN echo "server-port=${SERVER_PORT}"           >> server.properties
RUN echo "enable-rcon=false"                    >> server.properties
RUN echo "sync-chunk-writes=true"               >> server.properties
RUN echo "op-permission-level=4"                >> server.properties
RUN echo "prevent-proxy-connections=false"      >> server.properties
RUN echo "resource-pack="                       >> server.properties
RUN echo "entity-broadcast-range-percentage=100">> server.properties
RUN echo "rcon.password="                       >> server.properties
RUN echo "player-idle-timeout=0"                >> server.properties
RUN echo "debug=false"                          >> server.properties
RUN echo "force-gamemode=false"                 >> server.properties
RUN echo "rate-limit=0"                         >> server.properties
RUN echo "hardcore=false"                       >> server.properties
RUN echo "white-list=false"                     >> server.properties
RUN echo "broadcast-console-to-ops=true"        >> server.properties
RUN echo "spawn-npcs=true"                      >> server.properties
RUN echo "spawn-animals=true"                   >> server.properties
RUN echo "snooper-enabled=true"                 >> server.properties
RUN echo "function-permission-level=2"          >> server.properties
RUN echo "text-filtering-config="               >> server.properties
RUN echo "spawn-monsters=true"                  >> server.properties
RUN echo "enforce-whitelist=false"              >> server.properties
RUN echo "resource-pack-sha1="                  >> server.properties
RUN echo "spawn-protection=16"                  >> server.properties
RUN echo "max-world-size=29999984"              >> server.properties

RUN echo "eula=true" > eula.txt

RUN mkdir plugins
RUN cd plugins && wget https://ci.eps-dev.de/job/BungeecordAutoConfig-Spigot/lastSuccessfulBuild/artifact/Spigot/target/BungeecordAutoConfig.jar 
RUN mkdir plugins/BungeecordAutoConfig

RUN echo "bungee_address: '${BUNGEE_ADRESS}'"       >> plugins/BungeecordAutoConfig/config.yml
RUN echo "bungee_password: '${BUNGEE_PASSWORD}'"    >> plugins/BungeecordAutoConfig/config.yml
RUN echo "server_type: '${SERVER_TYPE}'"            >> plugins/BungeecordAutoConfig/config.yml
RUN echo "signs: []"                                >> plugins/BungeecordAutoConfig/config.yml

RUN cd plugins && wget https://ci.lucko.me/job/LuckPerms/1368/artifact/bukkit/loader/build/libs/LuckPerms-Bukkit-5.3.69.jar &&\
    mkdir LuckPerms && cd LuckPerms &&\
    wget -O config.yml https://pastebin.com/raw/6uibygMb &&\
    sed -i "s#plsreplace#${MONGO_URI}#g" config.yml

RUN rm -rf work
RUN rm -rf BuildData
RUN rm -rf Spigot
RUN rm -rf Bukkit

EXPOSE ${SERVER_PORT}

CMD ./server.sh
