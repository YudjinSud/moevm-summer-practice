package com.moevm.practice.core.snapshot;

import com.moevm.practice.core.commands.Command;
import com.moevm.practice.core.commands.StepForwardCommand;
import com.moevm.practice.core.graph.Graph;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Deque;

public class GraphHistory {
    private ArrayList<Graph.Snapshot> snapshots;

    private Graph graph;

    public GraphHistory(Graph graph) {
        this.snapshots = new ArrayList<>();
        this.graph = graph;
    }

    public void backUp() {
        this.snapshots.add(this.graph.createSnapshot());
    }

    public Graph.Snapshot getSnapshot(int index) {
        return this.snapshots.get(index);
    }

}
