module com.example.square {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.square to javafx.fxml;
    exports com.example.square;
}