package name.koflerdavid.timetracking.impl;

import name.koflerdavid.timetracking.TaskLog;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;


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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskLog)) return false;
        final TaskLog that = (TaskLog) o;
        return Objects.equals(taskName, that.getTaskName()) &&
                Objects.equals(duration, that.getDuration()) &&
                Objects.equals(beginning, that.getBeginning());
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, duration, beginning);
    }
}
