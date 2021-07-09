package com.moevm.practice.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.moevm.practice.gui.GuiUtils;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

import com.moevm.practice.gui.GuiLoader;

public class MainMenuController implements Initializable {

    @FXML
    private Canvas canvas;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main menu loaded!");
        System.out.println(this.canvas.getHeight());
        GuiUtils.setBorderCanvas(this.canvas);

    }


    @FXML
    private void runAlgorithm() throws IOException {

        /* TODO(Eugene):
         *  Контроллер не должен напрямую взаимодействовать с gui, он знает только про существование модели.
         *  Отображение же необходимо сделать на базе паттерна наблюдатель, где publisher - модель, рассылающая
         *  свое состояние всем заинтересованным сущностям, т.е. gui.
         *  Логгер тоже будет подписан на изменения модели.
         *  Контроллер взаимодействует с моделью через Фасад, этот же фасад рассылает обновления.
         */
        GuiLoader.setRoot("algorithmMenu");
    }

    @FXML
    private void inputGraphFromFile() throws IOException {
    }

    @FXML
    private void inputGraphFromKeyboard() throws IOException {
    }

    @FXML
    private void resetGraph() throws IOException {
    }

    @FXML
    private void saveGraph() throws IOException {
    }
}
