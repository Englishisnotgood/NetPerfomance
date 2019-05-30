import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * NS2轨迹文件解析类
 * new一个对象之后，调用init方法，读取文件并构造两个Map
 * 调用getcarMap和getMobilityMap分别获得文件解析后的Map
 * carMap是以车的id为键，车结点类为值的Map
 * mobilityMap是以时间为key，移动结点列表为value的Map
 */

public class MobilityAnalys {
    HashMap<Integer, carPoint> carMap = new HashMap<>();
    List<Mobility> mobilityList = new ArrayList<>();
    HashMap<Float, List<Mobility>> mobilityMap_time_moblity = new HashMap<>();
    private String addr = null;
    public MobilityAnalys(String addr){
        this.addr = addr;
    }

    public void init(){
        try {
            FileReader fr = new FileReader(addr);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while((line=br.readLine())!=null) {
                if(line.length() > 30) {
                    //看这个点是否在里面，如果不在就new一个car加进去
                    String tarr[] = line.split(" ");
                    Float time = Float.parseFloat(tarr[2]);
                    int id = 0;
                    for (int i = tarr[3].indexOf("(")+1; i <tarr[3].indexOf(")") ; i++) {
                        id = id*10 + tarr[3].charAt(i)-'0';
                    }
                    //随道路的坐标偏移和缩放做的坐标变换
                    float x = Float.parseFloat(tarr[5])/4 + 10;
                    float y = 600 - Float.parseFloat(tarr[6])/4;

                    Mobility m = new Mobility(time, id, x, y);
                    mobilityList.add(m);
                    if(mobilityMap_time_moblity.keySet().contains(time)){
                        mobilityMap_time_moblity.get(time).add(m);
                    }
                    else{
                        List<Mobility> m2 = new ArrayList<>();
                        m2.add(m);
                        mobilityMap_time_moblity.put(time, m2);
                    }

                    if(carMap.keySet().contains(id)){
                        //Map中已经有这辆车，调用移动函数

                    }
                    else{
                        carPoint tcar = new carPoint(id, x, y, time);
                        carMap.put(id, tcar);
                    }
                }
                else{
                    //yiDongList.add(line);
                    //小于30个字符长度，是定义语句，直接跳过
                }
            }
        }
        catch (IOException e){
            System.out.println("文件读取错误");
        }
    }

    public HashMap<Float, List<Mobility>> getMobilityMap(){
        return mobilityMap_time_moblity;
    }

    public HashMap<Integer, carPoint> getCarMap(){
        return carMap;
    }

}
