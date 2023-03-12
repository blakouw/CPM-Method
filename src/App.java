import javax.swing.*;

public class App extends JFrame {
    private JPanel CPM;
    private JButton criticalPathButton;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton deleteNodeButton;
    private JButton addNodeButton;

    public App(){
        setTitle("CPM");
        setSize(600,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
