//import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.lang.reflect.Array;
//import java.util.*;
//import static java.lang.Math.min;
//import static java.lang.Math.max;
//
//public class TP3Bismillah {
//    private static InputReader in;
//    private static PrintWriter out;
//    public static Kantor kantor;
//    final static int INF = Integer.MAX_VALUE;
////    public static BinaryHeap<Pekerja> heap;
//
//    public static void main(String[] args) {
//        InputStream inputStream = System.in;
//        in = new InputReader(inputStream);
//        OutputStream outputStream = System.out;
//        out = new PrintWriter(outputStream);
//
//        int N = in.nextInt(); // Bilangan pangkat" tiap vertex
//        int M = in.nextInt(); // Banyak baris pertemanan
//        int Q = in.nextInt(); // Permintaan Kaguya
//
//        kantor = new Kantor(N);
////        heap = new BinaryHeap<Pekerja>();
//
//        // isinya kumpulan karyawan berdasar pangkat yang sama dan nomor
//        for (int i=0; i<=N; i++) { // kemungkinan bug
//            kantor.pangkatSama.add(new ArrayList<>());
//            kantor.pekerja.add(new ArrayList<>());
//        }
//
//        // isinya karyawan sesuai nomor
//        kantor.pekerja.add(null);
//        kantor.pangkatSama.add(null);
//        kantor.parent.add(INF);
//        for (int i=1; i<=N; i++) {  // kemungkinan bug
//            int pangkat = in.nextInt();
//            Pekerja pekerjaBaru = new Pekerja(i,pangkat);
//            kantor.pekerja.get(i).add(pekerjaBaru);             // head = karyawan tsb
//            kantor.pangkatSama.get(pangkat).add(pekerjaBaru);   // add ke kumpulan pangkat sama
//            // inisialisasi parrent ufds
//            kantor.parent.add(pangkat);     // parent ke pangkat masing" dulu
//        }
//
//        // debug
////        out.println(kantor.pekerja);
////        out.println(kantor.pangkatSama);
//
//        // tambah pertemanan
//        for(int i=0; i<M; i++) {
//            int vertex1 = in.nextInt();
//            int vertex2 = in.nextInt();
//            tambah(vertex1,vertex2);
//
//            // khusus BOSS
//            int parent1 = kantor.parent.get(vertex1);
//            int parent2 = kantor.parent.get(vertex2);
//            int update = 0;
//            // update dgn parent terbesar
//            if (parent1 > parent2) {
//                update = parent1;
//            } else {
//                update = parent2;
//            }
//            kantor.parent.set(vertex1,update);
//            kantor.parent.set(vertex2,update);
//        }
//
//        // perintah Kaguya-sama
//        for (int i=0; i<Q; i++) {
//            int perintah = in.nextInt();
//
//            if(perintah==1) {  // TAMBAH
//                int vertex1 = in.nextInt();
//                int vertex2 = in.nextInt();
//                tambah(vertex1,vertex2);
//            } else if (perintah==2) {  // RESIGN
//                int karyawan = in.nextInt();
//                Pekerja pekerja = kantor.pekerja.get(karyawan).get(0);
//                pekerja.isKerja = false;    // udah resign
//            } else if (perintah==3) { // CARRY
//                int staff = in.nextInt();
//                Pekerja pekerja = kantor.pekerja.get(staff).get(0);
//                out.println(carry(pekerja));
//            } else if (perintah==4) { // BOSS (UFDS)
//                int boss = in.nextInt();
//                Pekerja pekerja = kantor.pekerja.get(boss).get(0);
//                if (pekerja.heapAtas.data.size()==0 && pekerja.heapBawah.data.size()==0) {
//                    out.println(0);
//                } else {
//                    out.println(kantor.parent.get(boss));
//                }
//            } else if (perintah==5) { // SEBAR
//                int firstStaff = in.nextInt();
//                int secStaff = in.nextInt();
//                Pekerja pekerja1 = kantor.pekerja.get(firstStaff).get(0);
//                Pekerja pekerja2 = kantor.pekerja.get(secStaff).get(0);
//
//            } else if (perintah==6) { // SIMULASI
//
//            } else { // NETWORKING
//                out.println(0); // gatau lg wowkwok
//            }
//        }
//
//
//
//
//        // end i/o
//        out.flush();
//    }
//
//    public static void tambah(int vertex1, int vertex2) {
//        Pekerja pekerja1 = kantor.pekerja.get(vertex1).get(0);      // head = objek karyawan sesuai nomor
//        Pekerja pekerja2 = kantor.pekerja.get(vertex2).get(0);
//
//        // add in heap pekerja1
//        if(pekerja1.compareTo(pekerja2) >= 0) { // pangkat lebih besar -> heapAtas
//            pekerja1.heapAtas.insert(pekerja2);
//            pekerja1.countAtas++;
//        } else {    // vice versa (pangkat2 < pangkat1)
//            pekerja1.heapBawah.insert(pekerja2);
//            pekerja1.countBawah++;
//        }
//
//        // add in heap pekerja2
//        if(pekerja2.compareTo(pekerja1) >= 0) { // pangkat lebih besar -> heapAtas
//            pekerja2.heapAtas.insert(pekerja1);
//            pekerja2.countAtas++;
//        } else {    // vice versa (pangkat1 < pangkat2)
//            pekerja2.heapBawah.insert(pekerja1);
//            pekerja2.countBawah++;
//        }
//
//        kantor.addEdge(pekerja1,pekerja2);
////        heap.insert(pekerja1);
////        heap.insert(pekerja2);
//    }
//
//    public static int carry(Pekerja karyawan) {
//        int res = 0;
//        boolean cekBawah = false;   // flag kalo heapAtas habis pindah ke heapBawah
//
//        if (karyawan.heapAtas.data.size() > 0) {  // heap atas ada
//            Pekerja nilaiMax = karyawan.heapAtas.peek();
//            res = nilaiMax.pangkat;
//
//            while (nilaiMax != null && !nilaiMax.isKerja) {         // cek resign
//                karyawan.heapAtas.remove();
//                nilaiMax = karyawan.heapAtas.peek();
//                if(nilaiMax != null) {
//                    res = nilaiMax.pangkat;
//                } else {
//                    res = 0;
//                    cekBawah = true;    // flag kalau heap atas habis
//                }
//            }
//        }
//
//        if (karyawan.heapBawah.data.size() > 0 && cekBawah) { // heap bawah ada & jika heap atas sudah HABIS
//            Pekerja nilaiMax = karyawan.heapBawah.peek();
//            res = nilaiMax.pangkat;
//
//            while (nilaiMax != null && !nilaiMax.isKerja) {         // cek resign
//                karyawan.heapBawah.remove();
//                nilaiMax = karyawan.heapBawah.peek();
//                if (nilaiMax != null) {
//                    res = nilaiMax.pangkat;
//                } else {
//                    res = 0;
//                }
//            }
//        }
//
//        return res;   // else for nolep :(
//    }
//
////    public static int boss(Pekerja bos) {
////        int res = 0;
////
////
////
////        return res;
////    }
//
//    public static int sebar(Pekerja pekerja1, Pekerja pekerja2) {
//
//        return -1;
//    }
//
//    public static int simulasi() {
//
//        return 0;
//    }
//
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
//// Adjascency List representation in Java
//class Kantor {
//
//    ArrayList<ArrayList<Pekerja>> pekerja;
//    ArrayList<ArrayList<Pekerja>> pangkatSama;
//    ArrayList<Integer> parent;
//
//    // N untuk pangkat sama, M untuk addTeman sesuai vertex yg diminta
//    public Kantor(int N) {
//        this.pangkatSama = new ArrayList<ArrayList<Pekerja>>(N+1);
//        this.pekerja = new ArrayList<ArrayList<Pekerja>>(N+1);
//        this.parent = new ArrayList<>();
//    }
//
//    void addEdge(Pekerja u, Pekerja v)  // untuk berteman
//    {
//        this.pekerja.get(u.posisi).add(v);
//        this.pekerja.get(v.posisi).add(u);
//    }
//
//    // A utility function to add an edge in an
//    // undirected graph
////    static void addEdge(ArrayList<ArrayList<Pekerja> > adj,Pekerja u, Pekerja v)
////    {
////        adj.get(u).add(v);
////        adj.get(v).add(u);
////    }
//
////    public static void main(String[] args) {
////        Graph graph = new Graph(5);
////        graph.addEdge(0,1);
////        graph.addEdge(0, 4);
////        graph.addEdge(1, 2);
////        graph.addEdge(1, 3);
////        graph.addEdge(1, 4);
////        graph.addEdge(2, 3);
////        graph.addEdge(3, 4);
////        graph.printGraph();
////    }
//
//    // print the graph
////    public void printGraph(){
////        for (int i = 0; i <vertex ; i++) {
////            if(list[i].size()>0) {
////                System.out.print("Vertex " + i + " is connected to: ");
////                for (int j = 0; j < list[i].size(); j++) {
////                    System.out.print(list[i].get(j) + " ");
////                }
////                System.out.println();
////            }
////        }
////    }
//
//    // ----------------- BFS ---------------------------
//
//
//}
//
//class Pekerja implements Comparable<Pekerja> {
//    int posisi;
//    int pangkat;
//    //    Pekerja next;
////    Pekerja prev;
//    boolean isKerja;
//    BinaryHeap<Pekerja> heapAtas = new BinaryHeap<>();
//    BinaryHeap<Pekerja> heapBawah = new BinaryHeap<>();
//    int countAtas;
//    int countBawah;
//
//    public Pekerja(int posisi, int pangkat) {
//        this.posisi = posisi;
//        this.pangkat = pangkat;
//        this.isKerja = true;
//        this.countAtas = 0;
//        this.countBawah = 0;
////        this.next = null;
////        this.prev = null;
//    }
//    // compare pangkat
//    @Override
//    public int compareTo(Pekerja o) {
//        return Integer.compare(o.pangkat, this.pangkat);
//    }
//}
//
//
//class BinaryHeap<T extends Comparable<T>> {
//    ArrayList<T> data;
//
//    public BinaryHeap() {
//        data = new ArrayList<T>();
//    }
//
//    public BinaryHeap(ArrayList<T> arr) {
//        data = arr;
//        heapify();
//    }
//
//    public T peek() {
//        if (data.isEmpty())
//            return null;
//        return data.get(0);
//    }
//
//    public void insert(T value) {
//        data.add(value);
//        percolateUp(data.size() - 1);
//    }
//
//    public T remove() {
//        T removedObject = peek();
//
//        if (data.size() == 1)
//            data.clear();
//        else {
//            data.set(0, data.get(data.size() - 1));
//            data.remove(data.size() - 1);
//            percolateDown(0);
//        }
//
//        return removedObject;
//    }
//
//    private void percolateDown(int idx) {
//        T node = data.get(idx);
//        int heapSize = data.size();
//
//        while (true) {
//            int leftIdx = getLeftChildIdx(idx);
//            if (leftIdx >= heapSize) {
//                data.set(idx, node);
//                break;
//            } else {
//                int minChildIdx = leftIdx;
//                int rightIdx = getRightChildIdx(idx);
//                if (rightIdx < heapSize && data.get(rightIdx).compareTo(data.get(leftIdx)) < 0)
//                    minChildIdx = rightIdx;
//
//                if (node.compareTo(data.get(minChildIdx)) > 0) {
//                    data.set(idx, data.get(minChildIdx));
//                    idx = minChildIdx;
//                } else {
//                    data.set(idx, node);
//                    break;
//                }
//            }
//        }
//    }
//
//    private void percolateUp(int idx) {
//        T node = data.get(idx);
//        int parentIdx = getParentIdx(idx);
//        while (idx > 0 && node.compareTo(data.get(parentIdx)) < 0) {
//            data.set(idx, data.get(parentIdx));
//            idx = parentIdx;
//            parentIdx = getParentIdx(idx);
//        }
//
//        data.set(idx, node);
//    }
//
//    private int getParentIdx(int i) {
//        return (i - 1) / 2;
//    }
//
//    private int getLeftChildIdx(int i) {
//        return 2 * i + 1;
//    }
//
//    private int getRightChildIdx(int i) {
//        return 2 * i + 2;
//    }
//
//    private void heapify() {
//        for (int i = data.size() / 2 - 1; i >= 0; i--)
//            percolateDown(i);
//    }
//
//    public void sort() {
//        int n = data.size();
//        while (n > 1) {
//            data.set(n - 1, remove(n));
//            n--;
//        }
//    }
//
//    public T remove(int n) {
//        T removedObject = peek();
//
//        if (n > 1) {
//            data.set(0, data.get(n - 1));
//            percolateDown(0, n - 1);
//        }
//
//        return removedObject;
//    }
//
//    private void percolateDown(int idx, int n) {
//        T node = data.get(idx);
//        int heapSize = n;
//
//        while (true) {
//            int leftIdx = getLeftChildIdx(idx);
//            if (leftIdx >= heapSize) {
//                data.set(idx, node);
//                break;
//            } else {
//                int minChildIdx = leftIdx;
//                int rightIdx = getRightChildIdx(idx);
//                if (rightIdx < heapSize && data.get(rightIdx).compareTo(data.get(leftIdx)) < 0)
//                    minChildIdx = rightIdx;
//
//                if (node.compareTo(data.get(minChildIdx)) > 0) {
//                    data.set(idx, data.get(minChildIdx));
//                    idx = minChildIdx;
//                } else {
//                    data.set(idx, node);
//                    break;
//                }
//            }
//        }
//    }
//
//}
//
//
///*Source :
// * https://algorithms.tutorialhorizon.com/graph-implementation-adjacency-list-better-set-2/
// * https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
// * https://drive.google.com/drive/folders/1l08fdlPXzeecDfckc4mxN8lrFYLwEBh0
// *
// * */