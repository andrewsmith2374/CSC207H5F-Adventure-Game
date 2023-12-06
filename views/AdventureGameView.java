package views; /**
 * Since I don't want to pay zoom for just cloud recording one video,
 * thus I just record it and push to the repo as a mp4 file, which 
 * is named as leesze17.mp4
 */

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.Passage;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;
import javafx.scene.Group;
import javafx.scene.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 *
 * ZOOM LINK: <URL HERE>
 * PASSWORD: <PASSWORD HERE>
 */
public class AdventureGameView {

    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton, settingButton, mapButton, fontUpButton, fontDownButton; //buttons
    Button quitButton = new Button(); // for other class
    Button control = new Button(); // control font size
    Boolean helpToggle = false; //is help on display?

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    ScrollPane roomDesc = new ScrollPane();
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField, fontSizTextField; //for user input

    Scene scene;

    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    int fontSize = 16;

    ScrollPane controlPane = new ScrollPane(); // Use for font control
    Button[] buttonList;
    TextField[] textList;

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) {
        this.model = model;
        this.stage = stage;
        intiUI();
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {
        // setting up the stage
        this.stage.setTitle("group83's Adventure Game");

        //FOR LOADING SCREEN
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;"); // Set the background color to black
        Scene scene1 = new Scene(root, 1000, 800);
        int numberOfDots = 5;
        double dotSpacing = 30;
        double movementRange = 50; // The vertical movement range of each dot

        ArrayList<Circle> circles = new ArrayList<Circle>();
        Circle dot1 = new Circle(12, Color.web("#581845"));
        Circle dot2 = new Circle(12, Color.web("#900C3F"));
        Circle dot3 = new Circle(12, Color.web("#C70039"));
        Circle dot4 = new Circle(12, Color.web("#FF5733"));
        Circle dot5 = new Circle(12, Color.web("#FFC300"));

        circles.add(dot1);
        circles.add(dot2);
        circles.add(dot3);
        circles.add(dot4);
        circles.add(dot5);

        // Create and animate each dot
        for (int i = 0; i < numberOfDots; i++) {
            circles.get(i).setCenterX(440 + i * dotSpacing);
            circles.get(i).setCenterY(scene1.getHeight() / 2);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), circles.get(i));
            transition.setByY(movementRange);
            transition.setAutoReverse(true);
            transition.setCycleCount(TranslateTransition.INDEFINITE);
            transition.setDelay(Duration.millis(i * 100)); // Stagger the start of each dot's movement
            transition.play();

            root.getChildren().add(circles.get(i));
        }

        this.stage.setScene(scene1);
        this.stage.setResizable(false);
        this.stage.show();



        PauseTransition startGame= new PauseTransition();
        startGame.setDuration(Duration.seconds(5));











        startGame.setOnFinished(event -> {
            root.getChildren().clear();

            //Inventory + Room items
            objectsInInventory.setSpacing(10);
            objectsInInventory.setAlignment(Pos.TOP_CENTER);
            objectsInRoom.setSpacing(10);
            objectsInRoom.setAlignment(Pos.TOP_CENTER);

            gridPane.setPadding(new Insets(20));
            gridPane.setBackground(new Background(new BackgroundFill(
                    Color.valueOf("#000000"),
                    new CornerRadii(0),
                    new Insets(0)
            )));

            //Three columns, three rows for the GridPane
            ColumnConstraints column1 = new ColumnConstraints(150);
            ColumnConstraints column2 = new ColumnConstraints(650);
            ColumnConstraints column3 = new ColumnConstraints(150);
            column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
            column1.setHgrow( Priority.SOMETIMES );

            // Row constraints
            RowConstraints row1 = new RowConstraints();
            RowConstraints row2 = new RowConstraints( 550 );
            RowConstraints row3 = new RowConstraints();
            row1.setVgrow( Priority.SOMETIMES );
            row3.setVgrow( Priority.SOMETIMES );

            gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
            gridPane.getRowConstraints().addAll( row1 , row2 , row1 );

            // Buttons
            saveButton = new Button("Save");
            saveButton.setId("Save");
            customizeButton(saveButton, 200, 50);
            makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
            addSaveEvent();

            loadButton = new Button("Load");
            loadButton.setId("Load");
            customizeButton(loadButton, 200, 50);
            makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
            addLoadEvent();

            helpButton = new Button("Instructions");
            helpButton.setId("Instructions");
            customizeButton(helpButton, 200, 50);
            makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
            addInstructionEvent();

            fontUpButton = new Button("+");
            fontUpButton.setId("FontUp");
            customizeButton(fontUpButton, 50, 50);
            makeButtonAccessible(fontUpButton, "Font Size Up Button", "This button can adjust font size", "This button will increase the font size of all words in the UI.");
            addFontUpEvent();

            fontDownButton = new Button("-");
            fontDownButton.setId("FontDown");
            customizeButton(fontDownButton, 50, 50);
            makeButtonAccessible(fontDownButton, "Font Size Down Button", "This button can adjust font size", "This button will decrease the font size of all words in the UI.");
            addFontDownEvent();

            fontSizTextField = new TextField(Integer.toString(fontSize));
            fontSizTextField.setId("Font Size");
            fontSizTextField.setAlignment(Pos.CENTER);
            addFontSizeEvent();

            String head = this.model.getDirectoryName();
            String settingIcon = head + "/settings.png";
            ImageView objPic = new ImageView(new Image(new File(settingIcon).toURI().toString()));
            objPic.setFitHeight(40);
            objPic.setFitWidth(40);
            objPic.setAccessibleText("setting");
            settingButton = new Button("", objPic);
            settingButton.setId("Setting");
            settingButton.setStyle("-fx-background-color: #000000");
            customizeButton(settingButton, 50, 50);
            makeButtonAccessible(settingButton, "Setting Button", "This button holds some config of the game", "This buttong holds the setting and config of the game, such as save and load games. Click it to do action on setting.");
            addSettingEvent();

            mapButton = new Button("Map");
            mapButton.setId("Map");
            customizeButton(mapButton, 100, 50);
            makeButtonAccessible(helpButton, "Map Button", "This button can show map.", "This button shows the detail what the player is playing currently. Click to show and move by map.");
            addMapEvent();

            HBox topButtons = new HBox();
            topButtons.getChildren().addAll(mapButton, settingButton);
            topButtons.setSpacing(10);
            topButtons.setAlignment(Pos.CENTER);

            inputTextField = new TextField();
            inputTextField.setFont(new Font("Arial", fontSize));
            inputTextField.setFocusTraversable(true);

            inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
            inputTextField.setAccessibleRoleDescription("Text Entry Box");
            inputTextField.setAccessibleText("Enter commands in this box.");
            inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
            addTextHandlingEvent(); //attach an event to this input field

            //labels for inventory and room items
            Label objLabel =  new Label("Objects in Room");
            objLabel.setAlignment(Pos.CENTER);
            objLabel.setStyle("-fx-text-fill: white;");
            objLabel.setFont(new Font("Arial", 16));

            Label invLabel =  new Label("Your Inventory");
            invLabel.setAlignment(Pos.CENTER);
            invLabel.setStyle("-fx-text-fill: white;");
            invLabel.setFont(new Font("Arial", 16));

            //add all the widgets to the GridPane
            gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
            gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
            gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

            Label commandLabel = new Label("What would you like to do?");
            commandLabel.setStyle("-fx-text-fill: white;");
            commandLabel.setFont(new Font("Arial", 16));

            updateScene(""); //method displays an image and whatever text is supplied
            updateItems(); //update items shows inventory and objects in rooms

            // adding the text area and submit button to a VBox
            VBox textEntry = new VBox();
            textEntry.setStyle("-fx-background-color: #000000;");
            textEntry.setPadding(new Insets(20, 20, 20, 20));
            textEntry.getChildren().addAll(commandLabel, inputTextField);
            textEntry.setSpacing(10);
            textEntry.setAlignment(Pos.CENTER);
            gridPane.add( textEntry, 0, 2, 3, 1 );

            // Font editbale
            buttonList = new Button[] {saveButton, loadButton, mapButton, helpButton, fontDownButton, fontUpButton, quitButton, control};
            // textList = new TextField[] {inputTextField, fontSizTextField};

            // Scroll Pane description
            roomDesc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            roomDesc.setPrefHeight(400);
            roomDesc.setPrefWidth(600);
            roomDesc.setStyle("-fx-background: #000000");
            roomDesc.setFitToWidth(true);

            // Render everything
            scene = new Scene(gridPane,  1000, 800);
            scene.setFill(Color.BLACK);
            
            this.stage.setScene(scene);
            this.stage.setResizable(false);
            this.stage.show();
        });

        startGame.play();

    }


    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", fontSize));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white; -fx-padding: 0");
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the inputTextField attribute 
     *
     * Your event handler should respond when users 
     * hits the ENTER or TAB KEY. If the user hits 
     * the ENTER Key, strip white space from the
     * input to inputTextField and pass the stripped 
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus 
     * of the scene onto any other node in the scene 
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        inputTextField.setOnKeyPressed(event -> keyPressed(event.getCode(), inputTextField.getText()));
    }

    // Helper for addTextHandlingEvent, which when enter is pressed
    private void keyPressed(KeyCode code, String text) {
        if (code == KeyCode.ENTER) {
            inputTextField.setText(null);
            try {
                text.strip();
                submitEvent(text);
            } catch (Exception e) {
                // do nothing
            }
        }
        else if (code == KeyCode.TAB) {
            gridPane.requestFocus();
        }
    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    public void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            updateScene("");
            String roomDescTemp = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) {
                roomDescLabel.setText(roomDescTemp + "\n\nObjects in this room:\n" + objectString);
                roomDesc.setContent(roomDescLabel);
            }
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            updateScene("");
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            updateScene("");
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            // Call update the scene once this section is hit
            updateItems();
            updateScene("");

            // Create a time gap
            PauseTransition gap = new PauseTransition(Duration.seconds(5));
            gap.setOnFinished(event -> {
                // Call recursion
                submitEvent("FORCED");
            });

            // Once updated, call a 5 second gap and submit a recursion in case
            // the desintaion room also have force
            gap.play();
            
        }
    }


    /**
     * showCommands
     * __________________________
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the 
     * current room.
     */
    private void showCommands() {
        String target = "";
        // Iterate through the passage table in the current room
        for (Passage ele : this.model.getPlayer().getCurrentRoom().getMotionTable().getDirection()) {
            // Add all the move into a targeted string
            target += ele.getDirection() + ": " + this.model.getRooms().get(ele.getDestinationRoom()).getRoomName() + "\n";
        }
        // Set the description text become the move
        roomDescLabel.setText(target);
        roomDesc.setContent(roomDescLabel);
    }


    /**
     * updateScene
     * __________________________
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     * 
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {
        if(helpToggle) {
            showInstructions();
        }
        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        roomDescLabel.setStyle("-fx-background-color: #000000;");
        roomDescLabel.setTextFill(Color.WHITE);
        roomDescLabel.setFont(new Font(fontSize));
        VBox roomPane = new VBox(roomImageView, roomDesc);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: #000000;");

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();

        // Set room is visited
        if (this.model.getPlayer().getCurrentRoom().getVisited() == false) {
            this.model.getPlayer().getCurrentRoom().visit();
        }

    }

    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     * 
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDescTemp = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDescTemp + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDescTemp);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", fontSize));
        roomDesc.setContent(roomDescLabel);
        // roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     * __________________________
     *
     * Get the image for the current room and place 
     * it in the roomImageView 
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(new File(roomImage).toURI().toString());
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     *
     * This method is partially completed, but you are asked to finish it off.
     *
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     * 
     * Images of each object are in the assets 
     * folders of the given adventure game.
     */
    public void updateItems() {

        // Scroll pane for object in the current room
        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        // Scroll Pane for object in inventory
        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        gridPane.add(scI,2,1);

        // Vbox for collecting button
        VBox objInInv = new VBox();
        VBox objInRm = new VBox();

        // Set button for obj in inventory
        objectInInventory(null, objInInv, objInRm);
        // Put the vbox into the scroll panel
        scI.setContent(objInInv);

        // Set button for obj in room
        objectInRoom(null, objInInv, objInRm);
        // Put the vbox into the scroll panel
        scO.setContent(objInRm);
    }

    // Helper for updateItems to make button of object in inventory
    private void objectInInventory(String pic, VBox objInInv, VBox objInRm) {
        // Item in inventory
        // Let the loop below loop for once iff it is call by action and there is no object in the inventory
        AdventureObject additional = new AdventureObject(null, null, null);
        boolean check = (pic != null && this.model.getPlayer().inventory.size() == 0);
        if (check) { this.model.getPlayer().inventory.add(additional); }
        // Iterate through players inventory
        for (AdventureObject ele : this.model.getPlayer().inventory) {
            // Object name
            String name = ele.getName();
            // Object image
            String picName = (pic == null) ? this.model.getDirectoryName() + "/objectImages/" + name + ".jpg" : pic;
            //skip if it is repeated
            String tempName = this.model.getDirectoryName() + "/objectImages/" + name + ".jpg";
            if (!tempName.equals(picName)) {
                continue;
            }
            // Basic for the picture
            ImageView objPic = new ImageView(new Image(new File(picName).toURI().toString()));
            objPic.setFitHeight(100);
            objPic.setFitWidth(100);
            objPic.setAccessibleText(name + ", " + ele.getDescription());
            // Set picture to the button
            Button temp = new Button(name + "\n", objPic);
            temp.setContentDisplay(ContentDisplay.TOP);
            // Basic for the button
            makeButtonAccessible(temp, "Object Button", "This button shows a object in player's inventory", "When you click the button, it will disappear from the inventory and drop to the room object");
            temp.setOnAction(e -> {
                objInInv.getChildren().remove(temp);
                this.model.getPlayer().inventory.remove(ele);
                this.model.getPlayer().getCurrentRoom().objectsInRoom.add(ele);
                objectInRoom(picName, objInInv, objInRm);
            });
            objInInv.getChildren().add(temp);
            // Loop only once if it is called by button action
            if (pic != null) { break; }
        }
        // Remove the append item after it is finished
        if (check) { this.model.getPlayer().inventory.remove(additional); }
    }

    // Helper for updateItems to make button of object in room
    private void objectInRoom(String pic, VBox objInInv, VBox objInRm) {
        // Object in Room
        // Let the loop below loop for once iff it is call by action and there is no object in room
        AdventureObject additional = new AdventureObject(null, null, null);
        boolean check = (pic != null && this.model.getPlayer().getCurrentRoom().objectsInRoom.size() == 0);
        if (check) { this.model.getPlayer().getCurrentRoom().objectsInRoom.add(additional); }
        // Iterate through current room obj
        for (AdventureObject ele : this.model.getPlayer().getCurrentRoom().objectsInRoom) {
            // Object name
            String name = ele.getName();
            // Object image
            String picName = (pic == null) ? this.model.getDirectoryName() + "/objectImages/" + name + ".jpg" : pic;
            //skip if it is repeated
            String tempName = this.model.getDirectoryName() + "/objectImages/" + name + ".jpg";
            if (!tempName.equals(picName)) {
                continue;
            }
            // Basic for the picture
            ImageView objPic = new ImageView(new Image(new File(picName).toURI().toString()));
            objPic.setFitHeight(100);
            objPic.setFitWidth(100);
            objPic.setAccessibleText(name + ", " + ele.getDescription());
            // Set picture to the button
            Button temp2 = new Button(name, objPic);
            temp2.setContentDisplay(ContentDisplay.TOP);
            // Basic for the button
            makeButtonAccessible(temp2, "Object Button", "This button shows a object in the current room", "When you click the button, it will disappear from the room and add to player's inventory");
            temp2.setOnAction(e -> {
                objInRm.getChildren().remove(temp2);
                this.model.getPlayer().getCurrentRoom().objectsInRoom.remove(ele);
                this.model.getPlayer().inventory.add(ele);
                objectInInventory(picName, objInInv, objInRm);
            });
            objInRm.getChildren().add(temp2);
            // Loop only once if it is called by button action
            if (pic != null) { break; }
        }
        // Remove the append item after it is finished
        if (check) { this.model.getPlayer().getCurrentRoom().objectsInRoom.remove(additional); }
    }

    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        if (!helpToggle) {
            // Get the help text, aka the instruction
            String helpText = this.model.getInstructions().strip();
            Label instruction = new Label();
            instruction.setText(helpText);
            instruction.setWrapText(true);
            // Set the detail of the label
            instruction.setStyle("-fx-background-color: #000000;");
            instruction.setTextFill(Color.WHITE);
            instruction.setFont(new Font("Arial", fontSize));
            instruction.setAlignment(Pos.CENTER);
            // Scroll Pane
            ScrollPane temp = controlPane;
            temp.setContent(instruction);
            temp.setFitToWidth(true);
            temp.setStyle("-fx-background-color: black;");
            gridPane.add(temp, 1, 1);
            // Set the helpToggle to true
            helpToggle = true;
            // Remove room image and room description
            roomImageView.setImage(null);
            roomDesc.setVisible(false);
        }
        else {
            helpToggle = false;
            roomDesc.setVisible(true);
            gridPane.getChildren().remove(controlPane);
            controlPane = new ScrollPane();
            // Redraw the room image and set the description by updating the whole scene
            updateScene("");
            // Set the toggle to false
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }

    /**
     * This method handles the event related to the
     * setting button.
     */
    public void addSettingEvent() {
        settingButton.setOnAction(e -> {
            gridPane.requestFocus();
            SettingView settingView = new SettingView(this);
        });
    }

    /**
     * This method handles the event related to the
     * map button
     */
    public void addMapEvent() {
        mapButton.setOnAction(e -> {
            gridPane.requestFocus();
            try {
                MapView mapView = new MapView(this);
            } catch (IOException e1) {}
        });
    }

    /**
     * This method handles the event related to the
     * font size button
     */
    public void addFontUpEvent() {
        fontUpButton.setOnAction(e -> {
            gridPane.requestFocus();
            fontSize += 1;
            ErrorView error = new ErrorView(this);
            boolean invalid = error.fontSizeError(fontSize);
            if (!invalid) {
                fontSizTextField.setText(Integer.toString(fontSize));
                fontSizTextField.fireEvent(e);
            }
            else {
                fontSize -= 1;
            }
        });
    }
    
    /**
     * This method handles the event related to the
     * font size button
     */
    public void addFontDownEvent() {
        fontDownButton.setOnAction(e -> {
            gridPane.requestFocus();
            fontSize -= 1;
            ErrorView error = new ErrorView(this);
            boolean invalid = error.fontSizeError(fontSize);
            if (!invalid) {
                fontSizTextField.setText(Integer.toString(fontSize));
                fontSizTextField.fireEvent(e);
            }
            else {
                fontSize += 1;
            }
        });
    }

    /**
     * This method handles the event related to the
     * font size button
     */
    public void addFontSizeEvent() {
        fontSizTextField.setOnAction(e -> {
            gridPane.requestFocus();
            int temp = fontSize;
            try {
                temp = Integer.parseInt(fontSizTextField.getText());
            } catch (Exception error) {
                ErrorView notInt = new ErrorView(this);
                notInt.typeError(true);
            }
            ErrorView error = new ErrorView(this);
            boolean invalid = error.fontSizeError(temp);
            if (!invalid) {
                fontSize = temp;
            }
            else if (temp > 30) {
                fontSize = 30;
            }
            else {
                fontSize = 1;
            }
            fontSizTextField.setText(Integer.toString(fontSize));
            for (Button b : buttonList) {
                b.setFont(Font.font(fontSize));
            }
            roomDescLabel.setFont(Font.font(fontSize));
            roomDesc.setContent(roomDescLabel);
            // Instruction
            Label tempLabel = (Label) controlPane.getContent();
            if (tempLabel != null) {
                tempLabel.setFont(Font.font(fontSize));
                controlPane.setContent(tempLabel);
            }
        });
    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;
    }

    /**
     * This method stops articulations 
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }
}
