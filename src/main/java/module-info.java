module com.moevm.practice {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.moevm.practice to javafx.fxml;
    exports com.moevm.practice;
    exports com.moevm.practice.controller;
    opens com.moevm.practice.controller to javafx.fxml;
}
