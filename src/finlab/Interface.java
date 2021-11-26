package finlab;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Interface {

    private final int DEFAULT_COLUMNS = 70;
    private final int DEFAULT_ROWS = 10;
    private final String menu = """
            1. Load file containing the graph's data
            2. Perform Depth First Traversal of the Graph
            3. Perform Breadth First Traversal of the Graph
            4. Show the shortest path from one vertext to another
            5. Exit
            """;
    private final JLabel fileLabel = new JLabel("Choose a File.");

    // MAIN PANELS
    private final JPanel menuPanel = new JPanel();
    private final JPanel fileChooserPanel = new JPanel();
    private final JPanel menuButtonsPanel = new JPanel();
    private final JPanel outputPanel = new JPanel();

    private final JTextArea menuText = new JTextArea(DEFAULT_ROWS - 4, DEFAULT_COLUMNS);
    private final JTextArea outputText = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private final JScrollPane menuPane = new JScrollPane(menuText);
    private final JScrollPane outputPane = new JScrollPane(outputText);

    private final JButton button1 = new JButton("Option 1");
    private final JButton button2 = new JButton("Option 2");
    private final JButton button3 = new JButton("Option 3");
    private final JButton button4 = new JButton("Option 4");
    private final JButton button5 = new JButton("Exit");
    private final JButton clearButton = new JButton("Clear");
    private final JButton fileButton = new JButton("File");
    private final JComponent[] menuComponents = { button1, button2, button3, button4, button5, clearButton };
    private final JComponent[] fileChooserComponents = { fileLabel, fileButton };

    // Accessor methods
    public JPanel getMenuPanel() { return menuPanel; }
    public JPanel getFileChooserPanel() { return fileChooserPanel; }
    public JPanel getMenuButtonsPanel() { return menuButtonsPanel; }
    public JPanel getOutputPanel() { return outputPanel; }

    // File Chooser segment
    private final JFileChooser fileChooser = new JFileChooser();
    private File file;

    Interface() {

        redirectSystemStreams();    // redirect console to textfield

        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileButton.addActionListener((ActionEvent e) -> {
            int returnVal = fileChooser.showOpenDialog(fileChooserPanel);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                fileLabel.setText(fileChooser.getName(file));
            }
        });

        // Populate the panels
        menuPanel.add(menuPane);    // showMenu()
        menuText.setText(menu);
        menuText.setEditable(false);

        for (var comp : fileChooserComponents) fileChooserPanel.add(comp);  // file chooser

        for (var button : menuComponents) menuButtonsPanel.add(button); // menu buttons

        outputPanel.add(outputPane);    // output panel
    }

    private static void createAndShowGUI() {
        Interface gui = new Interface();
        JFrame frame = new JFrame();

         // Use the Box Layout for the Content Pane
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Add the panels
        frame.getContentPane().add(gui.getMenuPanel());
        frame.getContentPane().add(gui.getFileChooserPanel());
        frame.getContentPane().add(gui.getMenuButtonsPanel());
        frame.getContentPane().add(gui.getOutputPanel());

        // Display the Window
        frame.pack();
        frame.setResizable(false);
        frame.setTitle("Graph Utility");
        frame.setVisible(true);
    }


    /**
     * Helper method for redirectSystemStreams()
     */
    private void updateTextPane(final String text) {
        SwingUtilities.invokeLater(() -> {
            Document doc = outputText.getDocument();
            try {
                doc.insertString(doc.getLength(), text, null);
            } catch (BadLocationException ignored) {

            }
            outputText.setCaretPosition(doc.getLength() - 1);
        });
    }

    /**
     * Redirects CLI output to the JTextArea tableText
     */
    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                updateTextPane(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) {
                updateTextPane(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) {
                write(b, 0, b.length);
            }
        };
        System.setOut(new PrintStream(out, true));
        // System.setErr(new PrintStream(out, true));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {

            }
            createAndShowGUI();
        });
    }
}
