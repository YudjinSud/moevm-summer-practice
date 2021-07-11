package com.moevm.practice.core;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

import static java.lang.System.*;

public final class Graph<T> {
    private class Params {
        int num = -1;
    }

    public Integer numberOfEdges = 0;
    private Map<T, ArrayList<T>> graph = new HashMap<>();
    private Map<T, ArrayList<T>> graphTransposed = new HashMap<>();
    private Map<T, Boolean> used = new HashMap<>();
    private Map<T, Params> params = new HashMap<>();
    private ArrayList<T> allVertex = new ArrayList<>();
    private ArrayList<T> order = new ArrayList<>(); // order in dfs1
    private ArrayList<T> component = new ArrayList<>();

    public Graph getInstance() {
        return this;
    }

    void addVertex(T vertex) {
        graph.put(vertex, new ArrayList<T>());
        graphTransposed.put(vertex, new ArrayList<T>());
    }

    public int getNum(T s){
        return params.get(s).num;
    }

    public void readGraph() {
        numberOfEdges = new Scanner(in).nextInt();
        for (int i = 0; i < numberOfEdges; i++) {

            // ?????

        }
    }

    public void addEdge(T from, T to) {
        if (!graph.containsKey(from)) {
            addVertex(from);
            Params p = new Params();
            p.num = allVertex.size();
            params.put(from, p);
            allVertex.add(from);
        }
        if (!graph.containsKey(to)) {
            addVertex(to);
            Params p = new Params();
            p.num = allVertex.size();
            params.put(to, p);
            allVertex.add(to);
        }
        graph.get(from).add(to);
        graphTransposed.get(to).add(from);
        numberOfEdges++;
    }

    public boolean hasVertex(T v) {
        return graph.containsKey(v);
    }

    public boolean hasEdge(T from, T to) {
        return graph.get(from).contains(to);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n Graph :: \n");
        for (T v : graph.keySet()) {
            builder.append(v.toString() + ": ");
            for (T w : graph.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }
        builder.append("\n");
        return (builder.toString());
    }

    void dfs1(T vertex) {
        used.put(vertex, true);
        for (T s : graph.get(vertex)) {
            if (!used.get(s)) {
                dfs1(s);
            }
        }
        order.add(vertex);
    }

    void dfs2(T vertex) {
        used.put(vertex, true);
        component.add(vertex);
        for (T s : graphTransposed.get(vertex)) {
            if (!used.get(s)) {
                dfs2(s);
            }
        }
    }

    private void printComponent() {
        for (T s : component) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public void mainAlgo() {
        //readGraph();
        used.clear();
        for(T v : allVertex) used.put(v,false);
        for (T z : allVertex){
            if (!used.get(z)) {
                dfs1(z);
            }
        }
        used.clear();
        for(T v : allVertex) used.put(v,false);
        for (T v : allVertex) {
            T z = order.get(order.size() - params.get(v).num - 1); // ???
            if (!used.get(z)) {
                dfs2(z);
                printComponent();
                component.clear();
            }
        }
    }

}