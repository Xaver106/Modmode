package de.xaver106.modmode.player;

import de.xaver106.modmode.ModMode;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * Provides a handler for managing all of the Player configurations.
 */
public class PlayerHandler {

    @SuppressWarnings("FieldCanBeLocal")
    private final String fileEnding = ".inv";
    private final String dataFolder;

    private final HashMap<UUID, PlayerFileYAML> playerFileList;
    private final ModMode pluginInstance;

    /**
     * Instantiates a new Player handler.
     *
     * @param pluginInstance The Plugin Instance
     */
    public PlayerHandler(ModMode pluginInstance) {

        this.pluginInstance = pluginInstance;
        dataFolder = pluginInstance.getDataFolder() + File.separator + "players";

        playerFileList = new HashMap<>();

        this.reloadFiles();
    }

    /**
     * Reloads the Files from the Disk.
     * Warning: Overides not saved changes.
     */
    public void reloadFiles() {

        if (playerFileList != null) {
            playerFileList.clear();
        }

        File[] directoryListing = new File(dataFolder).listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                playerFileList.put(UUID.fromString(child.getName().replace(fileEnding, "")), new PlayerFileYAML(child, pluginInstance));
            }
        }
    }

    /**
     * Saves all PlayerFiles to Disk
     * {@link PlayerFileYAML#saveFile()}
     */
    public void saveFiles() {
        for (PlayerFileYAML playerFileYAML : playerFileList.values()) {
            playerFileYAML.saveFile();
        }
    }

    /**
     * Returns the PlayerFile of the given Player from the UUID. Creates a new PlayerFile if none exists so far.
     *
     * @param uuid UUID of the Player.
     * @return PlayerFile of the Player
     */
    public PlayerFileYAML getPlayerFile(UUID uuid) {

        if (!playerFileList.containsKey(uuid)) {
            playerFileList.put(uuid, new PlayerFileYAML(new File(dataFolder, uuid + fileEnding), pluginInstance));
        }
        return playerFileList.get(uuid);
    }

    /**
     * Returns the PlayerFile of the given Player from the UUID. Creates a new PlayerFile if none exists so far.
     *
     * @param player the Player Object of the player.
     * @return PlayerFile of the Player {@link PlayerHandler#getPlayerFile(UUID)}
     */
    public PlayerFileYAML getPlayerFile(Player player) {

        return getPlayerFile(player.getUniqueId());
    }
}