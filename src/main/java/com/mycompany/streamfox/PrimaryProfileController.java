package com.mycompany.streamfox;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import static com.mycompany.streamfox.App.scene;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import static com.mycompany.streamfox.App.xOffset;
import static com.mycompany.streamfox.App.yOffset;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
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
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class PrimaryProfileController implements Initializable {

    private FirebaseAuth firebaseAuth;

    @FXML
    private Button YTBtn;
    
    @FXML
    private AnchorPane backPane;

    @FXML
    private Button cancelBtn;

    @FXML
    private ImageView closeWindow;

    @FXML
    private ImageView closeWindow1;

    @FXML
    private AnchorPane frontPane;

    @FXML
    private ImageView menuOpen;

    @FXML
    private ImageView minimizeWindow;

    @FXML
    private Circle profCircle;

    @FXML
    private ImageView ytLogoView;
    
    @FXML
    private Circle userProfView;
    
    @FXML
    private TextField profEmailTxt;

    @FXML
    private TextField profFirstNameTxt;

    @FXML
    private Button userNameMenuBtn;
    
    @FXML
    private TextField profLastNameTxt;

    @FXML
    private TextField profPassTxt;

    @FXML
    private Button saveBtn;

    @FXML
    private AnchorPane topBar;

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
        setValues();
        userNameMenuBtn.setText( ((String)userData.getProfileDataMap().get("fname"))+ " " +((String) userData.getProfileDataMap().get("lname")));
    }

    private void setValues() {
        profFirstNameTxt.setText((String) userData.getProfileDataMap().get("fname"));
        profLastNameTxt.setText((String) userData.getProfileDataMap().get("lname"));
        profEmailTxt.setText((String) userData.getProfileDataMap().get("email"));
        //add functionality to initialize circle with profile image through
        profCircle.setFill(new ImagePattern(new Image((String) userData.getProfileDataMap().get("profileImage"))));
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

    public PrimaryProfileController() {

        System.out.println(user);
    }

    @FXML
    void cancelFunc(ActionEvent event) {
        //Set all fields back to original value
    }

    @FXML
    void saveFunc(ActionEvent event) {
        //add all new values
    }

    @FXML
    void closeCommand(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void resetPassFunc(MouseEvent event) {
        //for when reset label is pressed
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
    void switchToYT(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }
    @FXML
    void switchToNetflix(ActionEvent event) throws IOException {
        App.setRoot("NetflixSignIn");
    }


    @FXML
    void switchToSettings(ActionEvent event) throws IOException {
        App.setRoot("primary_Settings");
    }
    
    @FXML
    void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("primary_Home");
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
    }

    @FXML
    void changePic(MouseEvent event) {
        FileChooser fileC = new FileChooser();

        FileChooser.ExtensionFilter filterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter filterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter filterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");

        fileC.getExtensionFilters().addAll(filterJPG, filterjpg, filterPNG, filterpng);
        
        File file = fileC.showOpenDialog(null);
        file.getAbsolutePath();
        
    }
    
    @FXML
    void YTBtnEnter(MouseDragEvent event) {
        Image newImg = new Image("/src/main/resources/youtube.png");
        ytLogoView.setImage(newImg);
    }

    @FXML
    void YTBtnExit(MouseDragEvent event) {
        Image newImg = new Image("/src/main/resources/youtubeGray.png");
        ytLogoView.setImage(newImg);
    }

}
