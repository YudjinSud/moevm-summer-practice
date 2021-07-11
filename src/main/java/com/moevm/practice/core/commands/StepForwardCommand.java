package com.moevm.practice.core.commands;

import com.moevm.practice.core.snapshot.GraphHistory;

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
