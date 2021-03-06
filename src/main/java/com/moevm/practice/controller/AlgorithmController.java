package com.moevm.practice.controller;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import com.moevm.practice.core.commands.Command;
import com.moevm.practice.core.commands.StepBackCommand;
import com.moevm.practice.core.commands.StepForwardCommand;
import com.moevm.practice.core.graph.Graph;
import com.moevm.practice.core.graph.GraphFacade;
import com.moevm.practice.core.snapshot.GraphHistory;
import com.moevm.practice.gui.GraphVisualizer;
import com.moevm.practice.gui.GuiUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AlgorithmController implements Initializable {


    private static final String STEP_FORWARD_CMD_LOG = "Шаг вперед выполнен. ";
    private static final String STEP_FORWARD_END_CMD_LOG = "Двигаться вперед больше нельзя! Алгоритм завершен\n";
    private static final String STEP_BACK_CMD_LOG = "Шаг назад выполнен\n";
    private static final String STEP_BACK_END_CMD_LOG = "Двигаться назад больше нельзя!\n";
    private static final String FILE_READ_CMD_LOG = "Граф прочитан из файла ";
    private static final String PAUSE_CMD_LOG = "Алгоритм приостановлен\n";
    private static final String RESUME_CMD_LOG = "Алгоритм возобновлен\n";
    private static final String PROCESS_TO_FINISH_CMD_LOG = "Запуск просчета алгоритма до конца\n";
    private static final String RESET_CMD_LOG = "Все сброшено! Введите граф заново \n";

    private Graph.Snapshot previousSnapshot;
    private Graph.Snapshot currentSnapshot;

    @FXML
    private Canvas previousStateCanvas;

    @FXML
    private Canvas currentStateCanvas;

    @FXML
    private TextArea mainLog;

    private String mainLogText = "";

    @FXML
    private VBox window;

    @FXML
    private Canvas canvas;

    @FXML
    private Button graphFromFileInputButton;

    @FXML
    private Button graphFromKeyboardInputButton;

    @FXML
    private Button runAlgoButton;

    @FXML
    private Button resetGraphButton;

    @FXML
    private Button saveGraphButton;

    @FXML
    private Button stepForwardButton;

    @FXML
    private Button stepBackButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resumeButton;

    @FXML
    private Button processToFinishButton;

    private GraphFacade graphFacade;

    private BufferedReader graphReader;

    private StepForwardCommand cmdForward;

    private StepBackCommand cmdBack;

    private GraphVisualizer previousStateGraphVisualizer;
    private GraphVisualizer currentStateGraphVisualizer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GuiUtils.setBorderCanvas(previousStateCanvas);
        GuiUtils.setBorderCanvas(currentStateCanvas);
        setButtonsDisabledState(true);
    }


    private void updateGraphsCanvas() {
        GuiUtils.clearCanvas(previousStateCanvas);
        GuiUtils.clearCanvas(currentStateCanvas);
        if (previousSnapshot != null) {
            GuiUtils.setTextOnCanvas(previousSnapshot.toString(), previousStateCanvas);
        }
        if (currentSnapshot != null) {
            GuiUtils.setTextOnCanvas(currentSnapshot.toString(), currentStateCanvas);
        }
    }


    private <T> String findNewUsedVertices() {
        StringBuilder res = new StringBuilder("Посещены вершины: ");
        Map newUsed = currentSnapshot.getUsed();
        Map prevUsed = previousSnapshot.getUsed();
        for (Object n : currentSnapshot.getUsed().keySet()) {
            if (!prevUsed.get(n).equals(newUsed.get(n))) {
                res.append(n.toString()).append(" ");
            }
        }

        return res.toString();
    }

    private <T> String getNewComponentAdded() {
        StringBuilder res = new StringBuilder("");
        ArrayList component = currentSnapshot.getComponent();
        for (Object s : component) {
            res.append(s.toString()).append(" ");
        }
        return res.toString();
    }


    @FXML
    private void stepForward() {
        previousSnapshot = currentSnapshot;
        currentSnapshot = cmdForward.execute();
        String thisStepUpdate = "";
        if (currentSnapshot.getState() == GraphHistory.AlgorithmState.FIRST_DFS) {
            thisStepUpdate = "Первый обход в глубину. " + findNewUsedVertices();
        } else if (currentSnapshot.getState() == GraphHistory.AlgorithmState.VERTICES_TRANSPOSED) {
            thisStepUpdate = "Ребра перевернуты.";
        } else if (currentSnapshot.getState() == GraphHistory.AlgorithmState.SECOND_DFS) {
            thisStepUpdate = "Второй обход в глубину. " + findNewUsedVertices();
        } else if (currentSnapshot.getState() == GraphHistory.AlgorithmState.COMPONENT_ADDED) {
            thisStepUpdate = "Найдена компонента связности: " + getNewComponentAdded();
        }
        mainLogText = GuiUtils.appendTextToLog(mainLogText, STEP_FORWARD_CMD_LOG + thisStepUpdate, this.mainLog);
        currentStateGraphVisualizer.drawGraph(currentSnapshot);
        previousStateGraphVisualizer.drawGraph(previousSnapshot);
        this.stepBackButton.setDisable(false);
        if (currentSnapshot.equals(graphFacade.getHistory().getSnapshot(graphFacade.getHistory().getSize() - 1))) {
            mainLogText = GuiUtils.appendTextToLog(mainLogText, STEP_FORWARD_END_CMD_LOG, this.mainLog);
            this.stepForwardButton.setDisable(true);
        }

        //  updateGraphsCanvas();
    }

    @FXML
    private void stepBack() {
        if (currentSnapshot.getState() == GraphHistory.AlgorithmState.COMPONENT_ADDED) {
            previousStateGraphVisualizer.clearConnectedComponent(previousSnapshot);
            currentStateGraphVisualizer.clearConnectedComponent(currentSnapshot);
        }
        currentSnapshot = previousSnapshot;
        previousSnapshot = cmdBack.execute();
        mainLogText = GuiUtils.appendTextToLog(mainLogText, STEP_BACK_CMD_LOG, this.mainLog);
        previousStateGraphVisualizer.drawGraph(previousSnapshot);
        currentStateGraphVisualizer.drawGraph(currentSnapshot);
        this.stepForwardButton.setDisable(false);
        if (previousSnapshot.equals(graphFacade.getHistory().getSnapshot(0))) {
            mainLogText = GuiUtils.appendTextToLog(mainLogText, STEP_BACK_END_CMD_LOG, this.mainLog);
            this.stepBackButton.setDisable(true);
        }
        //updateGraphsCanvas();
    }

    @FXML
    private void pause() {
        mainLogText = GuiUtils.appendTextToLog(mainLogText, PAUSE_CMD_LOG, this.mainLog);
        timeline.pause();
    }

    @FXML
    private void resume() {
        mainLogText = GuiUtils.appendTextToLog(mainLogText, RESUME_CMD_LOG, this.mainLog);
        timeline.play();
    }

    @FXML
    private void inputGraphFromFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Text files", "*.txt"));
//        Stage stage = (Stage) window.getScene().getWindow();
//        File selectedFile = fileChooser.showOpenDialog(stage);
        File selectedFile = new File("./examples/graph_1.txt");
        graphReader = new BufferedReader(new FileReader(selectedFile));
        initGraph();
        setButtonsDisabledState(false);
        mainLogText = GuiUtils.appendTextToLog(mainLogText,
                FILE_READ_CMD_LOG + selectedFile.getName() + "\n",
                this.mainLog);
    }

    private void initGraph() {
        graphFacade = new GraphFacade(graphReader);
        graphFacade.runMainAlgo();
        cmdForward = new StepForwardCommand(graphFacade.getHistory());
        cmdBack = new StepBackCommand(graphFacade.getHistory());
        Command.snapshotPointer = 0;
        previousSnapshot = graphFacade.getHistory().getSnapshot(0);
        currentSnapshot = previousSnapshot;
        previousStateGraphVisualizer = new GraphVisualizer(previousStateCanvas, previousSnapshot);
        currentStateGraphVisualizer = new GraphVisualizer(currentStateCanvas, currentSnapshot);
        previousStateGraphVisualizer.setGuiVerticesList(currentStateGraphVisualizer.getGuiVerticesList());
        previousStateGraphVisualizer.drawGraph(previousSnapshot);
        currentStateGraphVisualizer.drawGraph(currentSnapshot);
        // updateGraphsCanvas();
    }


    private String graphBufferedReaderString = "";

    private void appendGraphBufferedReaderString(String str) {
        if (!str.equals("")) {
            graphBufferedReaderString += str + "\n";
        }
    }

    @FXML
    private void inputGraphFromKeyboard() throws IOException {
        TextInputDialog dialog = new TextInputDialog("Введите граф");

        dialog.setTitle("Ввод графа с клавиатуры");
        dialog.setHeaderText("Введите количество ребер графа:");
        dialog.setContentText("Количество ребер:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(input -> {
            AtomicInteger cnt = new AtomicInteger(Integer.parseInt(input));
            AtomicInteger counter = new AtomicInteger();
            while (cnt.get() > 0) {
                TextInputDialog edgeDialog = new TextInputDialog("Введите ребро");

                edgeDialog.setTitle("Ввод графа с клавиатуры");
                edgeDialog.setHeaderText("Введите ребро графа № " + counter + ":");
                edgeDialog.setContentText("Ввод:");
                Optional<String> edgeResult = edgeDialog.showAndWait();
                edgeResult.ifPresent(edge -> {
                    cnt.getAndDecrement();
                    System.out.println(edge);
                    this.appendGraphBufferedReaderString(edge);
                    counter.getAndIncrement();
                });
            }
        });
        Reader graphInputString = new StringReader(graphBufferedReaderString);
        graphReader = new BufferedReader(graphInputString);
        initGraph();
        setButtonsDisabledState(false);
    }

    @FXML
    private void resetGraph() throws IOException {
        this.graphReader = null;
        this.graphFacade = null;
        mainLogText = "";
        mainLogText = GuiUtils.appendTextToLog(mainLogText, RESET_CMD_LOG, mainLog);
        setButtonsDisabledState(true);
        GuiUtils.clearCanvas(previousStateCanvas);
        GuiUtils.clearCanvas(currentStateCanvas);
        previousStateGraphVisualizer = null;
        currentStateGraphVisualizer = null;
        currentSnapshot = null;
        previousSnapshot = null;
    }

    private void setButtonsDisabledState(Boolean state) {
        resetGraphButton.setDisable(state);
        saveGraphButton.setDisable(state);
        stepForwardButton.setDisable(state);
        stepBackButton.setDisable(state);
        pauseButton.setDisable(state);
        resumeButton.setDisable(state);
        processToFinishButton.setDisable(state);
        runAlgoButton.setDisable(state);
    }

    @FXML
    private void saveGraph() throws IOException {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        Stage stage = (Stage) window.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveTextToFile(graphBufferedReaderString, file);
        }
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private AlgorithmController cntrl = this;

    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            cntrl.stepForward();
        }
    }));

    @FXML
    private void processToFinish() {
        timeline.setCycleCount(this.graphFacade.getHistory().getSize() - 1 - Command.snapshotPointer);
        timeline.play();
        mainLogText = GuiUtils.appendTextToLog(mainLogText, PROCESS_TO_FINISH_CMD_LOG, this.mainLog);
    }

}