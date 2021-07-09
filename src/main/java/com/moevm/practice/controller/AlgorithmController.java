package com.moevm.practice.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.moevm.practice.gui.GuiUtils;
import com.moevm.practice.gui.GuiLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;

public class AlgorithmController implements Initializable {

    @FXML
    private Canvas previousStateCanvas;

    @FXML
    private Canvas currentStateCanvas;

    @FXML
    private TextArea mainLog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GuiUtils.setBorderCanvas(previousStateCanvas);
        GuiUtils.setBorderCanvas(currentStateCanvas);
//        mainLog.getParent().requestFocus();
    }

    @FXML
    private void stepForward() {

    }

    @FXML
    private void stepBack() {

    }

    @FXML
    private void pause() {

    }

    @FXML
    private void resume() {

    }

    @FXML
    private void halt() throws IOException {
        /* TODO(Eugene):
         *  Контроллер не должен напрямую взаимодействовать с gui, он знает только про существование модели.
         *  Отображение же необходимо сделать на базе паттерна наблюдатель, где publisher - модель, рассылающая
         *  свое состояние всем заинтересованным сущностям, т.е. gui.
         *  Логгер тоже будет подписан на изменения модели.
         *  Контроллер взаимодействует с моделью через Фасад, этот же фасад рассылает обновления.
         */
        GuiLoader.setRoot("mainMenu");
    }

    @FXML
    private void processToFinish() {

    }

}