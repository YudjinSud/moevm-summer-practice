package com.moevm.practice.core.commands;

import com.moevm.practice.core.graph.Graph;
import com.moevm.practice.core.snapshot.GraphHistory;

public class StepForwardCommand extends Command{
    public StepForwardCommand(GraphHistory history) {
        super(history);
    }

    @Override
    public Graph.Snapshot execute() {
        if(snapshotPointer < this.history.getSize() - 1) {
            System.out.println(this.history.getSnapshot(++snapshotPointer));
            System.out.println(snapshotPointer);
            return this.history.getSnapshot(snapshotPointer);
        }
        else {
            System.out.println("Вперед двигаться больше нельзя!");
        }

        return this.history.getSnapshot(this.history.getSize() - 1);
    }

}
