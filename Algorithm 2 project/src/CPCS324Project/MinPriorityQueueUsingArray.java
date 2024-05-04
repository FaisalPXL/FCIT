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
 
public class MinPriorityQueueUsingArray extends MinPriorityQueue {

    private Edge[] array;
    private int currentElementIndex;
    private int maxSize;

    public MinPriorityQueueUsingArray(int queueSize) {
        array = new Edge[queueSize];
        maxSize = queueSize;
        currentElementIndex = 0;
    }

    @Override
    public void insert(Edge val) {
        array[currentElementIndex++] = val;
    }
    
    @Override
    public Edge extractMin() {
        int minEdgeIndex = -1;
        int minEdgeWeight = Integer.MAX_VALUE;
        for(int i = 0; i < currentElementIndex; i++){
            if(array[i].weight < minEdgeWeight){
                minEdgeWeight = array[i].weight;
                minEdgeIndex = i;
            }
        }
        swapArrayElements(array, minEdgeIndex, currentElementIndex-1);
        return array[--currentElementIndex];
    }

    public boolean isEmpty() {
        return currentElementIndex == 0;
    }
    private void swapArrayElements(Edge[] array, int index1, int index2) {
        Edge temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    @Override
    public Edge getMin() {
        return array[currentElementIndex - 1];
    }

}
