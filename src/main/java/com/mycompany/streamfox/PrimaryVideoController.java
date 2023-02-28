package com.mycompany.streamfox;

import com.google.firebase.auth.FirebaseAuth;
import static com.mycompany.streamfox.App.scene;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import static com.mycompany.streamfox.App.xOffset;
import static com.mycompany.streamfox.App.yOffset;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
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
    
    private WebEngine we;
    
    private int onOff = 0;
    
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

        we = webVideoView.getEngine();
        loadPage();
        
    }
        
    public void loadPage(){
        we.load("http://www.youtube.com/embed/utUPth77L_o?autoplay=1");
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
        //toggles fullscreen on and off
        //TODO: make the interface more dynamic (hard)
        System.out.println("fullscreen");
        App.fullscreen();
    }
   
    
}
