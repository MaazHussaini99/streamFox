/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AuthController {
    
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
    
    @FXML
    void exitCommand(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void switchTextLabel(MouseEvent event) {
        if(signLabel.getText().contains("Up")){
            signInBtn.setText("Sign Up");
            signLabel.setText("Sign In");
            accQuestionLabel.setText("Have an account? ");
        }else{
            signInBtn.setText("Sign In");
            signLabel.setText("Sign Up");
            accQuestionLabel.setText("Don't have an account? ");
        }
    }
    
    @FXML
    void preformActionBtn(ActionEvent event) {
        if(signInBtn.getText().contains("In")){
            login(event);
        }else{
            signUp(event);
        }
    }
    
    @FXML
    void login(ActionEvent event) {
        String email = emailTxtField.getText();
        String password = passwordField.getText();

        try {
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBsNMBJU17vpcxX6Nz3PBglq8wWOijTtq0 ");
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
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signUp(ActionEvent event) {
        String email = emailTxtField.getText();
        String password = passwordField.getText();
        CreateRequest req = new CreateRequest().setEmail(email).setPassword(password);
        try {
            firebaseAuth.createUser(req);
        } catch (FirebaseAuthException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign Up Failed");
            alert.setHeaderText("Error creating user");
            alert.setContentText("Please try again");
            alert.show();
            ex.printStackTrace();
        }

    }

}
