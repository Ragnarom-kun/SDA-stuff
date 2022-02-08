import java.io.*;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class TP1byFikri {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    private static Queue<String> s = new LinkedList<>(); //reset
    private static Queue<String> b = new LinkedList<>(); //reset

    private static Deque<String> normal = new LinkedList<>(); //reset
    private static Deque<String> bottom = new LinkedList<>();
    private static Deque<String> filter = new LinkedList<>();

    private static List<Integer> panutanB = new LinkedList<>(); //reset
    private static List<Integer> panutanS = new LinkedList<>(); //reset

    private static Map<String,Boolean> pernahNaik = new HashMap<>();
    private static Map<String,Integer> indeks = new HashMap<>();
    private static Map<String,Integer> tunjuk = new HashMap<>();
    private static Map<String,Integer> posisi = new HashMap<>();
    private static Map<String,Integer> duplicate = new HashMap<>();
    private static Map<String,String> tipe = new HashMap<>();

    private static String[] urutanBS = new String[1000];
    private static int[][] hasilDeploy = new int[1005][505];
    private static boolean[][] cekDeploy = new boolean[1005][505];

    public static void main(String[] args) {
        int C = in.nextInt();
        
        while (C!=0){
            batch();
            C--;
            hasilDeploy = new int[1005][505];
            cekDeploy = new boolean[1005][505];
            s = new LinkedList<>();
            b = new LinkedList<>();
            panutanB = new LinkedList<>();
            panutanS = new LinkedList<>();
            normal = new LinkedList<>();
        }

        out.close();
    }

    private static int deploy(int awal, int maks, int K, int N) {

        int total = 0;
        int mod = 1000000007;

        if (cekDeploy[awal][K]) {
            return hasilDeploy[awal][K];
        }

        if (K == 1) {
            if (urutanBS[awal].equals(urutanBS[awal+maks-1])) {
                return 1;
            }
        } else {
            for (int i=2; i<=maks; i++) {
                if (urutanBS[awal].equals(urutanBS[awal+i-1])) {
                    total = ((total % mod) + (deploy(awal+i, (N-i)-(K-2)*2, K-1, N-i) % mod)) % mod;
                }
            }
        }

        cekDeploy[awal][K] = true;
        hasilDeploy[awal][K] = total;

        return total;
    }

    private static void batch() {

        int N = in.nextInt();

        for (int i=0; i<N; i++) {
        	String kode = in.next();
        	String spesialiasi = in.next();
        	normal.addLast(kode);
            tipe.put(kode, spesialiasi);
            indeks.put(kode, i);
            tunjuk.put(kode, 0);
            posisi.put(kode, 1);
            duplicate.put(kode, 1);
            pernahNaik.put(kode, false);
        }

        int E = in.nextInt();
        int tunjukMaks = 0;
        String kodeTunjukMaks = "";

        for (int i = 1; i <= E; i++) {
            int F = 0;
        	int P = in.nextInt();

        	for (int j = 1; j <= P; j++) {
        		String kode = in.next();
        		int status = in.nextInt();

                duplicate.put(kode, duplicate.get(kode) + 1);
                tunjuk.put(kode, tunjuk.get(kode) + 1);
                tunjukMaks = Math.max(tunjukMaks, tunjuk.get(kode));

                if (status == 1) {
                    bottom.addLast(kode);
                    posisi.put(kode, (-1) * duplicate.get(kode));
                } else {
                    normal.addFirst(kode);
                    posisi.put(kode, 1);
                }
        	}

            while(!normal.isEmpty()) {
                String kode = normal.removeFirst();
                duplicate.put(kode, duplicate.get(kode) - 1);
                if (posisi.get(kode) == 1) {
                    posisi.put(kode, 0);
                    filter.addLast(kode);
                    out.print(kode + " ");
                } else if (posisi.get(kode) < 0) {
                    posisi.put(kode, (-1) * duplicate.get(kode));
                }
            }

            while(!bottom.isEmpty()) {
                String kode = bottom.removeFirst();
                int test = posisi.get(kode);
                duplicate.put(kode, duplicate.get(kode) - 1);
                if (test <= -1) {
                    if (duplicate.get(kode) == 0) {
                        filter.addLast(kode);
                        out.print(kode + " ");
                        posisi.put(kode, 0);
                    } else {
                        posisi.put(kode, (-1) * duplicate.get(kode));
                    }
                }
            }

            while(!filter.isEmpty()) {
                String kode = filter.removeFirst();
                posisi.put(kode, 1);
                duplicate.put(kode, 1);
                normal.addLast(kode);

                if (i == E) {
                    String tipeBS = tipe.get(kode);
                    if (tipeBS.equals("B")) {
                        urutanBS[F] = "B";
                        b.add(kode);
                        if (F == 0) {
                            panutanB.add(1);
                            panutanS.add(0);
                        } else {
                            panutanB.add(panutanB.get(F - 1) + 1);
                            panutanS.add(panutanS.get(F - 1));
                        }
                    } else {
                        urutanBS[F] = "S";
                        s.add(kode);
                        if (F == 0) {
                            panutanS.add(1);
                            panutanB.add(0);
                        } else {
                            panutanS.add(panutanS.get(F - 1) + 1);
                            panutanB.add(panutanB.get(F - 1));
                        }
                    }
                    F++;
                }

                if (tunjukMaks <= tunjuk.get(kode)) {
                    kodeTunjukMaks = kode;
                    tunjukMaks++;
                }

                if (indeks.get(kode) > N - 1 - filter.size()) {
                    pernahNaik.put(kode, true);
                }

                indeks.put(kode, N - 1 - filter.size());
            }

            out.println("");
        }

        String evaluasiAkhir = in.next();

        if (evaluasiAkhir.equals("PANUTAN")) {
            int q = in.nextInt();
            out.println(panutanB.get(q-1) + " " + panutanS.get(q-1));
        } else if (evaluasiAkhir.equals("KOMPETITIF")) {
            out.println(kodeTunjukMaks + " " + (tunjukMaks-1));
        } else if (evaluasiAkhir.equals("EVALUASI")) {
            boolean cek = true;
            while (!normal.isEmpty()) {
                String test = normal.removeFirst();
                if (!pernahNaik.get(test)) {
                    cek = false;
                    out.print(test + " ");
                }
            }
            if (cek) out.print("TIDAK ADA");
            out.println("");
        } else if (evaluasiAkhir.equals("DUO")) {
            while(!s.isEmpty() && !b.isEmpty()) {
                out.println(b.poll() + " " + s.poll());
            }
            
            if (!b.isEmpty()) {
                out.print("TIDAK DAPAT:");
                while(!b.isEmpty()) {
                    out.print(" " + b.poll());
                }
                out.println("");
            }

            if (!s.isEmpty()) {
                out.print("TIDAK DAPAT:");
                while(!s.isEmpty()) {
                    out.print(" " + s.poll());
                }
                out.println("");
            }
        } else {
            int K = in.nextInt();
            out.println(deploy(0, N-(K-1)*2, K, N));
        }
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