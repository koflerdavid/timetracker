package name.koflerdavid.timetracking.swinggui;

import name.koflerdavid.timetracking.RunningTask;
import name.koflerdavid.timetracking.TimeTrackingController;
import name.koflerdavid.timetracking.TimeTrackingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Instant;


public class TimeTracker extends JFrame {
    private final TimeTrackingController timeTrackingController;

    private JLabel searchTextFieldLabel;
    private JTextField searchField;
    private JButton startTaskButton;

    private JLabel currentTaskLabel;
    private JLabel currentTaskDisplay;
    private JButton stopTaskButton;

    private JMenuBar menuBar = new JMenuBar();
    JMenuItem stopTaskMenuItem = new JMenuItem("Stop task");
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    JMenuItem helpMenuItem = new JMenuItem("Help");
    JMenuItem aboutMenuItem = new JMenuItem("About");


    public TimeTracker(final TimeTrackingController timeTrackingController) {
        this.timeTrackingController = timeTrackingController;

        buildGui();
        assignBehaviour();
        displayCurrentTask();
    }

    // GUI setup methods

    private void buildGui() {
        buildMenuBar();

        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        setLayout(new BorderLayout(10, 10));
        setJMenuBar(menuBar);
        add(buildSearchAndCurrentTaskPanel(), BorderLayout.NORTH);

        setTitle("Timetracker");
        setSize(500, 300);
    }

    private void buildMenuBar() {
        menuBar = new JMenuBar();

        final JMenu actionMenu = new JMenu("Actions");
        actionMenu.add(stopTaskMenuItem);
        actionMenu.add(exitMenuItem);

        final JMenu reportMenu = new JMenu("Reports");

        final JMenu helpMenu = new JMenu("?");
        helpMenu.add(helpMenuItem);
        helpMenu.add(aboutMenuItem);

        menuBar.add(actionMenu);
        menuBar.add(reportMenu);
        menuBar.add(helpMenu);
    }

    private JPanel buildSearchAndCurrentTaskPanel() {
        searchTextFieldLabel = new JLabel("New task name:");

        searchField = new JTextField("sfd");
        searchField.setColumns(33);
        searchField.setSize(new Dimension(350, searchField.getHeight()));

        startTaskButton = new JButton("Start");

        final JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
        searchPanel.add(searchField);
        searchPanel.add(startTaskButton);

        currentTaskLabel = new JLabel("Current task:");

        currentTaskDisplay = new JLabel("f");
        currentTaskDisplay.setBackground(Color.CYAN);

        stopTaskButton = new JButton("Stop");

        final JPanel currentTaskPanel = new JPanel();
        currentTaskPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        currentTaskPanel.add(currentTaskDisplay);
        currentTaskPanel.add(stopTaskButton);

        final JPanel searchAndCurrentTaskPanel = new JPanel();
        searchAndCurrentTaskPanel.setLayout(new GridLayout(5, 1, 10, 10));
        searchAndCurrentTaskPanel.add(searchTextFieldLabel);
        searchAndCurrentTaskPanel.add(searchPanel);
        searchAndCurrentTaskPanel.add(currentTaskLabel);
        searchAndCurrentTaskPanel.add(currentTaskPanel);

        return searchAndCurrentTaskPanel;
    }

    private void assignBehaviour() {
        searchField.addActionListener(this::searchAndStartTask);
        startTaskButton.addActionListener(this::searchAndStartTask);
        stopTaskButton.addActionListener(this::stopCurrentTask);
        stopTaskMenuItem.addActionListener(this::stopCurrentTask);
    }

    // Helper methods

    private void searchAndStartTask(final ActionEvent event) {
        try {
            final String taskName = searchField.getText();
            timeTrackingController.startTask(taskName, Instant.now());
        } catch (final TimeTrackingException e) {
            ExceptionDialog.displayException(e);
        }

        displayCurrentTask();
    }

    private void stopCurrentTask(final ActionEvent event) {
        try {
            timeTrackingController.stopCurrentTask(Instant.now());
        } catch (final TimeTrackingException e) {
            ExceptionDialog.displayException(e);
        }

        displayCurrentTask();
    }

    private void displayCurrentTask() {
        final RunningTask currentTask = timeTrackingController.getCurrentTask();

        if (null != currentTask) {
            currentTaskDisplay.setText(currentTask.getTask().getName());
        } else {
            currentTaskDisplay.setText("");
        }

        stopTaskButton.setEnabled(null != currentTask);
    }
}
