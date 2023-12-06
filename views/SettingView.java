package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
public class SettingView {

    private Button closeWindowButton;

    private AdventureGameView adventureGameView;

    /**
     * Constructor
     */
    public SettingView(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        closeWindowButton = adventureGameView.quitButton;
        closeWindowButton.setText("Quit Game");
        closeWindowButton.setId("quitGameButton"); // DO NOT MODIFY ID
        closeWindowButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        closeWindowButton.setPrefSize(200, 50);
        closeWindowButton.setFont(new Font(adventureGameView.fontSize));
        closeWindowButton.setOnAction(e -> adventureGameView.stage.close());
        AdventureGameView.makeButtonAccessible(closeWindowButton, "quit game", "This is a button to quit the game", "Use this button to close the entire game window, and back to window.");

        // Lets build the text box here
        HBox font = new HBox(10, adventureGameView.fontDownButton, adventureGameView.fontSizTextField, adventureGameView.fontUpButton);
        font.setAlignment(Pos.CENTER);

        VBox saveGameBox = new VBox(10, font, adventureGameView.saveButton, adventureGameView.loadButton, adventureGameView.helpButton, closeWindowButton);
        saveGameBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(saveGameBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}

