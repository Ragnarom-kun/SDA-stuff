//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.*;
//
//// Source: https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/?ref=lbp
//public class Lab7 {
//
//    private static InputReader in;
//    private static PrintWriter out;
//    private static List<List<Node> > adj = new ArrayList<List<Node> >();
//    private static GFG dpq;
//
//    public static class GFG {
//
//        // Member variables of this class
//        private int dist[];
//        private Set<Integer> settled;
//        private PriorityQueue<Node> pq;
//        // Number of vertices
//        private int V;
//        List<List<Node> > adj;
//
//        // Constructor of this class
//        public GFG(int V)
//        {
//
//            // This keyword refers to current object itself
//            this.V = V;
//            dist = new int[V];
//            settled = new HashSet<Integer>();
//            pq = new PriorityQueue<Node>(V, new Node());
//        }
//
//        // Method 1
//        // Dijkstra's Algorithm
//        public void dijkstra(List<List<Node> > adj, int src)
//        {
//            this.adj = adj;
//
//            for (int i = 0; i < V; i++)
//                dist[i] = Integer.MAX_VALUE;
//
//            // Add source node to the priority queue
//            pq.add(new Node(src, 0));
//
//            // Distance to the source is 0
//            dist[src] = 0;
//
//            while (settled.size() != V) {
//
//                // Terminating ondition check when
//                // the priority queue is empty, return
//                if (pq.isEmpty())
//                    return;
//
//                // Removing the minimum distance node
//                // from the priority queue
//                int u = pq.remove().node;
//
//                // Adding the node whose distance is
//                // finalized
//                if (settled.contains(u))
//
//                    // Continue keyword skips exwcution for
//                    // following check
//                    continue;
//
//                // We don't have to call e_Neighbors(u)
//                // if u is already present in the settled set.
//                settled.add(u);
//
//                e_Neighbours(u);
//            }
//        }
//
//        // Method 2
//        // To process all the neighbours
//        // of the passed node
//        private void e_Neighbours(int u)
//        {
//
//            int edgeDistance = -1;
//            int newDistance = -1;
//
//            // All the neighbors of v
//            for (int i = 0; i < adj.get(u).size(); i++) {
//                Node v = adj.get(u).get(i);
//
//                // If current node hasn't already been processed
//                if (!settled.contains(v.node)) {
//                    edgeDistance = v.cost;
//                    newDistance = dist[u] + edgeDistance;
//
//                    // If new distance is cheaper in cost
//                    if (newDistance < dist[v.node])
//                        dist[v.node] = newDistance;
//
//                    // Add the current node to the queue
//                    pq.add(new Node(v.node, dist[v.node]));
//                }
//            }
//        }
//    }
//
//    // TODO: method to initialize graph
//    public static void createGraph(int N) {
//        for (int i = 0; i <= N; i++) {
//			List<Node> item = new ArrayList<Node>();
//			adj.add(item);
//		}
//    }
//
//    // TODO: method to create an edge with type==T that connects vertex U and vertex V in a graph
//    public static void addEdge(int U, int V, int T) {
//        adj.get(U).add(new Node(V, T));
//        adj.get(V).add(new Node(U, T));
//    }
//
//    // TODO: Handle teman X Y K
//    public static int canMudik(int X, int Y, int K) {
//
//        dpq.dijkstra(adj, X);
//        int totalTicketNeeded = dpq.dist[Y];
//        if(K >= totalTicketNeeded){
//            return 1;
//        }
//        else{
//            return 0;
//        }
//    }
//
//    public static void main(String[] args) {
//        InputStream inputStream = System.in;
//        in = new InputReader(inputStream);
//        OutputStream outputStream = System.out;
//        out = new PrintWriter(outputStream);
//
//        //N : Kota, M : Jalan, Q : Orang
//        int N = in.nextInt();
//        int M = in.nextInt();
//        int Q = in.nextInt();
//        createGraph(N);
//
//        for (int i=0;i < M;i++) {
//            int U = in.nextInt();
//            int V = in.nextInt();
//            int T = in.nextInt();
//            addEdge(U, V, T);
//        }
//
//        while(Q-- > 0) {
//            dpq = new GFG(N+1);
//            int X = in.nextInt();
//            int Y = in.nextInt();
//            int K = in.nextInt();
//            out.println(canMudik(X, Y, K));
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
//
//    static class Node implements Comparator<Node> {
//
//        // Member variables of this class
//        public int node;
//        public int cost;
//
//        // Constructors of this class
//
//        // Constructor 1
//        public Node() {}
//
//        // Constructor 2
//        public Node(int node, int cost)
//        {
//
//            // This keyword refers to current instance itself
//            this.node = node;
//            this.cost = cost;
//        }
//
//        // Method 1
//        @Override public int compare(Node node1, Node node2)
//        {
//
//            if (node1.cost < node2.cost)
//                return -1;
//
//            if (node1.cost > node2.cost)
//                return 1;
//
//            return 0;
//        }
//    }
//}
//
