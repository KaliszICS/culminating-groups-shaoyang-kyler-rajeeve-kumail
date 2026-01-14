package data.save;

import data.CharacterData;
import entities.characters.*;
import entities.items.*;
import entities.equipment.*;
import systems.inventory.*;
import java.io.Serializable;
import java.util.*;

/**
 * Represents game data in this game. Includes player info, inventory info, game progress, gacha data, and system data.
 * Implements {@link Serializable} so now game data could be written into a file
 */
public class GameData implements Serializable {
    private static final long serialVersionUID = 1L;

    // player info
    private List<PlayableCharacter> playerCharacters;
    private PlayableCharacter currentMainCharacter;
    private CharacterData characterData;
    // inventory info
    private List<Item> inventoryItems;
    private int inventoryMaxWeight;
    private Map<String, List<Item>> equippedItemsByCharacter;
    // game progress
    private int currentStoryProgress;
    private List<String> completedQuests;
    private List<String> unlockedLocations;
    // gachasystem data
    private int pityCounter5Star;
    private int pityCounter4Star;
    private boolean guaranteed5Star;
    private List<String> pullHistory;
    private GameSettings gameSettings;
    // system data
    private Date saveDate;
    private long playTimeInSeconds;
    private String gameVersion;

    /**
     * Instantiates a new Game data with no args
     */
    public GameData() {
        this.playerCharacters = new ArrayList<>();
        this.inventoryItems = new ArrayList<>();
        this.equippedItemsByCharacter = new HashMap<>();
        this.completedQuests = new ArrayList<>();
        this.unlockedLocations = new ArrayList<>();
        this.pullHistory = new ArrayList<>();
        this.gameSettings = new GameSettings();
        this.saveDate = new Date();
        this.gameVersion = "1.0.0";
        this.currentStoryProgress = 0;
        this.playTimeInSeconds = 0;
        this.pityCounter5Star = 0;
        this.pityCounter4Star = 0;
        this.guaranteed5Star = false;
        this.characterData = null;
    }

    /**
     * Instantiates a new Game data with playerCharacters, inventoryItems, CurrentStoryProcess, and Playtime specified.
     * Please use the default constructor unless you know what you are doing.
     *
     * @param playerCharacters     the player characters
     * @param inventoryItems       the inventory items
     * @param currentStoryProgress the current story progress
     * @param playTimeInSeconds    the play time in seconds
     */
    public GameData(List<PlayableCharacter> playerCharacters, List<Item> inventoryItems, int currentStoryProgress, long playTimeInSeconds) {
        this();
        this.playerCharacters = playerCharacters;
        this.inventoryItems = inventoryItems;
        this.currentStoryProgress = currentStoryProgress;
        this.playTimeInSeconds = playTimeInSeconds;
    }


    /**
     * Gets player characters.
     *
     * @return the player characters
     */
    public List<PlayableCharacter> getPlayerCharacters() {
        return playerCharacters;
    }

    /**
     * Sets player characters.
     *
     * @param playerCharacters the player characters
     */
    public void setPlayerCharacters(List<PlayableCharacter> playerCharacters) {
        this.playerCharacters = playerCharacters;
    }

    /**
     * Gets character data.
     *
     * @return the character data
     */
    public CharacterData getCharacterData() {
        return characterData;
    }

    /**
     * Sets character data.
     *
     * @param characterData the character data
     */
    public void setCharacterData(CharacterData characterData) {
        this.characterData = characterData;
    }

    /**
     * Gets current main character.
     *
     * @return the current main character
     */
    public PlayableCharacter getCurrentMainCharacter() {
        return currentMainCharacter;
    }

    /**
     * Sets current main character.
     *
     * @param currentMainCharacter the current main character
     */
    public void setCurrentMainCharacter(PlayableCharacter currentMainCharacter) {
        this.currentMainCharacter = currentMainCharacter;
    }

    /**
     * Gets inventory items.
     *
     * @return the inventory items
     */
    public List<Item> getInventoryItems() {
        return inventoryItems;
    }

    /**
     * Sets inventory items.
     *
     * @param inventoryItems the inventory items
     */
    public void setInventoryItems(List<Item> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    /**
     * Gets inventory max weight.
     *
     * @return the inventory max weight
     */
    public int getInventoryMaxWeight() {
        return inventoryMaxWeight;
    }

    /**
     * Sets inventory max weight.
     *
     * @param inventoryMaxWeight the inventory max weight
     */
    public void setInventoryMaxWeight(int inventoryMaxWeight) {
        this.inventoryMaxWeight = inventoryMaxWeight;
    }

    /**
     * Gets equipped items by character.
     *
     * @return the equipped items by character
     */
    public Map<String, List<Item>> getEquippedItemsByCharacter() {
        return equippedItemsByCharacter;
    }

    /**
     * Sets equipped items by character.
     *
     * @param equippedItemsByCharacter the equipped items by character
     */
    public void setEquippedItemsByCharacter(Map<String, List<Item>> equippedItemsByCharacter) {
        this.equippedItemsByCharacter = equippedItemsByCharacter;
    }

    /**
     * Gets current story progress.
     *
     * @return the current story progress
     */
    public int getCurrentStoryProgress() {
        return currentStoryProgress;
    }

    /**
     * Sets current story progress.
     *
     * @param currentStoryProgress the current story progress
     */
    public void setCurrentStoryProgress(int currentStoryProgress) {
        this.currentStoryProgress = currentStoryProgress;
    }

    /**
     * Gets completed quests.
     *
     * @return the completed quests
     */
    public List<String> getCompletedQuests() {
        return completedQuests;
    }

    /**
     * Sets completed quests.
     *
     * @param completedQuests the completed quests
     */
    public void setCompletedQuests(List<String> completedQuests) {
        this.completedQuests = completedQuests;
    }

    /**
     * Gets unlocked locations.
     *
     * @return the unlocked locations
     */
    public List<String> getUnlockedLocations() {
        return unlockedLocations;
    }

    /**
     * Sets unlocked locations.
     *
     * @param unlockedLocations the unlocked locations
     */
    public void setUnlockedLocations(List<String> unlockedLocations) {
        this.unlockedLocations = unlockedLocations;
    }

    /**
     * Gets pity counter 5 star.
     *
     * @return the pity counter 5 star
     */
    public int getPityCounter5Star() {
        return pityCounter5Star;
    }

    /**
     * Sets pity counter 5 star.
     *
     * @param pityCounter5Star the pity counter 5 star
     */
    public void setPityCounter5Star(int pityCounter5Star) {
        this.pityCounter5Star = pityCounter5Star;
    }

    /**
     * Gets pity counter 4 star.
     *
     * @return the pity counter 4 star
     */
    public int getPityCounter4Star() {
        return pityCounter4Star;
    }

    /**
     * Sets pity counter 4 star.
     *
     * @param pityCounter4Star the pity counter 4 star
     */
    public void setPityCounter4Star(int pityCounter4Star) {
        this.pityCounter4Star = pityCounter4Star;
    }

    /**
     * Check whether the next pull is a guaranteed 5 star character
     *
     * @return the boolean
     */
    public boolean isGuaranteed5Star() {
        return guaranteed5Star;
    }

    /**
     * Sets guaranteed 5 star status
     *
     * @param guaranteed5Star the guaranteed 5 star
     */
    public void setGuaranteed5Star(boolean guaranteed5Star) {
        this.guaranteed5Star = guaranteed5Star;
    }

    /**
     * Gets pull history
     *
     * @return the pull history
     */
    public List<String> getPullHistory() {
        return pullHistory;
    }

    /**
     * Sets pull history.
     *
     * @param pullHistory the pull history
     */
    public void setPullHistory(List<String> pullHistory) {
        this.pullHistory = pullHistory;
    }

    /**
     * Gets game settings.
     *
     * @return the game settings
     */
    public GameSettings getGameSettings() {
        return gameSettings;
    }

    /**
     * Sets game settings.
     *
     * @param gameSettings the game settings
     */
    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    /**
     * Gets save date.
     *
     * @return the save date
     */
    public Date getSaveDate() {
        return saveDate;
    }

    /**
     * Sets save date.
     *
     * @param saveDate the save date
     */
    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    /**
     * Gets play time in seconds.
     *
     * @return the play time in seconds
     */
    public long getPlayTimeInSeconds() {
        return playTimeInSeconds;
    }

    /**
     * Sets play time in seconds.
     *
     * @param playTimeInSeconds the play time in seconds
     */
    public void setPlayTimeInSeconds(long playTimeInSeconds) {
        this.playTimeInSeconds = playTimeInSeconds;
    }

    /**
     * Gets game version.
     *
     * @return the game version
     */
    public String getGameVersion() {
        return gameVersion;
    }

    /**
     * Sets game version.
     *
     * @param gameVersion the game version
     */
    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    /**
     * Add completed quest with a specific quest id.
     *
     * @param questId the quest id (must match otherwise it might crash)
     */
    public void addCompletedQuest(String questId) {
        if (!completedQuests.contains(questId)) {
            completedQuests.add(questId);
        }
    }

    /**
     * Check if a specific quest is completed.
     *
     * @param questId the quest id to check
     * @return the boolean indicating whether the quest is completed
     */
    public boolean isQuestCompleted(String questId) {
        return completedQuests.contains(questId);
    }

    /**
     * Unlock location with provided name
     *
     * @param locationId the location id
     */
    public void unlockLocation(String locationId) {
        if (!unlockedLocations.contains(locationId)) {
            unlockedLocations.add(locationId);
        }
    }

    /**
     * Check if a location is unlocked.
     *
     * @param locationId the location id to check
     * @return the boolean indicating whether the location is unlocked
     */
    public boolean isLocationUnlocked(String locationId) {
        return unlockedLocations.contains(locationId);
    }

    /**
     * Add pull record.
     *
     * @param pullRecord the pull record
     */
    public void addPullRecord(String pullRecord) {
        pullHistory.add(pullRecord);
        if (pullHistory.size() > 1000) {
            pullHistory = pullHistory.subList(pullHistory.size() - 500, pullHistory.size());
        }
    }

    /**
     * Display summary of the game
     */
    public void displaySummary() {
        System.out.println("=== Game Data Summary ===");
        System.out.println("Save Time: " + saveDate);
        System.out.println("Game Version: " + gameVersion);
        System.out.println("Main Story Progress: Chapter " + currentStoryProgress);
        System.out.println("Play Time: " + formatPlayTime(playTimeInSeconds));
        System.out.println("Character Count: " + playerCharacters.size());
        System.out.println("Inventory Items: " + inventoryItems.size() + " items");
        System.out.println("Completed Side Quests: " + completedQuests.size());
        System.out.println("Unlocked Locations: " + unlockedLocations.size());
        System.out.println("Gacha Pull Records: " + pullHistory.size() + " records");
        if (characterData != null) {
            System.out.println("Character Data: Loaded (" + characterData.getOwnedCharacters().size() + " characters)");
        } else {
            System.out.println("Character Data: Not Loaded");
        }
        System.out.println("====================");
    }

    private String formatPlayTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        String temp = hours + ":" + minutes + ":" + secs;
        return temp;
    }
}

/**
 * Represents the game settings in game.
 */
class GameSettings implements Serializable {
    private static final long serialVersionUID = 2L;

    private int volumeMaster;
    private int volumeMusic;
    private int volumeSfx;
    private String language;
    private String displayMode;
    private int graphicsQuality;
    private boolean subtitlesEnabled;
    private String controlScheme;

    /**
     * Open a new game settings will default setups.
     */
    public GameSettings() {
        this.volumeMaster = 80;
        this.volumeMusic = 70;
        this.volumeSfx = 75;
        this.language = "English";
        this.displayMode = "CommandLine";
        this.graphicsQuality = 2; // 0 = low, 1 = medium, 2 = high
        this.subtitlesEnabled = true;
        this.controlScheme = "default";
    }

    /**
     * Gets master volume
     *
     * @return the master volume
     */
    public int getVolumeMaster() {
        return volumeMaster;
    }

    /**
     * Sets master volume
     *
     * @param volumeMaster the master volume
     */
    public void setVolumeMaster(int volumeMaster) {
        this.volumeMaster = Math.max(0, Math.min(100, volumeMaster));
    }

    /**
     * Gets music volume
     *
     * @return the music volume
     */
    public int getVolumeMusic() {
        return volumeMusic;
    }

    /**
     * Sets music volume
     *
     * @param volumeMusic music volume
     */
    public void setVolumeMusic(int volumeMusic) {
        this.volumeMusic = Math.max(0, Math.min(100, volumeMusic));
    }

    /**
     * Gets sfx volume
     *
     * @return the sfx volume
     */
    public int getVolumeSfx() {
        return volumeSfx;
    }

    /**
     * Sets sfx volume
     *
     * @param volumeSfx the sfx volume
     */
    public void setVolumeSfx(int volumeSfx) {
        this.volumeSfx = Math.max(0, Math.min(100, volumeSfx));
    }

    /**
     * Gets language settings
     *
     * @return the language settings
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets language settings
     *
     * @param language the language settings
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets display mode in game
     *
     * @return the display mode in game
     */
    public String getDisplayMode() {
        return displayMode;
    }

    /**
     * Sets display mode in game
     *
     * @param displayMode the display mode in game
     */
    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    /**
     * Gets graphics quality.
     *
     * @return the graphics quality
     */
    public int getGraphicsQuality() {
        return graphicsQuality;
    }

    /**
     * Sets graphics quality.
     *
     * @param graphicsQuality the graphics quality
     */
    public void setGraphicsQuality(int graphicsQuality) {
        this.graphicsQuality = Math.max(0, Math.min(2, graphicsQuality));
    }

    /**
     * Check if subtitles are enabled
     *
     * @return the boolean indicating whether the subtitles are enabled or not
     */
    public boolean isSubtitlesEnabled() {
        return subtitlesEnabled;
    }

    /**
     * Sets whether the subtitles are enabled or not
     *
     * @param subtitlesEnabled the boolean indicating whether the subtitles are enabled or not
     */
    public void setSubtitlesEnabled(boolean subtitlesEnabled) {
        this.subtitlesEnabled = subtitlesEnabled;
    }

    /**
     * Gets control scheme.
     *
     * @return the control scheme
     */
    public String getControlScheme() {
        return controlScheme;
    }

    /**
     * Sets control scheme.
     *
     * @param controlScheme the control scheme
     */
    public void setControlScheme(String controlScheme) {
        this.controlScheme = controlScheme;
    }

    @Override
    public String toString() {
        return "Volume: Master " + volumeMaster + "%, Music " + volumeMusic + "%, SFX " + volumeSfx + "%, Language: " + language +
                ", Display Mode: " + displayMode + ", Graphics Quality: " + graphicsQuality +
                ", Subtitles: " + subtitlesEnabled;
    }
}
