//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.*;
//
//class Lab6Naive {
//    private static InputReader in;
//    private static PrintWriter out;
//    static List<Integer> tanah = new ArrayList<>();
//
//    public static void main(String[] args) {
//        InputStream inputStream = System.in;
//        in = new InputReader(inputStream);
//        OutputStream outputStream = System.out;
//        out = new PrintWriter(outputStream);
//
//        int N = in.nextInt();
//        for (int i = 0; i < N; i++) {
//            int height = in.nextInt();
//            tanah.add(height);
//        }
//
//        int Q = in.nextInt();
//        while(Q-- > 0) {
//            String query = in.next();
//            if (query.equals("A")) {
//                int y = in.nextInt();
//                tanah.add(y);
//            } else if (query.equals("U")) {
//                int x = in.nextInt();
//                int y = in.nextInt();
//                tanah.set(x, y);
//            } else {
//                handleR();
//            }
//        }
//
//        out.flush();
//    }
//
//    static ArrayList<Integer> findMin() {
//        ArrayList<Integer> res = new ArrayList<>();     // [index, element]
//        res.add(0); res.add(tanah.get(0));
//        for (int i=1; i<tanah.size(); i++) {
//            int var = tanah.get(i);
//            if (var < res.get(1)) {
//                res.set(0,i);       // index
//                res.set(1,var);     // element
//            }
//        }
//
//        return res;
//    }
//
//    static void handleR() {
//        ArrayList<Integer> temp = findMin();        // [index, element] = array list of smallest element
//        int index = temp.get(0);
//
//        if (tanah.size()==1) {  // cuma 1 element
//            out.println(tanah.get(0) + " " + 0);
//        } else {
//            if (index==0) { // paling kiri
//                int element = tanah.get(0);
//                int right = tanah.get(1);
//                int baru = Math.max(element,right);
//
//                // set element
//                tanah.set(0,baru);
//                tanah.set(1,baru);
//                out.println(baru + " " + 0);
//            } else if (index==tanah.size()-1) { // paling kanan
//                int sizeArr = tanah.size()-1;
//                int element = tanah.get(sizeArr);
//                int left = tanah.get(sizeArr-1);
//                int baru = Math.max(element,left);
//
//                // set element
//                tanah.set(sizeArr,baru);
//                tanah.set(sizeArr-1,baru);
//                out.println(baru + " " + sizeArr);
//            } else {
//                int element = tanah.get(index);
//                int left = tanah.get(index-1);
//                int right = tanah.get(index+1);
//
//                int baru = Math.max(element,left);
//                int baru2 = Math.max(baru,right);
//                // set element
//                tanah.set(index,baru2);
//                tanah.set(index-1,baru2);
//                tanah.set(index+1,baru2);
//                out.println(baru2 + " " + index);
//            }
//        }
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
//}
//
//// Source : ga ada karena iseng wkwkkwwk (naive solution)