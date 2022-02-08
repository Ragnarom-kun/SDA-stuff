public class LinkedListTest {
    public static void main(String[] args) {
        MyList list1 = new MyList();
//        list1.add(1); list1.add(2); list1.add(3); list1.add(4); list1.add(5);
//        list1.add(5);list1.add(6);
//        list1.add(5);list1.add(7);list1.add(9);list1.add(12);
//        list1.add(1);list1.add(2);
//        list1.add(4);
        list1.add(8);list1.add(9);list1.add(10);
        System.out.println(list1);

        MyList list2 = new MyList();
        list2.add(3); list2.add(5); list2.add(6); list2.add(7);
        System.out.println(list2);

        MyList diff = myFunc(list1, list2);
        System.out.println(diff);
    }

    static MyList myFunc(MyList list1, MyList list2) {
        MyList result = new MyList();
        Node node1 = list1.head;
        Node node2 = list2.head;

        while (node1 != null) {
            if (node1.value > node2.value) {
                if (node2.next == null) {
                    result.add(node1.value);
                    node1 = node1.next;
                } else {
                    node2 = node2.next;
                }
            } else if (node1.value == node2.value) {
                node1 = node1.next;
                if (node2.next != null) {
                    node2 = node2.next;
                }
            } else {
                result.add(node1.value);
                node1 = node1.next;
            }
        }

        return result;
    }
}

class MyList {
    Node head;

    MyList() {
        head = null;
    }

    void add(int val) {
        if (head == null)
            head = new Node(val, null);
        else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new Node(val, null);
        }
    }

    public String toString() {
        String s = "";
        Node temp = head;
        while (temp != null) {
            s += temp.value;
            temp = temp.next;
            if (temp != null)
                s += (" ");
        }
        return s;
    }
}

class Node {
    int value;
    Node next;

    Node(int v, Node n) {
        value = v;
        next = n;
    }
}

/*
Nama    : Romi Fadhurrohman Nabil
NPM     : 2006535016
Kelas   : SDA D
*/