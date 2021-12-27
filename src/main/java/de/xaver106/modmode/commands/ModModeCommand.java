package de.xaver106.modmode.commands;

import de.xaver106.modmode.ModMode;
import de.xaver106.modmode.player.PlayerFile;
import de.xaver106.modmode.player.PlayerHandler;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModModeCommand implements CommandExecutor {

    private final ModMode pluginInstance;
    private final PlayerHandler playerHandler;

    public ModModeCommand(ModMode plugin, PlayerHandler pPlayerHandler){
        pluginInstance = plugin;
        playerHandler = pPlayerHandler;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player player){

            PlayerFile playerFile = playerHandler.getPlayerFile(player);

            if (playerFile.getMode()){
                //Deaktivate Modmode
                player.getInventory().setContents(playerFile.getInventory());
                player.getInventory().setArmorContents(playerFile.getArmor());
                player.updateInventory();
                player.getEnderChest().setContents(playerFile.getEndChest());
                playerFile.setMode(false);
                playerFile.removeInventory();
                playerFile.removeArmor();
                playerFile.removeEndChest();
                player.sendRawMessage(Color.RED + "You are no longer a Mod!");
                player.setGameMode(GameMode.SURVIVAL);
            }else{
                //Activate Modmode
                playerFile.setInventory(player.getInventory().getContents());
                playerFile.setArmor(player.getInventory().getArmorContents());
                playerFile.setEndChest(player.getEnderChest().getContents());
                playerFile.setMode(true);
                player.getInventory().clear();
                player.getEnderChest().clear();
                player.sendMessage(Color.RED + "You are now a Mod!");
                player.setGameMode(GameMode.CREATIVE);
            }

        }

        return true;
    }
}