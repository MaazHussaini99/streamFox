package com.mycompany.streamfox;

import com.google.firebase.auth.FirebaseAuth;
import static com.mycompany.streamfox.App.scene;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import static com.mycompany.streamfox.App.xOffset;
import static com.mycompany.streamfox.App.yOffset;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class PrimaryVideoController implements Initializable{
    private FirebaseAuth firebaseAuth;
      
    @FXML
    private AnchorPane backPane;

    @FXML
    private AnchorPane frontPane;
    
    @FXML
    private AnchorPane topBar;
    
    @FXML
    private ImageView closeWindow;

    @FXML
    private ImageView minimizeWindow;
    
    @FXML
    private ImageView menuOpen;
    
    @FXML
    private WebView webVideoView;
    
    @FXML
    private ImageView channelPic;

    @FXML
    private Label channelTxt;
    
    @FXML
    private VBox recommendedTab;
    
    @FXML
    private Label titleTxt;
    
    private WebEngine we;
    
    private int onOff = 0;
    
    VBox[] testvb;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] sthumbs = {"https://img.youtube.com/vi/jfKfPfyJRdk/sddefault.jpg",
        "https://img.youtube.com/vi/nWji9F-WP6k/sddefault.jpg",
        "https://img.youtube.com/vi/DOWDNBu9DkU/sddefault.jpg",
        "https://img.youtube.com/vi/CpLdL8ONEm4/sddefault.jpg",
        "https://img.youtube.com/vi/YaF4yFILMi4/sddefault.jpg",
        "https://img.youtube.com/vi/qxicoCHRStA/sddefault.jpg"};
        
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

        we = webVideoView.getEngine();
        
        loadPage();
        
        
        
        String[] thumbs = {"https://img.youtube.com/vi/jfKfPfyJRdk/sddefault.jpg",
        "https://img.youtube.com/vi/YLt73w6criQ/sddefault.jpg",
        "https://img.youtube.com/vi/DOWDNBu9DkU/sddefault.jpg",
        "https://img.youtube.com/vi/CpLdL8ONEm4/sddefault.jpg",
        "https://img.youtube.com/vi/YaF4yFILMi4/sddefault.jpg",
        "https://img.youtube.com/vi/qxicoCHRStA/sddefault.jpg"};
        testvb = new VBox[7];
        for(int i = 0; i < 6; i++){
        testvb[i] = new VBox();
        ImageView imv = new ImageView();
        Image img = new Image(thumbs[i]);
        imv.setFitWidth(130);
        imv.setFitHeight(80);
        imv.setImage(img);
        Label tlabel = new Label();
        tlabel.setText("Title testing");
        testvb[i].getChildren().add(imv);
        testvb[i].getChildren().add(tlabel);
        
        recommendedTab.getChildren().add(testvb[i]);}
        
        
    }
        
    public void loadPage(){
        we.load("https://www.youtube.com/embed/_ZKTB_E52t8");
    }
    
    @FXML
    void menuMove(MouseEvent event) {
        if(onOff == 0){
        frontPane.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.seconds(0.2),frontPane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.2),frontPane);
        tt.setByX(+200);
        tt.play();
            
            onOff=1;
        }
        else if(onOff == 1){
        
        FadeTransition ft = new FadeTransition(Duration.seconds(0.1),frontPane);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.2),frontPane);
        tt.setByX(-200);
        tt.play();
        //frontPane.setVisible(false);
        
            onOff=0;
        }
        
    }
    
    public PrimaryVideoController(){
        User user = User.getInstance();
        System.out.println(user);
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
        App.setRoot("primary");
    }

    @FXML
    void minimizeCommand(MouseEvent event) {
            App.stage.setIconified(true);
    }
    
    /**
     * exits the application
     */
    @FXML
    private void exit(){
        System.out.println("exit");
        System.exit(0);
    }
    
    /**
     * switches the application to and from fullscreen mode
     */
    @FXML
    private void fullscreen(){
        firebaseAuth  = FirebaseAuth.getInstance();
        App.fullscreen();
        
        String[] thumbs = {"https://img.youtube.com/vi/jfKfPfyJRdk/sddefault.jpg",
        "https://img.youtube.com/vi/YLt73w6criQ/sddefault.jpg",
        "https://img.youtube.com/vi/DOWDNBu9DkU/sddefault.jpg",
        "https://img.youtube.com/vi/CpLdL8ONEm4/sddefault.jpg",
        "https://img.youtube.com/vi/YaF4yFILMi4/sddefault.jpg",
        "https://img.youtube.com/vi/qxicoCHRStA/sddefault.jpg"};
        
        
        if(webVideoView.getPrefWidth() == 512)
        {
            webVideoView.setPrefWidth(1024);
            webVideoView.setPrefHeight(576);
            testvb = new VBox[7];
        recommendedTab.getChildren().clear();
        for(int i = 0; i < 6; i++){
        testvb[i] = new VBox();
        
        ImageView imv = new ImageView();
        Image img = new Image(thumbs[i]);
        imv.setFitWidth(260);
        imv.setFitHeight(160);
        imv.setImage(img);
        Label tlabel = new Label();
        tlabel.setText("Title testing");
        testvb[i].getChildren().add(imv);
        testvb[i].getChildren().add(tlabel);
        
        recommendedTab.getChildren().add(testvb[i]);}
        }else{
            webVideoView.setPrefWidth(512);
            webVideoView.setPrefHeight(288);
            recommendedTab.getChildren().clear();
            for(int i = 0; i < 6; i++){
        testvb[i] = new VBox();
        ImageView imv = new ImageView();
        Image img = new Image(thumbs[i]);
        
        imv.setFitWidth(130);
        imv.setFitHeight(80);
        imv.setImage(img);
        Label tlabel = new Label();
        tlabel.setText("Title testing");
        testvb[i].getChildren().add(imv);
        testvb[i].getChildren().add(tlabel);
        
        recommendedTab.getChildren().add(testvb[i]);}
        }
        
                   
        //TODO: make the interface more dynamic (hard)
        
       
        
    }
   
    
}
