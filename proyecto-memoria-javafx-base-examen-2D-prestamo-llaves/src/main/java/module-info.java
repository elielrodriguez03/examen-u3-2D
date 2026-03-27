module com.example.prestamollaves {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.prestamollaves.controller to javafx.fxml;
    exports com.example.prestamollaves;
}
