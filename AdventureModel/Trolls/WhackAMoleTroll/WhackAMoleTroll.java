package AdventureModel.Trolls.WhackAMoleTroll;

import AdventureModel.AdventureGame;
import AdventureModel.Troll;
import AdventureModel.TrollFactory;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Class WhackAMoleTroll. Handles all the necessary task to run the troll game.
 * Implements Troll Interface and Observer Pattern.
 */

public class WhackAMoleTroll implements Troll, MoleEventListener {
    public static boolean gameStatus;
    public int score;
    public MoleEventManager moleEventManager;
    public List<String> requiredItems;


    /**
     * Troll Constructor. Initializes attributes.
     * @throws ClassNotFoundException
     */
    public WhackAMoleTroll() throws ClassNotFoundException {
        this.requiredItems = new ArrayList<>();
        requiredItems.add("SWORD_OF_ELDERS");
        this.score = 0;
        this.moleEventManager = new MoleEventManager();
        this.moleEventManager.subscribe(this);


    }

    /**
     * Overrides moleClicked() from MoleEventListener interface to increment score.
     * */

    @Override
    public void moleClicked() {
        score += 1;
    }

    /**
     * Get a random Mole to show on screen.
     * @return index to choose a random mole.
     */
    public int getMole() {
        Random rand = new Random();
        int num = rand.nextInt(5); //0 to 4 index number;
        return num;
    }

    /**
     * Get required items to go against the troll.
     * @return a string array of all required objects to play the troll.
     */
    @Override
    public List<String> getRequiredItems() {
        return requiredItems;
    }

    /**
     * Play the troll.
     * @param game represents the original game
     * @param destinationRoom room that player enters after defeating the troll
     */

    public void playGame(AdventureGame game, int destinationRoom) {
        WhackAMoleTrollView view = new WhackAMoleTrollView(this);

        view.runShowMole(Duration.seconds(2));


    }

    /**
     * Checks whether player defeated troll or not.
     * @return True if troll was defeated and false otherwise.
     */
    public boolean defeated() {
        return gameStatus;
    }
}

