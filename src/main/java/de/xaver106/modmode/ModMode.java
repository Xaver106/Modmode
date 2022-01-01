package de.xaver106.modmode;

import de.xaver106.modmode.commands.ModModeCommand;
import de.xaver106.modmode.player.PlayerHandler;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("FieldCanBeLocal")
public final class ModMode extends JavaPlugin {

    private PlayerHandler playerHandler;
    private LuckPerms luckpermsApi;

    @Override
    public void onEnable() {
        playerHandler = new PlayerHandler(this);

        getCommand("modmode").setExecutor(new ModModeCommand(playerHandler));

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckpermsApi = provider.getProvider();

        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
