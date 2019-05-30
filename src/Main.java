import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

public class Main {
    JFrame jFrame = new JFrame("平行空间车载网络性能测试系统");
    //菜单栏
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("路网文件");
    JMenuItem openItem = new JMenuItem("打开路网", new ImageIcon("/home/lzx/IdeaProjects/test/ico/open.png"));
    JMenuItem exitItem = new JMenuItem("退出", new ImageIcon("/home/lzx/IdeaProjects/test/ico/exit.png"));
    JMenu trajectoryMenu = new JMenu("轨迹");
    JMenuItem openTra = new JMenuItem("导入轨迹文件", new ImageIcon("/home/lzx/IdeaProjects/test/ico/open.png"));
    JMenuItem generateTra = new JMenuItem("生成轨迹文件", new ImageIcon("/home/lzx/IdeaProjects/test/ico/about.png"));
    JMenu helpMenu = new JMenu("帮助");
    JMenuItem helpItem = new JMenuItem("操作步骤", new ImageIcon("/home/lzx/IdeaProjects/test/ico/about.png"));
    //Top(Box)
    Box topBox = Box.createHorizontalBox();
    //LeftTop(Box)
    Box topLeftBox = Box.createVerticalBox();
    JLabel leftViewLabel = new JLabel();
    JPanel leftPanel = new JPanel();
    JLabel sendPacketsLabel = new JLabel("发包数:");
    JLabel sendPackets = new JLabel("0");
    JLabel receivePacketsLabel = new JLabel("收包数:");
    JLabel receivePackets = new JLabel("0");
    JLabel packetReachRateLabel = new JLabel("包到达率:");
    JLabel packetReachRate = new JLabel("0.000");
    JLabel delay = new JLabel("平均时延:");
    JLabel delayData = new JLabel("0.000");
    //RightTop(Box)
    Box topRightBox = Box.createVerticalBox();
    JScrollPane rightJSP = new JScrollPane();
    JLabel rightView = new JLabel();
    JPanel rightPanel = new JPanel();
    JLabel sendPacketsLabel2 = new JLabel("发包数:");
    JLabel sendPackets2 = new JLabel("0");
    JLabel receivePacketsLabel2 = new JLabel("收包数:");
    JLabel receivePackets2 = new JLabel("0");
    JLabel packetReachRateLabel2 = new JLabel("包到达率:");
    JLabel packetReachRate2 = new JLabel("0.0");
    JLabel delay2 = new JLabel("平均时延:");
    JLabel delayData2 = new JLabel("0.0");
    //Bottom(JPanel)
    Box bottom = Box.createHorizontalBox();
    //Bottom_left(JPanel)
    Box bottomLeft = Box.createVerticalBox();
    JPanel bottomLeftLine1 = new JPanel();
    JButton matrixA = new JButton("转移矩阵A");
    JTextField matrixAFile = new JTextField(40);
    JPanel bottomLeftLine2 = new JPanel();
    JButton matrixB = new JButton("发射矩阵B");
    JTextField matrixBFile = new JTextField(40);
    JPanel bottomLeftLine3 = new JPanel();
    JButton matrixPI = new JButton("初始概率PI");
    JTextField matrixPIFile = new JTextField(40);
    //Bottom_right(JPanel)
    Box bottomRight = Box.createHorizontalBox();
    JButton goButton = new JButton("开始", new ImageIcon("/home/lzx/IdeaProjects/test/ico/go.jpeg"));
    JButton pauseButton = new JButton("暂停", new ImageIcon("/home/lzx/IdeaProjects/test/ico/pause.jpg"));
    JButton overButton = new JButton("结束", new ImageIcon("/home/lzx/IdeaProjects/test/ico/stop.jpg"));
    JButton ExportButton = new JButton("导出数据", new ImageIcon("/home/lzx/IdeaProjects/test/ico/dao.jpg"));
    //

    HashMap<Integer, carPoint> carMap = new HashMap<>();
    HashMap<Float, java.util.List<Mobility>> mobilityMap = new HashMap<>();
    Float mapKey = new Float(0.0);
    Timer timer;
    java.util.List<Mobility> carMoblity;
    ImageIcon carImage = new ImageIcon("/home/lzx/IdeaProjects/test/ico/yellowCar.png");
    String traceAddr = null;
    HashMap<Float, Time_trace> traceTimeMap = new HashMap<>();
    HashMap<Integer, Float> start_time = new HashMap<>();
    HashMap<Integer, Float> end_time = new HashMap<>();

    //组装界面
    public void init() {
        //---------菜单栏---------
        fileMenu.add(openItem);
        fileMenu.add(exitItem);
        exitItem.addActionListener(event -> System.exit(0));
        trajectoryMenu.add(openTra);
        trajectoryMenu.add(generateTra);
        helpItem.addActionListener(event -> {
            JOptionPane.showMessageDialog(jFrame, "1.导入路网文件\n2.导入轨迹文件\n3.点击开始运行"
                    , "操作步骤", JOptionPane.INFORMATION_MESSAGE);
        });
        helpMenu.add(helpItem);
        menuBar.add(fileMenu);
        menuBar.add(trajectoryMenu);
        menuBar.add(helpMenu);
        jFrame.setJMenuBar(menuBar);
        MyJpanel drawAreaLeft = new MyJpanel();
        drawAreaLeft.setPreferredSize(new Dimension(600, 600));
        MyJpanel drawAreaRight = new MyJpanel();
        drawAreaRight.setPreferredSize(new Dimension(600, 600));
        // 以当前路径创建文件选择器
        JFileChooser chooser = new JFileChooser("/home/lzx/sumo_NS2");
        // 定义文件过滤器
        ExtensionFileFilter filter = new ExtensionFileFilter();
        filter.addExtension("net.xml");
        filter.addExtension("png");
        filter.setDescription("路网文件(*.net.xml)");
        chooser.addChoosableFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileView(new FileIconView(filter));
        openItem.addActionListener(event -> {
            int result = chooser.showDialog(jFrame, "打开路网文件");
            if (result == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getPath();
                NetFileAnalysis n = new NetFileAnalysis(name);
                n.init();
                java.util.List<Shape> shapeList = n.getShapList();
                drawAreaLeft.setShapList(shapeList);
                drawAreaLeft.repaint();
                drawAreaRight.setShapList(shapeList);
                drawAreaRight.repaint();
            }
        });
        // 以当前路径创建文件选择器
        JFileChooser chooser2 = new JFileChooser("/home/lzx/sumo_NS2");
        // 定义文件过滤器
        ExtensionFileFilter filter2 = new ExtensionFileFilter();
        filter2.addExtension("tcl");
        filter2.setDescription("轨迹文件(*.tcl)");
        chooser2.addChoosableFileFilter(filter2);
        chooser2.setAcceptAllFileFilterUsed(true);
        chooser2.setFileView(new FileIconView(filter2));
        openTra.addActionListener(event -> {
            int result = chooser2.showDialog(jFrame, "打开轨迹文件");
            if (result == JFileChooser.APPROVE_OPTION) {
                String addr = chooser2.getSelectedFile().getPath();
                MobilityAnalys ma = new MobilityAnalys(addr);
                ma.init();
                carMap = ma.getCarMap();
                mobilityMap = ma.getMobilityMap();
            }
        });

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mobilityMap.containsKey(mapKey)) {
                    timer.stop();
                } else {
                    carMoblity = mobilityMap.get(mapKey);
                    Time_trace t = traceTimeMap.get(mapKey);
                    if(t != null) {
                        sendPackets.setText(t.getSends().toString());
                        receivePackets.setText(t.getReceives().toString());
                        if(t.getSends() != 0) {
                            Float r = (float) t.getReceives() / t.getSends();
                            r = (float) (Math.round(r * 1000)) / 1000;
                            String sr = r.toString();
                            while(sr.length() < 5)
                                sr = sr + "0";
                            packetReachRate.setText(sr);
//                            Float duration = new Float(0);
//                            int i=0;
//                            for (i=0; i<t.getHighest_packet_id(); i++){
//                                if(start_time.keySet().contains(i) && end_time.keySet().contains(i)) {
//                                    Float tf = start_time.get(i) - end_time.get(i);
//                                    if(tf > 0) {
//                                        duration += tf;
//                                    }
//                                }
//                            }
//                            Float fd = duration/t.receives;
//                            fd = (float) (Math.round(fd * 1000)) / 1000;
//                            String fs = fd.toString();
//                            while(fs.length() < 5)
//                                fs = fs + "0";
//                            delayData.setText(fs);
                        }
                    }
                    drawAreaLeft.repaint();
                    mapKey += (float)0.1;
                    mapKey = (float)(Math.round(mapKey*10))/10;
                }
            }
        };
        timer = new Timer(100, taskPerformer);

        //----------------------------------------------------------------------------

        //----------------------------LeftTop(Box)------------------------------------
        topLeftBox.add(drawAreaLeft);
        leftPanel.add(sendPacketsLabel);
        leftPanel.add(sendPackets);
        leftPanel.add(receivePacketsLabel);
        leftPanel.add(receivePackets);
        leftPanel.add(packetReachRateLabel);
        leftPanel.add(packetReachRate);
        leftPanel.add(delay);
        leftPanel.add(delayData);
        topLeftBox.add(leftPanel);
        //----------------------------------------------------------------------------

        //----------------------------RightTop(Box)-----------------------------------
        topRightBox.add(drawAreaRight);
        rightPanel.add(sendPacketsLabel2);
        rightPanel.add(sendPackets2);
        rightPanel.add(receivePacketsLabel2);
        rightPanel.add(receivePackets2);
        rightPanel.add(packetReachRateLabel2);
        rightPanel.add(packetReachRate2);
        rightPanel.add(delay2);
        rightPanel.add(delayData2);
        topRightBox.add(rightPanel);
        //----------------------------------------------------------------------------

        //---------------------------Bottom_left(JPanel)------------------------------
        JFileChooser chooserA = new JFileChooser(".");
        ExtensionFileFilter filterA = new ExtensionFileFilter();
        filterA.addExtension("txt");
        filterA.setDescription("转移矩阵(*.txt)");
        chooserA.addChoosableFileFilter(filterA);
        chooserA.setAcceptAllFileFilterUsed(true);
        chooserA.setFileView(new FileIconView(filterA));
        matrixA.addActionListener(event -> {
            int result = chooserA.showDialog(jFrame, "打开转移矩阵A");
            if (result == JFileChooser.APPROVE_OPTION) {
                String name = chooserA.getSelectedFile().getPath();
                matrixAFile.setText(name);
            }
        });
        matrixAFile.setEditable(false);
        bottomLeftLine1.add(matrixA);
        bottomLeftLine1.add(matrixAFile);
        JFileChooser chooserB = new JFileChooser(".");
        ExtensionFileFilter filterB = new ExtensionFileFilter();
        filterB.addExtension("txt");
        filterB.setDescription("发射矩阵(*.txt)");
        chooserB.addChoosableFileFilter(filterB);
        chooserB.setAcceptAllFileFilterUsed(true);
        chooserB.setFileView(new FileIconView(filterB));
        matrixB.addActionListener(event -> {
            int result = chooserB.showDialog(jFrame, "打开发射矩阵B");
            if (result == JFileChooser.APPROVE_OPTION) {
                String name = chooserB.getSelectedFile().getPath();
                matrixBFile.setText(name);
            }
        });
        matrixBFile.setEditable(false);
        bottomLeftLine2.add(matrixB);
        bottomLeftLine2.add(matrixBFile);
        JFileChooser chooserPI = new JFileChooser(".");
        ExtensionFileFilter filterPI = new ExtensionFileFilter();
        filterPI.addExtension("txt");
        filterPI.setDescription("发射矩阵(*.txt)");
        chooserPI.addChoosableFileFilter(filterPI);
        chooserPI.setAcceptAllFileFilterUsed(true);
        chooserPI.setFileView(new FileIconView(filterPI));
        matrixPI.addActionListener(event -> {
            int result = chooserPI.showDialog(jFrame, "打开初始矩阵PI");
            if (result == JFileChooser.APPROVE_OPTION) {
                String name = chooserPI.getSelectedFile().getPath();
                matrixPIFile.setText(name);
            }
        });
        matrixPIFile.setEditable(false);
        bottomLeftLine3.add(matrixPI);
        bottomLeftLine3.add(matrixPIFile);
        bottomLeft.add(bottomLeftLine1);
        bottomLeft.add(bottomLeftLine2);
        bottomLeft.add(bottomLeftLine3);
        //----------------------------------------------------------------------------

        //---------------------------Bottom_right(JPanel)-----------------------------
        bottomRight.add(goButton);
        goButton.addActionListener(event -> {
            timer.start();
        });
        bottomRight.add(pauseButton);
        bottomRight.add(ExportButton);
        bottomRight.add(overButton);
        overButton.addActionListener(event -> System.exit(0));
        //----------------------------------------------------------------------------

        //-------------------------------Bottom---------------------------------------
        bottom.add(bottomLeft);
        bottom.add(bottomRight);
        //----------------------------------------------------------------------------

        //-------------------------------Box------------------------------------------
        topBox.add(topLeftBox);
        topBox.add(topRightBox);
        jFrame.add(topBox);
        jFrame.add(bottom, BorderLayout.SOUTH);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
        //----------------------------------------------------------------------------

        //---------------------------网络性能计算---------------------------------------
//        TraceFileAnalysis traceA = new TraceFileAnalysis();
//        traceA.init();
//        traceTimeMap = traceA.getTraceTimeMap();
//        start_time = traceA.getStart_time();
//        end_time = traceA.getEnd_time();
        //----------------------------------------------------------------------------
    }


    public static void main(String[] args) {
        new Main().init();
    }
    class MyJpanel extends JPanel{
        java.util.List<Shape> shapeList = null;
        public MyJpanel(){
            this.shapeList = null;
        }
        public MyJpanel(java.util.List<Shape> shapeName){
            this.shapeList = shapeName;
        }
        public void setShapList(java.util.List<Shape> shapeName){
            this.shapeList = shapeName;
        }
        public void paint(Graphics g){
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g.create();
            BasicStroke bs1 = new BasicStroke(10);
            g2d.setStroke(bs1);
            g2d.setColor(Color.BLACK);
            //画路
            if (shapeList != null) {
                for (Shape shape : shapeList) {
                    g2d.draw(shape);
                }
            }
            //画车
            if (carMoblity != null) {
                for (Mobility m : carMoblity) {
                    g2d.setColor(Color.red);
                    g2d.drawImage(carImage.getImage(), (int)m.getX(), (int) m.getY(),
                            carImage.getIconWidth(), carImage.getIconHeight(), null);
                }
            }
        }
    }
}
