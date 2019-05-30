/**
 * 解析.net.xml文件
 * 实例化此类，传入文件路径做参数
 * 调用init方法，完成解析
 * 调用getshaplist方法，返回shaplist列表
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NetFileAnalysis {
    private String fileAddress = null;
    private List<Shape> shapeList = new ArrayList<>();
    //车道线List
    private List<myLine> myLineList = new ArrayList<>();
    //车道idList
    private List<String> edgeIDList = new ArrayList<>();

    public NetFileAnalysis(String fileAddress){
        this.fileAddress = fileAddress;
    }

    public void init(){
        try {
            InputStream xmlInputStream = getXmlInputStream(fileAddress);
            Element rootElement = getRootElementFromIs(xmlInputStream);
            parseElementFromRoot(rootElement);
        }
        catch(FileNotFoundException e){
                System.out.println("文件未找到");
            }
        catch(Exception e){
                System.out.println("未知文件错误");
            }
    }
    public List<Shape> getShapList(){
        return this.shapeList;
    }

    //获取xml-io流
    private InputStream getXmlInputStream(String xmlPath) {
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(xmlPath);
        } catch (Exception e){
            e.printStackTrace();
        }
        return inputStream;
    }
    //解析xml-io流，获取document对象，以及document对象的根节点
    private Element getRootElementFromIs(InputStream inputStream) throws Exception {
        if(inputStream == null){
            return null;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        Element root = doc.getDocumentElement();
        if(inputStream != null){
            inputStream.close();
        }
        return root;
    }
    //从根元素解析得到元素
    private void parseElementFromRoot(Element root){
        NodeList n1 = root.getChildNodes();
        for (int i = 0; i < n1.getLength(); i++){
            Node node = n1.item(i);
            if(node instanceof Element){
                Element ele = (Element)node;
                if((ele.getTagName() == "edge") && !(ele.getAttribute("function").equals("internal"))) {
                    String EdgeId = ele.getAttribute("id");
                    edgeIDList.add(EdgeId);
                    System.out.println("EdgeId == " + EdgeId);
                    //从元素解析特定子元素并解析
                    fromEdgeGetLane(ele);
                }
            }
        }
        //调用画图方法
    }
    //从元素解析特定子元素并解析
    private void fromEdgeGetLane(Element ele){
        NodeList propertyEleList = ele.getElementsByTagName("lane");
        for (int i=0; i<propertyEleList.getLength(); i++) {
            Node node = propertyEleList.item(i);
            if(node instanceof Element){
                Element propertyEle = (Element)node;
                String laneId = propertyEle.getAttribute("id");
                String laneShape = propertyEle.getAttribute("shape");
                System.out.println(laneShape);
                String twoPoints[] = laneShape.split(" ");
                List<Float> value = new ArrayList<Float>();
                for (int j = 0; j<twoPoints.length; j++){
                    String t[] = twoPoints[j].split(",");
                    value.add(Float.parseFloat(t[0])/4);
                    value.add(Float.parseFloat(t[1])/4);
                }
                for (int k=0; k<value.size(); k++){
                    if(value.get(k) < 0)
                        value.set(k, (float)(0.0));
                }
                for(int l=0; l<value.size()-3; l+=2){
                    myLine tLine = new myLine((value.get(l)+10), (600-value.get(l+1)), (value.get(l+2)+10), (600-value.get(l+3)));
                    myLineList.add(tLine);
                }
                for(int z=0; z<myLineList.size(); z++){
                    Shape tshape = new Line2D.Float(myLineList.get(z).getX1(), myLineList.get(z).getY1(), myLineList.get(z).getX2(), myLineList.get(z).getY2());
                    shapeList.add(tshape);
                }
            }
        }
    }
}
