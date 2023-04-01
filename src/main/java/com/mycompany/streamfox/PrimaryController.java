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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PrimaryController implements Initializable {
    
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
    private HBox videos1;
    
    @FXML
    private HBox videos2;

    @FXML
    private HBox videos3;
    
    VBox[] testvb;
    
    private int onOff = 0;
    
    private boolean fullscreenBool;
    
    public String VIDload;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fullscreenBool = false;
        frontPane.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5),frontPane);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1),frontPane);
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
        
        VidObj[] help1 = new VidObj[5];

        help1[0] = new VidObj("YLt73w6criQ", "I Paid A Real Assassin To Try To Kill Me", "MrBeast");
        help1[1] = new VidObj("dT6taoucBX4", "SCREAMS SCREAMS and MORE SCREAMS [Fears To Fathom: Norwood Hitchhike]", "CoryxKenshin");
        help1[2] = new VidObj("_F6YBwIPzmk", "Star Wars Jedi: Survivor - Official Story Trailer", "EA Star Wars");
        help1[3] = new VidObj("LtwaDBjNop0", "Resumen de FC Barcelona vs Real Madrid (2-1)", "LaLiga Santander");
        help1[4] = new VidObj("DOWDNBu9DkU", "Amazing Invention- This Drone Will Change Everything", "Mark Rober");
        
        
        VidObj[] help2 = new VidObj[5];
        help2[0] = new VidObj("scTOJJbecGw", "Fooling my Friend with the LOUDEST SOUND in Minecraft", "Doni Bobes");
        help2[1] = new VidObj("EDnwWcFpObo", "NMIXX 'Love Me Like This' M/V", "JYP Entertainment");
        help2[2] = new VidObj("bEKmOVP-SOI", "Can You ACTUALLY Win Money on Gameshows?", "Jaiden Animations");
        help2[3] = new VidObj("moIuur9GUws", "World's Brightest Flashlight | OT38", "Dude Perfect");
        help2[4] = new VidObj("q3FXUUV3hWA", "Different Childhood Sleepovers (pt.5) | Ep.1 Dtay Known", "Dtay Known");
        
        VidObj[] help3 = new VidObj[5];
        help3[0] = new VidObj("5RNrCRjZO0M", "I Played Diablo 4 Beta.. My HONEST Thoughts", "Asmongold TV ");
        help3[1] = new VidObj("S9EnUSSU7HI", "I Trapped 25 TikTokers In A Box", "Airrack");
        help3[2] = new VidObj("clJyTJ3vvh4", "Momoshiki vs Kawaki | Boruto: Naruto Next Generations", "Crunchyroll Collection");
        help3[3] = new VidObj("myNFxrTMczA", "Best Watercolor Art Wins $5,000!", "ZHC Crafts");
        help3[4] = new VidObj("PZM6j8bKnks", "Ben Affleck and Matt Damon on 'Air'", "CBS Sunday Morning");
        
        testvb = new VBox[6];
        for(int i = 0; i < help1.length; i++){
        testvb[i] = new VBox();
        ImageView imv = new ImageView();
        Image img = new Image("https://img.youtube.com/vi/"+help1[i].id+"/sddefault.jpg");
        imv.setFitWidth(200);
        imv.setFitHeight(100);
        imv.setImage(img);
        Label tlabel = new Label();
        tlabel.setMaxWidth(200);
        tlabel.setText(help1[i].title);
        
        int placeholder = i;
        imv.setOnMouseClicked( new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event){
                System.out.println("working");
                VIDload = help1[placeholder].id;
                try {
                    playVideoMode(event);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        testvb[i].getChildren().add(imv);
        testvb[i].getChildren().add(tlabel);
        
        videos1.getChildren().add(testvb[i]);}
        
        testvb = new VBox[6];
        for(int i = 0; i < help2.length; i++){
        testvb[i] = new VBox();
        ImageView imv = new ImageView();
        Image img = new Image("https://img.youtube.com/vi/"+help2[i].id+"/sddefault.jpg");
        imv.setFitWidth(200);
        imv.setFitHeight(100);
        imv.setImage(img);
        Label tlabel = new Label();
        tlabel.setMaxWidth(200);
        tlabel.setText(help2[i].title);
        
        int placeholder = i;
        imv.setOnMouseClicked( new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event){
                System.out.println("working");
                VIDload = help2[placeholder].id;
                try {
                    playVideoMode(event);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        testvb[i].getChildren().add(imv);
        testvb[i].getChildren().add(tlabel);
        
        videos2.getChildren().add(testvb[i]);}
        
        testvb = new VBox[6];
        for(int i = 0; i < help3.length; i++){
        testvb[i] = new VBox();
        ImageView imv = new ImageView();
        Image img = new Image("https://img.youtube.com/vi/"+help3[i].id+"/sddefault.jpg");
        imv.setFitWidth(200);
        imv.setFitHeight(100);
        imv.setImage(img);
        Label tlabel = new Label();
        tlabel.setMaxWidth(200);
        tlabel.setText(help3[i].title);
        
        int placeholder = i;
        imv.setOnMouseClicked( new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event){
                System.out.println("working");
                VIDload = help3[placeholder].id;
                try {
                    playVideoMode(event);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        testvb[i].getChildren().add(imv);
        testvb[i].getChildren().add(tlabel);
        
        videos3.getChildren().add(testvb[i]);}
        
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
    
    public PrimaryController(){
        User user = User.getInstance();
        UserData userData = new UserData();
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
    
    @FXML
    void playVideoMode(MouseEvent event) throws IOException {
        App.setRoot("primary_video");
    }
    
    @FXML
    void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("primary_Home");
    }
    
    @FXML
    void switchToProfile(ActionEvent event) throws IOException {
        App.setRoot("primary_Profile");
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
