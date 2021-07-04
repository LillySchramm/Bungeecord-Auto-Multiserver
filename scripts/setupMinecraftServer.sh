# This script installs a minecraft server and, if wished, a luckyperms with MongoDB config.

read -r -p "Do you want luckyperms with MongoDB installed? [y/N] " response
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
apt install openjdk-16-jre-headless git -y

# Setup Minecraft Server

cd #
mkdir minecraft
cd minecraft

wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
java -Xmx1G -jar BuildTools.jar --rev 1.17

echo "eula=true" > eula.txt
echo "java -Xmx3G -Xms3G -jar spigot-1.17.jar --nogui" > start.sh

chmod +x start.sh

# Setup Luckyperms

if [ -z ${URI+x} ];
then
    echo "Skiping MongoDB setup."
else
    mkdir plugins

    cd plugins/
    wget https://ci.lucko.me/job/LuckPerms/1348/artifact/bukkit/loader/build/libs/LuckPerms-Bukkit-5.3.50.jar

    mkdir LuckPerms
    cd LuckPerms
    wget -O config.yml https://pastebin.com/raw/6uibygMb
    sleep .5
    sed -i "s#plsreplace#${URI}#g" config.yml

    cd ..
    cd ..
fi


# Start the server

./start.sh