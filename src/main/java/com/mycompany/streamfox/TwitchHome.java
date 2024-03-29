package com.mycompany.streamfox;

import com.google.api.services.youtube.model.VideoListResponse;
import com.google.firebase.auth.FirebaseAuth;
import static com.mycompany.streamfox.App.scene;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import static com.mycompany.streamfox.App.xOffset;
import static com.mycompany.streamfox.App.yOffset;
import static com.mycompany.streamfox.PrimaryHomeController.dateString;
import static com.mycompany.streamfox.PrimaryHomeController.vid;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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

    private DialogPane dialog;
    VBox[] testvb;

    private int onOff = 0;

    public static int indexVid;

    User user = User.getInstance();
    UserData userData = UserData.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
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
            
            int i;
            
            if(App.stage.isFullScreen() == false){
                for (i = 0; i < 10; i++) {
                    VBox tempVB = new VBox();
                    ImageView imv = new ImageView();
                    String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
                    String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
                    Label l1 = new Label(currentTitle);
                    l1.setMaxWidth(200);
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
                    imv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        
                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("working");
                            
                            TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].channel;
                            TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                            TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;
                            try {
                                switchToTwitchVideo(event);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    
                    tempVB.getChildren().add(imv);
                    tempVB.getChildren().add(l1);
                    
                    twitchVids.setSpacing(20);
                    twitchVids.getChildren().add(tempVB);
                    
                }
                for (; i < 20; i++) {
                    VBox tempVB = new VBox();
                    ImageView imv = new ImageView();
                    String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
                    String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
                    Label l1 = new Label(currentTitle);
                    l1.setMaxWidth(200);
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
                    imv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        
                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("working");
                            
                            TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].id;
                            TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                            TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;
                            
                            try {
                                switchToTwitchVideo(event);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    
                    tempVB.getChildren().add(imv);
                    tempVB.getChildren().add(l1);
                    
                    twitchVids1.setSpacing(20);
                    twitchVids1.getChildren().add(tempVB);
                }
                for (; i < 30; i++) {
                    VBox tempVB = new VBox();
                    ImageView imv = new ImageView();
                    String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
                    String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
                    Label l1 = new Label(currentTitle);
                    l1.setMaxWidth(200);
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
                    imv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        
                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("working");
                            
                            TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].id;
                            TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                            TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;
                            
                            try {
                                switchToTwitchVideo(event);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    
                    tempVB.getChildren().add(imv);
                    tempVB.getChildren().add(l1);
                    
                    twitchVids2.setSpacing(20);
                    twitchVids2.getChildren().add(tempVB);
                }
            }else{
                for (i = 0; i < 10; i++) {
                    VBox tempVB = new VBox();
                    ImageView imv = new ImageView();
                    String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
                    String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
                    Label l1 = new Label(currentTitle);
                    l1.setMaxWidth(400);
                    int width = 400;
                    int height = 200;
                    String formattedString = currentBoxArt
                            .replace("{width}", String.valueOf(width))
                            .replace("{height}", String.valueOf(height));
                    System.out.println(formattedString);
                    
                    Image img2 = new Image(formattedString);
                    imv.setFitWidth(400);
                    imv.setFitHeight(200);
                    imv.setImage(img2);
                    
                    int placeholder = i;
                    imv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        
                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("working");
                            
                            TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].channel;
                            TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                            TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;
                            try {
                                switchToTwitchVideo(event);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    
                    tempVB.getChildren().add(imv);
                    tempVB.getChildren().add(l1);
                    
                    twitchVids.setSpacing(20);
                    twitchVids.getChildren().add(tempVB);
                    
                }
                for (; i < 20; i++) {
                    VBox tempVB = new VBox();
                    ImageView imv = new ImageView();
                    String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
                    String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
                    Label l1 = new Label(currentTitle);
                    l1.setMaxWidth(400);
                    int width = 400; 
                    int height = 200;
                    String formattedString = currentBoxArt
                            .replace("{width}", String.valueOf(width))
                            .replace("{height}", String.valueOf(height));
                    System.out.println(formattedString);
                    
                    Image img2 = new Image(formattedString);
                    imv.setFitWidth(400);
                    imv.setFitHeight(200);
                    imv.setImage(img2);
                    
                    int placeholder = i;
                    imv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        
                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("working");
                            
                            TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].id;
                            TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                            TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;
                            
                            try {
                                switchToTwitchVideo(event);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    
                    tempVB.getChildren().add(imv);
                    tempVB.getChildren().add(l1);
                    
                    twitchVids1.setSpacing(20);
                    twitchVids1.getChildren().add(tempVB);
                }
                for (; i < 30; i++) {
                    VBox tempVB = new VBox();
                    ImageView imv = new ImageView();
                    String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
                    String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
                    Label l1 = new Label(currentTitle);
                    l1.setMaxWidth(400);
                    int width = 400;
                    int height = 200;
                    String formattedString = currentBoxArt
                            .replace("{width}", String.valueOf(width))
                            .replace("{height}", String.valueOf(height));
                    System.out.println(formattedString);
                    
                    Image img2 = new Image(formattedString);
                    imv.setFitWidth(400);
                    imv.setFitHeight(200);
                    imv.setImage(img2);
                    
                    int placeholder = i;
                    imv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        
                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("working");
                            
                            TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].id;
                            TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                            TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;
                            
                            try {
                                switchToTwitchVideo(event);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    
                    tempVB.getChildren().add(imv);
                    tempVB.getChildren().add(l1);
                    
                    twitchVids2.setSpacing(20);
                    twitchVids2.getChildren().add(tempVB);
                }
            }
            
            
            userNameMenuBtn.setText(((String) userData.getProfileDataMap().get("fname")) + " " + ((String) userData.getProfileDataMap().get("lname")));
            userProfView.setFill(new ImagePattern(new Image((String) userData.getProfileDataMap().get("profileImage"))));
            CheckTotalWatchTimeLimit();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

 void CheckTotalWatchTimeLimit() throws IOException {

        double tempYTDailyWatchTime = (double) userData.getYTDailyWatchDataMap().get(dateString);
        double tempYTWeeklyWatchTime = (double) userData.getYTDailyWatchDataMap().get("WeeklyWatchTime");
        double tempTwitchDailyWatchTime = (double) userData.getTwitchDailyWatchDataMap().get(dateString);
        double tempTwitchWeeklyWatchTime = (double) userData.getTwitchDailyWatchDataMap().get("WeeklyWatchTime");

        double dailyWatchtimeLimitForAllServices = (double) userData.getWatchTimeSettingsDataMap().get("setDailyLimit");
        System.out.print("current Daily Watch time Limit is" + dailyWatchtimeLimitForAllServices);

        double weeklyWatchtimelimitForAllServices = (double) userData.getWatchTimeSettingsDataMap().get("setWeeklyLimit");

        System.out.print("current Daily Watch time Limit is" + dailyWatchtimeLimitForAllServices);

        System.out.print("current Weekly Watch time Limit is" + weeklyWatchtimelimitForAllServices);

        if ((tempYTDailyWatchTime >= dailyWatchtimeLimitForAllServices) || (tempYTWeeklyWatchTime >= weeklyWatchtimelimitForAllServices) || (tempTwitchDailyWatchTime >= dailyWatchtimeLimitForAllServices) || (tempTwitchWeeklyWatchTime >= weeklyWatchtimelimitForAllServices)) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sorry Your Watchtime Limit has Been Reached");
            alert.setHeaderText("Please press OK to Confirm and take a break or CANCEL to Continue Watching ");
            alert.setResizable(false);
            alert.setContentText("Are you sure? ");
            dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
            //alert.showAndWait();

            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent()) {

            } // alert is exited, no button has been pressed.
           else if (result.get() == ButtonType.OK) {
                
                 App.setWidth(330);
                    App.setHeight(400);
                    App.scene = new Scene(loadFXML("authentication"), App.width, App.height);

                    App.stage.setScene(App.scene);
            } //oke button is pressed
            else if (result.get() == ButtonType.CANCEL) {
                alert.close();

            }
        }

    }
 
  @FXML
    public void  logOut(MouseEvent event) throws IOException{
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout Notification");
            alert.setHeaderText("Please press OK to logout  or CANCEL to Continue Watching ");
            alert.setResizable(false);
            alert.setContentText("Are you sure? ");
            dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
     //       alert.showAndWait();

            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent()) {

            } // alert is exited, no button has been pressed.
            else if (result.get() == ButtonType.OK) {
                
                 App.setWidth(330);
                    App.setHeight(400);
                    App.scene = new Scene(loadFXML("authentication"), App.width, App.height);

                    App.stage.setScene(App.scene);
            } //oke button is pressed
            else if (result.get() == ButtonType.CANCEL) {
                alert.close();

            }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
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
        App.setRoot("Twitch_video");
    }

    @FXML
    void switchToSettings(ActionEvent event) throws IOException {
        App.setRoot("primarySettings");
    }

    @FXML
    void switchToYT(ActionEvent event) throws IOException {
        App.setRoot("Youtube");
    }

    /**
     * switches the application to and from fullscreen mode
     */
    @FXML
    private void fullscreen() {
        firebaseAuth = FirebaseAuth.getInstance();
        int i;
        //toggles fullscreen on and off
        //TODO: make the interface more dynamic (hard)
        System.out.println("fullscreen");
        App.fullscreen();
        VidObj[] help = new VidObj[10];
        VidObj[] help1 = new VidObj[10];
        VidObj[] help2 = new VidObj[10];
        VidObj[] help3 = new VidObj[10];
        VidObj[] help4 = new VidObj[10];
        
        twitchVids.getChildren().clear();
        twitchVids1.getChildren().clear();
        twitchVids2.getChildren().clear();
        
        if(App.stage.isFullScreen() == false){
        for (i = 0; i < 10; i++) {
            VBox tempVB = new VBox();
            ImageView imv = new ImageView();
            String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
            String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
            Label l1 = new Label(currentTitle);
            l1.setMaxWidth(200);
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
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");

                    TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].channel;
                    TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                    TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;
                    try {
                        switchToTwitchVideo(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            tempVB.getChildren().add(imv);
            tempVB.getChildren().add(l1);

            twitchVids.setSpacing(20);
            twitchVids.getChildren().add(tempVB);

        }
        for (; i < 20; i++) {
            VBox tempVB = new VBox();
            ImageView imv = new ImageView();
            String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
            String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
            Label l1 = new Label(currentTitle);
            l1.setMaxWidth(200);
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
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");

                    TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].id;
                    TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                    TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;

                    try {
                        switchToTwitchVideo(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            tempVB.getChildren().add(imv);
            tempVB.getChildren().add(l1);

            twitchVids1.setSpacing(20);
            twitchVids1.getChildren().add(tempVB);
        }
        for (; i < 30; i++) {
            VBox tempVB = new VBox();
            ImageView imv = new ImageView();
            String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
            String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
            Label l1 = new Label(currentTitle);
            l1.setMaxWidth(200);
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
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");

                    TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].id;
                    TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                    TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;

                    try {
                        switchToTwitchVideo(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            tempVB.getChildren().add(imv);
            tempVB.getChildren().add(l1);

            twitchVids2.setSpacing(20);
            twitchVids2.getChildren().add(tempVB);
        } 
        }else{
            for (i = 0; i < 10; i++) {
            VBox tempVB = new VBox();
            ImageView imv = new ImageView();
            String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
            String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
            Label l1 = new Label(currentTitle);
            l1.setMaxWidth(400);
            int width = 400;
            int height = 200;
            String formattedString = currentBoxArt
                    .replace("{width}", String.valueOf(width))
                    .replace("{height}", String.valueOf(height));
            System.out.println(formattedString);

            Image img2 = new Image(formattedString);
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img2);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");

                    TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].channel;
                    TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                    TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;
                    try {
                        switchToTwitchVideo(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            tempVB.getChildren().add(imv);
            tempVB.getChildren().add(l1);

            twitchVids.setSpacing(20);
            twitchVids.getChildren().add(tempVB);

        }
        for (; i < 20; i++) {
            VBox tempVB = new VBox();
            ImageView imv = new ImageView();
            String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
            String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
            Label l1 = new Label(currentTitle);
            l1.setMaxWidth(400);
            int width = 400;
            int height = 200;
            String formattedString = currentBoxArt
                    .replace("{width}", String.valueOf(width))
                    .replace("{height}", String.valueOf(height));
            System.out.println(formattedString);

            Image img2 = new Image(formattedString);
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img2);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");

                    TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].id;
                    TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                    TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;

                    try {
                        switchToTwitchVideo(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            tempVB.getChildren().add(imv);
            tempVB.getChildren().add(l1);

            twitchVids1.setSpacing(20);
            twitchVids1.getChildren().add(tempVB);
        }
        for (; i < 30; i++) {
            VBox tempVB = new VBox();
            ImageView imv = new ImageView();
            String currentBoxArt = PrimaryHomeController.vid[indexVid][i].thumbnail;
            String currentTitle = PrimaryHomeController.vid[indexVid][i].title;
            Label l1 = new Label(currentTitle);
            l1.setMaxWidth(400);
            int width = 400;
            int height = 200;
            String formattedString = currentBoxArt
                    .replace("{width}", String.valueOf(width))
                    .replace("{height}", String.valueOf(height));
            System.out.println(formattedString);

            Image img2 = new Image(formattedString);
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img2);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");

                    TwitchController.startVid = PrimaryHomeController.vid[indexVid][placeholder].id;
                    TwitchController.titleStartText = PrimaryHomeController.vid[indexVid][placeholder].title;
                    TwitchController.channelStartText = PrimaryHomeController.vid[indexVid][placeholder].channel;

                    try {
                        switchToTwitchVideo(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            tempVB.getChildren().add(imv);
            tempVB.getChildren().add(l1);

            twitchVids2.setSpacing(20);
            twitchVids2.getChildren().add(tempVB);
        } 
        }
        
    }

}
