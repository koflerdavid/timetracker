package name.koflerdavid.timetracking;

import name.koflerdavid.timetracking.impl.DefaultTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


public class CsvFileTaskManager implements TaskManager {
    private final Map<String, Task> tasks;

    public CsvFileTaskManager(final String tasklistFile) throws IOException {
        try (FileReader reader = new FileReader(tasklistFile)) {
            tasks = parseTasks(new BufferedReader(reader));
        }
    }

    public static Map<String, Task> parseTasks(final BufferedReader reader) throws IOException {
        final TreeMap<String, Task> tasks = new TreeMap<>();

        reader.lines().forEach((taskName) -> tasks.put(taskName, new DefaultTask(taskName)));

        return tasks;
    }

    @Override
    public Task getTaskByName(final String taskName) {
        return tasks.get(taskName);
    }

    @Override
    public Task createTask(final String taskName) {
        return tasks.computeIfAbsent(taskName, DefaultTask::new);
    }
}
