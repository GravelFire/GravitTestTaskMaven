package gravit.test.task;

import gravit.test.task.commands.followme;
import gravit.test.task.commands.getmyhead;
import gravit.test.task.commands.swap;
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
        getCommand("followme").setExecutor(new followme());
        getCommand("getmyhead").setExecutor(new getmyhead());
        getCommand("swap").setExecutor(new swap());
    }
    public void onDisable() {
        followMeRunnables.forEach(BukkitRunnable::cancel);
    }
}