package com.mycompany.streamfox;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class PrimaryController {

    public VBox PrimaryTopPane;

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
        System.out.println("fullscreen");
        App.fullscreen();
    }
}
