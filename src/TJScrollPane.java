import javax.swing.*;
import java.awt.*;

public class TJScrollPane extends JApplet {
    JScrollPane scrollPane = null;
    JLabel label = null;
    JPanel panel = null;
    Icon icon = null;
    public void init(){
        Container container = getContentPane();
        container.setLayout(new GridLayout(1,1));
        icon = new ImageIcon("/home/lzx/IdeaProjects/test/ico/3.gif");
        label = new JLabel(icon);
        panel = new JPanel();
        panel.add(label);

        scrollPane = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container.add(scrollPane);
    }
}
