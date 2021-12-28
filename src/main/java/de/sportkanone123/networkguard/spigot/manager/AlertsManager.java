package de.sportkanone123.networkguard.spigot.manager;

import org.bukkit.entity.Player;

public class AlertsManager {
    public static void handleDetection(Player player, String info){
        System.out.println(player.getName() + " failed " + info);
    }
}
