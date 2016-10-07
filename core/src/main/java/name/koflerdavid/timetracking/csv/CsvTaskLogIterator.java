package name.koflerdavid.timetracking.csv;

import name.koflerdavid.timetracking.TaskLog;
import name.koflerdavid.timetracking.impl.DefaultTaskLog;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class CsvTaskLogIterator implements Iterator<TaskLog> {
    private final CSVParser parser;

    private TaskLog nextTaskLog = null;


    public CsvTaskLogIterator(final CSVParser parser) {
        this.parser = parser;
    }

    @Override
    public boolean hasNext() {
        if (null != nextTaskLog) {
            return true;
        }

        return parseNextCsvRecord();
    }

    @Override
    public TaskLog next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        final TaskLog toReturn = nextTaskLog;
        nextTaskLog = null;

        return toReturn;
    }

    private boolean parseNextCsvRecord() {
        final Iterator<CSVRecord> records = parser.iterator();

        while (null == nextTaskLog) {
            if (!records.hasNext()) {
                return false;
            }

            final CSVRecord record = records.next();

            try {
                final String taskName = parseTaskName(record);
                final Instant begin = parseTaskBegin(record);
                final Duration duration = parseTaskDuration(record);

                nextTaskLog = new DefaultTaskLog(taskName, begin, duration);
            } catch (DateTimeParseException | NumberFormatException | IndexOutOfBoundsException e) {
                // Ignore errors and try to parse the next CSV record.
            }
        }

        return null != nextTaskLog;
    }

    private Duration parseTaskDuration(final CSVRecord record) throws NumberFormatException {
        final int duration = Integer.parseInt(record.isSet("duration") ? record.get("duration") : record.get(2));
        return Duration.ofSeconds(duration);
    }

    private String parseTaskName(final CSVRecord record) {
        return record.isSet("name") ? record.get("name") : record.get(0);
    }

    private Instant parseTaskBegin(final CSVRecord record) throws DateTimeParseException {
        return Instant.parse(record.isSet("begin") ? record.get("begin") : record.get(1));
    }
}
