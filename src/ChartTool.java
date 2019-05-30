import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChartTool {
    String[] rowKeys = {"A平台"};
    String[] colKeys = {"0", "5", "10", "15", "20", "25", "30", "35", "40", "45",
            "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100"};
    double[][] data = {{4, 3, 1, 1, 1, 1, 2, 2, 2, 1, 8, 2, 1, 1, 2, 1, 8, 2, 1, 1}};

    public String[] getRowKeys() {
        return rowKeys;
    }

    public void setRowKeys(String[] rowKeys) {
        this.rowKeys = rowKeys;
    }

    public String[] getColKeys() {
        return colKeys;
    }

    public void setColKeys(String[] colKeys) {
        this.colKeys = colKeys;
    }

    public double[][] getData() {
        return data;
    }

    public void setData(double[][] data) {
        this.data = data;
    }

    public void go(){
        CategoryDataset dataset = createDataset();
        JFreeChart freeChart = createChart(dataset);
        saveAsFile(freeChart, "/home/lzx/IdeaProjects/test/src/char.jpg", 800, 400);
    }

    public CategoryDataset createDataset() {
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
    }
    // 根据CategoryDataset创建JFreeChart对象
    public JFreeChart createChart(CategoryDataset categoryDataset) {
        // 创建JFreeChart对象：ChartFactory.createLineChart
        JFreeChart jfreechart = ChartFactory.createLineChart("不同类别按小时计算拆线图", // 标题
                "年分", // categoryAxisLabel （category轴，横轴，X轴标签）
                "数量", // valueAxisLabel（value轴，纵轴，Y轴的标签）
                categoryDataset, // dataset
                PlotOrientation.VERTICAL, true, // legend
                false, // tooltips
                false); // URLs
        // 使用CategoryPlot设置各种参数。以下设置可以省略。
        CategoryPlot plot = (CategoryPlot)jfreechart.getPlot();
        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);
        // 前景色 透明度
        plot.setForegroundAlpha(0.5f);
        // 其他设置 参考 CategoryPlot类
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
        renderer.setBaseShapesVisible(true); // series 点（即数据点）可见
        renderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见
        renderer.setUseSeriesOffset(true); // 设置偏移量
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        return jfreechart;
    }

    // 保存为文件
    public void saveAsFile(JFreeChart chart, String outputPath,
                                  int weight, int height) {
        FileOutputStream out = null;
        try {
            File outFile = new File(outputPath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(outputPath);
            // 保存为PNG
            // ChartUtilities.writeChartAsPNG(out, chart, 600, 400);
            // 保存为JPEG
            ChartUtilities.writeChartAsJPEG(out, chart, 800, 400);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }
}
