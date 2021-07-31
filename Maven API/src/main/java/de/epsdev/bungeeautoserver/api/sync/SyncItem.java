package de.epsdev.bungeeautoserver.api.sync;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class SyncItem {
    public String name;
    public String type;
    public int health = 0;

    public ArrayList<String> flags = new ArrayList<>();
    public HashMap<String, Integer> enchantments = new HashMap<>();

    private final Type FLAGS_TYPE = new TypeToken<ArrayList<String>>(){}.getType();
    private final Type OBJECT_ARRAY = new TypeToken<JSONArray>(){}.getType();

    public SyncItem(String name, String type){
        this.name = name;
        this.type = type;
    }

    public SyncItem(JSONObject json){
        this.name = json.getString("name");
        this.type = json.getString("type");
        this.health = json.getInt("health");

        this.flags = new Gson().fromJson(json.getJSONArray("flags").toString(), FLAGS_TYPE);

        JSONArray jsonArray = json.getJSONArray("enchantments");
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject o = jsonArray.getJSONObject(i);
            this.enchantments.put(o.getString("name"), o.getInt("level"));
        }
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", name);
        jsonObject.put("type", type);
        jsonObject.put("health", health);

        ArrayList<JSONObject> objs = new ArrayList<>();
        enchantments.forEach((k,v) -> {
            JSONObject o = new JSONObject();

            o.put("name", k);
            o.put("level", v);

            objs.add(o);
        });

        jsonObject.put("enchantments",objs);
        jsonObject.put("flags", flags);

        return jsonObject;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void setEnchantments(HashMap<String, Integer> enchantments){
        this.enchantments = enchantments;
    }

    public void setFlags(ArrayList<String> flags){
        this.flags = flags;
    }

}
