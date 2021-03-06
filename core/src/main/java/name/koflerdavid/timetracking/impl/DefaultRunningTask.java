package name.koflerdavid.timetracking.impl;

import name.koflerdavid.timetracking.RunningTask;
import name.koflerdavid.timetracking.Task;

import java.time.Instant;


public class DefaultRunningTask implements RunningTask {
    private final Task task;
    private final Instant beginning;


    public DefaultRunningTask(final Task task, final Instant beginning) {
        this.task = task;
        this.beginning = beginning;
    }

    public Task getTask() {
        return task;
    }

    @Override
    public Instant getBeginning() {
        return beginning;
    }
}
