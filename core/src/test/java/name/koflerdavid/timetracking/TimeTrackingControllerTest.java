package name.koflerdavid.timetracking;

import name.koflerdavid.timetracking.impl.DefaultRunningTaskFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RunWith(Parameterized.class)
public class TimeTrackingControllerTest {
    private final String taskName;
    private final Instant startOfTask;
    private final Instant endOfTask;

    private TaskProvider taskProvider;
    private TaskStore taskStore;
    private LogProvider logProvider;
    private LogStore logStore;

    private TimeTrackingController timeTrackingController;


    public TimeTrackingControllerTest(final String taskName, final Instant startOfTask, final Instant endOfTask) {
        this.taskName = taskName;
        this.startOfTask = startOfTask;
        this.endOfTask = endOfTask;
    }

    @Before
    public void setUp() throws Exception {
        final InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        taskProvider = inMemoryTaskManager;
        taskStore = inMemoryTaskManager;

        final InMemoryLogManager inMemoryLogManager = new InMemoryLogManager();
        logStore = inMemoryLogManager;
        logProvider = inMemoryLogManager;

        timeTrackingController = new TimeTrackingController(new DefaultRunningTaskFactory(), taskProvider, taskStore, logStore);
    }

    @Test
    public void testNotExistingTaskIsCorrectlyStarted() throws Exception {
        Assert.assertNull(timeTrackingController.getTaskByName(taskName));

        timeTrackingController.startTask(taskName, startOfTask);

        assertTaskIsStarted(taskName, startOfTask);
    }

    @Test
    public void testNotExistingTaskIsStoredAfterBeingStarted() throws Exception {
        Assert.assertNull(timeTrackingController.getTaskByName(taskName));

        timeTrackingController.startTask(taskName, startOfTask);

        assertTaskIsStarted(taskName, startOfTask);
    }

    @Test
    public void testExistingTaskIsSuccessfullyStarted() throws Exception {
        taskStore.createTask(taskName);
        Assert.assertNotNull(timeTrackingController.getTaskByName(taskName));

        timeTrackingController.startTask(taskName, startOfTask);

        assertTaskIsStarted(taskName, startOfTask);
    }

    @Test
    public void testTaskIsLoggedAfterStopping() throws Exception {
        Assert.assertFalse(logProvider.getLog().iterator().hasNext());

        timeTrackingController.startTask(taskName, startOfTask);
        timeTrackingController.stopCurrentTask(endOfTask);

        assertTaskLoggedCorrectly(taskName, startOfTask, endOfTask);
    }

    @Test
    public void testInterruptedTaskIsLoggedCorrectly() throws Exception {
        final String nextTask = this.taskName + "_2";
        final Instant startOfNextTask = endOfTask;

        timeTrackingController.startTask(taskName, startOfTask);
        timeTrackingController.startTask(nextTask, endOfTask);

        assertTaskLoggedCorrectly(this.taskName, startOfTask, endOfTask);
        assertTaskIsStarted(nextTask, startOfNextTask);
    }

    private void assertTaskIsStarted(final String taskName, final Instant startOfTask) {
        final RunningTask currentTask = timeTrackingController.getCurrentTask();

        Assert.assertNotNull(currentTask.getTask());
        Assert.assertEquals(taskName, currentTask.getTask().getName());
        Assert.assertEquals(startOfTask, currentTask.getBeginning());
    }

    private void assertTaskLoggedCorrectly(final String taskName, final Instant startOfTask, final Instant endOfTask) {
        final TaskLog log = logProvider.getLog().iterator().next();
        Assert.assertNotNull(log);
        Assert.assertEquals(log.getTaskName(), taskName);
        Assert.assertEquals(log.getBeginning(), startOfTask);
        Assert.assertEquals(log.getDuration(), Duration.between(startOfTask, endOfTask));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        final List<Object[]> data = new ArrayList<>();

        data.add(new Object[]{"Add feature #50", Instant.parse("2016-08-20T09:40:30Z"), Instant.parse("2016-08-20T10:00:30Z")});
        data.add(new Object[]{"Fix bug #21", Instant.parse("2016-09-20T10:00:00Z"), Instant.parse("2016-09-20T23:00:00Z")});

        return data;
    }
}
