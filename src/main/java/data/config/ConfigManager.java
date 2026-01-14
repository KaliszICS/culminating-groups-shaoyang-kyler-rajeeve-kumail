package data.config;

import util.fileio.FileHandler;
import java.util.*;
import java.io.*;

/**
 * This class manages the game configuration settings
 * It handles loading, saving, and accessing configuration properties
 */

public class ConfigManager{
    /**
     * Properties object to store configuration key-value pairs
     */
    private Properties configProps;
    /**
     * Path to the configuration file 
     * */
    private String configPath;

    /**
     * Constructor that creates a ConfigManager object
     * Loads the configuration from the file or creates default settings if the file does not exist
     */
    public ConfigManager() {
        this.configProps = new Properties();
        this.configPath = "game_config.properties";
        loadConfig();
    }

    /**
     * Loads configuration from the file
     */
    public void loadConfig() {
    File configPathFile = new File(configPath);

    if (!configPathFile.exists()) {
        System.out.println("Config not found! Creating defaults at: " + configPath);
        setDefaultProperties();
        saveConfig();
        return;
    }

    try (FileInputStream stream = new FileInputStream(configPathFile)) {
        configProps.load(stream);
        System.out.println("Config loaded successfully: " + configProps.size() + " entries.");
    } catch (IOException ex) {
        System.err.println("Could not read config file: " + ex.getMessage());
        setDefaultProperties();
    }
}
    /**
     * Sets default configuration properties
     */
     private void setDefaultProperties() {
        configProps.setProperty("game.difficulty", "normal");
        configProps.setProperty("game.language", "en_CA");
        configProps.setProperty("game.show_enemy_hp", "true");
        configProps.setProperty("game.auto_save", "true");
        configProps.setProperty("game.auto_save_interval", "15");
        configProps.setProperty("game.auto_battle", "false");
        configProps.setProperty("game.battle_speed", "1.0");
        configProps.setProperty("game.show_damage_numbers", "true");
    }
    /**
     * Gets all configuration properties
     * @return Properties object containing all configuration key-value pairs
     */ 
     public Properties getAllProperties() {
        return new Properties(configProps);
    }
    /**
     * Gets a configuration property by key
     * @param key the configuration key
     * @return the value associated with the key, or null if the key does not exist
     */
    public String getProperty(String key){
        return configProps.getProperty(key);
    }

    /**
     * Gets a configuration property by key with a default value
     * @param key the configuration key
     * @param defaultValue the default value to return if the key does not exist
     * @return the value associated with the key, or defaultValue if the key does not exist
     */
    public String getProperty(String key, String defaultValue) {
        return configProps.getProperty(key, defaultValue);
    }

    /**
     * Gets an integer configuration property by key
     * @param key the configuration key
     * @return the integer value associated with the key, or 0 if the key does not exist or is not a valid integer
     */
    public int getIntProperty(String key) {
        return getIntProperty(key, 0);
    }

    /**
     * Gets an integer configuration property by key with a default value
     * @param key the configuration key
     * @param defaultVal the default integer value to return if the key does not exist or is not a valid integer
     * @return the integer value associated with the key, or defaultVal if the key does not exist or is not a valid integer
     */
    public int getIntProperty(String key, int defaultVal) {
        String propString = configProps.getProperty(key);
        if (propString == null) {
            return defaultVal;
        }

        try {
            return Integer.parseInt(propString);
        } catch (NumberFormatException ex) {
            return defaultVal;
        }
    }
    
    /**
     * Gets a double configuration property by key
     * @param key the configuration key
     * @return the double value associated with the key, or 0.0 if the key does not exist or is not a valid double
     */
    public double getDoubleProperty(String key) {
        return getDoubleProperty(key, 0.0);
    }

    public double getDoubleProperty(String key, double defaultVal) {
        String propDouble = configProps.getProperty(key);
        if (propDouble == null) return defaultVal;

        try {
            return Double.parseDouble(propDouble);
        } catch (NumberFormatException ex) {
            return defaultVal;
        }
    }

    public boolean getBooleanProperty(String key) {
        return getBooleanProperty(key, false);
    }

    public boolean getBooleanProperty(String key, boolean fallback) {
        String propbool = configProps.getProperty(key);
        if (propbool == null){
            return fallback;
        }
        return propbool.equalsIgnoreCase("true") || propbool.equals("1");
    }     

    public void setProperty(String key, String value) {
        configProps.setProperty(key, value);
        System.out.println("Set configuration items: " + key + " = " + value);
    }

    public void setProperty(String key, int value) {
        setProperty(key, String.valueOf(value));
    }

    public void setProperty(String key, double value) {
        setProperty(key, String.valueOf(value));
    }

    public void setProperty(String key, boolean value) {
        setProperty(key, String.valueOf(value));
    }

    public Properties getPropertiesByPrefix(String prefix) {
        Properties resultProps = new Properties();

        for (String key : configProps.stringPropertyNames()) {
            String value = configProps.getProperty(key);
            if (key.startsWith(prefix)) {
                resultProps.setProperty(key, value);
            }
        }

        return resultProps;
    }

    public void removeProperty(String key) {
        configProps.remove(key);
        System.out.println("delete config item: " + key);
    }

    public boolean containsProperty(String key) {
        return configProps.containsKey(key);
    }

    public void saveConfig() {

        FileHandler fileHandler = new FileHandler();


        String dataToSave = "# Star Rail Game Configuration File\n";
        dataToSave += "# Generated on: " + new Date() + "\n";
        dataToSave += "# Do not edit this file manually unless you know what you are doing\n\n";

        List<String> sortedKeys = new ArrayList<>();
        for (Object objKey : configProps.keySet()) {
            sortedKeys.add((String) objKey);
        }
        Collections.sort(sortedKeys);

        for (String key : sortedKeys) {
            String value = configProps.getProperty(key);
            dataToSave += key + "=" + value + "\n";
        }

        boolean success = fileHandler.exportToTXT(dataToSave, configPath);
        if (success) {
            System.out.println("Configuration saved successfully: " + configPath);
        } else {
            System.err.println("Failed to save configuration");
        }
    }
    
    public void reload() {
        configProps.clear();
        loadConfig();
    }

    public void resetToDefaults() {
        configProps.clear();
        setDefaultProperties();
        saveConfig();
    }

    public boolean validateConfig() {
        boolean isValid = true;

        String[] requiredKeys = {
                "game.language",
                "game.difficulty",
                "graphics.resolution"
        };

        for (String key : requiredKeys) {
            if (!containsProperty(key)) {
                System.err.println("Missing required configuration items: " + key);
                isValid = false;
            }
        }

        int fpsLimit = getIntProperty("system.max_fps");
        if (fpsLimit < 30 || fpsLimit > 240) {
            System.err.println("FPS limit exceeds the effective range(30-240): " + fpsLimit);
            isValid = false;
        }

        double masterVolume = getDoubleProperty("audio.master_volume");
        if (masterVolume < 0 || masterVolume > 100) {
            System.err.println("Master volume out of range (0-100): " + masterVolume);
            isValid = false;
        }

        return isValid;
    }

    public void exportToCSV(String filename) {
        FileHandler fileHandler = new FileHandler("CSV");

        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{"Configuration item", "value", "type", "description"});

        Map<String, String> descriptions = new HashMap<>();
        descriptions.put("graphics.resolution", "Screen resolution");
        descriptions.put("graphics.fullscreen", "Full screen mode");
        descriptions.put("graphics.quality", "Graphics quality");
        descriptions.put("audio.master_volume", "Main volume");
        descriptions.put("game.difficulty", "Game difficulty");
        descriptions.put("game.language", "Language settings");

        List<String> sortedKeys = new ArrayList<>(configProps.stringPropertyNames());
        Collections.sort(sortedKeys);

        for (String key : sortedKeys) {
            String value = configProps.getProperty(key);
            String type = getValueType(value);
            String description = descriptions.getOrDefault(key, "Game configuration items");

            csvData.add(new String[]{key, value, type, description});
        }

        boolean success = fileHandler.exportToCSV(csvData, filename);
        if (success) {
            System.out.println("The configuration has been exported to a CSV file: " + filename);
        } else {
            System.err.println("Exporting configuration to CSV failed.");
        }
    }

    public boolean importFromFile(String importPath) {
        File importFile = new File(importPath);
        if (!importFile.exists() || !importFile.isFile()) {
            System.err.println("Import file does not exist: " + importPath);
            return false;
        }

        Properties importedProps = new Properties();
        try (FileInputStream fis = new FileInputStream(importFile)) {
            importedProps.load(fis);

            for (String key : importedProps.stringPropertyNames()) {
                configProps.setProperty(key, importedProps.getProperty(key));
            }

            System.out.println("Configuration imported from file successfully.: " + importPath);
            System.out.println("Imported " + importedProps.size() + " configuration items");

            saveConfig();
            return true;

        } catch (IOException e) {
            System.err.println("Failed to import configuration file: " + e.getMessage());
            return false;
        }
    }

    private String getValueType(String value) {
        if (value == null) {
            return "unknown";
        }

        try {
            Integer.parseInt(value);
            return "integer";
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(value);
                return "double";
            } catch (NumberFormatException e2) {
                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    return "Boolean value";
                } else {
                    return "string";
                }
            }
        }
    }

   public void printConfigSummary() {
        System.out.println(" Summary of configuration information ");
        System.out.println("Configuration file path: " + configPath);
        System.out.println("Number of configuration items: " + configProps.size());
        System.out.println("last modified: " + new Date(new File(configPath).lastModified()));

        Map<String, Integer> categoryCount = new HashMap<>();
        for (String key : configProps.stringPropertyNames()) {
            String category = key.split("\\.")[0];
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        System.out.println("\nStatistics by category:");
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " item");
        }

        System.out.println("\nConfiguration verification: " + (validateConfig() ? "✓ efficient" : "✗ invalid"));
    }

    public boolean backupConfig() {
        String backupPath = configPath + ".backup_" + System.currentTimeMillis();
        return saveConfigTo(backupPath);
    }

    public boolean saveConfigTo(String path) {
        FileHandler fileHandler = new FileHandler();
        String data = "";

        List<String> sortedKeys = new ArrayList<>(configProps.stringPropertyNames());
        Collections.sort(sortedKeys);

        for (String key : sortedKeys) {
            data += key + "=" + configProps.getProperty(key) + "\n";
        }

        return fileHandler.exportToTXT(data, path);
    }
}