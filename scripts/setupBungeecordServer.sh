# This script sets up an Bungecoord server plus BungeecordAutoConfig

# Inputs

echo "You need to set a password. A strong password should be chosen because it is used to connect servers to the network."
read -p "Enter a password for this server: " password
echo " "

read -p "Default Server Type: " server_tpye

#System Update

apt update
apt upgrade -y

apt install openjdk-16-jre-headless screen -y

JAVA_16="/usr/lib/jvm/java-1.16.0-openjdk-amd64/bin/java"

# Bungeecord setup

mkdir bungeecord
cd bungeecord

wget https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar

echo "while true; do"                                   >> server.sh
echo "  ${JAVA_16} -Xmx1G -Xms1G -jar BungeeCord.jar"   >> server.sh
echo "done"                                             >> server.sh

echo "cd ${PWD}"                                        >> start.sh
echo "screen -dmS Bungeecord ./server.sh"               >> start.sh

chmod +x server.sh start.sh

# Setup BungeecordAutoConfig

mkdir plugins
cd plugins

wget https://ci.eps-dev.de/job/BungeecordAutoConfig-Bungee/lastSuccessfulBuild/artifact/Bungee/target/Bungee-1.0-SNAPSHOT.jar --no-check-certificate # For some reason my certs aren't known even though all browsers accept them without a problem 

mkdir Bungee
cd Bungee

echo "key: '${password}'"                               >> config.yml
echo "default_type: ${server_type}"                     >> config.yml

cd ../..

# Start the server

./start.sh
screen -r Bungeecord
