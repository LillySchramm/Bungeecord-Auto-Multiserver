package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.config.Config;
import de.epsdev.bungeeautoserver.api.enums.OperationType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BungeecordAutoConfig extends JavaPlugin {
    public static EPS_API eps_api;
    @Override
    public void onEnable() {
        if(Config.isBungeeReady()){
            eps_api = new EPS_API(OperationType.CLIENT);
            eps_api.setRemoteAddress("raspberrypi");
            eps_api.setPort(Bukkit.getPort());

            eps_api.init();
        }else {
            Bukkit.shutdown();
        }
    }

    @Override
    public void onDisable() {
        eps_api.disable();
    }
}
