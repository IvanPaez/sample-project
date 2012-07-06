/*
 * MyMouseMenus.java
 *
 * Created on March 21, 2007, 3:34 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */

package mousemenu;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * A collection of classes used to assemble popup mouse menus for the custom
 * edges and vertices developed in this example.
 * @author Dr. Greg M. Bernstein
 */
public class MyMouseMenus {

    public static class EdgeMenu extends JPopupMenu {
        // private JFrame frame;
        public EdgeMenu(final JFrame frame) {
            super("Edge Menu");
            // this.frame = frame;
            this.add(new DeleteEdgeMenuItem<GraphElements.MyEdge>());
            this.addSeparator();
            this.add(new CommunicationSizeDisplay());
            this.add(new CommunicationCostDisplay());
            this.addSeparator();
            this.add(new EdgePropItem(frame));
        }

    }

    public static class EdgePropItem extends JMenuItem implements EdgeMenuListener<mousemenu.GraphElements.MyEdge>,
            MenuPointListener {
        GraphElements.MyEdge edge;
        VisualizationViewer visComp;
        Point2D point;

        public void setEdgeAndView(GraphElements.MyEdge edge, VisualizationViewer visComp) {
            this.edge = edge;
            this.visComp = visComp;
        }

        public void setPoint(Point2D point) {
            this.point = point;
        }

        public  EdgePropItem(final JFrame frame) {
            super("Edit Edge Properties...");
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    EdgePropertyDialog dialog = new EdgePropertyDialog(frame, edge);
                    dialog.setLocation((int)point.getX()+ frame.getX(), (int)point.getY()+ frame.getY());
                    dialog.setVisible(true);
                }

            });
        }

    }
    public static class CommunicationSizeDisplay extends JMenuItem implements EdgeMenuListener<mousemenu.GraphElements.MyEdge> {
        public void setEdgeAndView(GraphElements.MyEdge e, VisualizationViewer visComp) {
            this.setText("Communication Size " + e + " = " + e.getCommunicationSize());
        }
    }

    public static class CommunicationCostDisplay extends JMenuItem implements EdgeMenuListener<mousemenu.GraphElements.MyEdge> {
        public void setEdgeAndView(GraphElements.MyEdge e, VisualizationViewer visComp) {
            this.setText("Communication Cost " + e + " = " + e.getCommunicationCost());
        }
    }

    public static class VertexMenu extends JPopupMenu {
        public VertexMenu(final JFrame frame) {
            super("Vertex Menu");
            this.add(new DeleteVertexMenuItem<GraphElements.MyVertex>());
            this.addSeparator();
            this.add(new FrequencyDisplay());
            this.add(new PowerDisplay());
            this.add(new DataCollectorDisplay());
            this.addSeparator();
            this.add(new VertexPropItem(frame));
        }
    }

    public static class VertexPropItem extends JMenuItem implements VertexMenuListener<GraphElements.MyVertex>,
            MenuPointListener {
        GraphElements.MyVertex vertex;
        VisualizationViewer visComp;
        Point2D point;

        public void setVertexAndView(GraphElements.MyVertex vertex, VisualizationViewer visComp) {
            this.vertex = vertex;
            this.visComp = visComp;
        }

        public void setPoint(Point2D point) {
            this.point = point;
        }

        public VertexPropItem(final JFrame frame) {
            super("Edit Vertex Properties...");
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    VertexPropertyDialog dialog = new VertexPropertyDialog(frame, vertex);
                    dialog.setLocation((int)point.getX()+ frame.getX(), (int)point.getY()+ frame.getY());
                    dialog.setVisible(true);
                }

            });
        }

    }

    public static class FrequencyDisplay extends JMenuItem implements VertexMenuListener<mousemenu.GraphElements.MyVertex> {
        public void setVertexAndView(GraphElements.MyVertex e, VisualizationViewer visComp) {
            this.setText("Frequency " + e + " = " + e.getFrequency());
        }
    }

    public static class PowerDisplay extends JMenuItem implements VertexMenuListener<mousemenu.GraphElements.MyVertex> {
        public void setVertexAndView(GraphElements.MyVertex e, VisualizationViewer visComp) {
            this.setText("Power " + e + " = " + e.getPower());
        }
    }

    public static class DataCollectorDisplay extends JMenuItem implements VertexMenuListener<mousemenu.GraphElements.MyVertex> {
        public void setVertexAndView(GraphElements.MyVertex e, VisualizationViewer visComp) {
            this.setText("Is Data Collector " + e + " = " + e.isDataCollector());
        }
    }


    /*
public static class pscCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<GraphElements.MyVertex> {
GraphElements.MyVertex v;

public pscCheckBox() {
super("PSC Capable");
this.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
v.setPacketSwitchCapable(isSelected());
}

});
}
public void setVertexAndView(GraphElements.MyVertex v, VisualizationViewer visComp) {
this.v = v;
this.setSelected(v.isPacketSwitchCapable());
}

}

public static class tdmCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<GraphElements.MyVertex> {
GraphElements.MyVertex v;

public tdmCheckBox() {
super("TDM Capable");
this.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
v.setTdmSwitchCapable(isSelected());
}

});
}
public void setVertexAndView(GraphElements.MyVertex v, VisualizationViewer visComp) {
this.v = v;
this.setSelected(v.isTdmSwitchCapable());
}

}                           */

}
