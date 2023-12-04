package AdventureModel.Trolls.RockPaperScissorTroll;

import AdventureModel.AdventureGame;
import AdventureModel.Room;
import AdventureModel.Troll;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RockPaperScissorTroll implements Troll {

    public static boolean gameStat;
    public String helpText;
    private List<String> options;
    private List<String> requiredItems;

    public RockPaperScissorTroll() throws ClassNotFoundException, IOException {
        this.options = new ArrayList<>();
        this.requiredItems = new ArrayList<>();
        options.add("rock");
        options.add("paper");
        options.add("scissor");
        this.helpText = "";
        parseHelp();

    }

    public void parseHelp() throws IOException {
        String fileName = "AdventureModel/Trolls/RockPaperScissorTroll/help.txt";
        BufferedReader buff = new BufferedReader(new FileReader(fileName));
        String line = buff.readLine();
        while (line != null) {
            helpText = helpText + line + "\n";
            line = buff.readLine();
        }
    }
    public String computerChoice() {
        Random rand = new Random();
        int index = rand.nextInt(3);
        return options.get(index);
        // these will be set as id's to choose what image will be put on;
    }
    public boolean win(String player, String compPlay) {
        if(Objects.equals(player, compPlay)) {
            gameStat = true;
            return true;
        }
        else {
            return false;
        }
    }
    public void playGame(AdventureGame model, int destinationRoom) {
        RockPaperScissorTrollView view = new RockPaperScissorTrollView(this);


    }

    @Override
    public boolean defeated() {
        return gameStat;
    }

    @Override
    public List<String> getRequiredItems() {
        return requiredItems;
    }
}
