package name.koflerdavid.timetracking.swinggui;

import name.koflerdavid.timetracking.InMemoryLogManager;
import name.koflerdavid.timetracking.InMemoryTaskManager;
import name.koflerdavid.timetracking.TimeTrackingController;
import name.koflerdavid.timetracking.impl.DefaultRunningTaskFactory;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;

import javax.swing.*;


public class Main {
    public static void main(final String... args) {
        SwingUtilities.invokeLater(() -> {
            final InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
            final InMemoryLogManager inMemoryLogManager = new InMemoryLogManager();

            final TimeTrackingController timeTrackingController =
                    new TimeTrackingController(new DefaultRunningTaskFactory(),
                            inMemoryTaskManager,
                            inMemoryTaskManager,
                            inMemoryLogManager);

            final TimeTracker timeTracker = new TimeTracker(timeTrackingController);
            timeTracker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            timeTracker.setVisible(true);
        });
    }

    private static ArgumentParser buildParser() {
        final ArgumentParser parser = ArgumentParsers
                .newArgumentParser("timetracker")
                .defaultHelp(true)
                .description("Log your activities");
        ;

        return parser;
    }
}
