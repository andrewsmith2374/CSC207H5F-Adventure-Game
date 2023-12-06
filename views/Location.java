package views;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import AdventureModel.AdventureObject;
import AdventureModel.Room;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Location {

    private AdventureGameView adventureGameView;
    private HashMap<Integer, int[]> allCoordinate;
    private HashMap<Integer, String> allRoomName;

    public Location(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;
        allCoordinate = new HashMap<Integer, int[]>();
        allRoomName = new HashMap<Integer, String>();
    }

    public HashMap<Integer, int[]> allLocation() throws IOException {

        String objectFileName = "";

        if (this.adventureGameView != null){
            objectFileName = this.adventureGameView.model.getDirectoryName() + "/coordinate.txt";
        }
        BufferedReader buff = new BufferedReader(new FileReader(objectFileName));

        while (buff.ready()) {
            // info
            String roomNumber = buff.readLine();
            String roomName = buff.readLine();
            String roomCoordinate = buff.readLine();
            String blank = buff.readLine();

            // convert to correct type
            int rmNm = Integer.parseInt(roomNumber);
            String[] temp = roomCoordinate.split(",");
            int pointX = Integer.parseInt(temp[0]);
            int pointY = Integer.parseInt(temp[1]);
            int[] pointXY = {pointX, pointY};
            
            // put into hashmap
            allCoordinate.put(rmNm, pointXY);
            allRoomName.put(rmNm, roomName);
        }
        
        return allCoordinate;
    }

    /**
     * Move player location with animation
     */
    public void moveDot(Button button, Circle player, String direction, Button exit) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(player);
        transition.setToX(button.getTranslateX());
        transition.setToY(button.getTranslateY() - 40);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.play();
        // Create a time gap
        PauseTransition gap = new PauseTransition(Duration.seconds(2));
        gap.setOnFinished(event -> {
            exit.fire();
            // Move player
            this.adventureGameView.submitEvent(direction);
        });
        gap.play();
    }
    
    /**
     * return the room name with given room number
     * @return room name
     */
    public String getRoomName(int rmNm) {
        return allRoomName.get(rmNm);
    }

}
