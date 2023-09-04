package gravit.test.task.commands;

import gravit.test.task.GravitTestTask;
import gravit.test.task.utility.CooldownUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 * Воспользуюсь правом, что не было указано всё выносить в конфиг
 */
public class SwapCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s,
      String[] strings) {
    List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
    if (playerList.size() < 2) {
      commandSender.sendMessage(GravitTestTask.getPrefix() + " §8| §7Недостаточно игроков на сервере");
      return true;
    }
    if (CooldownUtil.hasCooldown("swap")) {
      commandSender.sendMessage(GravitTestTask.getPrefix() + " §8| §7Кто-то до вас воспользовался правом перемещения. Придётся подождать 5 секунд");
      return true;
    }
    CooldownUtil.putCooldown("swap", 5000L);

    if (strings.length == 0) {
      swapPlayers(playerList, -1);
      return true;
    }
    String regex = "^[0-9]*";
    if (!Pattern.matches(regex, strings[0])) {
      commandSender.sendMessage(GravitTestTask.getPrefix() + " §8| §7Не является числом");
      return true;
    }
    swapPlayers(playerList, Integer.parseInt(strings[0]));
    return true;
  }
  public void swapPlayers(List<Player> playerList, int count) {
    int i = 0;
    for (Player p : playerList) {
      if (count > 0 && i >= count) break;
      playerList.remove(p);
      Player target  = playerList.get(new Random().nextInt(playerList.size()));
      Location location = p.getLocation();
      p.teleport(target.getLocation());
      target.teleport(location);
      p.sendMessage(GravitTestTask.getPrefix() + " §8| §7Вы поменялись местами с §e" + target.getName());
      target.sendMessage(GravitTestTask.getPrefix() + " §8| §7Вы поменялись местами с §e" + p.getName());
      playerList.remove(target);
      i++;
    }
  }
}
