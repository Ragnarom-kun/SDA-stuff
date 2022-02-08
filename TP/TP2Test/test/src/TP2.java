import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class TP2 {

    private static InputReader in;
    private static PrintWriter out;
    static HashMap<String, PulauTree> treePulau = new HashMap<>();        // kumpulan nama tree dlm tree
    static HashMap<String, PulauLL> linkedListPulau = new HashMap<>();  // kumpulan nama pulau dlm LL, head = kuil
    static String posisiRaiden = "";
    static HashMap<String, PulauLL> linkedListSorted = new HashMap<>();
//    static HashMap<String, PulauLL> pointerKuil = new HashMap<>();      // pointer nyimpen kuil pulau
//    static HashMap<Integer, DataranGroup> kumpulanDataran = new HashMap<>();
//    static HashMap<String, GabunganPulau> kumpulanPulau = new HashMap<>();  // gabungan pulau

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // inisialisasi banyak pulau
        int N = in.nextInt();
        for(int i = 0; i < N; i++){
            String nama = in.next();
            int banyak = in.nextInt();
            PulauTree pulauTree = new PulauTree();
            PulauLL pulau = new PulauLL();
            pulau.namaPulau = nama;
//            int[] tempArr = new int[banyak];

            // inisialisasi dataran
            for(int j=0; j<banyak; j++) {
                int dataran = in.nextInt();
                pulau.insert(dataran);
                pulauTree.head = pulauTree.insert(pulauTree.head, dataran);
//                tempArr[j] = dataran;
            }

            // merge sort array
//            MergeSort ob = new MergeSort();
//            ob.sort(tempArr,0, tempArr.length-1);   // O(n log n)
            // insert sorted array to bst
//            pulauTree.head = pulauTree.sortedArrayToBST(tempArr, 0, tempArr.length - 1);
//            out.println();
//            System.out.println("Preorder traversal of constructed BST");

//            pulauTree.preOrderUpdate(pulauTree.head, pulau);

            // add pulau ke hashmap
            treePulau.put(nama,pulauTree);
            linkedListPulau.put(nama,pulau);
        }

        // inisialisasi Raiden Shogun
        String R = in.next();
        int E = in.nextInt();
        setRaiden(R,E);

        // input perintah perintah
        int Q = in.nextInt();
        for (int i=0; i<Q; i++) {
            String activities = in.next();
            if (activities.equals("UNIFIKASI")) {
                String U = in.next();
                String V = in.next();
                unifikasi(U,V);
            } else if (activities.equals("PISAH")) {
                String U = in.next();
                pisah(U);
            } else if (activities.equals("GERAK")) {
                String arah = in.next();
                int S = in.nextInt();
                gerak(arah,S);
            } else if (activities.equals("TEBAS")) {
                String arah = in.next();
                int S = in.nextInt();
            } else if (activities.equals("TELEPORTASI")) {
                String V = in.next();
                teleportasi(V);
            } else if (activities.equals("RISE")) {

            }
        }

        // close input
        out.flush();

    }

    public static void unifikasi(String U, String V) {

        PulauLL pulauKiri = linkedListPulau.get(U);     // BOVU
        PulauLL pulauKanan = linkedListPulau.get(V);    // KEDA
        // utk update tree
        PulauTree treeKiri = treePulau.get(U);
        PulauTree treeKanan = treePulau.get(V);
        boolean cek = true;
        int res = 0;

        while(pulauKiri != null && cek) {

            if (pulauKiri.next == null) { // udh di ujung
                pulauKiri.tail.next = pulauKanan.head;
                pulauKanan.head.prev = pulauKiri.tail;

                // update LL
                pulauKiri.next = pulauKanan;
                pulauKanan.prev = pulauKiri;
                linkedListPulau.put(pulauKiri.namaPulau, pulauKiri);
                linkedListPulau.put(V,pulauKanan);

                // update tree
                treeKiri.next = treeKanan;
                treeKanan.prev = treeKiri;
                treePulau.put(pulauKiri.namaPulau, treeKiri);
                treePulau.put(V,treeKanan);

                cek = false;
            }
            pulauKiri = pulauKiri.next;
        }

        PulauLL temp = linkedListPulau.get(U);

        while(temp != null) {
            res += temp.banyakDataran;
            temp = temp.next;
        }
        out.println(res);
        // debug
//        String debug1 = "";
//        String debug2 = "";
//        if (pulau.next != null) {
//            debug1 = pulau.next.namaPulau;
//        }
//        if (pulau2.prev != null) {
//            debug2 = pulau2.prev.namaPulau;
//        }
//        out.println("pulau "+pulau.namaPulau+ " has next : "+debug1);
//        out.println("pulau "+pulau2.namaPulau+" has prev : "+debug2);
//        out.println("--------------------------");
//        out.println("Banyak daratan di pulau pertama: "+pulau.banyakPulau);
//        out.println("Banyak daratan di pulau kedua : "+pulau2.banyakPulau);
    }

    public static void pisah(String U) {
        PulauLL pulauBaru = linkedListPulau.get(U); // calon pulau baru
        PulauLL pulauSatu = pulauBaru.prev;

        pulauSatu.tail.next = null;
        pulauBaru.head.prev = null;

        pulauSatu.next = null;
        pulauBaru.prev = null;
        linkedListPulau.put(U, pulauBaru);
        linkedListPulau.put(pulauSatu.namaPulau, pulauSatu);

        int res1 = 0;
        int res2 = 0;

        while (pulauSatu!=null) {       // O(N)
            res1 += pulauSatu.banyakDataran;
            pulauSatu = pulauSatu.prev;
        }

        while (pulauBaru!=null) { // O(N)
            res2 += pulauBaru.banyakDataran;
            pulauBaru = pulauBaru.next;
        }

        out.println(res1 + " " + res2);
//        // debug
//        out.println("Banyak pulau dari pulau kedua : "+pulauBaru.banyakPulau);
//        out.println("Head pulau kedua: "+head2.height.getHeight());
//        out.println("Banyak pulau stlh pisah: "+jumlah1);
//        out.println("Banyak pulau dari pulau kedua : "+pulauBaru.banyakPulau);
    }

    public static void setRaiden(String pulau, int posisi) {
        PulauLL pulauUtama = linkedListPulau.get(pulau);
        Dataran temp = pulauUtama.head;
        pulauUtama.pointerRaiden = temp;
        for (int i=1; i<posisi; i++) {
            temp = temp.next;
        }
        pulauUtama.pointerRaiden = temp;
        posisiRaiden = pulauUtama.namaPulau;
        linkedListPulau.put(pulauUtama.namaPulau, pulauUtama);
        // debug
//        out.println("Posisi raiden awal: "+pulauUtama.pointerRaiden.height.getHeight());
    }

    public static void gerak(String arah, int step) {
        PulauLL pulauRaiden = linkedListPulau.get(posisiRaiden);    // pulau Raiden berada
        if (arah.equals("KIRI")) {  // gerak ke prev
            if (pulauRaiden.pointerRaiden.isKuil) {

            }
            for(int i=0; i<step; i++) {

            }
        } else {    // gerak ke next

        }
    }

    public static void tebas(String arah, int step) {

    }

    public static void teleportasi(String posisi) {         // teleport ke kuil
        PulauLL pulau = linkedListPulau.get(posisi);
        PulauLL posisiLama = linkedListPulau.get(posisiRaiden);
        pulau.pointerRaiden = pulau.head;       // teleport ke kuil baru
        posisiLama.pointerRaiden = null;
        linkedListPulau.put(posisi,pulau);
        linkedListPulau.put(posisiRaiden,posisiLama);
        //update pointer
        posisiRaiden = posisi;
    }

//    /* A utility function to print array of size n */
//    static void printArray(int arr[])
//    {
//        int n = arr.length;
//        for (int i = 0; i < n; ++i)
//            System.out.print(arr[i] + " ");
//        System.out.println();
//    }

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

// Java program to demonstrate
// delete operation in binary
// binary search tree (BST)
class PulauTree {
    NodeTree head;  // head = root
    PulauTree next;
    PulauTree prev;

    // A utility function to do inorder traversal of BST
//    void inorder(Node root)
//    {
//        if (root != null)
//        {
//            inorder(root.left);
//            System.out.print(root.key + " ");
//            inorder(root.right);
//        }
//    }

    /* A utility function to print preorder traversal of BST */
//    void preOrderUpdate(NodeTree node, PulauLL pulau) {
//        if (node == null) {
//            return;
//        }
////        System.out.print(node.height + " ");
//        pulau.head
//        preOrderUpdate(node.left);
//        preOrderUpdate(node.right);
//    }

    /* A function that constructs Balanced Binary Search Tree
     from a sorted array */
//    NodeTree sortedArrayToBST(int arr[], int start, int end) {
//
//        /* Base Case */
//        if (start > end) {
//            return null;
//        }
//
//        /* Get the middle element and make it root */
//        int mid = (start + end) / 2;
//        NodeTree node = new NodeTree(arr[mid]);
//
//        /* Recursively construct the left subtree and make it
//         left child of root */
//        node.left = sortedArrayToBST(arr, start, mid - 1);
//
//        /* Recursively construct the right subtree and make it
//         right child of root */
//        node.right = sortedArrayToBST(arr, mid + 1, end);
//
//        return node;
//    }

    // A utility function to insert a new node
// with given key in BST
    NodeTree insert(NodeTree node, int key)
    {

        // If the tree is empty, return a new node
        if (node == null)
            return new NodeTree(key);

        // If key already exists in BST,
        // increment count and return
        if (key == node.height)
        {
            (node.counter)++;
            return node;
        }

        // Otherwise, recur down the tree
        if (key < node.height)
            node.left = insert(node.left, key);
        else
            node.right = insert(node.right, key);

        // Return the (unchanged) node pointer
        return node;
    }

    // Given a binary search tree and a key, this
// function deletes the key and returns the
// new root
    NodeTree deleteNode(NodeTree root, int k)
    {

        // Base case
        if (root == null)
            return root;

        // Recursive calls for ancestors of
        // node to be deleted
        if (root.height > k)
        {
            root.left = deleteNode(root.left, k);
            return root;
        }
        else if (root.height < k)
        {
            root.right = deleteNode(root.right, k);
            return root;
        }

        // We reach here when root is the node
        // to be deleted.

        // If one of the children is empty
        if (root.left == null)
        {
            NodeTree temp = root.right;
            return temp;
        }
        else if (root.right == null)
        {
            NodeTree temp = root.left;
            return temp;
        }

        // If both children exist
        else
        {
            NodeTree succParent = root;

            // Find successor
            NodeTree succ = root.right;

            while (succ.left != null)
            {
                succParent = succ;
                succ = succ.left;
            }

            // Delete successor. Since successor
            // is always left child of its parent
            // we can safely make successor's right
            // right child as left of its parent.
            // If there is no succ, then assign
            // succ->right to succParent->right
            if (succParent != root)
                succParent.left = succ.right;
            else
                succParent.right = succ.right;

            // Copy Successor Data to root
            root.height = succ.height;

            return root;
        }
    }

    /* Given a non-empty binary search tree, return
the node with minimum key value found in that
tree. Note that the entire tree does not need
to be searched. */
    NodeTree minValueNode(NodeTree node)
    {
        NodeTree current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;

        return current;
    }
}

class Dataran {
    Dataran next;
    Dataran prev;
    NodeTree heightObj;     // refer ke node BST
    boolean isKuil;
    int height;             // selalu update kalo node BST rise, quake, dkk

    public Dataran(int tinggi, Dataran next, Dataran prev){
        this.height =tinggi;

        this.next = next;
        this.prev = prev;
        this.isKuil = false;
    }

    public void updateHeight(NodeTree updateTinggi) {
        this.height = updateTinggi.height;
    }

}

class PulauLL {
    Dataran head;
    Dataran pointer;
    Dataran pointerRaiden;
    Dataran tail;
    int banyakDataran;
    PulauLL next;
    PulauLL prev;
    String namaPulau;
//    HashMap<Integer, Height> tinggiSama = new HashMap<>();  // hashmap dgn object tinggi sama (utk grouping dataran)

    public PulauLL() {
        this.head = null;
        this.pointer = null;
        this.pointerRaiden = null;
        this.tail = null;
        this.banyakDataran = 0;
        this.next = null;
        this.prev = null;
        this.namaPulau = "";
    }

    public void insert(int tinggi){  // add dataran ke LL (untuk garakan raiden)

//        Height tinggi = new Height(input);
        if (head==null) { // head == null or the first node
            head = new Dataran(tinggi,null,null);
            pointer = head; tail = pointer;
            head.isKuil = true; // head = node kuil
        } else if (pointer == head && head.next == null) {  // making second node
            head.next = new Dataran(tinggi,null,head);
            pointer = head.next; tail = pointer;
        } else if (pointer == tail){        // adding last element (exclude 1st & 2nd node)
            pointer.next = new Dataran(tinggi,null,pointer);
            pointer = pointer.next; tail = pointer;
        } else {    // inserting floor (nambah di tengah)
            if (pointer != null) {
                Dataran temp = new Dataran(tinggi,pointer.next,pointer);
                if (pointer.next != null) {
                    pointer.next.prev = temp;
                }
                pointer.next = temp;
                pointer = pointer.next;
            }
        }
        this.banyakDataran++;
//        tinggiSama.put(input,tinggi);
    }

//    public void divide(Dataran input) {  // pisah & return byk pulau pertama
//        Dataran dataran1 = search(input);       // O(n)
//        if (dataran1 != null) {     // pisah 2 pulau doang
//            tail = dataran1;
//            tail.next = null;
//            if (input != null) {
//                input.prev = null;
//            }
//        }
//
//        //debug
////        System.out.println("Banyak pulau zzz: "+this.banyakPulau);
//
////        System.out.println("Banyak pulau zzz: "+this.banyakPulau);
//    }

//    public Dataran search(Dataran input) {  // cari node sebelum head pulau kedua yg digabung
//        Dataran temp = head;
//
//        while (temp != null) {
//            if (temp.height.getHeight()==input.height.getHeight()) {
//                return temp.prev;   // return sebelum head pulau kedua yg mau dipisah
//            }
//            temp = temp.next;
//        }
//        return null;
//    }

}

//class DataranGroup {
//    int height;
//    Dataran head;
//    Dataran tail;
//
//    public DataranGroup() {
//
//    }
//}

//class GabunganPulau {
//    PulauLL head;
//    PulauLL tail;
//    PulauLL pointer;
//    int banyakPulau;
//
//    public GabunganPulau() {
//        this.head = null;
//        this.tail = null;
//        this.pointer = 0;
//        this.banyakPulau = 0;
//    }
//
//    public void gabung(PulauLL input) {
//
//    }
//
//    public void pisah(PulauLL input) {
//
//    }
//}

class NodeTree {
    int height, counter;    // height = key
    NodeTree left, right;

    public NodeTree(int tinggi) {
        this.height = tinggi;
        this.counter = 1;
        this.left = this.right = null;
    }
}

//class Height {
//    int height;
//
//    public Height(int height) {
//        this.height = height;
//    }
//
//    public int getHeight() {
//        return height;
//    }
//
//    public void setHeight(int height) {
//        this.height = height;
//    }
//}

/* Java program for Merge Sort */
class MergeSort
{
    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    void merge(int arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    void sort(int arr[], int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m =l+ (r-l)/2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }



    // Driver code
//    public static void main(String args[])
//    {
//        int arr[] = { 12, 11, 13, 5, 6, 7 };
//
//        System.out.println("Given Array");
//        printArray(arr);
//
//        MergeSort ob = new MergeSort();
//        ob.sort(arr, 0, arr.length - 1);
//
//        System.out.println("\nSorted array");
//        printArray(arr);
//    }
}
/* This code is contributed by Rajat Mishra */

/*Source :
 * https://www.geeksforgeeks.org/binary-search-tree-set-2-delete/?ref=lbp
 * source code Lab 4
 * https://www.geeksforgeeks.org/how-to-handle-duplicates-in-binary-search-tree/
 * https://www.geeksforgeeks.org/sorted-array-to-balanced-bst/
 *
 * */

