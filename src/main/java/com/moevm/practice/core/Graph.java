package com.moevm.practice.core;

import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    private final int MAX_VERTEX = 100;
    public Integer numberOfEdges = 0;
    ArrayList<ArrayList<Vertex>> graph = new ArrayList<>(MAX_VERTEX); // graph
    ArrayList<ArrayList<Vertex>> graphT = new ArrayList<>(MAX_VERTEX); // Transpose graph
    ArrayList<Vertex> order = new ArrayList<>(); // order in dfs1
    ArrayList<Vertex> component = new ArrayList<>();
    ArrayList<Vertex> allVertex = new ArrayList<>();
    ArrayList<Boolean> usedVertex = new ArrayList<>(MAX_VERTEX);

    void setupGraph(){
        for(int i = 0; i < MAX_VERTEX; i++){
            graph.add(new ArrayList<Vertex>());
            graphT.add(new ArrayList<Vertex>());
        }
    }

    public boolean checkEdge(Vertex v1, Vertex v2) {
        for (Vertex v : graph.get(v1.toInt)) {
            if (v.toInt == v2.toInt) {
                return true;
            }
        }
        return false;
    }

    void addVertex(Vertex v){
        //TODO: if v is unique, but we need map[vertex, int]
        allVertex.add(v);
    }

    public void readGraph() {
        numberOfEdges = new Scanner(System.in).nextInt();
        for (int i = 0; i < numberOfEdges; i++) {
            Vertex v1 = readVertex();
            Vertex v2 = readVertex();
            addEdge(v1, v2);
            addVertex(v1);
            addVertex(v2);
        }
    }

    public void addEdge(Vertex v1, Vertex v2) {
        // TODO: map[vertex, int] to connect int to vertex
        numberOfEdges++;
        graph.get(v1.toInt).add(v2);
        graphT.get(v2.toInt).add(v1);

    }

    Vertex readVertex() {
        Vertex v = new Vertex();
        //TODO: template Vertex
        int num = new Scanner(System.in).nextInt();
        v.toInt = num;
        return v;
    }

    void dfs1(Vertex vertex) {
        usedVertex.set(vertex.toInt, true);
        for (Vertex v : graph.get(vertex.toInt)) {
            if (!usedVertex.get(v.toInt)) {
                dfs1(v);
            }
        }
        order.add(vertex);
    }

    void dfs2(Vertex vertex) {
        usedVertex.set(vertex.toInt, true);
        component.add(vertex);
        for (Vertex v : graphT.get(vertex.toInt)) {
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

    void printComponent() {
        for (Vertex ver : component) {
            ver.printVertex();
        }
    }

    void mainAlgo() {
        setupGraph();
        readGraph();
        eraseUsed();
        for (Vertex v : allVertex) {
            Vertex z = order.get(order.size() - v.toInt - 1);
            if (!usedVertex.get(z.toInt)) {
                dfs1(z);
            }
        }
        eraseUsed();
        for (Vertex v : allVertex) {
            Vertex z = order.get(order.size() - v.toInt - 1);
            if (!usedVertex.get(z.toInt)) {
                dfs2(z);
                printComponent();
                component.clear();
            }
        }
    }

}