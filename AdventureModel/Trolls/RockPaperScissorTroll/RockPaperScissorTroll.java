package AdventureModel.Trolls.RockPaperScissorTroll;

import AdventureModel.AdventureGame;

import AdventureModel.Troll;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;

/**
 * Class RockPaperScissorTroll implements Troll interface
 */

public class RockPaperScissorTroll implements Troll {

    public static boolean gameStat;
    public String helpText;
    private List<String> options;
    private List<String> requiredItems;

    /**
     * Class constructor. Initialize attributes
     * @throws ClassNotFoundException
     * @throws IOException
     */

    public RockPaperScissorTroll() throws ClassNotFoundException, IOException {
        this.options = new ArrayList<>();
        this.requiredItems = new ArrayList<>();
        requiredItems.add("SWORD_OF_ELDERS");
        options.add("rock");
        options.add("paper");
        options.add("scissor");
        this.helpText = "";
        parseHelp();

    }

    /**
     * Get help text from help.txt file.
     */

    public void parseHelp() throws IOException {
        String fileName = "AdventureModel/Trolls/RockPaperScissorTroll/help.txt";
        BufferedReader buff = new BufferedReader(new FileReader(fileName));
        String line = buff.readLine();
        while (line != null) {
            helpText = helpText + line + "\n";
            line = buff.readLine();
        }
    }

    /**
     * Get a random computer choice
     * @return Random choice to represent computers move.
     */
    public String computerChoice() {
        Random rand = new Random();
        int index = rand.nextInt(3);
        return options.get(index);
        // these will be set as id's to choose what image will be put on;
    }

    /**
     * Check if player wins by comparing moves.
     * @param player
     * @param compPlay
     * @return True is player's
     */
    public boolean win(String player, String compPlay) {
        if(compPlay == "rock" && player == "scissor") {
            gameStat = true;
            return true;
        }
        else if(compPlay == "scissor" && player == "paper") {
            gameStat = true;
            return true;
        }
        else if(compPlay == "paper" && player == "rock") {
            gameStat = true;
            return true;
        }
        else {
            gameStat = false;
            return false;
        }
    }

    /**
     * Play the game
     * @param model
     * @param destinationRoom
     */
    public void playGame(AdventureGame model, int destinationRoom) {
        RockPaperScissorTrollView view = new RockPaperScissorTrollView(this);

    }

    /**
     * Determine if player defeated the troll or not.
     * @return
     */

    @Override
    public boolean defeated() {
        return gameStat;
    }

    /**
     * Get objects needed to play the game.
     * @return a string array of all objects needs to go against the troll
     */
    @Override
    public List<String> getRequiredItems() {
        return requiredItems;
    }
}
