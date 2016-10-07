package name.koflerdavid.timetracking.swinggui;

import name.koflerdavid.timetracking.*;
import name.koflerdavid.timetracking.impl.DefaultRunningTaskFactory;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import javax.swing.*;
import java.util.Arrays;


public class Main {
    public static void main(final String... args) {
        final Settings settings = new Settings();
        final ArgumentParser parser = buildArgumentParser();

        try {
            parser.parseArgs(args, settings);

            final TaskManager taskManager = createTaskManager(settings);
            final LogManager logManager = createLogManager(settings);

            SwingUtilities.invokeLater(() -> {
                final TimeTrackingController timeTrackingController =
                        new TimeTrackingController(new DefaultRunningTaskFactory(),
                                taskManager,
                                taskManager,
                                logManager);

                final TimeTracker timeTracker = new TimeTracker(timeTrackingController);
                timeTracker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                timeTracker.setVisible(true);
            });

        } catch (final ArgumentParserException e) {
            parser.printHelp();
            System.exit(1);

        } catch (final TimeTrackingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(System.err);
        }
    }

    private static TaskManager createTaskManager(final Settings settings) throws TimeTrackingException {
        if (null == settings.getTaskFile()) {
            return new InMemoryTaskManager();
        }

        return new CsvFileTaskManager(settings.getTaskFile());
    }

    private static LogManager createLogManager(final Settings settings) {
        return new InMemoryLogManager();
    }

    private static ArgumentParser buildArgumentParser() {
        final ArgumentParser parser = ArgumentParsers.newArgumentParser("timetracker")
                .defaultHelp(true)
                .description("Log your activities");

        parser.addArgument("-l", "--log-file")
                .dest("logFile")
                .setDefault("logfile.csv");

        parser.addArgument("-t", "--task-file")
                .dest("taskFile")
        ;

        return parser;
    }
}
