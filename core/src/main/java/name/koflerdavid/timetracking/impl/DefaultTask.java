package name.koflerdavid.timetracking.impl;

import name.koflerdavid.timetracking.Task;


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
