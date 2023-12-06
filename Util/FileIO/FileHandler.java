package Util.FileIO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileHandler {
    public FileHandler() {}

    public BufferedReader getBufferedReader(String path) {
        try {
            return new BufferedReader(new FileReader(path));
        } catch(FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
