package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.enums.OperationType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class spigottest extends JavaPlugin {

    @Override
    public void onEnable() {
        EPS_API eps_api = new EPS_API(OperationType.CLIENT);
        eps_api.setRemoteAddress("raspberrypi");
        eps_api.setPort(Bukkit.getPort());

        eps_api.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
