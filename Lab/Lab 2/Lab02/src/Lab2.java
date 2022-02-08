import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


public class Lab2 {

    private static InputReader in;
    private static PrintWriter out;
    private static Deque<String> namaGang = new ArrayDeque<String>();
    private static Deque<Integer> jumlahAnggota = new ArrayDeque<Integer>();
    private static HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
    private static int sum;

    /*
    sumber inspirasi :
    https://www.w3schools.com/java/java_hashmap.asp
    https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#pollFirst--
    https://www.geeksforgeeks.org/deque-interface-java-example/
    https://stackoverflow.com/questions/6163166/why-is-arraydeque-better-than-linkedlist/32625029
    https://www.geeksforgeeks.org/hashmap-containskey-method-in-java/
    https://stackoverflow.com/questions/8923251/what-is-the-time-complexity-of-hashmap-containskey-in-java
    */

    static private int handleDatang(String Gi, int Xi) {

        namaGang.addLast(Gi);
        jumlahAnggota.addLast(Xi);
        sum += Xi;

        return sum;
    }

    static private String handleLayani(int Yi) {

        String nama = "";
        int num = 0;

        while (Yi > 0) {
            String last = namaGang.pollFirst();
            int temp = jumlahAnggota.pollFirst();

            if (temp > Yi) {
                num = Yi;
                jumlahAnggota.addFirst(temp - Yi);
                namaGang.addFirst(last);
            } else {
                num = temp;
            }

            Yi -= temp;

            checking(hashMap, last, num);
            sum -= num;
            nama = last;
        }

        return nama;
    }

    static private int handleTotal(String Gi) {
        if (hashMap.containsKey(Gi)) {
            return hashMap.get(Gi);
        }
        return 0;
    }

    static private void checking(HashMap<String, Integer> hashMap1, String str, int num) {
        if (hashMap1.containsKey(str)) {
            hashMap1.put(str,hashMap1.get(str)+num);
        }
        else {
            hashMap1.put(str, num);
        }
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N;

        N = in.nextInt();

        for(int tmp=0;tmp<N;tmp++) {
            String event = in.next();

            if(event.equals("DATANG")) {
                String Gi = in.next();
                int Xi = in.nextInt();

                out.println(handleDatang(Gi, Xi));
            } else if(event.equals("LAYANI")) {
                int Yi = in.nextInt();

                out.println(handleLayani(Yi));
            } else {
                String Gi = in.next();

                out.println(handleTotal(Gi));
            }
        }

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
// Romi Fadhurrohman Nabil (2006535016) SDA D