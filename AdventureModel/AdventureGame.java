package AdventureModel;

import java.io.*;
import java.util.*;

/**
 * Class AdventureGame.  Handles all the necessary tasks to run the Adventure game.
 */
public class AdventureGame implements Serializable {
    private final String directoryName; //An attribute to store the Introductory text of the game.
    private String helpText; //A variable to store the Help text of the game. This text is displayed when the user types "HELP" command.
    private final HashMap<Integer, Room> rooms; //A list of all the rooms in the game.
    private HashMap<String,String> synonyms = new HashMap<>(); //A HashMap to store synonyms of commands.
    private final String[] actionVerbs = {"QUIT","INVENTORY","TAKE","DROP"}; //List of action verbs (other than motions) that exist in all games. Motion vary depending on the room and game.
    public Player player; //The Player of the game.
    private final TrollFactory trollFactory;

    /**
     * Adventure Game Constructor
     * __________________________
     * Initializes attributes
     *
     * @param name the name of the adventure
     */
    public AdventureGame(String name){
        this.synonyms = new HashMap<>();
        this.rooms = new HashMap<>();
        this.directoryName = "Games/" + name; //all games files are in the Games directory!
        this.trollFactory = new TrollFactory();
        try {
            setUpGame();
        } catch (IOException e) {
            throw new RuntimeException("An Error Occurred: " + e.getMessage());
        }
    }

    /**
     * Save the current state of the game to a file
     * 
     * @param file pointer to file to write to
     */
    public void saveModel(File file) {
        try {
            FileOutputStream outfile = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(outfile);
            oos.writeObject(this);
        } catch (IOException ignored) {
            throw new RuntimeException();
        }
    }

    /**
     * setUpGame
     * __________________________
     *
     * @throws IOException in the case of a file I/O error
     */
    public void setUpGame() throws IOException {

        String directoryName = this.directoryName;
        AdventureLoader loader = new AdventureLoader(this, directoryName);
        loader.loadGame();

        // set up the player's current location
        this.player = new Player(this.rooms.get(1));
    }

    /**
     * tokenize
     * __________________________
     *
     * @param input string from the command line
     * @return a string array of tokens that represents the command
     */
    public String[] tokenize(String input){

        input = input.toUpperCase();
        String[] commandArray = input.split(" ");

        int i = 0;
        while (i < commandArray.length) {
            if(this.synonyms.containsKey(commandArray[i])){
                commandArray[i] = this.synonyms.get(commandArray[i]);
            }
            i++;
        }
        return commandArray;

    }

    /**
     * movePlayer
     * __________________________
     * Moves the player in the given direction, if possible.
     * Return false if the player wins or dies as a result of the move.
     *
     * @param direction the move command
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    public boolean movePlayer(String direction) {
        direction = direction.toUpperCase();
        Room currentRoom = player.getCurrentRoom();
        // currentRoom.visit();
        PassageTable motionTable = currentRoom.getMotionTable(); //where can we move?
        if (!motionTable.optionExists(direction)) { return true; } //no move
        ArrayList<Passage> possibilities = getPossibilities(direction, motionTable);

        Passage chosen = checkPassages(possibilities);
        if (chosen == null) return true; //doh, we just can't move.
        changeRoom(chosen);

        return !this.player.getCurrentRoom().getMotionTable().getDirection().get(0).getDirection().equals("FORCED");
    }

    /*
     * Return an ArrayList of Passages that correspond to the given direction from motionTable
     */
    private static ArrayList<Passage> getPossibilities(String direction, PassageTable motionTable) {
        ArrayList<Passage> possibilities = new ArrayList<>();
        for (Passage entry : motionTable.getDirection()) {
            if (entry.getDirection().equals(direction)) { //this is the right direction
                possibilities.add(entry); // are there possibilities?
            }
        }
        return possibilities;
    }

    /*
     * Return the first valid passage in possibilities
     * Return null if the player cannot move
     */
    private Passage checkPassages(ArrayList<Passage> possibilities) {
        // try the blocked passages first
        for (Passage entry : possibilities) {
            System.out.println(entry.getIsBlocked());
            System.out.println(entry.getKeyName());

            if (entry.getIsBlocked()) {
                try {
                    checkForTroll(entry);
                } catch(InterruptedException e) {
                    return entry; // Troll already defeated
                }
                catch(ClassNotFoundException ignored) {}
                boolean hasRequiredItems = this.player.getInventory().contains(entry.getKeyName());
                if (hasRequiredItems) { return entry; }
            } else { return entry; } //the passage is unlocked
        }
        return null;
    }


    /*
     * Set the player's current room to chosen
     */
    private void changeRoom(Passage chosen) {
        int roomNumber = chosen.getDestinationRoom();
        Room room = this.rooms.get(roomNumber);
        // room.visit();
        player.setCurrentRoom(room);
    }

    /*
     * Check for a Troll in the given passage
     * If there is no Troll or the player does not have the required item, throw a ClassNotFoundException
     */
    private void checkForTroll(Passage entry) throws ClassNotFoundException, InterruptedException {
        String name = entry.getKeyName();
        Troll troll = trollFactory.createTroll(name);
        List<String> requiredItems = troll.getRequiredItems();
        for(String item : requiredItems) {
            if(!this.player.getInventory().contains(item)) {
                throw new ClassNotFoundException();
            }
        }
        if(troll.defeated()) { throw new InterruptedException("Troll defeated"); }
        troll.playGame(this, entry.getDestinationRoom());
    }

    /**
     * interpretAction
     * interpret the user's action.
     *
     * @param command String representation of the command.
     */
    public String interpretAction(String command){

        String[] inputArray = tokenize(command); //look up synonyms

        PassageTable motionTable = this.player.getCurrentRoom().getMotionTable(); //where can we move?

        if (motionTable.optionExists(inputArray[0])) {
            if (!movePlayer(inputArray[0])) {
                if (this.player.getCurrentRoom().getMotionTable().getDirection().get(0).getDestinationRoom() == 0)
                    return "GAME OVER";
                else return "FORCED";
            } //something is up here! We are dead or we won.
            return null;
        } else if(Arrays.asList(this.actionVerbs).contains(inputArray[0])) {
            if(inputArray[0].equals("QUIT")) { return "GAME OVER"; } //time to stop!
            else if(inputArray[0].equals("INVENTORY") && this.player.getInventory().size() == 0) return "INVENTORY IS EMPTY";
            else if(inputArray[0].equals("INVENTORY") && this.player.getInventory().size() > 0) return "THESE OBJECTS ARE IN YOUR INVENTORY:\n" + this.player.getInventory().toString();
            else if(inputArray[0].equals("TAKE") && inputArray.length < 2) return "THE TAKE COMMAND REQUIRES AN OBJECT";
            else if(inputArray[0].equals("DROP") && inputArray.length < 2) return "THE DROP COMMAND REQUIRES AN OBJECT";
            else if(inputArray[0].equals("TAKE") && inputArray.length == 2) {
                if(this.player.getCurrentRoom().checkIfObjectInRoom(inputArray[1])) {
                    this.player.takeObject(inputArray[1]);
                    return "YOU HAVE TAKEN:\n " + inputArray[1];
                } else {
                    return "THIS OBJECT IS NOT HERE:\n " + inputArray[1];
                }
            }
            else if(inputArray[0].equals("DROP") && inputArray.length == 2) {
                if(this.player.checkIfObjectInInventory(inputArray[1])) {
                    this.player.dropObject(inputArray[1]);
                    return "YOU HAVE DROPPED:\n " + inputArray[1];
                } else {
                    return "THIS OBJECT IS NOT IN YOUR INVENTORY:\n " + inputArray[1];
                }
            }
        }
        return "INVALID COMMAND.";
    }

    /**
     * getDirectoryName
     * __________________________
     * Getter method for directory 
     * @return directoryName
     */
    public String getDirectoryName() {
        return this.directoryName;
    }

    /**
     * getInstructions
     * __________________________
     * Getter method for instructions 
     * @return helpText
     */
    public String getInstructions() {
        return helpText;
    }

    /**
     * getPlayer
     * __________________________
     * Getter method for Player 
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * getRooms
     * __________________________
     * Getter method for rooms 
     * @return map of key value pairs (integer to room)
     */
    public HashMap<Integer, Room> getRooms() {
        return this.rooms;
    }

    /**
     * getSynonyms
     * __________________________
     * Getter method for synonyms 
     * @return map of key value pairs (synonym to command)
     */
    public HashMap<String, String> getSynonyms() {
        return this.synonyms;
    }

    /**
     * setHelpText
     * __________________________
     * Setter method for helpText
     * @param help which is text to set
     */
    public void setHelpText(String help) {
        this.helpText = help;
    }


}
