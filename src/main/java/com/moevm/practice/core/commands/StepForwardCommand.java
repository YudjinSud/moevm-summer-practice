package com.moevm.practice.core.commands;

import com.moevm.practice.core.graph.Graph;
import com.moevm.practice.core.snapshot.GraphHistory;

import java.util.ArrayList;

public class StepForwardCommand extends Command{
    public StepForwardCommand(GraphHistory history) {
        super(history);
    }

    @Override
    public boolean execute() {
        System.out.println(this.history.getSnapshot(snapshotPointer++));

        return true;
    }

}