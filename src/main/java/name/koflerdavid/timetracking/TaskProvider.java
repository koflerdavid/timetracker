package name.koflerdavid.timetracking;

import java.time.Instant;


public interface TaskProvider {

    RunningTask startTask(String taskName, Instant date);

    Task getTaskByName(String taskName);
}
