package com.moevm.practice.core.graph;


import java.io.BufferedReader;
import java.io.IOException;

/***
 * Отвечает за ввод графа и перенаправление сообщений обратно на gui.
 */
public class GraphFacade {

    public Graph graph;

    public GraphFacade(BufferedReader graphBR) {
        graph = new Graph<>();
        try {
            StringBuilder sb = new StringBuilder();
            String line = graphBR.readLine();

            while (line != null) {
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
//        System.out.println(graph.toString());
//        graph.mainAlgo();
    }

}
