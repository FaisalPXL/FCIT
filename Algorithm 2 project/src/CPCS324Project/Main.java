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

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Random;
import java.io.PrintWriter;
import java.util.PriorityQueue;


public class Main {
    private static HashMap<Character, Integer> shiftTable;
    public static void main(String[] args) throws Exception {
        //print menu and recive user's choice
        Scanner in = new Scanner(System.in);
        
        //check what algorithm the user choose and call it
        boolean flag = true;
        while(flag){
            System.out.print(   "1. Comparison between Horspool and Brute force algorithms\n" +
                            "2. Finding minimum spanning tree using Prim’s algorithm\n" +
                            "3. Finding minimum spanning tree using Kruskal’s algorithm\n" +
                            "4. Finding shortest path using Dijkstra’s algorithm\n" +
                            "5. Quit\n" + 
                            "Enter your choice: ");
            int choice = in.nextInt();
            switch (choice){
                case 1:
                    callHorspool();
                    break;
                case 2:
                    primAlgorithm();
                    break;
                case 3:
                    callkruskal();
                    break;
                case 4:
                    callDijkstra();
                    break;
                case 5:
                    System.out.println("thank you :)");
                    flag = false;
                    break;
                default:
                    System.out.print("\nPlease choose from 1 to 5: ");
                    System.out.println("");
            }
        }
        System.exit(0);
    }
    
    //Horspool algorithm
    public static void callHorspool() throws Exception {
        Scanner input = new Scanner(System.in);
        File inputFile = new File("input.txt");
        Scanner read = new Scanner(inputFile);
        File outputFile = new File("patterns.txt");
        PrintWriter print = new PrintWriter(outputFile);
        //---------------------------------------------
        comparison(input,read,print);
    }
    
    public static String readText(int numOfLines, Scanner read){
        StringBuilder readTxt = new StringBuilder();
        for(int i=0;i<numOfLines;i++){
            if(read.hasNextLine()){
                readTxt.append(read.nextLine());
            }
        }
        return readTxt.toString().toLowerCase();
    }
    //-------------------------------------------------------------------
    //method to generate pattern-----------------------------------------
    public static String[] generatePattern(int numOfLines,int numOfPatterns, int lengthOfPattern,String text){
        String[]pattern = new String[numOfPatterns];
        Random random = new Random();
        for(int i=0;i<numOfPatterns;i++){
            int number = random.nextInt(text.length()-lengthOfPattern);
            pattern[i] = text.substring(number, number+lengthOfPattern);
        }
        System.out.println("\n\n" + numOfPatterns+" patterns, each of length "+lengthOfPattern+" have been generated in a file pattern.txt");
        return pattern;
    }
    //-------------------------------------------------------------------
    //method to shift tables---------------------------------------------
    public static void shiftTable(String pattern,int lengthOfPattern,String text){
    
        for(int i=0;i<=lengthOfPattern-2;i++){
            shiftTable.put(pattern.charAt(i), lengthOfPattern-1-i);
        }
        System.out.println(shiftTable);    
    }
    //-------------------------------------------------------------------
    //brute force algorithm----------------------------------------------
    public static int bruteForce(String text, String pattern){
        
        for(int i=0;i<text.length()-pattern.length();i++){
            int j=0;
            while((j<pattern.length())&&(pattern.substring(j, j+1).equals(text.substring(i+j, i+j+1)))){
                j++;
            }
            if(j==pattern.length())
                return i;               
        }
        return -1;
    }
    //-------------------------------------------------------------------
    //horspool algorithm---------------------------------------------------
    public static int horspool(String pattern,String text, int lengthOfPattern ){
        int i = lengthOfPattern - 1;
        while(i<=text.length()-1){
            int k = 0;
            while((k<=lengthOfPattern-1) && (pattern.charAt(lengthOfPattern-1-k)==(text.charAt(i-k)))){
                k++;
            }
            if(k==lengthOfPattern)
                return i-lengthOfPattern+1;
            else
                i = i + shiftTable.getOrDefault(text.charAt(i), pattern.length());//shiftTable(pattern[i], lengthOfPattern, text);
        }
        return -1;
    }
    //-------------------------------------------------------------------
    //comparison between horspool and brute force algorithms-------------
    public static void comparison(Scanner input,Scanner read,PrintWriter print) {
         //-------------------------------------------------------------------------
        System.out.println("\nHow many lines you want to read from the text file?");
        int numOfLines = input.nextInt(); //user enter number of text's lines
        System.out.println("\nWhat is the length of each pattern?");
        int lengthOfPattern = input.nextInt(); //user enter the length of the patterns
        System.out.println("\nHow many patterns to be generated?");
        int numOfPatterns = input.nextInt(); //user enter number of patterns
        //-------------------------------------------------------------------------
        
        shiftTable = new HashMap<>();
        String text = readText(numOfLines, read);
        String[]pattern = generatePattern(numOfLines, numOfPatterns, lengthOfPattern, text);
        for(int j=0;j<pattern.length;j++){
            print.println(pattern[j]);
        }
        print.close();
        ArrayList<Long> horspoolTime = new ArrayList<>();
        ArrayList<Long> bruteForceTime = new ArrayList<>();
        long startTime, endTime, timeTaken;

        for (int i=0;i<pattern.length;i++) {
            shiftTable.clear();
            System.out.println("\nThe shift table for \""+pattern[i]+"\" is:");
            shiftTable(pattern[i], lengthOfPattern, text);
            //horspool time-------------------
            startTime = System.nanoTime();
            horspool(pattern[i], text, lengthOfPattern);
            endTime = System.nanoTime();
            timeTaken = endTime - startTime;
            horspoolTime.add(timeTaken);
            //---------------------------------
            //brute force time-----------------
            startTime = System.nanoTime();
            bruteForce(text,pattern[i]);
            endTime = System.nanoTime();
            timeTaken = endTime - startTime;
            bruteForceTime.add(timeTaken);
        }
        System.out.println("\nAverage time of search in Brute Force Approach: "+getaverage(bruteForceTime));
        System.out.println("\nAverage time of search in Horspool Approach: "+getaverage(horspoolTime));

        if (getaverage(horspoolTime) > getaverage(bruteForceTime)) {
            System.out.println("\nFor this instance Brute Force approach is better than Horspool approach");
        } else {
            System.out.println("\nFor this instance Horspool approach is better than Brute Force approach\n");
        }
    }
    //-------------------------------------------------------------------
    //average time-------------------------------------------------------
    public static long getaverage(ArrayList<Long> timeTaken) {
        long sum = 0;
        for (int i = 0; i < timeTaken.size(); i++) {
            sum = timeTaken.get(i) + sum;
        }
        return sum / timeTaken.size();
    }
    
    //call kruskal class to apply the algorithm 
    public static void callkruskal() throws Exception {
        File input1 = new File("input1.txt");
        Scanner in = new Scanner(input1);
        while(in.hasNext()){
            //store number of vertces and edges, store edges info in arraylist
            int numOfVer = in.nextInt();
            int numOfEdg = in.nextInt();
            ArrayList<Edge> edges = new ArrayList<>();
            for(int i=0; i < numOfEdg; i++)
                edges.add(new Edge(in.nextInt(), in.nextInt(), in.nextInt()));
            
            //calculate the running time for this algorithm, and invoke the algorithm
            long startTime = System.nanoTime();
            kruskal kruskal = new kruskal(numOfVer);
            ArrayList<Edge> mst = kruskal.kruskalMST(edges);
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            
            //print the minnimum spanning tree
            System.out.println("The edges in the MST are:");
            for (Edge e : mst) {
                System.out.println("Edge from " + e.u + " to " + e.v + " has weight " + e.weight);
            }
            System.out.println();
            System.out.println("Running Time of Kruskal’s algorithm using Union-Find approach is " + timeTaken + " Nano seconds.\n");
        }
    }
    
    //prim's algorithm
    private static void primAlgorithm() throws Exception {
        //File and input
        File input1 = new File("input1.txt");
        Scanner in = new Scanner(input1);
        Graph graph = new Graph();
        //Getting vertices and edges from the file
        int vertices = getVertices(in);
        int edges = getEdges(in);
        //Adding the vertices and edges to the graph
        addVerticesToGraph(vertices, graph);
        addEdgesToGraph(edges, in, graph);
        //Printing, once for min-prio array and once for min-heap
        printPrim(graph, 0, QueueType.ARRAY);
        printPrim(graph, 0, QueueType.HEAP);
    }

    //print function
    private static void printPrim(Graph graph, int source, QueueType queueType) {
        //Start calculating time
        long startTime = System.nanoTime();
        
        //Calculate the primAlgorithm in PrimAlgorithm class
        ArrayList<Edge> edges = PrimAlgorithm.findMST(graph, source, queueType);
        
        //End calculating time and calculate it
        long endTime = System.nanoTime();
        long timeTaken = endTime - startTime;
        
        //Print the cost
        int totalCost = getTotalCost(edges);
        
        //Print min-prio array
        if (queueType == QueueType.ARRAY) {
            printTotalCost(totalCost, "Prim's algorithm (Using unordered Min-Priority queue)");
        } else { //Print min-prio queue
            printTotalCost(totalCost, "Prim's algorithm (Using Min-Heap)");
        }
        
        //Print the edges
        printEdges(edges);
        if (queueType == QueueType.ARRAY) {
            printTimeTaken(timeTaken, "Prim's algorithm (Using unordered Min-Priority queue)");
        } else {
            printTimeTaken(timeTaken, "Prim's algorithm (Using Min-Heap)");
        }
    }

    //Formatting the time taken print
    private static void printTimeTaken(long timeTaken, String algorithmName) {
        System.out.printf("\nRunning time of %s is %d Nano seconds\n\n", algorithmName, timeTaken);
    }

    //Formatting the edges print
    private static void printEdges(ArrayList<Edge> edges) {
        System.out.println("The edges in the MST are: ");
        edges.forEach((edge) -> {
            System.out.printf("Edge from %d to %d has weight %d.0\n", edge.u, edge.v, edge.weight);
        });
    }

    //Formatting the total cost print
    private static void printTotalCost(int totalCost, String algorithmName) {
        System.out.printf("Total weight of MST by %s: %d.0\n", algorithmName, totalCost);
    }

    //Getting the total cost for all edges
    private static int getTotalCost(ArrayList<Edge> edges) {
        int totalCost = 0;
        for (Edge edge : edges) {
            totalCost += edge.weight;
        }
        return totalCost;
    }

    //Getting vertices from file
    public static int getVertices(Scanner in) {
        return Integer.parseInt(in.nextLine());
    }

    //Getting edges from file
    public static int getEdges(Scanner in) {
        return Integer.parseInt(in.nextLine());
    }

    //Adding edges to the graph
    public static void addEdgesToGraph(int numberOfEdges, Scanner in, Graph graph) {
        for (int i = 0; i < numberOfEdges; i++) {
            String[] edgeInfo = in.nextLine().split(" ");
            graph.addEdge(Integer.parseInt(edgeInfo[0]), Integer.parseInt(edgeInfo[1]), Integer.parseInt(edgeInfo[2]));
        }
    }

    //Adding vertices to the graph
    public static void addVerticesToGraph(int numberOfVertices, Graph graph) {
        for (int i = 0; i < numberOfVertices; i++) {
            graph.addVertex(i);
        }
    }
    
    
    //Dijkstra algorithm
    public static void callDijkstra() throws Exception {
        File input2 = new File("input2.txt");
        Scanner in = new Scanner(input2);
        Scanner OnScreen = new Scanner(System.in);

        int numOfVertices = in.nextInt();
        int numOfEdges = in.nextInt();
        int[][] adjMatrix = new int[numOfVertices][numOfVertices];

        for (int i = 0; i < numOfEdges; i++) {
            adjMatrix[in.nextInt()][in.nextInt()] = in.nextInt();
        }

        System.out.println("Weight Matrix:");
        printMatrix(numOfVertices, adjMatrix);

        System.out.println("# of vertices is: " + numOfVertices + ", # of edges is: " + numOfEdges);
        printGraph(numOfVertices, adjMatrix);

        System.out.println();
        System.out.print("Enter Source vertex: ");
        int source = OnScreen.nextInt();
        System.out.print("\n");
        

        long startTime1 = System.nanoTime();
        dijkstraUnorderedArray(source, numOfVertices, adjMatrix);
        long endTime1 = System.nanoTime();
        long time1 = endTime1 - startTime1;
        
        System.out.print("\n");
        
        long startTime2 = System.nanoTime();
        dijkstraMinHeap(source, numOfVertices, adjMatrix);
        long endTime2 = System.nanoTime();
        long time2 = endTime2 - startTime2;
        

        System.out.print("\n");
        System.out.println();
        
        System.out.println("Comparison Of the running time:");
        System.out.println("Running time of Dijkstra using priority queue is: " + time1 + " nano seconds");
        System.out.println("Running time of Dijkstra using min Heap is: " + time2 + " nano seconds");
        if (time1 < time2) {
            System.out.println("Running time of Dijkstra using priority queue is better");
        } else {
            System.out.println("Running time of Dijkstra using min Heap is better\n");
        }
    }
    
    public static void printMatrix(int v, int[][] adjMatrix) {
        System.out.print("  ");
        int i = 0;
        while (i < v) {
            System.out.print(i + " ");
            i++;
        }
        System.out.println();
        System.out.println("");
        i = 0;
        while (i < v) {
            System.out.print(i + " ");
            for (int j = 0; j < v; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
            i++;
        }
        System.out.println("");
    }
    
    public static void printGraph(int v, int[][] adjMatrix) {
        int i = 0;
        while (i < v) {
            System.out.print(i + ": ");
            boolean hasEdges = false;
            for (int j = 0; j < v; j++) {
                if (adjMatrix[i][j] != 0) {
                    if (hasEdges) {
                        System.out.print(" ");
                    }
                    System.out.print(i + "-" + j + " " + adjMatrix[i][j]);
                    hasEdges = true;
                }
            }
            System.out.println();
            i++;
        }
    }
    
    public static void dijkstraUnorderedArray(int src, int v, int[][] adjMatrix) {
        double[] distance = new double[v];
        boolean[] visited = new boolean[v];
        int i = 0;
        for (; i < v;) {
            distance[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
            i++;
        }

        distance[src] = 0;

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new Node(src, 0));

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            int u = node.vertex;

            if (visited[u]) {
                continue;
            }

            visited[u] = true;

            for (int j = 0; j < v; j++) {
                if (adjMatrix[u][j] != 0 && distance[u] + adjMatrix[u][j] < distance[j]) {
                    distance[j] = distance[u] + adjMatrix[u][j];
                    priorityQueue.offer(new Node(j, distance[j]));
                }
            }
        }

        System.out.println("Dijkstra using priority queue:");
        printShortestPaths(src, distance, v, adjMatrix);
    }
    
    public static void dijkstraMinHeap(int src, int v, int[][] adjMatrix) {
        double[] distance = new double[v];
        boolean[] visited = new boolean[v];
        int i = 0;
        while (i < v) {
            distance[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
            i++;
        }

        distance[src] = 0;

        PriorityQueue<Node> minHeap = new PriorityQueue<>();
        minHeap.offer(new Node(src, 0));
        while (!minHeap.isEmpty()) {
            Node node = minHeap.poll();
            int u = node.vertex;
            visited[u] = true;

            for (int j = 0; j < v; j++) {
                if (!visited[j] && adjMatrix[u][j] != 0 && distance[u] + adjMatrix[u][j] < distance[j]) {
                    distance[j] = distance[u] + adjMatrix[u][j];
                    minHeap.offer(new Node(j, distance[j]));
                }
            }
        }

        System.out.println("Dijkstra using min heap:");
        printShortestPaths(src, distance, v, adjMatrix);
    }
    
    private static void printShortestPaths(int src, double[] distance, int v, int[][] adjMatrix) {
        System.out.println("Shortest paths from vertex " + src + " are:");
        for (int i = 0; i < v; i++) {
            if (i != src) {
                ArrayList<Integer> path = new ArrayList<>();
                int parent = getParent(distance, i, v, adjMatrix);
                while (parent != -1) {
                    path.add(0, parent);
                    parent = getParent(distance, parent, v, adjMatrix);
                }
                path.add(i);

                System.out.print("A path from " + src + " to " + i + ": ");
                for (int j = 0; j < path.size(); j++) {
                    System.out.print(path.get(j));
                    if (j != path.size() - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println(" (Length: " + distance[i] + ")");
            } else {
                System.out.println("A path from " + src + " to " + i + ": " + src + " (Length: 0.0)");
            }
        }
    }
    
    private static int getParent(double[] distance, int vertex, int v, int[][] adjMatrix) {
        if (distance[vertex] == Double.POSITIVE_INFINITY) {
            return -1;
        }
        int i = 0;
        while (i < v) {
            if (adjMatrix[i][vertex] != 0 && distance[i] + adjMatrix[i][vertex] == distance[vertex]) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
