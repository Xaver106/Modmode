package de.xaver106.modmode;

import de.xaver106.modmode.commands.ModModeCommand;
import de.xaver106.modmode.player.PlayerFile;
import de.xaver106.modmode.player.PlayerHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class ModMode extends JavaPlugin {

    private PlayerHandler playerHandler;

    @Override
    public void onEnable() {
        playerHandler = new PlayerHandler(this);

        getCommand("modmode").setExecutor(new ModModeCommand(this, playerHandler));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
