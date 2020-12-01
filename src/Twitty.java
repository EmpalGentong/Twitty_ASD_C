class Vertex {

    public char label; // label (e.g. 'A')
    public boolean wasVisited;

    public Vertex(char lab) // constructor
    {
        label = lab;
        wasVisited = false;
    }
}

class StackX {

    private final int SIZE = 20;
    private int[] st;
    private int top;

    public StackX() // constructor
    {
        st = new int[SIZE]; // make array
        top = -1;
    }

    public void push(int j) // put item on stack
    {
        st[++top] = j;
    }

    public int pop() // take item off stack
    {
        return st[top--];

    }

    public int peek() // peek at top of stack
    {
        return st[top];

    }

    public boolean isEmpty() // true if nothing on stack-
    {
        return (top == -1);
    }
}

class Queue {

    private final int SIZE = 20;
    private int[] queArray;
    private int front;
    private int rear;

    public Queue() {
        queArray = new int[SIZE];
        front = 0;
        rear = -1;
    }

    public void insert(int j) {
        if (rear == SIZE - 1) {
            rear = -1;
        }
        queArray[++rear] = j;
    }

    public int remove() {
        int temp = queArray[front++];
        if (front == SIZE) {
            front = 0;
        }
        return temp;
    }

    public boolean isEmpty() {
        return (rear + 1 == front || (front + SIZE - 1 == rear));
    }
}

class AdjacencyMatriksGraph {

    private final int MAX_VERTS = 20;
    private Vertex vertexList[];
    private int adjMat[][];
    private int nVerts;
    private StackX theStack;
    private Queue theQueue;

    public AdjacencyMatriksGraph() // constructor
    {
        vertexList = new Vertex[MAX_VERTS];
        // adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        for (int j = 0; j < MAX_VERTS; j++) // set adjacency
        {
            for (int k = 0; k < MAX_VERTS; k++) // matrix to 0
            {
                adjMat[j][k] = 0;
            }
        }
        theStack = new StackX();
        theQueue = new Queue();
    }

    public void addVertex(char lab) {
        vertexList[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end) {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }

    public int getAdjUnvisitedVertex(int v) {
        for (int j = 0; j < nVerts; j++) {
            if (adjMat[v][j] == 1 && vertexList[j].wasVisited == false) {
                return j;
            }
        }
        return -1;
    }

    public void dfs(int x) // depth-firstsearch
    { // beginatvertex0
        vertexList[x].wasVisited = true; //karna dimulai dari node x maka wasVisited di set true (sudah dikunjungi)
        displayVertex(x); // cetak vertex awal
        theStack.push(x); // push vertex awal ke stack
        while (!theStack.isEmpty()) // pada awal while, stack berisi vertex awal. Dan looping tidak berhenti hingga stack kosong
        {
            int v = getAdjUnvisitedVertex(theStack.peek()); // memanggil methodnya, dimana pasti mengembalikan nilai integer atau -1
            if (v == -1) // cek jika tidak ada vertex lagi maka stack di pop
            {
                theStack.pop();
            } else// jika ternyata masih ada vertex
            {
                vertexList[v].wasVisited = true;
                displayVertex(v); // displayit
                theStack.push(v); // pushit
            }
        } // endwhile
        for (int j = 0; j < nVerts; j++) // reset flags
        {
            vertexList[j].wasVisited = false;
        }
    }

    public void bfs(int x) // breadth-firstsearch
    { // beginatvertex0
        vertexList[x].wasVisited = true; // mark it
        displayVertex(x); // displayit
        theQueue.insert(x); // insertattail
        int v2;
        while (!theQueue.isEmpty()) {
            int v1 = theQueue.remove();
            // until it has no unvisited neighbors
            while ((v2 = getAdjUnvisitedVertex(v1)) != -1) { // get one,
                vertexList[v2].wasVisited = true; // mark it
                displayVertex(v2); // displayit
                theQueue.insert(v2); // insertit
            } // endwhile
        } // endwhile(queuenot empty)
        // queueis empty, sowe'redone
        for (int j = 0; j < nVerts; j++) // reset flags
        {
            vertexList[j].wasVisited = false;
        }
    }

    public void displayVertex(int v) {
        System.out.print(vertexList[v].label);
    }


}
public class Twitty{
    public static void main(String[] args) {
        AdjacencyMatriksGraph theGraph = new AdjacencyMatriksGraph();
        theGraph.addVertex('0');//0
        theGraph.addVertex('1');//1
        theGraph.addVertex('2');//2
        theGraph.addVertex('3');//3
        theGraph.addVertex('4');//4
        theGraph.addVertex('5');//5
        theGraph.addEdge(0, 1);
        theGraph.addEdge(0, 2);
        theGraph.addEdge(0, 3);
        theGraph.addEdge(1, 5);
        theGraph.addEdge(2, 4);
        System.out.print("Visits bfs: ");
        theGraph.bfs(0); // breadth-first search
        System.out.println();
        System.out.print("Visits dfs: ");
        theGraph.dfs(0); // depth-first search
        System.out.println();
    }
}