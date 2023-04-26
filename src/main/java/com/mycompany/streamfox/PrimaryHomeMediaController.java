package com.mycompany.streamfox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.Track;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PrimaryHomeMediaController  {

    @FXML
    private ToggleButton playorPause;
    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    public Media VideoFile ;
    @FXML
    private Button prevButton, nextButton;
    @FXML
    private Button LoadFiles;
    @FXML
    private Slider volume;
    @FXML
    private Slider VideoDurationBar;
    @FXML
    private Label VideoName;

    public  ArrayList<Media> mediaFiles = new ArrayList();
    private int counter = -1;

    private static String FileData;
    @FXML
    private ListView PlayList;
    @FXML
    private ListView VideoInfo;

    @FXML
    private ImageView imageview;

    void initialize() {
        imageview.setVisible(true);
    }

    @FXML
    private void closeApp(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void showHelpInfo(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User Notifcation");
        alert.setHeaderText("VidroPlayer Info");
        alert.setContentText("This messgae is to inform you how the video player works \n first you press file, add "
                + "\n this takes you to another menu where you can write the name of any video file and add it to the database \n from there you could go back press list everything avaible choose your video file by clicking on the video path and press Load then press play and it should play your video. ");
        alert.showAndWait();

    }
    
        @FXML
    public void handleMouseClick(MouseEvent event) {
        FileData = (String) PlayList.getSelectionModel().getSelectedItem();
    }
    

    @FXML
    private void LoadFromFiles(ActionEvent event) throws MalformedURLException {

        Stage stage = (Stage) playorPause.getScene().getWindow();

  
        
        File file = new File(FileData);

        if (file.isFile() != true) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("FIle Not Found");
            alert.setHeaderText("This File Does not Exist or is in a different directory Please go to the add Video Menu and re add it ");
            alert.showAndWait();
        }
        

        
      VideoFile = new Media(file.toURI().toString());
    
        mediaFiles.add(VideoFile);
        mediaPlayer = new MediaPlayer(VideoFile);
     

        
        try {
            mediaView.getMediaPlayer().dispose();
        } catch (Exception e) {
            System.out.println("media not disposed" + e);
        }
        prevButton.setDisable(false);
        nextButton.setDisable(false);
        ++counter;
      //  mediaView = new MediaView(mediaPlayer);
mediaView.setMediaPlayer(mediaPlayer);
        volume.valueProperty().addListener((Observable observable) -> {
            if (volume.isValueChanging()) {
                System.out.println(volume.getValue());
                mediaView.getMediaPlayer().setVolume(volume.getValue() / 200);
            }
        });

        mediaView.getMediaPlayer().setOnEndOfMedia(() -> {
            playorPause.setSelected(false);
        });
    
    }

    @FXML
    private void playorPausemethod(ActionEvent event) {

        MediaPlayer currentVideoPlaying = mediaView.getMediaPlayer();
       Media currentVideo = mediaView.getMediaPlayer().getMedia();

        if (playorPause.getText().equals("Play")) {
                mediaView.setVisible(true);
            imageview.setVisible(false);
       
            playorPause.setText("Pause");
         
            currentVideoPlaying.play();
            currentVideoPlaying.currentTimeProperty().addListener((ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) -> {
                VideoDurationBar.setValue(newValue.toSeconds());
            });

            VideoDurationBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(VideoDurationBar.getValue()));
                }
            });

            VideoDurationBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(VideoDurationBar.getValue()));
                }
            });
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    javafx.util.Duration total = VideoFile.getDuration();
                    VideoDurationBar.setMax(total.toSeconds());
                }
            });

        }   else if (playorPause.getText().equals("Pause"))  {

            playorPause.setText("Play");
            mediaView.setVisible(false);
            currentVideoPlaying.pause();
    
        
                   
    
            imageview.setVisible(true);
        }
    }



    @FXML
    public void UpdateListData(ActionEvent event) {
        ObservableList<String> Showitems = VideoInfo.getItems();
        ObservableList<String> ShowVideoInfo = PlayList.getItems();
        Thread updateListView = new Thread(() -> {
            System.out.println("Thread name" + Thread.currentThread().getName());
            String databaseURL = "";

            Connection conn = null;
            try {

                databaseURL = "jdbc:ucanaccess://.//VideoPlayerDatabase.accdb";
                conn = DriverManager.getConnection(databaseURL);

                String tableName = "VideoLibary";
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery("select Name from " + tableName);
                while (result.next()) {

                    String NameData = result.getString("Name");
                    System.out.println("Thread name " + Thread.currentThread().getName());
                    Showitems.add(NameData);

                }
                ResultSet result2 = stmt.executeQuery("select FileName from " + tableName);
                while (result2.next()) {

                    String StorageData = result2.getString("FileName");

                    System.out.println("Thread FileName" + Thread.currentThread().getName());
                    ShowVideoInfo.add(StorageData);

                }

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(PrimaryHomeMediaController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        updateListView.start();

    }

    @FXML
    private void prevVideoClicked(ActionEvent event) {
        if (counter == 0) {
            mediaView.getMediaPlayer().seek(Duration.ZERO);
            mediaView.getMediaPlayer().stop();
              playorPause.setText("Play");
        } else {

            mediaView.getMediaPlayer().dispose();

          
mediaView.setMediaPlayer(new MediaPlayer(mediaFiles.get(--counter)));;
             playorPause.setText("Pause");
            volume.valueProperty().addListener((Observable observable) -> {
                if (volume.isValueChanging()) {
                    System.out.println(volume.getValue());
                    mediaView.getMediaPlayer().setVolume(volume.getValue() / 100);
                }
            });

        }

    }

    @FXML
    private void nextVideoClicked(ActionEvent event) {
          playorPause.setText("Play");
       
          if (counter + 1 == mediaFiles.size()) {
            mediaView.getMediaPlayer().stop();
            playorPause.setText("Play");
          } else {
            mediaView.getMediaPlayer().dispose();

          mediaView.setMediaPlayer(new MediaPlayer(mediaFiles.get(++counter)));

      playorPause.setText("Pause");
       
         //   String newTitle = (String) mediaView.getMediaPlayer().getMedia().getMetadata().get("title");
           // VideoName.setText(newTitle);

            mediaView.getMediaPlayer().setOnEndOfMedia(() -> {
                  playorPause.setText("Play");
            });
        }
    }

    @FXML
    private void goToAddVideotoDBWindow(ActionEvent event) throws IOException {
        if ((playorPause.getText().equals("Pause"))) {
            mediaView.getMediaPlayer().stop();
        }
        App.setRoot("MediaPlayerSecondary");

    }

}
