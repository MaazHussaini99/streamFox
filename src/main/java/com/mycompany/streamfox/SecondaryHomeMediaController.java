package com.mycompany.streamfox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Statement;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SecondaryHomeMediaController {

    public Connection conn = null;
    @FXML
    private Button getFIleExtension;

    @FXML
    private TextField showVideoData;

    @FXML
    private TextField TitleField;

    @FXML
    private ListView myListView;

    public static String FileData;

    public static ObservableList<String> Showitems;

    @FXML
    private void selectVideoFile(ActionEvent event) {
        System.out.println("Thread name" + Thread.currentThread().getName());
        String Name = TitleField.getText();
        if (Name == "") {
            try {
                throw new Exception("Please fillout all textFIeld before selecting the file");

            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(SecondaryHomeMediaController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Input parameters not filled");
                alert.setHeaderText("Please fill all text fields before adding a file ");
                alert.showAndWait();

            }

        } else if (Name != "") {
            File file = null;
            Stage stage = (Stage) getFIleExtension.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("Video files", "*.mp4", "*.3gp", "*.wmv", "*.mkv"); // tested AVI apparently java doesn't like it
            fileChooser.getExtensionFilters().add(fileExtension);
            file = fileChooser.showOpenDialog(stage);
            String FileName = file.toString();

            showVideoData.setText(FileName);
            FileData = convertAllDatatoJsonString(Name, FileName);

        }
    }

    private String convertAllDatatoJsonString(String Name, String FileName) {
        HomeMediaVideoFile videodata = new HomeMediaVideoFile(Name, FileName);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String jsonString = gson.toJson(videodata);
        Showitems = myListView.getItems();
        Showitems.add(jsonString);
        return jsonString;
    }

    private HomeMediaVideoFile convertVideoDataIntoJSONObj(String jsonData) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        HomeMediaVideoFile addedVideo = gson.fromJson(jsonData, HomeMediaVideoFile.class);
        return addedVideo;

    }

    private void updateDatabase() {
        System.out.println("Thread name" + Thread.currentThread().getName());
        try {
            HomeMediaVideoFile DatabeingAdded = null;

            DatabeingAdded = convertVideoDataIntoJSONObj(FileData);

            String databaseURL = "";
            Connection conn = null;

            try {
                databaseURL = "jdbc:ucanaccess://.//VideoPlayerDatabase.accdb";

                conn = DriverManager.getConnection(databaseURL);

                String sql = "INSERT INTO VideoLibary(Name,FileName) VALUES (?,?)";
                //clearDatabase();
                String Name = DatabeingAdded.getVideoName();

                String FileName = DatabeingAdded.getFileName();

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, Name);
                preparedStatement.setString(2, FileName);
                int row = preparedStatement.executeUpdate();
                if (row > 0) {
                    System.out.println("Row inserted");
                }
                Platform.runLater(() -> {

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Database Insertion Operation Notification");
                    alert.setHeaderText("Your file has Sucessfully been inserted into the Database ");
                    alert.showAndWait();

                });
            } catch (SQLException ex) {
                Platform.runLater(() -> { // where I think the error might be 

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Database Insertion error");
                    alert.setHeaderText("Please check if the file you are trying to add is not a duplicate and/or is on your Harddrive ");
                    alert.showAndWait();

                });
                java.util.logging.Logger.getLogger(SecondaryHomeMediaController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Platform.runLater(() -> {

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Input parameters not filled");
                alert.setHeaderText("Please fill all text fields before adding a file ");
                alert.showAndWait();

            });

        }
    }

    @FXML
    private void clearAllVideoData(ActionEvent event) {
        Showitems.clear();
        TitleField.clear();
        showVideoData.clear();
    }

    @FXML
    private void addFiletoDB(ActionEvent event) {

        Thread updateDatabaseThread = new Thread(() -> {
            updateDatabase();
        });
        updateDatabaseThread.start();

    }

    private void clearDB() {
        System.out.println("Thread name" + Thread.currentThread().getName());
        String databaseURL = "";
        Connection conn = null;
        System.out.println("The Database has been cleared so no duplicate entries will be stored within the database");
        try {
            databaseURL = "jdbc:ucanaccess://.//VideoPlayerDatabase.accdb";
            conn = DriverManager.getConnection(databaseURL);
            String sql = "DELETE FROM VideoLibary";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            int rowsDeleted = preparedStatement.executeUpdate();

            Platform.runLater(() -> {

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Database clear Operation Notification");
                alert.setHeaderText("The Database has been Successfully cleared ");
                alert.showAndWait();

            });
            System.out.println("Database Cleared");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(SecondaryHomeMediaController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void clearDatabase(ActionEvent event) throws SQLException {

        Thread clearDatabaseThread = new Thread(() -> {
            clearDB();
        });
        clearDatabaseThread.start();

    }

    @FXML
    private void gobacktoMainDisplay(ActionEvent event) throws IOException {
        App.setRoot("MediaPlayerPrimary");
    }
}
