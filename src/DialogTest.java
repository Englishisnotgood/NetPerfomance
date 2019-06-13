import javafx.scene.image.Image;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
// 增加选择路由协议的按钮
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DialogTest {
    //---------------------总体---------------------------------
    JFrame jFrame = new JFrame("基于平行空间车载网络性能测试系统");
    Box box = new Box(BoxLayout.Y_AXIS);
    //---------------------------------------------------------

    //-------------------菜单栏--------------------------------
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("路网文件");
    JMenuItem openItem = new JMenuItem("打开路网", new ImageIcon("/home/lzx/IdeaProjects/test/ico/open.png"));
    JMenuItem exitItem = new JMenuItem("退出", new ImageIcon("/home/lzx/IdeaProjects/test/ico/exit.png"));
    JMenu trajectoryMenu = new JMenu("轨迹文件");
    JMenuItem openTra = new JMenuItem("导入轨迹文件", new ImageIcon("/home/lzx/IdeaProjects/test/ico/open.png"));
    JMenuItem generateTra = new JMenuItem("生成轨迹文件", new ImageIcon("/home/lzx/IdeaProjects/test/ico/about.png"));
    JMenuItem preOpenTra = new JMenuItem("导入预测轨迹文件", new ImageIcon("/home/lzx/IdeaProjects/test/ico/open.png"));
    JMenu traceMenu = new JMenu("trace文件");
    JMenuItem traceItem = new JMenuItem("导入trace文件", new ImageIcon("/home/lzx/IdeaProjects/test/ico/open.png"));
    JMenuItem preTraceItem = new JMenuItem("导入预测trace文件", new ImageIcon("/home/lzx/IdeaProjects/test/ico/open.png"));
    JMenu helpMenu = new JMenu("帮助");
    JMenuItem helpItem = new JMenuItem("操作步骤", new ImageIcon("/home/lzx/IdeaProjects/test/ico/about.png"));
    //---------------------------------------------------------

    //----------------Top(Jpanel)------------------------------
    JPanel top = new JPanel();
    //LeftVerticalPanel(VerticalPanel)
    VerticalPanel leftVerticalPanel = new VerticalPanel("");
    JPanel leftDataPanel = new VerticalPanel("");
    JLabel sendPacketsLabel = new JLabel("发包数:");
    JLabel sendPackets = new JLabel("0");
    JPanel sendPacketsPanel = new JPanel();
    JLabel receivePacketsLabel = new JLabel("收包数:");
    JLabel receivePackets = new JLabel("0");
    JPanel receivePacketsPanel = new JPanel();
    JLabel packetReachRateLabel = new JLabel("包到达率:");
    JLabel packetReachRate = new JLabel("0.000");
    JPanel packetRechRatePanle = new JPanel();
    JLabel delay = new JLabel("平均时延:");
    JLabel delayData = new JLabel("0.000");
    JPanel delayPanel = new JPanel();
    Float delayd1 = new Float(0.0);
    //RightVerticalPanel(VerticalPanel)
    VerticalPanel  rightVerticalPanel = new VerticalPanel("");
    JPanel rightDataPanel = new VerticalPanel("");
    JLabel sendPacketsLabel2 = new JLabel("发包数:");
    JLabel sendPackets2 = new JLabel("0");
    JPanel sendPacketsPanel2 = new JPanel();
    JLabel receivePacketsLabel2 = new JLabel("收包数:");
    JLabel receivePackets2 = new JLabel("0");
    JPanel receivePacketsPanel2 = new JPanel();
    JLabel packetReachRateLabel2 = new JLabel("包到达率:");
    JLabel packetReachRate2 = new JLabel("0.000");
    JPanel packetReachRatePanel2 = new JPanel();
    JPanel delayPanel2 = new JPanel();
    JLabel delay2 = new JLabel("平均时延:");
    JLabel delayData2 = new JLabel("0.000");
    Float delayd2 = new Float(0.0);
    //---------------------------------------------------------

    //------------------Bottom(JPanel)-------------------------
    JPanel bottom = new JPanel();
    //Bottom_left(VertiaclPanel)
    VerticalPanel bottomLeft = new VerticalPanel("系统消息");
    JTextArea msgArea = new JTextArea(8,5);
//    JPanel bottomLeftLine1 = new JPanel();
//    JButton matrixA = new JButton("转移矩阵A");
//    JTextField matrixAFile = new JTextField(40);
//    JPanel bottomLeftLine2 = new JPanel();
//    JButton matrixB = new JButton("发射矩阵B");
//    JTextField matrixBFile = new JTextField(40);
//    JPanel bottomLeftLine3 = new JPanel();
//    JButton matrixPI = new JButton("初始概率PI");
//    JTextField matrixPIFile = new JTextField(40);
    //Bottom_right(JPanel)
    Box bottomRight = Box.createHorizontalBox();
    JButton goButton = new JButton("开始", new ImageIcon("/home/lzx/IdeaProjects/test/ico/go.jpeg"));
    JButton pauseButton = new JButton("暂停", new ImageIcon("/home/lzx/IdeaProjects/test/ico/pause.jpg"));
    JButton overButton = new JButton("结束", new ImageIcon("/home/lzx/IdeaProjects/test/ico/stop.jpg"));
    JButton ExportButton = new JButton("导出数据", new ImageIcon("/home/lzx/IdeaProjects/test/ico/dao.jpg"));
    JLabel thisTime = new JLabel("仿真时间:");
    JLabel timeLabel = new JLabel("0.0");
    VerticalPanel timePanel = new VerticalPanel("仿真时间");
    JPanel curTimePanel = new JPanel();
    JLabel preTimeLabel = new JLabel("预测时间:");
    JLabel preTime = new JLabel("0.5");
    JPanel preTimePanel = new JPanel();
    //---------------------------------------------------------

    HashMap<Integer, carPoint> carMap = new HashMap<>();
    HashMap<Integer, carPoint> carMap2 = new HashMap<>();
    Float maxKey = new Float(0);
    HashMap<Float, List<Mobility>> mobilityMap = new HashMap<>();
    HashMap<Float, List<Mobility>> mobilityMap2 = new HashMap<>();
    Float mapKey = new Float(0.0);
    Float mapKey2 = new Float(0.0);
    Timer timer;
    List<Mobility> carMoblity;
    List<Mobility> carMoblity2;
    ImageIcon carImage = new ImageIcon("/home/lzx/IdeaProjects/test/ico/yellowCar.png");
    String traceAddr = null;
    HashMap<Float, Time_trace> traceTimeMap = new HashMap<>();
    HashMap<Float, Time_trace> traceTimeMap2 = new HashMap<>();
    HashMap<Integer, Float> start_time = new HashMap<>();
    HashMap<Integer, Float> start_time2 = new HashMap<>();
    HashMap<Integer, Float> end_time = new HashMap<>();
    HashMap<Integer, Float> end_time2 = new HashMap<>();
    //图表部分
    List<Float> chartData = new ArrayList<>();
    List<Float> chartData2 = new ArrayList<>();
    Integer il = 0;
    //组装界面
    public void init(){
        msgArea.setText("基于平行空间车载网络性能计算系统\n");
        //----------------------菜单栏------------------------------
        fileMenu.add(openItem);
        fileMenu.add(exitItem);
        exitItem.addActionListener(event -> System.exit(0));
        trajectoryMenu.add(openTra);
        trajectoryMenu.add(preOpenTra);
        //trajectoryMenu.add(generateTra);
        traceMenu.add(traceItem);
        traceMenu.add(preTraceItem);
        // 以当前路径创建文件选择器
        JFileChooser chooser3 = new JFileChooser("/home/lzx/sumo_NS2");
        // 定义文件过滤器
        ExtensionFileFilter filter3 = new ExtensionFileFilter();
        filter3.addExtension("tr");
        filter3.setDescription("trace文件(*.tr)");
        chooser3.addChoosableFileFilter(filter3);
        chooser3.setAcceptAllFileFilterUsed(true);
        chooser3.setFileView(new FileIconView(filter3));
        traceItem.addActionListener(event ->{
            int result = chooser3.showDialog(jFrame, "导入trace文件");
            if(result == JFileChooser.APPROVE_OPTION){
                String name = chooser3.getSelectedFile().getPath();
                msgArea.append("已打开trace文件"+name+"\n");
                //---------------------------网络性能计算---------------------------------------
                TraceFileAnalysis traceA = new TraceFileAnalysis(name);
                traceA.init();
                traceTimeMap = traceA.getTraceTimeMap();
                start_time = traceA.getStart_time();
                end_time = traceA.getEnd_time();
                delayd1 = traceA.getDelay();
                //----------------------------------------------------------------------------
            }
        });
        helpItem.addActionListener(event -> {
            JOptionPane.showMessageDialog(jFrame, "1.导入路网文件\n2.导入轨迹文件\n3.导入trace文件\n" +
                            "4.点击开始运行"
                    ,"操作步骤", JOptionPane.INFORMATION_MESSAGE);
        });
        // 以当前路径创建文件选择器
        JFileChooser chooser4 = new JFileChooser("/home/lzx/del");
        // 定义文件过滤器
        ExtensionFileFilter filter4 = new ExtensionFileFilter();
        filter4.addExtension("tr");
        filter4.setDescription("trace文件(*.tr)");
        chooser4.addChoosableFileFilter(filter3);
        chooser4.setAcceptAllFileFilterUsed(true);
        chooser4.setFileView(new FileIconView(filter3));
        preTraceItem.addActionListener(event ->{
            int result = chooser4.showDialog(jFrame, "导入预测trace文件");
            if(result == JFileChooser.APPROVE_OPTION){
                String name = chooser4.getSelectedFile().getPath();
                System.out.println("导入预测trace文件"+name);
                msgArea.append("已打开预测trace文件"+name+"\n");
                //---------------------------网络性能计算---------------------------------------
                TraceFileAnalysis traceA = new TraceFileAnalysis(name);
                traceA.init();
                traceTimeMap2 = traceA.getTraceTimeMap();
                start_time2 = traceA.getStart_time();
                end_time2 = traceA.getEnd_time();
                delayd2 = traceA.getDelay();
                //----------------------------------------------------------------------------
            }
        });
        helpItem.addActionListener(event -> {
            JOptionPane.showMessageDialog(jFrame, "1.导入路网文件\n2.导入轨迹文件\n3.导入trace文件\n" +
                            "4.点击开始运行"
                    ,"操作步骤", JOptionPane.INFORMATION_MESSAGE);
        });


        helpMenu.add(helpItem);
        menuBar.add(fileMenu);
        menuBar.add(trajectoryMenu);
        menuBar.add(traceMenu);
        menuBar.add(helpMenu);
        jFrame.setJMenuBar(menuBar);
        MyJpanel drawAreaLeft = new MyJpanel();
        drawAreaLeft.setPreferredSize(new Dimension(900, 750));
        MyJpanel2 drawAreaRight = new MyJpanel2();
        drawAreaRight.setPreferredSize(new Dimension(900, 750));
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
            if(result == JFileChooser.APPROVE_OPTION){
                String name = chooser.getSelectedFile().getPath();
                msgArea.append("已打开路网文件"+name+"\n");
                NetFileAnalysis n = new NetFileAnalysis(name);
                n.init();
                List<Shape> shapeList = n.getShapList();
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
            if(result == JFileChooser.APPROVE_OPTION){
                String addr = chooser2.getSelectedFile().getPath();
                msgArea.append("已打开轨迹文件"+addr+"\n");
                MobilityAnalys ma = new MobilityAnalys(addr);
                ma.init();
                carMap = ma.getCarMap();
                mobilityMap = ma.getMobilityMap();
                Set<Float> set = mobilityMap.keySet();
                Object[] obj = set.toArray();
                Arrays.sort(obj);
                maxKey = (Float)obj[obj.length-1];
            }
        });
        // 以当前路径创建文件选择器
        JFileChooser chooser5 = new JFileChooser("/home/lzx/del");
        // 定义文件过滤器
        ExtensionFileFilter filter5 = new ExtensionFileFilter();
        filter5.addExtension("tcl");
        filter5.setDescription("轨迹文件(*.tcl)");
        chooser5.addChoosableFileFilter(filter2);
        chooser5.setAcceptAllFileFilterUsed(true);
        chooser5.setFileView(new FileIconView(filter2));
        preOpenTra.addActionListener(event -> {
            int result = chooser5.showDialog(jFrame, "打开预测轨迹文件");
            if(result == JFileChooser.APPROVE_OPTION){
                String addr = chooser5.getSelectedFile().getPath();
                System.out.println("打开预测轨迹文件"+addr);
                msgArea.append("已打开路网文件"+addr+"\n");
                MobilityAnalys pma = new MobilityAnalys(addr);
                pma.init();
                carMap2 = pma.getCarMap();
                mobilityMap2 = pma.getMobilityMap();
            }
        });

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                il = il +1;
                timeLabel.setText(mapKey.toString());
                if (mapKey > maxKey) {
                    Float r = (float)(Math.round(delayd1*1000))/1000;
                    String sr = r.toString();
                    while(sr.length() < 5)
                        sr = sr + "0";
                    delayData.setText(sr);
                    r = (float)(Math.round(delayd2*1000))/1000;
                    sr = r.toString();
                    while(sr.length() < 5)
                        sr = sr + "0";
                    delayData2.setText(sr);
                    msgArea.append("仿真结束\n");
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
                            if(il % 50 == 0){
                                chartData.add(r);
                            }
                        }
                    }
                    drawAreaLeft.repaint();
                    mapKey += (float)0.1;
                    mapKey = (float)(Math.round(mapKey*10))/10;
                    mapKey2 = mapKey + (float)0.4;
                    mapKey2 = (float)(Math.round(mapKey2*10))/10;
                }
                if (mapKey2 > maxKey){
                    System.out.println("预测已运行完毕");
                }else{
                    preTime.setText(mapKey2.toString());
                    carMoblity2 = mobilityMap2.get(mapKey2);
                    Time_trace t = traceTimeMap2.get(mapKey2);
                    if(t != null){
                        sendPackets2.setText(t.getSends().toString());
                        receivePackets2.setText(t.getReceives().toString());
                        if(t.getSends() != 0){
                            Float r = (float)t.getReceives()/t.getSends();
                            r = (float)(Math.round(r * 1000))/1000;
                            String sr = r.toString();
                            while(sr.length() < 5)
                                sr = sr+"0";
                            packetReachRate2.setText(sr);
                            if(il % 50 == 0){
                                chartData2.add(r);
                            }
                        }
                    }
                    drawAreaRight.repaint();
                }
            }
        };
        timer = new Timer(100, taskPerformer);
        //----------------------------------------------------------------------------

        top.setBorder(new TitledBorder(new EtchedBorder()
                , "" , TitledBorder.CENTER ,TitledBorder.TOP));
        //----------------------------LeftTop(VerticalPanel)------------------------------------
        leftVerticalPanel.setBorder(new TitledBorder(new EtchedBorder()
                , "实时仿真" , TitledBorder.CENTER ,TitledBorder.TOP));
        drawAreaLeft.setBorder(new TitledBorder(new EtchedBorder()
                , "" , TitledBorder.CENTER ,TitledBorder.TOP));
        leftVerticalPanel.add(drawAreaLeft);
        sendPacketsPanel.add(sendPacketsLabel);
        sendPacketsPanel.add(sendPackets);
        leftDataPanel.add(sendPacketsPanel);
        receivePacketsPanel.add(receivePacketsLabel);
        receivePacketsPanel.add(receivePackets);
        leftDataPanel.add(receivePacketsPanel);
        packetRechRatePanle.add(packetReachRateLabel);
        packetRechRatePanle.add(packetReachRate);
        leftDataPanel.add(packetRechRatePanle);
        delayPanel.add(delay);
        delayPanel.add(delayData);
        leftDataPanel.add(delayPanel);
        leftDataPanel.setBorder(new TitledBorder(new EtchedBorder()
                , "" , TitledBorder.CENTER ,TitledBorder.TOP));
        leftVerticalPanel.add(leftDataPanel);
        //----------------------------------------------------------------------------

        //----------------------------RightTop(VerticalPanel)-----------------------------------
        rightVerticalPanel.setBorder(new TitledBorder(new EtchedBorder()
                , "预测仿真" , TitledBorder.CENTER ,TitledBorder.TOP));
        drawAreaRight.setBorder(new TitledBorder(new EtchedBorder()
                , "" , TitledBorder.CENTER ,TitledBorder.TOP));
        rightVerticalPanel.add(drawAreaRight);
        sendPacketsPanel2.add(sendPacketsLabel2);
        sendPacketsPanel2.add(sendPackets2);
        rightDataPanel.add(sendPacketsPanel2);
        receivePacketsPanel2.add(receivePacketsLabel2);
        receivePacketsPanel2.add(receivePackets2);
        rightDataPanel.add(receivePacketsPanel2);
        packetReachRatePanel2.add(packetReachRateLabel2);
        packetReachRatePanel2.add(packetReachRate2);
        rightDataPanel.add(packetReachRatePanel2);
        delayPanel2.add(delay2);
        delayPanel2.add(delayData2);
        rightDataPanel.add(delayPanel2);
        rightDataPanel.setBorder(new TitledBorder(new EtchedBorder()
                , "" , TitledBorder.CENTER ,TitledBorder.TOP));
        rightVerticalPanel.add(rightDataPanel);
        //----------------------------------------------------------------------------

        //---------------------------Bottom_left(JPanel)------------------------------
        JFileChooser chooserA = new JFileChooser("/home/lzx/test");
        ExtensionFileFilter filterA = new ExtensionFileFilter();
        filterA.addExtension("txt");
        filterA.setDescription("转移矩阵(*.txt)");
        chooserA.addChoosableFileFilter(filterA);
        chooserA.setAcceptAllFileFilterUsed(true);
        chooserA.setFileView(new FileIconView(filterA));
//        matrixA.addActionListener(event -> {
//            int result = chooserA.showDialog(jFrame, "打开转移矩阵A");
//            if(result == JFileChooser.APPROVE_OPTION){
//                String name = chooserA.getSelectedFile().getPath();
//                matrixAFile.setText(name);
//            }
//        });
//        matrixAFile.setEditable(false);
//        bottomLeftLine1.add(matrixA);
//        bottomLeftLine1.add(matrixAFile);
//        JFileChooser chooserB = new JFileChooser("/home/lzx/test");
//        ExtensionFileFilter filterB = new ExtensionFileFilter();
//        filterB.addExtension("txt");
//        filterB.setDescription("发射矩阵(*.txt)");
//        chooserB.addChoosableFileFilter(filterB);
//        chooserB.setAcceptAllFileFilterUsed(true);
//        chooserB.setFileView(new FileIconView(filterB));
//        matrixB.addActionListener(event -> {
//            int result = chooserB.showDialog(jFrame, "打开发射矩阵B");
//            if(result == JFileChooser.APPROVE_OPTION){
//                String name = chooserB.getSelectedFile().getPath();
//                matrixBFile.setText(name);
//            }
//        });
//        matrixBFile.setEditable(false);
//        bottomLeftLine2.add(matrixB);
//        bottomLeftLine2.add(matrixBFile);
//        JFileChooser chooserPI = new JFileChooser("/home/lzx/test");
//        ExtensionFileFilter filterPI = new ExtensionFileFilter();
//        filterPI.addExtension("txt");
//        filterPI.setDescription("发射矩阵(*.txt)");
//        chooserPI.addChoosableFileFilter(filterPI);
//        chooserPI.setAcceptAllFileFilterUsed(true);
//        chooserPI.setFileView(new FileIconView(filterPI));
//        matrixPI.addActionListener(event -> {
//            int result = chooserPI.showDialog(jFrame, "打开初始矩阵PI");
//            if(result == JFileChooser.APPROVE_OPTION){
//                String name = chooserPI.getSelectedFile().getPath();
//                matrixPIFile.setText(name);
//            }
//        });
//        matrixPIFile.setEditable(false);
//        bottomLeftLine3.add(matrixPI);
//        bottomLeftLine3.add(matrixPIFile);
//        bottomLeft.add(bottomLeftLine1);
//        bottomLeft.add(bottomLeftLine2);
//        bottomLeft.add(bottomLeftLine3);
        msgArea.setEditable(false);
        bottomLeft.add(msgArea);
        //----------------------------------------------------------------------------

        //---------------------------Bottom_right(JPanel)-----------------------------
        bottomRight.add(goButton);
        goButton.addActionListener(event -> {
            timer.start();
        });
        bottomRight.add(pauseButton);
        pauseButton.addActionListener(event -> {
            if(timer.isRunning()){
                timer.stop();
            }
            else{
                timer.start();
            }
        });
        //bottomRight.add(ExportButton);
        ExportButton.addActionListener(event -> {
            if(timer.isRunning() || timeLabel.getText() == "0.0"){
                //弹出警告框
                JOptionPane.showMessageDialog(jFrame, "请在仿真结束后执行此操作"
                        ,"此时不可导出数据", JOptionPane.ERROR_MESSAGE);
            }
            else{
                ChartTool chartTool = new ChartTool();
                String[] rowKeys = {"实时", "预测"};
                chartTool.setRowKeys(rowKeys);
                Float[] data1 = chartData.toArray(new Float[chartData.size()]);
                Float[] data2 = chartData2.toArray(new Float[chartData2.size()]);
                double[][] data = new double[2][20];
                for(int i=0; i<data1.length; i++){
                    data[0][i] = data1[i];
                }
                for(int i=0; i<data2.length; i++){
                    data[1][i] = data2[i];
                }
                chartTool.setData(data);
                chartTool.go();
            }
        });
        bottomRight.add(overButton);
        curTimePanel.add(thisTime);
        curTimePanel.add(timeLabel);
        timePanel.setBorder(new TitledBorder(new EtchedBorder()
                , "仿真时间" , TitledBorder.CENTER ,TitledBorder.TOP));
        timePanel.add(curTimePanel);
        preTimePanel.add(preTimeLabel);
        preTimePanel.add(preTime);
        timePanel.add(preTimePanel);
        bottom.add(timePanel);
        bottomRight.setBorder(new TitledBorder(new EtchedBorder()
                , "控制面板" , TitledBorder.CENTER ,TitledBorder.TOP));
        overButton.addActionListener(event -> System.exit(0));
        //----------------------------------------------------------------------------

        //-------------------------------Bottom---------------------------------------
        bottom.setLayout(new GridLayout(1,2));
        bottom.add(bottomLeft);
        bottom.add(bottomRight);
        bottom.setBorder(new TitledBorder(new EtchedBorder()
                , "" , TitledBorder.CENTER ,TitledBorder.TOP));
        //----------------------------------------------------------------------------

        //-------------------------------Box------------------------------------------
        top.add(leftVerticalPanel);
        top.add(rightVerticalPanel);
        jFrame.add(top);
        jFrame.add(bottom, BorderLayout.SOUTH);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle bounds = new Rectangle(screenSize);
        jFrame.setBounds(bounds);
        jFrame.setVisible(true);
        //----------------------------------------------------------------------------
    }
    public static void main(String[] args){
        new DialogTest().init();
    }

    class MyJpanel extends JPanel{
        List<Shape> shapeList = null;
        public MyJpanel(){
            this.shapeList = null;
        }
        public MyJpanel(List<Shape> shapeName){
            this.shapeList = shapeName;
        }
        public void setShapList(List<Shape> shapeName){
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
                    g2d.drawString(""+m.getId(), (int)m.getX()+8, (int)m.getY()+8);
                }
            }
        }
    }

    class MyJpanel2 extends JPanel{
        List<Shape> shapeList = null;
        public MyJpanel2(){
            this.shapeList = null;
        }
        public MyJpanel2(List<Shape> shapeName){
            this.shapeList = shapeName;
        }
        public void setShapList(List<Shape> shapeName){
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
            if (carMoblity2 != null) {
                for (Mobility m : carMoblity2) {
                    g2d.setColor(Color.red);
                    g2d.drawImage(carImage.getImage(), (int)m.getX(), (int) m.getY(),
                            carImage.getIconWidth(), carImage.getIconHeight(), null);
                    g2d.drawString(""+m.getId(), (int)m.getX()+8, (int)m.getY()+8);
                }
            }
        }
    }
}

class VerticalPanel extends JPanel {
    public VerticalPanel(String title){
        setBorder(new TitledBorder(new EtchedBorder()
                , title , TitledBorder.CENTER ,TitledBorder.TOP));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
