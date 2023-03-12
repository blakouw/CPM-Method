import javax.swing.*;

public class App extends JFrame {

    JPanel container;
    JButton addNode;
    JButton editNode;
    JButton chartGrantt;
    JButton delay;
    JButton criticalPath;
    JButton clear;

    public App(){
        super("Critical Path Method");
        this.setTitle("Critical Path Method");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1200, 800);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setLayout(null);
        container = new JPanel();
        container.setLayout(null);
        container.setBounds(0, 0, 1200, 800);
        this.add(container);

        addNode = new JButton("Dodaj");
        container.add(addNode);
        addNode.setBounds(5, 20, 250, 100);

        editNode = new JButton("Edytuj");
        container.add(editNode);
        editNode.setBounds(5, 140, 250, 100);

        chartGrantt = new JButton("Wykres Grantt'a");
        container.add(chartGrantt);
        chartGrantt.setBounds(5, 260, 250, 100);

        delay = new JButton("Pokaż opóżnienie");
        container.add(delay);
        delay.setBounds(5, 380, 250, 100);

        criticalPath = new JButton("Ścieżka krytyczna");
        container.add(criticalPath);
        criticalPath.setBounds(5, 500, 250, 100);

        clear = new JButton("Wyczyść");
        container.add(clear);
        clear.setBounds(5, 620, 250, 100);
        setVisible(true);
    }
}
