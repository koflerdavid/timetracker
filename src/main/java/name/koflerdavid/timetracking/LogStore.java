package name.koflerdavid.timetracking;

import java.time.Instant;


public interface LogStore {
    void stopTask(RunningTask currentTask, Instant endOfTask);
}
