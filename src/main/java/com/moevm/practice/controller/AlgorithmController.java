package com.moevm.practice.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.moevm.practice.core.graph.Graph;
import com.moevm.practice.core.graph.GraphFacade;
import com.moevm.practice.gui.GuiUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class AlgorithmController implements Initializable {
    @FXML
    private Canvas previousStateCanvas;

    @FXML
    private Canvas currentStateCanvas;

    @FXML
    private TextArea mainLog;

    @FXML private VBox window;

    @FXML
    private Canvas canvas;

    @FXML
    private Button graphFromFileInputButton;

    private Graph graph;

    private BufferedReader graphReader;

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
    private void inputGraphFromFile() throws IOException {
        //  FileChooser fileChooser = new FileChooser();
        //  fileChooser.getExtensionFilters().addAll(
        //          new FileChooser.ExtensionFilter("Text files", "*.txt"));
        //  Stage stage = (Stage) window.getScene().getWindow();
        //  File selectedFile = fileChooser.showOpenDialog(stage);
        File selectedFile = new File("./examples/graph_1.txt");
        graphReader = new BufferedReader(new FileReader(selectedFile));
        initGraph();
        System.out.println(selectedFile.getName());
    }

    private void initGraph() {
        graph = new Graph();
        GraphFacade graphFacade = new GraphFacade(graph, graphReader);
        graph.mainAlgo();
        int ab = 3;
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


    @FXML
    private void processToFinish() {

    }

}