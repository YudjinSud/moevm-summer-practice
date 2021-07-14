package com.moevm.practice.gui;

import com.moevm.practice.core.graph.Graph;
import com.moevm.practice.core.snapshot.GraphHistory;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.lang.reflect.Array;
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

        private int connectedComponentId = -1;

        private Boolean inConnectedComponent = false;

        private Color connectedComponentColor;

        private void drawVertexValue() {
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

            double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * ARROW_HEAD_SIZE;
            double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * ARROW_HEAD_SIZE;

            double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * ARROW_HEAD_SIZE;
            double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * ARROW_HEAD_SIZE;

            graphicsContext.moveTo(this.x, this.y);
            int tox, toy;
            if (angle < Math.PI / 2) {
                tox = to.x - SHAPE_RADIUS / 2;
                toy = to.y - SHAPE_RADIUS / 2;
            } else {
                tox = to.x + SHAPE_RADIUS / 2;
                toy = to.y + SHAPE_RADIUS / 2;
            }

            graphicsContext.lineTo(tox, toy);


            graphicsContext.lineTo(x1 + tox, y1 + toy);
            graphicsContext.lineTo(x2 + tox, y2 + toy);
            graphicsContext.lineTo(tox, toy);
            graphicsContext.lineTo(this.x, this.y);
            graphicsContext.stroke();
        }


        private void draw() {
            graphicsContext.strokeOval(x - SHAPE_RADIUS, y - SHAPE_RADIUS, SHAPE_RADIUS * 2, SHAPE_RADIUS * 2);
            if (this.used && !this.inConnectedComponent) {
                graphicsContext.setFill(Color.GREEN);
                graphicsContext.fillOval(x - SHAPE_RADIUS, y - SHAPE_RADIUS, SHAPE_RADIUS * 2, SHAPE_RADIUS * 2);
            } else if (this.inConnectedComponent) {
                graphicsContext.setFill(this.connectedComponentColor);
                graphicsContext.fillOval(x - SHAPE_RADIUS, y - SHAPE_RADIUS, SHAPE_RADIUS * 2, SHAPE_RADIUS * 2);
            }
            this.drawVertexValue();
        }


        private boolean isIntersected(ArrayList<GuiVertexWrapper> guiVerticesList) {
            for(GuiVertexWrapper v : guiVerticesList) {
                int xDist  = Math.abs(this.x - v.x);
                int yDist  = Math.abs(this.y - v.y);
                if(Math.sqrt( xDist * xDist + yDist *yDist) < (SHAPE_RADIUS * 2)) {
                    return true;
                }
            }
            return false;
        }


        public GuiVertexWrapper(int id, String value, double canvasHeight, double canvasWidth, ArrayList<GuiVertexWrapper> guiVerticesList) {
            this.x = getRandomNumberRange(SHAPE_RADIUS, (int) canvasWidth - SHAPE_RADIUS);
            this.y = getRandomNumberRange(SHAPE_RADIUS, (int) canvasHeight - SHAPE_RADIUS);
            while(this.isIntersected(guiVerticesList)) {
                this.x = getRandomNumberRange(SHAPE_RADIUS, (int) canvasWidth - SHAPE_RADIUS);
                this.y = getRandomNumberRange(SHAPE_RADIUS, (int) canvasHeight - SHAPE_RADIUS);
            }
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
                    graphicsContext.getCanvas().getWidth(),
                    this.guiVerticesList));
        }
    }

    public <T> void clearConnectedComponent(Graph.Snapshot graph) {
        if(graph.getStronglyConnectedComponents().size() == 0) return;
        ArrayList<T> last = (ArrayList<T>) graph.getStronglyConnectedComponents().get(graph.getStronglyConnectedComponents().size() - 1);

        for(T v : last) {
            guiVerticesList.get(graphVerticesMap.get(v.toString())).inConnectedComponent = false;
        }

    }

    private <T> void updateState(Graph.Snapshot updatedGraph) {
        Map<T, Boolean> usedMap = updatedGraph.getUsed();
        for (T v : usedMap.keySet()) {
            guiVerticesList.get(graphVerticesMap.get(v.toString())).used = usedMap.get(v);
//            guiVerticesList.get(graphVerticesMap.get(v.toString())).connectedComponentColor = Color.WHITE;
//            guiVerticesList.get(graphVerticesMap.get(v.toString())).inConnectedComponent = false;
        }

        if(updatedGraph.getState() == GraphHistory.AlgorithmState.COMPONENT_ADDED) {
            ArrayList<ArrayList<T>> scc = updatedGraph.getStronglyConnectedComponents();
            for (ArrayList<T> component : scc) {
                Color componentColor = GuiUtils.makeRandomColor(component.toString());
                for (T v : component) {
                    guiVerticesList.get(graphVerticesMap.get(v.toString())).connectedComponentColor = componentColor;
                    guiVerticesList.get(graphVerticesMap.get(v.toString())).inConnectedComponent = true;
                }
            }
        }


}

    private <T> void drawEdges(Graph.Snapshot graph) {
        Map<T, ArrayList<T>> graphAdjacencyList = new HashMap<>();
        if (!graph.getState().equals(GraphHistory.AlgorithmState.VERTICES_TRANSPOSED)) {
            graphAdjacencyList = graph.getGraphAdjacencyList();
        } else {
            graphAdjacencyList = graph.getGraphTransposedAdjacencyList();
        }

        int counter = 0;
        for (T from : graphAdjacencyList.keySet()) {
            for (T to : graphAdjacencyList.get(from)) {
                GuiVertexWrapper guiFrom = guiVerticesList.get(graphVerticesMap.get(from.toString()));
                GuiVertexWrapper guiTo = guiVerticesList.get(graphVerticesMap.get(to.toString()));
                guiFrom.drawEdge(guiTo);
                counter++;
            }
        }
    }


    public <T> void drawGraph(Graph.Snapshot graph) {
        this.updateState(graph);
        GuiUtils.clearCanvas(graphicsContext.getCanvas());
        for (GuiVertexWrapper v : guiVerticesList) {
            v.draw();
        }
        this.drawEdges(graph);
    }
}

