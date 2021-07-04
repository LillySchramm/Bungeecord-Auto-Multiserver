package de.epsdev.plugins.bungee.commands;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.PlayerManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class c_Instance extends Command {
    public c_Instance() {
        super("instance");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer)sender;
            p.sendMessage(new ComponentBuilder("You are currently connected to " + PlayerManager.players.get(p.getDisplayName())).color(ChatColor.YELLOW).create());
        }
    }
}
