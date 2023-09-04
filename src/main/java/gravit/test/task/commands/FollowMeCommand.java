package gravit.test.task.commands;

import gravit.test.task.GravitTestTask;
import gravit.test.task.runnable.FollowMeRunnable;
import gravit.test.task.utility.CommonUtil;
import gravit.test.task.utility.CooldownUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FollowMeCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s,
      String[] strings) {
    if (commandSender instanceof ConsoleCommandSender) return true;
    Player player = (Player) commandSender;
    if (CooldownUtil.hasCooldown(player.getName() + "_followme")) {
      player.sendMessage(GravitTestTask.getSettings().getMessage("cooldown"));
      return true;
    }
    CooldownUtil.putCooldown(player.getName() + "_followme", GravitTestTask.getSettings().getFollowMeCooldown() * 1000L);
    List<Player> playerList = new ArrayList<>();
    Bukkit.getOnlinePlayers().stream().filter(pall -> !pall.equals(player) && player.getLocation().distanceSquared(pall.getLocation()) <= 50).forEach(pall -> {
      playerList.add(pall);
      pall.sendMessage(GravitTestTask.getSettings().getMessage("followme").replace("%playername%", player.getName()));
    });
    if (playerList.size() == 0) {
      player.sendMessage(GravitTestTask.getSettings().getMessage("notenoughplayers"));
      return true;
    }

    if (CommonUtil.getVERSION() >= 9) CommonUtil.sendGlowing(player, playerList);
    FollowMeRunnable followMeRunnable = new FollowMeRunnable(player, playerList);
    followMeRunnable.runTaskTimer(GravitTestTask.getPlugin(GravitTestTask.class), 20, 20);
    GravitTestTask.getFollowMeRunnables().add(followMeRunnable);
    return true;
  }
}
