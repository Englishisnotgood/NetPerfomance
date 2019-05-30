import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestRefersh {
    JFrame jFrame = new JFrame();
    myPanel jPanel = new myPanel();
    myLabel jLabel = new myLabel();
    Timer timer;
    int x = 10;
    public void init(){
        jFrame.setSize(500, 500);
        //jPanel.setBackground(Color.white);
        jPanel.setOpaque(false);
        JLabel j = new JLabel("cao");
        jPanel.add(j);
        jFrame.add(jPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel.repaint();
            }
        };
        timer = new Timer(100, taskPerformer);
        timer.start();
    }
    class myPanel extends JPanel {
        public void paint(Graphics g){
            //super.paint(g);
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g.create();
            BasicStroke bs1 = new BasicStroke(10);
            g2d.setStroke(bs1);
            //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.2f));
            g2d.setColor(Color.yellow);
            g2d.fillOval(5, 5, 100, 100);
//            g2d.setColor(Color.red);
//            g2d.fillRect(x, 20, 20, 20);
//            x += 1;
        }
    }
    class myLabel extends JLabel {
        public void paint(Graphics g){
            //super.paint(g);
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D)g.create();
            BasicStroke bs1 = new BasicStroke(10);
            g2d.setStroke(bs1);
            g2d.setColor(Color.red);
            g2d.fillRect(x, 20, 20, 20);
            x+=1;
        }
    }

    public static void main(String[] args) {
        new TestRefersh().init();
    }
}
