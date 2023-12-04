package Testing;

import AdventureModel.Trolls.WordleTroll.AcceptedGuessGenerator.AcceptedGuessGenerator;
import AdventureModel.Trolls.WordleTroll.AcceptedGuessGenerator.WordleAcceptedGuessGenerator;
import AdventureModel.Trolls.WordleTroll.SecretWordGenerator.SecretWordGenerator;
import AdventureModel.Trolls.WordleTroll.SecretWordGenerator.TestGenerator;
import AdventureModel.Trolls.WordleTroll.SecretWordGenerator.WordleSecretWordGenerator;
import AdventureModel.Trolls.WordleTroll.WordleTroll;
import FileIO.FileHandler;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.nio.file.LinkPermission;
import java.util.HashSet;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

public class WordleTrollTests {
    @Test
    void testInit() {
        WordleTroll wt = new WordleTroll();
        assertEquals(wt.guesses.length, 4);
    }

    @Test
    void testGetRequiredItems() {
        WordleTroll wt = new WordleTroll();
        assertEquals(wt.getRequiredItems().size(), 0);
    }

    @Test
    void testSecretWordGenerator() {
        String pathName = "AdventureModel/Trolls/WordleTroll/SecretWordGenerator/possible_answers.txt";
        BufferedReader buff = new FileHandler().getBufferedReader(pathName);
        SecretWordGenerator swg = new WordleSecretWordGenerator(buff);
        assertEquals(5, swg.generate().length());
    }

    @Test
    void testAcceptedGuessGenerator() {
        String pathName = "Testing/test_accepted_guesses.txt";
        BufferedReader buff = new FileHandler().getBufferedReader(pathName);
        AcceptedGuessGenerator agg = new WordleAcceptedGuessGenerator(buff);
        HashSet<String> result = agg.generate();
        assertEquals(result.size(), 5);
        assertTrue(result.contains("SCOWL"));
        assertTrue(result.contains("WAGER"));
        assertTrue(result.contains("TYING"));
        assertTrue(result.contains("SWARM"));
        assertTrue(result.contains("SHADY"));
    }

    @Test
    void testSubmitGuess() {
        WordleTroll wt = new WordleTroll();
        String guess = "CRANE";
        wt.submitGuess(guess);
        assertEquals(wt.guesses[0], guess);
    }

    @Test
    void testSubmitGuessInvalid() {
        WordleTroll wt = new WordleTroll();
        String guess = "LKJSD";
        try {
            wt.submitGuess(guess);
            fail();
        } catch(InputMismatchException ignored) {}
    }

    @Test
    void testSubmitGuessRepeat() {
        WordleTroll wt = new WordleTroll();
        String guess = "CRANE";
        try {
            wt.submitGuess(guess);
            wt.submitGuess(guess);
            fail();
        } catch(InputMismatchException ignored) {}
    }

    @Test
    void testCheckGuessGrey() {
        SecretWordGenerator tg = new TestGenerator();
        WordleTroll wt = new WordleTroll();
        wt.setWordGenerator(tg);
        String guess = "WEARY";
        wt.submitGuess(guess);
        assertEquals(wt.checkGuess(0), "00000");
    }

    @Test
    void testCheckGuessCorrect() {
        SecretWordGenerator tg = new TestGenerator();
        WordleTroll wt = new WordleTroll();
        wt.setWordGenerator(tg);
        String guess = "STING";
        wt.submitGuess(guess);
        assertEquals(wt.checkGuess(0), "22222");
    }

    @Test
    void testCheckGuessYellow() {
        SecretWordGenerator tg = new TestGenerator();
        WordleTroll wt = new WordleTroll();
        wt.setWordGenerator(tg);
        String guess = "PIOUS";
        wt.submitGuess(guess);
        assertEquals(wt.checkGuess(0), "01001");
    }

    @Test
    void testCheckGuessGreen() {
        SecretWordGenerator tg = new TestGenerator();
        WordleTroll wt = new WordleTroll();
        wt.setWordGenerator(tg);
        String guess = "CRANE";
        wt.submitGuess(guess);
        assertEquals(wt.checkGuess(0), "00020");
    }

    @Test
    void testGetInstructions() {
        WordleTroll wt = new WordleTroll();
        assertEquals(wt.getInstructions(), "");
    }
}
