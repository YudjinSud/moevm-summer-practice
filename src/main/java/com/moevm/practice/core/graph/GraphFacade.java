package com.moevm.practice.core.graph;


import com.moevm.practice.core.snapshot.GraphHistory;

import java.io.BufferedReader;
import java.io.IOException;

/***
 * Отвечает за ввод графа и перенаправление сообщений обратно на gui.
 */
public class GraphFacade {

    private Graph graph;

    public GraphHistory getHistory() {
        return graph.history;
    }

    public Graph getGraph() {
        return graph;
    }

    public GraphFacade(BufferedReader graphBR) {
        graph = new Graph<>();
        try {
            StringBuilder sb = new StringBuilder();
            String line = graphBR.readLine();

            while (line != null) {
                if (line.equals("")) {
                   return;
                }
                String[] tmp = line.split(",");
                graph.addEdge(
                        Integer.parseInt(tmp[0]),
                        Integer.parseInt(tmp[1])
                );
                line = graphBR.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runMainAlgo() {
        graph.mainAlgo();
    }

}
