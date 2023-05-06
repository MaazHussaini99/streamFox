package com.mycompany.streamfox;

import com.google.api.services.youtube.model.VideoListResponse;
import com.google.firebase.auth.FirebaseAuth;
import static com.mycompany.streamfox.App.scene;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import static com.mycompany.streamfox.App.xOffset;
import static com.mycompany.streamfox.App.yOffset;
import static com.mycompany.streamfox.PrimaryHomeController.vid;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.util.Duration;

public class TwitchHome implements Initializable {

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
    private Circle userProfView;
    
    @FXML
    private Button userNameMenuBtn;
    
    @FXML
    private HBox twitchVids;
    
    @FXML
    private HBox twitchVids1;

    @FXML
    private HBox twitchVids2;
    
    @FXML
    private TextField searchTxtField;

    VBox[] testvb;
    
    private int onOff = 0;

   

    User user = User.getInstance();
    UserData userData = UserData.getInstance();
    
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

        VidObj[] help = new VidObj[10];
        VidObj[] help1 = new VidObj[10];
        VidObj[] help2 = new VidObj[10];
        VidObj[] help3 = new VidObj[10];
        VidObj[] help4 = new VidObj[10];
        for(int i = 0; i< 10;i++){
            VBox tempVB = new VBox();
            ImageView imv = new ImageView();
            String currentBoxArt = vid[0][i].thumbnail;
            String currentTitle = vid[0][i].title;
            Label l1 = new Label(currentTitle);
                int width = 200;
                int height = 100;
                String formattedString = currentBoxArt
                        .replace("{width}", String.valueOf(width))
                        .replace("{height}", String.valueOf(height));
                System.out.println(formattedString);
            
                Image img2 = new Image(formattedString);
                imv.setFitWidth(200);
                imv.setFitHeight(100);
                imv.setImage(img2);
                
                int placeholder = i;
                l1.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    
                    TwitchController.startVid = vid[0][placeholder].id;
                    TwitchController.titleStartText = vid[0][placeholder].title;
                    TwitchController.channelStartText = vid[0][placeholder].channel;
                    try {
                        switchToTwitchVideo(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
                
            tempVB.getChildren().add(imv);
            tempVB.getChildren().add(l1);
            
            twitchVids.getChildren().add(tempVB);
            
        }
        for(int j = 0; j< 10;j++){
            VBox tempVB = new VBox();
            ImageView imv = new ImageView();
            String currentBoxArt = vid[1][j].thumbnail;
            String currentTitle = vid[1][j].title;
            Label l1 = new Label(currentTitle);
                int width = 200;
                int height = 100;
                String formattedString = currentBoxArt
                        .replace("{width}", String.valueOf(width))
                        .replace("{height}", String.valueOf(height));
                System.out.println(formattedString);
               
                Image img2 = new Image(formattedString);
                imv.setFitWidth(200);
                imv.setFitHeight(100);
                imv.setImage(img2);
                
                int placeholder = j;
                l1.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    
                    TwitchController.startVid = vid[1][placeholder].id;
                    TwitchController.titleStartText = vid[1][placeholder].title;
                    TwitchController.channelStartText = vid[1][placeholder].channel;
                    
                    try {
                        switchToTwitchVideo(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
                
            tempVB.getChildren().add(imv);
            tempVB.getChildren().add(l1);
            
            twitchVids.getChildren().add(tempVB);
        }
        for(int l = 0; l< 10;l++){
            VBox tempVB = new VBox();
            ImageView imv = new ImageView();
            String currentBoxArt = vid[2][l].thumbnail;
            String currentTitle = vid[2][l].title;
            Label l1 = new Label(currentTitle);
                int width = 200;
                int height = 100;
                String formattedString = currentBoxArt
                        .replace("{width}", String.valueOf(width))
                        .replace("{height}", String.valueOf(height));
                System.out.println(formattedString);
               
                Image img2 = new Image(formattedString);
                imv.setFitWidth(200);
                imv.setFitHeight(100);
                imv.setImage(img2);
                
                int placeholder = l;
                l1.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    
                    TwitchController.startVid = vid[2][placeholder].id;
                    TwitchController.titleStartText = vid[2][placeholder].title;
                    TwitchController.channelStartText = vid[2][placeholder].channel;
                    
                    try {
                        switchToTwitchVideo(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
                
            tempVB.getChildren().add(imv);
            tempVB.getChildren().add(l1);
            
            twitchVids.getChildren().add(tempVB);
        }
        userNameMenuBtn.setText(((String) userData.getProfileDataMap().get("fname")) + " " + ((String) userData.getProfileDataMap().get("lname")));
        userProfView.setFill(new ImagePattern(new Image((String) userData.getProfileDataMap().get("profileImage"))));

    }

    @FXML
    void searchFunction(ActionEvent event) throws IOException {
        
         
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

    public TwitchHome() {
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
    private void exit() {
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
    
    @FXML
    void switchToTwitch(ActionEvent event) throws IOException {
        App.setRoot("Twitch_Primary");
    }
    
    @FXML
    void switchToTwitchVideo(MouseEvent event) throws IOException {
        App.setRoot("Twitch_video_");
    }
    
    @FXML
    void switchToSettings(ActionEvent event) throws IOException {
        App.setRoot("primarySettings");
    }
    
    @FXML
    void switchToYT(ActionEvent event) {

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
        VidObj[] help = new VidObj[10];
        VidObj[] help1 = new VidObj[10];
        VidObj[] help2 = new VidObj[10];
        VidObj[] help3 = new VidObj[10];
        VidObj[] help4 = new VidObj[10];
        
        
        
}
    
}
