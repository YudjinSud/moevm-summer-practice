package com.moevm.practice.core;

import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    public Integer numberOfEdges = 0;
    ArrayList<ArrayList<Vertex>> graph = new ArrayList<>(100); // graph
    ArrayList<ArrayList<Vertex>> graphT = new ArrayList<>(100); // Transpose graph
    ArrayList<Vertex> order = new ArrayList<>(); // order in dfs1
    ArrayList<Vertex> component = new ArrayList<>();
    ArrayList<Vertex> allVertex = new ArrayList<>();
    ArrayList<Boolean> usedVertex = new ArrayList<>();

    public void readGraph() {
        numberOfEdges = new Scanner(System.in).nextInt();
        for (int i = 0; i < numberOfEdges; i++) {
            Vertex v1 = readVertex();
            Vertex v2 = readVertex();
            addEdge(v1, v2);
        }
    }

    public void addEdge(Vertex v1, Vertex v2) {
        // TODO: map[vertex, int] to connect int to vertex
        numberOfEdges++;
        ArrayList<Vertex> ar = new ArrayList<>();
        ar.add(v1);
        graph.add(ar);
        ar.clear();
        ar.add(v2);
        graphT.add(ar);
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