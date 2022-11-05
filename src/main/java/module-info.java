module com.example.sma3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sma3 to javafx.fxml;
    exports com.example.sma3;
}