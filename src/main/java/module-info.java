module com.mycompany.streamfox {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.auth;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires javafx.web;
    requires com.google.api.apicommon;
    requires google.cloud.firestore;
    requires google.cloud.core;
    opens com.mycompany.streamfox to javafx.fxml, google.cloud.firestore;
   
    exports com.mycompany.streamfox;
    
}
