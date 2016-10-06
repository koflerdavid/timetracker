package name.koflerdavid.timetracking;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Map;
import java.util.StringJoiner;

import static org.junit.Assert.*;


public class CsvFileTaskManagerTest {

    @Test
    public void testParsesEmptyFileCorrectly() throws Exception {
        final StringReader emptyReader = new StringReader("");

        final Map<String, Task> tasks = CsvFileTaskManager.parseTasks(new BufferedReader(emptyReader));

        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void testParsesFileWithOneRecord() throws Exception {
        final String TASK_NAME = "Fix Bug #3";
        final StringReader reader = new StringReader(TASK_NAME);

        final Map<String, Task> tasks = CsvFileTaskManager.parseTasks(new BufferedReader(reader));

        assertNotNull(tasks);
        assertEquals(1, tasks.size());

        final Task onlyTask = tasks.get(TASK_NAME);
        assertNotNull(onlyTask);
        assertEquals(TASK_NAME, onlyTask.getName());
    }

    @Test
    public void testParsesMultipleTasks() throws Exception {
        final String[] tasks = {"Fix Bug #3", "Add Feature #4", "Refactor Task class"};
        final StringJoiner stringJoiner = new StringJoiner("\n");

        for (final String taskName : tasks) {
            stringJoiner.add(taskName);
        }

        final StringReader reader = new StringReader(stringJoiner.toString());

        final Map<String, Task> parsedTasks = CsvFileTaskManager.parseTasks(new BufferedReader(reader));

        assertNotNull(parsedTasks);
        assertEquals(tasks.length, parsedTasks.size());

        for (final String taskName : tasks) {
            if (!parsedTasks.containsKey(taskName)) {
                fail("The following task was not parsed: " + taskName);
            }
        }
    }
}
