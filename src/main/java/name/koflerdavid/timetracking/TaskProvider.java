package name.koflerdavid.timetracking;


public interface TaskProvider {
    /**
     * Tries to find the task with the specified name in the store.
     *
     * @param taskName
     * @return the {@link Task} object if it is present in the store, or <code>null</code>
     */
    Task getTaskByName(String taskName);
}
