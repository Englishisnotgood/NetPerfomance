import javax.swing.*;
import java.awt.*;

public class DrawPersonDemo {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        JPanel jPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paint(Graphics graphics) {
                super.paint(graphics);

                graphics.drawOval(100, 70, 30, 30);
                graphics.drawRect(105, 100, 20, 30);
                graphics.drawLine(105, 100, 75, 120);
                graphics.drawLine(125, 100, 150, 120);
                graphics.drawLine(105, 130, 75, 150);
                graphics.drawLine(125, 130, 150, 150);
                graphics.drawOval(2000,20,10,10);
            }
        };

        jFrame.add(jPanel);
        jFrame.setSize(5000, 3000);
        jFrame.setVisible(true);
    }
}
