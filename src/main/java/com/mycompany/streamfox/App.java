package com.mycompany.streamfox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        //variables
        int width = 640;
        int height = 480;
        //this is for accessing the width and height easily later
        App.stage = stage;

        //stylesheet loading. make sure that the file is in the folder marked for resources in your IDE.
        String css = this.getClass().getResource("primarystylesheet.css").toExternalForm();

        //scene
        scene = new Scene(loadFXML("primary"), width, height);
        //this actually adds the stylesheet to the scene
        scene.getStylesheets().add(css);
        //Same purpose as stage
        scene.setFill(Color.TRANSPARENT);

        //stage
        stage.setScene(scene);
        //TODO: Draggable without the windows container around everything?
        //Removes the typical window around the application. Needs a new way to close the application.
        stage.initStyle(StageStyle.TRANSPARENT);
        //hides the "Press esc to exit fullscreen" prompt on the screen by setting it to empty string
        stage.setFullScreenExitHint("");

        //always last instruction in method
        stage.show();
    }

    /**
     * changes the application to be fullscreen if it is not fullscreen, or exits fullscreen if it is
     */
    public static void fullscreen(){
        stage.setFullScreen(!stage.isFullScreen());
    }

    //TODO: might be better to just set public globals for this

    /**
     * returns width of the stage
     */
    public static double getWidth(){
        return stage.getWidth();
    }

    /**
     * returns height of the stage
     */
    public static double getHeight(){
        return stage.getHeight();
    }

    /**
     * @param fxml the name of the fxml file to load, absent the file extension
     * @return the loaded fxml file
     * @throws IOException if the fxml file is not found
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * main method
     */
    public static void main(String[] args) {
        //TODO: multithread because launch blocks the thread until application end
        launch();
    }

}