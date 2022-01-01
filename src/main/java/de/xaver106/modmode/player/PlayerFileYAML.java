package de.xaver106.modmode.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

/**
 * Provides the Configuration for individual players.
 */
public class PlayerFileYAML implements PlayerFile {

    private YamlConfiguration yamlFile;
    private final File playerFile;
    private final Plugin pluginInstance;

    /**
     * Instantiates a new Player file.
     *
     * @param playerFile     The File to save to.
     * @param pluginInstance The Plugin Instance.
     */
    public PlayerFileYAML(File playerFile, Plugin pluginInstance) {

        this.pluginInstance = pluginInstance;
        this.playerFile = playerFile;

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
     * Sets a Value to the path.
     *
     * @param path  the path
     * @param value the value
     */
    public void set(String path, Object value) {
        yamlFile.set(path, value);
        saveFile();
    }

    public void setLocation(String path, Location location) {
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        set(path + ".world", world);
        set(path + ".x", x);
        set(path + ".y", y);
        set(path + ".z", z);
        set(path + ".yaw", yaw);
        set(path + ".pitch", pitch);
    }

    public Location getLocation(String path) {
        World world = Bukkit.getWorld(getString(path + ".world"));
        double x = getDouble(path + ".x");
        double y = getDouble(path + ".y");
        double z = getDouble(path + ".z");
        float yaw = getFloat(path + ".yaw");
        float pitch = getFloat(path + ".pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }

    public boolean getBool(String path) {
        return yamlFile.getBoolean(path);
    }

    public String getString(String path) {
        return yamlFile.getString(path);
    }

    @Override
    public double getDouble(String path) {
        return yamlFile.getDouble(path);
    }

    public int getInt(String path) {
        return yamlFile.getInt(path);
    }

    public float getFloat(String path) {
        return (float) yamlFile.getDouble(path);
    }

    /**
     * Get item stack item stack [ ].
     *
     * @param path the path
     * @return the item stack [ ]
     */
    @SuppressWarnings("unchecked")
    public ItemStack[] getItemStacks(String path) {

        reloadFile(); // Reload required, doesn't load correctly without.

        ArrayList<ItemStack> returnValue = (ArrayList<ItemStack>) yamlFile.getList(path, new ArrayList<ItemStack>());

        return returnValue.toArray(new ItemStack[0]);
    }

    /**
     * Get potion effects potion effect [ ].
     *
     * @param path the path
     * @return the potion effect [ ]
     */
    @SuppressWarnings("unchecked")
    public Collection<PotionEffect> getPotionEffects(String path) {

        reloadFile(); // Reload required, doesn't load correctly without.

        return (Collection<PotionEffect>) yamlFile.getList(path, new ArrayList<PotionEffect>());
    }

    public void remove(String path) {
        yamlFile.set(path, null);
        saveFile();
    }
}
