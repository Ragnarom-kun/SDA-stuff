import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


public class TestLab {

    private static InputReader in;
    private static PrintWriter out;
    static long[] prev;

//    // TODO
//    static private int findMaxBerlian(ArrayList<Integer> S, ArrayList<Integer> M, ArrayList<Integer> B) {
//        return -1;
//    }
//
//    // TODO
//    static private int findBanyakGalian(ArrayList<Integer> S, ArrayList<Integer> M, ArrayList<Integer> B) {
//        return -1;
//    }

    static private long[] findTotal(ArrayList<Integer> S, ArrayList<Integer> M, ArrayList<Integer> B) {
        Node[][] arr = new Node[S.size()+1][S.size()+1];
        long[] res = new long[2];
        res[0] = 0;
        res[1] = 0;

        // initiator with 0 array
        for (int i=0; i<arr[0].length; i++) {
            arr[0][i] = new Node(0,0);
        }

        // checking loop
        for (int i=1; i<arr.length; i++) {
            for (int j=i; j<arr.length; j++) {
                long num1 = (long) S.get(j-1); // siang
                long num2 = (long) M.get(j-1); // malam

//                if (i==1) {
//                    arr[1][j] = new Node(num1,num2);
//                    if (num1 > res[1]) {
//                        res[0] = 1;
//                        res[1] = num1 + B.get(0);
//                    } else if (num2 > res[1]) {
//                        res[0] = 1;
//                        res[1] = num2 + B.get(0);
//                    }
//                    continue;
////                    res[1] += B.get(i-1);
//                }

                Node plus1 = arr[i - 1][j - 1];
                long num3 = num1 + plus1.malam;
                long num4 = num2 + plus1.siang;
                arr[i][j] = new Node(num3, num4);

                long max = 0;
                for (int k=j-2; k>0; k--) {
                    if (arr[i-1][k]==null) {
                        continue;
                    }
                    long sem = arr[i-1][k].getMax();
                    if (sem>max) {
                        max = sem;
                    }
                }

                long num5 = max + num1;
                long num6 = max + num2;
                if (num5 > num3) {
                    arr[i][j] = new Node(num5,num4);
                    num3 = num5;
                }
                if (num6 > num4) {
                    arr[i][j] = new Node(num3,num6);
                }
            }

            if (i==1) {
                System.out.println(Arrays.toString(arr[i]));
                continue;
            }

            long angka = res[1];

//            for (int z=arr[i].length-1; z>0; z--) {
//                if (arr[i][z] == null || arr[i][z-1] == null) {
//                    continue;
//                }
//
//                if (arr[i][z].compareTo(arr[i][z-1]) > 0) {
//                    angka = arr[i][z].getMax();
//                }
//                else {
//                    angka = arr[i][z-1].getMax();
//                }
//            }

            for (int z=0; z<arr[i].length; z++) {
                if(arr[i][z] == null) {
                    continue;
                }
                if (arr[i][z] != null) {
                    if (angka < arr[i][z].getMax()) {
                        angka = arr[i][z].getMax();
                    }
                }
            }

            if (angka > res[1]) {
                res[1] = angka + B.get(i-1);
                res[0] = i;
            }
            System.out.println(Arrays.toString(arr[i]));
        }
        return res;
    }

//    private static Node findOptimation(Node nodes, int j) {
//        Node test =
//    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        ArrayList<Integer> S = new ArrayList<>();
        ArrayList<Integer> M = new ArrayList<>();
        ArrayList<Integer> B = new ArrayList<>();

        int N = in.nextInt();

        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            S.add(tmp);
        }

        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            M.add(tmp);
        }

        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            B.add(tmp);
        }

//        int jawabanBerlian = findMaxBerlian(S,M,B);
//        int jawabanGalian = findBanyakGalian(S,M,B);

        long[] jawaban = findTotal(S,M,B);

//        out.print(jawabanBerlian + " " + jawabanGalian);
        out.print(jawaban[1] + " " + jawaban[0]);

        out.flush();
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

class Node {

    static long siang;
    static long malam;
//    static String objekSiang;
//    static String objekMalam;

    public Node(long s, long m) {
        this.siang = s;
        this.malam = m;
//        this.objekSiang = "Siang " + String.valueOf(s);
//        this.objekMalam = "Malam " + String.valueOf(m);
    }

    public long getMax() {
        if (siang > malam) {
            return siang;
        }
        return malam;
    }

    @Override
    public String toString() {
        return "Siang : " + String.valueOf(siang) + "Malam : " + String.valueOf(malam)+ " ";
    }

    //    @Override
//    public int compareTo(Node o) {
//        return this.getMax() > o.getMax() ? 1 : -1;
//    }
}
