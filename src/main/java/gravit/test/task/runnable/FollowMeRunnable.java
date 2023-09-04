package gravit.test.task.runnable;

import gravit.test.task.GravitTestTask;
import java.util.List;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class FollowMeRunnable extends BukkitRunnable {
  private final Player leader;
  private final List<String> playerList;


  public void run() {
    if (!leader.isOnline()) cancel();
    if (playerList.size() == 0) cancel();
    int count = 0;
    for (String name: playerList) {
      Player p = Bukkit.getPlayer(name);
      if (p == null) {
        playerList.remove(name);
        continue;
      }
      if (!p.isOnline()) {
        playerList.remove(name);
        for (String effect : GravitTestTask.getSettings().getFollowMeeffectsList()) {
          p.removePotionEffect(PotionEffectType.getByName(effect));
        }
        continue;
      }
      if (!p.getWorld().getName().equalsIgnoreCase(leader.getWorld().getName())) {
        p.teleport(leader);
        continue;
      }
      double distance = leader.getLocation().distanceSquared(p.getLocation());
      if (distance > 50) {
        double damage = Math.ceil((distance - 50) / GravitTestTask.getSettings().getFollowMeRadius());
        p.damage(damage * GravitTestTask.getSettings().getFollowMeDamage());
        p.sendMessage(GravitTestTask.getSettings().getMessage("damageradius"));
        for (String effect : GravitTestTask.getSettings().getFollowMeeffectsList()) {
          p.removePotionEffect(PotionEffectType.getByName(effect));
        }
      } else {
        count++;
        int amplifier = (int) Math.floor(distance / GravitTestTask.getSettings().getFollowMeAmplifierRadius());
        for (String effect : GravitTestTask.getSettings().getFollowMeeffectsList()) {
          p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), 2 * 20, amplifier));
        }
      }
    }
    for (String effect : GravitTestTask.getSettings().getFollowMeeffectsList()) {
      leader.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), 2 * 20, Math.min(count / 2, 3)));
    }
  }
}
