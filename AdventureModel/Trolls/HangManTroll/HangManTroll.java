package AdventureModel.Trolls.HangManTroll;

import AdventureModel.AdventureGame;
import AdventureModel.Troll;

import java.util.*;

// List of words from https://www.hangmanwords.com/words
public class HangManTroll implements Troll {
    private final List<String> requiredItems;
    private static boolean isDefeated;

    public HangManTroll() {
        requiredItems = new ArrayList<>();
        requiredItems.add("SWORD_OF_ELDERS");

    }

    @Override
    public void playGame(AdventureGame model, int destinationRoom) {
        new HangManTrollView(this);
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
     * Ends the game
     */
    public void endGame(boolean gameWon) {
        if (gameWon) {
            isDefeated = true;
        }
    }
}
