# This script sets up an Bungecoord server plus BungeecordAutoConfig

# Inputs

echo "You need to set a password. A strong password should be chosen because it is used to connect servers to the network."
read -p "Enter a password for this server: " password
echo " "

read -p "Default Server Type: " defaultServer

#System Update

apt update
apt upgrade -y

apt install openjdk-16-jre-headless screen -y

JAVA_16="/usr/lib/jvm/java-1.16.0-openjdk-amd64/bin/java"

# Bungeecord setup

mkdir bungeecord
cd bungeecord

wget https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar

echo "#Installed via script"                            >> server.sh
echo "while true; do"                                   >> server.sh
echo "  ${JAVA_16} -Xmx1G -Xms1G -jar BungeeCord.jar"   >> server.sh
echo "done"                                             >> server.sh

echo "cd ${PWD}"                                        >> start.sh
echo "screen -dmS Bungeecord ./server.sh"               >> start.sh

chmod +x server.sh start.sh

# Setup BungeecordAutoConfig

mkdir plugins
cd plugins

wget https://ci.eps-dev.de/job/BungeecordAutoConfig-Bungee/lastSuccessfulBuild/artifact/Bungee/target/Bungee.jar --no-check-certificate # For some reason my certs aren't known even though all browsers accept them without a problem

mkdir Bungee
cd Bungee

echo "key: '${password}'"                                   >> config.yml
echo "default_type: '${defaultServer}'"                     >> config.yml

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
screen -r Bungeecord