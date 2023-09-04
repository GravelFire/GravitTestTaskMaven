package gravit.test.task.utility;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import gravit.test.task.protocollib.WrapperPlayServerEntityMetadata;
import java.util.List;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@UtilityClass
public class CommonUtil {
  @Getter
  private final int VERSION = Integer.parseInt((Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]).split("_")[1]);

  public void giveHead(Player player) {
    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

    SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

    if(VERSION <= 8) skullMeta.setOwner(player.getName());
    else skullMeta.setOwningPlayer(player);

    head.setItemMeta(skullMeta);

    player.getInventory().addItem(head);
  }

  public void sendGlowing(Player leader, List<Player> receivers) {
    WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata();
    WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(leader);
    watcher.setObject(0, WrappedDataWatcher.Registry.get(Byte.class), (byte) 0x40);

    wrapper.setMetadata(watcher.getWatchableObjects());
    wrapper.setEntityID(leader.getEntityId());

    receivers.forEach(wrapper::sendPacket);
  }

}
