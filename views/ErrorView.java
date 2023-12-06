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

public class ErrorView {

    private Label errorMessage = new Label();
    private Button closeWindowButton = new Button("Close Window");

    private AdventureGameView adventureGameView;

    final Stage dialog;

    /**
     * Constructor
     */
    public ErrorView(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        closeWindowButton = new Button("CLOSE");
        closeWindowButton.setId("closwButton"); // DO NOT MODIFY ID
        closeWindowButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        closeWindowButton.setPrefSize(200, 50);
        closeWindowButton.setFont(new Font(16));
        closeWindowButton.setOnAction(e -> dialog.close());
        AdventureGameView.makeButtonAccessible(closeWindowButton, "Close", "This is a button to close the error message", "Use this button to close the error message and return to the game.");

        errorMessage.setStyle("-fx-text-fill: white; -fx-font: 25 arial");

        VBox saveGameBox = new VBox(10, errorMessage, closeWindowButton);
        saveGameBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(saveGameBox);
        Scene dialogScene = new Scene(dialogVbox, 1000, 150);
        dialog.setScene(dialogScene);
    }

    /**
     * Font size error
     * @return boolean indicate whether it is a error or not
     */
    public boolean fontSizeError(int size) {
        if (size <= 0) {
            errorMessage.setText("You know that, no font can be smaller than or equal 0.");
        }
        else {
            errorMessage.setText("See, I don't want to get into trouble, but why do you need the font to be that big ???");
        }
        if (size <= 0 || size > 30) {
            dialog.show();
            return true;
        }
        return false;
    }

    /**
     * Type Error
     * @return boolean indicate whether it is error or not
     */
    public void typeError(Boolean indi) {
        if (indi) {
            errorMessage.setText("At what point you think this is a font size??");
            dialog.show();
        }
    }
}
