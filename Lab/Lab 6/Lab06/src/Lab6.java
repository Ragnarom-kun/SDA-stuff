//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.*;
//import static java.lang.Math.min;
//import static java.lang.Math.max;
//
//public class Lab6 {
//    private static InputReader in;
//    private static PrintWriter out;
//    static HashMap<Integer, LinkedList> hashDataran = new HashMap<>();  // hashmap dgn dataran yg tinggi kembar
//    static MinHeap minHeap = new MinHeap();
//    static LinkedList kumpulanDataran;
//    static ArrayList<Node> tanah;
//
//    public static void main(String[] args) {
//        InputStream inputStream = System.in;
//        in = new InputReader(inputStream);
//        OutputStream outputStream = System.out;
//        out = new PrintWriter(outputStream);
//
//        tanah = new ArrayList<>();
//
//        int N = in.nextInt();
//        for (int i = 0; i < N; i++) {
//            int height = in.nextInt();
//            // bikin object dataran
//            Node dataran = new Node(height,i);
//            // for LL
//            if (hashDataran.containsKey(height)) {  // jika sudah ada heightnya
//                kumpulanDataran = hashDataran.get(height);
//            } else {
//                kumpulanDataran = new LinkedList();
//                // for heap
//                minHeap.insert(height);
//            }
//            kumpulanDataran.insert(dataran);
//            hashDataran.put(height,kumpulanDataran);
//            // for arraylist asli
//            tanah.add(dataran);
//
////            // debug
////            kumpulanDataran.printValue();
////            out.print("Isi arraylist : ");
////            for (int z=0; z<tanah.size(); z++) {
////                out.print(tanah.get(z).value + ", ");
////            }
////            out.println();
//        }
//
//        int Q = in.nextInt();
//        while(Q-- > 0) {
//            String query = in.next();
//            if (query.equals("A")) {
//                // TODO: Handle query A
//                int height = in.nextInt();
//                // bikin object dataran
//                Node dataran = new Node(height,tanah.size());
//                // for LL
//                if (hashDataran.containsKey(height)) {
//                    kumpulanDataran = hashDataran.get(height);
//                } else {
//                    kumpulanDataran = new LinkedList();
//                    // for heap
//                    minHeap.insert(height);
//                }
//                kumpulanDataran.insert(dataran);
//                hashDataran.put(height,kumpulanDataran);
//                // for arraylist asli
//                tanah.add(dataran);
//
//
////                // debug
////                kumpulanDataran.printValue();
////                out.print("Isi arraylist : ");
////                for (int z=0; z<tanah.size(); z++) {
////                    out.print(tanah.get(z).value + ", ");
////                }
////                out.println();
//
//            } else if (query.equals("U")) {
//                // TODO: Handle query U
//                int index = in.nextInt();
//                int height = in.nextInt();
////                int tempHeight = tanah.get(index);
//
//                // update array list && LL
//                Node temp = tanah.get(index);
//                kumpulanDataran = hashDataran.get(temp.value);
//                Node dataran = kumpulanDataran.remove(temp);    // remove dulu
//                dataran.value = height;
//
//                // for LL
//                if (hashDataran.containsKey(height)) {
//                    kumpulanDataran = hashDataran.get(height);
//                    kumpulanDataran.insert(dataran);
//                    hashDataran.put(height,kumpulanDataran);
//                } else {
//                    kumpulanDataran = new LinkedList();
//                    minHeap.insert(height); // update heap kalau element blm ada
//                    kumpulanDataran.insert(dataran);
//                    hashDataran.put(height,kumpulanDataran);
//                }
//
//                tanah.set(index,dataran);
//                // debug
////                kumpulanDataran.printValue();
////                out.print("Isi arraylist : ");
////                for (int z=0; z<tanah.size(); z++) {
////                    out.print(tanah.get(z).value + ", ");
////                }
////                out.println();
//
//            } else {
//                // TODO: Handle query R
//                if (tanah.size()==1) {  // cuma 1 element
//                    out.println(tanah.get(0).value + " " + 0);
//                } else {
//                    // buang yg ga ada di LL walau ada di minElement Heap
//                    while (hashDataran.get(minHeap.Heap[1]).size == 0) {    // Heap[1] = element minimal
//                        minHeap.remove();
//                    }
//
//                    int minElement = minHeap.Heap[1];
//                    kumpulanDataran = hashDataran.get(minElement);
//                    Node dataran = kumpulanDataran.remove(kumpulanDataran.head);    // ambil head, trs remove dari LL
//
//                    // utk update heap
//                    if (kumpulanDataran.size==0) {      // LL.size==0, remove dari heap
//                        minHeap.remove();  // element terkecil
//                    }
//
//                    // index min untuk cari max value dari tetangga"nya
//                    int indexMin = dataran.index;
//                    int indexLeft = indexMin-1;
//                    int indexRight = indexMin+1;
//                    int maxElement = 0;
//
//                    if (indexMin==0) {      // paling kiri
//                        Node right = tanah.get(indexRight);
//                        int baru = Math.max(dataran.value,right.value);
//                        maxElement = baru;
//
//                        if (baru == dataran.value) {        // max == value indexMin
//                            kumpulanDataran = hashDataran.get(right.value);
//                            Node node = kumpulanDataran.remove(right); // remove
//                            node.value = baru;
//                            kumpulanDataran = hashDataran.get(baru);    // baru insert
//                            kumpulanDataran.insert(node);
//                            hashDataran.put(node.value,kumpulanDataran);
//                            tanah.set(indexRight,node);
//                        } else {    // max = rightIndex
//                            kumpulanDataran = hashDataran.get(dataran.value);
//                            Node node = kumpulanDataran.remove(dataran); // remove
//                            node.value = baru;
//                            kumpulanDataran = hashDataran.get(baru);    // baru insert
//                            kumpulanDataran.insert(node);
//                            hashDataran.put(node.value,kumpulanDataran);
//                            tanah.set(indexMin,node);
//                        }
//
//                    } else if (indexMin==tanah.size()-1) { // paling kanan
//                        Node left = tanah.get(indexLeft);
//                        int baru = Math.max(dataran.value,left.value);
//                        maxElement = baru;
//
//                        if (baru==dataran.value) {      // max == value indexMin
//                            kumpulanDataran = hashDataran.get(left.value);
//                            Node node = kumpulanDataran.remove(left); // remove
//                            node.value = baru;
//                            kumpulanDataran = hashDataran.get(baru);    // baru insert
//                            kumpulanDataran.insert(node);
//                            hashDataran.put(node.value,kumpulanDataran);
//                            tanah.set(indexLeft,node);
//                        } else {                        // max == value Left
//                            kumpulanDataran = hashDataran.get(dataran.value);
//                            Node node = kumpulanDataran.remove(dataran); // remove
//                            node.value = baru;
//                            kumpulanDataran = hashDataran.get(baru);    // baru insert
//                            kumpulanDataran.insert(node);
//                            hashDataran.put(node.value,kumpulanDataran);
//                            tanah.set(indexMin,node);
//                        }
//
//                    } else {
//                        Node left = tanah.get(indexLeft);
//                        Node right = tanah.get(indexRight);
//                        int baru = Math.max(dataran.value,left.value);
//                        int res = Math.max(baru,right.value);
//                        maxElement = res;
//                        // get
//                        LinkedList dataranKiri = hashDataran.get(left.value);
//                        LinkedList dataranKanan = hashDataran.get(right.value);
//                        LinkedList dataranMin = hashDataran.get(dataran.value);
//
//                        if (res==dataran.value && res==left.value&& res==right.value) {
//
//                            // remove
//                            Node kiri = dataranKiri.remove(left);
//                            Node kanan = dataranKanan.remove(right);
//                            Node min = dataranMin.remove(dataran);
//
//                            // set element
//                            min.value = res;
//                            kiri.value = res;
//                            kanan.value = res;
//
//                            kumpulanDataran = hashDataran.get(res);
//
//                            // update LL
//                            kumpulanDataran.insert(kiri);
//                            kumpulanDataran.insert(kanan);
//                            kumpulanDataran.insert(min);
//                            hashDataran.put(res,kumpulanDataran);
//
//                            // update
//                            tanah.set(indexMin,min);
//                            tanah.set(indexLeft,kiri);
//                            tanah.set(indexRight,kanan);
//                        } else if (res==left.value && res==dataran.value) { // max kiri dan min
//                            Node kanan = dataranKanan.remove(right);
//                            kanan.value = res;
//                            kumpulanDataran = hashDataran.get(res);
//                            kumpulanDataran.insert(kanan);
//                            tanah.set(indexRight,kanan);
//                        } else if (res==right.value && res==dataran.value) { // max min dan kanan
//                            Node kiri = dataranKiri.remove(left);
//                            kiri.value = res;
//                            kumpulanDataran = hashDataran.get(res);
//                            kumpulanDataran.insert(kiri);
//                            tanah.set(indexLeft,kiri);
//                        } else if (res==left.value && res==dataran.value) { // max kiri dan kanan
//                            Node min = dataranMin.remove(dataran);
//                            min.value = res;
//                            kumpulanDataran = hashDataran.get(res);
//                            kumpulanDataran.insert(min);
//                            tanah.set(indexMin,min);
//                        } else if (res==left.value) { // max kiri doang
//                            Node kanan = dataranKanan.remove(right);
//                            kanan.value = res;
//                            kumpulanDataran = hashDataran.get(res);
//                            kumpulanDataran.insert(kanan);
//                            tanah.set(indexRight,kanan);
//
//                            Node min = dataranMin.remove(dataran);
//                            min.value = res;
//                            kumpulanDataran = hashDataran.get(res);
//                            kumpulanDataran.insert(min);
//                            tanah.set(indexMin,min);
//                        } else if (res==right.value) {
//                            Node min = dataranMin.remove(dataran);
//                            min.value = res;
//                            kumpulanDataran = hashDataran.get(res);
//                            kumpulanDataran.insert(min);
//                            tanah.set(indexMin,min);
//
//                            Node kiri = dataranKiri.remove(left);
//                            kiri.value = res;
//                            kumpulanDataran = hashDataran.get(res);
//                            kumpulanDataran.insert(kiri);
//                            tanah.set(indexLeft,kiri);
//                        } else if (res==dataran.value) {
//                            Node kanan = dataranKanan.remove(right);
//                            kanan.value = res;
//                            kumpulanDataran = hashDataran.get(res);
//                            kumpulanDataran.insert(kanan);
//                            tanah.set(indexRight,kanan);
//
//                            Node kiri = dataranKiri.remove(left);
//                            kiri.value = res;
//                            kumpulanDataran = hashDataran.get(res);
//                            kumpulanDataran.insert(kiri);
//                            tanah.set(indexLeft,kiri);
//                        }
//                    }
//
//                    // print
//                    out.println(maxElement + " "+ indexMin);
//
//                    // debug
////                    kumpulanDataran.printValue();
////                    out.print("Isi arraylist : ");
////                    for (int z=0; z<tanah.size(); z++) {
////                        out.print(tanah.get(z).value + ", ");
////                    }
////                    out.println();
//                }
//            }
//        }
//
//        out.flush();
//    }
//
//    // taken from https://codeforces.com/submissions/Petr
//    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
//    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
//    static class InputReader {
//        public BufferedReader reader;
//        public StringTokenizer tokenizer;
//
//        public InputReader(InputStream stream) {
//            reader = new BufferedReader(new InputStreamReader(stream), 32768);
//            tokenizer = null;
//        }
//
//        public String next() {
//            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
//                try {
//                    tokenizer = new StringTokenizer(reader.readLine());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            return tokenizer.nextToken();
//        }
//
//        public int nextInt() {
//            return Integer.parseInt(next());
//        }
//
//    }
//}
//
//// Java Program to Implement Heaps
//// by Illustrating Min Heap
//
//// Main class (MinHeap)
//class MinHeap {
//
//    // Member variables of this class
//    int[] Heap;
//    int size;
//    int maxsize;
//
//    // Initializaing front as static with unity
//    private static final int FRONT = 1;
//
//    // Constructor of this class
//    public MinHeap()
//    {
//
//        // This keyword refers to current object itself
//        this.maxsize = 200000;
//        this.size = 0;
//
//        Heap = new int[this.maxsize];
//        Heap[0] = Integer.MIN_VALUE;
//    }
//
//    // Method 1
//    // Returning the position of
//    // the parent for the node currently
//    // at pos
//    public int parent(int pos) { return pos / 2; }
//
//    // Method 2
//    // Returning the position of the
//    // left child for the node currently at pos
//    public int leftChild(int pos) { return (2 * pos); }
//
//    // Method 3
//    // Returning the position of
//    // the right child for the node currently
//    // at pos
//    public int rightChild(int pos)
//    {
//        return (2 * pos) + 1;
//    }
//
//    // Method 4
//    // Returning true if the passed
//    // node is a leaf node
//    public boolean isLeaf(int pos)
//    {
//
//        if (pos > (size / 2) && pos <= size) {
//            return true;
//        }
//
//        return false;
//    }
//
//    // Method 5
//    // To swap two nodes of the heap
//    public void swap(int fpos, int spos)
//    {
//
//        int tmp;
//        tmp = Heap[fpos];
//
//        Heap[fpos] = Heap[spos];
//        Heap[spos] = tmp;
//    }
//
//    // Method 6
//    // To heapify the node at pos
//
//    // method to heapify the node at pos
//    public void minHeapify(int pos) {
//        int l = leftChild(pos);
//        int r = rightChild(pos);
//        int smallest = pos;
//        if (l < size && Heap[l] < Heap[smallest])       // leftChild < parent
//            smallest = l;
//        if (r < size && Heap[r] < Heap[smallest])       // rightChild < parent
//            smallest = r;
//        if (smallest != pos)
//        {
//            swap(Heap[pos], Heap[smallest]);
//            minHeapify(smallest);
//        }
//    }
//
//    // Method 7
//    // To insert a node into the heap
//    public void insert(int element)
//    {
//
//        if (size >= maxsize) {
//            return;
//        }
//
//        Heap[++size] = element;
//        int current = size;
//
//        while (Heap[parent(current)] > Heap[current] && current > 1) {
//            swap(current, parent(current));
//            current = parent(current);
//        }
//        minHeapify(current);
//    }
//
//    // method remove spesific element
//    public void removeSpecificElement(int i) {
//        Heap[i] = Heap[size];
//        size--;
//        while (Heap[parent(i)] > Heap[i] && i > 1) {
//            swap(i, parent(i));
//            i = parent(i);
//        }
//
//        minHeapify(i);
//    }
//
//    // Method 8
//    // To print the contents of the heap
//    public void print()
//    {
//        for (int i = 1; i <= size / 2; i++) {
//
//            // Printing the parent and both childrens
//            System.out.print(
//                    " PARENT : " + Heap[i]
//                            + " LEFT CHILD : " + Heap[2 * i]
//                            + " RIGHT CHILD :" + Heap[2 * i + 1]);
//
//            // By here new line is required
//            System.out.println();
//        }
//    }
//
//    // Method 9
//    // To remove and return the minimum element from the heap
//    public int remove()
//    {
//
//        if (size == 1)
//        {
//            size--;
//            return Heap[1];
//        }
//
//        int popped = Heap[FRONT];
//        Heap[FRONT] = Heap[size--];
//        minHeapify(FRONT);
//
//        return popped;
//    }
//
//    // Method 10
//    // Main driver method
////    public static void main(String[] arg)
////    {
////
////        // Display message
////        System.out.println("The Min Heap is ");
////
////        // Creating object opf class in main() methodn
////        GFG minHeap = new GFG(15);
////
////        // Inserting element to minHeap
////        // using insert() method
////
////        // Custom input entries
////        minHeap.insert(5);
////        minHeap.insert(3);
////        minHeap.insert(17);
////        minHeap.insert(10);
////        minHeap.insert(84);
////        minHeap.insert(19);
////        minHeap.insert(6);
////        minHeap.insert(22);
////        minHeap.insert(9);
////
////        // Print all elements of the heap
////        minHeap.print();
////
////        // Removing minimum value from above heap
////        // and printing it
////        System.out.println("The Min val is "
////                + minHeap.remove());
////    }
//}
//
//class LinkedList {
//    Node head;
//    Node tail;
//    int size;
//
//    public void insert(Node dataran){
//
//        if (head==null) { // head == null or the first element
//            head = dataran;
//            tail = head;
//        } else if (head != null && head.next == null) { // insert element kedua
//            head.next = dataran;
//            head.next.prev = head;
//            tail = head.next;
//            tail.next = null;
//        } else if (dataran.index < head.index && head.prev != null) {  // index node < head
//            head.prev = dataran;
//            dataran.next = head;
//            dataran.prev = null;
//            head = head.prev;
//        } else if (dataran.index > tail.index && tail.next != null){        // index node > index tail
//            tail.next = dataran;
//            dataran.prev = tail;
//            dataran.next = null;
//            tail = tail.next;
//        } else {    // inserting di tengah
//            Node temp = head;
//            boolean cek = true;
//            while(cek && temp != null) {
//                if (temp != null && temp.next != null && temp.index <= dataran.index && dataran.index <= temp.next.index) {
//                    dataran.next = temp.next;
//                    dataran.prev = temp;
//                    temp.next.prev = dataran;
//                    temp.next = dataran;
//                    cek = false;
//                }
//                temp = temp.next;
//            }
//        }
//        size++;
//    }
//
//    public Node remove(Node dataran) {      // kyknya masih ada bug
//        if (this.head == dataran && this.tail==dataran) {   // head == tail
//            this.head = null;
//            this.tail = null;
//        } else if (dataran == this.head) {
//            this.head = head.next;
//            this.head.prev = null;
//        } else if (dataran == this.tail) {
//            this.tail = tail.prev;
//            this.tail.next = null;
//        } else {    // bukan head atau tail
//            Node temp = head;
//            boolean cek = true;
//            while (cek && temp != null) {
//                if (temp == dataran && temp.next != null) {
//                    temp.next.prev = null;
//                    temp.next = null;
//                    cek = false;
//                }
//                temp = temp.next;
//            }
//        }
//        size--;
//        return dataran;
//    }
//
//    public void printValue() {
//        Node temp = head;
//        System.out.print("Value : " + temp.value+ " ");
//        System.out.print("index : ");
//        while(temp != null) {
//            System.out.print(temp.index+",");
//            temp = temp.next;
//        }
//        System.out.println();
//    }
//}
//
//class Node {
//    int value;
//    int index;
//
//    Node next;
//    Node prev;
//
//    public Node(int value, int index) {
//        this.index = index;
//        this.value = value;
//        this.next = null;
//        this.prev = null;
//    }
//
//}
//
//
///* Source :
//https://www.geeksforgeeks.org/priority-queue-using-binary-heap/
//https://www.geeksforgeeks.org/min-heap-in-java/
//https://stackoverflow.com/questions/32751620/remove-an-element-from-any-position-in-a-max-heap
//* */