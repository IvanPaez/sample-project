package smartforest;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.PajekNetReader;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.*;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.FactoryUtils;

import javax.swing.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ipaezana
 * Date: 6/18/12
 * Time: 11:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleGraphDraw {

    public static void main(String[] args) throws IOException
    {
        JFrame jf = new JFrame();
        Graph g = getGraph();
        VisualizationViewer vv = new VisualizationViewer(new FRLayout(g));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
     //   vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
     //   vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        jf.getContentPane().add(vv);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }
    /**
     * Generates a graph: in this case, reads it from the file
     * "samples/datasetsgraph/simple.net"
     * @return A sample undirected graph
     */
    public static Graph getGraph() throws IOException
    {
        PajekNetReader pnr = new PajekNetReader(FactoryUtils.instantiateFactory(Object.class));
        Graph g = new UndirectedSparseGraph();

        //pnr.load("src/main/resources/datasets/simple.net", g);
        pnr.load("/Users/ipaezana/Documents/JUNG/jung2-2_0-sources/jung-samples-2.0-sources/datasets/rose.net", g);
        return g;
    }
}
