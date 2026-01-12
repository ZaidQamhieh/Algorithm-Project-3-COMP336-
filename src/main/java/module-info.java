module pack.algo {
    requires javafx.controls;
    requires javafx.fxml;


    opens pack.algo to javafx.fxml;
    exports pack.algo;
}