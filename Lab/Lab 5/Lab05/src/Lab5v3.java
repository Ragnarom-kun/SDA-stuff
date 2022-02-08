import java.io.*;
import java.util.*;

public class Lab5v3 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    static HashMap<Integer, Integer> banyakTipe = new HashMap<>();      // counting banyak tipe per harga permen
    static HashMap<String, Integer> permenHarga = new HashMap<>();      // key : nama permen, value : harga permen
    static AVLTree tree = new AVLTree();                                // AVL tree yg digunakan

    public static void main(String[] args) {

        //Menginisialisasi kotak sebanyak N
        int N = in.nextInt();
        for(int i = 0; i < N; i++){
            String nama = in.next();
            int harga = in.nextInt();
            int tipe = in.nextInt();
            handleStock(nama, harga, tipe);
        }

        //Query 
        //(method dan argumennya boleh diatur sendiri, sesuai kebutuhan)
        int NQ = in.nextInt();
        for(int i = 0; i < NQ; i++){
            String Q = in.next();
            if (Q.equals("BELI")){
                int L = in.nextInt();
                int R = in.nextInt();
                out.println(handleBeli(L, R));

            }else if(Q.equals("STOCK")){
                String nama = in.next();
                int harga = in.nextInt();
                int tipe = in.nextInt();
                handleStock(nama, harga, tipe);

            }else{ //SOLD_OUT
                String nama = in.next();
                handleSoldOut(nama);

            }
        }

        out.flush();
    }

    static String handleBeli(int L, int R){
        int min = tree.cariMin(tree.root,L,-1);
        int max = tree.cariMax(tree.root,R,-1);

        if (min== -1 || max== -1) {
            return -1 + " " + -1;
        } else if (!((L <= min && min <= R) && (L <= max && max <= R))) {
            return -1 + " " + -1;
        } else if (min==max && banyakTipe.get(min)<2) {
            return -1 + " " + -1;
        } else if (min > max) {
            return -1 + " " + -1;
        }

        return min + " " + max;
    }

    static void handleStock(String nama, int harga, int tipe){
        if (!banyakTipe.containsKey(harga)) {
            banyakTipe.put(harga,1);
            tree.root = tree.insert(tree.root, harga);
        } else {
            int count = banyakTipe.get(harga);
            count++;
            banyakTipe.put(harga,count);
        }
        permenHarga.put(nama,harga);
    }

    static void handleSoldOut(String nama){
        int harga = permenHarga.get(nama);
        int count = banyakTipe.get(harga);

        if (count > 1) {
            count--;
            banyakTipe.put(harga,count);
        } else {
            tree.root = tree.deleteNode(tree.root, harga);
            banyakTipe.remove(harga);
        }
        permenHarga.remove(nama);
    }


    // taken from https://codeforces.com/submissions/Petr
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

class Node
{
    int key, height;
    Node left, right;

    Node(int d)
    {
        key = d;
        height = 1;
    }
}

class AVLTree
{
    Node root;

    // A utility function to get height of the tree
    int height(Node N)
    {
        if (N == null)
            return 0;
        return N.height;
    }

    // A utility function to get maximum of two integers
    int max(int a, int b)
    {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    Node rightRotate(Node y)
    {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    Node leftRotate(Node x)
    {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    int getBalance(Node N)
    {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    Node insert(Node node, int key)
    {
        /* 1. Perform the normal BST rotation */
        if (node == null)
            return (new Node(key));

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else // Equal keys not allowed
            return node;

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                height(node.right));

        /* 3. Get the balance factor of this ancestor
        node to check whether this node became
        Wunbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then
        // there are 4 cases Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key)
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key)
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    /*Fungsi untuk mencari batas atas dari node harga paling kiri --> leftValue >= L*/
    int cariMin(Node node, int left, int res)
    {
        // default value
        if (node == null) {
            return res;
        }

        /* If root->data is equal to key */
        if (node.key == left)
            return node.key;
        else if (left < node.key) {
            res = node.key;
            node = node.left;
        } else {
            node = node.right;
        }

        return cariMin(node, left, res);
    }

    /* Given a non-empty binary search tree, return the
    node with minimum key value found in that tree.
    Note that the entire tree does not need to be
    searched. */
    Node minValueNode(Node node)
    {
        Node current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;

        return current;
    }

    Node deleteNode(Node root, int key)
    {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key < root.key)
            root.left = deleteNode(root.left, key);

            // If the key to be deleted is greater than the
            // root's key, then it lies in right subtree
        else if (key > root.key)
            root.right = deleteNode(root.right, key);

            // if key is same as root's key, then this is the node
            // to be deleted
        else
        {

            // node with only one child or no child
            if ((root.left == null) || (root.right == null))
            {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else // One child case
                    root = temp; // Copy the contents of
                // the non-empty child
            }
            else
            {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                Node temp = minValueNode(root.right);

                // Copy the inorder successor's data to this node
                root.key = temp.key;

                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = max(height(root.left), height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0)
        {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0)
        {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    /*Fungsi untuk mencari batas bawah dari node harga paling kanan --> rightValue <= R*/
    int cariMax(Node node, int right, int res) {
        // default value
        if (node == null) {
            return res;
        }
        if (node.key == right) {
            return node.key;
        } else if (right > node.key) {
            res = node.key;
            node = node.right;
        } else {
            node = node.left;
        }
        return cariMax(node, right, res);
    }
}

// This code has been contributed by Mayank Jaiswal

/* Source :
https://www.geeksforgeeks.org/avl-tree-set-2-deletion/
https://www.geeksforgeeks.org/avl-with-duplicate-keys/
https://www.geeksforgeeks.org/floor-and-ceil-from-a-bst/
https://www.geeksforgeeks.org/floor-in-binary-search-tree-bst/?ref=lbp
*/

/* Get the inspiration from :
Sulthan Afif Althaf, Stefanus Ndaru Wedhatama
*/