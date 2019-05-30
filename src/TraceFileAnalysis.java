import org.omg.PortableInterceptor.INACTIVE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TraceFileAnalysis {
    String addr = null;
    HashMap<Float, Time_trace> traceTimeMap = new HashMap<>();
    HashMap<Integer, Float> start_time = new HashMap<>();
    HashMap<Integer, Float> end_time = new HashMap<>();
    Float avg_delay = new Float(0.0);

    public void init() {
        try {
            FileReader fr = new FileReader(addr);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            Integer highest_packet_id = 0;
            Integer sends = 0;
            Integer receives = 0;
            Integer routing_packets = 0;
            Float first_received_time = new Float(0);
            Integer first = 0;
            Float end_to_end_delay = new Float(0);
            Float avg_end_to_delay = new Float(0);
            Float pdfraction = new Float(0);
            Float hold = new Float(0.0);
            while ((line = br.readLine()) != null) {
                if (line.length() > 70) {
                    String tarr[] = line.split(" ");
                    String action = tarr[0];
                    if (action.equals("s") || action.equals("r") || action.equals("f")) {
                        Float time = Float.parseFloat(tarr[1]);
                        if (time - hold < 1) {
                            Integer packet_id = Integer.parseInt(tarr[6]);
                            String trace = tarr[3];
                            String type = tarr[7];
                            //记录发送数据包数量
                            if (action.equals("s") && trace.equals("AGT") && type.equals("tcp")) {
                                sends++;
                            }
                            //记录目前系统中处理最高数据包的ID
                            if (packet_id > highest_packet_id) {
                                highest_packet_id = packet_id;
                            }
                            //记录数据包的传送时间
                            if (!start_time.keySet().contains(packet_id)) {
                                start_time.put(packet_id, time);
                            }
                            //记录收到的数据包个数及数据包的接收时间
                            if (action.equals("r") && trace.equals("AGT") && type.equals("tcp")) {
                                if (first == 0) {
                                    first_received_time = time;
                                    first = 1;
                                }
                                receives++;
                                end_time.put(packet_id, time);
                            } else {
                                end_time.put(packet_id, Float.parseFloat("-1"));
                            }
                        } else {
                            Time_trace t = new Time_trace(sends, receives, highest_packet_id);
                            traceTimeMap.put(hold, t);
                            hold = hold + (float) 0.1;
                            //保留小数点后一位
                            hold = (float) (Math.round(hold * 10)) / 10;
                        }
                    }
                }
            }
            //计算有效数据包的端到端时延
            for (Integer i = 0; i <= highest_packet_id; i++) {
                if (start_time.keySet().contains(i) && end_time.keySet().contains(i)) {
                    Float packet_duration = end_time.get(i) - start_time.get(i);
                    if (packet_duration > 0) {
                        end_to_end_delay += packet_duration;
                    }
                }
            }
            //平均时延
            avg_end_to_delay = end_to_end_delay / receives;
            //数据包到达比例
            pdfraction = (receives.floatValue() / sends) * 100;
            //输出
            System.out.println(" Total packet sends: " + sends);
            System.out.println(" Total packet receives: " + receives);
            System.out.println(" Packet delivery fraction: " + pdfraction);
            System.out.println(" Average End-to-End delay: " + avg_end_to_delay);
            System.out.println(" first packet received time: " + first_received_time);
            avg_delay = avg_end_to_delay;
        } catch (IOException e) {
            System.out.println("trace文件错误");
        }
    }

    public HashMap<Float, Time_trace> getTraceTimeMap() {
        return traceTimeMap;
    }

    public HashMap<Integer, Float> getEnd_time() {
        return end_time;
    }

    public HashMap<Integer, Float> getStart_time() {
        return start_time;
    }

    public TraceFileAnalysis(String addr) {
        this.addr = addr;
    }

    public TraceFileAnalysis() {
        this.addr = null;
    }

    public Float getDelay(){
        return avg_delay;
    }
}

class Time_trace {
    Integer sends;
    Integer receives;
    Integer highest_packet_id;

    public Time_trace(Integer sends, Integer receives, Integer highest_packet_id) {
        this.sends = sends;
        this.receives = receives;
        this.highest_packet_id = highest_packet_id;
    }

    public Integer getSends() {
        return sends;
    }

    public void setSends(Integer sends) {
        this.sends = sends;
    }

    public Integer getReceives() {
        return receives;
    }

    public void setReceives(Integer receives) {
        this.receives = receives;
    }

    public Integer getHighest_packet_id() {
        return highest_packet_id;
    }

    public void setHighest_packet_id(Integer highest_packet_id) {
        this.highest_packet_id = highest_packet_id;
    }
}
