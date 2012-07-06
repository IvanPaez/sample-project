/*
 * DeleteEdgeMenuItem.java
 *
 * Created on March 21, 2007, 2:47 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */
package mousemenu;

/**
 * Created with IntelliJ IDEA.
 * User: ipaezana
 * Date: 6/14/12
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * A class to implement the deletion of an edge from within a
 * PopupVertexEdgeMenuMousePlugin.
 * @author Dr. Greg M. Bernstein
 */
public class DeleteEdgeMenuItem<E> extends JMenuItem implements EdgeMenuListener<E> {
    private E edge;
    private VisualizationViewer visComp;

    /** Creates a new instance of DeleteEdgeMenuItem */
    public DeleteEdgeMenuItem() {
        super("Delete Edge");
        this.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                visComp.getPickedEdgeState().pick(edge, false);
                visComp.getGraphLayout().getGraph().removeEdge(edge);
                visComp.repaint();
            }
        });
    }

    /**
     * Implements the EdgeMenuListener interface to update the menu item with info
     * on the currently chosen edge.
     * @param edge
     * @param visComp
     */
    public void setEdgeAndView(E edge, VisualizationViewer visComp) {
        this.edge = edge;
        this.visComp = visComp;
        this.setText("Delete Edge " + edge.toString());
    }

}
