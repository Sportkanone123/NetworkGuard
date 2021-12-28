package de.sportkanone123.networkguard.spigot.data;

import de.sportkanone123.networkguard.spigot.manager.ModuleManager;
import de.sportkanone123.networkguard.spigot.module.Module;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@Setter
public class PlayerData {
    private final Player player;
    private final List<Module> modules;

    public PlayerData(final Player player) {
        this.player = player;
        modules = ModuleManager.loadChecks(this);
    }
}
