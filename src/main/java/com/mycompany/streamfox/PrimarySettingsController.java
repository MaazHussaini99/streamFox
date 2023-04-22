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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
    private Spinner<Integer> DisneyDailyWatchTime;

    @FXML
    private Spinner<Integer> DisneyWeeklyWatchTime;

    @FXML
    private CheckBox Netflix;

    @FXML
    private Spinner<Integer> TwitchDailyWatchTime;

    @FXML
    private Spinner<Integer> TwitchWeeklyWatchTime;
    
    @FXML
    private Spinner<Integer> DailyWatchTime;

    @FXML
    private Spinner<Integer> WeeklyWatchTime;

    @FXML
    private CheckBox Youtube;

    @FXML
    private Spinner<Integer> YoutubeDailyWatchTime;

    @FXML
    private Spinner<Integer> YoutubeWeeklyWatchTime;
    @FXML
    private Button DeleteYA;
    @FXML
    private Button DisableYA;
    @FXML
    private MenuButton StreamingServiceMenu;

    @FXML
    private CheckBox Disney;
    
    @FXML
    private Button userNameMenuBtn;
    
    User user = User.getInstance();
    UserData userData = UserData.getInstance();
    
    @FXML
    private Circle userProfView;

    private int onOff = 0;
    
    static int YoutubeDailyValue=1;
     static int DisneyDailyValue=1;
     static int TwitchDailyValue=1;
     
    static int YoutubeWeeklyValue=1;
     static int DisneyWeeklyValue=1;
     static int TwitchWeeklyValue=1;
     
    static int totalDaily;
    static int totalWeekly;
    
     boolean hasYoutubeDailyChanged=false;
       boolean hasDisneyDailyChanged=false;
        boolean hasTwitchDailyChanged=false;
        
        boolean hasYoutubeWeeklyChanged=false;
       boolean hasDisneyWeeklyChanged=false;
        boolean hasTwitchWeeklyChanged=false;


    private void setValues() { // temp 
        DailyWatchTime.setUserData(this);
        WeeklyWatchTime.setUserData(this);
        


    }

 /*   void StreamingSerivceAvailbility(ActionEvent event) throws IOException {
        // Allows a user to descide what Streaming Service they have using a check
        if (Netflix.isSelected()) {

        }
//        if (Twitch.isSelected()) {

        }
        if (Youtube.isSelected()) {

        }

    }*/
    
//    FXCollections.observableList("1 Hour", "2 Hours", "3 Hours", "4 Hours",
//                "5 Hours", "6 Hours", "7 Hours", "8 Hours", "9 Hours", "10 Hours", "11 Hours", "12 Hours", "13 Hours",
//                "14 Hours", "15 Hours", "16 Hours, 17 Hours , 18 Hours , 19 Hours, 20 Hours , 21 Hours, 22 Hours, 23 Hours , 24 Hours")
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
    void ResetPassword(ActionEvent event) throws IOException {
    
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

        }
        );
        

        //move around here
        topBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.stage.setX(event.getScreenX() - App.xOffset);
                App.stage.setY(event.getScreenY() - App.yOffset);
            }
        });
        
        SpinnerValueFactory<Integer> valueFacDaily = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,24);
        valueFacDaily.setValue(24);
        SpinnerValueFactory<Integer> TwitchvalueFacDaily = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,24);
        valueFacDaily.setValue(24);
        SpinnerValueFactory<Integer> YoutubevalueFacDaily = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,24);
        valueFacDaily.setValue(24);
        SpinnerValueFactory<Integer> DisneyvalueFacDaily = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,24);
        valueFacDaily.setValue(24);
        
        SpinnerValueFactory<Integer> valueFacWeekly = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,168);
        valueFacWeekly.setValue(168);
        SpinnerValueFactory<Integer> TwitchvalueFacWeekly = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,168);
        valueFacWeekly.setValue(168);
        SpinnerValueFactory<Integer> YoutubevalueFacWeekly = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,168);
        valueFacWeekly.setValue(168);
        SpinnerValueFactory<Integer> DisneyvalueFacWeekly = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,168);
        valueFacWeekly.setValue(168);
        
        
        //Setting watch time area we need listeners for each, and as the value is changed it should update firebase in the listener
        DailyWatchTime.setValueFactory(valueFacDaily);
        WeeklyWatchTime.setValueFactory(valueFacWeekly);
        
        YoutubeDailyWatchTime.setValueFactory(YoutubevalueFacDaily);
        YoutubeWeeklyWatchTime.setValueFactory(YoutubevalueFacWeekly);
        //DailyWatchTime.getValueFactory().setValue(3);
        
         YoutubeDailyWatchTime.valueProperty().addListener(new ChangeListener<Integer>(){
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                 YoutubeDailyValue= YoutubeDailyWatchTime.getValue();
                  if(( hasTwitchDailyChanged=true) ||  (hasDisneyDailyChanged=true)){
                    totalDaily=YoutubeDailyValue+DisneyDailyValue+TwitchDailyValue;
                    DailyWatchTime.getValueFactory().setValue(totalDaily);
                 }
             //  System.out.println("this is Current vlaue"+CurrentValueTest);
                 hasYoutubeDailyChanged=true;
            } 
        });
         
        
        TwitchDailyWatchTime.setValueFactory(TwitchvalueFacDaily);
        TwitchWeeklyWatchTime.setValueFactory(TwitchvalueFacWeekly);
        
           TwitchDailyWatchTime.valueProperty().addListener(new ChangeListener<Integer>(){
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                 TwitchDailyValue= TwitchDailyWatchTime.getValue();
                  if(( hasDisneyDailyChanged=true) ||  (hasYoutubeDailyChanged=true)){
                    totalDaily=YoutubeDailyValue+DisneyDailyValue+TwitchDailyValue;
                    DailyWatchTime.getValueFactory().setValue(totalDaily);
                 }
                hasTwitchDailyChanged=true;
            } 
        });
        
        DisneyDailyWatchTime.setValueFactory(DisneyvalueFacDaily);
        DisneyWeeklyWatchTime.setValueFactory(DisneyvalueFacWeekly);
        

  
        //added visual indicator for all spinners
   DisneyDailyWatchTime.valueProperty().addListener(new ChangeListener<Integer>(){
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                 DisneyDailyValue= DisneyDailyWatchTime.getValue();
                  if(( hasTwitchDailyChanged=true) ||  (hasYoutubeDailyChanged=true)){
                    totalDaily=YoutubeDailyValue+DisneyDailyValue+TwitchDailyValue;
                    DailyWatchTime.getValueFactory().setValue(totalDaily);
                 }
                hasDisneyDailyChanged=true;
                
             //  System.out.println("this is Current vlaue"+CurrentValueTest);
            } 
        });
  
       
        DailyWatchTime.valueProperty().addListener(new ChangeListener<Integer>(){
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                
                Thread  dwt = new Thread( () -> {
                    System.out.println("DailyWatchtimeThread – Going to sleep");


                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                Map<String, Object> dailyWatchTimeMap = UserData.getInstance().getWatchTimeDataMap();
        dailyWatchTimeMap.put("setDailyLimit", totalDaily);
        UserData.getInstance().updateTotalWatchTime(dailyWatchTimeMap);
               
        });
                  dwt.start(); 
                }
            });
            YoutubeWeeklyWatchTime.valueProperty().addListener(new ChangeListener<Integer>(){
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                 YoutubeWeeklyValue= YoutubeWeeklyWatchTime.getValue();
                  if(( hasTwitchWeeklyChanged=true) ||  (hasDisneyWeeklyChanged=true)){
                    totalWeekly=YoutubeWeeklyValue+DisneyWeeklyValue+TwitchWeeklyValue;
                       WeeklyWatchTime.getValueFactory().setValue(totalWeekly);
                 }
             //  System.out.println("this is Current vlaue"+CurrentValueTest);
                 hasYoutubeWeeklyChanged=true;
            } 
        });
         
        
        
        TwitchWeeklyWatchTime.valueProperty().addListener(new ChangeListener<Integer>(){
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                 TwitchWeeklyValue=TwitchWeeklyWatchTime.getValue();
                  if(( hasDisneyWeeklyChanged=true) ||  (hasYoutubeWeeklyChanged=true)){
                    totalWeekly=YoutubeWeeklyValue+DisneyWeeklyValue+TwitchWeeklyValue;
                       WeeklyWatchTime.getValueFactory().setValue(totalWeekly);
                 }
                hasTwitchWeeklyChanged=true;
            } 
        });
        
    

  
        //added visual indicator for all spinners
   DisneyWeeklyWatchTime.valueProperty().addListener(new ChangeListener<Integer>(){
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                 DisneyWeeklyValue= DisneyWeeklyWatchTime.getValue();
                  if(( hasTwitchWeeklyChanged=true) ||  (hasYoutubeWeeklyChanged=true)){
                   totalWeekly=YoutubeWeeklyValue+DisneyWeeklyValue+TwitchWeeklyValue;
                    WeeklyWatchTime.getValueFactory().setValue(totalWeekly);
                 }
                  
                hasDisneyWeeklyChanged=true;
                
             //  System.out.println("this is Current vlaue"+CurrentValueTest);
            } 
        });
   
   
             WeeklyWatchTime.valueProperty().addListener(new ChangeListener<Integer>(){
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
               Thread  wwt = new Thread( () -> {
                    System.out.println("WeeklyWatchtime Thread – Going to sleep");


                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                Map<String, Object> weeklyWatchTimeMap = UserData.getInstance().getWatchTimeDataMap();
        weeklyWatchTimeMap.put("setWeeklyLimit", totalWeekly);
        UserData.getInstance().updateTotalWatchTime(weeklyWatchTimeMap);
               
        });
                  wwt.start(); 
                }
            
        });
        
        userNameMenuBtn.setText(((String) userData.getProfileDataMap().get("fname")) + " " + ((String) userData.getProfileDataMap().get("lname")));
        userProfView.setFill(new ImagePattern(new Image((String) userData.getProfileDataMap().get("profileImage"))));
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
        App.setRoot("Youtube");
    }
    
@FXML
    void switchToTwitch(ActionEvent event) throws IOException {
        App.setRoot("TwitchPrimary");
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
