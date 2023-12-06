package views;

import javafx.application.Application;
import javafx.collections.ObservableList;

import static javafx.application.Application.launch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import AdventureModel.Passage;
import AdventureModel.Room;
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
import javafx.scene.input.MouseEvent;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class MapView {
    private Circle c1;
    private ImageView map;
    private AdventureGameView adventureGameView;
    private GridPane oPane;
    private Button exitButton;
    private Button killButton = new Button();
    private Button cherryTree = new Button();

    public MapView(AdventureGameView adventureGameView) throws IOException {
        this.adventureGameView = adventureGameView;
        // image: https://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap.jpg
        // "views" +　name
        String head = this.adventureGameView.model.getDirectoryName();
        String backgroundImage = head + "/NarniaMap.jpg";
        map = new ImageView(new Image(new File(backgroundImage).toURI().toString()));
        // Set only the cirlce
        c1 = new Circle(8);
        c1.setFill(Color.WHITE);
        c1.setTranslateX(0);
        c1.setTranslateY(90);

        GridPane test = paneSetUp();

        oPane = adventureGameView.gridPane;
        var scene = new Scene(test,  1000, 800);
        adventureGameView.stage.setScene(scene);

        Location test2 = new Location(adventureGameView);
        try {
            test2.allLocation();
        } catch (IOException e) {}
    }
    
    public GridPane paneSetUp() throws IOException {
        // construct the scene contents over a stacked background.
        StackPane layout = new StackPane();
        layout.getChildren().setAll(
            map
        );

        // Put button on the pane
        createButton(layout);

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

    /**
     * Create button on the stack pane
     * @throws IOException
     */
    public void createButton(StackPane layout) throws IOException {
        Location location = new Location(this.adventureGameView);
        // HashMap<roomNumber, roomCoordinate>
        HashMap<Integer, int[]> locationList = location.allLocation();
        int currentRmNum = this.adventureGameView.model.getPlayer().getCurrentRoom().getRoomNumber();
        // Current player location
        String currRmName = location.getRoomName(currentRmNum);
        Button currButton = new Button();
        // Button
        makeButton(currButton, currRmName, "false", locationList, currentRmNum);
        // player location
        c1.setTranslateX(locationList.get(currentRmNum)[0]);
        c1.setTranslateY(locationList.get(currentRmNum)[1] - 40);
        layout.getChildren().addAll(
            currButton, c1
        );

        // Seach for all possible path, set red color to represent
        List<Passage> path = this.adventureGameView.model.getPlayer().getCurrentRoom().getMotionTable().getDirection();
        ArrayList<Integer> allRoom = new ArrayList<Integer>();
        for (Passage ele : path) {
            int nextRm = ele.getDestinationRoom();
            allRoom.add(nextRm);
            String dir = ele.getDirection();
            Button tempButton = new Button();
            if (ele.getKeyName() != null) {
                if (ele.getKeyName().endsWith("Troll")) {
                    makeButton(tempButton, location.getRoomName(nextRm), "troll", locationList, nextRm);
                }
                else {
                    makeButton(tempButton, location.getRoomName(nextRm), "false", locationList, nextRm);
                }
            }
            else {
                    makeButton(tempButton, location.getRoomName(nextRm), "false", locationList, nextRm);
            }
            tempButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override 
                public void handle(ActionEvent t) {
                    location.moveDot(tempButton, c1, dir, exitButton);
                }
            });
            layout.getChildren().addAll(
                tempButton
            );
        }

        // Search for all visited place
        List<Room> roomList = new ArrayList<>(this.adventureGameView.model.getRooms().values());
        for (Room ele : roomList) {
            if (allRoom.contains(ele.getRoomNumber()) || ele.getRoomNumber() == this.adventureGameView.model.getPlayer().getCurrentRoom().getRoomNumber()) {
                continue;
            }
            if (ele.getVisited()) {
                Button notButton = new Button();
                System.out.println(ele.getRoomName());
                makeButton(notButton, location.getRoomName(ele.getRoomNumber()), "true", locationList, ele.getRoomNumber());
                notButton.setDisable(true);
                layout.getChildren().addAll(
                    notButton
                );
            }
        }
    }

    /**
     * Make button and related stuff
     * @return made button
     */
    public void makeButton(Button b, String name, String visit, HashMap<Integer, int[]> loca, int num) {
        b.setText(name);
        b.setFont(new Font(this.adventureGameView.fontSize));
        if (visit.equals("false")) {
            b.setStyle("-fx-base: firebrick;");
        }
        else if (visit.equals("true")) {
            b.setStyle("-fx-base: grey;");
        }
        else {
            b.setStyle("-fx-base: purple;");
        }
        b.setTranslateX(loca.get(num)[0]);
        b.setTranslateY(loca.get(num)[1]);
    }

    public void exit() {
        adventureGameView.stage.setScene(this.adventureGameView.scene);
    }

}