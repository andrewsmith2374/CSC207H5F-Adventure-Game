package AdventureModel.Trolls.WordleTroll.SecretWordGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.random.RandomGenerator;

public class WordleSecretWordGenerator implements SecretWordGenerator{
    BufferedReader bufferedReader;
    public WordleSecretWordGenerator(BufferedReader buff) {
        bufferedReader = buff;
    }

    @Override
    public String generate() {
        Random random = new Random();
        int wordIndex = random.nextInt(2316);
        try {
            for (int i = 0; i < wordIndex; i++) {
                bufferedReader.readLine();
            }
            return bufferedReader.readLine().strip().toUpperCase();
        } catch(IOException ignored) {
            throw new RuntimeException("Reached end of file");
        }
    }
}
