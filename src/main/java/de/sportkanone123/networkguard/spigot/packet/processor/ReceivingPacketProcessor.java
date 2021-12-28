package de.sportkanone123.networkguard.spigot.packet.processor;

import de.sportkanone123.networkguard.spigot.NetworkGuard;
import de.sportkanone123.networkguard.spigot.data.PlayerData;
import de.sportkanone123.networkguard.spigot.packet.LoginPacket;
import de.sportkanone123.networkguard.spigot.packet.PlayPacket;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;

public class ReceivingPacketProcessor {

    public static void handlePlayPacket(Player player, PlayPacket packet){
        if(!packet.isFlying() && !packet.isPosition() && !packet.isKeepAlive())
            System.out.println("Received: " + packet.getRawPacket().getName());

        if(NetworkGuard.getPlayerDataManager().has(player)){
            PlayerData data = NetworkGuard.getPlayerDataManager().getPlayerData(player);
            data.getModules().forEach(module -> module.handle(packet));
        }else if(player != null && player.isOnline()){
            NetworkGuard.getPlayerDataManager().add(player);
            PlayerData data = NetworkGuard.getPlayerDataManager().getPlayerData(player);
            data.getModules().forEach(module -> module.handle(packet));
        }
    }

    public static void handleLoginPacket(InetSocketAddress address, LoginPacket packet){

    }
}
