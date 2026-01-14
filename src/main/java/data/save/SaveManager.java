package data.save;

import util.fileio.FileHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles anything related to saving data
 * These include: save slots, save directory and write the game data
 */

/**
 * @author Kyler Huang
 * @version 1.0
 */
public class SaveManager {

    private String saveFilePath;
    private int maxSaveSlots;
    private FileHandler fileHandler;

    /**
     * Constructor that creates a SaveManager object
     * Sets a max save lot and creates a filder handler object
     */
    public SaveManager() {
        saveFilePath = "saves/";
        maxSaveSlots = 5;
        fileHandler = new FileHandler("SAVE");
        makeSaveFolder();
    }

    /**
     * saves data to the first slot
     *
     * @param gameData to be saved
     * @return true if the game data was saved. false if the game data was not saved
     */
    public boolean saveGame(GameData gameData) {
        if (gameData == null) {
            return false;
        }

        for (int i = 1; i <= maxSaveSlots; i++) {
            File saveFile = new File(saveFilePath + "save_" + i + ".dat");

            if (!saveFile.exists()) {
                return saveGame(gameData, i);
            }
        }

        System.out.println("No empty save slot found. Overwriting slot 1.");
        return saveGame(gameData, 1);
    }

    /**
     * saves the same data if a slot already exist
     *
     * @param gameData to be saved
     * @param slot     the slot number to save the game data
     * @return true if the game data was saved. false if the game data was not saved
     */
    public boolean saveGame(GameData gameData, int slot) {
        if (slot < 1 || slot > maxSaveSlots) {
            System.out.println("Invalid slot number: " + slot);
            return false;
        }

        gameData.setSaveDate(new java.util.Date());

        File file = new File(saveFilePath + "save_" + slot + ".dat");
        System.out.println("Saving to slot " + slot + " at: " + file.getPath());

        boolean success = fileHandler.writeToFile(gameData, file.getPath());

        if (success) {
            System.out.println("Successfully saved game to slot " + slot);
            System.out.println("Characters saved: " +
                    (gameData.getCharacterData() != null ?
                            gameData.getCharacterData().getOwnedCharacters().size() : 0));
        } else {
            System.out.println("Failed to save game to slot " + slot);
        }

        return success;
    }

    /**
     * loading the game data from a specfic save slot
     *
     * @param slot the slot number to load the game data from
     * @return the game data loaded from the save slot
     */

    public GameData loadGame(int slot) {
        if (slot < 1 || slot > maxSaveSlots) {
            System.out.println("Invalid slot number.");
            return null;
        }

        File saveFile = new File(saveFilePath + "save_" + slot + ".dat");

        if (!saveFile.exists()) {
            System.out.println("Save slot is empty.");
            return null;
        }

        Object loadedData = fileHandler.readFromFile(saveFile.getPath());

        if (loadedData instanceof GameData) {
            return (GameData) loadedData;
        }

        System.out.println("Save file is corrupted.");
        return null;
    }

    /**
     * deletes the save slot
     *
     * @param slot the slot number to delete the game data from
     * @return true if the save slot was deleted. false if the save slot was not deleted
     */
    public boolean deleteSave(int slot) {
        if (slot < 1 || slot > maxSaveSlots) {
            return false;
        }

        File saveFile = new File(saveFilePath + "save_" + slot + ".dat");

        if (saveFile.exists()) {
            return saveFile.delete();
        }

        return false;
    }

    /**
     * lists all the save slots that have data
     * @return a list of save slot names that have data
     */
    public List<String> listSaves() {
        List<String> saveList = new ArrayList<>();

        for (int i = 1; i <= maxSaveSlots; i++) {
            File saveFile = new File(saveFilePath + "save_" + i + ".dat");

            if (saveFile.exists()) {
                saveList.add("Save Slot " + i);
            }
        }

        return saveList;
    }

    /**
     * creates the save folder if it does not exist
     */
    private void makeSaveFolder() {
        File folder = new File(saveFilePath);
        if (!folder.exists() && !folder.mkdirs()) {
            System.err.println("Failed to create save folder: " + saveFilePath);
        }
    }
}