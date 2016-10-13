package name.koflerdavid.timetracking.swinggui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.io.StringWriter;


public class ExceptionDialog extends JDialog {
    private final ExceptionDialogController controller;
    private final Exception exception;

    private final JLabel exceptionMessage = new JLabel("");

    private final JTextArea stackTrace = new JTextArea("");

    private JScrollPane stackTraceScrollPane;

    private final JPanel buttons = new JPanel();

    private final JButton exitButton = new JButton("Exit");


    public ExceptionDialog(final ExceptionDialogController controller, final Exception exception) {
        this.controller = controller;
        this.exception = exception;


        buildGui();
        addBehaviour();
        displayException();
    }

    private void buildGui() {
        stackTraceScrollPane = new JScrollPane(stackTrace);

        buttons.setLayout(new FlowLayout());
        buttons.add(exitButton);

        setLayout(new BorderLayout(10, 10));
        add(exceptionMessage, BorderLayout.NORTH);
        add(stackTraceScrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        setSize(800, 600);
    }

    private void addBehaviour() {
        exitButton.addActionListener(this::exitApplication);
    }

    private void exitApplication(final ActionEvent actionEvent) {
        controller.exitApplication();
    }

    private void displayException() {
        exceptionMessage.setText(exception.getLocalizedMessage());

        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);
        exception.printStackTrace(writer);

        stackTrace.setText(stringWriter.toString());
    }

    public static void displayException(final Exception e) {
        final ExceptionDialog exceptionDialog = new ExceptionDialog(new ExceptionDialogController(), e);
        exceptionDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        exceptionDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        exceptionDialog.setVisible(true);
    }
}
