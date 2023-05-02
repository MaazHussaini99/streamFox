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
    requires google.api.services.youtube.v3.rev222;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.json.jackson2;
    requires com.google.api.client;
    requires google.api.client;
    requires jdk.httpserver;
    requires mail;
    requires google.cloud.storage;
    requires okhttp3;
    requires jxbrowser;
    requires jxbrowser.javafx;
    requires json;
    opens com.mycompany.streamfox to javafx.fxml, google.cloud.firestore;
    exports com.mycompany.streamfox;
    
    

}
