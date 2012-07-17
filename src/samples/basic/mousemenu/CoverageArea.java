package mousemenu;

import edu.uci.ics.jung.graph.SparseMultigraph;

import javax.swing.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ipaezana
 * Date: 7/12/12
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoverageArea {

    private double maxCoverage = 0;
    private double coverage =0;
    private int columns;
    private int rows;
    private int numberOfSensorInALine = 2;
    private int distanceBetweenSensors = 4;
    private int totalSensors = 0;

    private  List<String> sortNames = new ArrayList<String>();
    private Collection<GraphElements.MyVertex> collVertex;

    private static SparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge> mGraph;


    String[][] grid;  //this is an array of arrays


    public CoverageArea(SparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge> mGraph){
        this.mGraph = mGraph;
        collVertex = mGraph.getVertices();
      //  GraphElements.MyVertex[] arrayVertex = (GraphElements.MyVertex[])mGraph.getVertices().toArray();
//        GraphElements.MyVertex[] arrayVertex = (GraphElements.MyVertex[])collVertex.toArray();

        //Collections.sort(collVertex);

      //  vertexs = mGraph.getVertices();

        Iterator itr = collVertex.iterator();

       // Collections.sort(itr);

        GraphElements.MyVertex vertex;
       while (itr.hasNext()) {
           vertex = (GraphElements.MyVertex)itr.next();
        //   System.out.println("Name="+vertex.getName()+" Frequency="+vertex.getFrequency());
           //list.add(vertex);
        }
        totalSensors = mGraph.getVertexCount();
        double xRows = Math.ceil((totalSensors % numberOfSensorInALine));

       // System.out.println(""+totalSensors+" / "+numberOfSensorInALine+" = "+totalSensors / numberOfSensorInALine+"xRows="+xRows);

        rows =  (((totalSensors / numberOfSensorInALine) + (int)xRows)  * distanceBetweenSensors) + distanceBetweenSensors + 1;
        if (totalSensors < numberOfSensorInALine) {
            columns = (totalSensors * distanceBetweenSensors) + distanceBetweenSensors + 1;
        } else {
            columns = (numberOfSensorInALine * distanceBetweenSensors) + distanceBetweenSensors + 1;
        }

        System.out.println("Grid["+rows+", "+columns+"]");
        grid = new String[rows][columns];




    }

    public double getPercentageCoverage(){
        return coverage / maxCoverage;
    }

    public double getCoverage(){

        int cellsOut = 0;
        int totalCells = rows * columns;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                if(grid[i][j]== "0") {
                    cellsOut++;
                }
            }
        }
        coverage = totalCells - cellsOut;
        System.out.println("coverage= "+coverage+ " out of "+maxCoverage);
      //  System.out.println("maxCoverage="+maxCoverage);
        return maxCoverage;
    }

    public double getMaximumCoverage(){

        int cellsOut = 0;
        int totalCells = rows * columns;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                if(grid[i][j]== "0") {
                    cellsOut++;
                }
            }
        }

        maxCoverage = totalCells - cellsOut;
        return maxCoverage;
    }

    public void sortNodes(){
        collVertex = mGraph.getVertices();

        for(GraphElements.MyVertex node : mGraph.getVertices()){
            sortNames.add(node.getName());

        }
        Collections.sort(sortNames);
//        Iterator it = sortNames.iterator();
//        while(it.hasNext()){
//               System.out.println("Node="+it.next());
//        }
    }

    public void drawRadius(){

        drawGrid();

        int numberOfDetectedSensors = 0;
        GraphElements.MyVertex verObj;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                if(grid[i][j]== "X") {
                    //we found a sensor
                    //String nodeName = "Node"+numberOfDetectedSensors;
                    String nodeName = sortNames.get(numberOfDetectedSensors);
                    verObj = getNode(nodeName);

                    //    System.out.println("BBB Name="+verObj.getName()+" Frequency="+verObj.getFrequency());


                    for(int k = 0; k < verObj.getFrequency(); k++){


                        if(!grid[i-(k+1)][j].equals("X") && grid[i-(k+1)][j].equals("0")) grid[i-(k+1)][j] = "1";
                        else if (!grid[i-(k+1)][j].equals("X") && grid[i-(k+1)][j].equals("1")) grid[i-(k+1)][j] = "2";              //radius north

                        if(!grid[i+(k+1)][j].equals("X") && grid[i+(k+1)][j].equals("0")) grid[i+(k+1)][j] = "1";
                        else if(!grid[i+(k+1)][j].equals("X") && grid[i+(k+1)][j].equals("1")) grid[i+(k+1)][j] = "2";               //radius south

                        if(!grid[i][j-(k+1)].equals("X") && grid[i][j-(k+1)].equals("0")) grid[i][j-(k+1)] = "1";
                        else if(!grid[i][j-(k+1)].equals("X") && grid[i][j-(k+1)].equals("1")) grid[i][j-(k+1)] = "2";               //radius west

                        if(!grid[i][j+(k+1)].equals("X") && grid[i][j+(k+1)].equals("0")) grid[i][j+(k+1)] = "1";
                        else if(!grid[i][j+(k+1)].equals("X") && grid[i][j+(k+1)].equals("1")) grid[i][j+(k+1)] = "2";               //radius east

                        if(k < verObj.getFrequency() / 2){

                            //only when k = 0, and k = 1
                            if(grid[i-(k+1)][j - (k+1)].equals("0"))   grid[i-(k+1)][j - (k+1)] = "1";         //diagonal north-west
                            else if(grid[i-(k+1)][j - (k+1)].equals("1"))   grid[i-(k+1)][j - (k+1)] = "2";
                            else if(grid[i-(k+1)][j - (k+1)].equals("2"))   grid[i-(k+1)][j - (k+1)] = "3";
                            else if(grid[i-(k+1)][j - (k+1)].equals("3"))   grid[i-(k+1)][j - (k+1)] = "4";

                            if(grid[i-(k+1)][j + (k+1)].equals("0"))   grid[i-(k+1)][j + (k+1)] = "1";         //diagonal north-east
                            else if(grid[i-(k+1)][j + (k+1)].equals("1")) grid[i-(k+1)][j + (k+1)] = "2";
                            else if(grid[i-(k+1)][j + (k+1)].equals("2")) grid[i-(k+1)][j + (k+1)] = "3";
                            else if(grid[i-(k+1)][j + (k+1)].equals("3")) grid[i-(k+1)][j + (k+1)] = "4";

                            if(grid[i+(k+1)][j - (k+1)].equals("0"))   grid[i+(k+1)][j - (k+1)] = "1";         //diagonal south-west
                            else if(grid[i+(k+1)][j - (k+1)].equals("1")) grid[i+(k+1)][j - (k+1)] = "2";
                            else if(grid[i+(k+1)][j - (k+1)].equals("2")) grid[i+(k+1)][j - (k+1)] = "3";
                            else if(grid[i+(k+1)][j - (k+1)].equals("3")) grid[i+(k+1)][j - (k+1)] = "4";

                            if( grid[i+(k+1)][j + (k+1)].equals("0"))  grid[i+(k+1)][j + (k+1)] = "1";          //diagonal south-east
                            else if(grid[i+(k+1)][j + (k+1)].equals("1")) grid[i+(k+1)][j + (k+1)] = "2";
                            else if(grid[i+(k+1)][j + (k+1)].equals("2")) grid[i+(k+1)][j + (k+1)] = "3";
                            else if(grid[i+(k+1)][j + (k+1)].equals("3")) grid[i+(k+1)][j + (k+1)] = "4";

                            if (verObj.getFrequency() > distanceBetweenSensors / 2){

                            if(grid[i-1][j + k - (verObj.getFrequency() - 1)].equals("0")) grid[i-1][j + k - (verObj.getFrequency() - 1)] = "1";                   // movements in "L" north-west
                                else if(grid[i-1][j + k - (verObj.getFrequency() - 1)].equals("1")) grid[i-1][j + k - (verObj.getFrequency() - 1)] = "2";            //A
                           //         else if(grid[i-1][j + k - (verObj.getFrequency() - 1)].equals("2")) grid[i-1][j + k - (verObj.getFrequency() - 1)] = "3";

                            if(grid[i + k -(verObj.getFrequency() - 1)][j-1].equals("0")) grid[i + k -(verObj.getFrequency() - 1)][j-1] = "1";                    //B
                                else if(grid[i + k -(verObj.getFrequency() - 1)][j-1].equals("1")) grid[i + k -(verObj.getFrequency() - 1)][j-1] = "2";
                          //          else if(grid[i + k -(verObj.getFrequency() - 1)][j-1].equals("2")) grid[i + k -(verObj.getFrequency() - 1)][j-1] = "3";

                            if(grid[i-1][j - k +(verObj.getFrequency()- 1)].equals("0")) grid[i-1][j - k +(verObj.getFrequency()- 1)] = "1";                   // movement in "L" north-east
                                else if(grid[i-1][j - k +(verObj.getFrequency()- 1)].equals("1")) grid[i-1][j - k +(verObj.getFrequency()- 1)] = "2";           //A
                          //          else if(grid[i-1][j - k +(verObj.getFrequency()- 1)].equals("2")) grid[i-1][j - k +(verObj.getFrequency()- 1)] = "3";

                            if(grid[i + k -(verObj.getFrequency()- 1)][j+1].equals("0")) grid[i + k -(verObj.getFrequency()- 1)][j+1] = "1";                   //B
                                else if(grid[i + k -(verObj.getFrequency()- 1)][j+1].equals("1")) grid[i + k -(verObj.getFrequency()- 1)][j+1] =  "2";
//                                    else if(grid[i + k -(verObj.getFrequency()- 1)][j+1].equals("2")) grid[i + k -(verObj.getFrequency()- 1)][j+1] =  "3";


                            if(grid[i+1][j + k -(verObj.getFrequency() - 1)].equals("0")) grid[i+1][j + k -(verObj.getFrequency() - 1)] = "1";                 // movement in "L" south-west
                                else if(grid[i+1][j + k -(verObj.getFrequency() - 1)].equals("1")) grid[i+1][j + k -(verObj.getFrequency() - 1)] = "2";         //A
//                                    else if(grid[i+1][j + k -(verObj.getFrequency() - 1)].equals("2")) grid[i+1][j + k -(verObj.getFrequency() - 1)] = "3";

                            if(grid[i - k +(verObj.getFrequency() - 1)][j-1].equals("0")) grid[i  - k +(verObj.getFrequency() - 1)][j-1] = "1";
                                else if(grid[i - k +(verObj.getFrequency() - 1)][j-1].equals("1")) grid[i  - k +(verObj.getFrequency() - 1)][j-1] = "2";          //B
//                            else if(grid[i - k +(verObj.getFrequency() - 1)][j-1].equals("2")) grid[i  - k +(verObj.getFrequency() - 1)][j-1] = "3";


                            if(grid[i+1][j - k +(verObj.getFrequency() - 1)].equals("0")) grid[i+1][j - k +(verObj.getFrequency() - 1)] = "1";                 // movement in "L" south-east
                                else if(grid[i+1][j - k +(verObj.getFrequency() - 1)].equals("1")) grid[i+1][j - k +(verObj.getFrequency() - 1)] = "2";         //A
//                            else if(grid[i+1][j - k +(verObj.getFrequency() - 1)].equals("2")) grid[i+1][j - k +(verObj.getFrequency() - 1)] = "3";

                            if(grid[i - k +(verObj.getFrequency() - 1)][j+1].equals("0")) grid[i - k +(verObj.getFrequency() - 1)][j+1] = "1";                  //B
                                else if(grid[i - k +(verObj.getFrequency() - 1)][j+1].equals("1")) grid[i - k +(verObj.getFrequency() - 1)][j+1] = "2";
//                            else if(grid[i - k +(verObj.getFrequency() - 1)][j+1].equals("2")) grid[i - k +(verObj.getFrequency() - 1)][j+1] = "3";

                              }
                        }

                    }

                    numberOfDetectedSensors++;
                }

            }
        }
        System.out.println("Current coverage:");
        print(4); //print the grid with 4 pixels spam
    }

    public void drawMaxRadius(){
        int numberOfDetectedSensors = 0;
        GraphElements.MyVertex verObj;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                if(grid[i][j]== "X") {
                    //we found a sensor
                           String nodeName = "Node"+numberOfDetectedSensors;
                           verObj = getNode(nodeName);

                       //    System.out.println("BBB Name="+verObj.getName()+" Frequency="+verObj.getFrequency());


                            //for(int k = 0; k < verObj.getFrequency(); k++){
                            for(int k = 0; k < distanceBetweenSensors; k++){

                               if(!grid[i-(k+1)][j].equals("X") && grid[i-(k+1)][j].equals("0")) grid[i-(k+1)][j] = "1";
                                    else if (!grid[i-(k+1)][j].equals("X") && grid[i-(k+1)][j].equals("1")) grid[i-(k+1)][j] = "2";              //radius north

                               if(!grid[i+(k+1)][j].equals("X") && grid[i+(k+1)][j].equals("0")) grid[i+(k+1)][j] = "1";
                                    else if(!grid[i+(k+1)][j].equals("X") && grid[i+(k+1)][j].equals("1")) grid[i+(k+1)][j] = "2";               //radius south

                               if(!grid[i][j-(k+1)].equals("X") && grid[i][j-(k+1)].equals("0")) grid[i][j-(k+1)] = "1";
                                    else if(!grid[i][j-(k+1)].equals("X") && grid[i][j-(k+1)].equals("1")) grid[i][j-(k+1)] = "2";               //radius west

                               if(!grid[i][j+(k+1)].equals("X") && grid[i][j+(k+1)].equals("0")) grid[i][j+(k+1)] = "1";
                                    else if(!grid[i][j+(k+1)].equals("X") && grid[i][j+(k+1)].equals("1")) grid[i][j+(k+1)] = "2";               //radius east

                                //if(k < verObj.getFrequency() / 2){
                                if(k < (distanceBetweenSensors / 2)){
                                    //only when k = 0, and k = 1
                                 if(grid[i-(k+1)][j - (k+1)].equals("0"))   grid[i-(k+1)][j - (k+1)] = "1";         //diagonal north-west
                                    else if(grid[i-(k+1)][j - (k+1)].equals("1"))   grid[i-(k+1)][j - (k+1)] = "2";
                                        else if(grid[i-(k+1)][j - (k+1)].equals("2"))   grid[i-(k+1)][j - (k+1)] = "3";
                                            else if(grid[i-(k+1)][j - (k+1)].equals("3"))   grid[i-(k+1)][j - (k+1)] = "4";

                                 if(grid[i-(k+1)][j + (k+1)].equals("0"))   grid[i-(k+1)][j + (k+1)] = "1";         //diagonal north-east
                                    else if(grid[i-(k+1)][j + (k+1)].equals("1")) grid[i-(k+1)][j + (k+1)] = "2";
                                        else if(grid[i-(k+1)][j + (k+1)].equals("2")) grid[i-(k+1)][j + (k+1)] = "3";
                                            else if(grid[i-(k+1)][j + (k+1)].equals("3")) grid[i-(k+1)][j + (k+1)] = "4";

                                 if(grid[i+(k+1)][j - (k+1)].equals("0"))   grid[i+(k+1)][j - (k+1)] = "1";         //diagonal south-west
                                     else if(grid[i+(k+1)][j - (k+1)].equals("1")) grid[i+(k+1)][j - (k+1)] = "2";
                                        else if(grid[i+(k+1)][j - (k+1)].equals("2")) grid[i+(k+1)][j - (k+1)] = "3";
                                            else if(grid[i+(k+1)][j - (k+1)].equals("3")) grid[i+(k+1)][j - (k+1)] = "4";

                                 if( grid[i+(k+1)][j + (k+1)].equals("0"))  grid[i+(k+1)][j + (k+1)] = "1";          //diagonal south-east
                                    else if(grid[i+(k+1)][j + (k+1)].equals("1")) grid[i+(k+1)][j + (k+1)] = "2";
                                        else if(grid[i+(k+1)][j + (k+1)].equals("2")) grid[i+(k+1)][j + (k+1)] = "3";
                                            else if(grid[i+(k+1)][j + (k+1)].equals("3")) grid[i+(k+1)][j + (k+1)] = "4";


                                 if(grid[i-1][j-(k+2)].equals("0")) grid[i-1][j-(k+2)] = "1";                   // movements in "L" north-west
                                    else if(grid[i-1][j-(k+2)].equals("1")) grid[i-1][j-(k+2)] = "2";            //A
                                        else if(grid[i-1][j-(k+2)].equals("2")) grid[i-1][j-(k+2)] = "3";

                                 if(grid[i-(k+2)][j-1].equals("0")) grid[i-(k+2)][j-1] = "1";                    //B
                                    else if(grid[i-(k+2)][j-1].equals("1")) grid[i-(k+2)][j-1] = "2";
                                        else if(grid[i-(k+2)][j-1].equals("2")) grid[i-(k+2)][j-1] = "3";


                                 if(grid[i-1][j+(k+2)].equals("0")) grid[i-1][j+(k+2)] = "1";                   // movement in "L" north-east
                                    else if(grid[i-1][j+(k+2)].equals("1")) grid[i-1][j+(k+2)] = "2";           //A
                                        else if(grid[i-1][j+(k+2)].equals("2")) grid[i-1][j+(k+2)] = "3";

                                 if(grid[i-(k+2)][j+1].equals("0")) grid[i-(k+2)][j+1] = "1";                   //B
                                    else if(grid[i-(k+2)][j+1].equals("1")) grid[i-(k+2)][j+1] = "2";
                                        else if(grid[i-(k+2)][j+1].equals("2")) grid[i-(k+2)][j+1] = "3";


                                 if(grid[i+1][j-(k+2)].equals("0")) grid[i+1][j-(k+2)] = "1";                 // movement in "L" south-west
                                    else if(grid[i+1][j-(k+2)].equals("1")) grid[i+1][j-(k+2)] = "2";         //A
                                        else if(grid[i+1][j-(k+2)].equals("2")) grid[i+1][j-(k+2)] = "3";

                                 if(grid[i+(k+2)][j-1].equals("0")) grid[i+(k+2)][j-1] = "1";
                                    else if(grid[i+(k+2)][j-1].equals("1")) grid[i+(k+2)][j-1] = "2";          //B
                                       else if(grid[i+(k+2)][j-1].equals("2")) grid[i+(k+2)][j-1] = "3";


                                 if(grid[i+1][j+(k+2)].equals("0")) grid[i+1][j+(k+2)] = "1";                 // movement in "L" south-east
                                    else if(grid[i+1][j+(k+2)].equals("1")) grid[i+1][j+(k+2)] = "2";         //A
                                       else if(grid[i+1][j+(k+2)].equals("2")) grid[i+1][j+(k+2)] = "3";

                                 if(grid[i+(k+2)][j+1].equals("0")) grid[i+(k+2)][j+1] = "1";                  //B
                                    else if(grid[i+(k+2)][j+1].equals("1")) grid[i+(k+2)][j+1] = "2";
                                        else if(grid[i+(k+2)][j+1].equals("2")) grid[i+(k+2)][j+1] = "3";

                                }

                            }

                    numberOfDetectedSensors++;
                }

            }
        }
        System.out.println("Maximum coverage:");
        print(4); //print the grid with 4 pixels spam
    }


    //get node
    public static GraphElements.MyVertex getNode(String name){
        GraphElements.MyVertex v = new GraphElements.MyVertex();
        for(GraphElements.MyVertex node : mGraph.getVertices()){
            if(node.getName().equals(name)){
                v = node;
            }
        }
        return v;
    }




    public void drawGrid(){
            //collVertex

        int numberOfLine = 1;
        int dropedSensorsXline = 1;
        int totalSensorsDropped = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                if((i == (numberOfLine * distanceBetweenSensors)) && (j == (dropedSensorsXline*distanceBetweenSensors))){
                    if ((i < rows-1) && (dropedSensorsXline <= numberOfSensorInALine) && (totalSensorsDropped < totalSensors)){
                        grid[i][j] = "X";
                        dropedSensorsXline++;
                        totalSensorsDropped++;
                    }  else if (i < rows-1) {
                        grid[i][j] = "0";
                        dropedSensorsXline=1;
                        numberOfLine++;
                //        System.out.println("grid=["+i+"]["+j+"]");
                //        System.out.println("Number of Line="+numberOfLine);
                    }   else {
                        grid[i][j] = "0";
                    }
                }
                else {
                    grid[i][j] = "0";
                }
            }
        }
      //  print(4); //print the grid with 4 pixels spam
       // return 0;
    }

    public void print(int width) {
        System.out.println(); // start on new line.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String s = String.valueOf(grid[i][j]); // format the number
                int padding = Math.max(1, width - s.length()); // At _least_ 1
                // space
                for (int k = 0; k < padding; k++) {
                    System.out.print(' ');
                }
                System.out.print(s);
            }
            System.out.println();
        }
        System.out.println(); // end with blank line.
    }
}
