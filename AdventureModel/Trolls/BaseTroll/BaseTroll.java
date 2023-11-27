package AdventureModel.Trolls.BaseTroll;

import AdventureModel.Troll;

import java.util.ArrayList;
import java.util.List;

public class BaseTroll implements Troll {
    public BaseTroll() {}

    @Override
    public void playGame() {
        System.out.println("Game Played!");
    }

    @Override
    public List<String> getRequiredItems() {
        return new ArrayList<>();
    }

    @Override
    public boolean defeated() { return false; }
}
