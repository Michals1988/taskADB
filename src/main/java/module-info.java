module com.example.task {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires json.simple;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.task to javafx.fxml;
    exports com.example.task;
}