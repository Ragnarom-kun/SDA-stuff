//import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.lang.reflect.Array;
//import java.sql.SQLOutput;
//import java.util.*;
//import static java.lang.Math.min;
//import static java.lang.Math.max;
//
//public class TP3Bismillah {
//    private static InputReader in;
//    private static PrintWriter out;
//    public static Kantor kantor;
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
//        kantor.parent[0] = -1;
//        kantor.secParent[0] = -1;
//        for (int i=1; i<=N; i++) {  // kemungkinan bug
//            int pangkat = in.nextInt();
//            Pekerja pekerjaBaru = new Pekerja(i,pangkat);
//            kantor.pekerja.get(i).add(pekerjaBaru);             // head = karyawan tsb
//            kantor.pangkatSama.get(pangkat).add(pekerjaBaru);   // add ke kumpulan pangkat sama
//            // inisialisasi parrent ufds utk BOSS
//            kantor.parent[i] = i;     // insialisasi parent (isinya index")
//            kantor.secParent[i] = i;
//        }
//
//        // debug print BOSS
//        System.out.println("----------------------------------------------------------");
//        System.out.print("Parent biggest : ");
//        for (int i=1; i<kantor.parent.length; i++) {
//            System.out.print(kantor.parent[i] + " ");
//        }
//        System.out.println();
//        System.out.print("Parent second : ");
//        for (int i=1; i<kantor.parent.length; i++) {
//            System.out.print(kantor.secParent[i] + " ");
//        }
//        System.out.println();
//        System.out.println("----------------------------------------------------------");
//
//        // tambah pertemanan
//        for(int i=0; i<M; i++) {
//            int vertex1 = in.nextInt();
//            int vertex2 = in.nextInt();
//            tambah(vertex1,vertex2);
//
//            // khusus BOSS ( bug )
//
//            int parent1 = findRepresentative(vertex1);  // index parent 1
//            int parent2 = findRepresentative(vertex2);  // index parent 2
//
//            System.out.println("Parent 1 : " + parent1);
//            System.out.println("Parent 2 : " + parent2);
//
//            // update dgn parent terbesar
//            if (kantor.pekerja.get(parent1).get(0).pangkat > kantor.pekerja.get(parent2).get(0).pangkat) {
//                // parent1 > parent2 (pangkat)
//                kantor.parent[parent2] = parent1;
//                kantor.secParent[parent1] = parent2;
//            } else if (kantor.pekerja.get(parent1).get(0).pangkat < kantor.pekerja.get(parent2).get(0).pangkat) {
//                // parent2 > parent1 (pangkat)
//                kantor.parent[parent1] = parent2;
//                kantor.secParent[parent2] = parent1;
//            }
//            else { // jika sama
//                kantor.secParent[parent1] = parent2;
//                kantor.secParent[parent2] = parent1;
//            }
//
//            // debug print BOSS
//            System.out.print("Parent biggest : ");
//            for (int j=1; j<kantor.parent.length; j++) {
//                System.out.print(kantor.parent[j] + " ");
//            }
//            System.out.println();
//            System.out.print("Parent second : ");
//            for (int j=1; j<kantor.secParent.length; j++) {
//                System.out.print(kantor.secParent[j] + " ");
//            }
//            System.out.println();
//            System.out.println("----------------------------------------------------------");
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
//                int index = kantor.parent[boss];
//                if (pekerja.heapAtas.data.size()==0 && pekerja.heapBawah.data.size()==0) {  // ga ada teman
//                    out.println(0);
//                } else if (index == pekerja.posisi) {       // index msh asli, pake secParent
//                    out.println(kantor.pekerja.get(kantor.secParent[boss]).get(0).pangkat);
//                }else {
//                    out.println(kantor.pekerja.get(index).get(0).pangkat);
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
//        // end i/o
//        out.flush();
//    }
//
//    public static void tambah(int vertex1, int vertex2) {
//        Pekerja pekerja1 = kantor.pekerja.get(vertex1).get(0);      // head = objek karyawan sesuai nomor
//        Pekerja pekerja2 = kantor.pekerja.get(vertex2).get(0);
//
//        // add in heap pekerja1
//        if(pekerja1.compareTo(pekerja2) >= 0) { // pangkat pekerja2 lebih besar -> heapAtas
//            pekerja1.heapAtas.insert(pekerja2);
//            pekerja1.countAtas++;
//        } else {    // vice versa (pangkat2 < pangkat1)
//            pekerja1.heapBawah.insert(pekerja2);
//            pekerja1.countBawah++;
//        }
//
//        // add in heap pekerja2
//        if(pekerja2.compareTo(pekerja1) >= 0) { // pangkat pekerja1 lebih besar -> heapAtas
//            pekerja2.heapAtas.insert(pekerja1);
//            pekerja2.countAtas++;
//        } else {    // vice versa (pangkat1 < pangkat2)
//            pekerja2.heapBawah.insert(pekerja1);
//            pekerja2.countBawah++;
//        }
//
//        kantor.addEdge(pekerja1,pekerja2);
//    }
//
//    public static int carry(Pekerja karyawan) {
//        int res = 0;
//        boolean cekBawah = true;   // flag kalo heapAtas habis pindah ke heapBawah
//
//        if (karyawan.heapAtas.data.size() > 0) {  // heap atas ada
//            Pekerja nilaiMax = karyawan.heapAtas.peek();
//
//
//            while (nilaiMax != null && !nilaiMax.isKerja) {         // cek resign
//                karyawan.heapAtas.remove();
//                nilaiMax = karyawan.heapAtas.peek();
//            }
//
//            if(nilaiMax != null) {          // dpt element tertinggi yg ga resign
//                res = nilaiMax.pangkat;
//                cekBawah = false;
//            }
//        }
//
//        if (karyawan.heapBawah.data.size() > 0) { // heap bawah ada & jika heap atas sudah HABIS
//            Pekerja nilaiMax = karyawan.heapBawah.peek();
//
//
//            while (nilaiMax != null && !nilaiMax.isKerja) {         // cek resign
//                karyawan.heapBawah.remove();
//                nilaiMax = karyawan.heapBawah.peek();
//            }
//
//            if (nilaiMax != null && cekBawah) {     // update pangkat terbesar
//                res = nilaiMax.pangkat;
//            }
//        }
//
//        return res;   // else for nolep :(
//    }
//
//    public static int findRepresentative(int index) {    // msh ada bug
//        if (kantor.parent[index] == index) {
//            return index;
//        } else {
//            kantor.parent[index] = findRepresentative(kantor.parent[index]);
//            return kantor.parent[index];
//        }
//    }
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
//    int[] parent;       // the biggest parent (array index)
//    int[] secParent;    // second biggest (krn network except dirinya sendiri) (array index tho)
//
//    // N untuk pangkat sama, M untuk addTeman sesuai vertex yg diminta
//    public Kantor(int N) {
//        this.pangkatSama = new ArrayList<ArrayList<Pekerja>>(N+1);
//        this.pekerja = new ArrayList<ArrayList<Pekerja>>(N+1);
//        this.parent = new int[N+1];
//        this.secParent = new int[N+1];
//    }
//
//    void addEdge(Pekerja u, Pekerja v)  // untuk berteman
//    {
//        this.pekerja.get(u.posisi).add(v);
//        this.pekerja.get(v.posisi).add(u);
//    }
//
//    // ----------------- BFS ---------------------------
//
//
//}
//
//class Pekerja implements Comparable<Pekerja> {
//    int posisi;
//    int pangkat;
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