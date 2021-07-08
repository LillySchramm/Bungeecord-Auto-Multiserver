# Bungeecord Auto Multiserver

An easy way to start and maintain a bungeecord network.

----------------

# Contents

1. Overview
2. Usage
3. Installation <br>
   3.1 Automated <br>
   3.2 Manual <br>
4. Updating
5. Common problems
6. Contributing

# 1. Overview

This project is split in two parts: A Bungeecord plugin and a Spigot. These two play togetter in order to give you the 
ability to start and manage a Minecraft-Bungeecord network in the easiest way possible: Once the initial setup is 
complete it's literally drag 'n drop.

## Features

This project has more features than just its easy maintainability. 
<br>
It also features scripts that automate the entire installation process that also allows you to install 
[LuckPerms](https://www.spigotmc.org/resources/luckperms.28140/) networkwide (requires an MongoDB-URI). Besides that it also features Auto-Update so that you never have to worry about keeping your network up to date.
<br>
For ease of use it also supports teleport-signs. Portals, NPC, GUI and cross-server Inventorysync are yet to come.


## Support

The plugin supports everything that's version 1.12+.


# 2. Usage

This part only covers how to use the plugin. For installation instructions see __3. Installation__.

To create an bungeecord-network you need at least two servers, one for the bungeecord proxy and one for the Minecraft 
server, or one server with enough processing power, I'd recommend at least 4 cores and 6GB ram for that use case 
(not recommended because the proxy will be, depending on the size of your network, under constant load). <br>

### The Bungeecord Proxy

In a normal use case you shouldn't have to touch the proxy at all.

The proxy config file contains two important points:

1. ```key``` is the password which all game servers use to authenticate themselves.
2. ```default_type``` is the server type that all players will be sent to upon connecting. You should always have at 
   least one not-full server with this type online or else all connecting players will be shown the following message:
   <br>![error](https://i.imgur.com/rXfpXUQ.png)
   
### The Game Server

The game servers config has three important points:
1. ```bungee_address``` is the address of your Bungee-proxy.
2. ```bungee_password``` is the Bungee-proxies password.
3. ```server_type``` is the servers type (for example: 'Hub' or 'PvP') it is cAsE sensitive. It tells the proxy which
use case the server has. If you have multiple servers with the same name they will be counted as one and filled up one 
   after the other. If you, for example, have 10 Servers called 'PvP' with a capacity of 2 Players each,
   their capacity will show up on signs and ```/getserverinfo PvP``` as "x / 20 Players" even though, in the background, 
   they get filled with players separately. If you want your players to connect to a specific server you need to have 
   them in their own type. (For example ```Survival1```, ```Survival2```, ```Survival3```)
   
How to restart the server: Using ```/rl``` will cause heavy incompatibility issues, usage will get disabled in the 
future. Instead it's recommended to use ```/stop``` in order to properly restart the server.

### Commands

The plugin comes with a few important commands. __Warning: The default bungee commands will get disabled in a future 
release.__

- ```/instance``` returns the current instance a player is currently connected to.
    - No permissions required<br><br>
![msg](https://i.imgur.com/GaiDbC0.png)
      
- ```/changeinstance <player?> <instance>``` sends you or, if defined, a player to a specified instance (for example 
```T0xsR```).  
  - Requires the ```bungee.changeserver``` permission.
    
- ```/changeserver <player?> <type>``` sends you or, if defined, another player to an instance of the specified type (for example
  ```PvP```). Same as right-clicking a teleport-sign. 
    - Requires the ```bungee.changeserver``` permission.
    
- ```/getserverinfo <type>``` shows you all server, and their current population, of a specified type.
    - Requires the ```bungee.status``` permission.<br>
    ![msg](https://i.imgur.com/tlQjXW2.png)
 - ```/<defaultType>``` sends the player to a server with the type that was defined as default. 
    - No permissions required<br><br>
    
### Teleport-Signs

#### Functionality

Teleport signs are signs that, when right-clicked, send the player to another server.

<br>

![img](https://i.imgur.com/bCrVXDi.png)

<br>

Signs with destinations that are offline are shown like this: <br>

![img](https://i.imgur.com/quBdAbv.png)

#### How-To

To create a teleport-sign the player needs to have the ```bungee.editsign``` permission.

Just place a sign of your joice and type in the first line ```Bungee:<destination>``` (replace "<destination>" with your
desired destination)

![img](https://i.imgur.com/LOkFMh2.png)

And Press ```Done```.

The sign will take a few seconds to sync. After that everyone can right-click the sign to get teleported like with 
```/changeserver```. 

# 3.Installation

### 3.1 Automated

#### Requirements

In order to execute the automated scripts you need following:

- An SSH connection an sudo password to an Ubuntu v20+ server (other distributions/version might work but aren't tested)
- The server needs access to the internet
- The IP addresses of all involved servers

#### The bungee proxy
    
In order to install a game server you first need to set up a bungee proxy. 

This can be done by executing the following command: 

```shell
wget https://raw.githubusercontent.com/EliasSchramm/Bungeecord-Auto-Multiserver/main/scripts/setupBungeecordServer.sh && sudo chmod +x setupBungeecordServer.sh && sudo bash ./setupBungeecordServer.sh 
```

Enter the requested information:<br>
![cmd](https://i.imgur.com/8rIooiS.png) <br>

Hit enter again, and the script will start installing everything.
After it finished you will be sent to the screen-instance it is running in. It might restart a few times in order to 
finish the configuration. Once that's done you have a fully operational proxy.

#### The game server

To install the game server you have to execute the following command:

```shell
wget https://raw.githubusercontent.com/EliasSchramm/Bungeecord-Auto-Multiserver/main/scripts/setupMinecraftServer.sh && sudo chmod +x setupMinecraftServer.sh && sudo bash ./setupMinecraftServer.sh 
```

Enter the requested information. <br>
![cmd](https://i.imgur.com/qcWZo0O.png) <br>

Hit enter again, and the script will start installing everything.
After it finished you will be sent to the screen-instance it is running in. It might restart a few times in order to
finish the configuration. Once that's done you have a fully operational game server. If you have entered your data 
correctly you should be able to see something like ```[Auto Bungee] Connected XDMm2 10.0.0.3``` in your proxy console.
<br>

Repeat this progress for every gameserver you want to set up. 

Congrats, you're done! Try connecting to your proxy server via Minecraft.
----

### 3.2 Manual

#### The bungee proxy

Install the bungeecord server after this guide: [https://www.spigotmc.org/wiki/bungeecord-installation/](https://www.spigotmc.org/wiki/bungeecord-installation/)
<br>
Start the server once. Stop it.

Download the latest build of BungeecordAutoConfig-Bungee [here](https://ci.eps-dev.de/job/BungeecordAutoConfig-Bungee/lastSuccessfulBuild/artifact/Bungee/target/Bungee.jar)
and place it in the ````plugins/```` folder. 
<br>

Start the server once. Stop it.
<br>

Fill the fields in the now generated ```plugins/Bungee/config.yml``` with your information.
<br>

Start the server. It may restart/stop a few times in order to finish the installation.
<br>

#### The game server

Install the spigot server after this guide: [https://www.spigotmc.org/wiki/buildtools/](https://www.spigotmc.org/wiki/buildtools/)
<br>

Start the server once. Stop it.
<br>

Download the latest build of BungeecordAutoConfig-Spigot [here](https://ci.eps-dev.de/job/BungeecordAutoConfig-Spigot/lastSuccessfulBuild/artifact/Spigot/target/BungeecordAutoConfig.jar)
and place it in the ````plugins/```` folder.
<br>

Fill the fields in the now generated ```plugins/BungeecordAutoConfig/config.yml``` with your information.
<br>

Start the server. It may restart/stop a few times in order to finish the installation.
<br>

# Updating
Automatic if installed via script. (At restart.)

<br>

Just replace the .jar with the newest build from: [https://ci.eps-dev.de](https://ci.eps-dev.de)
<br>
[Bungeecord](https://ci.eps-dev.de/job/BungeecordAutoConfig-Bungee/lastSuccessfulBuild/artifact/Bungee/target/Bungee.jar) <br>
[Spigot](https://ci.eps-dev.de/job/BungeecordAutoConfig-Spigot/lastSuccessfulBuild/artifact/Spigot/target/BungeecordAutoConfig.jar)



# 5. Common problems

- Can't connect
    - All servers need to have port 10101 (for the proxy) and the port they are running at exposed to the internet.
- My proxy doesn't respond anymore
    - Restart it. ````Control + C```` is the way to do that.

# 6. Contributing

Report issues at [https://github.com/EliasSchramm/Bungeecord-Auto-Multiserver/issues/](https://github.com/EliasSchramm/Bungeecord-Auto-Multiserver/issues/)
