package name.koflerdavid.timetracking;

import java.time.Instant;
import java.util.TreeMap;


public class InMemoryTaskManager implements TaskProvider, TaskStore {
    private final TreeMap<String, Task> treeMap = new TreeMap<>();

    @Override
    public Task createTask(final String taskName) {
        return treeMap.computeIfAbsent(taskName, (name) -> new DefaultTask(name));
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
        return treeMap.get(taskName);
    }
}
