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

public class MinPriorityQueueUsingHeap extends MinPriorityQueue {

    private Edge[] heap;
    private int maxSize;
    private int heapSize;

    public MinPriorityQueueUsingHeap(int maxSize) {
        this.maxSize = maxSize;
        this.heap = new Edge[maxSize];
        this.heapSize = 0;
    }

    private int getLeftChild(int index) {
        return 2 * index + 1;
    }

    private int getRightChild(int index) {
        return 2 * index + 2;
    }

    private int getParent(int index) {
        if (index == 0) {
            return -1;
        }
        return (index - 1) / 2;
    }

    private void minHeapify(int index) {
        int smallest = index;
        int l = this.getLeftChild(index);
        int r = this.getRightChild(index);

        if (l < this.heapSize && this.heap[l].weight < this.heap[index].weight) {
            smallest = l;
        }
        if (r < this.heapSize && this.heap[r].weight < this.heap[smallest].weight) {
            smallest = r;
        }
        if (smallest != index) {
            swapArrayElements(this.heap, smallest, index);
            this.minHeapify(smallest);
        }
    }

    public Edge getMin() {

        return this.heap[0];
    }

    public Edge extractMin() {

        Edge ret = this.heap[0];
        this.heap[0] = this.heap[this.heapSize - 1];

        this.heapSize--;
        this.minHeapify(0);

        return ret;
    }

    public void decrement(int index, Edge newValue) {

        this.heap[index] = newValue;
        while (index > 0 && this.heap[index].weight < this.heap[this.getParent(index)].weight) {
            swapArrayElements(this.heap, index, this.getParent(index));
            index = this.getParent(index);
        }
    }

    public void insert(Edge val) {
        this.heapSize++;
        this.heap[this.heapSize - 1] = val;
        this.decrement(this.heapSize - 1, val);
    }

    private void swapArrayElements(Edge[] array, int index1, int index2) {
        Edge temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public boolean isEmpty() {
        return heapSize == 0;

    }
}
