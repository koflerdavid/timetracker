package name.koflerdavid.timetracking;

import java.time.Instant;


public interface RunningTask {
    Task getTask();

    Instant getBeginning();
}
