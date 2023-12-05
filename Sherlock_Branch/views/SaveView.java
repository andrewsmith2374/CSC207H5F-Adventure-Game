

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Class SaveView.
 *
 * Saves Serialized adventure games.
 */
public class SaveView {

    static String saveFileSuccess = "Saved Adventure Game!!";
    static String saveFileExistsError = "Error: File already exists";
    static String saveFileNotSerError = "Error: File must end with .ser";
    private Label saveFileErrorLabel = new Label("");
    private Label saveGameLabel = new Label(String.format("Enter name of file to save"));
    private TextField saveFileNameTextField = new TextField("");
    private Button saveGameButton = new Button("Save Game");
    private Button closeWindowButton = new Button("Close Window");

    private AdventureGameView adventureGameView;

    /**
     * Constructor
     */
    public SaveView(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");
        saveGameLabel.setId("SaveGame"); // DO NOT MODIFY ID
        saveFileErrorLabel.setId("SaveFileErrorLabel");
        saveFileNameTextField.setId("SaveFileNameTextField");
        saveGameLabel.setStyle("-fx-text-fill: #e8e6e3;");
        saveGameLabel.setFont(new Font(adventureGameView.fontSize));
        saveGameLabel.setWrapText(true);
        saveFileErrorLabel.setStyle("-fx-text-fill: #e8e6e3;");
        saveFileErrorLabel.setFont(new Font(adventureGameView.fontSize));
        saveFileErrorLabel.setWrapText(true);
        saveFileNameTextField.setStyle("-fx-text-fill: #000000;");
        saveFileNameTextField.setFont(new Font(adventureGameView.fontSize));

        String gameName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + ".ser";
        saveFileNameTextField.setText(gameName);

        saveGameButton = new Button("Save board");
        saveGameButton.setId("SaveBoardButton"); // DO NOT MODIFY ID
        saveGameButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        saveGameButton.setPrefSize(200, 50);
        saveGameButton.setFont(new Font(adventureGameView.fontSize));
        AdventureGameView.makeButtonAccessible(saveGameButton, "save game", "This is a button to save the game", "Use this button to save the current game.");
        saveGameButton.setOnAction(e -> saveGame());

        VBox saveGameBox = new VBox(10, saveGameLabel, saveFileNameTextField, saveGameButton, saveFileErrorLabel);
        saveGameBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(saveGameBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    /**
     * Saves the Game
     * Save the game to a serialized (binary) file.
     * Get the name of the file from saveFileNameTextField.
     * Files will be saved to the Games/Saved directory.
     * If the file already exists, set the saveFileErrorLabel to the text in saveFileExistsError
     * If the file doesn't end in .ser, set the saveFileErrorLabel to the text in saveFileNotSerError
     * Otherwise, load the file and set the saveFileErrorLabel to the text in saveFileSuccess
     */
    private void saveGame() {
        String name = saveFileNameTextField.getText();
        File dir = new File("C:\\Users\\sherl\\Documents\\Programming\\Java\\CSC207\\group_83\\Sherlock_Branch\\Games\\Saved\\" + name);
        File temp = new File("C:\\Users\\sherl\\Documents\\Programming\\Java\\CSC207\\group_83\\Sherlock_Branch\\Games\\Saved\\");
        // Check if the file is existed or not
        if (temp.list().length > 0) {
            if (Arrays.asList(temp.listFiles()).contains(dir)) {
                saveFileErrorLabel.setText(saveFileExistsError);
            }
        }
        // Check for extension
        if (!name.endsWith(".ser")) {
            saveFileErrorLabel.setText(saveFileNotSerError);
        } else {
            adventureGameView.model.saveModel(dir);
            saveFileErrorLabel.setText(saveFileSuccess);
        }
    }


}
