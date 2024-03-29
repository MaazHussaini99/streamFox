package com.mycompany.streamfox;

import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import static com.mycompany.streamfox.App.scene;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import static com.mycompany.streamfox.App.xOffset;
import static com.mycompany.streamfox.App.yOffset;
import static com.mycompany.streamfox.AuthController.day;
import static com.mycompany.streamfox.FirebaseStart.getDatabaseReference;
import static com.mycompany.streamfox.PrimaryHomeController.dateString;
import static com.mycompany.streamfox.PrimaryYoutubeController.Searchresults;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class PrimaryVideoController implements Initializable {

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
    private Circle userProfView;

    @FXML
    private ListView<String> commentView;

    @FXML
    private TextField searchTxtField;

    @FXML
    private Button userNameMenuBtn;

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

    private DialogPane dialog;

    @FXML
    private TextField commentText;

    @FXML
    private ListView<String> platformCommentView;

    @FXML
    private Button submitComment;

    ObservableList<String> platComments = observableArrayList();
    ObservableList<String> platImageUrls = observableArrayList();

    private WebEngine we;

    private int onOff = 0;

    public static VidObj[] Searchresults;

    VBox[] testvb;

    User user = User.getInstance();
    UserData userData = UserData.getInstance();

    SearchListResponse relatedVids;
    CommentThreadListResponse comments;

    public static double totalYoutubeWeeklyWatchTime;
    static String startVid;
    static String titleStartText;
    static String channelStartText;
    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;
    private long elapsedTime = 0;
    private Thread myThread;
    private final Lock lock = new ReentrantLock();
    boolean isThreadCompleted = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //adding youtube comments
        ObservableList<String> obComments = observableArrayList();
        ObservableList<String> imageUrls = observableArrayList();
        try {
            comments = Comments.getCommentsFromVideo(startVid);
            for (CommentThread commentThread : comments.getItems()) {
                String authorName = commentThread.getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName();
                String text = commentThread.getSnippet().getTopLevelComment().getSnippet().getTextOriginal();
                String imageUrl = commentThread.getSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl();
                obComments.add(authorName + ": " + text);
                imageUrls.add(imageUrl);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        commentView.setItems(obComments);
        commentView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(item);
                    int index = getIndex();
                    String imageUrl = imageUrls.get(index);
                    
                    imageView.setImage(new Image(imageUrl));
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                    // set the width's
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());
                    // allow wrapping
                    setWrapText(true);
                }
            }
        });
        runOnce();
        startTimer();
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
        titleTxt.setText(titleStartText);
        channelTxt.setText(channelStartText);
        System.out.println(startVid);
        loadPage(startVid);
        VidObj[] help = new VidObj[20];
        try {
            relatedVids = YoutubeVids.getRelatedVids(startVid);
            
            for (int i = 0; i < 20; i++) {
                help[i] = new VidObj(relatedVids.getItems().get(i).getId().getVideoId(),
                        relatedVids.getItems().get(i).getSnippet().getTitle(),
                        relatedVids.getItems().get(i).getSnippet().getChannelTitle());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (App.stage.isFullScreen() == false) {
            testvb = new VBox[help.length];
            for (int i = 0; i < help.length; i++) {
                testvb[i] = new VBox();
                ImageView imv = new ImageView();
                Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
                imv.setFitWidth(130);
                imv.setFitHeight(80);
                imv.setImage(img);
                Label tlabel = new Label();
                tlabel.setMaxWidth(130);
                tlabel.setText(help[i].title);
                
                int placeholder = i;
                imv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            //relatedVids.getItems().get(1).
                            
                            recommendedTab.getChildren().clear();
                            System.out.println("working");
                            String myId = help[placeholder].id;
                            testvb = new VBox[help.length];
                            VidObj[] help = new VidObj[20];
                            
                            SearchListResponse newRelatedVids = YoutubeVids.getRelatedVids(myId);
                            
                            for (int i = 0; i < 20; i++) {
                                help[i] = new VidObj(newRelatedVids.getItems().get(i).getId().getVideoId(),
                                        newRelatedVids.getItems().get(i).getSnippet().getTitle(),
                                        newRelatedVids.getItems().get(i).getSnippet().getChannelTitle());
                            }

                            for (int i = 0; i < help.length; i++) {
                                testvb[i] = new VBox();
                                ImageView imv = new ImageView();
                                Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
                                imv.setFitWidth(130);
                                imv.setFitHeight(80);
                                imv.setImage(img);
                                Label tlabel = new Label();
                                tlabel.setMaxWidth(130);
                                tlabel.setText(help[i].title);
                                
                                testvb[i].getChildren()
                                        .add(imv);
                                testvb[i].getChildren()
                                        .add(tlabel);
                                
                                recommendedTab.getChildren()
                                        .add(testvb[i]);
                            }
                            
                            loadPage(help[placeholder].id);
                            titleTxt.setText(help[placeholder].title);
                            channelTxt.setText(help[placeholder].channel);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                });
                testvb[i].getChildren()
                        .add(imv);
                testvb[i].getChildren()
                        .add(tlabel);
                
                recommendedTab.getChildren()
                        .add(testvb[i]);
            }
        } else {
            webVideoView.setPrefWidth(1024);
            webVideoView.setPrefHeight(576);
            testvb = new VBox[help.length];
            for (int i = 0; i < help.length; i++) {
                testvb[i] = new VBox();
                ImageView imv = new ImageView();
                Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
                imv.setFitWidth(260);
                imv.setFitHeight(160);
                imv.setImage(img);
                Label tlabel = new Label();
                tlabel.setMaxWidth(460);
                tlabel.setText(help[i].title);
                
                int placeholder = i;
                imv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            //relatedVids.getItems().get(1).
                            
                            recommendedTab.getChildren().clear();
                            System.out.println("working");
                            String myId = help[placeholder].id;
                            testvb = new VBox[help.length];
                            VidObj[] help = new VidObj[20];
                            
                            SearchListResponse newRelatedVids = YoutubeVids.getRelatedVids(myId);
                            
                            for (int i = 0; i < 20; i++) {
                                help[i] = new VidObj(newRelatedVids.getItems().get(i).getId().getVideoId(),
                                        newRelatedVids.getItems().get(i).getSnippet().getTitle(),
                                        newRelatedVids.getItems().get(i).getSnippet().getChannelTitle());
                            }

                            for (int i = 0; i < help.length; i++) {
                                testvb[i] = new VBox();
                                ImageView imv = new ImageView();
                                Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
                                imv.setFitWidth(260);
                                imv.setFitHeight(160);
                                imv.setImage(img);
                                Label tlabel = new Label();
                                tlabel.setMaxWidth(130);
                                tlabel.setText(help[i].title);
                                
                                testvb[i].getChildren()
                                        .add(imv);
                                testvb[i].getChildren()
                                        .add(tlabel);
                                
                                recommendedTab.getChildren()
                                        .add(testvb[i]);
                            }
                            
                            loadPage(help[placeholder].id);
                            titleTxt.setText(help[placeholder].title);
                            channelTxt.setText(help[placeholder].channel);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                });
                testvb[i].getChildren()
                        .add(imv);
                testvb[i].getChildren()
                        .add(tlabel);
                
                recommendedTab.getChildren()
                        .add(testvb[i]);
            }
        }

        runOnce();

        startTimer();

        frontPane.setVisible(false);
        ft = new FadeTransition(Duration.seconds(0.5), frontPane);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();

        tt = new TranslateTransition(Duration.seconds(0.1), frontPane);
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

        titleTxt.setText(titleStartText);
        channelTxt.setText(channelStartText);

        System.out.println(startVid);
        loadPage(startVid);

        //VidObj[] help = new VidObj[20];
        try {
            relatedVids = YoutubeVids.getRelatedVids(startVid);

            for (int i = 0; i < 20; i++) {
                help[i] = new VidObj(relatedVids.getItems().get(i).getId().getVideoId(),
                        relatedVids.getItems().get(i).getSnippet().getTitle(),
                        relatedVids.getItems().get(i).getSnippet().getChannelTitle());
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
            //         alert.showAndWait();

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
    public void logOut(MouseEvent event) throws IOException {
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

    public void loadPage(String VID) {
        we.load("https://www.youtube.com/embed/" + VID);

    }

    void runOnce() {
        DatabaseReference videoRef = getDatabaseReference(startVid);
        myThread = new Thread(() -> {
            System.out.println("Lock Started");
            // code to be executed with the lock
            videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot uidSnapshot : dataSnapshot.getChildren()) {
                        String uid = uidSnapshot.getKey();
                        DatabaseReference uidRef = videoRef.child(uid);

                        uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                                    String commentId = commentSnapshot.getKey();
                                    String comment = commentSnapshot.child("text").getValue(String.class);
                                    platComments.add(userData.setProfileImage(uid).get("fname").toString() + " "
                                            + userData.setProfileImage(uid).get("lname").toString() + ": " + comment);
                                    platImageUrls.add(userData.setProfileImage(uid).get("profileImage").toString());
                                    System.out.println(comment);
                                }
                                // use Platform.runLater to update the UI after the thread completes
                                Platform.runLater(() -> {
                                    platformCommentView.setItems(platComments);
                                    platformCommentView.setCellFactory(param -> new ListCell<String>() {
                                        private ImageView imageView = new ImageView();

                                        @Override
                                        protected void updateItem(String item, boolean empty) {
                                            super.updateItem(item, empty);
                                            if (empty || item == null) {
                                                setGraphic(null);
                                                setText(null);
                                            } else {
                                                setText(item);
                                                int index = getIndex();
                                                String imageUrl = platImageUrls.get(index);

                                                imageView.setImage(new Image(imageUrl));
                                                imageView.setFitWidth(50);
                                                imageView.setFitHeight(50);
                                                setGraphic(imageView);
                                                // set the width's
                                                setMinWidth(param.getWidth());
                                                setMaxWidth(param.getWidth());
                                                setPrefWidth(param.getWidth());
                                                // allow wrapping
                                                setWrapText(true);
                                            }
                                        }
                                    });
                                    System.out.println("Lock ended");
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle errors that occur while reading the data
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors that occur while reading the data
                }
            });
        });
        myThread.start();
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
    void submitComment(ActionEvent event) throws IOException {

        DatabaseReference videoRef = getDatabaseReference(startVid);
        DatabaseReference uidRef = videoRef.child(user.getUid());

        String newCommentText = commentText.getText();
        String newCommentId = uidRef.push().getKey(); // Generate a new comment ID

        Thread submitCommentThread = new Thread(() -> {

            uidRef.child(newCommentId).child("text").setValue(newCommentText, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // Handle the error

                        System.out.println("Failed to add comment: " + databaseError.getMessage());
                    } else {
                        // The comment was added successfully
                        platComments.add(userData.setProfileImage(user.getUid()).get("fname").toString() + " "
                                + userData.setProfileImage(user.getUid()).get("lname").toString() + ": " + newCommentText);
                        platImageUrls.add(userData.setProfileImage(user.getUid()).get("profileImage").toString());
                        System.out.println("New comment added with ID: " + newCommentId);

                        Platform.runLater(() -> {
                            // Update the UI
                            platformCommentView.setItems(platComments);
                            platformCommentView.setCellFactory(param -> new ListCell<String>() {
                                private ImageView imageView = new ImageView();

                                @Override
                                protected void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty || item == null) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        setText(item);
                                        int index = getIndex();
                                        String imageUrl = platImageUrls.get(index);

                                        imageView.setImage(new Image(imageUrl));
                                        imageView.setFitWidth(50);
                                        imageView.setFitHeight(50);
                                        setGraphic(imageView);
                                        // set the width's
                                        setMinWidth(param.getWidth());
                                        setMaxWidth(param.getWidth());
                                        setPrefWidth(param.getWidth());
                                        // allow wrapping
                                        setWrapText(true);
                                    }
                                }
                            });
                        });
                    }
                }
            });

        });
        submitCommentThread.start();
    }

    @FXML
    void searchFunction(ActionEvent event) throws IOException {
        stopTimer();
        storeWatchtime();
        Searchresults = Search.returnArray(searchTxtField.getText());
        PrimarySearchResultsController.SearchControllerresults = Searchresults;
        if (searchTxtField.getText() == null) {
            System.out.println("Error in search");
        } else {
            App.setRoot("primary_SearchResult");
        }

    }

    public PrimaryVideoController() {
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

        App.setRoot("Twitch_Primary");
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
    void twitchMode(ActionEvent event) throws IOException {
        App.setRoot("Twitch_Primary");
    }

    @FXML
    void minimizeCommand(MouseEvent event) {
        App.stage.setIconified(true);
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
        firebaseAuth = FirebaseAuth.getInstance();
        App.fullscreen();

        VidObj[] help = new VidObj[20];
        for (int i = 0; i < 20; i++) {
            help[i] = new VidObj(relatedVids.getItems().get(i).getId().getVideoId(),
                    relatedVids.getItems().get(i).getSnippet().getTitle(),
                    relatedVids.getItems().get(i).getSnippet().getChannelTitle());
        }

        testvb = new VBox[7];

        if (webVideoView.getPrefWidth() == 512) {
            webVideoView.setPrefWidth(1024);
            webVideoView.setPrefHeight(576);
            recommendedTab.getChildren().clear();
            testvb = new VBox[help.length];
            for (int i = 0; i < help.length; i++) {
                testvb[i] = new VBox();
                ImageView imv = new ImageView();
                Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
                imv.setFitWidth(260);
                imv.setFitHeight(160);
                imv.setImage(img);
                Label tlabel = new Label();
                tlabel.setMaxWidth(260);
                tlabel.setText(help[i].title);

                int placeholder = i;
                imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("working");

                        loadPage(help[placeholder].id);
                        titleTxt.setText(help[placeholder].title);
                        channelTxt.setText(help[placeholder].channel);
//                        try {
//                            CommentThreadListResponse response = Comments.getCommentsFromVideo(help[placeholder].id);
//                            response.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName();
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
                    }
                });
                testvb[i].getChildren().add(imv);
                testvb[i].getChildren().add(tlabel);

                recommendedTab.getChildren().add(testvb[i]);
            }

        } else {
            webVideoView.setPrefWidth(512);
            webVideoView.setPrefHeight(288);
            recommendedTab.getChildren().clear();
            testvb = new VBox[help.length];
            for (int i = 0; i < help.length; i++) {
                testvb[i] = new VBox();
                ImageView imv = new ImageView();
                Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
                imv.setFitWidth(130);
                imv.setFitHeight(80);
                imv.setImage(img);
                Label tlabel = new Label();
                tlabel.setMaxWidth(130);
                tlabel.setText(help[i].title);

                int placeholder = i;
                imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("working");

                        loadPage(help[placeholder].id);
                        titleTxt.setText(help[placeholder].title);
                        channelTxt.setText(help[placeholder].channel);
                    }
                });
                testvb[i].getChildren().add(imv);
                testvb[i].getChildren().add(tlabel);

                recommendedTab.getChildren().add(testvb[i]);
            }
        }
    }
    double totalWatchTimeFromYesterday;

    //double  totalWatchTimeForToday;
    public void caluluateTotalWeeklyWatchtime() {
        //int Tommorrow =(int)  userData.getProfileDataMap().get("tommorrow's Date");

        //if(today == Tommorrow){
        //  totalWatchTimeFromYesterday=(double)  userData.getYTDailyWatchDataMap().get(YestardyString);
        //totalWatchTimeForToday=(double)  userData.getYTDailyWatchDataMap().get();                 
        //  }
        // double totalWatchTimeForToday=(double)  userData.getYTDailyWatchDataMap().get("WeeklyWatchTime");
        //  totalWeeklyWatchTime= totalWatchTimeForToday;
        //totalWatchTimeFromYesterday=(double)  userData.getYTDailyWatchDataMap().get(yesterDayString);
        switch (day) {
            case Calendar.MONDAY:
                dateString = "mondayWatchTime";
                totalWatchTimeFromYesterday = (double) userData.getYTDailyWatchDataMap().get("WeeklyWatchTime");
                totalYoutubeWeeklyWatchTime = totalWatchTimeFromYesterday + ((double) userData.getYTDailyWatchDataMap().get(dateString));
                Map<String, Object> WeeklyWatchTimeMap = UserData.getInstance().getYTDailyWatchDataMap();
                WeeklyWatchTimeMap.put("WeeklyWatchTime", totalYoutubeWeeklyWatchTime);
                UserData.getInstance().updateYoutubeWatchTime(WeeklyWatchTimeMap);

                // totalWeeklyWatchTime=(double)  userData.getYTDailyWatchDataMap().get(dateString);       
                break;

            case Calendar.TUESDAY:
                dateString = "tuesdayWatchTime";
                totalWatchTimeFromYesterday = (double) userData.getYTDailyWatchDataMap().get("WeeklyWatchTime");
                totalYoutubeWeeklyWatchTime = totalWatchTimeFromYesterday + ((double) userData.getYTDailyWatchDataMap().get(dateString));
                Map<String, Object> WeeklyTuesdayWatchTimeMap = UserData.getInstance().getYTDailyWatchDataMap();
                WeeklyTuesdayWatchTimeMap.put("WeeklyWatchTime", totalYoutubeWeeklyWatchTime);
                UserData.getInstance().updateYoutubeWatchTime(WeeklyTuesdayWatchTimeMap);

                //totalWeeklyWatchTime=(double)  userData.getYTDailyWatchDataMap().get(dateString);       
                break;

            case Calendar.WEDNESDAY:
                totalWatchTimeFromYesterday = (double) userData.getYTDailyWatchDataMap().get("WeeklyWatchTime");
                totalYoutubeWeeklyWatchTime = totalWatchTimeFromYesterday + ((double) userData.getYTDailyWatchDataMap().get(dateString));
                Map<String, Object> WeeklyWednesdayWatchTimeMap = UserData.getInstance().getYTDailyWatchDataMap();
                WeeklyWednesdayWatchTimeMap.put("WeeklyWatchTime", totalYoutubeWeeklyWatchTime);
                UserData.getInstance().updateYoutubeWatchTime(WeeklyWednesdayWatchTimeMap);

                break;

            case Calendar.THURSDAY:
                totalWatchTimeFromYesterday = (double) userData.getYTDailyWatchDataMap().get("WeeklyWatchTime");
                totalYoutubeWeeklyWatchTime = totalWatchTimeFromYesterday + ((double) userData.getYTDailyWatchDataMap().get(dateString));
                Map<String, Object> WeeklyThursdayWatchTimeMap = UserData.getInstance().getYTDailyWatchDataMap();
                WeeklyThursdayWatchTimeMap.put("WeeklyWatchTime", totalYoutubeWeeklyWatchTime);
                UserData.getInstance().updateYoutubeWatchTime(WeeklyThursdayWatchTimeMap);

                //otalWeeklyWatchTime=(double)  userData.getYTDailyWatchDataMap().get(dateString);   
                break;

            case Calendar.FRIDAY:

                totalWatchTimeFromYesterday = (double) userData.getYTDailyWatchDataMap().get("WeeklyWatchTime");
                totalYoutubeWeeklyWatchTime = totalWatchTimeFromYesterday + ((double) userData.getYTDailyWatchDataMap().get(dateString));
                Map<String, Object> WeeklyFridayWatchTimeMap = UserData.getInstance().getYTDailyWatchDataMap();
                WeeklyFridayWatchTimeMap.put("WeeklyWatchTime", totalYoutubeWeeklyWatchTime);
                UserData.getInstance().updateYoutubeWatchTime(WeeklyFridayWatchTimeMap);

                break;

            case Calendar.SATURDAY:

                totalWatchTimeFromYesterday = (double) userData.getYTDailyWatchDataMap().get("WeeklyWatchTime");
                totalYoutubeWeeklyWatchTime = totalWatchTimeFromYesterday + ((double) userData.getYTDailyWatchDataMap().get(dateString));
                Map<String, Object> WeeklySaturdayWatchTimeMap = UserData.getInstance().getYTDailyWatchDataMap();
                WeeklySaturdayWatchTimeMap.put("WeeklyWatchTime", totalYoutubeWeeklyWatchTime);
                UserData.getInstance().updateYoutubeWatchTime(WeeklySaturdayWatchTimeMap);
                break;
            //    break;    //    break;

            case Calendar.SUNDAY:

                totalWatchTimeFromYesterday = (double) userData.getYTDailyWatchDataMap().get("WeeklyWatchTime");
                totalYoutubeWeeklyWatchTime = totalWatchTimeFromYesterday + ((double) userData.getYTDailyWatchDataMap().get(dateString));
                Map<String, Object> WeeklySundayWatchTimeMap = UserData.getInstance().getYTDailyWatchDataMap();
                WeeklySundayWatchTimeMap.put("WeeklyWatchTime", totalYoutubeWeeklyWatchTime);
                UserData.getInstance().updateYoutubeWatchTime(WeeklySundayWatchTimeMap);

                break;
        }

        //TODO: make the interface more dynamic (hard)
    }

    public void storeWatchtime() {
        double currenttime = (double) userData.getYTDailyWatchDataMap().get(dateString);
        double miunteswatchtime = (double) elapsedTime / 6000; // convert to minutes
        double newwatchtime = (double) elapsedTime / (1000 * 60 * 60); // convert to hours
        System.out.println("the watch time " + miunteswatchtime);
        System.out.println("the watch time " + newwatchtime);
        currenttime += newwatchtime;

        Map<String, Object> dailyWatchTimeMap = UserData.getInstance().getYTDailyWatchDataMap();
        //caluluateTotalWeeklyWatchtime(time);
        dailyWatchTimeMap.put(dateString, currenttime);

        UserData.getInstance().updateYoutubeWatchTime(dailyWatchTimeMap);
        caluluateTotalWeeklyWatchtime();

    }
}
