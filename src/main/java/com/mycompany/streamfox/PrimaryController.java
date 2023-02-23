package com.mycompany.streamfox;
package com.mycompany.streamfox;

import com.google.firebase.auth.FirebaseAuth;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PrimaryController {
    
    private FirebaseAuth firebaseAuth;
    
    @FXML
    public VBox displayVbox;
    @FXML
    public SplitPane PrimaryTopPane;
    @FXML
    public AnchorPane menuAnchorPane;
    @FXML
    public AnchorPane displayAnchorPane;

    /**
     * exits the application
     */
    @FXML
    private void exit(){
        System.out.println("exit");
        System.exit(0);
    }

    /**
     * switches the application to and from fullscreen mode
     */
    @FXML
    private void fullscreen(){
        firebaseAuth  = FirebaseAuth.getInstance();
        //toggles fullscreen on and off
        //TODO: make the interface more dynamic (hard)
        System.out.println("fullscreen");
        App.fullscreen();
    }
}
