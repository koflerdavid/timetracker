package name.koflerdavid.timetracking;

import java.time.Duration;
import java.time.Instant;


public interface LogStore {
    void logTask(String taskName, Instant beginning, Duration duration);
}
