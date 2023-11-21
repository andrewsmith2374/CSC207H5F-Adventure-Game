package AdventureModel.Trolls.WordleTroll;

import AdventureModel.Troll;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;

// List of words from https://github.com/Kinkelin/WordleCompetition
public class WordleTroll implements Troll {
    public String[] guesses;
    private WordleTrollView view;
    private String secretWord;
    private HashSet<String> acceptedGuesses;

    public WordleTroll() {
        secretWord = generateSecretWord("possible_answers.txt");
        acceptedGuesses = generateAcceptedGuesses("accepted_guesses.txt");
    }

    @Override
    public boolean playGame() {
        throw new UnsupportedOperationException("Implemented playGame");
    }

    @Override
    public List<String> getRequiredItems() {
        throw new UnsupportedOperationException("Implemented getRequiredItems");
    }

    /*
     * Take a given string and store in guesses if the string is an accepted guess
     * If the string is not accepted, throw an InputMismatchException
     */
    public void submitGuess(String guess) throws InputMismatchException {
        throw new UnsupportedOperationException("Implement submitGuess");
    }

    /*
     * Check the guess at given index
     * Return a string of length 5. Each character will either be a 0, 1, or 2.
     * 0 means the character is not in the secret word
     * 1 means the character is in the secret word at an unknown location
     * 2 means the character is in the secret word at the given location
     */
    public String checkGuess(int index) { throw new UnsupportedOperationException("Implement checkGuess"); }

    /*
     * Generates a random secret word for the WordleTroll from a given file
     */
    private String generateSecretWord(String fileName) {
        throw new UnsupportedOperationException("Implement generateSecretWord");
    }

    /*
     * Generates a set of all accepted words from a given file
     */
    private HashSet<String> generateAcceptedGuesses(String fileName) {
        throw new UnsupportedOperationException("Implement generateAcceptedGuesses");
    }
}
