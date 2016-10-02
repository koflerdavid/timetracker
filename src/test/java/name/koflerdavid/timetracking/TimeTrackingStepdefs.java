package name.koflerdavid.timetracking;

import cucumber.api.DataTable;
import cucumber.api.java8.En;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;


public class TimeTrackingStepdefs implements En {
    private final TaskProvider taskProvider;
    private final TaskStore taskStore;
    private final LogProvider logProvider;
    private final LogStore logStore;
    private final TimeTrackingController timeTrackingController;


    public TimeTrackingStepdefs() {
        final InMemoryTaskManager taskManager = new InMemoryTaskManager();
        this.taskProvider = taskManager;
        this.taskStore = taskManager;

        final InMemoryLogManager logManager = new InMemoryLogManager();
        this.logProvider = logManager;
        this.logStore = logManager;

        timeTrackingController = new TimeTrackingController(taskManager, taskManager, logManager);


        Given("^there is a task \"([^\"]*)\"$", this::createOrGetExistingTask);

        Given("^\"([^\"]*)\" is the current task since \"([^\"]*)\"$", this::startTaskAt);

        When("^I stop the current task at \"([^\"]*)\"$", (final String endOfTaskDateTimeString) -> {
            assertNotNull("There should be a running task", timeTrackingController.getCurrentTask());

            final Instant endOfTask = Instant.parse(endOfTaskDateTimeString);

            timeTrackingController.stopCurrentTask(endOfTask);
        });

        Then("^there should be the following log entry:$", (final DataTable dataTable) -> {
            final Map<String, String> input = dataTable.asMap(String.class, String.class);

            final String beginDateTimeString = input.get("begin");
            final Instant beginning = Instant.parse(beginDateTimeString);

            final String durationString = input.get("duration");
            final Duration duration = Duration.parse(durationString);

            final String taskName = input.get("task");

            boolean found = false;
            for (final TaskLog taskLog : logProvider.getLog()) {
                // We will `continue` if a differing criterion was found.

                if (!Objects.equals(taskName, taskLog.getTaskName())) {
                    continue;
                }

                if (!Objects.equals(beginning, taskLog.getBeginning())) {
                    continue;
                }

                if (!Objects.equals(duration, taskLog.getDuration())) {
                    continue;
                }

                found = true;
            }

            assertTrue("The task log should have been present", found);
        });

        When("^I start \"([^\"]*)\" at \"([^\"]*)\"$", this::startTaskAt);

        Given("^there is no task with name \"([^\"]*)\"$", (final String taskName) -> {
            final Task task = taskProvider.getTaskByName(taskName);
            assertNull("The task should not exist yet", task);
        });

        When("^I create a new task with name \"([^\"]*)\"$", (final String taskName) -> {
            final Task newTask = taskStore.createTask(taskName);
            assertNotNull("The task should have been created", newTask);
            assertTaskNameIs(taskName, newTask);
        });
    }

    private Task createOrGetExistingTask(final String taskName) {
        Task task = taskProvider.getTaskByName(taskName);
        if (null == task) {
            task = taskStore.createTask(taskName);
        }

        assertNotNull("The task should have been created", task);
        assertEquals("The task should have the name " + task, taskName, task.getName());

        return task;
    }

    private void startTaskAt(final String taskName, final String beginningOfTaskDateString) {
        final Instant beginning = Instant.parse(beginningOfTaskDateString);

        timeTrackingController.startTask(taskName, beginning);
    }

    private void assertTaskNameIs(final String taskName, final Task task) {
        assertEquals("The task should have the specified name", taskName, task.getName());
    }
}
