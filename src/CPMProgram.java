import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class CPMProgram extends JFrame implements ActionListener {
    private final JPanel panel;
    private final JButton addButton, deleteButton, editButton, calculateButton, clearButton;
    private final JLabel nameLabel, durationLabel, dependencyLabel, criticalPathLabel, delayLabel, namesLabel, delLabel, depLabel;
    private final JTextField nameField, durationField, dependencyField;
    private final JTextArea criticalPathArea, delayArea, nodesArea;
    private final JScrollPane criticalPathScrollPane, delayScrollPane, nodesScrollPane;

    private final Map<String, Node> nodes;

    private static final int PANEL_WIDTH = 700;
    private static final int PANEL_HEIGHT = 500;

    public CPMProgram() {
        setTitle("Critical Path Method Program");
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 10, 100, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(110, 10, 100, 25);
        panel.add(nameField);

        durationLabel = new JLabel("Duration:");
        durationLabel.setBounds(10, 40, 100, 25);
        panel.add(durationLabel);

        durationField = new JTextField();
        durationField.setBounds(110, 40, 100, 25);
        panel.add(durationField);

        dependencyLabel = new JLabel("Dependency:");
        dependencyLabel.setBounds(10, 70, 100, 25);
        panel.add(dependencyLabel);

        dependencyField = new JTextField();
        dependencyField.setBounds(110, 70, 100, 25);
        panel.add(dependencyField);

        addButton = new JButton("Add");
        addButton.setBounds(10, 100, 100, 25);
        addButton.addActionListener(this);
        panel.add(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(120, 100, 100, 25);
        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        editButton = new JButton("Edit");
        editButton.setBounds(230, 100, 100, 25);
        editButton.addActionListener(this);
        panel.add(editButton);

        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(340, 100, 100, 25);
        calculateButton.addActionListener(this);
        panel.add(calculateButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(450, 100, 100, 25);
        clearButton.addActionListener(this);
        panel.add(clearButton);

        criticalPathLabel = new JLabel("Critical Path:");
        criticalPathLabel.setBounds(10, 140, 100, 25);
        panel.add(criticalPathLabel);

        criticalPathArea = new JTextArea();
        criticalPathArea.setEditable(false);
        criticalPathScrollPane = new JScrollPane(criticalPathArea);
        criticalPathScrollPane.setBounds(10, 170, 200,200);
        panel.add(criticalPathScrollPane);

        delayLabel = new JLabel("Delay:");
        delayLabel.setBounds(220, 140, 100, 25);
        panel.add(delayLabel);

        delayArea = new JTextArea();
        delayArea.setEditable(false);
        delayScrollPane = new JScrollPane(delayArea);
        delayScrollPane.setBounds(220, 170, 100, 200);
        panel.add(delayScrollPane);

        namesLabel = new JLabel("Names:");
        namesLabel.setBounds(330, 140, 50, 25);
        panel.add(namesLabel);

        delLabel = new JLabel("Durations:");
        delLabel.setBounds(418, 140, 100, 25);
        panel.add(delLabel);

        depLabel = new JLabel("Dependencies:");
        depLabel.setBounds(505, 140, 100, 25);
        panel.add(depLabel);

        nodesArea = new JTextArea();
        nodesArea.setEditable(false);
        nodesScrollPane = new JScrollPane(nodesArea);
        nodesScrollPane.setBounds(330, 170, 260, 200);
        panel.add(nodesScrollPane);

        Border line = BorderFactory.createLineBorder(Color.GRAY);
        Border empty = new EmptyBorder(5, 5, 5, 5);
        CompoundBorder border = new CompoundBorder(line, empty);
        criticalPathArea.setBorder(border);
        delayArea.setBorder(border);

        nodes = new HashMap<>();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameField.getText();
            int duration = Integer.parseInt(durationField.getText());
            String dependency = dependencyField.getText();

            Node node = new Node(name, duration);

            if (!dependency.isEmpty()) {
                String[] dependencies = dependency.split(",");
                for (String dependencyName : dependencies) {
                    Node dependencyNode = nodes.get(dependencyName.trim());
                    node.addDependency(dependencyNode);
                }
            }

            nodes.put(name, node);

            nameField.setText("");
            durationField.setText("");
            dependencyField.setText("");

        } else if (e.getSource() == deleteButton) {
            String name = JOptionPane.showInputDialog("Enter node name to delete:");
            Node node = nodes.get(name);
            nodes.remove(name);
            for (Node n : nodes.values()) {
                n.removeDependency(node);
            }

        } else if (e.getSource() == editButton) {
            String name = JOptionPane.showInputDialog("Enter node name to edit:");
            Node node = nodes.get(name);
            nameField.setText(node.getName());
            durationField.setText(Integer.toString(node.getDuration()));
            Set<Node> dependencies = node.getDependencies();
            StringBuilder dependencyString = new StringBuilder();
            for (Node dependency : dependencies) {
                dependencyString.append(dependency.getName()).append(",");
            }
            dependencyField.setText(dependencyString.toString());

        } else if (e.getSource() == calculateButton) {
            Map<Node, Integer> earliestStartTimes = new HashMap<>();
            Map<Node, Integer> latestStartTimes = new HashMap<>();

            // calculate earliest start times
            for (Node node : nodes.values()) {
                int earliestStartTime = 0;
                for (Node dependency : node.getDependencies()) {
                    int dependencyEndTime = earliestStartTimes.get(dependency) + dependency.getDuration();
                    earliestStartTime = Math.max(earliestStartTime, dependencyEndTime);
                }
                earliestStartTimes.put(node, earliestStartTime);
            }

            // calculate latest start times
            int totalDuration = 0;
            for (Node node : nodes.values()) {
                totalDuration = Math.max(totalDuration, earliestStartTimes.get(node) + node.getDuration());
            }
            latestStartTimes.put(nodes.get("END"), totalDuration);
            for (Node node : nodes.values()) {
                int latestStartTime = latestStartTimes.get(nodes.get("END")) - node.getDuration();
                for (Node dependent : node.getDependents()) {
                    latestStartTime = Math.min(latestStartTime, earliestStartTimes.get(dependent) - node.getDuration());
                }
                latestStartTimes.put(node, latestStartTime);
            }

            // find critical path and calculate delay
            StringBuilder criticalPath = new StringBuilder();
            StringBuilder nodesPath = new StringBuilder();
            int delay = 0;
            for (Node node : nodes.values()) {
                nodesPath.append(node.getName()).append("\t").append(node.getDuration()).append("\t");
                for(Node node1 : node.getDependencies()){
                    nodesPath.append(node1.getName()).append(", ");
                }
                nodesPath.append(System.getProperty("line.separator"));

                int slack = latestStartTimes.get(node
                ) - earliestStartTimes.get(node);
                if (slack == 0) {
                    criticalPath.append(node.getName()).append(" -> ");
                    delay += node.getDuration();
                }
            }
            criticalPath.delete(criticalPath.length() - 4, criticalPath.length());
            criticalPathArea.setText(criticalPath.toString());
            delayArea.setText(Integer.toString(delay));
            nodesArea.setText(nodesPath.toString());
        } else if (e.getSource() == clearButton) {
            nodes.clear();
            criticalPathArea.setText("");
            delayArea.setText("");
            nodesArea.setText("");
        }
    }

}
