package AdventureModel.Trolls.WordleTroll;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class WordleTrollView {
    private WordleTroll model;
    private Stage stage;
    private GridPane gridPane;
    private ArrayList<TextField> inputTextField;

    public WordleTrollView(WordleTroll model) {
        model = model;
        stage = new Stage();
        gridPane = new GridPane();
        inputTextField = new ArrayList<TextField>();

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
