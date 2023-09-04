package gravit.test.task;

import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class Settings {
  private final boolean getMyHeadeffects;
  private final List<String> getMyHeadeffectsList;
  private final int getMyHeadDamage;
  private final int getMyHeadCooldown;

  private final boolean glowing;

  private final boolean followMeeffects;
  private final List<String> followMeeffectsList;
  private final int followMeRadius;
  private final int followMedamageRadius;
  private final int followMeDamage;
  private final int followMeAmplifierRadius;

  private final int followMeCooldown;

  public final HashMap<String, String> messages = new HashMap<>();

  public Settings(FileConfiguration configuration) {
    getMyHeadeffects = configuration.getBoolean("getmyhead.effects.enabled");
    getMyHeadeffectsList = configuration.getStringList("getmyhead.effects.list");
    getMyHeadDamage = configuration.getInt("followme.damage");
    getMyHeadCooldown = configuration.getInt("getmyhead.cooldown");

    glowing = configuration.getBoolean("followme.glowing");
    followMeeffects = configuration.getBoolean("followme.effects.enabled");
    followMeeffectsList = configuration.getStringList("followme.effects.list");
    followMeRadius = configuration.getInt("followme.radius");
    followMedamageRadius = configuration.getInt("followme.damage-radius");
    followMeDamage = configuration.getInt("followme.damage");
    followMeAmplifierRadius = configuration.getInt("followme.effects.amplifier-radius");
    followMeCooldown = configuration.getInt("followme.cooldown");

    messages.clear();
    for (String key : configuration.getConfigurationSection("messages").getKeys(false))
      messages.put(key, configuration.getString("messages." + key));
  }
  public String getMessage(String key) {
    return GravitTestTask.getPrefix() + ChatColor.translateAlternateColorCodes('&', messages.get(key));
  }
}
