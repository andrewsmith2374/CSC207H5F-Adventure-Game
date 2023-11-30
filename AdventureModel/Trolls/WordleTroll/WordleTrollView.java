package AdventureModel.Trolls.WordleTroll;

import UIHelpers.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class WordleTrollView {
    private WordleTroll model;
    private UIHelper uiHelper;
    private Stage stage;
    private GridPane gridPane;
    private Button helpButton;
    private TextField inputTextField;
    private ArrayList<ArrayList<VBox>> guessFields;
    private Boolean helpToggle = false; //is help on display?

    public WordleTrollView(WordleTroll model) {
        this.model = model;
        uiHelper = new UIHelper();
        stage = new Stage();
        guessFields = new ArrayList<>(4);
        initUI();
    }

    private void initUI() {
        stage.setTitle("Wordle!");

        gridPane = new GridPane();
        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        int colWidth = 100;
        // Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints(colWidth);
        ColumnConstraints column3 = new ColumnConstraints(colWidth);
        ColumnConstraints column4 = new ColumnConstraints(colWidth);
        ColumnConstraints column5 = new ColumnConstraints(colWidth);
        ColumnConstraints column6 = new ColumnConstraints(colWidth);
        ColumnConstraints column7 = new ColumnConstraints();
        column1.setHgrow(Priority.SOMETIMES);
        column2.setHgrow( Priority.SOMETIMES );
        column3.setHgrow( Priority.SOMETIMES );
        column4.setHgrow( Priority.SOMETIMES );
        column5.setHgrow( Priority.SOMETIMES );
        column6.setHgrow( Priority.SOMETIMES );
        column7.setHgrow(Priority.SOMETIMES);

        // Row constraints
        RowConstraints row1 = new RowConstraints(200);
        RowConstraints row2 = new RowConstraints(colWidth);
        RowConstraints row3 = new RowConstraints(colWidth);
        RowConstraints row4 = new RowConstraints(colWidth);
        RowConstraints row5 = new RowConstraints(colWidth);
        RowConstraints row6 = new RowConstraints(200);
        row1.setVgrow( Priority.SOMETIMES );
//        row2.setVgrow( Priority.SOMETIMES );
//        row3.setVgrow( Priority.SOMETIMES );
//        row4.setVgrow( Priority.SOMETIMES );
//        row5.setVgrow( Priority.SOMETIMES );
        row6.setVgrow( Priority.ALWAYS );

        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6, column7);
        gridPane.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6);

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        uiHelper.customizeButton(helpButton, 3 * colWidth, (int) (0.75 * colWidth));
        uiHelper.makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        helpButton.setAlignment(Pos.CENTER);

        gridPane.add(helpButton, 2, 0, 3, 1 );  // Add buttons

        for(int i = 0; i < 4; i++) {
            guessFields.add(i, new ArrayList<VBox>());
            for(int j = 0; j < 5; j++) {
                Text textField = new Text("");
                textField.setTextAlignment(TextAlignment.CENTER);
                VBox textBox = new VBox();
                textBox.setMaxSize(colWidth * 0.8, colWidth * 0.8);
                textBox.setAlignment(Pos.CENTER);
                textBox.setStyle("-fx-background-color: #808080; -fx-text-fill: black;");
                textBox.setPadding(new Insets(20, 20, 20, 20));
                textBox.getChildren().add(textField);
                textBox.setSpacing(colWidth);
                guessFields.get(i).add(j, textBox);
                gridPane.add(guessFields.get(i).get(j), j + 1, i + 1, 1, 1);
            }
        }

        inputTextField = new TextField();
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
//        textEntry.setMinSize(5 * colWidth, 0.3 * colWidth);
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().add(inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add(textEntry,1,5,5, 1 );

        var scene = new Scene(gridPane,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    /*
     * Articulate the instructions for this game
     */
    public void articulateInstructions() {
        throw new UnsupportedOperationException("Implement articulateInstructions");
    }

    /*
     * Terminate this window and end the game
     */
    public void closeWindow() { throw new UnsupportedOperationException("Implement closeWindow"); }

    /*
     * Change view to the main game
     */
    private void startGame() { throw new UnsupportedOperationException("Implement startGame"); }

    /*
     * Take a guess and display the results
     */
    private void processInput() { throw new UnsupportedOperationException("Implement processInput"); }
}
