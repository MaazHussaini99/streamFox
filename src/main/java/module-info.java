module com.mycompany.streamfox {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.auth;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires javafx.web;
    requires com.google.api.api-common;
    
    opens com.mycompany.streamfox to javafx.fxml, com.google.api.api-common;
   
    exports com.mycompany.streamfox;
    exports databasepackage;
    
    
    
}
