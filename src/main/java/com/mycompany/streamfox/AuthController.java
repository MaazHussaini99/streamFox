/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import static com.mycompany.streamfox.App.height;
import static com.mycompany.streamfox.App.width;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

public class AuthController implements Initializable {

    @FXML
    private Label accQuestionLabel;

    @FXML
    private TextField emailTxtField;

    @FXML
    private ImageView exitImage;

    @FXML
    private Button signInBtn;

    @FXML
    private Label signLabel;

    @FXML
    private PasswordField passwordField;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private DialogPane dialog;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emailTxtField.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        passwordField.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
    }

    @FXML
    void exitCommand(MouseEvent event) {

        System.exit(0);
    }

    @FXML
    void switchTextLabel(MouseEvent event) {
        if (signLabel.getText().contains("Up")) {
            signInBtn.setText("Sign Up");
            signLabel.setText("Sign In");
            accQuestionLabel.setText("Have an account? ");
        } else {
            signInBtn.setText("Sign In");
            signLabel.setText("Sign Up");
            accQuestionLabel.setText("Don't have an account? ");
        }
    }

    @FXML
    void preformActionBtn(ActionEvent event) {
        if (signInBtn.getText().contains("In")) {
            login(event);
        } else if (signInBtn.getText().contains("Up")) {
            signUp(event);
        }
    }

    void login(ActionEvent event) {
        String email = emailTxtField.getText();
        String password = passwordField.getText();
        if (checkRegex(email, password)) {
            try {
                URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBsNMBJU17vpcxX6Nz3PBglq8wWOijTtq0");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);
                String jsonInputString = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"returnSecureToken\":true}";
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());

                    //initialize the user object
                    UserRecord uid;
                    try {
                        uid = firebaseAuth.getUserByEmail(email);
                        System.out.println("Current user UID: " + uid.getUid());
                    } catch (FirebaseAuthException ex) {
                        ex.printStackTrace();
                    }

                    //Add primary screen functionality
                    App.setWidth(640);
                    App.setHeight(480);
                    App.scene = new Scene(loadFXML("primary"), App.width, App.height);
                    
                    App.stage.setScene(App.scene);
                    
                    App.setRoot("primary");
                    
                }
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }
                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();

                loginMalformedURLAlert();

            } catch (IOException e) {
                e.printStackTrace();
                loginIOExceptionAlert();
            } 
        }
    }

    boolean checkRegex(String email, String password) {
        Pattern regEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Pattern regPass = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,16}$", Pattern.CASE_INSENSITIVE);
        Matcher matchPass = regPass.matcher(password);
        Matcher matchEmail = regEmail.matcher(email);
        if (matchPass.find() && matchEmail.find()) {
            return true;
        } else {
            if (!matchEmail.find()) {
                emailMatchRegexAlert();
                return false;
            } else if (!matchPass.find()) {
                passMatchRegexAlert();
                return false;
            }
        }
        return true;
    }

    void signUp(ActionEvent event) {
        String email = emailTxtField.getText();
        String password = passwordField.getText();
        if (checkRegex(email, password)) {
            CreateRequest req = new CreateRequest().setEmail(email).setPassword(password);
            try {
                firebaseAuth.createUser(req);
                //add primary screen functionality
                userCreatedAlert();
            } catch (FirebaseAuthException ex) {
                firebaseAuthExceptionAlert();
                ex.printStackTrace();
            }
        }
    }

    void loginMalformedURLAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("Sign In Failed");
        alert.setHeaderText("Error in user login");
        alert.setContentText("Please try again");
        dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
        alert.showAndWait();
    }

    void loginIOExceptionAlert() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setTitle("Sign In Failed");
                alert.setHeaderText("Error in user login");
                alert.setContentText("Please try again");
                dialog = alert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
                
                
                alert.showAndWait();
    }

    void emailMatchRegexAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("Email Invalid");
        alert.setHeaderText("Make sure your email is correct!");
        alert.setContentText("Should follow standard rules of email id");
        dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
        alert.showAndWait();
    }

    void passMatchRegexAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("Password Invalid");
        alert.setHeaderText("Make sure you have the following in the password!");
        alert.setContentText("-At least 8 characters long \n"
                + "-At least 1 Uppercase \n"
                + "-At least 1 special\n"
                + "-At least 1 digit");

        dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
        alert.showAndWait();
    }

    void userCreatedAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("User Created");
        alert.setHeaderText("User Created Successfully");
        alert.setContentText("Your account has been created,\nyou can now sign in.");
        dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
        alert.showAndWait();
    }

    void firebaseAuthExceptionAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("Sign Up Failed");
        alert.setHeaderText("Error creating user");
        alert.setContentText("Please try again");
        dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("cssAuth.css").toString());
        alert.showAndWait();
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
}
