package com.moevm.practice.core.commands;

import com.moevm.practice.core.snapshot.GraphHistory;

public class RunAlgoCommand extends Command {

    public RunAlgoCommand(GraphHistory history) {
        super(history);
    }

    @Override
    public boolean execute() {
        return false;
    }
}
