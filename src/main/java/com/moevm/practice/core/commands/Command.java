package com.moevm.practice.core.commands;

import com.moevm.practice.core.snapshot.GraphHistory;

public abstract class Command {
    public GraphHistory history;

    public static int snapshotPointer = 0;

    public Command(GraphHistory history) {
        this.history = history;
    }

    public abstract boolean execute();
}
