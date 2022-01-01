package de.xaver106.modmode.player;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

/**
 * The interface Player file.
 */
public interface PlayerFile {

    /**
     * Saves the changes made to disk.
     */
    void saveFile();

    /**
     * Reloads the configuration (Saves it and loads it back)
     */
    void reloadFile();

    /**
     * Sets Data to path.
     *
     * @param path  the path
     * @param value the value
     */
    void set(String path, Object value);

    void setLocation(String path, Location location);


    /**
     * Gets a boolean Value from path.
     *
     * @param path the path
     * @return the bool
     */
    boolean getBool(String path);

    double getDouble(String path);

    float getFloat(String path);

    int getInt(String path);

    Location getLocation(String path);

    /**
     * Gets an itemStack Liste from path.
     *
     * @param path the path
     * @return the item stack [ ]
     */
    ItemStack[] getItemStacks(String path);

    /**
     * Removes value at path.
     *
     * @param path the path
     */
    void remove(String path);

    Collection<PotionEffect> getPotionEffects(String path);

}
