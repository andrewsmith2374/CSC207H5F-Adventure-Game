package AdventureModel.Trolls.WhackAMoleTroll;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class WhackAMoleTrollView
 */

public class WhackAMoleTrollView {

    private WhackAMoleTroll model;
    private Stage stage;
    private Label scoreLabel;
    GridPane gridPane = new GridPane();
    ArrayList<Button> moles;
    private Button moleButton;
    public Timeline moleTimeLine;

    /**
     * WhackAMoleTrollView Constructor to initialize attributes
     * @param model to represent the Troll game.
     */
    public WhackAMoleTrollView(WhackAMoleTroll model) {
        this.model = model;
        this.stage = new Stage();
        this.scoreLabel = new Label();
        this.moles = new ArrayList<>();
        intiUI();


    }

    /**
     * Initialize the UI
     */
    public void intiUI() {
        this.stage.setTitle("Whack A Mole");


        Image background = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/whack a mole background .png");
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                        false, false, true, true));

        gridPane.setBackground(new Background(backgroundImage));

        scoreLabel.setText("Score: ");
        scoreLabel.setFont(new Font("Times New Roman", 25));
        scoreLabel.setTextAlignment(TextAlignment.LEFT);
        scoreLabel.setWrapText(true);
        scoreLabel.setId("Score");

        Image mole1 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/mole1.png");
        Button mole1Button = new Button();
        mole1Button.setGraphic(new ImageView(mole1));
        mole1Button.setId("mole1");
        Image mole2 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/mole2.png");
        Button mole2Button = new Button();
        mole2Button.setGraphic(new ImageView(mole2));
        mole2Button.setId("mole2");
        Image mole3 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/mole3.png");
        Button mole3Button = new Button();
        mole3Button.setGraphic(new ImageView(mole3));
        mole3Button.setId("mole3");
        Image mole4 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/mole4.png");
        Button mole4Button = new Button();
        mole4Button.setGraphic(new ImageView(mole4));
        mole4Button.setId("mole4");
        Image mole5 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/mole5.png");
        Button mole5Button = new Button();
        mole5Button.setGraphic(new ImageView(mole5));
        mole5Button.setId("mole5");

        moles.add(0, mole1Button);
        moles.add(1, mole2Button);
        moles.add(2, mole3Button);
        moles.add(3, mole4Button);
        moles.add(4, mole5Button);

        //add thesr to VBOX's and assign position

        Image hole1 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/hole1.png");
        ImageView hole1View = new ImageView(hole1);
        VBox box1 = new VBox(hole1View);
        box1.setAlignment(Pos.BOTTOM_CENTER);

        Image hole2 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/hole1.png");
        ImageView hole2View = new ImageView(hole2);
        VBox box2 = new VBox(hole2View);
        box2.setAlignment(Pos.BOTTOM_CENTER);

        Image hole3 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/hole1.png");
        ImageView hole3View = new ImageView(hole3);
        VBox box3 = new VBox(hole3View);
        box3.setAlignment(Pos.BOTTOM_CENTER);

        Image hole4 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/hole1.png");
        ImageView hole4View = new ImageView(hole4);
        VBox box4 = new VBox(hole4View);
        box4.setAlignment(Pos.BOTTOM_CENTER);

        Image hole5 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/hole1.png");
        ImageView hole5View = new ImageView(hole5);
        VBox box5 = new VBox(hole5View);
        box5.setAlignment(Pos.BOTTOM_CENTER);

        Image hole6 = new Image("AdventureModel/Trolls/WhackAMoleTroll/graphics/hole1.png");
        ImageView hole6View = new ImageView(hole6);
        VBox box6 = new VBox(hole6View);
        box6.setAlignment(Pos.BOTTOM_CENTER);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        row1.setVgrow(Priority.NEVER);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(40);
        row2.setVgrow(Priority.NEVER);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(40);
        row3.setVgrow(Priority.NEVER);
        gridPane.getRowConstraints().addAll(row1, row2, row3);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33.3);
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33.3);
        col2.setHgrow(Priority.NEVER);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33.3);
        col3.setHgrow(Priority.NEVER);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        VBox scoreHold = new VBox(scoreLabel);
        scoreHold.setAlignment(Pos.TOP_LEFT);
        gridPane.add(scoreHold, 0, 0);
        scoreHold.setId("ScoreHold");

        gridPane.add(box1, 0, 1);
        gridPane.add(box2, 1, 1);
        gridPane.add(box3, 2, 1);
        gridPane.add(box4, 0, 2);
        gridPane.add(box5, 1, 2);
        gridPane.add(box6, 2, 2);

        gridPane.setGridLinesVisible(false);

        var scene = new Scene(gridPane, 1000, 800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Show a random mole on a random hole on the screen.
     */

    public void showMole() {
        Random random = new Random();
        int moleNum = model.getMole();
        moleButton = moles.get(moleNum);

        int randomRow;
        do {
            randomRow = random.nextInt(3);
        } while (randomRow == 0);

        int randomCol = random.nextInt(3);
        try {
            ImageView moleImage = (ImageView) moleButton.getGraphic();
            moleImage.setFitHeight(300);
            moleImage.setFitWidth(300);
            moleButton.setPrefWidth(300);
            moleButton.setPrefHeight(300);
            moleButton.setBackground(Background.EMPTY);
            moleButton.setAlignment(Pos.TOP_CENTER);
            gridPane.add(moleButton, randomCol, randomRow);
        } catch(IllegalArgumentException ignored) {}

        finally {

        stage.show();
        addMoleButtonEvent(); //if button is clicked it updates score;
        // need a method to remove the button after 2 seconds
        removeMole(moleButton); }
    }

    /**
     * Keep the mole on screen for a few seconds and then remove it
     * @param moleButton the current mole on screen
     */

    public void removeMole(Button moleButton) {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> gridPane.getChildren().remove(moleButton));
        pause.play();
        stage.show();
    }

    /**
     * Increment score by 1 if mole is clicked. If score reaches 5, player wins and game ends.
     */

    public void addMoleButtonEvent() {
        moleButton.setOnAction(e -> {
            this.model.moleEventManager.notifyMoleClicked();
            gridPane.getChildren().remove(moleButton);// calls update score and incremenets score to 1
            this.scoreLabel.setText("Score: " + model.score);
            if(model.score == 5) {
                moleTimeLine.stop();
                winLabel();
            }
            stage.show();
        });
    }

    /**
     * Show moles on the screen.
     * @param duration time interval between each mole that pops up.
     */

    public void runShowMole(Duration duration) {
        try {
            moleTimeLine = new Timeline(new KeyFrame(duration, event -> {
                showMole();
            }));
            moleTimeLine.setCycleCount(15);

            // Set an event handler to be triggered when the timeline finishes
            moleTimeLine.setOnFinished(e -> {
                // Check the end condition of the game (e.g., reaching a certain score)
                if (model.score < 10) {
                    loseLabel(); // Show the "LOST!" label
                }

            });
        } catch (IllegalArgumentException ignored) {
            // Ignored, do nothing.
        } finally {
            moleTimeLine.play();
            stage.setOnCloseRequest(e -> {moleTimeLine.stop();
                stage.close();});
        }
    }

    /**
     * Show that player lost on the UI and end game.
     */

    public void loseLabel() {
        model.gameStatus = false;
        Label lose = new Label();
        lose.setText("LOST!");
        lose.setId("lose");
        lose.setFont(new Font("Times New Roman", 25));
        lose.setAlignment(Pos.TOP_CENTER);
        gridPane.add(lose, 1, 1);
        stage.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {stage.close();});
        pause.play();
    }

    /**
     * Show that player won on UI and end game.
     */

    public void winLabel() {
        model.gameStatus = true;
        Label win = new Label();
        win.setText("WIN");
        win.setId("win");
        win.setFont(new Font("Times New Roman", 25));
        win.setAlignment(Pos.TOP_CENTER);
        gridPane.add(win, 1, 1);
        stage.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            stage.close();
        });
        pause.play();




    }
}


