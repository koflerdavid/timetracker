package name.koflerdavid.timetracking.csv;

import name.koflerdavid.timetracking.LogManager;
import name.koflerdavid.timetracking.TaskLog;
import name.koflerdavid.timetracking.TimeTrackingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.time.Duration;
import java.time.Instant;


public class CsvFileLogManager implements LogManager {
    private final String logFilePath;

    public CsvFileLogManager(final String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public Iterable<? extends TaskLog> getLog() throws TimeTrackingException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            final CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

            return () -> new CsvTaskLogIterator(parser);

        } catch (final FileNotFoundException e) {
            throw new TimeTrackingException("could not open logfile", e);
        } catch (final IOException e) {
            throw new TimeTrackingException("could not read logfile", e);
        }
    }

    @Override
    public void logTask(final String taskName, final Instant beginning, final Duration duration) throws TimeTrackingException {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            final CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            printer.printRecord(taskName, beginning, duration.getSeconds());
            printer.flush();

        } catch (final IOException e) {
            throw new TimeTrackingException("could not write logfile", e);
        }
    }
}
