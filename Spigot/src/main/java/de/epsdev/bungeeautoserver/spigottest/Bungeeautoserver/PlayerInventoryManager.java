package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.sync.SyncInventory;
import de.epsdev.bungeeautoserver.api.sync.SyncItem;
import de.epsdev.bungeeautoserver.api.tools.SyncInventoryManagement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerInventoryManager {

    /*

    NOTES

    The items are structured in the following way:

    (Slot)      (... translates to)
    0           Head
    1           Body
    2           Leggings
    3           Boots
    4           Offhand

    5 - 40      Player Inventory    (-5 offset)

    41 - 67     Player Enderchest   (-41 offset)

    */

    public static void setPlayerInventory(SyncInventory syncInventory){
        Player player = Bukkit.getPlayer(UUID.fromString(syncInventory.uuid));

        player.sendMessage(EPS_API.PREFIX +
                ChatColor.GREEN + "" + ChatColor.BOLD + "Syncing inventory....");

        AtomicReference<PlayerInventory> inventory = new AtomicReference<>(player.getInventory());

        syncInventory.items.forEach((k, v) -> {
            inventory.set(setItem(genItem(v), k, inventory.get()));
        });

        player.sendMessage(EPS_API.PREFIX +
                ChatColor.GREEN + "" + ChatColor.BOLD + "Done!");
    }

    // Places an item inside of an inventory according to the schema at the top of this file.
    private static PlayerInventory setItem(ItemStack itemStack, int pos, PlayerInventory inventory) {

        switch (pos){
            case 0:
                inventory.setHelmet(itemStack);
                return inventory;
            case 1:
                inventory.setChestplate(itemStack);
                return inventory;
            case 2:
                inventory.setLeggings(itemStack);
                return inventory;
            case 3:
                inventory.setBoots(itemStack);
                return inventory;
            case 4:
                inventory.setItemInOffHand(itemStack);
                return inventory;
        }

        if (pos < 41){
            pos = pos - 5; // remove offset

            inventory.setItem(pos, itemStack);
        }else {
            // WIP
        }

        return inventory;
    }

    public static SyncInventory getPlayerSyncInventory(Player player){
        if(SyncInventoryManagement.ChannelName.equals("")) return null;

        PlayerInventory playerInventory = player.getInventory();
        SyncInventory syncInventory = generateSyncInventory(playerInventory);

        return syncInventory;
    }

    // WIP
    private static SyncInventory generateSyncInventory(PlayerInventory playerInventory){
        SyncInventory syncInventory = new SyncInventory(playerInventory.getHolder().getUniqueId().toString());

        for (int i = 0; i < 4 * 9; i++){
            ItemStack itemStack = playerInventory.getItem(i);

            if(itemStack != null){
                ItemMeta itemMeta = itemStack.getItemMeta();
                SyncItem syncItem = new SyncItem(itemMeta.getDisplayName(), itemStack.getType().toString());

                // TODO: Enchantments

                ArrayList<String> flags = new ArrayList<>();
                for (ItemFlag itemFlag : itemMeta.getItemFlags()) flags.add(itemFlag.name());
                syncItem.setFlags(flags);

                syncInventory.setItem(4 + i,syncItem);
            }
        }

        return syncInventory;
    }

    // Generates an ItemStack from an SyncItem
    // WIP
    private static ItemStack genItem(SyncItem item){
        ItemStack itemStack = new ItemStack(Material.valueOf(item.type));

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(item.name);

        for (String flag : item.flags){
            ItemFlag itemFlag = ItemFlag.valueOf(flag);
            itemMeta.addItemFlags(itemFlag);
        }

        for(String name : item.enchantments.keySet()){
            int level = item.enchantments.get(name);
            itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(name)), level, false);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
