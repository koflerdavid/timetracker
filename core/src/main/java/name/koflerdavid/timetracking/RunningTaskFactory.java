package name.koflerdavid.timetracking;

import java.time.Instant;


public interface RunningTaskFactory {
    RunningTask createRunningTask(final Task task, final Instant beginning);
}
