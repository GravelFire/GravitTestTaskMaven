package gravit.test.task;

import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class Settings {
  private final boolean getMyHeadEffects;
  private final List<String> getMyHeadEffectsList;
  private final int getMyHeadDamage;
  private final int getMyHeadCooldown;

  private final boolean glowing;

  private final boolean followMeEffects;
  private final List<String> followMeEffectsList;
  private final int followMeRadius;
  private final int followMeDamageRadius;
  private final int followMeDamage;
  private final int followMeAmplifierRadius;

  private final int followMeCooldown;

  public final HashMap<String, String> messages = new HashMap<>();

  public Settings(FileConfiguration configuration) {
    getMyHeadEffects = configuration.getBoolean("getmyhead.effects.enabled");
    getMyHeadEffectsList = configuration.getStringList("getmyhead.effects.list");
    getMyHeadDamage = configuration.getInt("followme.damage");
    getMyHeadCooldown = configuration.getInt("getmyhead.cooldown");

    glowing = configuration.getBoolean("followme.glowing");
    followMeEffects = configuration.getBoolean("followme.effects.enabled");
    followMeEffectsList = configuration.getStringList("followme.effects.list");
    followMeRadius = configuration.getInt("followme.radius");
    followMeDamageRadius = configuration.getInt("followme.damage-radius");
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
