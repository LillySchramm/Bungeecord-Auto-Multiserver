import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.enums.OperationType;
import de.epsdev.bungeeautoserver.api.sync.SyncChannel;
import de.epsdev.bungeeautoserver.api.sync.SyncInventory;
import de.epsdev.bungeeautoserver.api.sync.SyncItem;
import de.epsdev.bungeeautoserver.api.tools.PlayerManagement;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String args[]){
        SyncItem syncItem = new SyncItem("I am a name", "ROFL");
        syncItem.setFlags(new ArrayList<String>(Arrays.asList(new String[]{"s", "sssss"})));
        syncItem.enchantments.put("uwu", 1);

        SyncInventory syncInventory = new SyncInventory("123-456-789");

        syncInventory.setItem(10, syncItem);
        syncInventory.setItem(11, syncItem);
        syncInventory.setItem(12, syncItem);

        SyncChannel syncChannel = new SyncChannel("survival");
        syncChannel.setInventory(syncInventory);
    }
}
