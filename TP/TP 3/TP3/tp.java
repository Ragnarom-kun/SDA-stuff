import java.io.*;
import java.util.*;

/**
* @author Mika Suryofakhri Rahwono - 2006596251
*/
public class tp {
    private static InputReader in;
    private static PrintWriter out;
    private static ArrayList<Karyawan> kumpulanKaryawan = new ArrayList<>();
    private static ArrayList<Integer>[] tingkatKaryawan;
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        int N = in.nextInt();
        int M = in.nextInt();
        int Q = in.nextInt();
        Graph g = new Graph(N);
        Graph gSebar = new Graph(N);
        tingkatKaryawan = new ArrayList[N];
        int loop = 0;
        while (N!=0){
            int tingkat = in.nextInt();
            Karyawan kary = new Karyawan(tingkat);
            kumpulanKaryawan.add(kary); // nama kary = index
            if(tingkatKaryawan[tingkat-1] != null){
                Iterator<Integer> iter = tingkatKaryawan[tingkat-1].iterator();
                while (iter.hasNext()){
                    int i = iter.next();
                    gSebar.addEdge(i, loop);
                    gSebar.addEdge(loop, i);
                }
            }
            else{
                tingkatKaryawan[tingkat-1] = new ArrayList<>();
            }
            tingkatKaryawan[tingkat-1].add(loop);
            loop++;
//            System.out.println(kumpulanKaryawan.size());
            N--;
        }
        while (M!=0){
            int A = in.nextInt() -1;
            int B = in.nextInt() -1;
            kumpulanKaryawan.get(A).addTeman(kumpulanKaryawan.get(B));
            kumpulanKaryawan.get(B).addTeman(kumpulanKaryawan.get(A));
            g.addEdge(A, B);
            g.addEdge(B, A);
            gSebar.addEdge(A, B);
            gSebar.addEdge(B, A);
            M--;
        }
        while (Q!=0) {
            int inp = in.nextInt();
            if (inp == 1) {
                int A = in.nextInt()-1;
                int B = in.nextInt()-1;
                kumpulanKaryawan.get(A).addTeman(kumpulanKaryawan.get(B));
                kumpulanKaryawan.get(B).addTeman(kumpulanKaryawan.get(A));
                g.addEdge(A, B);
                g.addEdge(B, A);
                gSebar.addEdge(A, B);
                gSebar.addEdge(B, A);
            } else if (inp == 2) {
                int A = in.nextInt()-1;
                Karyawan resign = kumpulanKaryawan.get(A);
                Iterator<Karyawan> iter = resign.getTeman().iterator();
                while (iter.hasNext()){
                    iter.next().removeTeman(resign);
                }
                kumpulanKaryawan.remove(A);
                kumpulanKaryawan.add(A, null);
                Iterator<Integer> iter2 = tingkatKaryawan[resign.getPeringkat()-1].iterator();
                while (iter2.hasNext()){
                    gSebar.removeEdge(iter2.next(), A);
                }
            } else if (inp == 3) {
                int A = in.nextInt()-1;
                System.out.println(kumpulanKaryawan.get(A).getTemanTertinggi());
            } else if (inp == 4) {
                int A = in.nextInt()-1;
                System.out.println(g.BFSBoss(A, kumpulanKaryawan));
            } else if (inp == 5) {
                int A = in.nextInt()-1;
                int B = in.nextInt()-1;
                System.out.println(gSebar.BFSSebar(A, kumpulanKaryawan, kumpulanKaryawan.get(B)));
            } else if (inp == 6) {
                System.out.println(-1);
            } else if (inp == 7) {
                System.out.println(0);
            }
            Q--;
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

class Karyawan {
    private ArrayList<Karyawan> teman;
    private int peringkat;
    private int temanTertinggi;
    private int jmlTemanTertinggi;
    Karyawan(int peringkat){
        this.teman = new ArrayList<>();
        this.temanTertinggi = 0;
        this.jmlTemanTertinggi=0;
        this.peringkat = peringkat;
    }
    public int getTemanTertinggi() {
        return temanTertinggi;
    }
    public ArrayList<Karyawan> getTeman() {
        return teman;
    }
    public int getPeringkat() {
        return peringkat;
    }
    public int getJmlTemanTertinggi() {
        return jmlTemanTertinggi;
    }
    public void addTeman(Karyawan teman){
        this.teman.add(teman);
        if (this.temanTertinggi < teman.getPeringkat()){
            this.temanTertinggi = teman.getPeringkat();
            this.jmlTemanTertinggi = 1;
        }
        else if (this.temanTertinggi == teman.getPeringkat()){
            this.jmlTemanTertinggi++;
        }
    }
    public void removeTeman(Karyawan teman){
        this.teman.remove(teman);
        if(this.temanTertinggi == teman.getPeringkat()){
            jmlTemanTertinggi--;
            if(this.jmlTemanTertinggi == 0) {
                updatePeringkat();
            }
        }
    }
    public void updatePeringkat(){
        if (this.teman.size() == 0){
            this.temanTertinggi = 0;
            return;
        }
        int max = 0;
        int jmlMax = 0;
        Iterator<Karyawan> iter = this.teman.iterator();
        while (iter.hasNext()){
            int z = iter.next().getPeringkat();
            if(z > max){
                max = z;
                jmlMax = 0;
            }
            if(z == max){
                jmlMax++;
            }
        }
        this.temanTertinggi = max;
        this.jmlTemanTertinggi = jmlMax;
    }

}

// This class represents a directed graph using adjacency list
// representation
class Graph
{
    private int V; // No. of vertices
    private LinkedList<Integer> adj[]; //Adjacency Lists

    // Constructor
    Graph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }

    // Function to add an edge into the graph
    void addEdge(int v,int w)
    {
        adj[v].add(w);
    }

    void removeEdge(int v, int w){
        adj[v].remove(w);
    }

    // prints BFS traversal from a given source s
    int BFSBoss(int s, ArrayList<Karyawan> kumpulanKary)
    {
        // Mark all the vertices as not visited(By default
        // set as false)
        if(adj[s].size() == 0) return 0;
        boolean visited[] = new boolean[V];

        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        int max = 0;
        int z = s;
        // Mark the current node as visited and enqueue it
        visited[s]=true;
        queue.add(s);
        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            if (kumpulanKary.get(s).getPeringkat() > max && z != s) max = kumpulanKary.get(s).getPeringkat();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
        return max;
    }
    int BFSSebar(int s, ArrayList<Karyawan> kumpulanKary, Karyawan karTujuan)
    {
        // Mark all the vertices as not visited(By default
        // set as false)
        if(adj[s].size() == 0) return -1;
        boolean visited[] = new boolean[V];

        // Create a queue for BFS
        LinkedList<Node> queue = new LinkedList<Node>();

        int jarak = 0;
        Node z;
        // Mark the current node as visited and enqueue it
        visited[s]=true;
        queue.add(new Node(s, 0));
        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            z = queue.poll();
            if(jarak == z.getJarak()) jarak++;
            if(kumpulanKary.get(z.getValue()).equals(karTujuan)) return z.getJarak()-1;

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adj[z.getValue()].listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(new Node(n, jarak));
                }
            }
        }
        return -1;
    }
    static class Node {
        private int jarak;
        private int value;

        Node(int value, int jarak) {
            this.value =value;
            this.jarak = jarak;
        }

        public int getJarak() {
            return jarak;
        }

        public int getValue() {
            return value;
        }
    }
}
// This code is contributed by Aakash Hasija
