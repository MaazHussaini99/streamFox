package com.mycompany.streamfox;

import java.io.IOException;
import javafx.fxml.FXML;
import com.google.firebase.auth.FirebaseAuth;

public class PrimaryController {
    private FirebaseAuth firebaseAuth;
    
    @FXML
    private void switchToSecondary() throws IOException {
        
        firebaseAuth  = FirebaseAuth.getInstance();
        System.out.println(firebaseAuth);
        App.setRoot("secondary");
    }
}
