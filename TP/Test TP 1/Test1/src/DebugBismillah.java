import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


public class DebugBismillah {

    private static InputReader in;
    private static PrintWriter out;
    // static variable
    private static Deque<String> dequeUtama;
    private static Deque<String> dequeKedua;
    private static Map<String, Boolean> statusPrinted;
    private static Map<String, Integer> counterLast;
    private static Map<String, Murid> kumpulanMurid;
    private static Map<String, String> jenisAdd;
    private static int maxSementara;
    private static ArrayList<String> hasilEval;
    private static Map<Integer,String[]> duoMap;
    private static ArrayList<Murid> rankingMurid;
    private static Map<String, String> spesialisasiMurid;   // <Index, [Bakso,Siomay]>
    private static ArrayList<String> tidakDapat;
    private static int[][] arrNilai;
    private static boolean[][] arrStatus;

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // code here
        int B = in.nextInt(); // Batch
        for (int i=0; i<B; i++) {
            // initialization
            dequeUtama = new ArrayDeque<>();
            dequeKedua = new ArrayDeque<>();
            statusPrinted = new HashMap<>();
            counterLast = new HashMap<>();
            kumpulanMurid = new HashMap<>();
            jenisAdd = new HashMap<>();
            hasilEval = new ArrayList<>();
            duoMap = new HashMap<>();
            rankingMurid = new ArrayList<>();
            spesialisasiMurid = new HashMap<>();
            tidakDapat = new ArrayList<>();
            maxSementara = 0;

            int P = in.nextInt(); // People
            for (int j=0; j<P; j++) {
                String nama = in.next();
                String spesialisasi = in.next();
                Murid murid = new Murid(nama,spesialisasi);
                kumpulanMurid.put(nama,murid);
                dequeUtama.add(nama);
                statusPrinted.put(nama,false);
                counterLast.put(nama,0);
                jenisAdd.put(nama,"first");
                spesialisasiMurid.put(nama,spesialisasi);
                murid.setCurrentRank(j+1);
            }

            int D = in.nextInt(); // Day
            for (int d=0; d<D; d++) {
                int U = in.nextInt(); // update ranking per hari
                for (int u=0; u<U; u++) {
                    String nama = in.next();
                    String jenis = in.next();
                    UpdateRanking(nama,jenis);
                }
                // remove & sort deque
                int constant = dequeUtama.size();
                for (int k=0; k<constant; k++) {
                    String nama = dequeUtama.pollFirst();
                    Murid murid = kumpulanMurid.get(nama);
                    int counter = counterLast.get(nama);
                    boolean status = statusPrinted.get(nama);

                    // cek addFirst atau addLast
                    if (jenisAdd.get(nama).equals("first")) { // first = liat true atau false
                        if (!status) {                          // false = blm diambil
                            dequeKedua.add(nama);
                            statusPrinted.put(nama,true);
                        }
                    } else {
                        if (counter==0) {
                            dequeKedua.add(nama);
                            counterLast.put(nama,0);
                        } else {
                            counter--;
                            counterLast.put(nama,counter);
                        }
                    }
                }
                // print yg udah disort berdasar ranking + check prev & now ranking, masukin ke map ranking
                int rank = 1;
                for (String nama : dequeKedua) {
                    out.print(nama + " ");
                    Murid murid = kumpulanMurid.get(nama);
                    rankingMurid.add(murid);
                    murid.setPrevRank(murid.getCurrentRank()); // prev = now sebelumnya
                    murid.setCurrentRank(rank++);
                    if (murid.getCurrentRank() < murid.getPrevRank()) { // ketika rank naik -> terevaluasi = true
                        murid.setTerevaluasi(true);
                    }
                }
                out.println();

                // reset deque akhir day & reset semua per hari
                dequeUtama = dequeKedua;
                dequeKedua = new ArrayDeque<>();
                // reset status
                for (String nama : statusPrinted.keySet()) {
                    statusPrinted.put(nama,false);
                }

                // reset counter
                for (String nama : counterLast.keySet()) {
                    counterLast.put(nama,0);
                }
            }

            String event = in.next();

            if (event.equals("PANUTAN")) {
                int event2 = in.nextInt();
                int[] panutan = Panutan(event2);
                out.println(panutan[0] + " " + panutan[1]);
            } else if (event.equals("KOMPETITIF")) {
                out.println(Kompetitif());
            } else if (event.equals("EVALUASI")) {
                Evaluasi();
                if (hasilEval.size() > 0) {
                    for (String str : hasilEval) {
                        out.print(str + " ");
                    }
                    out.println();
                } else {
                    out.println("TIDAK ADA");
                }
            } else if (event.equals("DUO")) {
                Duo();
                for (Map.Entry<Integer, String[]> duo : duoMap.entrySet()) {
                    if (duo.getValue()[0].equals("kosong") || duo.getValue()[1].equals("kosong")) { // kosong -> break
                        break;
                    }
                    out.println(duo.getValue()[0] + " " + duo.getValue()[1]);
                }
                if (tidakDapat.size() > 0) {
                    out.print("TIDAK DAPAT: ");
                    for (String str : tidakDapat) {
                        out.print(str + " ");
                    }
                    out.println();
                }

            } else { // deploy :(
                int kelompok = in.nextInt();
                arrNilai = new int[kelompok][rankingMurid.size()];
                arrStatus = new boolean[kelompok][rankingMurid.size()];
                // insialisasi awal (krn min 2 org dlm kelompok) utk memoization
                arrNilai[kelompok-1][0] = 0;
                arrStatus[kelompok-1][0] = true;

                String akhir = rankingMurid.get(1).getSpesialisasi();

                out.println(Deploy(0,kelompok,akhir));
            }
            // end each batch
        }

        // end
        out.flush();
    }

    static private void UpdateRanking(String nama, String jenis) {
        Murid murid = kumpulanMurid.get(nama);
        int counter = counterLast.get(nama);

        if (jenis.equals("0")) {        // pakai status
            jenisAdd.put(nama,"first");
            dequeUtama.addFirst(nama);
        } else {                        // pakai counter
            jenisAdd.put(nama,"last");
            dequeUtama.addLast(nama);
        }
        counter++;
        counterLast.put(nama,counter);
        murid.setBanyakDitunjuk(); // jika ditunjuk maka nambah sekali
        // mencari banyak ditunjuk terbanyak utk kompetitif
        if (murid.getBanyakDitunjuk() > maxSementara) {
            maxSementara = murid.getBanyakDitunjuk();
        }
    }

    static private int[] Panutan(int rank) { // kayaknya msh ada salah
        int Bakso = 0;
        int Siomay = 0;
        int index = 0;

        for (String nama : dequeUtama) {
            if (kumpulanMurid.get(nama).getSpesialisasi().equals("B")) {
                Bakso++;
            } else {
                Siomay++;
            }
            index++;
            if (index >= rank) {
                break;
            }
        }

        int[] res = new int[] {Bakso,Siomay};

        return res;
    }

    static private String Kompetitif() { // O(1)

        for (String nama : dequeUtama) {
            Murid murid = kumpulanMurid.get(nama);
            if (murid.getBanyakDitunjuk()==maxSementara) {
                return murid.getKodeMurid() + " " + maxSementara;
            }
        }
        return "";
    }

    static private void Evaluasi() {
        for (String nama : dequeUtama) {
            Murid murid = kumpulanMurid.get(nama);
            if (!murid.getTerevaluasi()) {
                hasilEval.add(murid.getKodeMurid());
            }
        }
    }

    static private void Duo() {
        int baksoIndex = 0;
        int siomayIndex = 0;
        int duoIndex = 0;
        boolean newElement = true;

        for (String nama : dequeUtama) {
            String[] pair = {"kosong","kosong"};
            if (newElement) {
                duoMap.put(duoIndex,pair);
            }
            if (spesialisasiMurid.get(nama).equals("B")) {
                duoMap.get(baksoIndex)[0] = nama;
                baksoIndex++;
            } else {
                duoMap.get(siomayIndex)[1] = nama;
                siomayIndex++;
            }
            newElement = false;
            // reset jika index salah satu lebih besar
            if (baksoIndex > duoIndex || siomayIndex > duoIndex) {
                duoIndex++;
                newElement = true;
            }
        }

        // masukin elemen single ke arraylist tidakAda
        for (Map.Entry<Integer, String[]> duo : duoMap.entrySet()) {
            if (duo.getValue()[0].equals("kosong") && duo.getValue()[1].equals("kosong")) {
                continue;
            }
            if (duo.getValue()[0].equals("kosong")) {
                tidakDapat.add(duo.getValue()[1]);
            } else if (duo.getValue()[1].equals("kosong")) {
                tidakDapat.add(duo.getValue()[0]);
            }
        }
    }

    static private int Deploy(int index, int k, String akhir) {

        String awal = rankingMurid.get(index).getSpesialisasi();
        int total = 0;
        // base case
        if (awal.equals(akhir)) {       // awal == akhir
            return 1;
        }

        if (!awal.equals(akhir)) {      // awal != akhir
            return 0;
        }

        if (k==0 && index != (arrNilai[0].length-1)) {     // k==0 tp bukan akhir barisan
            return 0;
        }

        if (k==0 && index == (arrNilai[0].length-1)) {     // k==0 dan akhir barisan
            return 1;
        }

        // cek memoization (sudah dikunjungi)
        if (arrStatus[index][k-1]) {
            total = 0;
        }

        // recursive case
        for (int z=1; z < rankingMurid.size()-1; z++) {
            String akhirBaru = rankingMurid.get(z+1).getSpesialisasi();
            total = total%1000000007 + Deploy(z,k-1,akhirBaru)%1000000007;
        }

        arrNilai[k][index] = total;
        arrStatus[k][index] = true;

        return total%1000000007;
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

class Murid {
    private String kodeMurid;
    private String spesialisasi;
    private boolean terevaluasi;
    private int banyakDitunjuk;
    private int prevRank;
    private int currentRank;

    public Murid(String kodeMurid, String spesialisasi) {
        this.kodeMurid = kodeMurid;
        this.spesialisasi = spesialisasi;
        this.terevaluasi = false;                       // nanti yg true -> rankingnya naik
        this.banyakDitunjuk = 0;
        this.prevRank = 0;
        this.currentRank = 0;
    }

    // getter
    public int getBanyakDitunjuk() {
        return banyakDitunjuk;
    }
    public String getSpesialisasi() {
        return spesialisasi;
    }
    public String getKodeMurid() {
        return kodeMurid;
    }
    public boolean getTerevaluasi() {
        return terevaluasi;
    }
    public int getPrevRank() {
        return prevRank;
    }
    public int getCurrentRank() {
        return currentRank;
    }

    // setter
    public void setBanyakDitunjuk() {
        this.banyakDitunjuk++;
    }
    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }
    public void setKodeMurid(String kodeMurid) {
        this.kodeMurid = kodeMurid;
    }
    public void setTerevaluasi(boolean terevaluasi) {
        this.terevaluasi = terevaluasi;
    }
    public void setCurrentRank(int currentRank) {
        this.currentRank = currentRank;
    }
    public void setPrevRank(int prevRank) {
        this.prevRank = prevRank;
    }
}

/* Referensi :
- https://stackoverflow.com/questions/10656471/performance-differences-between-arraylist-and-linkedlist
- https://www.geeksforgeeks.org/
- https://www.w3schools.com/
- https://www.bigocheatsheet.com/
*/