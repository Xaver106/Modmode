package de.xaver106.modmode.player;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Provides the Configuration for individual players.
 */
public class PlayerFile {

    private YamlConfiguration yamlFile;
    private final File playerFile;
    private final Plugin pluginInstance;

    /**
     * Instantiates a new Player file.
     *
     * @param file   The File to save to.
     * @param plugin The Plugin Instance.
     */
    public PlayerFile(File file, Plugin plugin) {

        pluginInstance = plugin;
        playerFile = file;

        if (playerFile.exists() && !playerFile.isDirectory()) { // If the File exists load it
            yamlFile = YamlConfiguration.loadConfiguration(playerFile);

        } else { // If not create new File
            yamlFile = new YamlConfiguration();
            yamlFile.set("isMod", false);
            try {
                yamlFile.save(playerFile);
            } catch (IOException e) {
                pluginInstance.getLogger().log(Level.SEVERE, "Unable to create new Player File:" + playerFile.getName());
            }
        }
    }

    /**
     * Saves the changes made to disk.
     */
    public void saveFile() {
        try {
            yamlFile.save(playerFile);
        } catch (IOException e) {
            pluginInstance.getLogger().log(Level.SEVERE, "Unable to save Player File:" + playerFile.getName());
        }
    }

    /**
     * Reloads the configuration (Saves it and loads it back)
     */
    public void reloadFile() {

        saveFile();

        yamlFile = YamlConfiguration.loadConfiguration(playerFile);
    }

    /**
     * Sets mode.
     *
     * @param isMod the is mod
     */
    public void setMode(Boolean isMod) {
        yamlFile.set("isMod", isMod);
        saveFile();
    }

    /**
     * Gets mode.
     *
     * @return the mode
     */
    public Boolean getMode() {
        return yamlFile.getBoolean("isMod");
    }

    /**
     * Sets inventory.
     *
     * @param inventory the inventory
     */
    public void setInventory(ItemStack[] inventory) {
        yamlFile.set("inventory", inventory);
        saveFile();
    }

    /**
     * Get inventory item stack [ ].
     *
     * @return the item stack [ ]
     */
    public ItemStack[] getInventory() {
        return extractItemStacks("inventory");
    }

    /**
     * Remove inventory.
     */
    public void removeInventory() {
        yamlFile.set("inventory", null);
        saveFile();
    }

    /**
     * Sets armor.
     *
     * @param armor the armor
     */
    public void setArmor(ItemStack[] armor) {
        yamlFile.set("armor", armor);
        saveFile();
    }

    /**
     * Get armor item stack [ ].
     *
     * @return the item stack [ ]
     */
    public ItemStack[] getArmor() {
        return extractItemStacks("armor");
    }

    /**
     * Remove armor.
     */
    public void removeArmor() {
        yamlFile.set("armor", null);
        saveFile();
    }

    /**
     * Sets end chest.
     *
     * @param endChest the end chest
     */
    public void setEndChest(ItemStack[] endChest) {
        yamlFile.set("endChest", endChest);
        saveFile();
    }

    /**
     * Get end chest item stack [ ].
     *
     * @return the item stack [ ]
     */
    public ItemStack[] getEndChest() {
        return extractItemStacks("endChest");
    }

    /**
     * Remove end chest.
     */
    public void removeEndChest() {
        yamlFile.set("endChest", null);
        saveFile();
    }

    /**
     * Internal use only method to extract ItemStack Arrays from the configuration.
     *
     * @param path path of the ItemStacks to extract
     * @return The extracted and deserialized ItemStack Array
     */
    @SuppressWarnings("unchecked")
    private ItemStack[] extractItemStacks(String path) {

        reloadFile(); // Reload required, doesn't load correctly without.

        ArrayList<ItemStack> returnValue = (ArrayList<ItemStack>) yamlFile.getList(path, new ArrayList<ItemStack>());

        return returnValue.toArray(new ItemStack[0]);
    }
}
