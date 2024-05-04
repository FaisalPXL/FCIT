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

public class PrimAlgorithm {

    public static ArrayList<Edge> findMST(Graph graph, int source, QueueType queueType) {
        if (queueType == QueueType.ARRAY) {
            return findMST(graph, source, new MinPriorityQueueUsingArray(graph.getNumberOfEdges()));

        } else {
            return findMST(graph, source, new MinPriorityQueueUsingHeap(graph.getNumberOfEdges()));
        }
    }

    private static ArrayList<Edge> findMST(Graph graph, int source, MinPriorityQueue queue) {
        boolean[] visited = new boolean[graph.getnVerts()];
        visited[source] = true;
        ArrayList<Edge> includedEdges = new ArrayList<>();
        MinPriorityQueue priorityQueue = queue;
        Vertex[] verticesList = graph.getVertexList();
        for (Edge edge : verticesList[source].neighbors) {
            priorityQueue.insert(edge);
        }
        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.extractMin();
            if (visited[edge.v]) {
                continue;
            }
            visited[edge.v] = true;
            includedEdges.add(edge);
            for (Edge neighbor : verticesList[edge.v].neighbors) {
                priorityQueue.insert(neighbor);
            }
        }
        return includedEdges;
    }
}
