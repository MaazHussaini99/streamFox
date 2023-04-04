/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import static com.mycompany.streamfox.App.scene;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import static com.mycompany.streamfox.App.xOffset;
import static com.mycompany.streamfox.App.yOffset;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DropdownButton;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class PrimarySettingsController implements Initializable {

    private FirebaseAuth firebaseAuth;

    @FXML
    private ImageView menuOpen;

    @FXML
    private ImageView minimizeWindow;

    @FXML
    private AnchorPane topBar;

    @FXML
    private ImageView closeWindow;

    @FXML
    private ImageView closeWindow1;

    @FXML
    private AnchorPane frontPane;

    @FXML
    private DropdownButton  WatchTimeLimit;

    @FXML
    private TextField YoutubeWeeklyWatchTime;
    @FXML
    private TextField YoutubeDailyWatchTime;

    @FXML
    private TextField NetflixWeeklyWatchTime;
    @FXML
    private TextField NetflixDailyWatchTime;

    @FXML
    private TextField DisneyWeeklyWatchTime;
    @FXML
    private TextField DisneyDailyWatchTime;

    @FXML
    private TextField WeeklyWatchTime;

    @FXML
    private TextField DailyWatchTime;
    @FXML
    private Button DeleteYA;
    @FXML
    private Button DisableYA;
    @FXML
    private MenuButton StreamingServiceMenu;

    @FXML
    private CheckBox Netflix;

    @FXML
    private CheckBox Disney;

    @FXML
    private CheckBox Youtube;

    private int onOff = 0;

    private void setValues() { // temp 
        DailyWatchTime.setUserData(this);
        WeeklyWatchTime.setUserData(this);
        WatchTimeLimit.getEventDispatcher();

    }

    void StreamingSerivceAvailbility(ActionEvent event) throws IOException {
        // Allows a user to descide what Streaming Service they have using a check
        if (Netflix.isSelected()) {

        }
        if (Disney.isSelected()) {

        }
        if (Youtube.isSelected()) {

        }

    }

    //  temp skeleton method for backend use 
    void setWatchTimeLimit(){
        DropdownButton dropdownButton = new DropdownButton();

 // temp structure 
if(dropdownButton.getItems().String.equals("1 Hour")){

}else if(dropdownButton.getItems().String.equals("2 Hours")){
    
}else if(dropdownButton.getItems().String.equals("3 Hours")){
    
}else if(dropdownButton.getItems().String.equals("4 Hours")){
    
}else if(dropdownButton.getItems().String.equals("5 Hours")){
    
}else if(dropdownButton.getItems().String.equals("6 Hours")){
    
}else if(dropdownButton.getItems().String.equals("7 Hours")){
    
}else if(dropdownButton.getItems().String.equals("8 Hours")){
    
}else if(dropdownButton.getItems().String.equals("9 Hours")){
    
}else if(dropdownButton.getItems().String.equals("10 Hours")){
    
}else if(dropdownButton.getItems().String.equals("11 Hours")){
    
}else if(dropdownButton.getItems().String.equals("12 Hours")){
    
}else if(dropdownButton.getItems().String.equals("13 Hours")){
    
}else if(dropdownButton.getItems().String.equals("14 Hours")){
    
}else if(dropdownButton.getItems().String.equals("15 Hours")){
    
}else if(dropdownButton.getItems().String.equals("16 Hours")){
    
}else if(dropdownButton.getItems().String.equals("17 Hours")){
    
}else if(dropdownButton.getItems().String.equals("18 Hours")){
    
}else if(dropdownButton.getItems().String.equals("19 Hours")){
    
}else if(dropdownButton.getItems().String.equals("20 Hours")){
    
}else if(dropdownButton.getItems().String.equals("21 Hours")){
    
}else if(dropdownButton.getItems().String.equals("22 Hours")){
    
}else if(dropdownButton.getItems().String.equals("23 Hours")){
    
}else if(dropdownButton.getItems().String.equals("24 Hours")){
    
} 
        
    








    
    }
    
    @FXML
    void DisableYourAcount(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Disable Cofirmation Requiered");
        alert.setHeaderText("Please press OK or Cancel to Confirm That you Want to Disable Your Account \n or Go Back to the Previous Menu ");
        alert.setResizable(false);
        alert.setContentText("Are you sure? ");
        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {

        } // alert is exited, no button has been pressed.
        else if (result.get() == ButtonType.OK) {

        } //oke button is pressed
        else if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
        // cancel button is pressed

    }

    @FXML
    void DeleteYourAcount(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Cofirmation Requiered");
        alert.setHeaderText("Please press OK or Cancel to Confirm that you want to Delete your Account \n or Go Back to the Previous Menu ");
        alert.setResizable(false);
        alert.setContentText("Are you sure?,Changes can not be undone");
        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {

        } // alert is exited, no button has been pressed.
        else if (result.get() == ButtonType.OK) {

        } //oke button is pressed
        else if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
        // cancel button is pressed
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        frontPane.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), frontPane);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();

        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1), frontPane);
        tt.setByX(-200);
        tt.play();

        topBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.xOffset = event.getSceneX();
                App.yOffset = event.getSceneY();
            }
        });

        //move around here
        topBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.stage.setX(event.getScreenX() - App.xOffset);
                App.stage.setY(event.getScreenY() - App.yOffset);
            }
        });
    }

    @FXML
    void menuMove(MouseEvent event) {
        if (onOff == 0) {
            frontPane.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.seconds(0.2), frontPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.2), frontPane);
            tt.setByX(+200);
            tt.play();

            onOff = 1;
        } else if (onOff == 1) {

            FadeTransition ft = new FadeTransition(Duration.seconds(0.1), frontPane);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();

            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.2), frontPane);
            tt.setByX(-200);
            tt.play();
            //frontPane.setVisible(false);

            onOff = 0;
        }

    }

    @FXML
    void switchToYT(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void switchToProfile(ActionEvent event) throws IOException {
        App.setRoot("primary_Profile");
    }

    @FXML
    void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("primary_Home");
    }

    /**
     * switches the application to and from fullscreen mode
     */
    @FXML
    private void fullscreen() {
        firebaseAuth = FirebaseAuth.getInstance();
        //toggles fullscreen on and off
        //TODO: make the interface more dynamic (hard)
        System.out.println("fullscreen");
        App.fullscreen();
    }

    @FXML
    void closeCommand(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void minimizeCommand(MouseEvent event) {
        App.stage.setIconified(true);
    }

    /**
     * exits the application
     */
    @FXML
    private void exit() {
        System.out.println("exit");
        System.exit(0);
    }

}
