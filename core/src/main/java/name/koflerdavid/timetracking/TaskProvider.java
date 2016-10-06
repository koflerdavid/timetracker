package name.koflerdavid.timetracking;


public interface TaskProvider {
    /**
     * Tries to find the task with the specified name in the store.
     *
     * @param taskName the name of the task
     * @return the {@link Task} object if it is present in the store, or <code>null</code>
     * @throws TimeTrackingException if there were problems when looking up the task.
     */
    Task getTaskByName(String taskName) throws TimeTrackingException;
}
