package com.moevm.practice.gui;

import com.moevm.practice.core.graph.Graph;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GraphVisualizer {
    private class GuiVertexWrapper {
        private final int SHAPE_RADIUS = 25;
        private final int ARROW_HEAD_SIZE = 10;


        public int getRandomNumberRange(int min, int max) {
            Random random = new Random();
            return random.nextInt(max - min) + min;
        }

        public int x;

        public int y;

        public boolean used;

        private String value;

        private int id;

        private void drawValue() {
            graphicsContext.setFont(new Font(Font.getDefault().getName(), 16));
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.setTextAlign(TextAlignment.CENTER);
            graphicsContext.setTextBaseline(VPos.CENTER);

            graphicsContext.fillText(
                    this.value,
                    Math.round(x),
                    Math.round(y)
            );
        }

        //@elek34ka помоги
        private void drawEdge(GuiVertexWrapper to) {
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.setLineWidth(1.0);
            graphicsContext.beginPath();

            double angle = Math.atan2((to.y - this.y), (to.x - this.x)) - Math.PI / 2.0;
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);

            double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * ARROW_HEAD_SIZE ;
            double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * ARROW_HEAD_SIZE ;

            double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * ARROW_HEAD_SIZE ;
            double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * ARROW_HEAD_SIZE ;

            graphicsContext.moveTo(this.x, this.y);
            int tox, toy;
            if(angle < Math.PI / 2) {
                tox = to.x - SHAPE_RADIUS / 2;
                toy = to.y - SHAPE_RADIUS / 2;
            }
            else {
                tox = to.x + SHAPE_RADIUS / 2;
                toy = to.y + SHAPE_RADIUS / 2;
            }

            graphicsContext.lineTo(tox,toy);


            graphicsContext.lineTo(x1 + tox, y1 + toy);
            graphicsContext.lineTo(x2 + tox, y2 + toy);
            graphicsContext.lineTo(tox, toy);
            graphicsContext.lineTo(this.x, this.y);
            graphicsContext.stroke();
        }


        private void draw() {
            graphicsContext.strokeOval(x - SHAPE_RADIUS, y - SHAPE_RADIUS, SHAPE_RADIUS * 2, SHAPE_RADIUS * 2);
            if(this.used) {
                graphicsContext.setFill(Color.GREEN);
                graphicsContext.fillOval(x - SHAPE_RADIUS, y - SHAPE_RADIUS, SHAPE_RADIUS * 2, SHAPE_RADIUS * 2);
            }
            this.drawValue();
        }

        public GuiVertexWrapper(int id, String value, double canvasHeight, double canvasWidth) {
            this.x = getRandomNumberRange(SHAPE_RADIUS, (int) canvasWidth - SHAPE_RADIUS);
            this.y = getRandomNumberRange(SHAPE_RADIUS, (int) canvasHeight - SHAPE_RADIUS);
            this.used = false;
            this.id = id;
            this.value = value;
        }

    }

    private Stage stage;

    private final GraphicsContext graphicsContext;

    public ArrayList<GuiVertexWrapper> getGuiVerticesList() {
        return this.guiVerticesList;
    }


    private ArrayList<GuiVertexWrapper> guiVerticesList;

    private Map<String, Integer> graphVerticesMap; // отоброжание со значения вершины на номер в списке guiVerticesList

    public void setGuiVerticesList(ArrayList<GuiVertexWrapper> guiVerticesList) {
        int absolutelyMagicConstant = 10; // Абсолютно нет идей, чем она определяется. Все вершины на канвасе имеют
        // координату относительно верхнего левого угла, но сами канвасы рисуются относительно stage.
        // эта константа помогает перенести координаты вершин из правого холста в левый
        for (int i = 0; i < this.guiVerticesList.size(); i++) {
            this.guiVerticesList.get(i).x = guiVerticesList.get(i).x - absolutelyMagicConstant;
            this.guiVerticesList.get(i).y = guiVerticesList.get(i).y;
        }
    }

    public GraphVisualizer(Canvas canvas, Graph.Snapshot graph) {
        this.stage = (Stage) canvas.getScene().getWindow();
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.guiVerticesList = new ArrayList<>();
        this.graphVerticesMap = new HashMap<>();
        initGraphPicture(graph);
    }

    public <T> void initGraphPicture(Graph.Snapshot graph) {
        Map<T, ArrayList<T>> graphAdjacencyList = graph.getGraphAdjacencyList();
        int counter = 0;
        for (T v : graphAdjacencyList.keySet()) {
            this.graphVerticesMap.put(v.toString(), counter);
            this.guiVerticesList.add(new GuiVertexWrapper(counter++, v.toString(),
                    graphicsContext.getCanvas().getHeight(),
                    graphicsContext.getCanvas().getWidth()));
        }
    }

    public <T> void updateState(Graph.Snapshot updatedGraph) {
        Map<T, Boolean> usedMap = updatedGraph.getUsed();
        for(T v :usedMap.keySet()) {
            guiVerticesList.get(graphVerticesMap.get(v.toString())).used = usedMap.get(v);
        }

        Map<T, ArrayList<T>> transposedAdjacencyList = updatedGraph.getGraphTransposedAdjacencyList();
        System.out.println();
    }


    public <T> void drawGraph(Graph.Snapshot graph) {
        this.updateState(graph);
        GuiUtils.clearCanvas(graphicsContext.getCanvas());
        for (GuiVertexWrapper v : guiVerticesList) {
            v.draw();
        }

        Map<T, ArrayList<T>> graphAdjacencyList = graph.getGraphAdjacencyList();
        int counter  = 0;
        for (T from : graphAdjacencyList.keySet()) {
            for(T to : graphAdjacencyList.get(from)) {
                GuiVertexWrapper guiFrom = guiVerticesList.get(graphVerticesMap.get(from.toString()));
                GuiVertexWrapper guiTo = guiVerticesList.get(graphVerticesMap.get(to.toString()));
                guiFrom.drawEdge(guiTo);
                counter++;
            }
        }
        System.out.println("Числа нарисованных ребер = " + counter);
    }
}

