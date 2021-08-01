package de.epsdev.bungeeautoserver.api.tools;

import de.epsdev.bungeeautoserver.api.interfaces.SyncInventoryEventEmitter;
import de.epsdev.bungeeautoserver.api.sync.SyncChannel;
import de.epsdev.bungeeautoserver.api.sync.SyncInventory;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SyncInventoryManagement {
    public static String ChannelName = ""; // Just for ClientServer
    public static SyncInventoryEventEmitter emitter;

    private static HashMap<String, SyncChannel> CHANNELS = new HashMap<>();

    public static SyncInventory getInventory(String channelName, String player_uuid){
        if (CHANNELS.containsKey(channelName)){
            SyncChannel channel = CHANNELS.get(channelName);
            return channel.getInventory(player_uuid);
        }else {
            SyncChannel syncChannel = new SyncChannel(channelName);
            SyncInventory inventory = syncChannel.getInventory(player_uuid);

            CHANNELS.put(channelName, syncChannel);
            emitter.save();

            return inventory;
        }
    }

    public static void setInventory(String channelName, SyncInventory inventory){
        // Just to make sure that the channel exists
        getInventory(channelName, inventory.uuid);

        CHANNELS.get(channelName).setInventory(inventory);
        emitter.save();
    }

    public static ArrayList<String> getJSON(){
        ArrayList<String> ret = new ArrayList<>();

        CHANNELS.forEach((ignored, v) -> {
            ret.add(v.toJSONObject().toString());
        });

        return ret;
    }

    public static void loadFromJSON(ArrayList<String> JSONStrings){
        for (String s : JSONStrings){
            JSONObject jsonObject = new JSONObject(s);

            SyncChannel channel = new SyncChannel(jsonObject);
            CHANNELS.put(channel.name, channel);
        }
    }
}
