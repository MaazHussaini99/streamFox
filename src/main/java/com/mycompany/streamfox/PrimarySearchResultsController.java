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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

public class PrimarySearchResultsController implements Initializable {

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
    private VBox searchResultsBox;

    VBox[] testvb;

    private int onOff = 0;

    public static VidObj[] Searchresults;
    
    @FXML
    private TextField searchTxtField;
    
    private boolean fullscreenBool;

    public static String VIDload;
    public static String titleLoad;
    public static String channelLoad;
    private DialogPane dialog;

    User user = User.getInstance();
    UserData userData = UserData.getInstance();
    public static VidObj[] SearchControllerresults;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        VidObj[] help;

        help = SearchControllerresults;

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

        if (App.stage.isFullScreen() == false) {

            testvb = new VBox[26];
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
                        PrimaryVideoController.startVid = help[placeholder].id;
                        PrimaryVideoController.titleStartText = help[placeholder].title;
                        PrimaryVideoController.channelStartText = help[placeholder].channel;
                        try {
                            playVideoMode(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                testvb[i].getChildren().add(imv);
                testvb[i].getChildren().add(tlabel);

                searchResultsBox.getChildren().add(testvb[i]);
            }

        } else {

            testvb = new VBox[26];
            for (int i = 0; i < help.length; i++) {
                testvb[i] = new VBox();
                ImageView imv = new ImageView();
                Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
                imv.setFitWidth(800);
                imv.setFitHeight(400);
                imv.setImage(img);
                Label tlabel = new Label();
                tlabel.setMaxWidth(800);
                tlabel.setText(help[i].title);

                int placeholder = i;
                imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("working");
                        PrimaryVideoController.startVid = help[placeholder].id;
                        PrimaryVideoController.titleStartText = help[placeholder].title;
                        PrimaryVideoController.channelStartText = help[placeholder].channel;
                        try {
                            playVideoMode(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                testvb[i].getChildren().add(imv);
                testvb[i].getChildren().add(tlabel);

                searchResultsBox.getChildren().add(testvb[i]);
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

    public PrimarySearchResultsController() {
        User user = User.getInstance();
        UserData userData = new UserData();
        System.out.println(user);
    }

    @FXML
    void closeCommand(MouseEvent event) {
        System.exit(0);
    }

     @FXML
    void searchFunction(ActionEvent event) throws IOException {

        Searchresults = Search.returnArray(searchTxtField.getText());
        PrimarySearchResultsController.SearchControllerresults = Searchresults;
        if (searchTxtField.getText() == null) {
            System.out.println("Error in search");
        } else {
            App.setRoot("primary_SearchResult");
        }

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
    void switchToTwitch(ActionEvent event) throws IOException {
        App.setRoot("Twitch_video");
    }

    @FXML
    void twitchMode(ActionEvent event) throws IOException {
        App.setRoot("Twitch_Primary");
    }

    @FXML
    void switchToProfile(ActionEvent event) throws IOException {
        App.setRoot("primary_Profile");
    }

    @FXML
    void switchToSettings(ActionEvent event) throws IOException {
        App.setRoot("primary_Settings");
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

    /**
     * switches the application to and from fullscreen mode
     */
    @FXML
    private void fullscreen() {
        firebaseAuth = FirebaseAuth.getInstance();

        VidObj[] help;

        help = SearchControllerresults;
        
        searchResultsBox.getChildren().clear();
        System.out.println("fullscreen");
        App.fullscreen();
        if (App.stage.isFullScreen() == false) {

            testvb = new VBox[26];
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
                        PrimaryVideoController.startVid = help[placeholder].id;
                        PrimaryVideoController.titleStartText = help[placeholder].title;
                        PrimaryVideoController.channelStartText = help[placeholder].channel;
                        try {
                            playVideoMode(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                testvb[i].getChildren().add(imv);
                testvb[i].getChildren().add(tlabel);

                searchResultsBox.getChildren().add(testvb[i]);
            }

        } else {

            testvb = new VBox[26];
            for (int i = 0; i < help.length; i++) {
                testvb[i] = new VBox();
                ImageView imv = new ImageView();
                Image img = new Image("https://img.youtube.com/vi/" + help[i].id + "/sddefault.jpg");
                imv.setFitWidth(800);
                imv.setFitHeight(400);
                imv.setImage(img);
                Label tlabel = new Label();
                tlabel.setMaxWidth(800);
                tlabel.setText(help[i].title);

                int placeholder = i;
                imv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("working");
                        PrimaryVideoController.startVid = help[placeholder].id;
                        PrimaryVideoController.titleStartText = help[placeholder].title;
                        PrimaryVideoController.channelStartText = help[placeholder].channel;
                        try {
                            playVideoMode(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                testvb[i].getChildren().add(imv);
                testvb[i].getChildren().add(tlabel);

                searchResultsBox.getChildren().add(testvb[i]);
            }

        }

    }

}
