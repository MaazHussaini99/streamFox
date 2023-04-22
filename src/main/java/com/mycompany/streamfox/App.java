package com.mycompany.streamfox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {
    
     static Scene scene;
     static Stage stage;
    
    static double xOffset = 0;
    static double yOffset = 0;

    static int width = 330;
    static int height = 400;
    
  
    @Override
    public void start(Stage stage) throws IOException {
        
        //int width = 330;
        //int height = 400;
        App.stage = stage;
        
        scene = new Scene(loadFXML("authentication"), width, height);
        stage.initStyle(StageStyle.UNDECORATED);
        
        scene.setOnMousePressed(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        
        //move around here
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        
        stage.setScene(scene);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("logo")));
        stage.setFullScreenExitHint("");
        
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException {
        FirebaseStart.initializeFirebase();
        FirebaseStart.initializeFirestore();
        
        launch();
    }
    
     public static void fullscreen(){
        stage.setFullScreen(!stage.isFullScreen());
    }
     
    public static double getWidth(){
        return stage.getWidth();
    }

    public static double getHeight(){
        return stage.getHeight();
    } 
     
    public static void setWidth(int widthNew){
        width = widthNew;
    }

    public static void setHeight(int heightNew){
        height = heightNew;
    } 

}