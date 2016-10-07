package name.koflerdavid.timetracking.memory;

import name.koflerdavid.timetracking.LogManager;
import name.koflerdavid.timetracking.TaskLog;
import name.koflerdavid.timetracking.impl.DefaultTaskLog;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;


public class InMemoryLogManager implements LogManager {
    private final ArrayList<TaskLog> logs = new ArrayList<>();

    @Override
    public Iterable<? extends TaskLog> getLog() {
        return Collections.unmodifiableList(logs);
    }

    @Override
    public void logTask(final String taskName, final Instant beginning, final Duration duration) {
        logs.add(new DefaultTaskLog(taskName, beginning, duration));
    }
}
