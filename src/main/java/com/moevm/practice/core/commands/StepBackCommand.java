package com.moevm.practice.core.commands;

import com.moevm.practice.core.graph.Graph;
import com.moevm.practice.core.snapshot.GraphHistory;

public class StepBackCommand extends Command {
    public StepBackCommand(GraphHistory history) {
        super(history);
    }

    @Override
    public Graph.Snapshot execute() {
        if(snapshotPointer > 0) {
            System.out.println(this.history.getSnapshot(--snapshotPointer));
            System.out.println(snapshotPointer);
            return this.history.getSnapshot(snapshotPointer);

        }
        else {
            System.out.println("Назад двигаться больше нельзя!");
        }

        return this.history.getSnapshot(0);
    }

}