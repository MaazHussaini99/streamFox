module com.mycompany.streamfox {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.auth;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    
    opens com.mycompany.streamfox to javafx.fxml;
    exports com.mycompany.streamfox;
    
    
}
