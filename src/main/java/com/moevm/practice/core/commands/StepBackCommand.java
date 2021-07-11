package com.moevm.practice.core.commands;

import com.moevm.practice.core.snapshot.GraphHistory;

public class StepBackCommand extends Command {
    public StepBackCommand(GraphHistory history) {
        super(history);
    }

    @Override
    public boolean execute() {
        System.out.println(this.history.getSnapshot(snapshotPointer--));


        return true;
    }

}