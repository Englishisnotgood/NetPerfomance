
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.awt.*;


public class drawLine {
    public drawLine(){
        NetFileAnalysis n = new NetFileAnalysis("/home/lzx/sumo_NS2/qingnian/qingnian.net.xml");
        n.init();
        List<Shape> shapeList = n.getShapList();
        interfaceFun(shapeList);
    }

    public void showDialog(NetFileAnalysis n){
        n.init();
        List<Shape> shapeList = n.getShapList();
        interfaceFun(shapeList);
    }

    public static  void main(String[] args) throws Exception{
        new drawLine();
    }

    //图形界面
    private void interfaceFun(List<Shape> shapeList){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jFrame = new JFrame("路网测试");
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                jFrame.setLocationRelativeTo(null);
                // 以当前路径创建文件选择器
                JFileChooser chooser = new JFileChooser(".");
                // 定义文件过滤器
                ExtensionFileFilter filter = new ExtensionFileFilter();
                filter.addExtension("net.xml");
                filter.setDescription("路网文件(*.net.xml)");
                chooser.addChoosableFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(true);
                chooser.setFileView(new FileIconView(filter));
                // ------下面代码开始为该窗口安装菜单------
                JMenuBar menuBar = new JMenuBar();
                JMenu menu = new JMenu("文件");
                menuBar.add(menu);
                JMenuItem openItem = new JMenuItem("打开");
                menu.add(openItem);
                //单击openItem菜单项显示“打开文件”的对话框
                openItem.addActionListener(event -> {
                    int result = chooser.showDialog(jFrame, "打开路网文件");
                    if(result == JFileChooser.APPROVE_OPTION){
                        String name = chooser.getSelectedFile().getPath();
                        System.out.println(name);
                    }
                });
                JMenuItem exitItem = new JMenuItem("Exit");
                menu.add(exitItem);
                exitItem.addActionListener(event -> System.exit(0));
                jFrame.setJMenuBar(menuBar);
                JLabel jLabel = new JLabel() {
                    @Override
                    public void paint(Graphics graphics) {
                        super.paint(graphics);
                        Graphics2D g2d = (Graphics2D)graphics.create();
                        BasicStroke bs1 = new BasicStroke(10);
                        g2d.setStroke(bs1);
                        g2d.setColor(Color.BLACK);
                        //画路
                        for(Shape shape : shapeList){
                            g2d.draw(shape);
                        }
                    }
                };
                JScrollPane scrollPane = new JScrollPane(jLabel);

                jFrame.add(scrollPane);
                jFrame.pack();
                jFrame.setVisible(true);
            }
        });
    }
}

