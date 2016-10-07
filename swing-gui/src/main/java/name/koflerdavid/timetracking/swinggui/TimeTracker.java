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
        super("Timetracker");
        this.timeTrackingController = timeTrackingController;

        buildGui();
        assignBehaviour();
        displayCurrentTask();
    }

    private void buildGui() {
        setLayout(new BorderLayout(10, 10));

        buildMenuBar();
        setJMenuBar(menuBar);

        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        add(buildSearchAndCurrentTaskPanel(), BorderLayout.NORTH);

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
        final JPanel searchAndCurrentTaskPanel = new JPanel();
        searchAndCurrentTaskPanel.setLayout(new GridLayout(5, 1, 10, 10));

        searchTextFieldLabel = new JLabel("New task name:");
        searchAndCurrentTaskPanel.add(searchTextFieldLabel);


        final JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));

        searchField = new JTextField("sfd");
        searchField.setColumns(33);
        searchField.setSize(new Dimension(350, searchField.getHeight()));
        searchPanel.add(searchField);

        startTaskButton = new JButton("Start");
        searchPanel.add(startTaskButton);

        searchAndCurrentTaskPanel.add(searchPanel);


        currentTaskLabel = new JLabel("Current task:");
        searchAndCurrentTaskPanel.add(currentTaskLabel);


        final JPanel currentTaskPanel = new JPanel();
        currentTaskPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 10));

        currentTaskDisplay = new JLabel("f");
//        currentTaskDisplay.setPreferredSize(new Dimension(370, currentTaskPanel.getHeight()));
        currentTaskDisplay.setBackground(Color.CYAN);
        currentTaskPanel.add(currentTaskDisplay);

        stopTaskButton = new JButton("Stop");
        currentTaskPanel.add(stopTaskButton);

        searchAndCurrentTaskPanel.add(currentTaskPanel);

        return searchAndCurrentTaskPanel;
    }

    private void assignBehaviour() {
        searchField.addActionListener(this::searchAndStartTask);
        startTaskButton.addActionListener(this::searchAndStartTask);
        stopTaskButton.addActionListener(this::stopCurrentTask);
        stopTaskMenuItem.addActionListener(this::stopCurrentTask);
    }

    private void searchAndStartTask(final ActionEvent event) {
        try {
            final String taskName = searchField.getText();
            timeTrackingController.startTask(taskName, Instant.now());
        } catch (final TimeTrackingException e) {
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }

        displayCurrentTask();
    }

    private void stopCurrentTask(final ActionEvent event) {
        timeTrackingController.stopCurrentTask(Instant.now());
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
