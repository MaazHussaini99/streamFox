/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import static com.mycompany.streamfox.AuthController.day;
import static com.mycompany.streamfox.PrimaryHomeController.dateString;
import static com.mycompany.streamfox.PrimaryVideoController.channelStartText;
import static com.mycompany.streamfox.PrimaryVideoController.startVid;
import static com.mycompany.streamfox.PrimaryVideoController.titleStartText;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.ProprietaryFeature;
import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import com.teamdev.jxbrowser.fullscreen.event.FullScreenEvent;
import com.teamdev.jxbrowser.fullscreen.*;
import java.util.Calendar;
import java.util.Map;
import javafx.stage.Screen;

public class TwitchController implements Initializable {

    @FXML
    private AnchorPane backPane;

    @FXML
    private ImageView channelPic;

    @FXML
    private Label channelTxt;

    @FXML
    private ImageView closeWindow;

    @FXML
    private VBox videoView;

    @FXML
    private ImageView closeWindow1;

    @FXML
    private AnchorPane frontPane;

    @FXML
    private ImageView menuOpen;

    @FXML
    private ImageView minimizeWindow;

    @FXML
    private VBox recommendedTab;

    @FXML
    private TextField searchTxtField;

    @FXML
    private Label titleTxt;

    @FXML
    private AnchorPane topBar;

    @FXML
    private Button userNameMenuBtn;

    @FXML
    private Circle userProfView;

    @FXML
    private AnchorPane basePane;

    @FXML
    private VBox browserVBox;

    @FXML
    private VBox webVBox;

    @FXML
    private AnchorPane aP;

    private WebEngine we;

    private int onOff = 0;

    public static VidObj[] Searchresults;

    VBox[] testvb;

    User user = User.getInstance();
    UserData userData = UserData.getInstance();

    SearchListResponse relatedVids;
    CommentThreadListResponse comments;

      public static double totalTwitchWeeklyWatchTime;
    static String startVid;
    static String titleStartText;
    static String channelStartText;
       private long startTime = 0;
    private long stopTime = 0;
       private boolean running = false;
    private long elapsedTime = 0;

    private EngineOptions options;
    private Engine engine;
    private Browser browser;
    private BrowserView view;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .enableProprietaryFeature(ProprietaryFeature.AAC)
                .enableProprietaryFeature(ProprietaryFeature.H_264)
                .enableProprietaryFeature(ProprietaryFeature.WIDEVINE)
                .licenseKey("1BNDHFSC1G5ZOFBWG6WQUSLCBTDAYZZXMAP2GRH6RECP8NHENP4ZY4YHBV1MUUDQTXFCFF")
                .build();

        engine = Engine.newInstance(options);
        browser = engine.newBrowser();
        //loadPage(startVid);
        browser.navigation().loadUrl("https://player.twitch.tv/?channel="+channelStartText+"&parent=localhost&autoplay=false");
        view = BrowserView.newInstance(browser);
        view.setPrefSize(512, 288);

        view.setVisible(true);

//        FullScreen fullScreen = browser.fullScreen();
//        fullScreen.addFullScreenListener(event -> {
//            if (event.isFullScreen()) {
//                System.out.println("Entered full screen mode");
//            } else {
//                System.out.println("Exited full screen mode");
//            }
//        });
        videoView.getChildren().add(view);

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


        titleTxt.setText(titleStartText);
        channelTxt.setText(channelStartText);
        System.out.println(startVid);

        userNameMenuBtn.setText(((String) userData.getProfileDataMap().get("fname")) + " " + ((String) userData.getProfileDataMap().get("lname")));
        userProfView.setFill(new ImagePattern(new Image((String) userData.getProfileDataMap().get("profileImage"))));
         startTimer();
      

    }

    @FXML
    void searchFunction(ActionEvent event) throws IOException {

    }

    @FXML
    void closeCommand(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("primary_Home");
             stopTimer();
           storeWatchtime();
    }

    @FXML
    void switchToYT(ActionEvent event) throws IOException {
        App.setRoot("Youtube");
             stopTimer();
           storeWatchtime();
    }

    @FXML
    void switchToTwitch(ActionEvent event) throws IOException {
           App.setRoot("Twitch_video");
                stopTimer();
           storeWatchtime();
    }

    @FXML
    void switchToProfile(ActionEvent event) throws IOException {
        App.setRoot("primary_Profile");
             stopTimer();
           storeWatchtime();
    }

    @FXML
    void switchToSettings(ActionEvent event) throws IOException {
        App.setRoot("Settings");
             stopTimer();
           storeWatchtime();
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

    /**
     * switches the application to and from fullscreen mode
     */
    @FXML
    private void fullscreen() {
        App.fullscreen();
        if (App.stage.isFullScreen() == false) {
            view.setPrefSize(512, 288);
        } else {
            view.setPrefSize(1024, 576);
        }
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
     private void startTimer() {
        startTime = System.currentTimeMillis();
        running = true;

    }

    private void stopTimer() {
        stopTime = System.currentTimeMillis();
        running = false;
        elapsedTime = stopTime - startTime;
     
    }
    
         double  totalTwitchWatchTimeFromYesterday;
    
    public void caluluateTwitchTotalWeeklyWatchtime(){
        
         
                switch (day) {
              case Calendar.MONDAY:
         totalTwitchWatchTimeFromYesterday= (double)  userData.getTwitchDailyWatchDataMap().get( "WeeklyWatchTime");   
            totalTwitchWeeklyWatchTime=  totalTwitchWatchTimeFromYesterday+((double)  userData.getTwitchDailyWatchDataMap().get(dateString));            
           Map<String, Object> WeeklyWatchTimeMap = UserData.getInstance().getTwitchDailyWatchDataMap();
                     WeeklyWatchTimeMap.put("WeeklyWatchTime", totalTwitchWeeklyWatchTime) ;
                    UserData.getInstance().updateTwitchWatchTime(  WeeklyWatchTimeMap);
                    
        
        break;

    case Calendar.TUESDAY:
          totalTwitchWatchTimeFromYesterday= (double)  userData.getTwitchDailyWatchDataMap().get( "WeeklyWatchTime");   
            totalTwitchWeeklyWatchTime=  totalTwitchWatchTimeFromYesterday+((double)  userData.getTwitchDailyWatchDataMap().get(dateString));   
           Map<String, Object> WeeklyTuesdayWatchTimeMap =UserData.getInstance().getTwitchDailyWatchDataMap();
                     WeeklyTuesdayWatchTimeMap.put("WeeklyWatchTime", totalTwitchWeeklyWatchTime) ;
                    UserData.getInstance().updateTwitchWatchTime(  WeeklyTuesdayWatchTimeMap);
                     
        
        break;


    case Calendar.WEDNESDAY:
   totalTwitchWatchTimeFromYesterday= (double)  userData.getTwitchDailyWatchDataMap().get( "WeeklyWatchTime");   
             totalTwitchWeeklyWatchTime=  totalTwitchWatchTimeFromYesterday+((double)  userData.getTwitchDailyWatchDataMap().get(dateString));      
           Map<String, Object> WeeklyWednesdayWatchTimeMap = UserData.getInstance().getTwitchDailyWatchDataMap();
                     WeeklyWednesdayWatchTimeMap.put("WeeklyWatchTime", totalTwitchWeeklyWatchTime) ;
                    UserData.getInstance().updateTwitchWatchTime(  WeeklyWednesdayWatchTimeMap);
    
        break;

    case Calendar.THURSDAY:
                 totalTwitchWatchTimeFromYesterday= (double)  userData.getTwitchDailyWatchDataMap().get( "WeeklyWatchTime");   
            totalTwitchWeeklyWatchTime=  totalTwitchWatchTimeFromYesterday+((double)  userData.getTwitchDailyWatchDataMap().get(dateString));   
           Map<String, Object> WeeklyThursdayWatchTimeMap = UserData.getInstance().getTwitchDailyWatchDataMap();
                     WeeklyThursdayWatchTimeMap .put("WeeklyWatchTime", totalTwitchWeeklyWatchTime) ;
                    UserData.getInstance().updateTwitchWatchTime(  WeeklyThursdayWatchTimeMap );
                    
   
        
        break;

    case Calendar.FRIDAY:
              totalTwitchWatchTimeFromYesterday= (double)  userData.getTwitchDailyWatchDataMap().get( "WeeklyWatchTime");   
             totalTwitchWeeklyWatchTime=  totalTwitchWatchTimeFromYesterday+((double)  userData.getTwitchDailyWatchDataMap().get(dateString));   
           Map<String, Object> WeeklyFridayWatchTimeMap = UserData.getInstance().getTwitchDailyWatchDataMap();
                    WeeklyFridayWatchTimeMap .put("WeeklyWatchTime", totalTwitchWeeklyWatchTime) ;
                    UserData.getInstance().updateTwitchWatchTime(     WeeklyFridayWatchTimeMap);
                    
        break;

    case Calendar.SATURDAY:
             
            totalTwitchWatchTimeFromYesterday= (double)  userData.getTwitchDailyWatchDataMap().get( "WeeklyWatchTime");   
           totalTwitchWeeklyWatchTime=  totalTwitchWatchTimeFromYesterday+((double)  userData.getTwitchDailyWatchDataMap().get(dateString));   
           Map<String, Object> WeeklySaturdayWatchTimeMap = UserData.getInstance().getTwitchDailyWatchDataMap();
                  WeeklySaturdayWatchTimeMap.put("WeeklyWatchTime", totalTwitchWeeklyWatchTime) ;
                    UserData.getInstance().updateTwitchWatchTime( WeeklySaturdayWatchTimeMap);
        break;


    case Calendar.SUNDAY:
           totalTwitchWatchTimeFromYesterday= (double)  userData.getTwitchDailyWatchDataMap().get( "WeeklyWatchTime");   
         totalTwitchWeeklyWatchTime=  totalTwitchWatchTimeFromYesterday+((double)  userData.getTwitchDailyWatchDataMap().get(dateString));   
           Map<String, Object> WeeklySundayWatchTimeMap = UserData.getInstance().getTwitchDailyWatchDataMap();
                  WeeklySundayWatchTimeMap .put("WeeklyWatchTime", totalTwitchWeeklyWatchTime) ;
                    UserData.getInstance().updateTwitchWatchTime(  WeeklySundayWatchTimeMap);
        
        break;
    }

        //TODO: make the interface more dynamic (hard)
    }
     public void storeWatchtime(){
          double currenttime = (double)userData.getTwitchDailyWatchDataMap().get(dateString);
          double miunteswatchtime = (double) elapsedTime /6000; // convert to minutes
         double newwatchtime = (double) elapsedTime / (1000 * 60 * 60); // convert to hours
          System.out.println("the watch time "+ miunteswatchtime );
               System.out.println("the watch time "+newwatchtime );
               currenttime +=  newwatchtime;
        
            Map<String, Object> dailyWatchTimeMap = UserData.getInstance().getTwitchDailyWatchDataMap();
                        //caluluateTotalWeeklyWatchtime(time);
                     dailyWatchTimeMap .put(dateString, currenttime);
                
                     UserData.getInstance().updateTwitchWatchTime( dailyWatchTimeMap);
                      caluluateTwitchTotalWeeklyWatchtime();
        
    }

    public void loadPage(String vid) {
        //browser.navigation().loadUrl("https://player.twitch.tv/?channel=shroud&parent=localhost&autoplay=false");
        //we.load("https://player.twitch.tv/?video=41280532217&parent=localhost&autoplay=false");
    }
}