/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class PrimarySettingsController implements Initializable {

    private FirebaseAuth firebaseAuth;

    @FXML
    private ImageView menuOpen;

    @FXML
    private ImageView minimizeWindow;

    @FXML
    private AnchorPane topBar;

    @FXML
    private ImageView closeWindow;

    @FXML
    private ImageView closeWindow1;

    @FXML
    private AnchorPane frontPane;

    @FXML
    private CheckBox twitchCheckBox;

    @FXML
    private Spinner<Integer> TwitchDailyWatchTime;

    @FXML
    private Spinner<Integer> TwitchWeeklyWatchTime;

    @FXML
    private Spinner<Integer> DailyWatchTime;

    @FXML
    private Spinner<Integer> WeeklyWatchTime;

    @FXML
    private CheckBox youtubeCheckBox;

    @FXML
    private Spinner<Integer> YoutubeDailyWatchTime;

    @FXML
    private Spinner<Integer> YoutubeWeeklyWatchTime;
    @FXML
    private Button DeleteYA;
    @FXML
    private Button DisableYA;
    @FXML
    private MenuButton StreamingServiceMenu;

    @FXML
    private Button userNameMenuBtn;

    private DialogPane dialog;

    User user = User.getInstance();
    UserData userData = UserData.getInstance();

    @FXML
    private Circle userProfView;

    private int onOff = 0;

    static int YoutubeDailyValue = 1;
    static int TwitchDailyValue = 1;

    static int YoutubeWeeklyValue = 1;
    static int TwitchWeeklyValue = 1;

    static int totalDaily;
    static int totalWeekly;

    boolean hasYoutubeDailyChanged = false;
    boolean hasTwitchDailyChanged = false;

    boolean hasYoutubeWeeklyChanged = false;
    boolean hasTwitchWeeklyChanged = false;

    private void setValues() { // temp 
        DailyWatchTime.setUserData(this);
        WeeklyWatchTime.setUserData(this);

    }

    @FXML
    void DisableYourAcount(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Disable Cofirmation Requiered");
        alert.setHeaderText("Please press OK or Cancel to Confirm That you Want to Disable Your Account \n or Go Back to the Previous Menu ");
        alert.setResizable(false);
        alert.setContentText("Are you sure? ");
        dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {

        } // alert is exited, no button has been pressed.
        else if (result.get() == ButtonType.OK) {

        } //oke button is pressed
        else if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
        // cancel button is pressed

    }

    @FXML
    void DeleteYourAcount(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Cofirmation Requiered");
        alert.setHeaderText("Please press OK or Cancel to Confirm that you want to Delete your Account \n or Go Back to the Previous Menu ");
        alert.setResizable(false);
        alert.setContentText("Are you sure?,Changes can not be undone");
        dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {

        } // alert is exited, no button has been pressed.
        else if (result.get() == ButtonType.OK) {

        } //oke button is pressed
        else if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
        // cancel button is pressed
    }

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

        }
        );

        //move around here
        topBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.stage.setX(event.getScreenX() - App.xOffset);
                App.stage.setY(event.getScreenY() - App.yOffset);
            }
        });

        SpinnerValueFactory<Integer> valueFacDaily = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24);
        valueFacDaily.setValue(24);
        SpinnerValueFactory<Integer> TwitchvalueFacDaily = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24);
        valueFacDaily.setValue(24);
        SpinnerValueFactory<Integer> YoutubevalueFacDaily = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24);
        valueFacDaily.setValue(24);

        SpinnerValueFactory<Integer> valueFacWeekly = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 168);
        valueFacWeekly.setValue(168);
        SpinnerValueFactory<Integer> TwitchvalueFacWeekly = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 168);
        valueFacWeekly.setValue(168);
        SpinnerValueFactory<Integer> YoutubevalueFacWeekly = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 168);
        valueFacWeekly.setValue(168);

        //Setting watch time area we need listeners for each, and as the value is changed it should update firebase in the listener
        DailyWatchTime.setValueFactory(valueFacDaily);
        WeeklyWatchTime.setValueFactory(valueFacWeekly);

        YoutubeDailyWatchTime.setValueFactory(YoutubevalueFacDaily);
        YoutubeWeeklyWatchTime.setValueFactory(YoutubevalueFacWeekly);
        //DailyWatchTime.getValueFactory().setValue(3);

        YoutubeDailyWatchTime.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                YoutubeDailyValue = YoutubeDailyWatchTime.getValue();
                if (hasTwitchDailyChanged = true) {
                    totalDaily = YoutubeDailyValue + TwitchDailyValue;
                    if (totalDaily < totalWeekly) {
                        DailyWatchTime.getValueFactory().setValue(totalDaily);
                        //   YoutubeDailyWatchTime.setDisable(f);
                    } else {

                        DailyWatchTime.getValueFactory().setValue(totalWeekly);
                        totalDaily = totalWeekly;
                        if (totalDaily == totalWeekly) {
                            YoutubeDailyWatchTime.decrement();

                            Node increment = YoutubeDailyWatchTime.lookup(".increment-arrow-button");
                            if (increment != null) {
                                increment.getOnMouseReleased().handle(null);
                            }

                            Node decrement = YoutubeDailyWatchTime.lookup(".decrement-arrow-button");
                            if (decrement != null) {
                                decrement.getOnMouseReleased().handle(null);
                            }

                            YoutubeDailyWatchTime.setDisable(true); // disable the spinner
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Daily watch time is greater than weekly watch time!");
                            alert.setContentText("You cannot increment the spinner further. You can decrease the value.");
                            dialog = alert.getDialogPane();
                            dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
                            alert.showAndWait();

                            YoutubeDailyWatchTime.setDisable(false); // re-enable the spinner after alert is dismissed

                        }
                    }
                }
                hasYoutubeDailyChanged = true;
            }
        });

        TwitchDailyWatchTime.setValueFactory(TwitchvalueFacDaily);
        TwitchWeeklyWatchTime.setValueFactory(TwitchvalueFacWeekly);

        TwitchDailyWatchTime.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                TwitchDailyValue = TwitchDailyWatchTime.getValue();
                if (hasYoutubeDailyChanged = true) {
                    totalDaily = YoutubeDailyValue + TwitchDailyValue;
                    if (totalDaily < totalWeekly) {
                        DailyWatchTime.getValueFactory().setValue(totalDaily);

                    } else {
                        DailyWatchTime.getValueFactory().setValue(totalWeekly);
                        totalDaily = totalWeekly;
                        if (totalDaily == totalWeekly) {
                            TwitchDailyWatchTime.decrement();

                            Node increment = TwitchDailyWatchTime.lookup(".increment-arrow-button");
                            if (increment != null) {
                                increment.getOnMouseReleased().handle(null);
                            }

                            Node decrement = TwitchDailyWatchTime.lookup(".decrement-arrow-button");
                            if (decrement != null) {
                                decrement.getOnMouseReleased().handle(null);
                            }

                            TwitchDailyWatchTime.setDisable(true); // disable the spinner
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Daily watch time is greater than weekly watch time!");
                            alert.setContentText("You cannot increment the spinner further. You can decrease the value.");
                            dialog = alert.getDialogPane();
                            dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
                            alert.showAndWait();

                            TwitchDailyWatchTime.setDisable(false); // re-enable the spinner after alert is dismissed

                        }

                    }
                }
                hasTwitchDailyChanged = true;
            }
        });

        DailyWatchTime.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                totalDaily = DailyWatchTime.getValue();
               YoutubeDailyValue = YoutubeDailyWatchTime.getValue();
                YoutubeWeeklyValue = YoutubeWeeklyWatchTime.getValue();
                    TwitchDailyValue = TwitchDailyWatchTime.getValue();
                TwitchWeeklyValue = TwitchWeeklyWatchTime.getValue();
                if (totalDaily > totalWeekly && YoutubeDailyValue > totalWeekly && TwitchDailyValue > totalWeekly   ) {
                    DailyWatchTime.decrement();

                    Node increment = DailyWatchTime.lookup(".increment-arrow-button");
                    if (increment != null) {
                        increment.getOnMouseReleased().handle(null);
                    }

                    Node decrement = DailyWatchTime.lookup(".decrement-arrow-button");
                    if (decrement != null) {
                        decrement.getOnMouseReleased().handle(null);
                    }

                    DailyWatchTime.setDisable(true); // disable the spinner
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Daily watch time is greater than weekly watch time!");
                    alert.setContentText("You cannot increment the spinner further. You can decrease the value.");
                    dialog = alert.getDialogPane();
                    dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
                    alert.showAndWait();

                    DailyWatchTime.setDisable(false); // re-enable the spinner after alert is dismissed
                }
            }
        });
        YoutubeWeeklyWatchTime.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                YoutubeWeeklyValue = YoutubeWeeklyWatchTime.getValue();
                if (hasTwitchWeeklyChanged) {
                    totalWeekly = YoutubeWeeklyValue + TwitchWeeklyValue;
                    if (totalWeekly > totalDaily)   {
                        WeeklyWatchTime.getValueFactory().setValue(totalWeekly);
                    } else {
                        totalWeekly = totalDaily;
                        WeeklyWatchTime.getValueFactory().setValue(totalWeekly);

                    }

                }
                hasYoutubeWeeklyChanged = true;
            }
        });

        TwitchWeeklyWatchTime.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                TwitchWeeklyValue = TwitchWeeklyWatchTime.getValue();
                if (hasYoutubeWeeklyChanged) {
                    totalWeekly = YoutubeWeeklyValue + TwitchWeeklyValue;
                    if (totalWeekly > totalDaily) {
                        WeeklyWatchTime.getValueFactory().setValue(totalWeekly);

                    } else {
                        totalWeekly = totalDaily;
                        WeeklyWatchTime.getValueFactory().setValue(totalWeekly);
                    }

                }
                hasTwitchWeeklyChanged = true;
            }
        });

        //added visual indicator for all spinners
        WeeklyWatchTime.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                totalWeekly = WeeklyWatchTime.getValue();

            }

        });

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
    void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("primary_Home");
    }

    @FXML
    void saveSettingsChanges(ActionEvent event) {
        //for when save is pressed

        Map<String, Object> totalSettingsWatchTimeLimitMap = UserData.getInstance().getWatchTimeSettingsDataMap();
        totalSettingsWatchTimeLimitMap.put("setDailyLimit", totalDaily);
        totalSettingsWatchTimeLimitMap.put("setWeeklyLimit", totalWeekly);
        UserData.getInstance().updateSettingsForTotalWatchTime(totalSettingsWatchTimeLimitMap);
        
          Map<String, Object> totalTwitchWatchTimeLimitMap = UserData.getInstance().getTwitchDailyWatchDataMap();
         totalTwitchWatchTimeLimitMap.put("setDailyLimit", TwitchDailyValue);
         totalTwitchWatchTimeLimitMap.put("setWeeklyLimit", TwitchWeeklyValue);
        UserData.getInstance().updateTwitchWatchTime( totalTwitchWatchTimeLimitMap);
        
            Map<String, Object> totalYoutubeWatchTimeLimitMap = UserData.getInstance().getYTDailyWatchDataMap();
          totalYoutubeWatchTimeLimitMap.put("setDailyLimit", YoutubeDailyValue);
         totalYoutubeWatchTimeLimitMap.put("setWeeklyLimit", YoutubeWeeklyValue);
        UserData.getInstance().updateYoutubeWatchTime( totalYoutubeWatchTimeLimitMap);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //Setting the title
        alert.setTitle("Streamfox Settings Change notification");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        //Setting the content of the dialog
        alert.setHeaderText("Watchtime Limits Settings have been Changed");
        alert.setContentText("your settings have been saved");
        dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
        alert.showAndWait();

    }

      public void CheckTotalWatchTimeLimit() {

        double tempDailyWatchTime = (double) userData.getWatchTimeSettingsDataMap().get("setDailyWatchTime");
        double tempWeeklyWatchTime = (double) userData.getWatchTimeSettingsDataMap().get("setWeeklyWatchTime");
        String dailyTime = String.valueOf(Math.round(tempDailyWatchTime * 60));
        String weeklyTime = String.valueOf(Math.round(tempWeeklyWatchTime * 60));

        double dailywatchtimelimit = (double) userData.getWatchTimeSettingsDataMap().get("setDailyLimit");
        System.out.print("current Daily Watch time Limit is" + dailywatchtimelimit);

        double WeeklyWatchtimelimit = (double) userData.getWatchTimeSettingsDataMap().get("setWeeklyLimit");
        String dailyLimit = String.valueOf(Math.round(dailywatchtimelimit * 60));
        String WeeklyLimit = String.valueOf(Math.round(WeeklyWatchtimelimit * 60));

        System.out.print("current Daily Watch time Limit is" + dailyLimit);

        System.out.print("current Weekly Watch time Limit is" + WeeklyLimit);

        if (dailyLimit.equals(dailyTime) || WeeklyLimit.equals(weeklyTime)) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sorry Your Watchtime Limit has Been Reached");
            alert.setHeaderText("Please press OK to Confirm and take a break or CANCEL to Continue Watching ");
            alert.setResizable(false);
            alert.setContentText("Are you sure? ");
            alert.showAndWait();

            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent()) {

            } // alert is exited, no button has been pressed.
            else if (result.get() == ButtonType.OK) {
                System.exit(0);
            } //oke button is pressed
            else if (result.get() == ButtonType.CANCEL) {
                alert.close();

            }

        }

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

}
