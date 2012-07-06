package mousemenu;

/**
 * Created with IntelliJ IDEA.
 * User: ipaezana
 * Date: 6/25/12
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;

import java.util.*;

/**
 * A simple demonstration application showing how to create a line chart using
 * data from a {@link CategoryDataset}.
 */
public class GraphChart extends ApplicationFrame {
    /**
     * Creates a new demo.
     *
     * @param title the frame title.
     */
    public GraphChart(String title, Map<String, Vector> mapValues) {
   // public GraphChart(String title) {
        super(title);
       CategoryDataset dataset = createDataset(mapValues);
       // CategoryDataset dataset = createDataset();
       JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }
    /**
     * Creates a sample dataset.
     *
     * @return The dataset.
     */
    public static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
       dataset.addValue(212, "Classes", "JDK 1.0");
        dataset.addValue(504, "Classes", "JDK 1.1");
        dataset.addValue(1520, "Classes", "SDK 1.2");
        dataset.addValue(1842, "Classes", "SDK 1.3");
        dataset.addValue(2991, "Classes", "SDK 1.4");
        return dataset;
    }

    public static CategoryDataset createDataset(Map<String, Vector> mapValues) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
         //  System.out.println("BBBBiiiii");

        for (String keys : mapValues.keySet()) {
            System.out.println("XXX - Node=" +keys);
            Vector values = mapValues.get(keys);
            for (int i=0; i < values.size(); i++) {
                System.out.println("values["+i+"]=" +values.get(i));
                dataset.addValue(Integer.parseInt(values.get(i).toString()), keys, String.valueOf(i));

            }

        }

        /* for(int j = 0; j <vector.length; j++){
int[] values = vector[j];
for (int i=0; i < values.length; i++) {
// for (int j=0; j<values.length; j++) {
//System.out.println("values["+i+"]["+j+"]=" +values[i][j]+", name="+name+ ", i="+i+", j="+j);
System.out.println("values["+i+"]=" +values[i]+", Node="+j+ ", i="+i);


// }
}
}
        */
//        dataset.addValue(212, "Classes", "JDK 1.0");
//        dataset.addValue(504, "Classes", "JDK 1.1");
//        dataset.addValue(1520, "Classes", "SDK 1.2");
//        dataset.addValue(99, "Classes", "0");
//        dataset.addValue(98, "Classes", "1");
//        dataset.addValue(97, "Classes", "2");
//        dataset.addValue(96, "Classes", "3");
//        dataset.addValue(95, "Classes", "4");
//        dataset.addValue(94, "Classes", "5");
//        dataset.addValue(93, "Classes", "6");
//        dataset.addValue(92, "Classes", "7");
//        dataset.addValue(91, "Classes", "8");
//        dataset.addValue(90, "Classes", "9");

        return dataset;
    }
    /**
     * Creates a sample chart.
     *
     * @param dataset a dataset.
     *
     * @return The chart.
     */
    public static JFreeChart createChart(CategoryDataset dataset) {
// create the chart...
        JFreeChart chart = ChartFactory.createLineChart(
                "Life Spam of the Nodes", // chart title
                "Days", // domain axis label
                "Power", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips
                false // urls
        );
        chart.addSubtitle(new TextTitle("Life Spam of the Nodes"));
        TextTitle source = new TextTitle(
                "Source:  "
                        + "by ... (INRIA)"
        );
        source.setFont(new Font("SansSerif", Font.PLAIN, 10));
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(source);
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
// customise the range axis...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
// customise the renderer...
        LineAndShapeRenderer renderer
                = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setFillPaint(Color.white);
        return chart;
    }
    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }
    /**
     * Starting point for the demonstration application.
     *
     * @param args ignored.
     */
  /*  public static void main(String[] args) {
        GraphChart demo = new GraphChart("Line Chart Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }      */
}