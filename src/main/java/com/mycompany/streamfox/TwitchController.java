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

public class TwitchController {

    @FXML
    private AnchorPane backPane;

    @FXML
    private ImageView channelPic;

    @FXML
    private Label channelTxt;

    @FXML
    private ImageView closeWindow;

    @FXML
    private ImageView closeWindow1;

    @FXML
    private ListView<?> commentView;

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

    public void initialize(URL url, ResourceBundle rb) {

        options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .enableProprietaryFeature(ProprietaryFeature.AAC)
                .enableProprietaryFeature(ProprietaryFeature.H_264)
                .licenseKey("1BNDHFSC1G5ZOFBWG6WQUSLCBTDAYZZXMAP2GRH6RECP8NHENP4ZY4YHBV1MUUDQTXFCFF")
                .build();

        engine = Engine.newInstance(options);
        browser = engine.newBrowser();
        loadPage(startVid);
        view = BrowserView.newInstance(browser);
        view.setPrefSize(300, 500);
        view.setVisible(true);
        browserVBox.getChildren().add(0, view);
        
        

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

        //we = webVideoView.getEngine();

        titleTxt.setText(titleStartText);
        channelTxt.setText(channelStartText);

        System.out.println(startVid);
        

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
            //webVideoView.setPrefWidth(1024);
            //webVideoView.setPrefHeight(576);
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

//        commentView.setItems(obComments);
//        commentView.setCellFactory(param -> new ListCell<String>() {
//            private ImageView imageView = new ImageView();
//
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || item == null) {
//                    setGraphic(null);
//                    setText(null);
//                } else {
//                    setText(item);
//                    int index = getIndex();
//                    String imageUrl = imageUrls.get(index);
//                    imageView.setImage(new Image(imageUrl));
//                    setGraphic(imageView);
//                    // set the width's
//                    setMinWidth(param.getWidth());
//                    setMaxWidth(param.getWidth());
//                    setPrefWidth(param.getWidth());
//                    // allow wrapping
//                    setWrapText(true);
//                }
//            }
//        });
    }

    @FXML
    void closeCommand(MouseEvent event) {

    }

    @FXML
    void fullscreen(MouseEvent event) {

    }

    @FXML
    void minimizeCommand(MouseEvent event) {

    }

    @FXML
    void searchFunction(ActionEvent event) {

    }

    @FXML
    void switchToHome(ActionEvent event) {

    }

    @FXML
    void switchToNetflix(ActionEvent event) {

    }

    @FXML
    void switchToProfile(ActionEvent event) {

    }

    @FXML
    void switchToSettings(ActionEvent event) {

    }

    @FXML
    void switchToTwitch(ActionEvent event) {

    }

    @FXML
    void switchToYT(ActionEvent event) {

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
