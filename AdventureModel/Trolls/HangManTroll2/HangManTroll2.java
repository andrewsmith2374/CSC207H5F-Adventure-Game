package AdventureModel.Trolls.HangManTroll2;

import AdventureModel.AdventureGame;
import AdventureModel.Troll;

import java.util.*;

// List of words from https://www.hangmanwords.com/words
public class HangManTroll2 implements Troll {
    private final List<String> requiredItems;
    private static boolean isDefeated;

    public HangManTroll2() {
        requiredItems = new ArrayList<>();
        requiredItems.add("SHIP");
        requiredItems.add("WARHAMMER");
    }

    @Override
    public void playGame(AdventureGame model, int destinationRoom) {
        new HangManTrollView2(this);
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
