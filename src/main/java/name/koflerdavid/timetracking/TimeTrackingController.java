package name.koflerdavid.timetracking;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;


public class TimeTrackingController {
    private final AtomicReference<RunningTask> currentTask = new AtomicReference<>();
    private final TaskProvider taskProvider;
    private final TaskStore taskStore;
    private final LogStore logStore;


    public TimeTrackingController(final TaskProvider taskProvider, final TaskStore taskStore, final LogStore logStore) {
        this.taskProvider = taskProvider;
        this.taskStore = taskStore;
        this.logStore = logStore;
    }

    public RunningTask getCurrentTask() {
        return currentTask.get();
    }

    public void startTask(final String taskName, final Instant beginningOfNewTask) {
        Task newTask = taskProvider.getTaskByName(taskName);
        if (null == newTask) {
            newTask = taskStore.createTask(taskName);
        }

        final RunningTask oldTask = currentTask.getAndSet(new DefaultRunningTask(newTask, beginningOfNewTask));
        if (null != oldTask) {
            logTaskExecution(oldTask, beginningOfNewTask);
        }
    }

    public Task getTaskByName(final String taskName) {
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
