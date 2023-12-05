package AdventureModel.Trolls.RockPaperScissorTroll;

import java.io.BufferedReader;
import java.io.File;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.ToDoubleBiFunction;

/**
 * Rock Paper Scissor Troll View
 */

public class RockPaperScissorTrollView {
    private Stage stage;
    private Button helpButton;
    private boolean helpToggle;
    private Label help;
    private RockPaperScissorTroll model;
    private GridPane gridPane;
    private Button rock;
    private Button paper;
    private Button scissor;
    private List<ImageView> computerChoices;
    private ImageView rockView;
    private ImageView scissorView;
    private ImageView paperView;
    private ImageView compRock;
    private ImageView compPaper;
    private ImageView compScissor;
    private VBox box;
    private VBox compBox;

    /**
     * Class Constructor initializes attributes
     * @param model
     */

    public RockPaperScissorTrollView(RockPaperScissorTroll model) {
        this.model = model;
        this.stage = new Stage();
        this.box = new VBox();
        this.compBox = new VBox();
        this.computerChoices = new ArrayList<ImageView>();
        this.gridPane = new GridPane();
        rockView = new ImageView();
        paperView = new ImageView();
        scissorView = new ImageView();
        compRock = new ImageView();
        compPaper = new ImageView();
        compScissor = new ImageView();
        helpToggle = false;
        help = new Label();
        intiUI();
    }

    /**
     * Initialize the UI
     */

    private void intiUI() {
        stage.setTitle("Rock Paper Scissor");
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTGREY, null, null);
        Background background = new Background(backgroundFill);
        gridPane.setBackground(background);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(40);
        row1.setVgrow(Priority.NEVER);
        row2.setVgrow(Priority.NEVER);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(40);
        row3.setVgrow(Priority.NEVER);
        gridPane.getRowConstraints().addAll(row1, row2, row3);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(90);
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(10);
        col2.setHgrow(Priority.NEVER);
        gridPane.getColumnConstraints().addAll(col1, col2);


        helpButton = new Button();
        helpButton.setText("RULES");
        helpButton.setFont(new Font("Arial", 16));
        addHelpEvent();

        gridPane.add(helpButton, 1, 0);

        HBox buttonBox = new HBox();
        rock = new Button();
        rock.setId("rock");
        rock.setStyle("-fx-background-color: #088F8F; -fx-text-fill: white;");
        rock.setText("ROCK");
        rock.setFont(new Font("Arial", 30));
        buttonBox.getChildren().add(rock);
        addRockButtonEvent();

        scissor = new Button();
        scissor.setId("scissor");
        scissor.setStyle("-fx-background-color: #088F8F; -fx-text-fill: white;");
        scissor.setText("SCISSOR");
        scissor.setFont(new Font("Arial", 30));
        buttonBox.getChildren().add(scissor);
        addScissorButtonEvent();

        paper = new Button();
        paper.setId("paper");
        paper.setStyle("-fx-background-color: #088F8F; -fx-text-fill: white;");
        paper.setText("PAPER");
        paper.setFont(new Font("Arial", 30));
        buttonBox.getChildren().add(paper);
        addPaperButtonEvent();

        buttonBox.setSpacing(200);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 2);

        rockView.setImage(new Image("AdventureModel/Trolls/RockPaperScissorTroll/rock.png"));
        rockView.setId("rock");
        rockView.setFitWidth(300);
        rockView.setFitHeight(300);
        paperView.setImage(new Image("AdventureModel/Trolls/RockPaperScissorTroll/paper.png"));
        paperView.setId("paper");
        paperView.setFitWidth(300);
        paperView.setFitHeight(300);
        scissorView.setImage(new Image("AdventureModel/Trolls/RockPaperScissorTroll/scissors.png"));
        scissorView.setId("scissor");
        scissorView.setFitHeight(300);
        scissorView.setFitWidth(300);

        compRock.setImage(new Image("AdventureModel/Trolls/RockPaperScissorTroll/rock.png"));
        compRock.setId("rock");
        compRock.setFitWidth(300);
        compRock.setFitHeight(300);
        compPaper.setImage(new Image("AdventureModel/Trolls/RockPaperScissorTroll/paper.png"));
        compPaper.setId("paper");
        compPaper.setFitHeight(300);
        compPaper.setFitWidth(300);
        compScissor.setImage(new Image("AdventureModel/Trolls/RockPaperScissorTroll/scissors.png"));
        compScissor.setId("scissor");
        compScissor.setFitWidth(300);
        compScissor.setFitHeight(300);

        computerChoices.add(0, compRock);
        computerChoices.add(1, compPaper);
        computerChoices.add(2, compScissor);

        gridPane.setGridLinesVisible(false);

        var scene = new Scene(gridPane, 1000, 800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Show instructions when rules Button pressed.
     */
    public void addHelpEvent() {
        helpButton.setOnAction(e -> {
            if(!helpToggle) {
                help.setText(model.helpText);
                help.setFont(new Font("Arial", 16));
                help.setAlignment(Pos.TOP_RIGHT);
                gridPane.add(help, 0, 0);
                helpToggle = true;
            }
            else {
                gridPane.getChildren().removeAll(help);
                helpToggle = false;


            }
        });
    }

    /**
     * Play rock for player
     */

    public void addRockButtonEvent(){
        rock.setOnAction(e -> {
            String player = computerTurn();
            rockView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            updateBoxContent(box, rockView);
            if(model.win(player, rockView.getId())) {
                winLabel();
            }

        });
    }

    /**
     * Play Scissor for player
     */
    public void addScissorButtonEvent(){
        scissor.setOnAction(e -> {
            String player = computerTurn();
            scissorView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            updateBoxContent(box, scissorView);

            if(model.win(player, scissor.getId())) {
                winLabel();
            }

        });
    }

    /**
     * Play Paper for player
     */
    public void addPaperButtonEvent() {
        paper.setOnAction(e -> {
            String player = computerTurn();
            paperView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            updateBoxContent(box, paperView);

            if(model.win(player, paperView.getId())) {
                winLabel();
            }


        });
    }

    /**
     * Update the previous player move with the new move
     * @param box
     * @param image
     */
    public void updateBoxContent(VBox box, ImageView image) {
        try {
            box.getChildren().clear();
            box.getChildren().add(image);
            box.setAlignment(Pos.CENTER_RIGHT);
            gridPane.add(box, 0, 1);
        } catch(IllegalArgumentException ignored){}

    }

    /**
     * Play for the computer.
     * @return
     */
    public String computerTurn() {
        String choice = null;
        try {
            choice = model.computerChoice();
            for (int i = 0; i < 3; i++) {
                if (choice.equals(computerChoices.get(i).getId())) {
                    updateBoxContentComp(compBox, computerChoices.get(i));
                    break;  // Break out of the loop after updating content
                }
            }
        } catch (IllegalArgumentException ignored) {
            // Handle the exception or remove the catch block if not needed
        } finally {
            return choice;
        }
    }

    /**
     * Update computers previous move with the current move.
     * @param box
     * @param image
     */
    public void updateBoxContentComp(VBox box, ImageView image) {
        try {
            box.getChildren().clear();
            box.getChildren().add(image);
            box.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(box, 0, 1);
        } catch(IllegalArgumentException ignored){}

    }

    /**
     * Show that player won on the UI and end the game.
     */
    public void winLabel() {
        model.gameStat = true;
        Label win = new Label();
        win.setText("YOU WIN!");
        win.setFont(new Font("Times New Roman", 25));
        win.setAlignment(Pos.TOP_CENTER);
        gridPane.add(win, 0, 0);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> stage.close());
        pause.play();

    }
}



