package de.xaver106.modmode.commands;

import de.xaver106.modmode.player.PlayerFile;
import de.xaver106.modmode.player.PlayerHandler;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class ModModeCommand implements CommandExecutor {

    private final PlayerHandler playerHandler;

    public ModModeCommand(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player player) {

            PlayerFile playerFile = playerHandler.getPlayerFile(player);

            if (playerFile.getBool("Mode")) {
                //Deactivate Modmode
                //Inventorys
                player.getInventory().setContents(playerFile.getItemStacks("inventory"));
                player.getInventory().setArmorContents(playerFile.getItemStacks("armor"));
                player.updateInventory();
                player.getEnderChest().setContents(playerFile.getItemStacks("endChest"));
                clearPlayerPotionEffects(player);
                player.addPotionEffects(playerFile.getPotionEffects("potionEffects"));
                player.setHealth(playerFile.getDouble("health"));
                player.setFoodLevel(playerFile.getInt("foodLevel"));
                player.setSaturation(playerFile.getFloat("saturation"));
                player.setExhaustion(playerFile.getFloat("exhaustion"));
                player.setLevel(playerFile.getInt("level"));
                player.setExp(playerFile.getFloat("experience"));
                player.teleport(playerFile.getLocation("location"));
                playerFile.set("Mode", false);

                player.sendRawMessage(Color.RED + "You are no longer a Mod!");
                player.setGameMode(GameMode.SURVIVAL);
            } else {
                //Activate Modmode
                //Inventorys
                playerFile.set("inventory", player.getInventory().getContents());
                playerFile.set("armor", player.getInventory().getArmorContents());
                playerFile.set("endChest", player.getEnderChest().getContents());
                playerFile.set("potionEffects", player.getActivePotionEffects());
                clearPlayerPotionEffects(player);
                playerFile.set("health", player.getHealth());
                playerFile.set("foodLevel", player.getFoodLevel());
                playerFile.set("saturation", player.getSaturation());
                playerFile.set("exhaustion", player.getExhaustion());
                playerFile.set("level", player.getLevel());
                playerFile.set("experience", player.getExp());
                playerFile.setLocation("location", player.getLocation());

                playerFile.set("Mode", true);
                player.getInventory().clear();
                player.getEnderChest().clear();
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                player.setFoodLevel(20);

                player.sendMessage(Color.RED + "You are now a Mod!");
                player.setGameMode(GameMode.CREATIVE);
            }

        }

        return true;
    }

    private void clearPlayerPotionEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
    }
}