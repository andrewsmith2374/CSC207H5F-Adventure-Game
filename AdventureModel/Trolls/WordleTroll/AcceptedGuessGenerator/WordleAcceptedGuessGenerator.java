package AdventureModel.Trolls.WordleTroll.AcceptedGuessGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;

public class WordleAcceptedGuessGenerator implements AcceptedGuessGenerator {
    private BufferedReader bufferedReader;
    public WordleAcceptedGuessGenerator(BufferedReader buff) {
        bufferedReader = buff;
    }

    @Override
    public HashSet<String> generate() {
        HashSet<String> output = new HashSet<String>();
        try {
            bufferedReader.readLine();
            while (bufferedReader.ready()) {
                output.add(bufferedReader.readLine().strip().toUpperCase());
            }
            return output;
        } catch (IOException ignored) {
            throw new RuntimeException();
        }
    }
}
