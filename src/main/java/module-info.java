module com.mycompany.streamfox {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.auth;
    requires firebase.admin;
    requires google.cloud.firestore;
    requires com.google.auth.oauth2;
    requires javafx.web;
    requires google.cloud.core;
    requires com.google.api.apicommon;
    opens com.mycompany.streamfox to javafx.fxml, google.cloud.firestore;
    exports com.mycompany.streamfox;

}
