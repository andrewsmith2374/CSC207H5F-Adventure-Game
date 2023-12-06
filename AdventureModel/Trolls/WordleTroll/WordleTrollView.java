package AdventureModel.Trolls.WordleTroll;

import Util.UIHelpers.UIHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;

public class WordleTrollView {
    private final WordleTroll model;
    private final UIHelper uiHelper;
    private final Stage stage;
    private GridPane gridPane;
    private Label helpLabel;
    private TextField inputTextField;
    private Label commandLabel;
    private final ArrayList<ArrayList<VBox>> guessFields;
    private Boolean helpToggle = false; //is help on display?

    public WordleTrollView(WordleTroll model) {
        this.model = model;
        uiHelper = new UIHelper();
        stage = new Stage();
        guessFields = new ArrayList<>(4);
        initUI();
    }

    /*
     * Create all UI present at the start of the game
     */
    private void initUI() {
        int colWidth = 100;
        stage.setTitle("Wordle!");
        createGridPane();
        createColConstraints(colWidth);
        createRowConstraints(colWidth);
        createHelpButton(colWidth);
        createGuessFields(colWidth);
        createInputField();
        initiateScene();
    }

    /*
     * Set up background and initialize the Scene
     */
    private void initiateScene() {
        var scene = new Scene(gridPane,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    /*
     * Create the inputTextField and associated command label
     */
    private void createInputField() {
        inputTextField = new TextField();
        commandLabel = new Label("Input Your Guess!");
        commandLabel.setStyle("-fx-text-fill: white;");
        commandLabel.setFont(new Font("Arial", 16));
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        addTextHandlingEvent();
        gridPane.add(textEntry,1,5,5, 1 );
    }

    /*
     * Create 4 rows of 5 word guesses, initially greyed out
     */
    private void createGuessFields(int colWidth) {
        for(int i = 0; i < 4; i++) {
            guessFields.add(i, new ArrayList<VBox>());
            String guess = model.guesses[i];
            for(int j = 0; j < 5; j++) {
                String subGuess;
                if(guess == null) { subGuess = ""; } else { subGuess = guess.substring(j, j + 1); }
                Text textField = new Text(subGuess);
                textField.setTextAlignment(TextAlignment.CENTER);
                VBox textBox = new VBox();
                textBox.setMaxSize(colWidth * 0.8, colWidth * 0.8);
                textBox.setAlignment(Pos.CENTER);
                textBox.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
                textBox.setPadding(new Insets(20, 20, 20, 20));
                textBox.getChildren().add(textField);
                textBox.setSpacing(colWidth);
                guessFields.get(i).add(j, textBox);
                gridPane.add(guessFields.get(i).get(j), j + 1, i + 1, 1, 1);
            }
        }
    }

    /*
     * Create the help button at the top of the screen
     */
    private void createHelpButton(int colWidth) {
        Button helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        addMouseHandlingEvent(helpButton);
        uiHelper.customizeButton(helpButton, 3 * colWidth, (int) (0.75 * colWidth));
        uiHelper.makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        helpButton.setAlignment(Pos.CENTER);

        gridPane.add(helpButton, 2, 0, 3, 1 );  // Add buttons
    }

    /*
     * Adds functionality to the Instructions button
     */
    private void addMouseHandlingEvent(Button button) {
        EventHandler<MouseEvent> mouseClickedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(helpToggle) {
                    gridPane.getChildren().remove(helpLabel);
                    helpToggle = false;
                } else {
                    helpLabel = new Label(model.getInstructions());
                    helpLabel.setWrapText(true);
                    helpLabel.setStyle("-fx-text-fill: white;");
                    helpLabel.setFont(new Font("Arial", 16));
                    helpLabel.setAlignment(Pos.CENTER);
                    helpLabel.setMaxWidth(200);
                    gridPane.add(helpLabel, 6, 1, 1, 4);

                    helpToggle = true;
                }
            }
        };
        button.setOnMouseClicked(mouseClickedHandler);
    }


    /*
     * Create constraints so rows are properly sized and spaced
     */
    private void createRowConstraints(int colWidth) {
        // Row constraints
        RowConstraints row1 = new RowConstraints(200);
        RowConstraints row2 = new RowConstraints(colWidth);
        RowConstraints row3 = new RowConstraints(colWidth);
        RowConstraints row4 = new RowConstraints(colWidth);
        RowConstraints row5 = new RowConstraints(colWidth);
        RowConstraints row6 = new RowConstraints(200);
        row1.setVgrow( Priority.SOMETIMES );
        row6.setVgrow( Priority.ALWAYS );

        gridPane.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6);
    }

    /*
     * Create constraints so columns are properly sized and spaced
     */
    private void createColConstraints(int colWidth) {
        // Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints(colWidth);
        ColumnConstraints column3 = new ColumnConstraints(colWidth);
        ColumnConstraints column4 = new ColumnConstraints(colWidth);
        ColumnConstraints column5 = new ColumnConstraints(colWidth);
        ColumnConstraints column6 = new ColumnConstraints(colWidth);
        ColumnConstraints column7 = new ColumnConstraints(200);
        column1.setHgrow(Priority.SOMETIMES);
        column2.setHgrow( Priority.SOMETIMES );
        column3.setHgrow( Priority.SOMETIMES );
        column4.setHgrow( Priority.SOMETIMES );
        column5.setHgrow( Priority.SOMETIMES );
        column6.setHgrow( Priority.SOMETIMES );
        column7.setHgrow(Priority.SOMETIMES);
        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6, column7);
    }

    /*
     * Create the gridPane
     */
    private void createGridPane() {
        gridPane = new GridPane();
        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the inputTextField attribute
     * <p>
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to inputTextField and pass the stripped
     * string to submitEvent for processing.
     * <p>
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        EventHandler<KeyEvent> textEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    handleEnter();
                }
            }
        };
        inputTextField.addEventHandler(KeyEvent.KEY_RELEASED, textEventHandler);
    }

    /*
     * Take a guess and display the results
     */
    private void handleEnter() {
        String input = inputTextField.getText().strip().toUpperCase();
        try {
            model.submitGuess(input);
            commandLabel.setText("Input Your Guess!");
            updateGuesses();
        } catch(InputMismatchException e) {
            commandLabel.setText(e.getMessage());
        }
        inputTextField.clear();
    }

    /*
     * Update the correct row to match the most recent guess
     */
    private void updateGuesses() {
        int rowNum = model.getCurrentGuess() - 1;
        ArrayList<VBox> row = guessFields.get(rowNum);
        String guess = model.guesses[rowNum];
        String result = model.checkGuess(rowNum);
        for(int i = 0; i < row.size(); i++) {
            VBox vbox = updateVbox(result, i, row);

            Text text = (Text) vbox.getChildren().get(0);
            text.setText(guess.substring(i, i + 1));
        }
        if(Objects.equals(result, "22222")) {
            closeWindow(true);
        } else if(rowNum == 3) {
            closeWindow(false);
        }
    }

    /*
     * Update a vbox based on the results of a guess
     */
    private static VBox updateVbox(String result, int i, ArrayList<VBox> row) {
        String subRes = result.substring(i, i + 1);
        VBox vbox = row.get(i);
        if(subRes.equals("1")) {
            vbox.setStyle("-fx-background-color: #FFDA29; -fx-text-fill: white;");
        } else if(subRes.equals("2")) {
            vbox.setStyle("-fx-background-color: #00ff00; -fx-text-fill: white;");
        }
        return vbox;
    }

    /*
     * Terminate this window and end the game
     */
    public void closeWindow(boolean result) {
        model.endGame(result);
        if(result) {
            commandLabel.setText("Congrats! You won! Onwards!");
        } else {
            commandLabel.setText("Better luck next time! Heading back now");
        }
        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(5), event -> {stage.close();}));
        timeLine.play();
    }
}
