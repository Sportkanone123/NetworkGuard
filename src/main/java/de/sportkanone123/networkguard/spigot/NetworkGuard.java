package de.sportkanone123.networkguard.spigot;

import de.sportkanone123.networkguard.spigot.listener.BukkitListener;
import de.sportkanone123.networkguard.spigot.listener.NetworkListener;
import de.sportkanone123.networkguard.spigot.manager.ConfigManager;
import de.sportkanone123.networkguard.spigot.manager.ModuleManager;
import de.sportkanone123.networkguard.spigot.manager.PlayerDataManager;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.settings.PacketEventsSettings;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class NetworkGuard extends JavaPlugin {
    public static Plugin plugin;
    private PacketEvents instance;

    @Getter
    public static PlayerDataManager playerDataManager = new PlayerDataManager();

    @Override
    public void onLoad() {
        instance = PacketEvents.create(this);
        PacketEventsSettings settings = instance.getSettings();
        settings.checkForUpdates(false)
                .fallbackServerVersion(ServerVersion.v_1_17_1)
                .compatInjector(false)
                .bStats(false);
        instance.load();
    }

    @Override
    public void onEnable() {
        plugin = this;

        instance.registerListener(new NetworkListener());
        instance.init();

        Bukkit.getPluginManager().registerEvents(new BukkitListener(), this);

        Bukkit.getOnlinePlayers().forEach(player -> this.getPlayerDataManager().add(player));

        saveDefaultConfig();

        ModuleManager.setup();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> this.getPlayerDataManager().remove(player));

        instance.terminate();
    }

}
