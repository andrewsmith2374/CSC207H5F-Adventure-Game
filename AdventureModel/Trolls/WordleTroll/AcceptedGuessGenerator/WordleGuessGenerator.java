package AdventureModel.Trolls.WordleTroll.AcceptedGuessGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class WordleGuessGenerator implements AcceptedGuessGenerator {
    private String fileName;
    public WordleGuessGenerator(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public HashSet<String> generate() {
        BufferedReader fr;
        String pathName = "AdventureModel/Trolls/WordleTroll/AcceptedGuessGenerator/" + fileName;
        try {
            fr = new BufferedReader(new FileReader(pathName));
        } catch (FileNotFoundException ignored) {
            throw new RuntimeException("File not found");
        }
        try {
            return readFile(fr);
        } catch (IOException ignored) {
            return null;
        }
    }

    /*
     * Read through a given BufferedReader, adding each line after the first to the output HashSet
     */
    private HashSet<String> readFile(BufferedReader buff) throws IOException {
        HashSet<String> output = new HashSet<String>();
        buff.readLine();
        while(buff.ready()) {
            output.add(buff.readLine().strip().toUpperCase());
        }
        return output;
    }
}
