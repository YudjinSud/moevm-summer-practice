package com.moevm.practice.core.commands;

import com.moevm.practice.core.snapshot.GraphHistory;

public class StepForwardCommand extends Command{
    public StepForwardCommand(GraphHistory history) {
        super(history);
    }

    @Override
    public boolean execute() {
        if(snapshotPointer < this.history.getSize()) {
            System.out.println(this.history.getSnapshot(snapshotPointer++));
        }
        else {
            System.out.println("Вперед двигаться больше нельзя!");
        }

        return true;
    }

}
