package name.koflerdavid.timetracking;

import java.time.Duration;
import java.time.Instant;


public interface TaskLog {
    String getTaskName();

    Duration getDuration();

    Instant getBeginning();
}
