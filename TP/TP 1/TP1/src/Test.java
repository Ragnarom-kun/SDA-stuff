import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class Test {
    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        Deque<String> deq = new ArrayDeque<>();

//        InputStream inputStream = System.in;
//        in = new TP1.InputReader(inputStream);
//        OutputStream outputStream = System.out;
//        out = new PrintWriter(outputStream);
//
//        String test = in.next();
//        String test1 = in.next();
//        out.println(test);
//        out.println(test1);
////        String[] t1 = test.split("\\s+");
//
//
//
//        out.flush();

//        deq.add("A");
//        deq.add("B");
//        deq.addFirst("C");
//        deq.add("D");
//        deq.addLast("E");
//        deq.add("F");
//
//        System.out.println(deq);
//
//
//        deq.remove("B");
//        System.out.println(deq);
//        deq.addLast("B");
//        System.out.println(deq);
//
//        deq.remove("D");
//
//
//        System.out.println(deq);
//        deq.addLast("D");
//        System.out.println(deq);

//        Map<String,Integer> map = new HashMap<>();
//        map.put("satu",1);
//        System.out.println(map.get("satu"));
//        System.out.println(map);
//        map.put("satu",99);
//        System.out.println(map.get("satu"));
//        System.out.println(map);
        Map<String, Integer> hashMap = new HashMap<>();

        hashMap.put("RED", 5);
        hashMap.put("BLUE", 9);

        Object[] objectArray = hashMap.entrySet().toArray();


        System.out.println(Arrays.toString(objectArray));
        System.out.println(objectArray[0]);
    }

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

    //                    // DEBUGGING
//                    out.println(RankingMurid.keySet() + ": " + Arrays.toString(RankingMurid.values().toArray()));
//                    Set<Map.Entry<String, Murid[]>> ranks = RankingMurid.entrySet();
//
//                    for (Map.Entry<String, Murid[]> entry : ranks) {
//                        out.print("key: "+ entry.getKey());
//                        out.println(", Value: ");
//                        for (Murid n : entry.getValue()) {
//                            if (n != null) {
//                                out.print(n.getKodeMurid() + "-");
//                            } else {
//                                out.print("KOSONG" + "-");
//                            }
//                        }
//                        out.println();
//                    }
//                    // END OF DEBUGGING
}
