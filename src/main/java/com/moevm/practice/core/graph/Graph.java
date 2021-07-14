package com.moevm.practice.core.graph;

import com.moevm.practice.core.snapshot.GraphHistory;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.*;

public class Graph<T> implements Serializable {

    public Integer numberOfEdges = 0;

    public Graph(Map<T, ArrayList<T>> graphAdjacencyList,
                 Map<T, ArrayList<T>> graphTransposedAdjacencyList,
                 Map<T, Boolean> used,
                 Map<T, GraphParams> params,
                 ArrayList<T> allVertex, ArrayList<T> order,
                 ArrayList<T> component) {
        this(); // Вызов самого базового конструктора
        this.graphAdjacencyList = graphAdjacencyList;
        this.graphTransposedAdjacencyList = graphTransposedAdjacencyList;
        this.used = used;
        this.params = params;
        this.allVertex = allVertex;
        this.order = order;
        this.component = component;
    }

    public Graph(Graph<T> graph) {
        this(); // Вызов самого базового конструктора
        this.graphAdjacencyList = graph.graphAdjacencyList;
        this.graphTransposedAdjacencyList = graph.graphTransposedAdjacencyList;
        this.used = graph.used;
        this.params = graph.params;
        this.allVertex = graph.allVertex;
        this.order = graph.order;
        this.component = graph.component;
    }

    public GraphHistory history;


    public Graph() {
        this.history = new GraphHistory(this);
    }

    public class Snapshot {
        private final Graph<T> graph;

        public Map<T, ArrayList<T>> getGraphAdjacencyList() {
            return graphAdjacencyList;
        }

        public Map<T, ArrayList<T>> getGraphTransposedAdjacencyList() {
            return graphTransposedAdjacencyList;
        }

        public Map<T, Boolean> getUsed() {
            return used;
        }

        public Map<T, GraphParams> getParams() {
            return params;
        }

        public ArrayList<T> getAllVertex() {
            return allVertex;
        }

        public ArrayList<T> getOrder() {
            return order;
        }

        public ArrayList<T> getComponent() {
            return component;
        }

        public ArrayList<ArrayList<T>> getStronglyConnectedComponents() {
            return stronglyConnectedComponents;
        }

        private final Map<T, ArrayList<T>> graphAdjacencyList;
        private final Map<T, ArrayList<T>> graphTransposedAdjacencyList;
        private final Map<T, Boolean> used;
        private final Map<T, GraphParams> params;
        private final ArrayList<T> allVertex;
        private final ArrayList<T> order;
        private final ArrayList<T> component;


        private ArrayList<ArrayList<T>> stronglyConnectedComponents = new ArrayList<>();

        public GraphHistory.AlgorithmState getState() {
            return state;
        }

        private final GraphHistory.AlgorithmState state;

        public Snapshot(Graph<T> graph, Map<T, ArrayList<T>> graphAdjacencyList,
                        Map<T, ArrayList<T>> graphTransposedAdjacencyList,
                        Map<T, Boolean> used,
                        Map<T, GraphParams> params,
                        ArrayList<T> allVertex,
                        ArrayList<T> order,
                        ArrayList<T> component,
                        ArrayList<ArrayList<T>> stronglyConnectedComponents,
                        GraphHistory.AlgorithmState state) {
            this.graph = graph;
            this.graphAdjacencyList = graphAdjacencyList;
            this.graphTransposedAdjacencyList = graphTransposedAdjacencyList;
            this.used = used;
            this.params = params;
            this.allVertex = allVertex;
            this.order = order;
            this.component = component;
            this.stronglyConnectedComponents = stronglyConnectedComponents;
            this.state = state;
        }

        public void restore() {
            this.graph.setGraphAdjacencyList(this.graphAdjacencyList);
            this.graph.setGraphTransposedAdjacencyList(this.graphTransposedAdjacencyList);
            this.graph.setUsed(this.used);
            this.graph.setParams(this.params);
            this.graph.setAllVertex(this.allVertex);
            this.graph.setOrder(this.order);
            this.graph.setComponent(this.component);
        }
    }

    private Map<T, ArrayList<T>> makeDeepCopyMapArrayList(Map<T, ArrayList<T>> from, HashMap<T, ArrayList<T>> to) {

        for (Map.Entry<T, ArrayList<T>> entry : from.entrySet()) {
            to.put(entry.getKey(),
                    new ArrayList<T>(entry.getValue()));
        }

        return to;
    }

    private Map<T, Boolean> makeDeepCopyMapBoolean(Map<T, Boolean> from, HashMap<T, Boolean> to) {

        for (Map.Entry<T, Boolean> entry : from.entrySet()) {
            to.put(entry.getKey(),
                    (entry.getValue()));
        }

        return to;
    }

    private ArrayList<T> makeDeepCopyArrayList(ArrayList<T> from, ArrayList<T> to) {
        to.addAll(from);
        return to;
    }

    private ArrayList<ArrayList<T>> makeDeepCopyMatrix(ArrayList<ArrayList<T>> from, ArrayList<ArrayList<T>> to) {
        for(int i = 0; i < from.size(); i++) {
            to.add(new ArrayList<>());
        }
        int counter = 0;
        for(ArrayList<T> l : from) {
            for(T x : l) {
                to.get(counter).add(x);
            }
            counter++;
        }
        return to;
    }

    public <T> Snapshot createSnapshot(GraphHistory.AlgorithmState state) {
        HashMap graphAdjacencyListCopy = new HashMap<>();
        makeDeepCopyMapArrayList(this.getGraphAdjacencyList(), graphAdjacencyListCopy);

        HashMap graphTransposedAdjacencyListCopy = new HashMap<>();
        makeDeepCopyMapArrayList(this.getGraphTransposedAdjacencyList(), graphTransposedAdjacencyListCopy);

        HashMap usedCopy = new HashMap<>();
        makeDeepCopyMapBoolean(this.getUsed(), usedCopy);

        ArrayList allVertexCopy = new ArrayList();
        makeDeepCopyArrayList(this.getAllVertex(), allVertexCopy);

        ArrayList orderCopy = new ArrayList();
        makeDeepCopyArrayList(this.getOrder(), orderCopy);

        ArrayList componentCopy = new ArrayList();
        makeDeepCopyArrayList(this.getComponent(), componentCopy);

        ArrayList stronglyConnectedComponentsCopy = new ArrayList();
        makeDeepCopyMatrix(this.getStronglyConnectedComponents(), stronglyConnectedComponentsCopy);

        return new Snapshot(this,
                graphAdjacencyListCopy,
                graphTransposedAdjacencyListCopy,
                usedCopy,
                this.getParams(),
                allVertexCopy,
                orderCopy,
                componentCopy,
                stronglyConnectedComponentsCopy,
                state
        );
    }


    public Map<T, ArrayList<T>> getGraphAdjacencyList() {
        return graphAdjacencyList;
    }

    public Map<T, ArrayList<T>> getGraphTransposedAdjacencyList() {
        return graphTransposedAdjacencyList;
    }

    public Map<T, Boolean> getUsed() {
        return used;
    }

    public Map<T, GraphParams> getParams() {
        return params;
    }

    public ArrayList<T> getAllVertex() {
        return allVertex;
    }

    public ArrayList<T> getOrder() {
        return order;
    }

    public ArrayList<T> getComponent() {
        return component;
    }

    public void setGraphAdjacencyList(Map<T, ArrayList<T>> graphAdjacencyList) {
        this.graphAdjacencyList = graphAdjacencyList;
    }

    public void setGraphTransposedAdjacencyList(Map<T, ArrayList<T>> graphTransposedAdjacencyList) {
        this.graphTransposedAdjacencyList = graphTransposedAdjacencyList;
    }

    public void setUsed(Map<T, Boolean> used) {
        this.used = used;
    }

    public void setParams(Map<T, GraphParams> params) {
        this.params = params;
    }

    public void setAllVertex(ArrayList<T> allVertex) {
        this.allVertex = allVertex;
    }

    public void setOrder(ArrayList<T> order) {
        this.order = order;
    }

    public void setComponent(ArrayList<T> component) {
        this.component = component;
    }

    private Map<T, ArrayList<T>> graphAdjacencyList = new HashMap<>();
    private Map<T, ArrayList<T>> graphTransposedAdjacencyList = new HashMap<>();
    private Map<T, Boolean> used = new HashMap<>();
    private Map<T, GraphParams> params = new HashMap<>();
    private ArrayList<T> allVertex = new ArrayList<>();
    private ArrayList<T> order = new ArrayList<>(); // order in dfs1
    private ArrayList<T> component = new ArrayList<>();

    public ArrayList<ArrayList<T>> getStronglyConnectedComponents() {
        return stronglyConnectedComponents;
    }

    private ArrayList<ArrayList<T>> stronglyConnectedComponents = new ArrayList<>();

    public void addVertex(T vertex) {
        graphAdjacencyList.put(vertex, new ArrayList<T>());
        graphTransposedAdjacencyList.put(vertex, new ArrayList<T>());
    }

    public int getNum(T s) {
        return params.get(s).num;
    }

    public void addEdge(T from, T to) {
        if (!graphAdjacencyList.containsKey(from)) {
            addVertex(from);
            GraphParams p = new GraphParams();
            p.num = allVertex.size();
            params.put(from, p);
            allVertex.add(from);
        }
        if (!graphAdjacencyList.containsKey(to)) {
            addVertex(to);
            GraphParams p = new GraphParams();
            p.num = allVertex.size();
            params.put(to, p);
            allVertex.add(to);
        }
        graphAdjacencyList.get(from).add(to);
        graphTransposedAdjacencyList.get(to).add(from);
        numberOfEdges++;
    }

    public boolean hasVertex(T v) {
        return graphAdjacencyList.containsKey(v);
    }

    public boolean hasEdge(T from, T to) {
        return graphAdjacencyList.get(from).contains(to);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nGraph :: \n");
        for (T v : graphAdjacencyList.keySet()) {
            builder.append(v.toString()).append(": ");
            for (T w : graphAdjacencyList.get(v)) {
                builder.append(w.toString()).append(" ");
            }
            builder.append("\n");
        }
        builder.append("\n");
        return (builder.toString());
    }

    private void dfs1(T vertex) {
        used.put(vertex, true);
        this.history.backUp(GraphHistory.AlgorithmState.FIRST_DFS);
        for (T s : graphAdjacencyList.get(vertex)) {
            if (!used.get(s)) {
                dfs1(s);
            }
        }
        order.add(vertex);
        this.history.backUp(GraphHistory.AlgorithmState.FIRST_DFS);
    }

    private void dfs2(T vertex) {
        used.put(vertex, true);
        component.add(vertex);
        this.history.backUp(GraphHistory.AlgorithmState.SECOND_DFS);
        for (T s : graphTransposedAdjacencyList.get(vertex)) {
            if (!used.get(s)) {
                dfs2(s);
            }
        }
    }

    private void printComponent(int num) {
        System.out.println("\nStrongly connected component number " + num + " ::");
        for (T s : component) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public void mainAlgo() {
        //readGraph();
        used.clear();
        for (T v : allVertex) used.put(v, false);
        for (T z : allVertex) {
            if (!used.get(z)) {
                dfs1(z);
            }
        }
        used.clear();
        for (T v : allVertex) {
            used.put(v, false);
        }
        this.history.backUp(GraphHistory.AlgorithmState.VERTICES_TRANSPOSED);
        int num = 1;
        for (T v : allVertex) {
            T z = order.get(order.size() - getNum(v) - 1);
            if (!used.get(z)) {
                dfs2(z);
                this.stronglyConnectedComponents.add(this.component);
                printComponent(num++);
                this.history.backUp(GraphHistory.AlgorithmState.COMPONENT_ADDED);
                component.clear();
            }
        }
        this.history.backUp(GraphHistory.AlgorithmState.COMPLETED);

    }
}