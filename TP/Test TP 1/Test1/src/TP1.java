//import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.*;
//import static java.lang.Math.min;
//import static java.lang.Math.max;
//
//
//public class TP1 {
//    private static InputReader in;
//    private static PrintWriter out;
//    private static Map<String, String> PersonAndSpec = new HashMap<>(); // key = orang, value = spesialisasi
//    private static Deque<String> Ranking = new ArrayDeque<String>();
//    private static Map<String, Integer> OrangDitunjuk = new HashMap<>(); // berapa kali ditunjuk
//    private static Map<String, Integer> PeringkatOrang = new HashMap<>(); // orang dengan peringkatnya
//
////    private static ArrayList<String> Event = new ArrayList<String>();
////    private static int PointerDeque; // pemisah per batch
//
//    public static void main(String[] args) throws IOException {
//
//        InputStream inputStream = System.in;
//        in = new InputReader(inputStream);
//        OutputStream outputStream = System.out;
//        out = new PrintWriter(outputStream);
//
//        // work here
//        int B = in.nextInt(); // B for batch
//        // tiap batch independet
//        for (int i=0; i<B; i++) {
//            int P = in.nextInt(); // P for People (jumlah orang per batch)
//            for (int j=0; j<P; j++) {
//                String p1 = in.next(); // p1 = orang
//                String p2 = in.next(); // p2 = spesialisasi
//                PersonAndSpec.put(p1,p2); // p1 = orang, p2 = spesialisasi (B/S)
//                Ranking.add(p1);
//                OrangDitunjuk.put(p1,0); // insialisasi map penunjukan oleh siesta
//            }
//
//            int D = in.nextInt(); // D for day
//            for (int k=0; k<D; k++) {
//                int U = in.nextInt(); // U for update ranking
//                for(int l=0; l<U; l++) {
//                    String u1 = in.next(); // u1 = update nama
//                    String u2 = in.next(); // u2 = update ranking (0/1)
//                    UpdateRanking(u1,u2);
//                }
//                for (String rank : Ranking) {
//                    out.print(rank + " ");
//                }
//                out.println();
//            }
//
//            // hashmap setelah update ranking, peringkat terbesar = yg paling kecil angkanya (peringkat 1 > peringkat 2)
//            int indexRank = 1; // mulai dari peringkat 1
//            for(String deq : Ranking) {
//                PeringkatOrang.put(deq,indexRank++);
//            }
//
//            String event1 = in.next();
//
//            if (event1.equals("PANUTAN")) {
//                int event2 = in.nextInt();
//                int[] panutan = Panutan(event2);
//                out.println(panutan[0] + " " + panutan[1]);
//            } else if (event1.equals("KOMPETITIF")) {
//                out.println(Kompetitif());
//            } else if (event1.equals("EVALUASI")) {
//
//            } else if (event1.equals("DUO")) {
//
//            } else { // deploy :(
//
//            }
//
//            // reset tiap ganti batch
//            Ranking.clear();
//            PersonAndSpec.clear();
//            OrangDitunjuk.clear();
//            PeringkatOrang.clear();
//        }
//
//        // end
//        out.flush();
//    }
//
//    static private void UpdateRanking(String nama, String update) {
//        if(update.equals("0")) {
//            Ranking.remove(nama);
//            Ranking.addFirst(nama);
//        } else {
//            Ranking.remove(nama);
//            Ranking.add(nama);
//        }
//        int temp = OrangDitunjuk.get(nama) + 1; // tiap update ditunjuk sekali (+1)
//        OrangDitunjuk.put(nama,temp);
//    }
//
//    static private int[] Panutan(int rank) {
//        int Bakso = 0;
//        int Siomay = 0;
//        int index = 0;
//
//        for (String str : Ranking) {
//            if (PersonAndSpec.get(str).equals("B")) {
//                Bakso++;
//            } else {
//                Siomay++;
//            }
//            index++;
//            if (index==rank) {
//                break;
//            }
//        }
//
//        int[] res = new int[] {Bakso,Siomay};
//
//        return res;
//    }
//
//    static private String Kompetitif() {
//
//        Integer[] values = OrangDitunjuk.values().toArray(new Integer[0]);
//        int temp = values[0];
//        String res = "";
//
//        // nyari terbanyak yg ditunjuk (udah ketemu)
//        for(Integer num : values) {
//            if (num > temp) {
//                temp = num;
//            }
//        }
//
//        int rank = 0;
//        // cocokin dan cari peringkat tertinggi
//        for (String key : OrangDitunjuk.keySet()) {
//            if(OrangDitunjuk.get(key)==temp && PeringkatOrang.get(key)>rank) {
//                res = key;
//            }
//        }
//
//        return res + " " + temp;
//    }
//
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
///* Referensi :
//- https://stackoverflow.com/questions/10656471/performance-differences-between-arraylist-and-linkedlist
//- https://www.geeksforgeeks.org/
//*/
