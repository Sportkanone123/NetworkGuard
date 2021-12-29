package de.sportkanone123.networkguard.spigot.manager;

import de.sportkanone123.networkguard.spigot.data.PlayerData;
import de.sportkanone123.networkguard.spigot.module.PacketModule;
import de.sportkanone123.networkguard.spigot.module.TickModule;
import de.sportkanone123.networkguard.spigot.module.impl.antiexploit.*;
import de.sportkanone123.networkguard.spigot.module.impl.serveroptimizer.PerformanceModule;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Constructor<?>> CONSTRUCTORS = new ArrayList<>();

    public final Class<?>[] MODULES_PACKET = new Class[] {
            BookModule.class,
            CommandModule.class,
            CustomPayloadModule.class,
            ItemModule.class,
            SignModule.class
    };

    public final Class<?>[] MODULES_TICK = new Class[] {
            PerformanceModule.class
    };

    public List<PacketModule> loadPacketChecks(final PlayerData data) {
        final List<PacketModule> checkList = new ArrayList<>();
        for (Constructor<?> constructor : CONSTRUCTORS) {
            try {
                checkList.add((PacketModule) constructor.newInstance(data));
                System.out.println("Loaded checks for " + data.getPlayer().getName());
            } catch (Exception exception) {
                System.out.println("Failed to load checks for " + data.getPlayer().getName());
                exception.printStackTrace();
            }
        }
        return checkList;
    }

    public List<TickModule> loadTickChecks(final PlayerData data) {
        final List<TickModule> checkList = new ArrayList<>();
        for (Constructor<?> constructor : CONSTRUCTORS) {
            try {
                checkList.add((TickModule) constructor.newInstance(data));
                System.out.println("Loaded checks for " + data.getPlayer().getName());
            } catch (Exception exception) {
                System.out.println("Failed to load checks for " + data.getPlayer().getName());
                exception.printStackTrace();
            }
        }
        return checkList;
    }

    public void setup() {
        for (Class<?> clazz : MODULES_PACKET) {
            if (ConfigManager.getConfig("config").getBoolean("modules." + "antiexploit" + "." + clazz.getSimpleName().toLowerCase().replace("module", "") + ".isEnabled")) {
                try {
                    CONSTRUCTORS.add(clazz.getConstructor(PlayerData.class));
                    Bukkit.getLogger().info(clazz.getSimpleName() + " is enabled!");
                } catch (NoSuchMethodException exception) {
                    exception.printStackTrace();
                }
            } else {
                Bukkit.getLogger().info(clazz.getSimpleName() + " is disabled!");
            }
        }

        for (Class<?> clazz : MODULES_TICK) {
            if (ConfigManager.getConfig("config").getBoolean("modules." + "antiexploit" + "." + clazz.getSimpleName().toLowerCase().replace("module", "") + ".isEnabled")) {
                try {
                    CONSTRUCTORS.add(clazz.getConstructor(PlayerData.class));
                    Bukkit.getLogger().info(clazz.getSimpleName() + " is enabled!");
                } catch (NoSuchMethodException exception) {
                    exception.printStackTrace();
                }
            } else {
                Bukkit.getLogger().info(clazz.getSimpleName() + " is disabled!");
            }
        }
    }
}
