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
public abstract class Module {
    public final PlayerData data;

    private final String name;
    private final String description;
    private final boolean enabled;
    private final int maxVl;
    private final String punishCommand;

    private int vl;
    private CheckType checkType;

    public Module(final PlayerData data) {
        this.data = data;

        this.checkType = CheckType.fromPackageName(this.getClass().getPackage().getName());

        this.name = this.getCheckInfo().name();
        this.description = this.getCheckInfo().description();

        String simpleName = this.getCheckInfo().name().toLowerCase(Locale.ROOT).replace("module", "");
        this.enabled = ConfigManager.getConfig("config").getBoolean("modules." + checkType.name.toLowerCase() + "." + simpleName + ".isEnabled");
        this.maxVl = ConfigManager.getConfig("config").getInt("modules." + checkType.name.toLowerCase() + "." + simpleName + ".maxVL");
        this.punishCommand = ConfigManager.getConfig("config").getString("modules." + checkType.name.toLowerCase() + "." + simpleName + ".punishCommand");
    }

    public abstract void handle(final PlayPacket packet);

    public abstract void handle(final LoginPacket packet);

    public void fail(final String info) {
        vl++;

        AlertsManager.handleDetection(data.getPlayer(), info);

        if (vl > maxVl && !isExempt()) {
            Bukkit.getScheduler().runTask(NetworkGuard.plugin, new Runnable() {
                @Override
                public void run() {
                    if (data.getPlayer() != null && data.getPlayer().isOnline()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), punishCommand.replace("%player_name%", data.getPlayer().getName())
                                .replace("%player_uuid%", data.getPlayer().getUniqueId().toString())
                                .replace("%module_name%", name)
                        );
                    }
                }
            });
        }
    }

    protected boolean isExempt() {
        return data.getPlayer().hasPermission(ConfigManager.getConfig("config").getString("permission.bypass"));
    }

    public ModuleInfo getCheckInfo() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            return this.getClass().getAnnotation(ModuleInfo.class);
        }
        return null;
    }

    public enum CheckType {
        ANTI_ALT("AntiAlt"),
        Anti_BOT_ATTACK("AntiBotAttack"),
        ANTI_EXPLOIT("AntiExploit"),
        SERVER_OPTIMIZER("ServerOptimizer");

        private final String name;

        CheckType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static CheckType fromPackageName(String packageName) {
            for (CheckType checkType : CheckType.values()) {
                if (packageName.contains(checkType.getName().toLowerCase())) {
                    return checkType;
                }
            }
            return null;
        }
    }
}
