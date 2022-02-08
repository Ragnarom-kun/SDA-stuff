import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class TP3BismillahRev {
    private static InputReader in;
    private static PrintWriter out;
    public static Kantor kantor;
    public static int countSimulasi;
    public static boolean isSimulasiPertama;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N = in.nextInt(); // Bilangan pangkat" tiap vertex
        int M = in.nextInt(); // Banyak baris pertemanan
        int Q = in.nextInt(); // Permintaan Kaguya

        kantor = new Kantor(N);
        countSimulasi = 0;
        isSimulasiPertama = true;   // set simulasi pertama

        // isinya kumpulan karyawan berdasar pangkat yang sama dan nomor
        for (int i=0; i<=N; i++) {
            kantor.pangkatSama.add(new ArrayList<>());
        }

        // isinya karyawan sesuai nomor + ga pake index 0
        kantor.pekerja.add(null);
        kantor.pangkatSama.add(null);
        for (int i=1; i<=N; i++) {  //  mulai dari index 1
            int pangkat = in.nextInt();
            Pekerja pekerjaBaru = new Pekerja(i,pangkat);
            kantor.pekerja.add(pekerjaBaru);             // head = karyawan tsb
            kantor.pangkatSama.get(pangkat).add(pekerjaBaru);   // add ke kumpulan pangkat sama
        }

        // tambah pertemanan
        for(int i=0; i<M; i++) {
            int vertex1 = in.nextInt();
            int vertex2 = in.nextInt();
            tambah(vertex1,vertex2);
        }

        // perintah Kaguya-sama
        for (int i=0; i<Q; i++) {
            int perintah = in.nextInt();

            if(perintah==1) {  // TAMBAH
                int vertex1 = in.nextInt();
                int vertex2 = in.nextInt();
                tambah(vertex1,vertex2);
            } else if (perintah==2) {  // RESIGN
                int karyawan = in.nextInt();
                Pekerja pekerja = kantor.pekerja.get(karyawan);
                pekerja.isKerja = false;    // udah resign (set flag)
                resign(pekerja);
            } else if (perintah==3) { // CARRY
                int staff = in.nextInt();
                Pekerja pekerja = kantor.pekerja.get(staff);
                out.println(carry(pekerja));
            } else if (perintah==4) { // BOSS
                int boss = in.nextInt();
                Pekerja pekerja = kantor.pekerja.get(boss);
                out.println(boss(pekerja));
            } else if (perintah==5) { // SEBAR
                int firstStaff = in.nextInt();
                int secStaff = in.nextInt();
                Pekerja pekerja1 = kantor.pekerja.get(firstStaff);
                Pekerja pekerja2 = kantor.pekerja.get(secStaff);
                out.println(sebar(pekerja1,pekerja2));
            } else if (perintah==6) { // SIMULASI
                if (isSimulasiPertama) {
                    simulasiPertama();
                }
                out.println(countSimulasi);
            } else { // NETWORKING
                out.println(0); // gatau lg wowkwok
            }
        }

        // end i/o
        out.flush();
    }

    public static void tambah(int vertex1, int vertex2) {
        Pekerja pekerja1 = kantor.pekerja.get(vertex1);      // head = objek karyawan sesuai nomor
        Pekerja pekerja2 = kantor.pekerja.get(vertex2);

        // add in each heap
        pekerja1.maxHeap.insert(pekerja2);
        pekerja2.maxHeap.insert(pekerja1);

        int temp1 = pekerja1.countAtas;     // cek asalnya lulus atau nggak dari simulasi
        int temp2 = pekerja2.countAtas;

        if(pekerja2.pangkat >= pekerja1.pangkat) { // pangkat pekerja2 lebih besar sama dengan pekerja1 -> heapAtas
            pekerja1.countAtas++;
        }
        if (pekerja1.pangkat >= pekerja2.pangkat) {    // vice versa (pangkat2 <= pangkat1)
            pekerja2.countAtas++;
        }

        if (temp1==0 && pekerja1.countAtas>0 && pekerja1.isKerja) {
            countSimulasi--;
        }
        if (temp2==0 && pekerja2.countAtas>0 && pekerja2.isKerja) {
            countSimulasi--;
        }
    }

    public static void resign(Pekerja pekerja) {                    // pekerja = yg resign
        for (int i=0; i<pekerja.maxHeap.data.size(); i++) {
            Pekerja karyawan = pekerja.maxHeap.data.get(i);
            int temp = karyawan.countAtas;
            if(karyawan.pangkat < pekerja.pangkat && karyawan.isKerja) {
                karyawan.countAtas--;
            }
            // sebelumnya ga lulus, krn ngurang elementnya jd lulus (simulasi)
            if (temp > 0 && karyawan.countAtas==0 && karyawan.isKerja) {
                countSimulasi++;
            }
        }

        if(pekerja.countAtas == 0) {   // cek apakah yg di resign sebelumnya lulus, jika iya kurangi countSimulasi
            countSimulasi--;
        }
    }

    public static void simulasiPertama() {
        countSimulasi = 0;
        for(int i=1; i<kantor.pekerja.size(); i++) {
            if(kantor.pekerja.get(i).countAtas == 0 && kantor.pekerja.get(i).isKerja) {
                countSimulasi++;
            }
        }
        isSimulasiPertama = false;
    }

    public static int carry(Pekerja karyawan) {
        int res = 0;

        if (karyawan.maxHeap.data.size() > 0) {  // heap atas ada
            Pekerja nilaiMax = karyawan.maxHeap.peek();


            while (nilaiMax != null && !nilaiMax.isKerja) {         // cek resign
                karyawan.maxHeap.remove();
                nilaiMax = karyawan.maxHeap.peek();
            }

            if(nilaiMax != null) {          // dpt element tertinggi yg ga resign
                res = nilaiMax.pangkat;
            }
        }

        return res;   // else for nolep :(
    }

    private static int sebar(Pekerja U, Pekerja V)
    {
        int[] dist = new int[kantor.pekerja.size()+1];

        if (U==V) {
            return 0;
        }
        return BFS(U, V, dist);
    }

    private static int BFS(Pekerja src, Pekerja dest, int[] dist)
    {
        LinkedList<Pekerja> queue = new LinkedList<Pekerja>();
        boolean[] visited = new boolean[kantor.pekerja.size()+1]; // index 0 ga pakai
        boolean[] visitedPangkat = new boolean[kantor.pekerja.size()+1];

        for (int i=0; i<kantor.pekerja.size()+1; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
        }

        // inisialisasi
        visited[src.posisi] = true;
        dist[src.posisi] = 0;
        queue.add(src);

        // bfs Algorithm
        while (!queue.isEmpty()) {
            Pekerja u = queue.remove();
            for (int i = 0; i < u.maxHeap.data.size(); i++) {
                if (!visited[u.maxHeap.data.get(i).posisi] && u.maxHeap.data.get(i).isKerja) {
                    visited[u.maxHeap.data.get(i).posisi] = true;
                    dist[u.maxHeap.data.get(i).posisi] = dist[u.posisi] + 1;
                    queue.add(u.maxHeap.data.get(i));

                    // stopping condition (when we find
                    // our destination)
                    if (u.maxHeap.data.get(i) == dest) {
                        return dist[dest.posisi]-1;
                    }

                }
            }
            // pangkat sama
            ArrayList<Pekerja> pangkatSama = kantor.pangkatSama.get(u.pangkat);
            if (!visitedPangkat[u.pangkat]) {
                for (int i = 0; i < pangkatSama.size(); i++) {
                    if (!visited[pangkatSama.get(i).posisi] && pangkatSama.get(i).isKerja) {
                        visited[pangkatSama.get(i).posisi] = true;
                        visitedPangkat[u.pangkat] = true;
                        dist[pangkatSama.get(i).posisi] = dist[u.posisi] + 1;
                        queue.add(pangkatSama.get(i));

                        // stopping condition (when we find
                        // our destination)
                        if (pangkatSama.get(i) == dest) {
                            return dist[dest.posisi]-1;
                        }

                    }
                }
            }
        }
        return -1;
    }

    static int boss(Pekerja U) { // for BOSS
        if (U.maxHeap.data.isEmpty()) {
            return 0;
        }
        if (U.boss == null) {
            Boss boss = new Boss();
            boss.max1 = U.pangkat;
            boolean[] visited = new boolean[kantor.pekerja.size()];
            DFS(U,visited,boss);
        }
        if(U.boss.max1 == U.pangkat) {
            return U.boss.max2;
        }
        return U.boss.max1;
    }

    static void DFS(Pekerja U, boolean[] visited, Boss boss) {
        visited[U.posisi] = true;
        U.boss = boss;

        for (Pekerja n : kantor.pekerja.get(U.posisi).maxHeap.data) {
            if (!visited[n.posisi]) {

                if (n.pangkat >= boss.max1) {
                    boss.max2 = boss.max1;
                    boss.max1 = n.pangkat;
                } else {
                    if (n.pangkat > boss.max2) {
                        boss.max2 = n.pangkat;
                    }
                }
                DFS(n,visited,boss);
            }
        }
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

// Adjascency List representation in Java
class Kantor {

    ArrayList<Pekerja> pekerja;
    ArrayList<ArrayList<Pekerja>> pangkatSama;

    // N untuk pangkat sama, M untuk addTeman sesuai vertex yg diminta
    public Kantor(int N) {
        this.pangkatSama = new ArrayList<ArrayList<Pekerja>>(N+1);
        this.pekerja = new ArrayList<>();
    }
}

class Pekerja implements Comparable<Pekerja> {
    int posisi;
    int pangkat;
    boolean isKerja;
    BinaryHeap<Pekerja> maxHeap = new BinaryHeap<>();
    int countAtas;
    Boss boss;

    public Pekerja(int posisi, int pangkat) {
        this.posisi = posisi;
        this.pangkat = pangkat;
        this.isKerja = true;
        this.countAtas = 0;
    }
    // compare pangkat
    @Override
    public int compareTo(Pekerja o) {
        return Integer.compare(o.pangkat, this.pangkat);
    }
}

class Boss {    // simpen max1 dan max2
    int max1;
    int max2;

    public Boss() {
        max1 = 0;
        max2 = 0;
    }
}

class BinaryHeap<T extends Comparable<T>> {
    ArrayList<T> data;

    public BinaryHeap() {
        data = new ArrayList<T>();
    }

    public BinaryHeap(ArrayList<T> arr) {
        data = arr;
        heapify();
    }

    public T peek() {
        if (data.isEmpty())
            return null;
        return data.get(0);
    }

    public void insert(T value) {
        data.add(value);
        percolateUp(data.size() - 1);
    }

    public T remove() {
        T removedObject = peek();

        if (data.size() == 1)
            data.clear();
        else {
            data.set(0, data.get(data.size() - 1));
            data.remove(data.size() - 1);
            percolateDown(0);
        }

        return removedObject;
    }

    private void percolateDown(int idx) {
        T node = data.get(idx);
        int heapSize = data.size();

        while (true) {
            int leftIdx = getLeftChildIdx(idx);
            if (leftIdx >= heapSize) {
                data.set(idx, node);
                break;
            } else {
                int minChildIdx = leftIdx;
                int rightIdx = getRightChildIdx(idx);
                if (rightIdx < heapSize && data.get(rightIdx).compareTo(data.get(leftIdx)) < 0)
                    minChildIdx = rightIdx;

                if (node.compareTo(data.get(minChildIdx)) > 0) {
                    data.set(idx, data.get(minChildIdx));
                    idx = minChildIdx;
                } else {
                    data.set(idx, node);
                    break;
                }
            }
        }
    }

    private void percolateUp(int idx) {
        T node = data.get(idx);
        int parentIdx = getParentIdx(idx);
        while (idx > 0 && node.compareTo(data.get(parentIdx)) < 0) {
            data.set(idx, data.get(parentIdx));
            idx = parentIdx;
            parentIdx = getParentIdx(idx);
        }

        data.set(idx, node);
    }

    private int getParentIdx(int i) {
        return (i - 1) / 2;
    }

    private int getLeftChildIdx(int i) {
        return 2 * i + 1;
    }

    private int getRightChildIdx(int i) {
        return 2 * i + 2;
    }

    private void heapify() {
        for (int i = data.size() / 2 - 1; i >= 0; i--)
            percolateDown(i);
    }

    public void sort() {
        int n = data.size();
        while (n > 1) {
            data.set(n - 1, remove(n));
            n--;
        }
    }

    public T remove(int n) {
        T removedObject = peek();

        if (n > 1) {
            data.set(0, data.get(n - 1));
            percolateDown(0, n - 1);
        }

        return removedObject;
    }

    private void percolateDown(int idx, int n) {
        T node = data.get(idx);
        int heapSize = n;

        while (true) {
            int leftIdx = getLeftChildIdx(idx);
            if (leftIdx >= heapSize) {
                data.set(idx, node);
                break;
            } else {
                int minChildIdx = leftIdx;
                int rightIdx = getRightChildIdx(idx);
                if (rightIdx < heapSize && data.get(rightIdx).compareTo(data.get(leftIdx)) < 0)
                    minChildIdx = rightIdx;

                if (node.compareTo(data.get(minChildIdx)) > 0) {
                    data.set(idx, data.get(minChildIdx));
                    idx = minChildIdx;
                } else {
                    data.set(idx, node);
                    break;
                }
            }
        }
    }

}

/*Source :
 * https://algorithms.tutorialhorizon.com/graph-implementation-adjacency-list-better-set-2/
 * https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
 * https://drive.google.com/drive/folders/1l08fdlPXzeecDfckc4mxN8lrFYLwEBh0
 * https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
 * */

/* Collaborator :
Yudha Haris Purnama
Muhammad Ilham Ghozali
* */