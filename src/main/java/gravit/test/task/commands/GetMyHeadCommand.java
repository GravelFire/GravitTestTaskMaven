package gravit.test.task.commands;

import gravit.test.task.GravitTestTask;
import gravit.test.task.utility.CommonUtil;
import gravit.test.task.utility.CooldownUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GetMyHeadCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s,
      String[] strings) {
    if (commandSender instanceof ConsoleCommandSender) return true;
    Player player = (Player) commandSender;
    if (CooldownUtil.hasCooldown(player.getName() + "_getmyhead")) {
      player.sendMessage(GravitTestTask.getSettings().getMessage("cooldown"));
      return true;
    }
    CooldownUtil.putCooldown(player.getName() + "_getmyhead", GravitTestTask.getSettings().getGetMyHeadCooldown() * 1000L);
    CommonUtil.giveHead(player);
    player.damage(GravitTestTask.getSettings().getGetMyHeadDamage());
    if (GravitTestTask.getSettings().isGetMyHeadEffects()) {
      for (String effect : GravitTestTask.getSettings().getGetMyHeadEffectsList()) {
        String[] effectSplit = effect.split(":");
        PotionEffectType potionEffectType = PotionEffectType.getByName(effectSplit[0]);
        if (potionEffectType == null)
          continue;
        player.addPotionEffect(
            new PotionEffect(potionEffectType, Integer.parseInt(effectSplit[1]) * 20,
                Integer.parseInt(effectSplit[2])));
      }
    }
    return false;
  }
}
