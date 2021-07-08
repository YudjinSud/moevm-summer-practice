package com.moevm.practice.controller;

import java.io.IOException;

import javafx.fxml.FXML;

import com.moevm.practice.gui.MainMenuLoader;

public class MainMenuController {

    @FXML
    private void runAlgorithm() throws IOException {

        /* TODO(Eugene):
         *  Контроллер не должен напрямую взаимодействовать с gui, он знает только про существование модели.
         *  Отображение же необходимо сделать на базе паттерна наблюдатель, где publisher - модель, рассылающая
         *  свое состояние всем заинтересованным сущностям, т.е. gui.
         *  Логгер тоже будет подписан на изменения модели.
         *  Контроллер взаимодействует с моделью через Фасад, этот же фасад рассылает обновления.
         */
        MainMenuLoader.setRoot("algorithmMenu");
    }

    @FXML
    private void inputGraphFromFile() throws IOException {
    }

    @FXML
    private void inputGraphFromKeyboard() throws IOException {
    }
}
