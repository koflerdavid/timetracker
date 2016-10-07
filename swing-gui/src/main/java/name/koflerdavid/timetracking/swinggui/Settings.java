package name.koflerdavid.timetracking.swinggui;

import net.sourceforge.argparse4j.annotation.Arg;


public class Settings {
    @Arg(dest = "logFile")
    private String logfile;

    @Arg(dest = "taskFile")
    private String taskFile;

    public String getLogfile() {
        return logfile;
    }

    public Settings setLogfile(final String logfile) {
        this.logfile = logfile;
        return this;
    }

    public String getTaskFile() {
        return taskFile;
    }

    public Settings setTaskFile(final String taskFile) {
        this.taskFile = taskFile;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Settings{logfile='%s', taskFile='%s'}", logfile, taskFile);
    }
}
