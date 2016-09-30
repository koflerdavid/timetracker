package name.koflerdavid.timetracking;


public class DefaultTask implements Task {
    private final String name;

    public DefaultTask(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
