import java.io.*;
import java.util.*;

public class TP1byAgil {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static Queue<Murid> daftarMurid;
    public static Map<String, Murid> muridMap; // ABC 1/0
    public static Murid[] relativeRank;
    public static boolean[] isBakso; //
    public static int jmlMurid;
    public static int constant = 1_000_000_000 + 7;

    public static void main(String[] args) {
        // Input jumlah batch dan kemudian iterasikan
        int jmlBatch = in.nextInt();

        for (int batch = 0; batch < jmlBatch; batch++) {
            // Buat sebuah hashmap yang menampung nodes setiap murid yang ada di dalam setiap batch
            daftarMurid = new LinkedList<Murid>();
            muridMap = new HashMap<String, Murid>();

            // Input jumlah murid untuk batch ini, kemudian kita masuk ke dalam sebuah loop
            jmlMurid = in.nextInt();

            for (int nMurid = 0; nMurid < jmlMurid; nMurid++) {
                String code = in.next();
                String jenis = in.next();
                Murid input = new Murid(code, jenis);
                daftarMurid.add(input);
                muridMap.put(code, input);

            }
            // Bagian pelatihan
            int jmlHari = in.nextInt();
            isBakso = new boolean[jmlMurid];

            for (int hari = 0; hari < jmlHari; hari++) {
                int jmlTunjuk = in.nextInt();

                // Inisialisasi sebuah array pembantu untuk mengetahui urutan relatif tiap elemen
                relativeRank = new Murid[jmlMurid+jmlTunjuk*2];
                int kiri = jmlTunjuk-1,  kanan = jmlMurid+jmlTunjuk;

                // Pindahkan murid yang ada di dalam queue ke dalam array pembantu
                for (int i = jmlTunjuk; i < jmlTunjuk + jmlMurid; i++) {
                    Murid murid = daftarMurid.poll();
                    murid.currRank = i-jmlTunjuk;
                    murid.relativeRank = i;
                    relativeRank[i] = murid;
                }

                //System.out.println("|-- debug --| " + batch + Arrays.toString(relativeRank)); // debuggg

                for (int tunjuk = 0; tunjuk < jmlTunjuk; tunjuk++) {
                    // Input perintah
                    String code = in.next();
                    String instruction = in.next();

                    // Fetch murid
                    Murid murid = muridMap.get(code);
                    murid.banyakTunjuk++;
                    int position = murid.relativeRank;

                    // Pindahkan sesuai instruksi
                    relativeRank[position] = null;

                    if (instruction.equals("0")) {
                        murid.relativeRank = kiri;
                        relativeRank[kiri--] = murid;
                        kanan++;
                    }
                    else {
                        murid.relativeRank = kanan;
                        relativeRank[kanan++] = murid;
                        kiri--;
                    }
                    //System.out.println("--- debug ---  " + Arrays.toString(relativeRank)); // debuggg
                }
                // Di akhir hari, loop relativeRank untuk mendapatkan rankingnya masing-masing
                traverse();
            }

            // Input instruksi yang akan dilakukan terhadap murid-murid di akhir batch
            String instruction = in.next();

            // Lakukan instruksi yang bersesuaian dengan String instruction
            if (instruction.equals("PANUTAN")) panutan(in.nextInt());
            else if (instruction.equals("KOMPETITIF")) kompetitif();
            else if (instruction.equals("EVALUASI")) evaluasi();
            else if (instruction.equals("DUO")) duo();
            else if (instruction.equals("DEPLOY")) deploy(in.nextInt());

        }// // //End of batch
        out.flush();
    }

    // // // // Method-Method Penting
    private static void traverse() {
        int cnt = 0;
        for (Murid murid : relativeRank) {
            if (murid != null) {
                murid.lastRank = murid.currRank;
                murid.currRank = cnt;
                murid.compareRank();

                isBakso[cnt++] = murid.isBakso;
                daftarMurid.add(murid);

                out.print(murid.code + " ");
            }
        }
        out.println();
    }

    private static void panutan(int q) { // O(q)
        int s = 0, b = 0;
        for (int i = 0; i < q; i++) {
            if (isBakso[i]) b++;
            else s++;
        }
        out.println(b + " " + s);
    }


    private static void kompetitif() { // O(n)
        Iterator<Murid> it = daftarMurid.iterator(); // n
        Murid maxPointed = it.next();

        while (it.hasNext()) {
            Murid murid = it.next();
            if (murid.banyakTunjuk > maxPointed.banyakTunjuk) {
                maxPointed = murid;
            }
        }
        out.println(maxPointed.code + " " + maxPointed.banyakTunjuk);
    }


    public static void evaluasi() { // O(n)
        boolean isEmpty = true;
        for (Murid murid : daftarMurid) {
            if (murid.isTurun) {
                out.print(murid.code + " ");
                isEmpty = false;
            }
        } if (isEmpty) out.print("TIDAK ADA");
        out.println();
    }


    public static void duo() { // O(n)
        Queue<Murid> temp = new LinkedList<Murid>();
        for (Murid murid : daftarMurid) {
            if (!temp.isEmpty() && (temp.peek().isBakso != murid.isBakso)){
                Murid murid1 = temp.poll();
                if (murid.isBakso) out.println(murid.code + " " + murid1.code);
                else out.println(murid1.code + " " + murid.code);
            }
            else temp.add(murid);
        }
        if (!temp.isEmpty()) {
            out.print("TIDAK DAPAT: ");
            for (Murid murid : temp) {
                out.print(murid.code + " ");
            } out.println();
        }
    }

    public static void deploy(int K) {  // O(Kn)
        // Start analysing isBakso untuk mendapatkan DEPLOY K  arr[K][N]
        int[] currVal = new int[jmlMurid+1], prevVal = new int[jmlMurid+1], temp; // Array pembantu untuk mendapatkan jawabannya
        int b = 0, s = 0, bb = 0, ss = 0; // Pointer pembantu untuk mendapatkan jawaban

        // Isi base case
        boolean jenisAwal = isBakso[0];
        for (int i = 2; i <= jmlMurid; i++) {
            currVal[i] = (isBakso[i-1] == jenisAwal) ? 1 : 0;
        }

        // Isi non base case, k=1
        for (int k = 1; k < K; k++) {
            b = 0; s = 0; bb = 0; ss = 0;

            // Switch currVal dengan prevVal
            temp = currVal;
            currVal = prevVal; // f[k][]
            prevVal = temp; // f[k-1][]

            int j;
            // Leading zeros
            for (j = 0; j < 2*k+2; j++) {
                currVal[j] = 0;
                if (isBakso[j]) { // update pointer
                    bb = b; b = j;
                } else {
                    ss = s; s = j;
                }
            }
            // Post-leading zeros
            for (j=2*k+2; j <= jmlMurid; j++) {
                // temukan nilai f(k,j) = f(k-1, xx) + f(k, xx+1)
                if (isBakso[j-1]) currVal[j] = (prevVal[bb] + currVal[bb+1]) % constant;
                else currVal[j] = (prevVal[ss] + currVal[ss+1]) % constant;

                // Update pointer
                if (j != jmlMurid && isBakso[j]) {
                    bb = b; b = j;
                }
                else {
                    ss = s; s = j;
                }
            }
        }
        // Cetak jawaban
        out.println(currVal[jmlMurid]);
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
}

class Murid {
    public String code; // Kode dari murid
    public boolean isBakso; // Jenis dari murid; kang bakso = true, else = false
    public boolean isTurun; // Apakah nilai murid dari awal sampai akhir Monoton Turun
    public int banyakTunjuk; // Banyak kali murid ditunjuk
    public int lastRank; // Ranking murid pada akhir hari sebelumnya
    public int currRank; // Ranking murid pada akhir hari ini
    public int relativeRank = 0;

    public Murid(String code, String jenis) {
        this.code = code;
        isBakso = jenis.equals("B") ? true : false;
        isTurun = true;
        banyakTunjuk = 0;
        lastRank = -1;
        currRank = 0;
        relativeRank = 0; // indeks murid di array relativerank
    }

    public void compareRank() {
        isTurun = isTurun && (currRank >= lastRank);
    }

    public String toString() {
        return code;
    }
}

