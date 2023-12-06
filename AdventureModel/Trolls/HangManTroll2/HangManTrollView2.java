package AdventureModel.Trolls.HangManTroll2;

import Util.UIHelpers.UIHelper;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class HangManTrollView2 {
    private final HangManTroll2 model;
    private final UIHelper uiHelper;
    private final Stage stage;
    private GridPane gridPane;
    private Label helpLabel;
    private TextField inputTextField;
    private Boolean helpToggle = false; //is help on display?

    private String wordToGuess = "e";
    private ArrayList<String> words;
    private ArrayList<String> lettersGuessed;
    private StringBuilder displayText;
    private int wrongGuesses = 0;
    public Boolean result;
    private List<Shape> hangmanParts = new ArrayList<>();
    private List<Shape> StandParts = new ArrayList<>();
    private String instructions = "The objective is to guess the letters that the mystery word contains. You are allowed 7 incorrect guesses before you lose. Type your guess in the text book above and click the guess button.";


    public HangManTrollView2(HangManTroll2 model) {
        this.model = model;
        uiHelper = new UIHelper();
        stage = new Stage();
        chooseWord();
        initUI();
    }

    //chooses the mystery word for hangman from the random-words.txt file
    public void chooseWord() {
        words = new ArrayList<>();
        lettersGuessed = new ArrayList<>();
        String filePath = "AdventureModel/Trolls/HangManTroll2/random-words.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int min = 0;
        int max = 200;
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        wordToGuess = words.get(randomNumber).toUpperCase();
    }

    /*
     * Create all UI present at the start of the game
     */
    private void initUI() {
        stage.setTitle("Hangman Troll Game");

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        //FOR LOADING SCREEN
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;"); // Set the background color to black
        Scene scene = new Scene(root, 1000, 800);
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
            circles.get(i).setCenterY(scene.getHeight() / 2);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), circles.get(i));
            transition.setByY(movementRange);
            transition.setAutoReverse(true);
            transition.setCycleCount(TranslateTransition.INDEFINITE);
            transition.setDelay(Duration.millis(i * 100)); // Stagger the start of each dot's movement
            transition.play();

            root.getChildren().add(circles.get(i));
        }


        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

        //transition to game
        PauseTransition startGame = new PauseTransition();
        startGame.setDuration(Duration.seconds(2));

        startGame.setOnFinished(event -> {
            root.getChildren().clear();

            displayText = new StringBuilder("_".repeat(wordToGuess.length()));

            Label wordLabel = new Label(displayText.toString());
            wordLabel.setTextFill(Color.WHITE);
            wordLabel.setLayoutX(scene.getWidth() / 2 - 15);
            wordLabel.setLayoutY(375);

            TextField letterInput = new TextField();
            letterInput.setStyle("-fx-text-fill: black;");

            letterInput.setLayoutX(scene.getWidth() / 2 - 70);
            letterInput.setLayoutY(400);
            letterInput.setPromptText("Enter a letter");

            Button guessButton = new Button("Guess");
            guessButton.setLayoutX(scene.getWidth() / 2 - 20);
            guessButton.setLayoutY(450);

            //mine
            Label endGameState = new Label("");
            endGameState.setStyle("-fx-text-fill: white;");
            endGameState.setLayoutX(200);
            endGameState.setLayoutY(500);


            endGameState.setFont(new Font("Arial", 16));

            //outputs for when guess button is clicked
            guessButton.setOnAction(e -> {

                String guess = letterInput.getText().toUpperCase();

                if (guess.length() == 0) {
                    endGameState.setText("Please Enter A Letter This Time Dumbo");
                } else if (lettersGuessed.contains(guess)) {
                    endGameState.setText("Please Remember The Letters You've Already Guessed Dumbo");
                } else if (guess.length() >= 2) {
                    endGameState.setText("Please Enter A Single Letter At A Time Dumbo");
                } else if (guess.length() == 1 && wordToGuess.contains(guess)) {
                    updateDisplayText(guess.charAt(0));
                    lettersGuessed.add(guess);
                    endGameState.setText("You Guessed Correctly");
                } else {
                    wrongGuesses++;
                    lettersGuessed.add(guess);
                    endGameState.setText("You Guessed Incorrectly");
                    drawHangman();
                }
                wordLabel.setText(displayText.toString());
                letterInput.clear();

                if (displayText.toString().equals(wordToGuess)) {
                    endGameState.setText("You have successfully slain the troll and lifted the suffering on the surrounding land");
                    result = true;
                    closeWindow(result);

                } else if (wrongGuesses == 7) {
                    endGameState.setText("You lose! The word was: " + wordToGuess + ". The Troll takes a swing at you. you lose 10 HP");

                    //cross mans eyes if you lose
                    Line Cross1 = new Line(588, 178, 595, 185);
                    Cross1.setStroke(Color.WHITE);
                    root.getChildren().add(Cross1);
                    Cross1.setVisible(true);

                    Line Cross2 = new Line(595, 178, 588, 185);
                    Cross2.setStroke(Color.WHITE);
                    root.getChildren().add(Cross2);
                    Cross2.setVisible(true);

                    Line Cross3 = new Line(612, 178, 605, 185);
                    Cross3.setStroke(Color.WHITE);
                    root.getChildren().add(Cross3);
                    Cross3.setVisible(true);

                    Line Cross4 = new Line(605, 178, 612, 185);
                    Cross4.setStroke(Color.WHITE);
                    root.getChildren().add(Cross4);
                    Cross4.setVisible(true);

                    result = false;
                    closeWindow(result);
                }
            });

            // Hangman graphics setup
            setupHangManStarterGraphics();
            setupHangmanGraphics();

            //make the hangman stand visible when the game starts
            for (int i = 0; i < 3; i++) {
                Shape part = StandParts.get(i);
                part.setVisible(true);
            }

            Label instructionsLabel = new Label(instructions);
            instructionsLabel.setLayoutX(300);
            instructionsLabel.setLayoutY(600);
            instructionsLabel.setMaxWidth(400);
            instructionsLabel.setWrapText(true);
            instructionsLabel.setTextAlignment(TextAlignment.CENTER);
            instructionsLabel.setFont(new Font(15));
            instructionsLabel.setTextFill(Color.WHITE);
            instructionsLabel.setVisible(false);

            Button instructionButton = new Button("Instructions");
            instructionButton.setLayoutX(scene.getWidth() / 2 - 35);
            instructionButton.setLayoutY(550);

            instructionButton.setOnAction(e -> {
                if (instructionsLabel.isVisible()) {
                    instructionsLabel.setVisible(false);
                } else {
                    instructionsLabel.setVisible(true);
                }

            });

            scene.setOnKeyPressed(event1 -> {
                if (event1.getCode() == KeyCode.ENTER) {
                    // Fire the button's action
                    guessButton.fire();
                }
            });

            root.getChildren().addAll(wordLabel, letterInput, guessButton, endGameState);
            root.getChildren().addAll(hangmanParts);
            root.getChildren().addAll(StandParts);
            root.getChildren().add(instructionsLabel);
            root.getChildren().add(instructionButton);

            stage.setTitle("Hangman Game");
            stage.setScene(scene);
            stage.show();
        });
        startGame.play();

    }

    //change the blank spots to the player inputed letters if guessed correctly
    private void updateDisplayText(char guessedLetter) {
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guessedLetter) {
                displayText.setCharAt(i, guessedLetter);
            }
        }
    }

    //parts that appear if user guesses incorrect letter
    private void setupHangmanGraphics() {
        // Rope
        Line rope = new Line(600, 100, 600, 170);
        rope.setStroke(Color.WHITE);
        hangmanParts.add(rope);

        // Head
        Circle head = new Circle(15, 15, 20);
        head.setStroke(Color.WHITE);
        head.setCenterX(600);
        head.setCenterY(185);
        hangmanParts.add(head);

        // Body
        Line body = new Line(600, 205, 600, 270);
        body.setStroke(Color.WHITE);
        hangmanParts.add(body);

        // Arms
        Line leftArm = new Line(600, 220, 580, 240);
        leftArm.setStroke(Color.WHITE);
        hangmanParts.add(leftArm);
        Line rightArm = new Line(600, 220, 620, 240);
        rightArm.setStroke(Color.WHITE);
        hangmanParts.add(rightArm);

        // Legs
        Line leftLeg = new Line(600, 270, 580, 290);
        leftLeg.setStroke(Color.WHITE);
        hangmanParts.add(leftLeg);
        Line rightLeg = new Line(600, 270, 620, 290);
        rightLeg.setStroke(Color.WHITE);
        hangmanParts.add(rightLeg);

        // Initially, all parts are not visible
        hangmanParts.forEach(part -> part.setVisible(false));
    }

    //parts that are visible on the screen by default
    private void setupHangManStarterGraphics() {
        // Base
        Line base = new Line(400, 351, 600, 350);
        base.setStroke(Color.WHITE);
        base.setStrokeWidth(5);
        StandParts.add(base);

        // Pole
        Line pole = new Line(500, 350, 500, 100);
        pole.setStroke(Color.WHITE);
        pole.setStrokeWidth(5);
        StandParts.add(pole);

        // Top
        Line top = new Line(500, 100, 600, 100);
        top.setStroke(Color.WHITE);
        top.setStrokeWidth(5);
        StandParts.add(top);
    }

    //make hangman parts appear if user guesses incorrectly
    private void drawHangman() {
        if (wrongGuesses <= hangmanParts.size()) {
            hangmanParts.get(wrongGuesses - 1).setVisible(true);
        }
    }

    //    /*
//     * Terminate this window and end the game
//     */
    public void closeWindow(boolean result) {
        model.endGame(result);

        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            stage.close();
        }));
        timeLine.play();
    }
}
