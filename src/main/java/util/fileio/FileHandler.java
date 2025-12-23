package util;

import entities.items.Item;
import systems.gacha.GachaSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class FileHandler {
    private String outputformat;

    private FileHandler(String format) {
        this.outputformat = format;
    }

    public static FileHandler getInstance(String format) {
        return new FileHandler(format);
    }

    public boolean writeToFile(Object data, String filename) {
        try {
            File file = new File(filename);
        } catch (Exception e) {
        }
        return false;
    }

    public Object readFromFile(String filename) {
        try {
            File file = new File(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean exportToCSV(List<Item> items, String filename) {
        try {
            File file = new File(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean exportToCSV(List<Item> items) {
        try (FileWriter fw = new FileWriter(outputformat)) {
            for (Item item : items) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean exportToTXT(List<Item> items, String filename) {
        try {
            File file = new File(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean exportToTXT(List<Item> items) {
        try {
            File file = new File(outputformat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}

