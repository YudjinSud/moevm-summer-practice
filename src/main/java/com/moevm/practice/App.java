package com.moevm.practice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.moevm.practice.gui.MainMenuLoader;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        MainMenuLoader.run(stage);
    }

    public static void main(String[] args) {
        Application.launch();
    }

}