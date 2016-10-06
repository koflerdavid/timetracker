package name.koflerdavid.timetracking;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;


public class TimeTrackingController {
    private final AtomicReference<RunningTask> currentTask = new AtomicReference<>();

    private final RunningTaskFactory runningTaskFactory;
    private final TaskProvider taskProvider;
    private final TaskStore taskStore;
    private final LogStore logStore;

    public TimeTrackingController(final RunningTaskFactory runningTaskFactory, final TaskProvider taskProvider, final TaskStore taskStore, final LogStore logStore) {
        this.runningTaskFactory = runningTaskFactory;
        this.taskProvider = taskProvider;
        this.taskStore = taskStore;
        this.logStore = logStore;
    }

    public RunningTask getCurrentTask() {
        return currentTask.get();
    }

    public void startTask(final String taskName, final Instant beginningOfNewTask) throws TimeTrackingException {
        Task newTask = taskProvider.getTaskByName(taskName);
        if (null == newTask) {
            newTask = taskStore.createTask(taskName);
        }

        final RunningTask newRunningTask = runningTaskFactory.createRunningTask(newTask, beginningOfNewTask);
        final RunningTask oldRunningTask = currentTask.getAndSet(newRunningTask);
        if (null != oldRunningTask) {
            logTaskExecution(oldRunningTask, beginningOfNewTask);
        }
    }

    public Task getTaskByName(final String taskName) throws TimeTrackingException {
        return taskProvider.getTaskByName(taskName);
    }

    public void stopCurrentTask(final Instant endOfTask) {
        final RunningTask currentTask = this.currentTask.getAndSet(null);

        if (null != currentTask) {
            logTaskExecution(currentTask, endOfTask);
        }
    }

    protected void logTaskExecution(final RunningTask runningTask, final Instant endOfTask) {
        final Instant beginning = runningTask.getBeginning();
        final Duration duration = Duration.between(beginning, endOfTask);

        logStore.logTask(runningTask.getTask().getName(), beginning, duration);
    }
}
