module com.moevm.practice {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.moevm.practice to javafx.fxml;
    exports com.moevm.practice;
}
