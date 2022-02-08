
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/*
Sumber kode:
https://www.geeksforgeeks.org/min-heap-in-java/
https://www.geeksforgeeks.org/binary-heap/
*/
class NodeList{
    public Node head;
    public Node tail;
    public int size;

    public NodeList(Node node){
        this.head = node;
        this.tail = node;
        this.size = 1;
    }

    public void add(Node node){
        Node location = this.head;
        //System.out.println(this.head + " " + this.tail);
        if(this.head == null && this.tail == null){
            this.head = node;
            this.tail = node;
        }
        else if(node.index < this.head.index){
            node.next = this.head;
            this.head.prev = node;
            this.head = node;
        }
        else if(node.index > this.tail.index){
            node.prev = this.tail;
            this.tail.next = node;
            this.tail = node;
        }
        else{
            while(location != null){
                if(node.index > location.index && node.index < location.next.index){
                    node.next = location.next;
                    node.prev = location;
                    location.next.prev = node;
                    location.next = node;
                    break;
                }
                location = location.next;
            }
        }
        this.size++;
    }
    public Node remove(Node kotak){
        if(this.head == kotak && this.tail == kotak){
            this.head = null;
            this.tail = null;
        }
        else if(this.head == kotak){
            kotak.next.prev = null;
            this.head = kotak.next;
            kotak.next = null;
        }
        else if(this.tail == kotak){
            kotak.prev.next = null;
            this.tail = kotak.prev;
            kotak.prev = null;
        }
        else{
            kotak.prev.next = kotak.next;
            kotak.next.prev = kotak.prev;
            kotak.prev = null;
            kotak.next = null;
        }
        this.size--;
        return kotak;
    }
    
}

class Node{
    public int value;
    public int index;
    public Node next;
    public Node prev;

    public Node(int value, int index){
        this.value = value;
        this.index = index;
    }
}

class MinHeap{
    // Member variables of this class
    public HashMap<Integer, NodeList> list_node = new HashMap<>();
    public int[] Heap;
    public int size;
    public int maxsize;
  
    // Initializaing front as static with unity
    private static final int FRONT = 1;
  
    // Constructor of this class
    public MinHeap(int maxsize){
  
        // This keyword refers to current object itself
        this.maxsize = maxsize;
        this.size = 0;
  
        Heap = new int[this.maxsize + 1];
        Heap[0] = Integer.MIN_VALUE;
        //Heap[0].value = Integer.MIN_VALUE;
    }
  
    // Method 1
    // Returning the position of
    // the parent for the node currently
    // at pos
    private int parent(int pos) { return pos / 2; }
  
    // Method 2
    // Returning the position of the
    // left child for the node currently at pos
    private int leftChild(int pos) { return (2 * pos); }
  
    // Method 3
    // Returning the position of
    // the right child for the node currently
    // at pos
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }
  
    // Method 5
    // To swap two nodes of the heap
    private void swap(int fpos, int spos){
  
        int tmp;
        tmp = Heap[fpos];
  
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    public void removeSpecificElement(int i) {
        if(i == 1) remove();
        else if(i == size){
            size--;
        }
        else{
            Heap[i] = Heap[size];
            size--;
            while (Heap[parent(i)] > Heap[i] && i > 1 ) {
                swap(i, parent(i));
                i = parent(i);
            }
        }
    }
  
    // Method 6
    // To heapify the node at pos
    public void minHeapify(int i){

        int l = leftChild(i);
        int r = rightChild(i);
        int smallest = i;
        if (l <= size && Heap[l] < Heap[i])
            smallest = l;
        if (r <= size && Heap[r] < Heap[smallest])
            smallest = r;
        if (smallest != i){
            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    public int Peek(){
        if(size == 0) return -1;
        return Heap[1];
    }

    // Method 7
    // To insert a node into the heap
    public void insert(int element, Node node){
        if (size >= maxsize) {
            return;
        }

        //System.out.print("xxx: " + element.value);
        //System.out.println();

        if(this.list_node.get(element) != null){
            this.list_node.get(element).add(node);
            return;
        }

        Heap[++size] = element;
        int current = size;
        this.list_node.put(element, new NodeList(node));
        this.checkPosition(current);

    }

    public void checkPosition(int current){
        while (Heap[current] < Heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public void sort(){
        int n = size;
        while(size > 1){
            int x = remove();
            Heap[n] = x;
            minHeapify(n);
        }
    }
  
    // Method 8
    // To print the contents of the heap
    public void print(){
        for (int i = 1; i <= size / 2; i++) {
  
            // Printing the parent and both childrens
            System.out.print(
                " PARENT : " + Heap[i]
                + " LEFT CHILD : " + Heap[2 * i]
                + " RIGHT CHILD :" + Heap[2 * i + 1]);
  
            // By here new line is required
            System.out.println();
        }
    }
  
    // Method 9
    // To remove and return the minimum
    // element from the heap
    public int remove(){
  
        int popped = Heap[FRONT];
        Heap[FRONT] = Heap[size--];
        minHeapify(FRONT);
  
        return popped;
    }

}

public class Lab6New9 {
    private static InputReader in;
    private static PrintWriter out;
    //public static HashMap<Integer, Integer> indexLocation = new HashMap<>();
    
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        MinHeap p = new MinHeap(200010);

        List<Node> tanah = new ArrayList<>();
        
        int N = in.nextInt();
        for (int i = 0; i < N; i++) {
            int height = in.nextInt();
            Node x = new Node(height, i);
            tanah.add(x);
            p.insert(height, x);
         }
        //p.print();
        //System.out.println(indexMin.get(4).keySet().toString());
        int Q = in.nextInt();
        while(Q-- > 0) {
            //for(int i = 0 ; i < tanah.size() ; i++) System.out.print(tanah.get(i) + " ");
            //System.out.println();
            //for(int i = 1 ; i <= p.size ; i++) System.out.print(p.Heap[i].value + " ");
            //System.out.println();
            String query = in.next();
            if (query.equals("A")) {
                // TODO: Handle query A
                int height = in.nextInt();
                Node x = new Node(height, tanah.size());
                tanah.add(x);
                p.insert(height, x);

            } else if (query.equals("U")) {
                int x = in.nextInt();
                int y = in.nextInt();
                int q = 0;
                
                Node oldNode = tanah.get(x);
                int oldHeight = tanah.get(x).value;
                //for(int i = 1 ; i <= p.size ; i++) System.out.print(p.Heap[i] + " ");
                //System.out.println();
                //for(int i = 0 ; i < tanah.size() ; i++) System.out.print(tanah.get(i).value + " ");
                //System.out.println();
                oldNode.value = y;
                tanah.set(x, oldNode);
                p.list_node.get(oldHeight).remove(oldNode);
                /*
                if(p.list_node.get(oldHeight).size == 0){
                    p.list_node.remove(oldHeight);
                    for(int i = 1 ; i <= p.size ; i++){
                        if(p.Heap[i] == oldHeight){
                            //System.out.println(oldHeight);
                            p.removeSpecificElement(i);
                            break;
                        }
                    }
                }
                */
                p.insert(y, oldNode);

            } else {
                while(p.list_node.get(p.Heap[1]).size == 0){
                    p.remove();
                }
                int min = p.Heap[1];

                Node minNode = p.list_node.get(min).remove(p.list_node.get(min).head);
                //System.out.println("X: "+ p.list_node.get(min.value).size);
                if(p.list_node.get(min).size == 0) {
                    p.remove();
                    p.list_node.remove(min);
                }

                int x = minNode.index;

                //System.out.println("VALUE: "  + p.list_node.get(8));

                if(tanah.size() == 1) {
                    out.println(tanah.get(0).value + " " + 0);
                    p.insert(tanah.get(0).value, tanah.get(0));
                }
                else if(x > 0 && x < tanah.size()-1){
                    Node left = tanah.get(x-1);
                    Node right = tanah.get(x+1);
                    int maxValue = 0;

                    if(left.value > right.value){
                        int oldValue = right.value;
                        maxValue = left.value;
                        p.list_node.get(right.value).remove(right);
/*
                        if(p.list_node.get(right.value).size == 0){
                            p.list_node.remove(oldValue);
                            for(int i = 1 ; i <= p.size ; i++){
                                if(p.Heap[i] == oldValue){
                                    p.removeSpecificElement(i);
                                    break;
                                }
                            }
                        }
                        */
                        right.value = maxValue;
                        p.insert(maxValue, right);
                    }
                    else if(left.value < right.value){
                        int oldValue = left.value;
                        maxValue = right.value;
                        p.list_node.get(left.value).remove(left);
/*
                        if(p.list_node.get(left.value).size == 0){
                            p.list_node.remove(oldValue);
                            for(int i = 1 ; i <= p.size ; i++){
                                if(p.Heap[i] == oldValue){
                                    p.removeSpecificElement(i);
                                    break;
                                }
                            }
                        }
                        */
                        left.value = maxValue;
                        p.insert(maxValue, left);
                    }
                    else{
                        maxValue = left.value;
                    }
                    minNode.value = maxValue;
                    p.insert(maxValue, minNode);
                    out.println(maxValue + " " + x);
                }
                
                else if(x < tanah.size()-1 && x != -1){
                    Node right = tanah.get(x+1);
                    int maxValue = right.value;
                    minNode.value = maxValue;
                    p.insert(maxValue, minNode);
                    out.println(maxValue + " " + x);
                }
                else if(x > 0 && x != tanah.size()){
                    Node left = tanah.get(x-1);
                    int maxValue = left.value;
                    minNode.value = maxValue;
                    p.insert(maxValue, minNode);
                    out.println(maxValue + " " + x);
                }
                else {
                    out.println(tanah.get(x).value + " " + x);
                    p.insert(tanah.get(x).value, minNode);
                }
                
            }
            //for(int i = 0 ; i < tanah.size() ; i++) System.out.print(tanah.get(i).value + " ");
            //System.out.println();
            //System.out.println("xxxxxxxxxxxxxxx");
            //for(int i = 1 ; i <= p.size ; i++) System.out.print(p.Heap[i] + " ");
            //System.out.println();
            /*
            for(int i = 1 ; i <= p.size ; i++){
                System.out.println("TINGGI: " + p.Heap[i]);
                Node location = p.list_node.get(p.Heap[i]).head;
                System.out.println(p.list_node.get(p.Heap[i]).head.index + " " + p.list_node.get(p.Heap[i]).tail.index);
                while(location != null){
                    System.out.print(location.index + " ");
                    location = location.next;
                }
                System.out.println();
            }
            System.out.println("---------------");
            */
        }

        out.flush();
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}

// disclaimer : punya eky