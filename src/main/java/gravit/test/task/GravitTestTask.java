package gravit.test.task;

import gravit.test.task.commands.FollowMeCommand;
import gravit.test.task.commands.GetMyHeadCommand;
import gravit.test.task.commands.SwapCommand;
import gravit.test.task.runnable.FollowMeRunnable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GravitTestTask extends JavaPlugin {
    @Getter
    private static String prefix;
    @Getter
    private static Settings settings;

    @Getter
    private static final List<FollowMeRunnable> followMeRunnables = new ArrayList<>();

    public void onEnable() {
        saveDefaultConfig();
        settings = new Settings(getConfig());
        prefix = "§e" + GravitTestTask.getPlugin(GravitTestTask.class).getDescription().getName();
        getCommand("followme").setExecutor(new FollowMeCommand());
        getCommand("getmyhead").setExecutor(new GetMyHeadCommand());
        getCommand("swap").setExecutor(new SwapCommand());
    }
    public void onDisable() {
        followMeRunnables.forEach(BukkitRunnable::cancel);
    }
}