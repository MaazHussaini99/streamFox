package com.mycompany.streamfox;

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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
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
    private ListView<?> commentView;
    
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

    VBox[] testvb;

    User user = User.getInstance();
    UserData userData = UserData.getInstance();

    SearchListResponse relatedVids;

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
        
        String startVid;
       
        if(PrimaryController.VIDload !=null){
            startVid = PrimaryController.VIDload;
            titleTxt.setText(PrimaryController.titleLoad);
            channelTxt.setText(PrimaryController.channelLoad);
        }else{
            startVid = PrimaryHomeController.VIDload;
            titleTxt.setText(PrimaryHomeController.titleLoad);
            channelTxt.setText(PrimaryHomeController.channelLoad);
        }
        
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

        //VidObj[] help = new VidObj[15];
//        help[0] = new VidObj("YLt73w6criQ", "I Paid A Real Assassin To Try To Kill Me", "MrBeast");
//        help[1] = new VidObj("dT6taoucBX4", "SCREAMS SCREAMS and MORE SCREAMS [Fears To Fathom: Norwood Hitchhike]", "CoryxKenshin");
//        help[2] = new VidObj("_F6YBwIPzmk", "Star Wars Jedi: Survivor - Official Story Trailer", "EA Star Wars");
//        help[3] = new VidObj("LtwaDBjNop0", "Resumen de FC Barcelona vs Real Madrid (2-1)", "LaLiga Santander");
//        help[4] = new VidObj("DOWDNBu9DkU", "Amazing Invention- This Drone Will Change Everything", "Mark Rober");
//        help[5] = new VidObj("scTOJJbecGw", "Fooling my Friend with the LOUDEST SOUND in Minecraft", "Doni Bobes");
//        help[6] = new VidObj("EDnwWcFpObo", "NMIXX 'Love Me Like This' M/V", "JYP Entertainment");
//        help[7] = new VidObj("bEKmOVP-SOI", "Can You ACTUALLY Win Money on Gameshows?", "Jaiden Animations");
//        help[8] = new VidObj("moIuur9GUws", "World's Brightest Flashlight | OT38", "Dude Perfect");
//        help[9] = new VidObj("q3FXUUV3hWA", "Different Childhood Sleepovers (pt.5) | Ep.1 Dtay Known", "Dtay Known");
//        help[10] = new VidObj("5RNrCRjZO0M", "I Played Diablo 4 Beta.. My HONEST Thoughts", "Asmongold TV ");
//        help[11] = new VidObj("S9EnUSSU7HI", "I Trapped 25 TikTokers In A Box", "Airrack");
//        help[12] = new VidObj("clJyTJ3vvh4", "Momoshiki vs Kawaki | Boruto: Naruto Next Generations", "Crunchyroll Collection");
//        help[13] = new VidObj("myNFxrTMczA", "Best Watercolor Art Wins $5,000!", "ZHC Crafts");
//        help[14] = new VidObj("PZM6j8bKnks", "Ben Affleck and Matt Damon on 'Air'", "CBS Sunday Morning");
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

        userNameMenuBtn.setText(((String) userData.getProfileDataMap().get("fname")) + " " + ((String) userData.getProfileDataMap().get("lname")));
        userProfView.setFill(new ImagePattern(new Image((String) userData.getProfileDataMap().get("profileImage"))));

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
    }

    @FXML
    void switchToYT(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void switchToProfile(ActionEvent event) throws IOException {
        App.setRoot("primary_Profile");
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
