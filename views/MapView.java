package views;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/** Constructs a scene with a pannable Map background. */
public class MapView extends Application {
    private Circle c1;
    private Image backgroundImage;
    private ImageView map;
    private Button killButton;
    private Button cherryTree;

    @Override public void init() {
        // image
        backgroundImage = new Image("https://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap.jpg");
        map = new ImageView(backgroundImage);
        // Set only the cirlce
        c1 = new Circle(5);
        c1.setFill(Color.RED);
        c1.setTranslateX(0);
        c1.setTranslateY(90);
        // Killbutton
        killButton = new Button("Move to witch's camp");
        // CherryTree
        cherryTree = new Button("Move to Cherry Tree");
    }

    @Override public void start(Stage stage) {
        stage.setTitle("Map testing");

        // construct the scene contents over a stacked background.
        StackPane layout = new StackPane();
        layout.getChildren().setAll(
            map,
            createKillButton(), createCherryTree(),
            c1
        );

        // wrap the scene contents in a pannable scroll pane.
        ScrollPane scroll = createScrollPane(layout);

        // show the scene.
        Scene scene = new Scene(scroll);
        stage.setScene(scene);
        stage.show();

        // bind the preferred size of the scroll area to the size of the scene.
        scroll.prefWidthProperty().bind(scene.widthProperty());
        scroll.prefHeightProperty().bind(scene.widthProperty());

        // center the scroll contents.
        scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
        scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);
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
  
    public static void main(String[] args) {
        launch(args);
    }  
}