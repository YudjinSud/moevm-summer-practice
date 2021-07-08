package com.moevm.practice.core;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    Integer numberOfEdges = 0;
    ArrayList<ArrayList<Vertex>> g = new ArrayList<>(); // graph
    ArrayList<ArrayList<Vertex>> gr = new ArrayList<>(); // Transpose graph
    ArrayList<Vertex> order = new ArrayList<>(); // order in dfs1
    ArrayList<Vertex> component = new ArrayList<>();
    ArrayList<Vertex> allVertex = new ArrayList<>();
    ArrayList<Boolean> usedVertex = new ArrayList<>();

    void readGraph() {
        numberOfEdges = new Scanner(System.in).nextInt();
        for (int i = 0; i < numberOfEdges; i++) {
            addEdge();
        }
    }

    void addEdge() {
        Vertex v1 = readVertex();
        Vertex v2 = readVertex();
        // TODO: map[vertex, int] to connect int to vertex
        g.get(v1.toInt).add(v2);
        gr.get(v2.toInt).add(v1);
    }

    Vertex readVertex() {
        Vertex v = new Vertex();
        int num = new Scanner(System.in).nextInt();
        //TODO: template Vertex
        v.toInt = num;
        return v;
    }

    void dfs1(Vertex vertex) {
        usedVertex.set(vertex.toInt, true);
        for (Vertex v : g.get(vertex.toInt)) {
            if (!usedVertex.get(v.toInt)) {
                dfs1(v);
            }
        }
        order.add(vertex);
    }

    void dfs2(Vertex vertex) {
        usedVertex.set(vertex.toInt, true);
        component.add(vertex);
        for (Vertex v : gr.get(vertex.toInt)) {
            if (!usedVertex.get(v.toInt)) {
                dfs2(v);
            }
        }
    }

    void eraseUsed() {
        for (int i = 0; i < usedVertex.size(); i++) {
            usedVertex.set(i, false);
        }
    }

    void printComponent(){
        for(Vertex ver : component){
            ver.printVertex();
        }
    }

    void mainAlgo() {
        readGraph();
        eraseUsed();
        for (Vertex v : allVertex) {
            Vertex z = order.get(order.size() - v.toInt - 1);
            if(!usedVertex.get(z.toInt)){
                dfs1(z);
            }
        }
        eraseUsed();
        for (Vertex v : allVertex) {
            Vertex z = order.get(order.size() - v.toInt - 1);
            if(!usedVertex.get(z.toInt)){
                dfs2(z);
                printComponent();
                component.clear();
            }
        }
    }

}