package data.save;

import util.fileio.FileHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** 
 * This class handles anything related to saving data
 * These include: save slots, save directory and write the game data
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
                return fileHandler.writeToFile(gameData, saveFile.getPath());
            }
        }

        System.out.println("No empty save slot found.");
        return false;
    }
/**
 * saves the same data if a slot already exist
 * @param gameData
 * @param slot
 * @return
 */
    public boolean saveGame(GameData gameData, int slot) {
        if (slot < 1 || slot > maxSaveSlots) {
            return false;
        }
    
    File file = new File(saveFilePath + "save_" + slot + ".dat");
    return fileHandler.writeToFile(gameData, file.getPath());
}

    /**
     * loading the game data from a specfic save slot
     * 
     * @param slot
     * @return
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
     * @param slot
     * @return
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

    public List<String> listSaves() {
        List<String> saveList = new ArrayList<>();

        for (int i = 1; i <= maxSaveSlots; i++) {
            File saveFile =  new File(saveFilePath + "save_" + i + ".dat");

            if (saveFile.exists()) {
                saveList.add("Save Slot " + i);
            }
        }

        return saveList;
    }

    private void makeSaveFolder() {
        File folder = new File(saveFilePath);
        if (!folder.exists() && !folder.mkdirs()) {
            System.err.println("Failed to create save folder: " + saveFilePath);
        }
    }
}
