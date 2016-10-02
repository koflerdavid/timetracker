package name.koflerdavid.timetracking;


public interface TaskStore {
    /**
     * Creates a task and stores it. If the task is already present the method should not throw an error.
     *
     * @param taskName the name of the new task.
     * @return a {@link Task} object carrying that name.
     */
    Task createTask(String taskName);
}
