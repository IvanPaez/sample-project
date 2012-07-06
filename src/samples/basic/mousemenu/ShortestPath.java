package mousemenu;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import org.apache.commons.collections15.Transformer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ipaezana
 * Date: 6/19/12
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShortestPath {
    DirectedGraph<GraphElements.MyVertex, GraphElements.MyEdge> g;
  //  g = new DirectedSparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge>();
    //GraphElements.MyVertex mFrom, mTo;

    public void calcUnweightedShortestPath(GraphElements.MyVertex nodeFrom, GraphElements.MyVertex nodeTo) {
        DijkstraShortestPath<GraphElements.MyVertex,GraphElements.MyEdge> alg = new DijkstraShortestPath(g);
        List<GraphElements.MyEdge> l = alg.getPath(nodeFrom, nodeTo);
        System.out.println("The shortest unweighted path from " + nodeFrom + " to " + nodeTo + " is:");
        System.out.println(l.toString());
    }

    public void calcWeightedShortestPath(GraphElements.MyVertex nodeFrom, GraphElements.MyVertex nodeTo) {
       Transformer<GraphElements.MyEdge, Double> wtTransformer = new Transformer<GraphElements.MyEdge,Double>() {
            public Double transform(GraphElements.MyEdge link) {
                return link.communicationCost;
            }
        };
        DijkstraShortestPath<GraphElements.MyVertex,GraphElements.MyEdge> alg = new DijkstraShortestPath(g, wtTransformer);
        List<GraphElements.MyEdge> l = alg.getPath(nodeFrom, nodeTo);
        Number dist = alg.getDistance(nodeFrom, nodeTo);
        System.out.println("The shortest weighted path from " + nodeFrom + " to " + nodeTo + " is:");
        System.out.println(l.toString());
        System.out.println("and the length of the path is: " + dist);
    }

}
