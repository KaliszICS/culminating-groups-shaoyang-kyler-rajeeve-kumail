package util.fileio;

import entities.items.Item;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class FileHandler {
    public String outputformat;
    public  File file;

    public FileHandler() {
        this.outputFormat = "TXT";
    }
    public FileHandler(String format) {
        this.outputformat = format;
    }

    public FileHandler FileHandler(String format) {
        return new FileHandler(format);
    }

    public boolean writeToFile(Object data, String filename) {
        if (data == null || filename == null || filename.isEmpty())
            return false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(data.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Object readFromFile(String filename) {
        try {
            File file = new File(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean exportToCSV(List<String[]> data, String filename) {
        try {
            File file = new File(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean exportToCSV(List<Item> items) {
        try {
            File file = new File("CSV");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean exportToTXT(String data, String filename) {
        try {
            File file = new File(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean exportToTXT(List<Item> items) {
        try  {
            File file = new File("TXT");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
