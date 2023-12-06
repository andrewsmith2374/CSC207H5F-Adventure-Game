package AdventureModel.Trolls.WordleTroll;

import AdventureModel.AdventureGame;
import AdventureModel.Troll;
import AdventureModel.Trolls.WordleTroll.AcceptedGuessGenerator.AcceptedGuessGenerator;
import AdventureModel.Trolls.WordleTroll.AcceptedGuessGenerator.WordleAcceptedGuessGenerator;
import AdventureModel.Trolls.WordleTroll.SecretWordGenerator.SecretWordGenerator;
import AdventureModel.Trolls.WordleTroll.SecretWordGenerator.WordleSecretWordGenerator;
import Util.FileIO.FileHandler;

import java.io.BufferedReader;
import java.util.*;

// List of words from https://github.com/Kinkelin/WordleCompetition
public class WordleTroll implements Troll {
    public String[] guesses;
    private SecretWordGenerator wordGenerator;
    private AcceptedGuessGenerator guessGenerator;
    private String secretWord;
    private final HashSet<String> acceptedGuesses;
    private final String instructions;
    private final List<String> requiredItems;
    private int currentGuess;
    private static boolean isDefeated;

    public WordleTroll() {
        guesses = new String[4];
        FileHandler fileHandler = new FileHandler();
        createWordGenerator(fileHandler);
        secretWord = wordGenerator.generate();
        createGuessGenerator(fileHandler);
        acceptedGuesses = guessGenerator.generate();
        instructions = "Guess a 5 letter secret word. With each guess, the game will let you know how close you were to" +
                " the secret word! Grey means the letter is not in the secret word, yellow means it's in the word but in" +
                " the wrong spot, and green means you got the right letter in the right spot.";
        requiredItems = populateRequiredItems();
        currentGuess = 0;
    }

    private List<String> populateRequiredItems() {
        final List<String> requiredItems;
        requiredItems = new ArrayList<>();
        requiredItems.add("BLADE_OF_DELIVERANCE");
        requiredItems.add("GLAMDRING");
        requiredItems.add("SWORD_OF_ELDERS");
        requiredItems.add("SWORD_OF_SANCTUARY");
        requiredItems.add("WARHAMMER");
        return requiredItems;
    }

    /*
     * Creates the default SecretWordGenerator
     */
    private void createWordGenerator(FileHandler fileHandler) {
        String path = "AdventureModel/Trolls/WordleTroll/SecretWordGenerator/possible_answers.txt";
        BufferedReader buff = fileHandler.getBufferedReader(path);
        wordGenerator = new WordleSecretWordGenerator(buff);
    }

    /*
     * Creates the default AcceptedGuessGenerator
     */
    private void createGuessGenerator(FileHandler fileHandler) {
        String path = "AdventureModel/Trolls/WordleTroll/AcceptedGuessGenerator/accepted_guesses.txt";
        BufferedReader buff = fileHandler.getBufferedReader(path);
        guessGenerator = new WordleAcceptedGuessGenerator(buff);
    }

    @Override
    public void playGame(AdventureGame model, int destinationRoom) {
        new WordleTrollView(this);
    }

    @Override
    public List<String> getRequiredItems() {
        return requiredItems;
    }

    @Override
    public boolean defeated() {
        return isDefeated;
    }

    /*
     * Take a given UPPERCASE string of length 5 and store in guesses if the string is an accepted guess
     * If the string is not accepted, throw an InputMismatchException
     */
    public void submitGuess(String guess) throws InputMismatchException {
        if(!acceptedGuesses.contains(guess)) {
            throw new InputMismatchException("Not a word!");
        }
        for(String prevGuess : guesses) {
            if(Objects.equals(prevGuess, guess)) {
                throw new InputMismatchException("Already tried!");
            }
        }
        guesses[currentGuess] = guess;
        currentGuess++;
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
            String letter = guess.substring(i, i + 1);
            String secretWordLetter = secretWord.substring(i, i + 1);
            if(!secretWord.contains(letter)) {
                output.append(0);
            } else if(letter.equals(secretWordLetter)) {
                output.append("2");
            } else {
                output.append("1");
            }
        }
        return String.valueOf(output);
    }

    public String getInstructions() { return instructions; }

    public int getCurrentGuess() { return currentGuess; }

    public void setWordGenerator(SecretWordGenerator generator) {
        wordGenerator = generator;
        secretWord = wordGenerator.generate();
    }

    /*
     * Ends the game
     */
    public void endGame(boolean gameWon) {
        if (gameWon) {
            isDefeated = true;
        }
    }
}
