package finlab;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interface {

    private final int DEFAULT_COLUMNS = 75;
    private final int DEFAULT_ROWS = 20;
    private final String menu = """
            1. Perform Depth First Traversal of the Graph
            2. Perform Breadth First Traversal of the Graph
            3. Show the shortest path from one vertex to another
            4. Exit
            """;
    private String infoMessage;
    private String graphType = "UNDIRECTED";
    private final JLabel fileLabel = new JLabel("Choose a File.");

    // MAIN PANELS
    private final JPanel menuPanel = new JPanel();
    private final JPanel fileChooserPanel = new JPanel();
    private final JPanel menuButtonsPanel = new JPanel();
    private final JPanel outputPanel = new JPanel();

    private final JTextArea menuText = new JTextArea(DEFAULT_ROWS - 15, DEFAULT_COLUMNS);
    private final JTextArea outputText = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private final JScrollPane menuPane = new JScrollPane(menuText);
    private final JScrollPane outputPane = new JScrollPane(outputText);

    private final JButton button1 = new JButton("Option 1");
    private final JButton button2 = new JButton("Option 2");
    private final JButton button3 = new JButton("Option 3");
    private final JButton button5 = new JButton("Exit");
    private final JButton clearButton = new JButton("Clear");
    private final JButton fileButton = new JButton("Load File");
    private final JComponent[] menuComponents = { button1, button2, button3, clearButton, button5 };
    private final JComponent[] fileChooserComponents = { fileLabel, fileButton };

    // Accessor methods
    public JPanel getMenuPanel() { return menuPanel; }
    public JPanel getFileChooserPanel() { return fileChooserPanel; }
    public JPanel getMenuButtonsPanel() { return menuButtonsPanel; }
    public JPanel getOutputPanel() { return outputPanel; }

    // File Chooser segment
    private final JFileChooser fileChooser = new JFileChooser(new File("res"));
    private File file;
    private Graph graph;

    Interface() {
        Utility utility = new Utility();
        redirectSystemStreams();    // redirect console to text field

        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileButton.addActionListener((ActionEvent e) -> {
            outputText.setText("");
            int returnVal = fileChooser.showOpenDialog(fileChooserPanel);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                fileLabel.setText(fileChooser.getName(file));
            }

            try {
                graph = utility.parseCSV(file);
                if (graph.isDirected()) graphType = "DIRECTED";
                        else graphType = "UNDIRECTED";
                infoMessage = file.getName() + " loaded successfully." +
                        "\n\nFile type: CSV" +
                        "\nGraph type: " + graphType;

                JOptionPane.showMessageDialog(null,
                        infoMessage, "Information",
                        JOptionPane.INFORMATION_MESSAGE);

                System.out.println("Vertices: " + Arrays.toString(graph.getVertexList().toArray()));
                System.out.println("\nMatrix: \n" +
                        Arrays.deepToString(graph.getMatrix()).replace("], ", "]\n"));
            }  catch (InvalidVertexException | InvalidDataFileException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(),
                        "Error", JOptionPane.WARNING_MESSAGE);
            }  catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, "Invalid CSV File.",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        clearButton.addActionListener((ActionEvent e) -> {
            outputText.setText("");
        });

        button1.addActionListener((ActionEvent e) -> {
            try {
                if (file == null) throw new InvalidDataFileException("No file loaded.");

                String input = JOptionPane.showInputDialog("Input starting vertex: ");

                if (!graph.getVertexList().contains(input))
                    throw new InvalidVertexException("Vertex " + input + " does not exist!");

                System.out.println("\nStarting vertex: " + input);

                utility.depthTraversal(input, graph.getMatrix(), graph.getVertexList());


            }  catch (InvalidVertexException | InvalidDataFileException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(),
                        "Error", JOptionPane.WARNING_MESSAGE);
            }  catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, "Invalid CSV File.",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        button2.addActionListener((ActionEvent e) -> {
            try {
                if (file == null) throw new InvalidDataFileException("No file loaded.");

                String input = JOptionPane.showInputDialog("Input starting vertex: ");

                if (!graph.getVertexList().contains(input))
                    throw new InvalidVertexException("Vertex " + input + " does not exist!");

                System.out.println("\nStarting vertex: " + input);
                System.out.println("Breadth First Traversal of the Graph: ");
                utility.breadthTraversal(input, graph.getMatrix(), graph.getVertexList());
            } catch (InvalidVertexException | InvalidDataFileException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(),
                        "Error", JOptionPane.WARNING_MESSAGE);
            }  catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, "Invalid CSV File.",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        button3.addActionListener((ActionEvent e) -> {
            try {
                if (file == null) throw new InvalidDataFileException("No file loaded.");

                int index;

                String input = JOptionPane.showInputDialog("Input starting vertex: ");

                if (!graph.getVertexList().contains(input))
                    throw new InvalidVertexException("Vertex " + input + " does not exist!");

                index = graph.getVertexList().indexOf(input);

                System.out.println("\nStarting vertex: " + input);
                System.out.println("Determining shortest path via the Djikstra's Algorithm: ");
                utility.determineShortestPath(graph.getMatrix(), index, graph.getVertexList());
                System.out.println();
            }  catch (InvalidVertexException | InvalidDataFileException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(),
                        "Error", JOptionPane.WARNING_MESSAGE);
            }  catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, "Invalid CSV File.",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        button5.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        // Populate the panels
        menuPanel.add(menuPane);    // showMenu()
        menuText.setText(menu);
        menuText.setEditable(false);

        for (var comp : fileChooserComponents) fileChooserPanel.add(comp);  // file chooser

        for (var button : menuComponents) menuButtonsPanel.add(button); // menu buttons

        outputPanel.add(outputPane);    // output panel
        outputText.setEditable(false);
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
