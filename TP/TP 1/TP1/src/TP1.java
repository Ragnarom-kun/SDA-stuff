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
//    private static Map<String, Boolean> StatusPrint;
//    private static Map<String, Murid> KumpulanMurid;
//    private static ArrayList<String> RankingMurid;
//    private static Deque<Murid> dequeUtama; // tempat duplikasi data
//    private static Deque<Murid> dequeKedua; // deque kedua
//    private static Map<String, Integer> counterAddLast;     // counter untuk addLast deque (nunjuk")
//    private static int maxSementara;
//    private static ArrayList<String> HasilEval; // hasil eval untuk diprint nantinya
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
//
//        for (int i=0; i<B; i++) { // tiap batch independet (ganti reference ADT tiap ganti batch)
//            // inisialisasi ADT & reset
//            dequeUtama = new ArrayDeque<>();
//            dequeKedua = new ArrayDeque<>();
//            StatusPrint = new HashMap<>();
//            KumpulanMurid = new HashMap<>();
//            RankingMurid = new ArrayList<>();
//            maxSementara = 0;
//            counterAddLast = new HashMap<>();
//            HasilEval = new ArrayList<>();
//
//            int P = in.nextInt(); // P for People (jumlah orang per batch)
//            for (int j=0; j<P; j++) {
//                String p1 = in.next(); // p1 = orang
//                String p2 = in.next(); // p2 = spesialisasi (B/S)
//                Murid murid = new Murid(p1,p2);  // buat objek murid
//                // Orang"awal
//                murid.setCurrentRank(j+1);
//                dequeUtama.add(murid);  // sebagai tempat duplikasi data
//                KumpulanMurid.put(p1,murid);
//                StatusPrint.put(p1,false);
//                counterAddLast.put(p1,0);
//            }
//
//            int D = in.nextInt(); // D for day
//            for (int k=0; k<D; k++) {
//                int U = in.nextInt(); // U for update ranking
//                for(int l=0; l<U; l++) { // siesta nunjuk" (update ranking)
//                    String u1 = in.next(); // u1 = update nama
//                    String u2 = in.next(); // u2 = update ranking (0/1)
//                    UpdateRanking(u1,u2);
//                }
//
//                // print ranking terurut from head to tail (setelah selesai update per hari)
//                int constant = dequeUtama.size();
//                for (int m=0; m<constant; m++) {
//                    Murid murid = dequeUtama.pollFirst();
//                    int counter = counterAddLast.get(murid.getKodeMurid());
//                    String nama = murid.getKodeMurid();
////                    if (counter==0) {
////                        if (!StatusPrint.get(murid.getKodeMurid())) {
////                            dequeKedua.add(murid);
////                            StatusPrint.put(murid.getKodeMurid(), true);
////                        }
////                    } else {
////                        counter--;
////                        counterAddLast.put(murid.getKodeMurid(), counter);
////                    }
//
//                    if (murid.getStatusPrint().equals("first") && !StatusPrint.get(nama)) {
//                        StatusPrint.put(nama,true);
//                        dequeKedua.add(murid);
//                    } else {
//
//                    }
//                }
//                // print ; update prev & current ranking object untuk eval; eval
//                int rank = 1;
//                for (Murid murid : dequeKedua) {
//                    out.print(murid.getKodeMurid() + " ");
//                    murid.setPrevRank(murid.getCurrentRank()); // prev = current (sebelum diupdate)
//                    murid.setCurrentRank(rank++);               // current di update
//                    if (murid.getCurrentRank() < murid.getPrevRank()) { // ketika rank naik -> terevaluasi = true
//                        murid.setTerevaluasi(true);
//                    }
//                }
//                // reset status ke false
//                for (String key : StatusPrint.keySet()) {
//                    StatusPrint.put(key,false);
//                }
//
//                dequeUtama = dequeKedua; // reset dgn hasil tersort
//                dequeKedua = new ArrayDeque<>(); // reset untuk next menampung urutan
//
//                // reset counter ke 0
//                for (String str : counterAddLast.keySet()) {
//                    counterAddLast.put(str,0);
//                }
//                out.println();
//            }
//
//            // simpan ke arraylist yg sudah terurut & update map kumpulanMurid
//            for (Murid murid : dequeUtama) {
//                RankingMurid.add(murid.getKodeMurid());
//                KumpulanMurid.put(murid.getKodeMurid(), murid);
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
//                Evaluasi();
//                for (String str : HasilEval) {
//                    out.print(str + " ");
//                }
//                out.println();
//            } else if (event1.equals("DUO")) {
//
//            } else { // deploy :(
//
//            }
//        }
//
//        // end
//        out.flush();
//    }
//    // berubah
//    static private void UpdateRanking(String nama, String update) { // siesta nunjuk" -> O(1)
//        Murid murid = KumpulanMurid.get(nama);
//        int counter = counterAddLast.get(nama);
//        int temp = 0;
//        // set false sebagai penanda belum diambil
//        StatusPrint.put(nama,false);
//
//        if (update.equals("0")) {
////            if (counterAddLast.get(nama) > 0) {
////                temp = counterAddLast.get(nama) + 1;
////                murid.setCounterAdd(temp);
////                counterAddLast.put(nama, 0); // biar langsung dicetak
////            }
//            murid.setStatusPrint("first");
//            dequeUtama.addFirst(murid);
//        } else {
////            if (counterAddLast.get(nama)==0) {
////                counter = murid.getCounterAdd();
////                murid.setCounterAdd(0);
////            }
//            murid.setStatusPrint("last");
//            dequeUtama.add(murid);
//        }
//        counter++;
//        counterAddLast.put(nama,counter);
//        murid.setBanyakDitunjuk(); // jika ditunjuk maka nambah sekali
//        // mencari banyak ditunjuk terbanyak utk kompetitif
//        if (murid.getBanyakDitunjuk() > maxSementara) {
//            maxSementara = murid.getBanyakDitunjuk();
//        }
//        KumpulanMurid.put(nama,murid);
//    }
//
//    static private int[] Panutan(int rank) { // kayaknya msh ada salah
//        int Bakso = 0;
//        int Siomay = 0;
//
//        for (int i=0; i<rank; i++) {
//            if (KumpulanMurid.get(RankingMurid.get(i)).getSpesialisasi().equals("B")) {
//                Bakso++;
//            } else {
//                Siomay++;
//            }
//        }
//
//        int[] res = new int[] {Bakso,Siomay};
//
//        return res;
//    }
//
//    static private String Kompetitif() { // O(1)
//
//        String kode = "";
//
//        for (int i=0; i<RankingMurid.size(); i++) {
//            Murid murid = KumpulanMurid.get(RankingMurid.get(i));
//            if (murid.getBanyakDitunjuk()==maxSementara) {
//                kode = murid.getKodeMurid();
//                break;
//            }
//        }
//
//        return kode + " " + maxSementara;
//    }
//
//    static void Evaluasi() { // cek terevaluasi (T/F); true = rankingnya pernah naik
//        for (String nama : RankingMurid) {
//            Murid murid = KumpulanMurid.get(nama);
//            if (!murid.getTerevaluasi()) {
//                HasilEval.add(murid.getKodeMurid());
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
//class Murid {
//    private String kodeMurid;
//    private String spesialisasi;
//    private boolean terevaluasi;
//    private int banyakDitunjuk;
////    private int counterAdd;
//    private int prevRank;
//    private int currentRank;
//    private String statusPrint;
//
//    public Murid(String kodeMurid, String spesialisasi) {
//        this.kodeMurid = kodeMurid;
//        this.spesialisasi = spesialisasi;
//        this.terevaluasi = false;                       // nanti yg true -> rankingnya naik
//        this.banyakDitunjuk = 0;
////        this.counterAdd = 0;
//        this.prevRank = 0;
//        this.currentRank = 0;
//        this.statusPrint = "first";
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
//    public boolean getTerevaluasi() {
//        return terevaluasi;
//    }
////    public int getCounterAdd() {
////        return counterAdd;
////    }
//    public int getPrevRank() {
//        return prevRank;
//    }
//    public int getCurrentRank() {
//        return currentRank;
//    }
//
//    public String getStatusPrint() {
//        return statusPrint;
//    }
//
//    // setter
//    public void setBanyakDitunjuk() {
//        this.banyakDitunjuk++;
//    }
//    public void setSpesialisasi(String spesialisasi) {
//        this.spesialisasi = spesialisasi;
//    }
//    public void setKodeMurid(String kodeMurid) {
//        this.kodeMurid = kodeMurid;
//    }
//    public void setTerevaluasi(boolean terevaluasi) {
//        this.terevaluasi = terevaluasi;
//    }
////    public void setCounterAdd(int counterAdd) {
////        this.counterAdd = counterAdd;
////    }
//    public void setCurrentRank(int currentRank) {
//        this.currentRank = currentRank;
//    }
//    public void setPrevRank(int prevRank) {
//        this.prevRank = prevRank;
//    }
//
//    public void setStatusPrint(String statusPrint) {
//        this.statusPrint = statusPrint;
//    }
//}
//
///* Referensi :
//- https://stackoverflow.com/questions/10656471/performance-differences-between-arraylist-and-linkedlist
//- https://www.geeksforgeeks.org/
//- https://www.w3schools.com/
//- https://www.bigocheatsheet.com/
//*/
