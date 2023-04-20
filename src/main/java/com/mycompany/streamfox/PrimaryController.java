package com.mycompany.streamfox;

import com.google.api.services.youtube.model.VideoListResponse;
import com.google.firebase.auth.FirebaseAuth;
import static com.mycompany.streamfox.App.scene;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import static com.mycompany.streamfox.App.xOffset;
import static com.mycompany.streamfox.App.yOffset;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
    private Circle userProfView;

    @FXML
    private Button userNameMenuBtn;

    @FXML
    private HBox videos1;

    @FXML
    private HBox videos2;

    @FXML
    private HBox videos3;
    
    @FXML
    private HBox videos4;
    
    @FXML
    private HBox videos5;

    VBox[] testvb;

    private int onOff = 0;

    public static String VIDload;
    public static String titleLoad;
    public static String channelLoad;

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
        
        try {
            VideoListResponse mostPopularVids = YoutubeVids.getMostPopularVids();
            int j = 0;
            for (int i=0; i < 10; i++, j++) {
                help[i] = new VidObj(mostPopularVids.getItems().get(j).getId(),
                        mostPopularVids.getItems().get(j).getSnippet().getTitle(),
                        mostPopularVids.getItems().get(j).getSnippet().getChannelTitle(),
                        mostPopularVids.getItems().get(i).getContentDetails().getDuration());
            }
            for (int i=0; i < 10; i++, j++) {
                help1[i] = new VidObj(mostPopularVids.getItems().get(j).getId(),
                        mostPopularVids.getItems().get(j).getSnippet().getTitle(),
                        mostPopularVids.getItems().get(j).getSnippet().getChannelTitle(),
                        mostPopularVids.getItems().get(i).getContentDetails().getDuration());
            }
            for (int i=0; i < 10; i++, j++) {
                help2[i] = new VidObj(mostPopularVids.getItems().get(j).getId(),
                        mostPopularVids.getItems().get(j).getSnippet().getTitle(),
                        mostPopularVids.getItems().get(j).getSnippet().getChannelTitle(),
                        mostPopularVids.getItems().get(i).getContentDetails().getDuration());
            }
            for (int i=0; i < 10; i++, j++) {
                help3[i] = new VidObj(mostPopularVids.getItems().get(j).getId(),
                        mostPopularVids.getItems().get(j).getSnippet().getTitle(),
                        mostPopularVids.getItems().get(j).getSnippet().getChannelTitle(),
                        mostPopularVids.getItems().get(i).getContentDetails().getDuration());
            }
            for (int i=0; i < 10; i++, j++) {
                help4[i] = new VidObj(mostPopularVids.getItems().get(j).getId(),
                        mostPopularVids.getItems().get(j).getSnippet().getTitle(),
                        mostPopularVids.getItems().get(j).getSnippet().getChannelTitle(),
                        mostPopularVids.getItems().get(i).getContentDetails().getDuration());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    if(App.stage.isFullScreen() == false){
        testvb = new VBox[10];
        for (int i = 0; i < help.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
            imv.setFitWidth(200);
            imv.setFitHeight(100);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(200);
            tlabel.setText(help[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help[placeholder].id;
                    titleLoad = help[placeholder].title;
                    channelLoad = help[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos1.getChildren().add(testvb[i]);
        }

        testvb = new VBox[10];
        for (int i = 0; i < help1.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help1[i].id + "/sddefault.jpg");
            imv.setFitWidth(200);
            imv.setFitHeight(100);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(200);
            tlabel.setText(help1[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help1[placeholder].id;
                    titleLoad = help1[placeholder].title;
                    channelLoad = help1[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help1[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help1[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos2.getChildren().add(testvb[i]);
        }

        testvb = new VBox[10];
        for (int i = 0; i < help2.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help2[i].id + "/sddefault.jpg");
            imv.setFitWidth(200);
            imv.setFitHeight(100);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(200);
            tlabel.setText(help2[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help2[placeholder].id;
                    titleLoad = help2[placeholder].title;
                    channelLoad = help2[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help2[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help2[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos3.getChildren().add(testvb[i]);
        }
    }else{
        testvb = new VBox[10];
        for (int i = 0; i < help.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(400);
            tlabel.setText(help[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help[placeholder].id;
                    titleLoad = help[placeholder].title;
                    channelLoad = help[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos1.getChildren().add(testvb[i]);
        }

        testvb = new VBox[10];
        for (int i = 0; i < help1.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help1[i].id + "/sddefault.jpg");
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(400);
            tlabel.setText(help1[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help1[placeholder].id;
                    titleLoad = help1[placeholder].title;
                    channelLoad = help1[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help1[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help1[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos2.getChildren().add(testvb[i]);
        }

        testvb = new VBox[10];
        for (int i = 0; i < help2.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help2[i].id + "/sddefault.jpg");
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(400);
            tlabel.setText(help2[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help2[placeholder].id;
                    titleLoad = help2[placeholder].title;
                    channelLoad = help2[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help2[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help2[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos3.getChildren().add(testvb[i]);
            
            } 
        
            testvb = new VBox[10];
        for (int i = 0; i < help3.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help3[i].id + "/sddefault.jpg");
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(400);
            tlabel.setText(help2[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help3[placeholder].id;
                    titleLoad = help3[placeholder].title;
                    channelLoad = help3[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help3[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help3[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos4.getChildren().add(testvb[i]);
            
            } 
        
            testvb = new VBox[10];
        for (int i = 0; i < help4.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help4[i].id + "/sddefault.jpg");
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(400);
            tlabel.setText(help4[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help4[placeholder].id;
                    titleLoad = help4[placeholder].title;
                    channelLoad = help4[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help4[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help4[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos5.getChildren().add(testvb[i]);
            
            } 
        
    
    }
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

    public PrimaryController() {
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
        App.setRoot("TwitchPrimary");
    }

    @FXML
    void switchToSettings(ActionEvent event) throws IOException {
        App.setRoot("primary_Settings");
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
        
        videos1.getChildren().clear();
        videos2.getChildren().clear();
        videos3.getChildren().clear();
        videos4.getChildren().clear();
        videos5.getChildren().clear();
        
        try {
            VideoListResponse mostPopularVids = YoutubeVids.getMostPopularVids();
            int j = 0;
            for (int i=0; i < 10; i++, j++) {
                help[i] = new VidObj(mostPopularVids.getItems().get(j).getId(),
                        mostPopularVids.getItems().get(j).getSnippet().getTitle(),
                        mostPopularVids.getItems().get(j).getSnippet().getChannelTitle(),
                        mostPopularVids.getItems().get(i).getContentDetails().getDuration());
            }
            for (int i=0; i < 10; i++, j++) {
                help1[i] = new VidObj(mostPopularVids.getItems().get(j).getId(),
                        mostPopularVids.getItems().get(j).getSnippet().getTitle(),
                        mostPopularVids.getItems().get(j).getSnippet().getChannelTitle(),
                        mostPopularVids.getItems().get(i).getContentDetails().getDuration());
            }
            for (int i=0; i < 10; i++, j++) {
                help2[i] = new VidObj(mostPopularVids.getItems().get(j).getId(),
                        mostPopularVids.getItems().get(j).getSnippet().getTitle(),
                        mostPopularVids.getItems().get(j).getSnippet().getChannelTitle(),
                        mostPopularVids.getItems().get(i).getContentDetails().getDuration());
            }
            for (int i=0; i < 10; i++, j++) {
                help3[i] = new VidObj(mostPopularVids.getItems().get(j).getId(),
                        mostPopularVids.getItems().get(j).getSnippet().getTitle(),
                        mostPopularVids.getItems().get(j).getSnippet().getChannelTitle(),
                        mostPopularVids.getItems().get(i).getContentDetails().getDuration());
            }
            for (int i=0; i < 10; i++, j++) {
                help4[i] = new VidObj(mostPopularVids.getItems().get(j).getId(),
                        mostPopularVids.getItems().get(j).getSnippet().getTitle(),
                        mostPopularVids.getItems().get(j).getSnippet().getChannelTitle(),
                        mostPopularVids.getItems().get(i).getContentDetails().getDuration());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    if(App.stage.isFullScreen() == false){
        testvb = new VBox[10];
        for (int i = 0; i < help.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
            imv.setFitWidth(200);
            imv.setFitHeight(100);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(200);
            tlabel.setText(help[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help[placeholder].id;
                    titleLoad = help[placeholder].title;
                    channelLoad = help[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos1.getChildren().add(testvb[i]);
        }

        testvb = new VBox[10];
        for (int i = 0; i < help1.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help1[i].id + "/sddefault.jpg");
            imv.setFitWidth(200);
            imv.setFitHeight(100);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(200);
            tlabel.setText(help1[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help1[placeholder].id;
                    titleLoad = help1[placeholder].title;
                    channelLoad = help1[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help1[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help1[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos2.getChildren().add(testvb[i]);
        }

        testvb = new VBox[10];
        for (int i = 0; i < help2.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help2[i].id + "/sddefault.jpg");
            imv.setFitWidth(200);
            imv.setFitHeight(100);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(200);
            tlabel.setText(help2[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help2[placeholder].id;
                    titleLoad = help2[placeholder].title;
                    channelLoad = help2[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help2[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help2[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos3.getChildren().add(testvb[i]);
        }
    }else{
        testvb = new VBox[10];
        for (int i = 0; i < help.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(400);
            tlabel.setText(help[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help[placeholder].id;
                    titleLoad = help[placeholder].title;
                    channelLoad = help[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos1.getChildren().add(testvb[i]);
        }

        testvb = new VBox[10];
        for (int i = 0; i < help1.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help1[i].id + "/sddefault.jpg");
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(400);
            tlabel.setText(help1[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help1[placeholder].id;
                    titleLoad = help1[placeholder].title;
                    channelLoad = help1[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help1[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help1[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos2.getChildren().add(testvb[i]);
        }

        testvb = new VBox[10];
        for (int i = 0; i < help2.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help2[i].id + "/sddefault.jpg");
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(400);
            tlabel.setText(help2[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help2[placeholder].id;
                    titleLoad = help2[placeholder].title;
                    channelLoad = help2[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help2[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help2[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos3.getChildren().add(testvb[i]);
            
            } 
        
            testvb = new VBox[10];
        for (int i = 0; i < help3.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help3[i].id + "/sddefault.jpg");
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(400);
            tlabel.setText(help3[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help3[placeholder].id;
                    titleLoad = help3[placeholder].title;
                    channelLoad = help3[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help3[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help3[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos4.getChildren().add(testvb[i]);
            
            } 
        
            testvb = new VBox[10];
        for (int i = 0; i < help4.length; i++) {
            testvb[i] = new VBox();
            ImageView imv = new ImageView();
            Image img = new Image("https://img.youtube.com/vi/" + help4[i].id + "/sddefault.jpg");
            imv.setFitWidth(400);
            imv.setFitHeight(200);
            imv.setImage(img);
            Label tlabel = new Label();
            tlabel.setMaxWidth(400);
            tlabel.setText(help4[i].title);

            int placeholder = i;
            imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    System.out.println("working");
                    VIDload = help4[placeholder].id;
                    titleLoad = help4[placeholder].title;
                    channelLoad = help4[placeholder].channel;
                    double time = (double)userData.getYTWatchDaysDataMap().get("fridayWatchTime");
                    System.out.println(help4[placeholder].vidLength);
                    java.time.Duration d = java.time.Duration.parse(help4[placeholder].vidLength);
                    int seconds = (int)d.get(java.time.temporal.ChronoUnit.SECONDS);
                    System.out.println("sec: " + seconds);
                    time += ((double)seconds / 3600);
                    
                    Map<String, Object> watchTimeMap = UserData.getInstance().getYTWatchDaysDataMap();
                    watchTimeMap.put("fridayWatchTime", time);
                    UserData.getInstance().updateWatchTimeYT(watchTimeMap);
                    try {
                        playVideoMode(event);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            testvb[i].getChildren().add(imv);
            testvb[i].getChildren().add(tlabel);

            videos5.getChildren().add(testvb[i]);
            
            } 
        }
    }

}
