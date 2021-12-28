package de.sportkanone123.networkguard.spigot.listener;

import de.sportkanone123.networkguard.spigot.NetworkGuard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        NetworkGuard.getPlayerDataManager().add(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        NetworkGuard.getPlayerDataManager().remove(event.getPlayer());
    }
}
