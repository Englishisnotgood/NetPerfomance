import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class xmlTest {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory bdf = DocumentBuilderFactory.newInstance();
        DocumentBuilder bd = bdf.newDocumentBuilder();
        Document document = bd.parse(new File("/home/lzx/sumo_net_learn/book"));
        NodeList list = document.getElementsByTagName("book");
        System.out.println("测试结果：");
        for (int i = 0; i < list.getLength(); i++){
            Element element = (Element)list.item(i);
            String name = element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
            System.out.println(name);
            String year = element.getElementsByTagName("year").item(0).getFirstChild().getNodeValue();
            System.out.println(year);
            System.out.println("--------------");
        }
    }
}
