package de.epsdev.bungeeautoserver.api.sync;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncInventory {
    public HashMap<Integer, SyncItem> items = new HashMap<>();
    public final String uuid;

    public SyncInventory(JSONObject jsonObject){
        this.uuid = jsonObject.getString("uuid");

        JSONArray objs = jsonObject.getJSONArray("items");

        for (int i = 0; i < objs.length(); i++){
            JSONObject obj = objs.getJSONObject(i);
            items.put(obj.getInt("pos"), new SyncItem(obj.getJSONObject("data")));
        }
    }

    public SyncInventory(String uuid){
        this.uuid = uuid;
    }

    public void setItem(int pos, SyncItem syncItem){
        this.items.put(pos, syncItem);
    }

    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", this.uuid);

        List<JSONObject> items = new ArrayList<>();
        this.items.forEach((k, v) -> {
            JSONObject o = new JSONObject();

            o.put("pos", k);
            o.put("data", v.toJSONObject());

            items.add(o);
        });
        jsonObject.put("items", items);

        return jsonObject;
    }
}
