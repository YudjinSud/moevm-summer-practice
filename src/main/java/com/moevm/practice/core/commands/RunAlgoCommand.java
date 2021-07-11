package com.moevm.practice.core.commands;

import com.moevm.practice.core.Graph;

public class RunAlgoCommand extends Command {

    RunAlgoCommand(Graph graph) {
        super(graph);
    }

    @Override
    public boolean execute() {
        graph.mainAlgo();
        return false;
    }
}
