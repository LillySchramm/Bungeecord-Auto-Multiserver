apt update
apt upgrade -y
apt install openjdk-16-jre-headless git -y

cd #
mkdir minecraft
cd minecraft

wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
java -Xmx1G -jar BuildTools.jar --rev 1.17

echo "eula=true" > eula.txt
echo "java -Xmx3G -Xms3G -jar spigot-1.17.jar --nogui" > start.sh

chmod +x start.sh

./start.sh