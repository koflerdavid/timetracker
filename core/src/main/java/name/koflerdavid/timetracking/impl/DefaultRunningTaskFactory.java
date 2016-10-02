package name.koflerdavid.timetracking.impl;

import name.koflerdavid.timetracking.RunningTask;
import name.koflerdavid.timetracking.Task;

import java.time.Instant;


public class DefaultRunningTaskFactory implements name.koflerdavid.timetracking.RunningTaskFactory {
    @Override
    public RunningTask createRunningTask(final Task task, final Instant beginning) {
        return new DefaultRunningTask(task, beginning);
    }
}
