package name.koflerdavid.timetracking;

import name.koflerdavid.timetracking.impl.DefaultTaskLog;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;


public class InMemoryLogManager implements LogProvider, LogStore {
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
