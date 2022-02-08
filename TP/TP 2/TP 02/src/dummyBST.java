// How to use BST
//    public static void main(String args[])
//    {
//
//    /* Let us create following BST
//          50
//        /     \
//       30     70
//      / \    / \
//     20 40  60 80 */
//        Node root = null;
//        root = insert(root, 50);
//        root = insert(root, 30);
//        root = insert(root, 20);
//        root = insert(root, 40);
//        root = insert(root, 70);
//        root = insert(root, 60);
//        root = insert(root, 80);
//
//        System.out.println("Inorder traversal of the " +
//                "given tree");
//        inorder(root);
//
//        System.out.println("\nDelete 20\n");
//        root = deleteNode(root, 20);
//        System.out.println("Inorder traversal of the " +
//                "modified tree");
//        inorder(root);
//
//        System.out.println("\nDelete 30\n");
//        root = deleteNode(root, 30);
//        System.out.println("Inorder traversal of the " +
//                "modified tree");
//        inorder(root);
//
//        System.out.println("\nDelete 50\n");
//        root = deleteNode(root, 50);
//        System.out.println("Inorder traversal of the " +
//                "modified tree");
//        inorder(root);
//    }

//int res = 0;
//        PulauLL temp = pulau;
//
//        while(temp!=null) {
//        if (temp.next==null) {
//        // update head & tail hsl unifikasi
//        temp.tail.next = pulau2.head;
//        pulau2.head.prev = temp.tail;
//        temp.next = pulau2;
//        pulau2.prev = temp;
//        linkedListPulau.put(U, temp);
//        linkedListPulau.put(V, pulau2);
//        break;
//        }
//        temp = temp.next;
//        }
//
//        //printing
//        pulau = linkedListPulau.get(U);
//        PulauLL temp2 = pulau;
//        while(temp2!=null) {
//        res += temp2.banyakPulau;
//        temp2 = temp2.next;
//        }
//        out.println(res);

//        if (pulau.next!=null) { // gabung kedua kalinya dst
//            PulauLL temp = pulau;
//            while(temp.next!=null) {
//                temp.union(pulau2);
//                linkedListPulau.put(temp.namaPulau, temp);
//                temp = temp.next;
//            }
//            pulau = temp;
//        }
// gabung pertama sesuai case
//        if (pulau.next == null) {      // for the first time
//
//            pulau.tail = pulau.next;
//        } else {
//            pulau.tail = pulau2;
//        }

//        PulauLL temp = pulau2;
//        while(temp!=null) {         // loop hitung daratan pulau kedua
//            res += temp.banyakPulau;
//            temp = temp.next;
//        }
//
//        while(pulau!=null) {        // loop hitung dataran pertama + dengan hasil dataran pulau kedua
//            res += pulau.banyakPulau;
//
//            if (pulau.next==null) {
//                pulau.next = pulau2;
//                pulau2.prev = pulau;
//
//                pulau.union(pulau2);
//                linkedListPulau.put(U, pulau);
//                linkedListPulau.put(V, pulau2);
//                break;
//            }
//
//            pulau = pulau.next;
//        }