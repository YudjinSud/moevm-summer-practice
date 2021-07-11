package com.moevm.practice.core.commands;

import com.moevm.practice.core.Graph;

public abstract class Command {
    public Graph graph;

    Command(Graph graph) {
        this.graph = graph;
    }

    public abstract boolean execute();
}
