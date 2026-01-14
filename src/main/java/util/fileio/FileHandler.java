package util.fileio;

import entities.items.Item;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;


/**
 * The  Filehandler of utilities, the purpose of filehandler is to create,read, write and export to CVS and TXT
 * @author Rajeeve Ravi
 *  @version 1.4
 */
public class FileHandler {
    /**
     * The Outputformat.
     */
    public String outputformat;
    /**
     * The File.
     */
    public  File file;

    /**
     * Constructor of FileHandler, for TXT
     */
    public FileHandler() {
        this.outputformat = "TXT";
    }

    /**
     * Constructor of new FileHandler, for format
     *
     * @param format the format
     */
    public FileHandler(String format) {
        this.outputformat = format;
    }

    /**
     * File handler file handler.
     *
     * @param format the format
     * @return the file handler
     */
    public FileHandler FileHandler(String format) {
        return new FileHandler(format);
    }

    /**
     * Write to file boolean.
     *
     * @param data     the data
     * @param filename the filename
     * @return the boolean (true)
     */
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

    /**
     * Read from file object.
     *
     * @param filename the filename
     * @return the object(null)
     */
    public Object readFromFile(String filename) {
        try {
            File file = new File(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Export to csv boolean.
     *
     * @param data     the data
     * @param filename the filename
     * @return the boolean (false)
     */
    public boolean exportToCSV(List<String[]> data, String filename) {
        try {
            File file = new File(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Export to csv boolean.
     *
     * @param items the items
     * @return the boolean (false)
     */
    public boolean exportToCSV(List<Item> items) {
        try {
            File file = new File("CSV");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Export to txt boolean.
     *
     * @param data     the data
     * @param filename the filename
     * @return the boolean (false)
     */
    public boolean exportToTXT(String data, String filename) {
        try {
            File file = new File(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Export to txt boolean.
     *
     * @param items the items
     * @return the boolean (false)
     */
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
