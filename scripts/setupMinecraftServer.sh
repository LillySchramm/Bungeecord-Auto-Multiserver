# This script installs a minecraft server, BungeecordAutoConfig and, if wished, a luckyperms with MongoDB config.

JAVA_8="/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre/bin/java"
JAVA_16="/usr/lib/jvm/java-1.16.0-openjdk-amd64/bin/java"

VERSIONS_V8=(1.12 1.12.1 1.12.2 1.13 1.13.1 1.13.2 1.14 1.14.1 1.14.2 1.14.3 1.14.4 1.15 1.15.1 1.15.2 1.16.1 1.16.2 1.16.3 1.16.4 1.16.5)
VERSIONS_V16=(1.17)

# Inputs

while true; do
    
    echo "WARNING: Currently only version 1.17 is considered stable. Other versions might work but are highly experimental, unstable and aren't supported. In the future I am planing to support 1.12+."
    read -p "Enter the server version you want: " serverVersion

    if [[ " ${VERSIONS_V8[@]} " =~ " ${serverVersion} " ]]; then
        JAVA=$JAVA_8
        break
    fi

    if [[ " ${VERSIONS_V16[@]} " =~ " ${serverVersion} " ]]; then
        JAVA=$JAVA_16
        break        
    fi

    echo "Version '${serverVersion}' not found. It could be that it is a) to new; b) older than 1.12; c) not supported by bukkit."
done

read -p "Enter the address of your bungeecord server: " bungeeAddress
read -p "Enter the password of your bungeecord server: " bungeePassword
read -p "Enter the port that this server should run at: " serverPort
read -p "Enter the type this server is (cAsE sensitive!): " serverType
read -p "Enter the amount of players that can play on this server: " serverPlayerMax
read -p "Enter the amount of view distance this server should allow players to see (in chunks): " serverViewMax
read -p "Enter the amount of ram this server should have (in MB): " serverRam

read -r -p "Do you want luckyperms with MongoDB installed? (requires an mongoDB URI) [y/N] " response
case "$response" in
    [yY][eE][sS]|[yY])

        read -p "Enter your MongoDB-URI:" URI

        ;;
    *)

        ;;
esac

# System Update

apt update
apt upgrade -y
apt install openjdk-16-jre-headless openjdk-8-jre-headless screen git -y

# Setup Minecraft Server

mkdir minecraft
cd minecraft

wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar

$JAVA -Xmx1G -jar BuildTools.jar --rev $serverVersion

echo "eula=true" > eula.txt

echo "#Installed via script"                                                                    >> server.sh
echo "cd ${PWD}"                                                                                >> server.sh
echo "while true; do"                                                                           >> server.sh
echo "  ${JAVA} -Xmx${serverRam}M -Xms${serverRam}M -jar spigot-${serverVersion}.jar --nogui"   >> server.sh
echo "done"                                                                                     >> server.sh

echo "cd ${PWD}"                                                                                >> start.sh                    
echo "screen -dmS Minecraft_${serverType}_${serverPort} ./server.sh"                            >> start.sh

chmod +x start.sh server.sh

echo "enable-jmx-monitoring=false"          >> server.properties
echo "rcon.port=25575"                      >> server.properties
echo "enable-command-block=false"           >> server.properties
echo "gamemode=survival"                    >> server.properties
echo "enable-query=false"                   >> server.properties
echo "level-name=world"                     >> server.properties
echo "motd=A Minecraft Server"              >> server.properties
echo "query.port=${serverPort}"             >> server.properties
echo "pvp=true"                             >> server.properties
echo "difficulty=easy"                      >> server.properties
echo "network-compression-threshold=256"    >> server.properties
echo "max-tick-time=60000"                  >> server.properties
echo "require-resource-pack=false"          >> server.properties
echo "max-players=${serverPlayerMax}"       >> server.properties
echo "use-native-transport=true"            >> server.properties
echo "online-mode=false"                    >> server.properties
echo "enable-status=true"                   >> server.properties
echo "allow-flight=false"                   >> server.properties
echo "broadcast-rcon-to-ops=true"           >> server.properties
echo "view-distance=${serverViewMax}"       >> server.properties
echo "server-ip="                           >> server.properties
echo "resource-pack-prompt="                >> server.properties
echo "allow-nether=true"                    >> server.properties
echo "server-port=${serverPort}"            >> server.properties
echo "enable-rcon=false"                    >> server.properties
echo "sync-chunk-writes=true"               >> server.properties
echo "op-permission-level=4"                >> server.properties
echo "prevent-proxy-connections=false"      >> server.properties
echo "resource-pack="                       >> server.properties
echo "entity-broadcast-range-percentage=100">> server.properties
echo "rcon.password="                       >> server.properties
echo "player-idle-timeout=0"                >> server.properties
echo "debug=false"                          >> server.properties
echo "force-gamemode=false"                 >> server.properties
echo "rate-limit=0"                         >> server.properties
echo "hardcore=false"                       >> server.properties
echo "white-list=false"                     >> server.properties
echo "broadcast-console-to-ops=true"        >> server.properties
echo "spawn-npcs=true"                      >> server.properties
echo "spawn-animals=true"                   >> server.properties
echo "snooper-enabled=true"                 >> server.properties
echo "function-permission-level=2"          >> server.properties
echo "text-filtering-config="               >> server.properties
echo "spawn-monsters=true"                  >> server.properties
echo "enforce-whitelist=false"              >> server.properties
echo "resource-pack-sha1="                  >> server.properties
echo "spawn-protection=16"                  >> server.properties
echo "max-world-size=29999984"              >> server.properties

# Setup Plugins

mkdir plugins
cd plugins/

## Setup BungeeAutoConfig

wget https://ci.eps-dev.de/job/BungeecordAutoConfig-Spigot/lastSuccessfulBuild/artifact/Spigot/target/BungeecordAutoConfig.jar --no-check-certificate # For some reason my certs aren't known even though all browsers accept them without a problem
mkdir BungeecordAutoConfig/
cd BungeecordAutoConfig

echo "bungee_address: '${bungeeAddress}'"   >> config.yml
echo "bungee_password: '${bungeePassword}'" >> config.yml
echo "server_type: '${serverType}'"         >> config.yml
echo "signs: []"                            >> config.yml

cd ..

## Setup Luckyperms

if [ -z ${URI+x} ];
then
    echo "Skiping Luckyperms setup."
else
    wget https://ci.lucko.me/job/LuckPerms/1348/artifact/bukkit/loader/build/libs/LuckPerms-Bukkit-5.3.50.jar

    mkdir LuckPerms
    cd LuckPerms
    wget -O config.yml https://pastebin.com/raw/6uibygMb
    sleep .5
    sed -i "s#plsreplace#${URI}#g" config.yml
fi

cd ../..


# Start the server

echo " "
echo " "
echo " "
echo "--------------------------------------------------"
echo "Finished the server setup."
echo "The server will start in 10 seconds and might"
echo "restart a few times in order to finish the config."
echo " "
echo "After that the server will connect to the bungee"
echo "network. (if it was configured correctly)-"
echo " "
echo "Thank you for using BungeecordAutoConfig."
echo "Please report bugs at: "
echo "https://github.com/EliasSchramm/Bungeecord-Auto-Multiserver/issues"
echo " "
echo "--------------------------------------------------"
echo " "
echo " "
echo " "

sleep 10

./start.sh
screen -r Minecraft_${serverType}_${serverPort}
