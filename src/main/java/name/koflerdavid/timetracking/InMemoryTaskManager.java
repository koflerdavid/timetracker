package name.koflerdavid.timetracking;

import java.time.Instant;


public class InMemoryTaskManager implements TaskProvider, TaskStore {
    @Override
    public Task createTask(final String taskName) {
        return new DefaultTask(taskName);
    }

    @Override
    public RunningTask startTask(final String taskName, final Instant date) {
        Task task = getTaskByName(taskName);
        if (null == task) {
            task = createTask(taskName);
        }

        return new DefaultRunningTask(task, date);
    }

    @Override
    public Task getTaskByName(final String taskName) {
        return null;
    }
}
