
/*
 * GraphElements.java
 *
 * Created on March 21, 2007, 9:57 AM
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */
package mousemenu;

import org.apache.commons.collections15.Factory;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 *
 * @author Dr. Greg M. Bernstein
 */
public class GraphElements {

    /** Creates a new instance of GraphElements */
    public GraphElements() {
    }

    public static class MyVertex {
        private String name;
        private int frequency;
        private int power;
        private boolean isDataCollector = false;

    /*    private boolean packetSwitchCapable;
        private boolean tdmSwitchCapable;              */
        public MyVertex(){

        }

        public MyVertex(String name, int frequency, int power, boolean isDataCollector) {
            this.name = name;
            this.frequency = frequency;
            this.power = power;
            this.isDataCollector = isDataCollector;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public boolean isDataCollector(){
            return isDataCollector;
        }

        public void setIsDataCollector(boolean isDataCollector1 ){
            this.isDataCollector = isDataCollector1;
        }
    /*
        public boolean isPacketSwitchCapable() {
            return packetSwitchCapable;
        }

        public void setPacketSwitchCapable(boolean packetSwitchCapable) {
            this.packetSwitchCapable = packetSwitchCapable;
        }

        public boolean isTdmSwitchCapable() {
            return tdmSwitchCapable;
        }

        public void setTdmSwitchCapable(boolean tdmSwitchCapable) {
            this.tdmSwitchCapable = tdmSwitchCapable;
        }
    */
        public String toString() {
            return name;
        }
    }

    public static class MyEdge {
        double communicationCost;
        double communicationSize;
        private String name;

        public MyEdge(String name) {
            this.name = name;
        }
        public double getCommunicationCost() {
            return communicationCost;
        }

        public void setCommunicationCost(double communicationCost) {
            this.communicationCost = communicationCost;
        }

        public double getCommunicationSize() {
            return communicationSize;
        }

        public void setCommunicationSize(double communicationSize) {
            this.communicationSize = communicationSize;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    // Single factory for creating Vertices...
    public static class MyVertexFactory implements Factory<MyVertex> {
        private static int nodeCount = 0;
        private static int defaultFrequency;
        private static int defaultPower;
        private boolean isDataCollector;
      //  private static boolean defaultPSC = false;
      //  private static boolean defaultTDM = true;
        private static MyVertexFactory instance = new MyVertexFactory();

        private MyVertexFactory() {
        }

        public static MyVertexFactory getInstance() {
            return instance;
        }

        public GraphElements.MyVertex create() {
            String name = "Node" + nodeCount++;
            MyVertex v = new MyVertex(name,  defaultFrequency, defaultPower, isDataCollector);
        //    v.setPacketSwitchCapable(defaultPSC);
        //    v.setTdmSwitchCapable(defaultTDM);
            return v;
        }

       /* public static boolean isDefaultPSC() {
            return defaultPSC;
        }

        public static void setDefaultPSC(boolean aDefaultPSC) {
            defaultPSC = aDefaultPSC;
        }

        public static boolean isDefaultTDM() {
            return defaultTDM;
        }

        public static void setDefaultTDM(boolean aDefaultTDM) {
            defaultTDM = aDefaultTDM;
        }                     */

        public static int getDefaultPower() {
            return defaultPower;
        }

        public static void setDefaultPower(int defaultPower) {
            MyVertexFactory.defaultPower = defaultPower;
        }

        public static int getDefaultFrequency() {
            return defaultFrequency;
        }

        public static void setDefaultFrequency(int defaultFrequency) {
            MyVertexFactory.defaultFrequency = defaultFrequency;
        }
    }

    // Singleton factory for creating Edges...
    public static class MyEdgeFactory implements Factory<MyEdge> {
        private static int linkCount = 0;
        private static double defaultCommunicationSize;
        private static double defaultCommunicationCost;

        private static MyEdgeFactory instance = new MyEdgeFactory();

        private MyEdgeFactory() {
        }

        public static MyEdgeFactory getInstance() {
            return instance;
        }

        public GraphElements.MyEdge create() {
            String name = "Link" + linkCount++;
            MyEdge link = new MyEdge(name);
            link.setCommunicationSize(defaultCommunicationSize);
            link.setCommunicationCost(defaultCommunicationCost);
            return link;
        }

        public static double getDefaultCommunicationSize() {
            return defaultCommunicationSize;
        }

        public static void setDefaultCommunicationSize(double aDefaultCommunicationSize) {
            defaultCommunicationSize = aDefaultCommunicationSize;
        }

        public static double getDefaultCommunicationCost() {
            return defaultCommunicationCost;
        }

        public static void setDefaultCommunicationCost(double aDefaultCommunicationCost) {
            defaultCommunicationCost = aDefaultCommunicationCost;
        }

    }

}
