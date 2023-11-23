package AdventureModel.Trolls.WordleTroll;

import AdventureModel.Troll;
import AdventureModel.Trolls.WordleTroll.AcceptedGuessGenerator.AcceptedGuessGenerator;
import AdventureModel.Trolls.WordleTroll.AcceptedGuessGenerator.WordleGuessGenerator;
import AdventureModel.Trolls.WordleTroll.SecretWordGenerator.SecretWordGenerator;
import AdventureModel.Trolls.WordleTroll.SecretWordGenerator.WordleSecretWordGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;

// List of words from https://github.com/Kinkelin/WordleCompetition
public class WordleTroll implements Troll {
    public String[] guesses;
    private SecretWordGenerator wordGenerator;
    private AcceptedGuessGenerator guessGenerator;
    private WordleTrollView view;
    private String secretWord;
    private HashSet<String> acceptedGuesses;
    private String instructions;
    private List<String> requiredItems;
    private int currentGuess;
    private int gameStatus;

    public WordleTroll() {
        this(new WordleSecretWordGenerator("possible_answers.txt"),
                new WordleGuessGenerator("accepted_guesses.txt"));
    }

    public WordleTroll(SecretWordGenerator secretWordGenerator) {
        this(secretWordGenerator, new WordleGuessGenerator("accepted_guesses.txt"));
    }

    public WordleTroll(AcceptedGuessGenerator acceptedGuessGenerator) {
        this(new WordleSecretWordGenerator("possible_answers.txt"), acceptedGuessGenerator);
    }

    public WordleTroll(SecretWordGenerator secretWordGenerator, AcceptedGuessGenerator acceptedGuessGenerator) {
        guesses = new String[5];
        wordGenerator = secretWordGenerator;
        secretWord = wordGenerator.generate();
        guessGenerator = acceptedGuessGenerator;
        acceptedGuesses = acceptedGuessGenerator.generate();
        instructions = ""; // TODO: Add instructions
        requiredItems = new ArrayList<String>(); // TODO: update to required item
        currentGuess = 0;

        gameStatus = 0;
    }

    @Override
    public boolean playGame() {
        view = new WordleTrollView(this);
        while(gameStatus == 0) {
            // Do nothing
        }
        return gameStatus == 1;
    }

    @Override
    public List<String> getRequiredItems() {
        return requiredItems;
    }

    /*
     * Take a given string of length 5 and store in guesses if the string is an accepted guess
     * If the string is not accepted, throw an InputMismatchException
     */
    public void submitGuess(String guess) throws InputMismatchException {
        if(acceptedGuesses.contains(guess)) {
            guesses[currentGuess] = guess;
            currentGuess++;
        }
        throw new InputMismatchException("Not a word!");
    }

    /*
     * Check the guess at given index
     * Return a string of length 5. Each character will either be a 0, 1, or 2.
     * 0 means the character is not in the secret word
     * 1 means the character is in the secret word at an unknown location
     * 2 means the character is in the secret word at the given location
     */
    public String checkGuess(int index) {
        StringBuilder output = new StringBuilder();
        String guess = guesses[index];
        for(int i = 0; i < guess.length(); i++) {
            String letter = guess.substring(i);
            if(!secretWord.contains(letter)) {
                output.append(0);
            } else if(letter.equals(secretWord.substring(i))) {
                output.append("2");
            } else {
                output.append("1");
            }
        }
        return String.valueOf(output);
    }

    public String getInstructions() { return instructions; }
}
