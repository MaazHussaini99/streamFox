package com.mycompany.streamfox;

import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
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
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    private WebEngine we;

    private int onOff = 0;

    public static VidObj[] Searchresults;
    
    VBox[] testvb;

    User user = User.getInstance();
    UserData userData = UserData.getInstance();

    SearchListResponse relatedVids;
    CommentThreadListResponse comments;

    static String startVid;
    static String titleStartText;
    static String channelStartText;
    
    private long startTime = 0;
private long stopTime = 0;
private boolean running = false;
private long elapsedTime = 0;
 private DialogPane dialog;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    if(App.stage.isFullScreen() == false)
    {
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
    }else{
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
    }}    

        userNameMenuBtn.setText(((String) userData.getProfileDataMap().get("fname")) + " " + ((String) userData.getProfileDataMap().get("lname")));
        userProfView.setFill(new ImagePattern(new Image((String) userData.getProfileDataMap().get("profileImage"))));
        
        //adding comments
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
    }

    public void loadPage(String VID) {
        we.load("https://www.youtube.com/embed/" + VID);

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
    void searchFunction(ActionEvent event) throws IOException {
        
        Searchresults = Search.returnArray(searchTxtField.getText());
        
        if(searchTxtField.getText() == null){
            System.out.println("Error in search");
        }else{
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
    long elapsedMinutes = elapsedTime / 60000; // convert to minutes
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                         alert.setTitle("Your watchtime has been recored");
                         alert.setHeaderText("watchtime Notfication");
                        alert.setContentText("your current daily watchtime is: " + elapsedMinutes+"Minutes");
                         dialog = alert.getDialogPane();
                         dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
                        alert.showAndWait();
            
    }

    @FXML
    void switchToYT(ActionEvent event) throws IOException {
        App.setRoot("Youtube");
             stopTimer();
    long elapsedMinutes = elapsedTime / 60000; // convert to minutes
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                         alert.setTitle("Your watchtime has been recored");
                         alert.setHeaderText("watchtime Notfication");
                        alert.setContentText("your current daily watchtime is: " + elapsedMinutes+" Minutes");
                         dialog = alert.getDialogPane();
                         dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
                        alert.showAndWait();
            
    }
    
  @FXML
    void switchToTwitch(ActionEvent event) throws IOException {
        App.setRoot("TwitchPrimary");
             stopTimer();
    long elapsedMinutes = elapsedTime / 60000; // convert to minutes
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                         alert.setTitle("Your watchtime has been recored");
                         alert.setHeaderText("watchtime Notfication");
                        alert.setContentText("your current daily watchtime is: " + elapsedMinutes+" Minutes");
                         dialog = alert.getDialogPane();
                         dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
                        alert.showAndWait();
            
    }
    


    @FXML
    void switchToProfile(ActionEvent event) throws IOException {
        App.setRoot("primary_Profile");
             stopTimer();
    long elapsedMinutes = elapsedTime / 60000; // convert to minutes
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                         alert.setTitle("Your watchtime has been recored");
                         alert.setHeaderText("watchtime Notfication");
                        alert.setContentText("your current daily watchtime is: " + elapsedMinutes+" Minutes");
                         dialog = alert.getDialogPane();
                         dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
                        alert.showAndWait();
            
    }

    @FXML
    void switchToSettings(ActionEvent event) throws IOException {
        App.setRoot("Settings");
             stopTimer();
    long elapsedMinutes = elapsedTime / 60000; // convert to minutes
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                         alert.setTitle("Your watchtime has been recored");
                         alert.setHeaderText("watchtime Notfication");
                        alert.setContentText("your current daily watchtime is: " + elapsedMinutes+" Minutes");
                         dialog = alert.getDialogPane();
                         dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
                        alert.showAndWait();
            
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

        //TODO: make the interface more dynamic (hard)
    }

}
