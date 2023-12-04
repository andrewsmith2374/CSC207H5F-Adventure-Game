package AdventureModel.Trolls.WordleTroll.AcceptedGuessGenerator;

import java.util.HashSet;

public interface AcceptedGuessGenerator {
    /*
     * Generates a set of all accepted words
     */
    public HashSet<String> generate();
}
