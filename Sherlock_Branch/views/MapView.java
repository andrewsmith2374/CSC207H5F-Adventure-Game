import javafx.application.Application;
import javafx.collections.ObservableList;

import static javafx.application.Application.launch;

import java.io.File;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class MapView {
    private Circle c1;
    private ImageView map;
    private AdventureGameView adventureGameView;
    private GridPane oPane;
    private Button exitButton;
    private Button killButton = new Button();
    private Button cherryTree = new Button();

    public MapView(AdventureGameView adventureGameView) {
        // image: https://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap.jpg
        String backgroundImage = "C:\\Users\\sherl\\Documents\\Programming\\Java\\CSC207\\group_83\\Sherlock_Branch\\views\\NarniaMap.jpg";
        map = new ImageView(new Image(new File(backgroundImage).toURI().toString()));
        // Set only the cirlce
        c1 = new Circle(5);
        c1.setFill(Color.RED);
        c1.setTranslateX(0);
        c1.setTranslateY(90);
        // Killbutton
        killButton = new Button("Move to witch's camp");
        // CherryTree
        cherryTree = new Button("Move to Cherry Tree");

        GridPane test = paneSetUp();

        this.adventureGameView = adventureGameView;
        oPane = adventureGameView.gridPane;
        var scene = new Scene(test,  1000, 800);
        adventureGameView.stage.setScene(scene);
    }
    
    public GridPane paneSetUp() {
        cherryTree = createCherryTree();
        killButton = createKillButton();
        // construct the scene contents over a stacked background.
        StackPane layout = new StackPane();
        layout.getChildren().setAll(
            map, killButton, cherryTree, c1
        );

        // wrap the scene contents in a pannable scroll pane.
        ScrollPane scroll = createScrollPane(layout);

        // center the scroll contents.
        scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
        scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

        // setup a grid pane for exit button
        GridPane ui = creatGridPane();
        ui.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));
        ui.add(scroll, 0, 1);

        // exit button
        createExitButton();
        ui.add(exitButton, 0, 0);
        GridPane.setHalignment(exitButton, HPos.RIGHT);

        return ui;
    }

    /** @return a ScrollPane which scrolls the layout. */
    private ScrollPane createScrollPane(Pane layout) {
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setPrefSize(800, 600);
        scroll.setContent(layout);
        return scroll;
    }

    /** @return a GirdPane which holds the scroll pane and exit button */
    private GridPane creatGridPane() {
        GridPane gridPane = new GridPane();  

        //Two columns, two rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(1000);

        // Row constraints
        RowConstraints row1 = new RowConstraints(25);
        RowConstraints row2 = new RowConstraints(775);

        gridPane.getColumnConstraints().addAll( column1 );
        gridPane.getRowConstraints().addAll( row1, row2 );

        return gridPane;
    }

    /** @return a exit button that will switch the UI back to interface */
    private void createExitButton() {
        exitButton = new Button("X");
        exitButton.setId("Exit");
        exitButton.setPrefSize(15, 15);
        exitButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        exitButton.setOnAction(e -> {
            exit();
        });
    }
    
    private void moveDot(Button button) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(c1);
        transition.setToX(button.getTranslateX());
        transition.setToY(button.getTranslateY() - 40);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.play();
    }

    /** @return a control to place on the scene. */
    private Button createKillButton() {
        killButton.setStyle("-fx-base: firebrick;");
        killButton.setTranslateX(65);
        killButton.setTranslateY(-150);
        killButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent t) {
            moveDot(killButton);
            }
        });
        return killButton;
    }

    /** @return a control to place on the scene. */
    private Button createCherryTree() {
        cherryTree.setStyle("-fx-base: firebrick;");
        cherryTree.setTranslateX(5);
        cherryTree.setTranslateY(160);
        cherryTree.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent t) {
            moveDot(cherryTree);
            }
        });
        return cherryTree;
    }

    private void exit() {
        adventureGameView.stage.setScene(this.adventureGameView.scene);
    }

}