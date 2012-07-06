/*
 * EditorMouseMenu.java
 *
 * Created on March 21, 2007, 10:34 AM; Updated May 29, 2007
 *
 * Copyright 2007 Grotto Networking
 */
package mousemenu;

/**
 * Created with IntelliJ IDEA.
 * User: ipaezana
 * Date: 6/14/12
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
import edu.uci.ics.jung.algorithms.generators.random.EppsteinPowerLawGenerator;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.shortestpath.BFSDistanceLabeler;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import org.apache.commons.collections15.Transformer;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RefineryUtilities;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.*;

import static mousemenu.GraphElements.MyVertex;
import static mousemenu.GraphElements.MyEdge;
import static mousemenu.GraphChart.createDataset;

/**
 * Illustrates the use of custom edge and vertex classes in a graph editing application.
 * Demonstrates a new graph mouse plugin for bringing up popup menus for vertices and
 * edges.
 * @author Dr. Greg M. Bernstein
 */
public class EditorMouseMenu extends JPanel {


    /**
     * Starting vertex
     */
    private String mFrom;

    /**
     * Ending vertex
     */
    private String mTo;
  //  private Graph<String,Number> mGraph;
    private Set<String> mPred;
    private Set<String> mDataCollectors;

    //Properties
    private static JFrame frame = new JFrame("Smart Forest Use Case");
    private static SparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge> mGraph;

    public static javax.swing.JFormattedTextField powerFormattedTextField;

    public static GraphChart demo;

    public static int numberNodes = 0;
    public static int numberLinks = 0;


    public EditorMouseMenu(){
      // this.mGraph = getGraph();
        this.mGraph = new SparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge>();
        final Layout<GraphElements.MyVertex, GraphElements.MyEdge> layout = new StaticLayout(mGraph);
        final VisualizationViewer<GraphElements.MyVertex, GraphElements.MyEdge> vv = new VisualizationViewer<GraphElements.MyVertex,GraphElements.MyEdge>(layout);

        layout.setSize(new Dimension(600,600));
        vv.setPreferredSize(new Dimension(650,650));



     //   vv.getRenderContext().setVertexDrawPaintTransformer(new MyVertexDrawPaintFunction<String>());

   //     vv.getRenderContext().setVertexFillPaintTransformer(new MyVertexFillPaintFunction<String>());


        // Setup up a new vertex to paint transformer...
        Transformer<GraphElements.MyVertex,Paint> vertexPaint = new Transformer<GraphElements.MyVertex,Paint>() {
            @Override
            public Paint transform(MyVertex graphElements) {
                if(graphElements.isDataCollector()){
                    return Color.GREEN;  //To change body of implemented methods use File | Settings | File Templates.
                } else {
                    return Color.RED;
                }

            }
        };
      //  repaint();
        // Set up a new stroke Transformer for the edges
        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
            public Stroke transform(String s) {
                return edgeStroke;
            }
        };
        // Change color for nodes DataCollectors
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);


        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        // Create a graph mouse and add it to the visualization viewer
        EditingModalGraphMouse gm = new EditingModalGraphMouse(vv.getRenderContext(),
                GraphElements.MyVertexFactory.getInstance(),
                GraphElements.MyEdgeFactory.getInstance());
        // Set some defaults for the Vertex...
        GraphElements.MyVertexFactory.setDefaultFrequency(4);
        GraphElements.MyVertexFactory.setDefaultPower(1000);
        // Set some defaults for the Edges...
        GraphElements.MyEdgeFactory.setDefaultCommunicationCost(1.0);
        GraphElements.MyEdgeFactory.setDefaultCommunicationSize(5.0);
        // Trying out our new popup menu mouse plugin...
        PopupVertexEdgeMenuMousePlugin myPlugin = new PopupVertexEdgeMenuMousePlugin();
        // Add some popup menus for the edges and vertices to our mouse plugin.
        JPopupMenu edgeMenu = new MyMouseMenus.EdgeMenu(frame);
        JPopupMenu vertexMenu = new MyMouseMenus.VertexMenu(frame);
        myPlugin.setEdgePopup(edgeMenu);
        myPlugin.setVertexPopup(vertexMenu);
        gm.remove(gm.getPopupEditingPlugin());  // Removes the existing popup editing plugin

        gm.add(myPlugin);   // Add our new plugin to the mouse

        vv.setGraphMouse(gm);


        //JFrame frame = new JFrame("Editing and Mouse Menu Demo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(vv);

        // Let's add a menu for changing mouse modes
        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = gm.getModeMenu();
        modeMenu.setText("Mouse Mode");
        modeMenu.setIcon(null); // I'm using this in a main menu
        modeMenu.setPreferredSize(new Dimension(100,20)); // Change the size so I can see the text
        menuBar.add(modeMenu);
        this.frame.setJMenuBar(menuBar);
        gm.setMode(ModalGraphMouse.Mode.EDITING); // Start off in editing mode


        setLayout(new BorderLayout());
        add(vv, BorderLayout.CENTER);
        // set up controls
        add(setUpControls(), BorderLayout.SOUTH);
    }



    /**
     * @author danyelf
     */
    public class MyVertexDrawPaintFunction<V> implements Transformer<V,Paint> {

        public Paint transform(V v) {
            return Color.black;
        }

    }

    public class MyVertexFillPaintFunction<V> implements Transformer<V,Paint> {

        public Paint transform( V v ) {
            if ( v == mFrom) {
                return Color.BLUE;
            }
            if ( v == mTo ) {
                return Color.BLUE;
            }
            if ( mPred == null ) {
                return Color.LIGHT_GRAY;
            } else {
                if ( mPred.contains(v)) {
                    return Color.RED;
                } else {
                    return Color.LIGHT_GRAY;
                }
            }
        }

    }

    /**
     *
     */
    private static JPanel setUpControls() {

        JButton simulate = new JButton("Simulate");

        powerFormattedTextField = new JFormattedTextField();
        powerFormattedTextField.setColumns(3);
        JPanel jp = new JPanel();
        jp.setBackground(Color.WHITE);
        jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
        jp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
     //   jp.add(new JLabel("Set number of days to simulate:"));
        JPanel jp2 = new JPanel();
        jp2.add(new JLabel("Set number of days to simulate:", SwingConstants.LEFT));
        jp2.add(powerFormattedTextField);
        jp2.setBackground(Color.white);
        JPanel jp3 = new JPanel();
     //   jp3.add(new JLabel("vertex to", SwingConstants.LEFT));
        jp3.add(simulate);
       // powerFormattedTextField.addActionListener();
        simulate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getVertexCount();
            }
        });
        jp3.setBackground(Color.white);
        jp.add( jp2 );
        jp.add( jp3 );
        return jp;
    }

    public static Number calcWeightedShortestPath(GraphElements.MyVertex nodeFrom, GraphElements.MyVertex nodeTo) {
        Transformer<GraphElements.MyEdge, Double> wtTransformer = new Transformer<GraphElements.MyEdge,Double>() {
            public Double transform(GraphElements.MyEdge link) {
                return link.communicationCost;
            }
        };
        DijkstraShortestPath<MyVertex,MyEdge> alg = new DijkstraShortestPath(mGraph, wtTransformer);
        //alg.get
        List<MyEdge> l = alg.getPath(nodeFrom, nodeTo);
        Number dist = alg.getDistance(nodeFrom, nodeTo);
        System.out.println("The length of the shortest path from " + nodeFrom + " to " + nodeTo + " is:"+ dist); //Is working PERFECT!
        System.out.println(l.toString());
        System.out.println("and the length of the path is: " + dist);
        return dist;
    }

    public static void getVertexCount(){
        int noDataCollectors = 0;
     //   Array vector = new Vector();

        Map<String, String> dataCollectors = new HashMap<String, String>();
        java.util.List<String> dcList = new ArrayList<String>();
        Map<String, Integer> distanceToDC = new HashMap<String, Integer>(); //of all nodes to the shortest Data Collector  (12 or 16)
        Map<String, Integer> shortestPath = new HashMap<String, Integer>(); //short distance form any node to dataCollector        (node, shortestDistance)

        Map<String, Integer> powerConsumption = new HashMap<String, Integer>();

        numberNodes = mGraph.getVertexCount();
        numberLinks = mGraph.getEdgeCount();
        System.out.println("1. Number of Nodes = "+numberNodes);
        System.out.println("2. Number of Links = "+numberLinks);

        for (MyVertex v : mGraph.getVertices()) {
        //    System.out.println("Vertex Name=" + v.getName() + ", Is Data Collector = "+ v.isDataCollector());
            if (v.isDataCollector()){
               // dataCollectors.put(String.valueOf(noDataCollectors), v.getName()); //System.out.println("Vertex Name=" + v.getName() + ", Is Data Collector = "+ v.isDataCollector());
                dcList.add(v.getName());
                noDataCollectors++;
            }

        }
//        for (MyEdge e : g.getEdges()){
//
//        }
        int[] nums = new int[noDataCollectors];

        System.out.println("3. Number of Data Collectors =" + noDataCollectors);
        for(int i =0; i < dcList.size(); i++){
                   System.out.println(i+ " = "+dcList.get(i));
        }

        Number dist;

        for(MyVertex node : mGraph.getVertices()){
            //este ciclo se hace 9 veces, en una matriz 3x3
            for(int i =0; i < dcList.size(); i++){
                //System.out.println(i+ " = "+dcList.get(i));
                //este ciclo se hace 4 veces, 1 por cada data collector
                dist = calcWeightedShortestPath(node, getNode(dcList.get(i)));
               // shortestPath.put(node.getName(), dist.intValue());
                nums[i] = dist.intValue();
            }
           // int min = getMinimum(shortestPath);
            int min = getMinimo(nums);
            distanceToDC.put(node.getName(), min);
           // shortestPath.clear();
        }

        System.out.println("4. Shortest distance to Data Collector");
        for (String nodes : distanceToDC.keySet()) {
            System.out.println("" +nodes+", distance="+distanceToDC.get(nodes));
        }


   //     System.out.println("powerFormattedTextField.getValue()="+powerFormattedTextField.getValue() );
    if(powerFormattedTextField.getText() != null && powerFormattedTextField.getText() != ""){
        int numberOfIterations = Integer.parseInt(powerFormattedTextField.getText());
 //   System.out.println("5. Power consumption... after "+ n +" iterations");
     //   int[] dataSet = new int[n];     //String dataSet = [power decressing]
        Vector<Object> vector;
        Map<String, Vector> mapValues = new HashMap<String, Vector>();


        MyVertex vertex;
        String nameNode = "";

        Set<String> ns = distanceToDC.keySet();
        Iterator iter = ns.iterator();
        while (iter.hasNext())
        {
            String obj = (String) iter.next();
            vertex = getNode(obj);
            vector = new Vector<Object>();
            for(int i = 0; i < numberOfIterations ; i++){
             //   System.out.println("i="+i);
                powerConsumption.put(vertex.getName(), vertex.getPower() - ((distanceToDC.get(obj)  * vertex.getFrequency())+ 1));
                vertex.setPower(vertex.getPower() - ((distanceToDC.get(obj)  * vertex.getFrequency()) + 1));
            //    System.out.println("YYY - " +vertex.getName()+", power="+vertex.getPower());
                vector.add(vertex.getPower());
                nameNode = vertex.getName();
                if (i == (numberOfIterations - 1)) {
                    // Last item...
                    mapValues.put(nameNode, vector);
                  //  vector.clear();
                }
            }

        }


        showGraph(mapValues);

                         /*
        for (String keys : mapValues.keySet()) {
            System.out.println("XXX - Node=" +keys);
            Vector values = mapValues.get(keys);
            for (int i=0; i < values.size(); i++) {
                System.out.println("values["+i+"]=" +values.get(i));
            }

        }                         */
            System.out.println("5. Power consumption... after "+ numberOfIterations +" iterations");
            for (String power : powerConsumption.keySet()) {
                System.out.println("" +power+", power="+powerConsumption.get(power));
            }
        }

    }


    public static void showGraph(Map<String, Vector> mapValues){

     //   demo = new GraphChart("My First Chart");
        demo = new GraphChart("Smart Forest Use Case", mapValues);
      //  CategoryDataset dataset = createDataset();

//        CategoryDataset dataset = createDataset(values, name);
//        JFreeChart chart = demo.createChart(dataset);
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new Dimension(500, 270));
//        frame.setContentPane(chartPanel);
        //the other code in main
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

    public static int getMinimo(int[] nums){
        int minimum = mGraph.getVertexCount();
        for (int i = 0; i < nums.length; i++){
//            System.out.println(nodes.get(i).toString()+ " = "+nodes.get(i));
            if( nums [i] < minimum){
                   minimum = nums [i];
            }
        }
            return minimum;
    }

    //get node
    public static MyVertex getNode(String name){
        MyVertex v = new MyVertex();
        for(MyVertex node : mGraph.getVertices()){
                 if(name  == node.getName()){
                    v = node;
                 }
        }
        return v;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().add(new EditorMouseMenu());
        jf.pack();
        jf.setVisible(true);
    }

}
