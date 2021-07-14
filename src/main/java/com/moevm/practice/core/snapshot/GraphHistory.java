package com.moevm.practice.core.snapshot;

import com.moevm.practice.core.graph.Graph;

import java.util.ArrayList;

public class GraphHistory {
    public enum AlgorithmState {
        FIRST_DFS,
        VERTICES_TRANSPOSED,
        SECOND_DFS,
        COMPONENT_ADDED,
        COMPLETED
    }

    private ArrayList<Graph.Snapshot> snapshots;

    private Graph graph;

    public int getSize() {
        return this.snapshots.size();
    }

    public GraphHistory(Graph graph) {
        this.snapshots = new ArrayList<>();
        this.graph = graph;
    }

    public void backUp(AlgorithmState state) {
        this.snapshots.add(this.graph.createSnapshot(state));
    }

    public Graph.Snapshot getSnapshot(int index) {
        return this.snapshots.get(index);
    }

}
