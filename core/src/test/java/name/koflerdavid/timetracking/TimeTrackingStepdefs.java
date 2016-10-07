package name.koflerdavid.timetracking;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import name.koflerdavid.timetracking.impl.DefaultRunningTaskFactory;
import name.koflerdavid.timetracking.memory.InMemoryLogManager;
import name.koflerdavid.timetracking.memory.InMemoryTaskManager;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;


public class TimeTrackingStepdefs {
    private final TaskProvider taskProvider;
    private final TaskStore taskStore;
    private final LogProvider logProvider;
    private final TimeTrackingController timeTrackingController;


    public TimeTrackingStepdefs() {
        final InMemoryTaskManager taskManager = new InMemoryTaskManager();
        this.taskProvider = taskManager;
        this.taskStore = taskManager;

        final InMemoryLogManager logManager = new InMemoryLogManager();
        this.logProvider = logManager;

        timeTrackingController = new TimeTrackingController(new DefaultRunningTaskFactory(), taskManager, taskManager, logManager);
    }

    @Given("^there is no task with name \"([^\"]*)\"$")
    public void ensureTaskDoesNotExist(final String taskName) throws Exception {
        final Task task = taskProvider.getTaskByName(taskName);
        assertNull("The task should not exist yet", task);
    }

    @Given("^there is a task \"([^\"]*)\"$")
    @When("^I create a new task with name \"([^\"]*)\"$")
    public Task createOrGetExistingTask(final String taskName) throws Exception {
        final Task task = taskStore.createTask(taskName);

        assertNotNull("The task should have been created", task);
        assertTaskNameIs(taskName, task);

        return task;
    }

    @Given("^\"([^\"]*)\" is the current task since \"([^\"]*)\"$")
    @When("^I start \"([^\"]*)\" at \"([^\"]*)\"$")
    public void startTaskAt(final String taskName, final String beginningOfTaskDateString) throws Exception {
        final Instant beginning = Instant.parse(beginningOfTaskDateString);

        timeTrackingController.startTask(taskName, beginning);
    }

    @When("^I stop the current task at \"([^\"]*)\"$")
    public void stopCurrentTask(final String endOfTaskDateTimeString) throws Exception {
        assertNotNull("There should be a running task", timeTrackingController.getCurrentTask());

        final Instant endOfTask = Instant.parse(endOfTaskDateTimeString);

        timeTrackingController.stopCurrentTask(endOfTask);
    }

    @Then("^there should be the following log entry:$")
    public void ensureLogEntryExists(final DataTable dataTable) throws Exception {
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
    }

    private void assertTaskNameIs(final String taskName, final Task task) {
        assertEquals("The task should have the specified name", taskName, task.getName());
    }
}
