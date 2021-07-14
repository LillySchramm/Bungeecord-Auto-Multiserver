package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.GUI;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.ServerManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Item_Config {
    public final int x;
    public final int y;
    public final String material_name;
    public final String name;
    public final String lore;
    public final String target;

    private static final int MAX_CHARS_PER_LINE = 26;

    public Item_Config(int x, int y, String material_name, String name, String lore, String target) {
        this.x = x;
        this.y = y;
        this.material_name = material_name;
        this.name = name;
        this.lore = lore;
        this.target = target;
    }

    public ItemStack genItemStack(){
        Material material = Material.matchMaterial(material_name);
        ItemStack itemStack = new ItemStack(material);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(name);

        List<String> sLore = toItemDescription(lore);
        itemMeta.setLore(sLore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private List<String> toItemDescription(String s) {

        int[] totals = ServerManager.calcTotals(EPS_API.serverInfo.getOrDefault(this.target, new ArrayList<>()));

        s = s.replace("%s", totals[1] + "/" + totals[0]);

        List<String> description = new ArrayList<>();
        description.add("");

        String[] words = s.split(" ");
        int cur_chars = 0;
        int cur_line = 0;
        for (String word : words) {
            if(cur_chars + word.length() > MAX_CHARS_PER_LINE){
                cur_chars = word.length();
                cur_line ++;
                description.add(word + " ");
            }else {
                cur_chars += word.length();
                description.set(cur_line, description.get(cur_line) + word + " ");
            }
        }

        return description;
    }
}
