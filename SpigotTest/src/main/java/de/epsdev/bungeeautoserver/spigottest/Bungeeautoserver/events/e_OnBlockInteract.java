package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.events;

import de.epsdev.bungeeautoserver.api.packages.RequestChangePlayerServerPackage;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.SignManager;
import org.bukkit.block.Block;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class e_OnBlockInteract implements Listener {

    @EventHandler
    void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if(action.equals(Action.RIGHT_CLICK_BLOCK)){

            if(block.getState() != null){
                if(SignManager.isSign(block)){
                    Sign sign = (Sign)block.getState();

                    if(SignManager.isBungeeSign(sign)){
                        BungeecordAutoConfig.eps_api.connection.send(new RequestChangePlayerServerPackage(
                                SignManager.getTargetServer(sign),
                                player.getDisplayName(),
                                false
                        ));
                    }

                }
            }

        }
    }

}
