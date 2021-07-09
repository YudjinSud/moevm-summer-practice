package com.moevm.practice;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import com.moevm.practice.gui.GuiLoader;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        GuiLoader.run(stage);
    }

    public static void main(String[] args) {
        Application.launch();
    }

}