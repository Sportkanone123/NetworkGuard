package de.sportkanone123.networkguard.spigot.module;

import de.sportkanone123.networkguard.spigot.NetworkGuard;
import de.sportkanone123.networkguard.spigot.data.PlayerData;
import de.sportkanone123.networkguard.spigot.manager.AlertsManager;
import de.sportkanone123.networkguard.spigot.manager.ConfigManager;
import de.sportkanone123.networkguard.spigot.packet.LoginPacket;
import de.sportkanone123.networkguard.spigot.packet.PlayPacket;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.Locale;


@Getter
@Setter
public abstract class TickModule {
    public final PlayerData data;

    private final String name;
    private final String description;
    private final boolean enabled;

    private int vl;
    private ModuleType checkType;

    public TickModule(final PlayerData data) {
        this.data = data;

        this.checkType = ModuleType.fromPackageName(this.getClass().getPackage().getName());

        this.name = this.getCheckInfo().name();
        this.description = this.getCheckInfo().description();

        String simpleName = this.getCheckInfo().name().toLowerCase(Locale.ROOT).replace("module", "");
        this.enabled = ConfigManager.getConfig("config").getBoolean("modules." + checkType.name.toLowerCase() + "." + simpleName + ".isEnabled");
    }

    public abstract void handleTick();

    public ModuleInfo getCheckInfo() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            return this.getClass().getAnnotation(ModuleInfo.class);
        }
        return null;
    }

    public enum ModuleType {
        ANTI_ALT("AntiAlt"),
        Anti_BOT_ATTACK("AntiBotAttack"),
        ANTI_EXPLOIT("AntiExploit"),
        SERVER_OPTIMIZER("ServerOptimizer");

        private final String name;

        ModuleType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static ModuleType fromPackageName(String packageName) {
            for (ModuleType checkType : ModuleType.values()) {
                if (packageName.contains(checkType.getName().toLowerCase())) {
                    return checkType;
                }
            }
            return null;
        }
    }
}
