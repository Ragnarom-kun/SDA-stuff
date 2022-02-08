// Java code to demonstrate Graph representation
// using ArrayList in Java

//import java.util.*;
//
//public class dummy {
//
//    // A utility function to add an edge in an
//    // undirected graph
//    static void addEdge(ArrayList<ArrayList<Integer> > adj,
//                        int u, int v)
//    {
//        adj.get(u).add(v);
//        adj.get(v).add(u);
//    }
//
//    // A utility function to print the adjacency list
//    // representation of graph
//    static void printGraph(ArrayList<ArrayList<Integer> > adj)
//    {
//        for (int i = 0; i < adj.size(); i++) {
//            System.out.println("\nAdjacency list of vertex" + i);
//            System.out.print("head");
//            for (int j = 0; j < adj.get(i).size(); j++) {
//                System.out.print(" -> "+adj.get(i).get(j));
//            }
//            System.out.println();
//        }
//    }

    // Driver Code
//    public static void main(String[] args)
//    {
        // Creating a graph with 5 vertices
//        int V = 10;
//        ArrayList<ArrayList<Integer> > adj
//                = new ArrayList<ArrayList<Integer> >(V);
//
//        System.out.println("List of arraylist: ");
//        for (int i =0; i<adj.size();i++) {
//            System.out.println(i + " " +adj.get(i));
//        }
//
//
//        for (int i = 0; i < V; i++)
//            adj.add(new ArrayList<Integer>());
//
//        System.out.println("List of arraylist: ");
//        for (int i =0; i<adj.size();i++) {
//            System.out.println(i + " " +adj.get(i));
//        }
//
//        System.out.println("testing: "+adj.get(adj.size()-1));
//        // Adding edges one by one
//        addEdge(adj, 0, 1);
//        addEdge(adj, 0, 4);
//        addEdge(adj, 1, 2);
//        addEdge(adj, 1, 3);
//        addEdge(adj, 1, 4);
//        addEdge(adj, 2, 3);
//        addEdge(adj, 3, 4);
//
//        printGraph(adj);
//
//        System.out.println("List of arraylist: ");
//        for (int i =0; i<adj.size();i++) {
//            System.out.println(i + " " +adj.get(i));
//        }
//        ArrayList<Integer> angka = new ArrayList<>();
//
//        angka.add(13);
//        angka.add(1);
//        angka.add(90);
//
//        System.out.println(angka);
//        System.out.println("size : " + angka.size());
//
//        angka.clear();
//
//        System.out.println(angka);
//        System.out.println("size : " + angka.size());
//    }
//}

//// debug print isi
//public static void printHeap(Pekerja pekerja) {
//        System.out.println("Heap karyawan ke- " + pekerja.posisi);
//        System.out.print("Ini heap atas : ");
//        for (int i=0; i<pekerja.heapAtas.data.size(); i++) {
//        System.out.print(pekerja.heapAtas.data.get(i).pangkat + " ");
//        }
//        System.out.println();
//
//        System.out.print("Ini heap bawah : ");
//        for (int i=0; i<pekerja.heapBawah.data.size(); i++) {
//        System.out.print(pekerja.heapBawah.data.get(i).pangkat + " ");
//        }
//        System.out.println();
//        System.out.println("------------------------------------------------------------");
//        }

