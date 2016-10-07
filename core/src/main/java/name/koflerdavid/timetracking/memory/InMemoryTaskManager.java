package name.koflerdavid.timetracking.memory;

import name.koflerdavid.timetracking.Task;
import name.koflerdavid.timetracking.TaskManager;
import name.koflerdavid.timetracking.impl.DefaultTask;

import java.util.TreeMap;


public class InMemoryTaskManager implements TaskManager {
    private final TreeMap<String, Task> treeMap = new TreeMap<>();

    @Override
    public Task createTask(final String taskName) {
        return treeMap.computeIfAbsent(taskName, (name) -> new DefaultTask(name));
    }

    @Override
    public Task getTaskByName(final String taskName) {
        return treeMap.get(taskName);
    }
}
