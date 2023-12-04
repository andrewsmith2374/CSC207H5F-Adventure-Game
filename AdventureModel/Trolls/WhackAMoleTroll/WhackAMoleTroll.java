package AdventureModel.Trolls.WhackAMoleTroll;

import AdventureModel.AdventureGame;
import AdventureModel.Troll;
import AdventureModel.TrollFactory;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;




public class WhackAMoleTroll implements Troll, MoleEventListener {
    public static boolean gameStatus;
    public int score;
    private ScheduledExecutorService scheduler;
    public MoleEventManager moleEventManager;
    public List<String> requiredItems;


    public WhackAMoleTroll() throws ClassNotFoundException {
        this.requiredItems = new ArrayList<>();
        this.score = 0;
        this.moleEventManager = new MoleEventManager();
        this.moleEventManager.subscribe(this);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

    }

    @Override
    public void moleClicked() {
        score += 1;
    }

    public int getMole() {
        Random rand = new Random();
        int num = rand.nextInt(5); //0 to 4 index number;
        return num;
    }

    @Override
    public List<String> getRequiredItems() {
        return requiredItems;
    }

    public void playGame(AdventureGame game, int destinationRoom) {
        WhackAMoleTrollView view = new WhackAMoleTrollView(this);
        view.runShowMole(Duration.seconds(2));

    }

    public boolean defeated() {
        return gameStatus;
    }
}

