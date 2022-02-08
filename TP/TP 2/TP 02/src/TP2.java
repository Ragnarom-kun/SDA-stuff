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
    static Raiden raiden;

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
            int[] tempArr = new int[banyak];
//            PulauLL pulauSorted = new PulauLL();

            // inisialisasi dataran
            for(int j=0; j<banyak; j++) {
                int dataran = in.nextInt();
                pulau.insert(dataran);
                pulauTree.head = pulauTree.insert(pulauTree.head, dataran);
                tempArr[j] = dataran;
            }

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
                tebas(arah,S);
            } else if (activities.equals("TELEPORTASI")) {
                String V = in.next();
                teleportasi(V);
            } else if (activities.equals("RISE")) {
                String U = in.next();
                long H = in.nextInt();
                long X = in.nextInt();
                rise(U,H,X);
            } else if (activities.equals("QUAKE")) {
                String U = in.next();
                long H = in.nextInt();
                long X = in.nextInt();
                quake(U,H,X);
            } else if (activities.equals("CRUMBLE")) {
                crumble();
            } else if (activities.equals("STABILIZE")) {
                stabilize();
            } else {    // sweeping
                String U = in.next();
                long L = in.nextInt();
                sweeping(U,L);
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

        // print
        out.println(res);
    }

    public static void pisah(String U) {
        PulauLL pulauBaru = linkedListPulau.get(U); // calon pulau baru
        PulauLL pulauSatu = pulauBaru.prev;
        PulauTree treeBaru = treePulau.get(U);
        PulauTree treeSatu = treeBaru.prev;

        pulauSatu.tail.next = null;
        pulauBaru.head.prev = null;

        pulauSatu.next = null;
        pulauBaru.prev = null;
        linkedListPulau.put(U, pulauBaru);
        linkedListPulau.put(pulauSatu.namaPulau, pulauSatu);

        // update tree nya jg
        treeSatu.next = null;
        treeBaru.prev = null;
        treePulau.put(U, treeBaru);
        treePulau.put(pulauSatu.namaPulau, treeSatu);

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
    }

    public static void setRaiden(String pulau, int posisi) {
        PulauLL pulauUtama = linkedListPulau.get(pulau);
        Dataran temp = pulauUtama.head;
        pulauUtama.pointerRaiden = pulauUtama.head;
        if (posisi > 1) {
            for (int i=1; i<posisi; i++) {
                temp = temp.next;
            }
            pulauUtama.pointerRaiden = temp;
        }
        raiden = new Raiden(pulauUtama.pointerRaiden, pulauUtama);
        linkedListPulau.put(pulauUtama.namaPulau, pulauUtama);
    }

    public static void gerak(String arah, int step) {
        String posisiRaiden = raiden.pulau.namaPulau;
        PulauLL pulauRaiden = linkedListPulau.get(posisiRaiden);    // pulau Raiden berada
        if (arah.equals("KIRI")) {  // gerak ke prev
            if (pulauRaiden.pointerRaiden.prev == null) { // ujung kiri
                out.println(pulauRaiden.pointerRaiden.height);
            }
            else {
                while(step > 0 && pulauRaiden.pointerRaiden != null) {
                    step--;
                    pulauRaiden.pointerRaiden = pulauRaiden.pointerRaiden.prev;
                }
                out.println(pulauRaiden.pointerRaiden.height);
                String posisiBaru = pulauRaiden.namaPulau;
                raiden.pointer = pulauRaiden.pointerRaiden;
                raiden.pulau = pulauRaiden;
                linkedListPulau.put(posisiBaru,pulauRaiden);
            }
        } else {    // gerak ke next (KANAN)
            if(pulauRaiden.pointerRaiden.next==null) {
                out.println(pulauRaiden.pointerRaiden.height);  // ujung kanan
            } else {
                while(step > 0 && pulauRaiden.pointerRaiden != null) {
                    step--;
                    pulauRaiden.pointerRaiden = pulauRaiden.pointerRaiden.next;
                }
                out.println(pulauRaiden.pointerRaiden.height);
                String posisiBaru = pulauRaiden.namaPulau;
                raiden.pointer = pulauRaiden.pointerRaiden;
                raiden.pulau = pulauRaiden;
                linkedListPulau.put(posisiBaru,pulauRaiden);
            }
        }
    }

    public static void tebas(String arah, int count) {
        String posisiRaiden = raiden.pulau.namaPulau;
        PulauLL pulauRaiden = linkedListPulau.get(posisiRaiden);    // pulau Raiden berada
        int step = 0;
        long posisiTebasAwal = pulauRaiden.pointerRaiden.height;
        Dataran dataranSlider = null;

        if (arah.equals("KIRI")) {  // gerak ke prev
            while(count > 0 && pulauRaiden.pointerRaiden != null) {
                if (pulauRaiden.pointerRaiden.height==posisiTebasAwal) {
                    dataranSlider = pulauRaiden.pointerRaiden;
                    step++;
                    count--;
                }
                pulauRaiden.pointerRaiden = pulauRaiden.pointerRaiden.prev;
            }
            if (step==0) {
                out.println(0);
            } else {
                out.println(dataranSlider.next.height);
                posisiRaiden = pulauRaiden.namaPulau;
                raiden.pointer = dataranSlider;
                raiden.pulau = pulauRaiden;
                linkedListPulau.put(posisiRaiden,pulauRaiden);
            }
        } else {    // gerak ke next (KANAN)
            while(count > 0 && pulauRaiden.pointerRaiden != null) {
                if (pulauRaiden.pointerRaiden.height==posisiTebasAwal) {
                    dataranSlider = pulauRaiden.pointerRaiden;
                    step++;
                    count--;
                }
                pulauRaiden.pointerRaiden = pulauRaiden.pointerRaiden.next;
            }
            if (step==0) {
                out.println(0);
            } else {
                out.println(dataranSlider.prev.height);
                posisiRaiden = pulauRaiden.namaPulau;
                raiden.pointer = dataranSlider;
                raiden.pulau = pulauRaiden;
                linkedListPulau.put(posisiRaiden,pulauRaiden);
            }
        }
    }

    public static void teleportasi(String posisi) {         // teleport ke kuil
        PulauLL pulau = linkedListPulau.get(posisi);
        PulauLL posisiLama = raiden.pulau;
        pulau.pointerRaiden = pulau.head;       // teleport ke kuil baru
        posisiLama.pointerRaiden = null;
        linkedListPulau.put(posisiLama.namaPulau,posisiLama);   // lama
        raiden.pointer = pulau.pointerRaiden;
        raiden.pulau = pulau;
        linkedListPulau.put(posisi,pulau);              // baru
        out.println(pulau.head.height);
    }

    public static void rise(String U, long H, long X){
        PulauLL pulau = linkedListPulau.get(U);
        Dataran root = pulau.head;
        long res = 0;
        while (root != null) {
            long temp = root.height;
            if (temp > H) {
                temp += X;
                root.height = temp;
                res++;
            }
            root = root.next;
        }
        linkedListPulau.put(pulau.namaPulau, pulau);
        out.println(res);
    }

    public static void quake(String U, long H, long X){
        PulauLL pulau = linkedListPulau.get(U);
        Dataran root = pulau.head;
        long res = 0;
        while (root != null) {
            long temp = root.height;
            if (temp < H) {
                temp -= X;
                if (temp <= 0) {
                    temp = 1;
                }
                root.height = temp;
                res++;
            }

            root = root.next;
        }
        linkedListPulau.put(pulau.namaPulau, pulau);
        out.println(res);
    }

    public static void crumble() {
        PulauLL pulauShogun = linkedListPulau.get(raiden.pulau);
        PulauLL pulauNext = pulauShogun.next;
        Dataran tempat = pulauShogun.pointerRaiden;
        long res = tempat.height;

        if (tempat.isKuil) {
            out.println(0);
        } else if (pulauNext != null && tempat.next == pulauNext.head) {     // ada pulau gabungan di kanan
            // pisah
            tempat.prev.next = pulauNext.head;
            pulauNext.head.prev = tempat.prev;

            // update
            pulauShogun.tail = pulauNext.head.prev;
            pulauShogun.banyakDataran--;
            linkedListPulau.put(pulauShogun.namaPulau, pulauShogun);
            if (pulauNext != null) {
                linkedListPulau.put(pulauNext.namaPulau, pulauNext);
            }
            out.println(res);
        } else if (tempat.next==null) {     // dataran terakhir pulau
            tempat.prev.next = null;
            pulauShogun.tail = tempat.prev;
            pulauShogun.banyakDataran--;
            linkedListPulau.put(pulauShogun.namaPulau, pulauShogun);
            if (pulauNext != null) {
                linkedListPulau.put(pulauNext.namaPulau, pulauNext);
            }
            out.println(res);
        } else {                                  // pisah biasa
            tempat.prev.next = tempat.next;
            tempat.next.prev = tempat.prev;
            pulauShogun.banyakDataran--;
            // update
            linkedListPulau.put(pulauShogun.namaPulau, pulauShogun);
            if (pulauNext != null) {
                linkedListPulau.put(pulauNext.namaPulau, pulauNext);
            }
            out.println(res);
        }
    }

    public static void stabilize() {
        PulauLL pulauShogun = linkedListPulau.get(raiden.pulau);
        PulauLL pulauNext = pulauShogun.next;
        Dataran tempat = pulauShogun.pointerRaiden;
        long res = 0;
        long tinggi = 0;
        if (tempat.prev != null) {
            tinggi = tempat.prev.height;
            res = tinggi;
        }

        if (tempat.isKuil) {
            out.println(0);
        } else if (pulauNext != null && tempat.next == pulauNext.head) {                 // last element tp ada gabungan pulau
            Dataran temp = new Dataran(tinggi,null,null);
            temp.prev = tempat;
            temp.next = pulauNext.head;
            tempat.next = temp;
            pulauNext.head.prev = temp;
            pulauShogun.tail = temp;

            pulauShogun.banyakDataran++;
            linkedListPulau.put(pulauShogun.namaPulau, pulauShogun);
            linkedListPulau.put(pulauNext.namaPulau, pulauNext);
            out.println(res);
        } else if (tempat.next==null) {     // last element
            Dataran temp = new Dataran(tinggi,null,null);
            temp.prev = tempat;
            tempat.next = temp;
            pulauShogun.tail = temp;

            pulauShogun.banyakDataran++;
            linkedListPulau.put(pulauShogun.namaPulau, pulauShogun);
            if (pulauNext != null) {
                linkedListPulau.put(pulauNext.namaPulau, pulauNext);
            }
            out.println(res);
        } else {                // add di tengah
            Dataran temp = new Dataran(tinggi,tempat.next,tempat);
            tempat.next = temp;
            pulauShogun.banyakDataran++;
            linkedListPulau.put(pulauShogun.namaPulau, pulauShogun);
            if (pulauNext != null) {
                linkedListPulau.put(pulauNext.namaPulau, pulauNext);
            }
            out.println(res);
        }
    }

    public static void sweeping(String pulau, long batas) {
        PulauLL objPulau = linkedListPulau.get(pulau);
        Dataran root = objPulau.head;
        int res = 0;
        while(root!=null) {
            if (root.height < batas) {
                root.prev.next = root.next;
                root.next.prev = root.prev;
                objPulau.banyakDataran--;
                res++;
            }
            root = root.next;
        }
        linkedListPulau.put(objPulau.namaPulau, objPulau);
        out.println(res);
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

// Java program to demonstrate
// delete operation in binary
// binary search tree (BST)
class PulauTree {
    NodeTree head;  // head = root
    PulauTree next;
    PulauTree prev;

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

    // A utility function to search a given key in BST
    public NodeTree search(NodeTree root, int key)
    {
        // Base Cases: root is null or key is present at root
        if (root==null || root.height==key)
            return root;

        // Key is greater than root's key
        if (root.height < key)
            return search(root.right, key);

        // Key is smaller than root's key
        return search(root.left, key);
    }
}

class Dataran {
    Dataran next;
    Dataran prev;
    NodeTree heightObj;     // refer ke node BST
    boolean isKuil;
    long height;             // selalu update kalo node BST rise, quake, dkk

    public Dataran(long tinggi, Dataran next, Dataran prev){
        this.height = tinggi;
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

    public void insert(long tinggi){  // add dataran ke LL (untuk garakan raiden)

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
    }

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
}

class Raiden {
    Dataran pointer;
    PulauLL pulau;

    public Raiden(Dataran pointer, PulauLL pulau) {
        this.pointer = pointer;
        this.pulau = pulau;
    }
}
/* This code is contributed by Rajat Mishra */

/*Source :
* https://www.geeksforgeeks.org/binary-search-tree-set-2-delete/?ref=lbp
* source code Lab 4
* https://www.geeksforgeeks.org/how-to-handle-duplicates-in-binary-search-tree/
* https://www.geeksforgeeks.org/sorted-array-to-balanced-bst/
*
* */

