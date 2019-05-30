import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrawCarTest {

    public static void main(String[] args) {
        List<String> dingYiList = new ArrayList<>();
        List<String> yiDongList = new ArrayList<>();
        HashMap<Integer, carPoint> carMap = new HashMap<>();
        List<Mobility> mobilityList = new ArrayList<>();
        HashMap<String, List<Mobility>> mobilityMap_time_moblity = new HashMap<>();
        try {
            FileReader fr = new FileReader("/home/lzx/sumo_NS2/qingnian/mobility.tcl");
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while((line=br.readLine())!=null) {
                if(line.length() > 30) {
                    //看这个点是否在里面，如果不在就new一个car加进去
                    String tarr[] = line.split(" ");
                    String time = tarr[2];
                    int id = 0;
                    for (int i = tarr[3].indexOf("(")+1; i <tarr[3].indexOf(")") ; i++) {
                        id = id*10 + tarr[3].charAt(i)-'0';
                    }
                    float x = Float.parseFloat(tarr[5]);
                    float y = Float.parseFloat(tarr[6]);

                    Mobility m = new Mobility(Float.parseFloat(time), id, x, y);
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
                        carPoint tcar = new carPoint(id, x, y, Float.parseFloat(time));
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
        for(String key : mobilityMap_time_moblity.keySet()){
            System.out.println("Key:" + key + " value:" + mobilityMap_time_moblity.get(key));
        }
    }
}
