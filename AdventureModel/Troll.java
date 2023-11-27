package AdventureModel;

import java.util.List;

public interface Troll {
    /*
     * Plays a Troll game in a new window, returning 1 if the player is successful and 0 otherwise
     */
    void playGame();

    /*
     * Returns a list of Strings where each string is the name of an AdventureObject that is required
     * for the player to go against the Troll
     */
    List<String> getRequiredItems();
}
