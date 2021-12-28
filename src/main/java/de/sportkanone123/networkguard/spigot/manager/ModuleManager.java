package de.sportkanone123.networkguard.spigot.manager;

import de.sportkanone123.networkguard.spigot.data.PlayerData;
import de.sportkanone123.networkguard.spigot.module.Module;
import de.sportkanone123.networkguard.spigot.module.impl.antiexploit.*;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleManager {
    private static final List<Constructor<?>> CONSTRUCTORS = new ArrayList<>();

    public static final Class<?>[] MODULES = new Class[] {
            BookModule.class,
            CommandModule.class,
            CustomPayloadModule.class,
            ItemModule.class,
            SignModule.class
    };

    public static List<Module> loadChecks(final PlayerData data) {
        final List<Module> checkList = new ArrayList<>();
        for (Constructor<?> constructor : CONSTRUCTORS) {
            try {
                checkList.add((Module) constructor.newInstance(data));
                System.out.println("Loaded checks for " + data.getPlayer().getName());
            } catch (Exception exception) {
                System.out.println("Failed to load checks for " + data.getPlayer().getName());
                exception.printStackTrace();
            }
        }
        return checkList;
    }

    public static void setup() {
        for (Class<?> clazz : MODULES) {
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
