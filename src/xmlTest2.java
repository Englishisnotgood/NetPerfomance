import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class xmlTest2 {

    public static void main(String[] args){
        try {
            InputStream xmlInputStream = getXmlInputStream("/home/lzx/sumo_net_learn/xmltest");
            Element rootElement = getRootElementFromIs(xmlInputStream);
            parseElementFromRoot(rootElement);
        }
        catch (FileNotFoundException e){
            System.out.println("文件未找到");
        }
        catch (Exception e){
            System.out.println("未知文件错误");
        }
    }

    //获取xml-io流
    private static InputStream getXmlInputStream(String xmlPath) {
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(xmlPath);
        } catch (Exception e){
            e.printStackTrace();
        }
        return inputStream;
    }

    //解析xml-io流，获取document对象，以及document对象的根节点
    private static Element getRootElementFromIs(InputStream inputStream) throws Exception {
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
    private static void parseElementFromRoot(Element root){
        NodeList n1 = root.getChildNodes();
        for (int i = 0; i < n1.getLength(); i++){
            Node node = n1.item(i);
            if(node instanceof Element){
                Element ele = (Element)node;
                //从元素解析得到属性值
                getDataFromElement(ele);
                //从元素解析特定子元素病解析
                getCertainElementFromParentElement(ele);
            }
        }
    }

    //从元素解析得到属性值
    private static void getDataFromElement(Element ele) {
        String tagName = ele.getTagName();
        System.out.println("tagName == " + tagName);
        String name = ele.getAttribute("name1");
        System.out.println("name == " + name);
        String className = ele.getAttribute("class");
        System.out.println("className == " + className);
    }

    //从元素解析特定子元素并解析
    private static void getCertainElementFromParentElement(Element ele){
        NodeList propertyEleList = ele.getElementsByTagName("property");
        for (int i=0; i<propertyEleList.getLength(); i++) {
            Node node = propertyEleList.item(i);
            if(node instanceof Element){
                Element prpertyEle = (Element)node;
                String name = prpertyEle.getAttribute("name");
                System.out.println("propertyEle : name == " + name);
                String value = prpertyEle.getAttribute("value");
                System.out.println("propertyEle : vale == "+ value);
            }
        }
    }
}
