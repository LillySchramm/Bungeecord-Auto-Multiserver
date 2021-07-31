package de.epsdev.bungeeautoserver.api.sync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SyncChannel {
    private HashMap<String, SyncInventory> inventories = new HashMap<>();
    public final String name;

    public SyncChannel(String name){
        this.name = name;
    }

    public SyncChannel(JSONObject jsonObject){
        this.name = jsonObject.getString("name");

        JSONArray objs = jsonObject.getJSONArray("items");

        for (int i = 0; i < objs.length(); i++){
            JSONObject obj = objs.getJSONObject(i);

            SyncInventory inventory = new SyncInventory(obj);
            setInventory(inventory);
        }
    }

    public void setInventory(SyncInventory inventory){
        this.inventories.put(inventory.uuid, inventory);
    }

    public SyncInventory getInventory(String uuid){
        SyncInventory inventory = inventories.getOrDefault(uuid, null);

        if(inventory == null){
            inventory = new SyncInventory(uuid);
            setInventory(inventory);
        }

        return inventory;
    }

    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", this.name);

        List<JSONObject> items = new ArrayList<>();
        this.inventories.forEach((k, v) -> {
            items.add(v.toJSONObject());
        });
        jsonObject.put("items", items);

        return jsonObject;
    }

}
