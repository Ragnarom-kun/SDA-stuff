import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


public class Dummy {
    private static InputReader in;
    private static PrintWriter out;
//    private static Map<String, String> PersonAndSpec = new HashMap<>(); // key = orang, value = spesialisasi
//    private static Deque<String> Ranking = new ArrayDeque<String>();
//    private static Map<String, Integer> OrangDitunjuk = new HashMap<>(); // berapa kali ditunjuk
//    private static Map<String, Integer> PeringkatOrang = new HashMap<>(); // orang dengan peringkatnya
    private static Map<String, Murid[]> RankingMurid = new HashMap<>(); // ranking murid dgn Murid[] = {prev,now,next}
//    private static Map<Integer, Murid> Ranking = new HashMap<>(); // ranking setelah diurutkan
//    private static Murid[] arrRanking;
//    private static Deque<Murid> dequeRanking = new ArrayDeque<>();
    private static int maxSementara = 0;
    private static Murid[] head;
    private static Murid[] tail;

//    private static ArrayList<String> Event = new ArrayList<String>();
//    private static int PointerDeque; // pemisah per batch

    public static void main(String[] args) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // work here
        int B = in.nextInt(); // B for batch
        // tiap batch independet
        for (int i=0; i<B; i++) {
            int P = in.nextInt(); // P for People (jumlah orang per batch)

            Murid prev = null;
            Murid[] next = null;
            for (int j=0; j<P; j++) {
                String p1 = in.next(); // p1 = orang
                String p2 = in.next(); // p2 = spesialisasi (B/S)
                Murid murid = new Murid(p1,p2);  // buat objek murid
                Murid[] node = new Murid[3]; //Murid[] = {prev,now,next}
                node[1] = murid;    // index 1 = now

                if (j == 0) { // bikin variabel head untuk murid pertama
                    head = node;
                }
                else if (j != 0) { // masukin ke hashmap
                    node[0] = prev; // array sekarang -> element prev masukin objek murid sebelumnya yg now
                    next[2] = murid; // array sebelumnya -> di element next masukin element now yg sekarang

                    if (j == 1) {
                        head[2] = murid; // {null, murid_prev, murid_now}
                    } else if (j == P-1) {
                        tail = node; // tail[2] == null (elemen terakhir)
                    }

                    RankingMurid.put(next[1].getKodeMurid(),next);
                }
                RankingMurid.put(murid.getKodeMurid(),node);
                // update prev utk murid now nanti & next untuk murid prevnya
                next = node; // menjadi array
                prev = murid; // untuk masukin prev di next[] saat now
            }

            int D = in.nextInt(); // D for day
            for (int k=0; k<D; k++) {
                int U = in.nextInt(); // U for update ranking
                for(int l=0; l<U; l++) {
                    String u1 = in.next(); // u1 = update nama
                    String u2 = in.next(); // u2 = update ranking (0/1)
                    UpdateRanking(u1,u2);
                }

                // print ranking terurut from head to tail
                Murid[] tempMurid = head;
                for (int z=0; z<RankingMurid.size(); z++) {
                    out.print(tempMurid[1].getKodeMurid() + " ");
                    if (tempMurid[2] != null) {
                        tempMurid = RankingMurid.get(tempMurid[2].getKodeMurid());
                    }
                }
                out.println();
            }

            String event1 = in.next();

            if (event1.equals("PANUTAN")) {
                int event2 = in.nextInt();
                int[] panutan = Panutan(event2);
                out.println(panutan[0] + " " + panutan[1]);
            } else if (event1.equals("KOMPETITIF")) {
                out.println(Kompetitif());
            } else if (event1.equals("EVALUASI")) {

            } else if (event1.equals("DUO")) {

            } else { // deploy :(

            }

            // reset tiap ganti batch
            RankingMurid.clear();
            maxSementara = 0;
        }

        // end
        out.flush();
    }

    static private void UpdateRanking(String nama, String update) { // {prev,now,next}
        Murid[] temp = RankingMurid.get(nama);
        Murid next = temp[2];
        Murid prev = temp[0];
        // remove element
        RankingMurid.remove(nama);

        if (prev != null && next != null) {
            // mindahin bagian prev
            Murid[] prevNode = RankingMurid.get(prev.getKodeMurid());
            prevNode[2] = next;
            RankingMurid.put(prev.getKodeMurid(),prevNode);

            // mindahin bagian next
            Murid[] nextNode = RankingMurid.get(next.getKodeMurid());
            nextNode[0] = prev;
            RankingMurid.put(next.getKodeMurid(),nextNode);
        } else if (update.equals("0") && next == null && prev != null) { // untuk update "0" tapi next == null (alias elemen terakhir dijadikan peringkat 1)
            Murid[] prevNode = RankingMurid.get(prev.getKodeMurid());
            prevNode[2] = null;
            tail = prevNode;
            RankingMurid.put(prev.getKodeMurid(),prevNode);
        } else if (update.equals("1") && prev == null && next != null) {
            Murid[] nextNode = RankingMurid.get(next.getKodeMurid());
            nextNode[0] = null;
            head = nextNode;
            RankingMurid.put(next.getKodeMurid(),nextNode);
        }

        if (update.equals("0") && prev != null) {
            temp[0] = null;
            temp[2] = head[1];
            // head sebelumnya kegeser ke kanan
            head[0] = temp[1];
            RankingMurid.put(head[1].getKodeMurid(), head);

            // update head
            head = temp;    // temp = head baru (pass by value)
            RankingMurid.put(head[1].getKodeMurid(), head); // update temp (head baru) ke map

        } else if (update.equals("1") && next != null) {
            temp[2] = null;
            temp[0] = tail[1];
            //tail sebelumnya kegeser ke kiri
            tail[2] = temp[1];
            RankingMurid.put(tail[1].getKodeMurid(),tail);

            // update tail
            tail = temp;
            RankingMurid.put(tail[1].getKodeMurid(),tail);
        }

        temp[1].setBanyakDitunjuk(); // tiap update ditunjuk sekali (+1)
    }

    static private int[] Panutan(int rank) {
        int Bakso = 0;
        int Siomay = 0;
        Murid[] tempMurid = head;

        for (int i = 0; i<rank; i++) {
            if (tempMurid[1].getSpesialisasi().equals("B")) {
                Bakso++;
            } else {
                Siomay++;
            }
            if (tempMurid[2] != null) {
                tempMurid = RankingMurid.get(tempMurid[2].getKodeMurid());
            }
        }

        int[] res = new int[] {Bakso,Siomay};

        return res;
    }

    static private String Kompetitif() { // O(1)

        String kode = "";
        Murid[] tempMurid = head;

        for (int i=0; i<RankingMurid.size(); i++) {
            if (tempMurid[1].getBanyakDitunjuk() >= maxSementara) {
                maxSementara = tempMurid[1].getBanyakDitunjuk();
                kode = tempMurid[1].getKodeMurid();
            }
            if (tempMurid[2] != null) {
                tempMurid = RankingMurid.get(tempMurid[2].getKodeMurid());
            }
        }

        return kode + " " + maxSementara;
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

//class Murid {
//    private String kodeMurid;
//    private String spesialisasi;
//    private boolean terevaluasi;
//    private int banyakDitunjuk;
//    private int peringkat;
//
//    public Murid(String kodeMurid, String spesialisasi) {
//        this.kodeMurid = kodeMurid;
//        this.spesialisasi = spesialisasi;
//        this.terevaluasi = false;
//        this.banyakDitunjuk = 0;
//        this.peringkat = 0;
//    }
//
//    // getter
//    public int getBanyakDitunjuk() {
//        return banyakDitunjuk;
//    }
//    public String getSpesialisasi() {
//        return spesialisasi;
//    }
//    public String getKodeMurid() {
//        return kodeMurid;
//    }
//     public boolean getTerevaluasi() {
//        return terevaluasi;
//     }
//    public int getPeringkat() {
//        return peringkat;
//    }
//
//    // setter
//    public void setBanyakDitunjuk() {
//        this.banyakDitunjuk++;
//    }
//
//    public void setPeringkat(int peringkat) {
//        this.peringkat = peringkat;
//    }
//
//    public void setSpesialisasi(String spesialisasi) {
//        this.spesialisasi = spesialisasi;
//    }
//    public void setKodeMurid(String kodeMurid) {
//        this.kodeMurid = kodeMurid;
//    }
//    public void setTerevaluasi(boolean terevaluasi) {
//        this.terevaluasi = terevaluasi;
//    }
//
//    @Override
//    public String toString() {
////        return "Murid{" +
////                "kodeMurid='" + kodeMurid + '\'' +
////                '}';
//        return kodeMurid + " ";
//    }
//}

/* Referensi :
- https://stackoverflow.com/questions/10656471/performance-differences-between-arraylist-and-linkedlist
- https://www.geeksforgeeks.org/
- https://www.w3schools.com/
- https://www.bigocheatsheet.com/
*/
