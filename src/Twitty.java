import java.io.*;
import java.sql.SQLOutput;


class Vertex {

    public String label; // label (e.g. 'A')
    public boolean wasVisited;

    public Vertex(String lab) // constructor
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
    int adjMat[][];
    int followed [][];
    int nVerts =0;
    private StackX theStack;
    private Queue theQueue;
    int numCon =0;

    public AdjacencyMatriksGraph() // constructor
    {
        vertexList = new Vertex[MAX_VERTS];
        // adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        followed = new int [MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        for (int j = 0; j < MAX_VERTS; j++) // set adjacency
        {
            for (int k = 0; k < MAX_VERTS; k++) // matrix to 0
            {
                followed[j][k]=0;
                adjMat[j][k] = 0;
            }
        }
        theStack = new StackX();
        theQueue = new Queue();
    }

    public void getVtxData(){
        int src = 0;
        while(src<nVerts){
            System.out.println(vertexList[src].label);
            src++;
        }
    }

    public void addVertex(String lab) {
        vertexList[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end) {
        adjMat[start][end] = 1;
        followed[end][start] = 1;
        numCon++;
    }
    public void follow(String name1, String name2){
        int counter1=0, counter2=0;
        for(int src = 0;src<vertexList.length;src++){
            if(vertexList[src].label.equalsIgnoreCase(name1)){
                counter1 = src;
                break;
            }

        }
        for(int src = 0;src<vertexList.length;src++){
            if(vertexList[src].label.equalsIgnoreCase(name2)){
                counter2 = src;
               break;
            }
        }
        addEdge(counter1,counter2);
    }

    public String mostFoll(){
        int temp = 0;
        int [] sumFoll = new int [followed.length];
        for(int i = 0;i<followed.length;i++){
            for(int j=0;j<followed.length;j++){
                if(followed[i][j] == 1){
                    sumFoll[i]++;
                }
            }
        }
        for(int i =0;i<sumFoll.length;i++){
            if(i==0){
                temp = i;
            }
            else{
                if(temp < sumFoll[i]){
                    temp=i;
                }
            }
        }
        return(vertexList[temp].label);
    }

    public int getAdjUnvisitedVertex(int v) {
        for (int j = 0; j < nVerts; j++) {
            if (adjMat[v][j] == 1 && vertexList[j].wasVisited == false) {
                return j;
            }
        }
        return -1;
    }
    public void numGroup(){
        int peop = nVerts;
        int numG = 0;
        dfs(0);
        for(int i=0;i<nVerts;i++){
            if(vertexList[i].wasVisited == false){
                numG++;
                dfs(i);
            }
        }
        System.out.println(numG);
        for (int j = 0; j < nVerts; j++) // reset flags
        {
            vertexList[j].wasVisited = false;
        }
    }

    public void dfs(int x) // depth-firstsearch
    { // beginatvertex0
        vertexList[x].wasVisited = true; //karna dimulai dari node x maka wasVisited di set true (sudah dikunjungi)
        //displayVertex(x); // cetak vertex awal
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
                //displayVertex(v);
                theStack.push(v); // pushit
            }
        } // endwhile

    }


    public int dfs2(int x,int y, String b) // depth-firstsearch
    { // beginatvertex0
        int minrt = -1;
        int index = 0;
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
                if(vertexList[v].label.equalsIgnoreCase(b)){
                    //dikurangi destinasi;
                    System.out.println("break");
                    break;
                }
                if(adjMat[v][y]==1){
                    index = v;
                }
                if(minrt >nVerts ){
                    System.out.println(" heyyo -> ");
                    minrt = -1;
                    break;
                }
                minrt++;
                displayVertex(v);
                theStack.push(v); // pushit
            }

        }
        if(adjMat[index][y]==1){
            minrt -= 1;
        }
        return minrt;
    }

    public int minrt(String a,String b) {
        int counter1=0, counter2=0;
        for(int src = 0; src<vertexList.length;src++){
            if(vertexList[src].label.equalsIgnoreCase(a)) {
                counter1 = src;
                break;
            }
        }
        for(int src = 0; src<vertexList.length;src++){
            if(vertexList[src].label.equalsIgnoreCase(b)){
                counter2 = src;
                break;
            }
        }
        int x = dfs2(counter1,counter2, b);
        for (int j = 0; j < nVerts; j++) // reset flags
        {
            vertexList[j].wasVisited = false;
        }
        return x;
    }

    public void bfs(int x) // breadth-firstsearch
    { // beginatvertex0
        vertexList[x].wasVisited = true; // mark it
        //displayVertex(x); // displayit
        theQueue.insert(x); // insertattail
        int v2;
        while (!theQueue.isEmpty()) {
            int v1 = theQueue.remove();
            // until it has no unvisited neighbors
            while ((v2 = getAdjUnvisitedVertex(v1)) != -1) { // get one,
                vertexList[v2].wasVisited = true; // mark it
                //displayVertex(v2); // displayit
                theQueue.insert(v2); // insertit
            } // endwhile
        } // endwhile(queuenot empty)
        for (int j = 0; j < nVerts; j++) // reset flags
        {
            vertexList[j].wasVisited = false;
        }
    }

    public void displayVertex(int v) {
        System.out.print(vertexList[v].label + " -> ");
    }

    public void compare(){
        for(int i = 0;i< vertexList.length-2;i++){
            displayVertex(i);
        }
        System.out.println("");
        bfs(0);
    }
    }

public class Twitty{
    public static void main(String[] args) throws IOException{
        AdjacencyMatriksGraph theGraph = new AdjacencyMatriksGraph();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int tot_User, tot_cnctn,query1,counter =0;
        String input;
        tot_User = Integer.parseInt(read.readLine());
        tot_cnctn = Integer.parseInt(read.readLine());
        query1 = tot_cnctn+tot_User;
        while(counter<query1){
            String [] temp = read.readLine().split(" ");
            if(counter < tot_User){
                theGraph.addVertex(temp[0]);
            }
            else{
                System.out.println(temp[0] + " "+ temp[1]);
                theGraph.follow(temp[0],temp[1]);
            }
           counter++;
        }
        while((input = read.readLine()) != null){
            String query[] = input.split(" ");
            if(query[0].equalsIgnoreCase("mostfollowed")){
                System.out.println(theGraph.mostFoll());
            }
            else if(query[0].equalsIgnoreCase("insert")){
                theGraph.addVertex(query[1]);
                System.out.println(query[1] + " inserted");
            }
            else if(query[0].equalsIgnoreCase("numgroup")){
                theGraph.numGroup();
            }
            else if(query[0].equalsIgnoreCase("connect")){
                theGraph.follow(query[1],query[2]);
                System.out.printf("connect %s %s success\n",query[1],query[2]);
            }
            else if(query[0].equalsIgnoreCase("minrt")){
                System.out.println("minrt : " +theGraph.minrt(query[1],query[2]));
                System.out.println(theGraph.minrt("graphy","hadiyan"));
            }
        }
    }
}