package examples;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.*;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ChainedTransformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ipaezana
 * Date: 6/13/12
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */

@SuppressWarnings("serial")
public class WorldMapGraphExample extends JApplet {

    /**
     * the graph
     */
    Graph<String, Number> graph;

    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer<String, Number> vv;

    Map<String,String[]> map = new HashMap<String,String[]>();
    List<String> cityList;



    /**
     * create an instance of a simple graph with controls to
     * demo the zoom features.
     *
     */
    public WorldMapGraphExample() {
        setLayout(new BorderLayout());

        //	map.put("TYO", new String[] {"35 40 N", "139 45 E"});
        map.put("PEK", new String[] {"39 55 N", "116 26 E"});
        //	map.put("MOW", new String[] {"55 45 N", "37 42 E"});
        //	map.put("JRS", new String[] {"31 47 N", "35 13 E"});
        //	map.put("CAI", new String[] {"30 03 N", "31 15 E"});
        //	map.put("CPT", new String[] {"33 55 S", "18 22 E"});
        map.put("PAR", new String[] {"48 52 N", "2 20 E"});
        //	map.put("LHR", new String[] {"51 30 N", "0 10 W"});
        //	map.put("HNL", new String[] {"21 18 N", "157 51 W"});
        map.put("NYC", new String[] {"40 77 N", "73 98 W"});
        //	map.put("SFO", new String[] {"37 62 N", "122 38 W"});
        //	map.put("AKL", new String[] {"36 55 S", "174 47 E"});
        //	map.put("BNE", new String[] {"27 28 S", "153 02 E"});
        map.put("HKG", new String[] {"22 15 N", "114 10 E"});
        //	map.put("KTM", new String[] {"27 42 N", "85 19 E"});
        //	map.put("IST", new String[] {"41 01 N", "28 58 E"});
        //	map.put("STO", new String[] {"59 20 N", "18 03 E"});
        map.put("RIO", new String[] {"22 54 S", "43 14 W"});
        map.put("LIM", new String[] {"12 03 S", "77 03 W"});
        //	map.put("YTO", new String[] {"43 39 N", "79 23 W"});
        map.put("BOG", new String[] {"4 37 N", "74 17 W"});
        map.put("BKK", new String[] {"13 45 N", "100 29 E"});
        map.put("TPE", new String[] {"25 02 N", "121 38 E"});
        map.put("KUL", new String[] {"3 8 N", "101 41 E"});   //3°8′51″N 101°41′36″E

        map.put("SZX", new String[] {"22 33 N", "114 06 E"});    //22°33′N 114°06′E﻿
        map.put("DPS", new String[] {"8 20 S", "115 00 E"});     //8°20′S 115°00′E
        map.put("CGK", new String[] {"6 12 S", "106 48 E"});     //6°12′S 106°48′E
        map.put("VTE", new String[] {"17 58 N", "102 36 E"});    //17°58′00″N 102°36′00″E

        map.put("PTY", new String[] {"8 59 N", "79 31 W"});   //30°10′28″N 85°39′52″W Panama   8°59′N 79°31′W
        map.put("MAD", new String[] {"40 23 N", "3 43 W"});    //40°23′N 3°43′W Madrid
        map.put("ANC", new String[] {"61 13 N", "149 53 W"});   //61°13′05″N 149°53′33″W Anchorage

        map.put("MED", new String[] {"6 14 N", "75 34 W"});  //6°14′9.33″N 75°34′30.49″W Medellin
        map.put("BOS", new String[] {"42 21 N", "71 03 W"}); //42°21′28″N 71°03′42″W Boston

        cityList = new ArrayList<String>(map.keySet());

        // create a simple graph for the demo
        graph = new DirectedSparseMultigraph<String, Number>();
        createVertices();
        createEdges();

        ImageIcon mapIcon = null;
        String imageLocation = "/images/political_world_map.jpg";
        try {
            mapIcon =
                    new ImageIcon(getClass().getResource(imageLocation));
        } catch(Exception ex) {
            System.err.println("Can't load \""+imageLocation+"\"");
        }
        final ImageIcon icon = mapIcon;

        Dimension layoutSize = new Dimension(2000,1000);

        Layout<String,Number> layout = new StaticLayout<String,Number>(graph,
                new ChainedTransformer(new Transformer[]{
                        new CityTransformer(map),
                        new LatLonPixelTransformer(new Dimension(2000,1000))
                }));

        layout.setSize(layoutSize);
        vv =  new VisualizationViewer<String,Number>(layout,
                new Dimension(800,400));

        if(icon != null) {
            vv.addPreRenderPaintable(new VisualizationViewer.Paintable(){
                public void paint(Graphics g) {
                    Graphics2D g2d = (Graphics2D)g;
                    AffineTransform oldXform = g2d.getTransform();
                    AffineTransform lat =
                            vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getTransform();
                    AffineTransform vat =
                            vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getTransform();
                    AffineTransform at = new AffineTransform();
                    at.concatenate(g2d.getTransform());
                    at.concatenate(vat);
                    at.concatenate(lat);
                    g2d.setTransform(at);
                    g.drawImage(icon.getImage(), 0, 0,
                            icon.getIconWidth(),icon.getIconHeight(),vv);
                    g2d.setTransform(oldXform);
                }
                public boolean useTransform() { return false; }
            });
        }

        vv.getRenderer().setVertexRenderer(
                new GradientVertexRenderer<String,Number>(
                        Color.white, Color.red,
                        Color.white, Color.blue,
                        vv.getPickedVertexState(),
                        false));

        // add my listeners for ToolTips
        vv.setVertexToolTipTransformer(new ToStringLabeller());
        vv.setEdgeToolTipTransformer(new Transformer<Number,String>() {
            public String transform(Number edge) {
                return "E"+graph.getEndpoints(edge).toString();
            }});

        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPositioner(new BasicVertexLabelRenderer.InsidePositioner());
        vv.getRenderer().getVertexLabelRenderer().setPosition(edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position.AUTO);

        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        add(panel);
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        vv.setGraphMouse(graphMouse);

        vv.addKeyListener(graphMouse.getModeKeyListener());
        vv.setToolTipText("<html><center>Type 'p' for Pick mode<p>Type 't' for Transform mode");

        final ScalingControl scaler = new CrossoverScalingControl();

//        vv.scaleToLayout(scaler);


        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });

        JButton reset = new JButton("reset");
        reset.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).setToIdentity();
                vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).setToIdentity();
            }});

        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        controls.add(reset);
        add(controls, BorderLayout.SOUTH);
    }

    /**
     * create some vertices
     * @param count how many to create
     * @return the Vertices in an array
     */
    private void createVertices() {
        for (String city : map.keySet()) {
            graph.addVertex(city);
        }
    }

    /**
     * create edges for this demo graph
     * @param v an array of Vertices to connect
     */
    void createEdges() {

        graph.addEdge(new Double(Math.random()), "MED", "NYC", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "NYC", "BKK", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "BKK", "VTE", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "VTE", "BKK", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "BKK", "TPE", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "TPE", "BKK", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "BKK", "KUL", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "KUL", "CGK", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "CGK", "DPS", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "DPS", "CGK", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "CGK", "KUL", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "KUL", "BKK", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "BKK", "PEK", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "PEK", "BKK", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "BKK", "PEK", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "PEK", "ANC", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "ANC", "NYC", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "NYC", "BOS", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "BOS", "NYC", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "NYC", "MED", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "BOG", "PTY", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "PTY", "NYC", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "NYC", "PEK", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "PEK", "HKG", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "HKG", "BKK", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "BKK", "SZX", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "HKG", "PEK", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "PEK", "NYC", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "NYC", "PTY", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "PTY", "BOG", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "BOG", "MAD", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "MAD", "PAR", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "MED", "BOG", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "BOG", "LIM", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "LIM", "RIO", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "RIO", "LIM", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "LIM", "BOG", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "BOG", "MED", EdgeType.DIRECTED);




        /*
  for(int i=0; i<map.keySet().size()*1.3; i++) {
      graph.addEdge(new Double(Math.random()), randomCity(), randomCity(), EdgeType.DIRECTED);
  }      */
    }

    private String randomCity() {
        int m = cityList.size();
        return cityList.get((int)(Math.random()*m));
    }

    static class CityTransformer implements Transformer<String,String[]> {

        Map<String,String[]> map;
        public CityTransformer(Map<String,String[]> map) {
            this.map = map;
        }

        /**
         * transform airport code to latlon string
         */
        public String[] transform(String city) {
            return map.get(city);
        }
    }

    static class LatLonPixelTransformer implements Transformer<String[],Point2D> {
        Dimension d;
        int startOffset;

        public LatLonPixelTransformer(Dimension d) {
            this.d = d;
        }
        /**
         * transform a lat
         */
        public Point2D transform(String[] latlon) {
            double latitude = 0;
            double longitude = 0;
            String[] lat = latlon[0].split(" ");
            String[] lon = latlon[1].split(" ");
            latitude = Integer.parseInt(lat[0]) + Integer.parseInt(lat[1])/60f;
            latitude *= d.height/180f;
            longitude = Integer.parseInt(lon[0]) + Integer.parseInt(lon[1])/60f;
            longitude *= d.width/360f;
            if(lat[2].equals("N")) {
                latitude = d.height / 2 - latitude;

            } else { // assume S
                latitude = d.height / 2 + latitude;
            }

            if(lon[2].equals("W")) {
                longitude = d.width / 2 - longitude;

            } else { // assume E
                longitude = d.width / 2 + longitude;
            }

            return new Point2D.Double(longitude,latitude);
        }

    }

    /**
     * a driver for this demo
     */
    public static void main(String[] args) {
        // create a frome to hold the graph
        final JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        content.add(new WorldMapGraphExample());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
