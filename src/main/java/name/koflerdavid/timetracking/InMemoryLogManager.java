package name.koflerdavid.timetracking;

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
    public void stopTask(final RunningTask currentTask, final Instant endOfTask) {
        final Duration duration = Duration.between(currentTask.getBeginning(), endOfTask);
        logs.add(new DefaultTaskLog(currentTask.getTask().getName(), currentTask.getBeginning(), duration));
    }
}
