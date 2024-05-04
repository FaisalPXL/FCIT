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
import java.util.Stack;
import java.util.Comparator;

// create KruskalAlgorithm class to create minimum spanning tree of the given graph
public class kruskal {
    private int[] parent;
    
    //constructor that makes set for each vertex
    public kruskal(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }
    
    //check's if both vertces in same set
    private int FindSet(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]]; 
            p = parent[p];
        }
        return p;
    }
    
    //union two sets 
    private void union(int p, int q) {
        int rootP = FindSet(p);
        int rootQ = FindSet(q);
        parent[rootP] = rootQ;
    }
    
    //creat stack to store weights values, and creat mst to store minnimum spanning tree
    public ArrayList<Edge> kruskalMST(ArrayList<Edge> edges) {
        Stack<Integer> weights = new Stack<>();
        ArrayList<Edge> mst = new ArrayList<>();
        
        // sort edges in ascending order of weight
        edges.sort(Comparator.naturalOrder());
        
        for (Edge e : edges) {
            int u = e.u;
            int v = e.v;
            int weight = e.weight;
            
            if (FindSet(u) != FindSet(v)) {
                // add edge to MST
                mst.add(e);
                weights.push(weight);
                union(u, v);
            }
        }
        
        // print sum weights of edges in addition to MST
        System.out.print("Total weight of MST by Kruskal's algorithm: ");
        int sum = 0;
        while (!weights.isEmpty()) {
            sum += weights.pop();
        }
        System.out.println(sum);
        
        return mst;
    }
}

