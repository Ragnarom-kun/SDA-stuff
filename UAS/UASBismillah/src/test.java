public class test {

    public static void main(String[] args) {
//        int[] heap = new int[] {13, 20, 17, 51, 23, 30, 45};
//        int[] heap = new int[] {73, 45, 59, 34, 17, 20, 63};
        int[] heap = new int[] {85, 20, 70, 18, 10, 12, 60};
        System.out.println(mystery(heap));
    }

    public static boolean mystery(int[] heap) {
        for (int i = 0; i < heap.length / 2; i++) {
            int j = (i * 2) + 1;
            if (j < heap.length && heap[i] < heap[j++])
                return false;
            if (j < heap.length && heap[i] < heap[j])
                return false;
        }
        return true;
    }

    public void cetakSisiKiri(Node n){
        if (n==null) {
            System.out.println("Kosong gan");
        } else {
            if (n.left != null) {
                cetakSisiKiri(n.left);
            }
            System.out.println(n.data);
        }
    }
}
