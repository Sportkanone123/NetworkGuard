package de.sportkanone123.networkguard.spigot.manager;

import de.sportkanone123.networkguard.spigot.NetworkGuard;
import de.sportkanone123.networkguard.spigot.data.PlayerData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public final class TickManager implements Runnable {

    @Getter
    private static BukkitTask task;

    public void start() {
        if(task == null) {
            task = Bukkit.getScheduler().runTaskTimerAsynchronously(NetworkGuard.plugin, this, 0L, 1L);
        }
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public void run() {
        for(PlayerData data : NetworkGuard.getPlayerDataManager().getAllData())
            data.getTickModules().forEach(module -> module.handleTick());
    }
}
