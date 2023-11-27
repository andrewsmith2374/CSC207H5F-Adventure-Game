package AdventureModel.Trolls.BaseTroll;

import AdventureModel.AdventureGame;
import AdventureModel.Troll;

import java.util.ArrayList;
import java.util.List;

public class BaseTroll implements Troll {
    public BaseTroll() {}

    @Override
    public void playGame(AdventureGame model, int destinationRoom) {
        System.out.println("Game Played!");
        model.player.setCurrentRoom(model.getRooms().get(destinationRoom));
    }

    @Override
    public List<String> getRequiredItems() {
        return new ArrayList<>();
    }

    @Override
    public boolean defeated() { return false; }
}
