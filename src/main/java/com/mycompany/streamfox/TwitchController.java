/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class TwitchController implements Initializable{

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

    private WebEngine we;

    private int onOff = 0;

    public static VidObj[] Searchresults;

    private BrowserView view;
    private EngineOptions options;
    private Engine engine;
    private Browser browser;

    VBox[] testvb;

    User user = User.getInstance();
    UserData userData = UserData.getInstance();

    SearchListResponse relatedVids;
    CommentThreadListResponse comments;

    static String startVid;
    static String titleStartText;
    static String channelStartText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .enableProprietaryFeature(ProprietaryFeature.AAC)
                .enableProprietaryFeature(ProprietaryFeature.H_264)
                .licenseKey("1BNDHFSC1G5ZOFBWG6WQUSLCBTDAYZZXMAP2GRH6RECP8NHENP4ZY4YHBV1MUUDQTXFCFF")
                .build();
        
        engine = Engine.newInstance(options);
        browser = engine.newBrowser();
        //loadPage(startVid);
        browser.navigation().loadUrl("https://player.twitch.tv/?channel=shroud&parent=localhost&autoplay=false");
        view = BrowserView.newInstance(browser);
        view.setPrefSize(512, 288);
        
        view.setVisible(true);
        
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

        //titleTxt.setText(titleStartText);
        //channelTxt.setText(channelStartText);

        System.out.println(startVid);
        
        userNameMenuBtn.setText(((String) userData.getProfileDataMap().get("fname")) + " " + ((String) userData.getProfileDataMap().get("lname")));
        userProfView.setFill(new ImagePattern(new Image((String) userData.getProfileDataMap().get("profileImage"))));

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
    }

    @FXML
    void switchToYT(ActionEvent event) throws IOException {
        App.setRoot("Youtube");
    }

    @FXML
    void switchToTwitch(ActionEvent event) throws IOException {
        App.setRoot("Twitch_video_");
    }

    @FXML
    void switchToProfile(ActionEvent event) throws IOException {
        App.setRoot("primary_Profile");
    }

    @FXML
    void switchToSettings(ActionEvent event) throws IOException {
        App.setRoot("Settings");
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
        if(App.stage.isFullScreen() == false){
            view.setPrefSize(512, 288);
        }else{
            view.setPrefSize(1024,576);
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

    public void loadPage(String vid) {
        browser.navigation().loadUrl("https://player.twitch.tv/?channel=shroud&parent=localhost&autoplay=false");
        //we.load("https://player.twitch.tv/?video=41280532217&parent=localhost&autoplay=false");
    }
}
