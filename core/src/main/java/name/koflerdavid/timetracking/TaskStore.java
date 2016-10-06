package name.koflerdavid.timetracking;


public interface TaskStore {
    /**
     * Creates a task and stores it. If the task is already present the method should not throw an error.
     *
     * @param taskName the name of the new task.
     * @return a {@link Task} object carrying that name.
     * @throws TimeTrackingException if there were problems when creating the task.
     */
    Task createTask(String taskName) throws TimeTrackingException;
}
