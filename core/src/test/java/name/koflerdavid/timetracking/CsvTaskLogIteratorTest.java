package name.koflerdavid.timetracking;

import name.koflerdavid.timetracking.csv.CsvTaskLogIterator;
import name.koflerdavid.timetracking.impl.DefaultTaskLog;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


public class CsvTaskLogIteratorTest {
    @Test(expected = NoSuchElementException.class)
    public void emptyCsvFile() throws Exception {
        final CsvTaskLogIterator taskLogIterator = createIteratorFor("");

        assertFalse(taskLogIterator.hasNext());
        taskLogIterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void parsingWithoutColumnHeader() throws Exception {
        final String taskName = "Add feature 2";
        final String beginDateTimeString = "2016-09-20T20:30:40Z";
        final int durationSeconds = 4000;

        final String csvString = String.format("\r\n%s,%s,%d\r\n", taskName, beginDateTimeString, durationSeconds);
        final CsvTaskLogIterator taskLogIterator = createIteratorFor(csvString);

        assertTrue(taskLogIterator.hasNext());
        final TaskLog taskLog = taskLogIterator.next();
        assertFalse(taskLogIterator.hasNext());

        assertNotNull(taskLog);
        final TaskLog expected = new DefaultTaskLog(taskName, Instant.parse(beginDateTimeString), Duration.ofSeconds(durationSeconds));
        assertEquals(expected, taskLog);

        taskLogIterator.next();
    }

    @Ignore("Not sure how to distinguish between logfiles with and without header lines")
    @Test(expected = NoSuchElementException.class)
    public void parseWithRegularColumnHeader() throws Exception {
        final String taskName = "Add feature 2";
        final String beginDateTimeString = "2016-09-20T20:30:40Z";
        final int durationSeconds = 4000;

        final String csvString = String.format("name,begin,duration\r\n%s,%s,%d\r\n", taskName, beginDateTimeString, durationSeconds);
        final CsvTaskLogIterator taskLogIterator = createIteratorFor(csvString);

        assertTrue(taskLogIterator.hasNext());
        final TaskLog taskLog = taskLogIterator.next();
        assertFalse(taskLogIterator.hasNext());

        assertNotNull(taskLog);
        final TaskLog expected = new DefaultTaskLog(taskName, Instant.parse(beginDateTimeString), Duration.ofSeconds(durationSeconds));
        assertEquals(expected, taskLog);

        taskLogIterator.next();
    }

    @Ignore("Not sure how to distinguish between logfiles with and without header lines")
    @Test(expected = NoSuchElementException.class)
    public void parseWithIrregularColumnHeader() throws Exception {
        final String taskName = "Add feature 2";
        final String beginDateTimeString = "2016-09-20T20:30:40Z";
        final int durationSeconds = 4000;

        final String csvString = String.format("duration,name,begin\r\n%d,%s,%s\r\n", durationSeconds, taskName, beginDateTimeString);
        final CsvTaskLogIterator taskLogIterator = createIteratorFor(csvString);

        assertTrue(taskLogIterator.hasNext());
        final TaskLog taskLog = taskLogIterator.next();
        assertFalse(taskLogIterator.hasNext());

        assertNotNull(taskLog);
        final TaskLog expected = new DefaultTaskLog(taskName, Instant.parse(beginDateTimeString), Duration.ofSeconds(durationSeconds));
        assertEquals(expected, taskLog);

        taskLogIterator.next();
    }

    private CsvTaskLogIterator createIteratorFor(final String csvFileContent) throws IOException {
        final CSVParser parser = CSVParser.parse(csvFileContent, CSVFormat.DEFAULT);
        return new CsvTaskLogIterator(parser);
    }
}
