package AdventureModel.Trolls.WordleTroll;

import AdventureModel.Troll;

import java.util.HashSet;
import java.util.List;

// List of words from https://github.com/Kinkelin/WordleCompetition
public class WordleTroll implements Troll {
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
