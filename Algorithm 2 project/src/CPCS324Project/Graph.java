/*
CPCS 324 GROUP PROJECT
SECTION CS2
Students:
Faisal Hussain Al Ragheeb 
2136580
fmalragheeb@stu.kau.edu.sa
Fahd Adel Alghamdi
2135938
fsalghamdi0001@stu.kau.edu.sa
Zayed Mohammed
2137085
zabdulhamidmohammed@stu.kau.edu.sa
Faisal Ahmed Balkhair
2136412
Fabalkhair@stu.kau.edu.sa
*/

package CPCS324Project;

import java.util.ArrayList;

public class Graph //Class to implement graph
{

    private final int MAX_VERTS = 20;
    private Vertex vertexList[]; // Array of vertices as objects of class Vertex
    private int nVerts; // current number of vertices

// -----------------------------------------------------------
    public Graph() // constructor
    {
        nVerts = 0;
        vertexList = new Vertex[MAX_VERTS];

    } // end constructor
// -----------------------------------------------------------

    public int getnVerts() {
        return nVerts;
    }

    public int getNumberOfEdges() {
        return 2 * (this.nVerts * (this.nVerts - 1) / 2);
    }

    public Vertex[] getVertexList() {
        return vertexList;
    }

    public Vertex getFirstVertex() {
        return vertexList[0];
    }

    public int getMAX_VERTS() {
        return MAX_VERTS;
    }

    public void addVertex(int lab) {
        if (vertexList.length != nVerts) {
            vertexList[nVerts] = new Vertex(lab);
            nVerts++;
        }
    }
// -----------------------------------------------------------

    public void addEdge(int start, int end, int weight) {
        for (int i = 0; i < nVerts; i++) {
            if (vertexList[i].label == start) {
                vertexList[i].neighbors.add(new Edge(start, end, weight));
            } else if (vertexList[i].label == end) {
                vertexList[i].neighbors.add(new Edge(end, start, weight));
            }
        }
    }
// ------------------------------------------------------------

    public void displayVertex(int v) {
        System.out.println(vertexList[v].label);
    }

// ------------------------------------------------------------
}

// ------------------------------------------------------------
class Vertex //Class to implement vertices of the graph
{

    public int label;
    public boolean wasVisited;
    ArrayList<Edge> neighbors;
    // ------------------------------------------------------------

    public Vertex(int label) // constructor
    {
        this.label = label;
        wasVisited = false;
        neighbors = new ArrayList<>();
    }

    // ------------------------------------------------------------
} // end of class Vertex
