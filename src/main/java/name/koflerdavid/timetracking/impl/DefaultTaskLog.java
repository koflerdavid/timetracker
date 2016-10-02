package name.koflerdavid.timetracking.impl;

import name.koflerdavid.timetracking.TaskLog;

import java.time.Duration;
import java.time.Instant;


public class DefaultTaskLog implements TaskLog {
    private final String taskName;
    private final Duration duration;
    private final Instant beginning;

    public DefaultTaskLog(final String taskName, final Instant beginning, final Duration duration) {
        this.taskName = taskName;
        this.beginning = beginning;
        this.duration = duration;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public Instant getBeginning() {
        return beginning;
    }
}
