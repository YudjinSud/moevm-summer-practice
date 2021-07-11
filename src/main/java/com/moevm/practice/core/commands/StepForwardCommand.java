package com.moevm.practice.core.commands;

import com.moevm.practice.core.Graph;

public class StepForwardCommand extends Command{
    StepForwardCommand(Graph graph) {
        super(graph);
    }

    @Override
    public boolean execute() {
        return false;
    }
}
