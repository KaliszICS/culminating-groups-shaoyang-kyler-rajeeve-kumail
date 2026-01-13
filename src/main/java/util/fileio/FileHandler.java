package util.fileio;

import entities.items.Item;
import java.io.*;
import java.util.List;

public class FileHandler {
    public String outputFormat;

    public FileHandler(String format) {
        this.outputFormat = format;
    }

    /**
     * Writes the GameData object to a file using Serialization.
     */
    public boolean writeToFile(Object data, String filename) {
        if (data == null || filename == null || filename.isEmpty()) {
            return false;
        }

        // We use ObjectOutputStream to save the actual object state
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reads a serialized GameData object back from a file.
     */
    public Object readFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading file: " + e.getMessage());
            return null;
        }
    }

    // --- Placeholders for your Export methods ---

    public boolean exportToCSV(List<Item> items, String filename) {
        // Logic for converting items list to CSV text goes here
        return false;
    }

    public boolean exportToTXT(String data, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(data);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
