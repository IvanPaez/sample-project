package smartforest;/*
 * examples.SimpleGraphView.java
 *
 * Created on March 8, 2007, 7:49 PM
 *
 * Copyright March 8, 2007 Grotto Networking
 */


import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import java.util.List;


/**
 *
 * @author Dr. Greg M. Bernstein
 */
public class ForestGraph {

    //ForestGraph<50, 50> graph;
    //Graph<String, Number> graph;
    Graph<Integer, String> graph;

    Map<String,String[]> map = new HashMap<String,String[]>();

    List<String> nodeList;

    /** Creates a new instance of examples.SimpleGraphView */
    public ForestGraph() {
        //setLayout(new BorderLayout());

        // Graph<V, E> where V is the type of the vertices and E is the type of the edges
        // Note showing the use of a SparseGraph rather than a SparseMultigraph
        graph = new SparseGraph<Integer, String>();
       // graph = new ForestGraph<50, 50>();
        // Add some vertices. From above we defined these to be type Integer.
      /*  g.addVertex((Integer)1);
        g.addVertex((Integer)2);
        g.addVertex((Integer)3);
        g.addVertex((Integer)4);
        g.addVertex((Integer)5);
        g.addVertex((Integer)6);
        g.addVertex((Integer)7);
        g.addVertex((Integer)8);
        g.addVertex((Integer)9);                       */
        // g.addVertex((Integer)1);  // note if you add the same object again nothing changes
        // Add some edges. From above we defined these to be of type String
        // Note that the default is for undirected edges.
       graph.addEdge("(1,2)", 1, 2); // Note that Java 1.5 auto-boxes primitives
        graph.addEdge("(2,3)", 2, 3);
        graph.addEdge("(1,4)", 1, 4); // Note that Java 1.5 auto-boxes primitives
        graph.addEdge("(2,5)", 2, 5);
        graph.addEdge("(3,6)", 3, 6); // Note that Java 1.5 auto-boxes primitives
        graph.addEdge("(4,5)", 4, 5);
        graph.addEdge("(5,6)", 5, 6); // Note that Java 1.5 auto-boxes primitives
        graph.addEdge("(4,7)", 4, 7);
        graph.addEdge("(5,8)", 5, 8); // Note that Java 1.5 auto-boxes primitives
        graph.addEdge("(6,9)", 6, 9);
        graph.addEdge("(7,8)", 7, 8); // Note that Java 1.5 auto-boxes primitives
        graph.addEdge("(8,9)", 8, 9);
      /*
        map.put("1", new String[] {"10 00 N", "10 00 W"});
        map.put("2", new String[] {"10 00 N", "0 0 W"});
        map.put("3", new String[] {"10 00 N", "10 0 E"});

        map.put("4", new String[] {"0 0 N", "10 00 W"});
        map.put("5", new String[] {"0 0 N", "0 0 W"});
        map.put("6", new String[] {"0 0 N", "10 0 E"});

        map.put("7", new String[] {"10 00 S", "10 00 W"});
        map.put("8", new String[] {"10 00 S", "0 0 W"});
        map.put("9", new String[] {"10 00 S", "10 0 E"});

        nodeList = new ArrayList<String>(map.keySet());                  */
        // create a simple graph for the demo
       // graph = new DirectedSparseMultigraph<String, Number>();
        // Note showing the use of a SparseGraph rather than a SparseMultigraph

        //createVertices();
       // createEdges();
    }

    /**
     * create some vertices
     * @param count how many to create
     * @return the Vertices in an array
     */
    private void createVertices() {
       /* for (String node : map.keySet()) {
            graph.addVertex(node);
        }       */
    }

    /**
     * create edges for this demo graph
     * @param v an array of Vertices to connect
     */
    void createEdges() {

    /*    graph.addEdge(new Double(Math.random()), "1", "2", EdgeType.UNDIRECTED);
        graph.addEdge(new Double(Math.random()), "2", "3", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "4", "5", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "5", "6", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "7", "8", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "8", "9", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "1", "4", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "2", "5", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "3", "6", EdgeType.DIRECTED);

        graph.addEdge(new Double(Math.random()), "4", "7", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "5", "8", EdgeType.DIRECTED);
        graph.addEdge(new Double(Math.random()), "6", "9", EdgeType.DIRECTED);               */
        /*
  for(int i=0; i<map.keySet().size()*1.3; i++) {
      graph.addEdge(new Double(Math.random()), randomCity(), randomCity(), EdgeType.DIRECTED);
  }      */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ForestGraph sgv = new ForestGraph(); // This builds the graph
        // Layout<V, E>, VisualizationComponent<V,E>
    //    Layout<Integer, String> layout = new CircleLayout(sgv.g);

        Layout<Integer, String> layout = new CircleLayout(sgv.graph);
        layout.setSize(new Dimension(300,300));
        BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
        vv.setPreferredSize(new Dimension(350,350));
        // Setup up a new vertex to paint transformer...
        Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
            public Paint transform(Integer i) {
                return Color.GREEN;
            }
        };
        // Set up a new stroke Transformer for the edges
        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
            public Stroke transform(String s) {
                return edgeStroke;
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

        JFrame frame = new JFrame("Smart Forest Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

}
