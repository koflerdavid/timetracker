package name.koflerdavid.timetracking;


public interface LogProvider {
    Iterable<? extends TaskLog> getLog();
}
