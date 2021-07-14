package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.ServerInfo;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.bungeeautoserver.api.packages.RequestServerStatusPackage;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.GUI.Item_Config;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.config.GUI_Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.ArrayList;
import java.util.List;

public class SignManager {
    public static ArrayList<String> signs = new ArrayList<>();

    public static boolean isSign(Block block){
        return block.getState() instanceof Sign;
    }

    public static boolean isBungeeSign(Sign sign){
        return sign.getLine(0).contains("§a") && (sign.getLine(3).contains(">Click To Join<") || sign.getLine(3).contains(">FULL<"));
    }

    public static String getTargetServer(Sign sign){
        return sign.getLine(0).replace(" ","").replace("§a", "");
    }

    public static Sign getBungeeSign(String s){
        int x = Integer.parseInt(s.split(">")[0]);
        int y = Integer.parseInt(s.split(">")[1]);
        int z = Integer.parseInt(s.split(">")[2]);

        Block block = Bukkit.getWorld("world").getBlockAt(x,y,z);

        if(block == null) return null;

        if(isSign(block)){
            Sign sign = (Sign) block.getState();
            if(isBungeeSign(sign)){
                return sign;
            }
        }

        return null;
    }

    public static void loadAllSigns(){
        List<String> _signs = BungeecordAutoConfig.config.getStringList("signs");

        for(String s : _signs){
            if(getBungeeSign(s) != null){
                signs.add(s);
            }
        }

        BungeecordAutoConfig.config.set("signs", signs.toArray(new String[0]));
        BungeecordAutoConfig.plugin.saveConfig();
    }

    private static String constructPosString(Sign sign){
        return sign.getX() + ">" + sign.getY() + ">" + sign.getZ();
    }

    public static void addSign(Sign sign){
        if(!signs.contains(constructPosString(sign))){
            signs.add(constructPosString(sign));

            BungeecordAutoConfig.config.set("signs", signs.toArray(new String[0]));
            BungeecordAutoConfig.plugin.saveConfig();
        }
    }

    public static void startSignUpdateScheduler(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(BungeecordAutoConfig.plugin, () -> {

            List<String> searching = new ArrayList<>();

            for (String s_sign : signs){
                Sign sign = getBungeeSign(s_sign);
                if(sign != null){
                    String target = getTargetServer(sign);
                    if(!searching.contains(target)){
                        BungeecordAutoConfig.eps_api.connection.send(new RequestServerStatusPackage(target));
                    }

                    int[] totals = ServerManager.calcTotals(EPS_API.serverInfo.getOrDefault(target,
                            new ArrayList<ServerInfo>()));

                    ChatColor chatColor = ChatColor.GREEN;
                    String bottomText = ">Click To Join<";
                    if(totals[0] == totals[1]){
                        chatColor = ChatColor.DARK_RED;
                        bottomText = ">FULL<";
                    }

                    sign.setLine(1, chatColor + center(totals[1] + "/" + totals[0]));
                    sign.setLine(3, chatColor + center(bottomText));
                    sign.update(true);
                }
            }

            // For the menu

            for (Item_Config item_config : GUI_Config.items_map.values()){
                if(!searching.contains(item_config.target)){
                    BungeecordAutoConfig.eps_api.connection.send(new RequestServerStatusPackage(item_config.target));
                    searching.add(item_config.target);
                }
            }

        }, 60L, 20L * 2);
    }

    public static String center(String org){
        String placeholder = "";;
        for (int i = 0; i < ((14 - org.length())/4) - 1; i++) {
            placeholder += " ";
        }
        return placeholder + org;
    }
}
