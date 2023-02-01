module com.mycompany.streamfox {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.streamfox to javafx.fxml;
    exports com.mycompany.streamfox;
}
