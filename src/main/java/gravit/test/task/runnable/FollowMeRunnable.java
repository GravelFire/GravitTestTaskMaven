package gravit.test.task.runnable;

import gravit.test.task.GravitTestTask;
import java.util.List;
import java.util.ListIterator;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class FollowMeRunnable extends BukkitRunnable {
  private final Player leader;
  private final List<Player> playerList;


  public void run() {
    if (!leader.isOnline()) cancel();
    if (playerList.size() == 0) cancel();
    int count = 0;
    ListIterator<Player> playerListIterator = playerList.listIterator();
    while (playerListIterator.hasNext()) {
        Player follower = playerListIterator.next();
        if (follower == null) {
          playerListIterator.remove();
          continue;
        }
        if (!follower.isOnline()) {
          if (GravitTestTask.getSettings().isFollowMeEffects()) {
            for (String effect : GravitTestTask.getSettings().getFollowMeEffectsList()) {
              follower.removePotionEffect(PotionEffectType.getByName(effect));
            }
          }
          playerListIterator.remove();
          continue;
        }

        if (!follower.getWorld().getName().equalsIgnoreCase(leader.getWorld().getName())) {
          follower.teleport(leader);
          continue;
        }
        double distance = leader.getLocation().distanceSquared(follower.getLocation());
        if (distance > 50) {
          double damage = Math.ceil((distance - 50) / GravitTestTask.getSettings().getFollowMeRadius());
          follower.damage(damage * GravitTestTask.getSettings().getFollowMeDamage());
          follower.sendMessage(GravitTestTask.getSettings().getMessage("damageradius"));
          if (GravitTestTask.getSettings().isFollowMeEffects()) {
            for (String effect : GravitTestTask.getSettings().getFollowMeEffectsList()) {
              follower.removePotionEffect(PotionEffectType.getByName(effect));
            }
          }
        } else {
          if (GravitTestTask.getSettings().isFollowMeEffects()) {
            count++;
            int amplifier = (int) Math.floor(
                distance / GravitTestTask.getSettings().getFollowMeAmplifierRadius());
            for (String effect : GravitTestTask.getSettings().getFollowMeEffectsList()) {
              follower.addPotionEffect(
                  new PotionEffect(PotionEffectType.getByName(effect), 2 * 20, amplifier));
            }
          }
        }
    }
    if (GravitTestTask.getSettings().isFollowMeEffects()) {
      for (String effect : GravitTestTask.getSettings().getFollowMeEffectsList()) {
        leader.addPotionEffect(
            new PotionEffect(PotionEffectType.getByName(effect), 2 * 20, Math.min(count / 2, 3)));
      }
    }
    }
}
