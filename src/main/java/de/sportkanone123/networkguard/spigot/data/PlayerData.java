package de.sportkanone123.networkguard.spigot.data;

import de.sportkanone123.networkguard.spigot.NetworkGuard;
import de.sportkanone123.networkguard.spigot.manager.ModuleManager;
import de.sportkanone123.networkguard.spigot.module.PacketModule;
import de.sportkanone123.networkguard.spigot.module.TickModule;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@Setter
public class PlayerData {
    private final Player player;
    private final List<PacketModule> packetModules;
    private final List<TickModule> tickModules;

    public PlayerData(final Player player) {
        this.player = player;

        packetModules = NetworkGuard.getModuleManager().loadPacketChecks(this);
        tickModules = NetworkGuard.getModuleManager().loadTickChecks(this);
    }
}
