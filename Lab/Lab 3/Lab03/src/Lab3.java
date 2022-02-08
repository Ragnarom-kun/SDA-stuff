import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


class Lab3 {

    private static InputReader in;
    private static PrintWriter out;

    /* sumber :
    Muhammmad Ilham Ghozali, Muhammad Agil Ghifari, Zidan Adidarma Kharisma
    https://youtu.be/39E1MckfA-w
    */
    static private long[] findTotal(ArrayList<Integer> S, ArrayList<Integer> M, ArrayList<Integer> B) {
        long[][][] arr = new long[2][S.size()+1][S.size()+1];
        long[] res = new long[2];
        long msf = 0;
        long mxh = 0;

        // index 0 = siang, index 1 = malam
        for(int i=1; i<arr[0].length; i++) {
            msf = 0;
            mxh = 0;
            for(int j=i; j<arr[0].length; j++) {
                long num1 = (long) S.get(j-1); // siang
                long num2 = (long) M.get(j-1); // malam

                long neighbourNight = arr[1][i-1][j-1];
                long neighbourDay = arr[0][i-1][j-1];

                long pick1 = num1 + neighbourNight; // tambah tetangga silang
                long pick2 = num2 + neighbourDay;

                arr[0][i][j] = pick1;
                arr[1][i][j] = pick2;

                long temp1 = S.get(j-1) + msf;
                long temp2 = M.get(j-1) + msf;

                if (arr[0][i][j] < temp1) {
                    arr[0][i][j] = temp1;
                }
                if (arr[1][i][j] < temp2) {
                    arr[1][i][j] = temp2;
                }

                long maxNeighbour = 0;

                if (neighbourDay>neighbourNight) {
                    maxNeighbour = neighbourDay;
                } else {
                    maxNeighbour = neighbourNight;
                }

                if (msf < maxNeighbour) {
                    msf = maxNeighbour;
                }

                long maxNode = 0;

                if (arr[0][i][j] > arr[1][i][j]) {
                    maxNode = arr[0][i][j];
                } else {
                    maxNode = arr[1][i][j];
                }

                long hasil = maxNode + B.get(j-1);
                if (mxh < hasil) {
                    mxh = hasil;
                }
            }

            if (mxh > res[1]) {
                res[1] = mxh;
                res[0] = i;
            }
        }
        return res;
    }

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

        long[] jawaban = findTotal(S,M,B);

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