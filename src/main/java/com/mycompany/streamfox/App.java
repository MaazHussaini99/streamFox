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
        App.stage = stage;

        //stylesheet
        String css = this.getClass().getResource("primarystylesheet.css").toExternalForm();

        //scene
        scene = new Scene(loadFXML("primary"), width, height);
        scene.getStylesheets().add(css);
        scene.setFill(Color.TRANSPARENT);

        //stage
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    /**
     * changes the application to be fullscreen if it is not fullscreen, or exits fullscreen if it is
     */
    public static void fullscreen(){
        stage.setFullScreen(!stage.isFullScreen());
    }

    public static double getWidth(){
        return stage.getWidth();
    }

    public static double getHeight(){
        return stage.getHeight();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}