package com.mycompany.streamfox;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobWriteOption;
import com.google.cloud.storage.Storage.PredefinedAcl;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;
import static com.mycompany.streamfox.App.scene;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import static com.mycompany.streamfox.App.xOffset;
import static com.mycompany.streamfox.App.yOffset;
import static com.mycompany.streamfox.PrimaryHomeController.dateString;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
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
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.mail.Session;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PrimaryProfileController implements Initializable {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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
    private BarChart watchTimeGraph;

    @FXML
    private AnchorPane topBar;
    
    @FXML
    private Label hoursLabel;

    private DialogPane dialog;

    private int onOff = 0;

    User user = User.getInstance();
    UserData userData = UserData.getInstance();
    
    @FXML
    
    private Label setTotalWeeklyWatchTime;
    @FXML
    
    private Label setTotalDailyWatchtime;
    
      @FXML
    
    private Label dailyTimeUnitLabel;
      
           
      @FXML
    
    private Label weeklyTimeUnitLabel;
       String  unitLabelDay;
         String  unitLabelWeek;
      
      
      
      
      
      
    
    
    
    double  tempDailyWatchTime;
    double  tempWeeklyWatchTime;

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
        hoursLabel.setRotate(270);
        tempDailyWatchTime=(double)  userData.getYTDailyWatchDataMap().get(dateString);
          tempWeeklyWatchTime=(double)  userData.getYTDailyWatchDataMap().get("WeeklyWatchTime");
         unitLabelDay="Hours";
        unitLabelWeek="Hours";
         
         if(tempDailyWatchTime<1) {
             tempDailyWatchTime= Math.round( tempDailyWatchTime*60);
              unitLabelDay="Minutes";
             
         }
         
              
         if(tempWeeklyWatchTime<1) {
             tempWeeklyWatchTime= Math.round( tempWeeklyWatchTime*60);
             unitLabelWeek="Minutes";
             
         }
         
         dailyTimeUnitLabel.setText(  unitLabelDay);
         weeklyTimeUnitLabel.setText(unitLabelWeek);
          hoursLabel.setText(unitLabelDay);
         
         
         

        
    
        
      
       
        setTotalDailyWatchtime.setText(Double.toString( tempDailyWatchTime));
        setTotalWeeklyWatchTime.setText(Double.toString(tempWeeklyWatchTime));
       
        
        
        
          

           
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
        userNameMenuBtn.setText(((String) userData.getProfileDataMap().get("fname")) + " " + ((String) userData.getProfileDataMap().get("lname")));
        
        
        //System.out.println(userData.getYTWatchDaysDataMap().get("fridayWatchTime").toString());
        NumberAxis yAxis = new NumberAxis(); 
           yAxis.setLabel("score");
        XYChart.Series youtubeSeries = new XYChart.Series();
        youtubeSeries.setName("Youtube");
        youtubeSeries.getData().add(new XYChart.Data("Monday", userData.getYTDailyWatchDataMap().get("mondayWatchTime")));
        youtubeSeries.getData().add(new XYChart.Data("Tuesday", userData.getYTDailyWatchDataMap().get("tuesdayWatchTime")));
        youtubeSeries.getData().add(new XYChart.Data("Wednesday", userData.getYTDailyWatchDataMap().get("wednesdayWatchTime")));
        youtubeSeries.getData().add(new XYChart.Data("Thursday", userData.getYTDailyWatchDataMap().get("thursdayWatchTime")));
        youtubeSeries.getData().add(new XYChart.Data("Friday", userData.getYTDailyWatchDataMap().get("fridayWatchTime")));
        youtubeSeries.getData().add(new XYChart.Data("Saturday", userData.getYTDailyWatchDataMap().get("saturdayWatchTime")));
        youtubeSeries.getData().add(new XYChart.Data("Sunday", userData.getYTDailyWatchDataMap().get("sundayWatchTime")));
        
        XYChart.Series netflixSeries = new XYChart.Series();
        netflixSeries.setName("Netflix");
        netflixSeries.getData().add(new XYChart.Data("Monday", 0));
        netflixSeries.getData().add(new XYChart.Data("Tuesday", 0));
        netflixSeries.getData().add(new XYChart.Data("Wednesday", 0));
        netflixSeries.getData().add(new XYChart.Data("Thursday", 0));
        netflixSeries.getData().add(new XYChart.Data("Friday", 0));
        netflixSeries.getData().add(new XYChart.Data("Saturday", 0));
        netflixSeries.getData().add(new XYChart.Data("Sunday", 0));
        
        watchTimeGraph.getData().addAll(youtubeSeries,netflixSeries);
      
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
    void closeCommand(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void resetPassFunc(MouseEvent event) throws FirebaseAuthException {
        
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Password Cofirmation Requiered");
        alert.setHeaderText("Please press OK or Cancel to Confirm that you would like to reset your password \n or Go Back to the Previous Menu ");
        alert.setResizable(false);
        alert.setContentText("Are you sure? ");
        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();
        /*if (!result.isPresent()) {

        }*/ // alert is exited, no button has been pressed.
         if (result.get() == ButtonType.OK) {
            Alert passalert = new Alert(Alert.AlertType.INFORMATION);
        passalert.initStyle(StageStyle.UNDECORATED);
        passalert.setTitle("Reset Password");
        passalert.setHeaderText("Use following link to reset your password");

        WebView webView = new WebView();
        webView.setPrefSize(600, 400);
        WebEngine webEngine = webView.getEngine();
        webEngine.load(firebaseAuth.generatePasswordResetLink(user.getUserEmail()));
        //firebaseAuth.generatePasswordResetLink(user.getUserEmail())
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(webView);
        passalert.setDialogPane(dialogPane);
        dialog = passalert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
        ButtonType buttonType = new ButtonType("Close", ButtonData.OK_DONE);
        passalert.getButtonTypes().setAll(buttonType);
        passalert.showAndWait();


        } //ok button is pressed
        else if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
        // cancel button is pressed

        //for when reset label is pressed
        
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "<your-email-service-host>");
//        props.put("mail.smtp.port", "<your-email-service-port>");
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("maazhussaini@outlook.com", "dude1962");
//            }
//        });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("<your-email-address>"));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getUserEmail()));
//            message.setSubject("Password reset");
//            message.setText("Click the following link to reset your password: " + firebaseAuth.generatePasswordResetLink(user.getUserEmail()));
//            Transport.send(message);
//            System.out.println("Password reset email sent");
//        } catch (MessagingException e) {
//            System.out.println("Error sending password reset email: " + e.getMessage());
//        }
    }

    @FXML
    void saveChanges(ActionEvent event) {
        //for when save is pressed
        Map<String, Object> map = UserData.getInstance().getProfileDataMap();
        map.put("fname", profFirstNameTxt.getText());
        map.put("lname", profLastNameTxt.getText());
        UserData.getInstance().updateProfile(map);
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
        App.setRoot("Youtube");
    }

    @FXML
    void switchToTwitch(ActionEvent event) throws IOException {
        App.setRoot("TwitchPrimary");
    }

    @FXML
    void switchToSettings(ActionEvent event) throws IOException {
        App.setRoot("Settings");
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

        //toggles fullscreen on and off
        //TODO: make the interface more dynamic (hard)
        System.out.println("fullscreen");
        App.fullscreen();
    }

    @FXML
    void changePic(MouseEvent event) throws IOException {
        FileChooser fileC = new FileChooser();

        FileChooser.ExtensionFilter filterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter filterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter filterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");

        fileC.getExtensionFilters().addAll(filterJPG, filterjpg, filterPNG, filterpng);

        File file = fileC.showOpenDialog(null);

        StorageClient storageClient = StorageClient.getInstance(FirebaseStart.fa);

        File localFile = new File(file.getAbsolutePath());

// Set options for the upload
        InputStream uploadFile = new FileInputStream(localFile); // userselection is the image path that the user chose
        String blobString = localFile.getName();;
        BlobInfo blobInfo = storageClient.bucket().create(blobString, uploadFile, Bucket.BlobWriteOption.userProject("streamfox-966e7"));
        BlobId blobId = blobInfo.getBlobId();
        Storage storage = storageClient.bucket().getStorage();
        long duration = 1L; // Time duration for the signed URL (in minutes)
        String signedUrl = storage.signUrl(blobInfo, duration, TimeUnit.HOURS).toString();

// Print the signed URL
        System.out.println("Signed URL: " + signedUrl);
        //String fileUrl = blobInfo.;
        Map<String, Object> profileMap = UserData.getInstance().getProfileDataMap();
        profileMap.put("profileImage", signedUrl);
        UserData.getInstance().updateProfile(profileMap);

        UserData.getInstance().setProfile();
        profCircle.setFill(new ImagePattern(new Image((String) UserData.getInstance().getProfileDataMap().get("profileImage"))));
        userProfView.setFill(new ImagePattern(new Image((String) UserData.getInstance().getProfileDataMap().get("profileImage"))));
        //System.out.println(fileUrl);
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
