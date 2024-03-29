package com.mycompany.streamfox;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.firebase.auth.FirebaseAuth;
import static com.mycompany.streamfox.App.scene;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import static com.mycompany.streamfox.App.xOffset;
import static com.mycompany.streamfox.App.yOffset;
import static com.mycompany.streamfox.AuthController.day;
import static com.mycompany.streamfox.YoutubeApiEngine.getService;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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

public class PrimaryHomeController implements Initializable {

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
    private HBox twitchVids;

    @FXML
    private ImageView minimizeWindow;

    @FXML
    private ImageView liveLogo;

    @FXML
    private ImageView menuOpen;

    @FXML
    private Circle userProfView;

    @FXML
    private Button userNameMenuBtn;

    @FXML
    private HBox ytVids;

    @FXML
    private Button youtubeButton;

    @FXML
    private Button youtubeButtonMenu;
    VBox[] testvb;
    VBox[] testvb2;

    private int onOff = 0;

    public static VidObj[][] vid;

    User user = User.getInstance();
    UserData userData = UserData.getInstance();

    public static String VIDload;
    public static String titleLoad;
    public static String channelLoad;
    public static String dateString;
    public static String yesterDayString;
    public static String twoDaysAgoString;

    public static double totalWeeklyWatchTime;
    private DialogPane dialog;

// int currentDayOfYesterdayValue = Instant.now().minus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).getDayOfWeek().getValue();
    boolean hasWatchtimeChanged = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //  Map<String, Object> profileMap = UserData.getInstance().getProfileDataMap();
        //  profileMap.put("tommorrow's Date",  tomorrow );
        // UserData.getInstance().updateProfile(profileMap);
//        System.out.println("The value of YouTube: " + (boolean) userData.getServiceListDataMap().get("youtube"));
//        if ((boolean) userData.getServiceListDataMap().get("youtube") == false) {
//            ytVids.setVisible(false);
//            youtubeButton.setVisible(false);
//            youtubeButtonMenu.setVisible(false);
//        } else {
            YoutubeApiEngine.initializeYoutube();
            ytVids.setSpacing(20);
            VidObj[] help = new VidObj[50];
            try {
                VideoListResponse mostPopularVids = YoutubeVids.getMostPopularVids();

                for (int i = 0; i < 50; i++) {
                    help[i] = new VidObj(mostPopularVids.getItems().get(i).getId(),
                            mostPopularVids.getItems().get(i).getSnippet().getTitle(),
                            mostPopularVids.getItems().get(i).getSnippet().getChannelTitle());
                    //mostPopularVids.getItems().get(i).getContentDetails().getDuration());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (App.stage.isFullScreen() == false) {
                testvb = new VBox[help.length];
                for (int i = 0; i < 20; i++) {
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
                            PrimaryVideoController.startVid = help[placeholder].id;
                            PrimaryVideoController.titleStartText = help[placeholder].title;
                            PrimaryVideoController.channelStartText = help[placeholder].channel;
                            channelLoad = help[placeholder].channel;
                            try {
                                playVideoMode(event);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    testvb[i].getChildren().add(imv);
                    testvb[i].getChildren().add(tlabel);

                    ytVids.getChildren().add(testvb[i]);
                }
            } else {
                testvb = new VBox[help.length];
                for (int i = 0; i < 20; i++) {
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
                            PrimaryVideoController.startVid = help[placeholder].id;
                            PrimaryVideoController.titleStartText = help[placeholder].title;
                            PrimaryVideoController.channelStartText = help[placeholder].channel;
                        }
                    });
                    testvb[i].getChildren().add(imv);
                    testvb[i].getChildren().add(tlabel);

                    ytVids.getChildren().add(testvb[i]);

                }
            }

        //}

        switch (day) {
            case Calendar.MONDAY:
                dateString = "mondayWatchTime";
                yesterDayString = "sundayWatchTime";

                break;

            case Calendar.TUESDAY:
                dateString = "tuesdayWatchTime";
                yesterDayString = "mondayWatchTime";
                break;

            case Calendar.WEDNESDAY:
                dateString = "wednesdayWatchTime";
                yesterDayString = "tuesdayWatchTime";
                break;

            case Calendar.THURSDAY:
                dateString = "thursdayWatchTime";
                yesterDayString = "wednesdayWatchTime";

                break;

            case Calendar.FRIDAY:
                dateString = "fridayWatchTime";
                yesterDayString = "thursdayWatchTime";
                break;

            case Calendar.SATURDAY:
                //   TommorrowString = "sundayWatchTime";
                dateString = "saturdayWatchTime";
                yesterDayString = "fridayWatchTime";
                break;

            case Calendar.SUNDAY:
                dateString = "sundayWatchTime";
                yesterDayString = "saturdayWatchTime";

                break;
        }
        //double totalWatchTimeForToday=(double)  userData.getYTDailyWatchDataMap().get(dateString);

        //     totalWeeklyWatchTime+=totalWatchTimeForToday;
        try {
            TwitchApiEngine.initializeTwitch();
            //TwitchVids.getVideo("1802420975");
            vid = TwitchVids.getTopVideos();
            System.out.println(vid[0][0].title);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

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

        //initialize youtube
        /*try {
            Comments.getCommentsFromVideo("QC6Q4Fge3uE");
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
        twitchVids.setSpacing(20);
        if (App.stage.isFullScreen() == false) {

            testvb2 = new VBox[vid.length];

            for (int j = 0; j < 5; j++) {
                testvb2[j] = new VBox();
                ImageView imv2 = new ImageView();
                String currentBoxArt = vid[j][0].boxArt;
                int width = 100;
                int height = 150;
                String formattedString = currentBoxArt
                        .replace("{width}", String.valueOf(width))
                        .replace("{height}", String.valueOf(height));
                System.out.println(formattedString);
                System.out.println(vid[0][0].gameId);
                System.out.println(vid[1][0].gameId);
                Image img2 = new Image(formattedString);
                imv2.setFitWidth(100);
                imv2.setFitHeight(150);
                imv2.setImage(img2);

                int placeholder = j;
                imv2.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("working");

                        try {
                            twitchMode(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                testvb2[j].getChildren().add(imv2);

                twitchVids.getChildren().add(testvb2[j]);

            }
        } else {

            testvb2 = new VBox[vid.length];

            for (int j = 0; j < 5; j++) {
                testvb2[j] = new VBox();
                ImageView imv2 = new ImageView();
                String currentBoxArt = vid[j][0].boxArt;
                int width = 200;
                int height = 300;
                String formattedString = currentBoxArt
                        .replace("{width}", String.valueOf(width))
                        .replace("{height}", String.valueOf(height));
                System.out.println(formattedString);
                System.out.println(vid[0][0].gameId);
                System.out.println(vid[1][0].gameId);
                Image img2 = new Image(formattedString);
                imv2.setFitWidth(200);
                imv2.setFitHeight(300);
                imv2.setImage(img2);

                int placeholder = j;
                imv2.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("working");

                        try {
                            twitchMode(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                testvb2[j].getChildren().add(imv2);

                twitchVids.getChildren().add(testvb2[j]);

            }
        }

        userNameMenuBtn.setText(((String) userData.getProfileDataMap().get("fname")) + " " + ((String) userData.getProfileDataMap().get("lname")));

        userProfView.setFill(new ImagePattern(new Image((String) userData.getProfileDataMap().get("profileImage"))));
 //       CheckTotalWatchTimeLimit();
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
         //   alert.showAndWait();

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
    void switchToNetflix(ActionEvent event) throws IOException {
        App.setRoot("NetflixSignIn");
    }

    @FXML
    void menuMove(MouseEvent event
    ) {
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

    public PrimaryHomeController() {
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
    private void exit() {
        System.out.println("exit");
        System.exit(0);
    }

    @FXML
    void playVideoMode(MouseEvent event) throws IOException {
        App.setRoot("primary_video");
    }

    @FXML
    void twitchMode(MouseEvent event) throws IOException {
        App.setRoot("Twitch_Primary");
    }

    @FXML
    void twitchMode1(ActionEvent event) throws IOException {
        App.setRoot("Twitch_Primary");
    }

    @FXML
    void switchToYT(ActionEvent event) throws IOException {
        App.setRoot("Youtube");
    }

    @FXML
    void switchToTwitch(ActionEvent event) throws IOException {
        App.setRoot("Twitch_video");
    }

    @FXML
    void switchToProfile(ActionEvent event) throws IOException {
        App.setRoot("primary_Profile");
    }

    @FXML
    void switchToSettings(ActionEvent event) throws IOException {
        App.setRoot("Settings");
    }

    /**
     * switches the application to and from fullscreen mode
     */
    @FXML
    private void fullscreen() {
        firebaseAuth = FirebaseAuth.getInstance();
        //toggles fullscreen on and off
        //TODO: make the interface more dynamic (hard)
        if ((boolean) userData.getServiceListDataMap().get("youtube") == false) {
            ytVids.setVisible(false);
            youtubeButton.setVisible(false);
            youtubeButtonMenu.setVisible(false);
        } else {
            YoutubeApiEngine.initializeYoutube();
            ytVids.getChildren().clear();
            ytVids.setSpacing(20);
            VidObj[] help = new VidObj[50];
            try {
                VideoListResponse mostPopularVids = YoutubeVids.getMostPopularVids();

                for (int i = 0; i < 50; i++) {
                    help[i] = new VidObj(mostPopularVids.getItems().get(i).getId(),
                            mostPopularVids.getItems().get(i).getSnippet().getTitle(),
                            mostPopularVids.getItems().get(i).getSnippet().getChannelTitle());
                    //mostPopularVids.getItems().get(i).getContentDetails().getDuration());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (App.stage.isFullScreen() == false) {
                testvb = new VBox[help.length];
                for (int i = 0; i < 20; i++) {
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
                            PrimaryVideoController.startVid = help[placeholder].id;
                            PrimaryVideoController.titleStartText = help[placeholder].title;
                            PrimaryVideoController.channelStartText = help[placeholder].channel;
                            channelLoad = help[placeholder].channel;
                            try {
                                playVideoMode(event);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    testvb[i].getChildren().add(imv);
                    testvb[i].getChildren().add(tlabel);

                    ytVids.getChildren().add(testvb[i]);
                }
            } else {
                testvb = new VBox[help.length];
                for (int i = 0; i < 20; i++) {
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
                            PrimaryVideoController.startVid = help[placeholder].id;
                            PrimaryVideoController.titleStartText = help[placeholder].title;
                            PrimaryVideoController.channelStartText = help[placeholder].channel;
                        }
                    });
                    testvb[i].getChildren().add(imv);
                    testvb[i].getChildren().add(tlabel);

                    ytVids.getChildren().add(testvb[i]);

                }
            }

        }
        
        twitchVids.getChildren().clear();

        App.fullscreen();

        if (App.stage.isFullScreen() == false) {
            testvb2 = new VBox[vid.length];

            for (int j = 0; j < 5; j++) {
                testvb2[j] = new VBox();
                ImageView imv2 = new ImageView();
                String currentBoxArt = vid[j][0].boxArt;
                int width = 100;
                int height = 150;
                String formattedString = currentBoxArt
                        .replace("{width}", String.valueOf(width))
                        .replace("{height}", String.valueOf(height));
                System.out.println(formattedString);
                System.out.println(vid[0][0].gameId);
                System.out.println(vid[1][0].gameId);
                Image img2 = new Image(formattedString);
                imv2.setFitWidth(100);
                imv2.setFitHeight(150);
                imv2.setImage(img2);

                int placeholder = j;
                imv2.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("working");

                        TwitchHome.indexVid = placeholder;

                        try {
                            twitchMode(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                testvb2[j].getChildren().add(imv2);

                twitchVids.getChildren().add(testvb2[j]);

            }

        } else {

            testvb2 = new VBox[vid.length];

            for (int j = 0; j < 5; j++) {
                testvb2[j] = new VBox();
                ImageView imv2 = new ImageView();
                String currentBoxArt = vid[j][0].boxArt;
                int width = 200;
                int height = 300;
                String formattedString = currentBoxArt
                        .replace("{width}", String.valueOf(width))
                        .replace("{height}", String.valueOf(height));
                System.out.println(formattedString);
                System.out.println(vid[0][0].gameId);
                System.out.println(vid[1][0].gameId);
                Image img2 = new Image(formattedString);
                imv2.setFitWidth(200);
                imv2.setFitHeight(300);
                imv2.setImage(img2);

                int placeholder = j;
                imv2.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("working");

                        try {
                            twitchMode(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                testvb2[j].getChildren().add(imv2);

                twitchVids.getChildren().add(testvb2[j]);

            }
        }

    }
}
