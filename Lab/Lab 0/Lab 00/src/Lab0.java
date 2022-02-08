import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class Lab0 {
    private static InputReader in;
    private static PrintWriter out;

    // method of modulo multiplication (I got the inspiration of this algorithm from discrete mathematics 2 slide)
    static int multiplyMod(int N, int P, int[] a) {
        // result which will be printed
        long res = a[0]%P;

        // modulo multiplication -> ((a%mod) * (b%mod)) % mod
        // a = a[0]; b= a[i] which is (1 <= i < a.length) in looping; mod = P
        for (int i=1; i<a.length; i++) {
            res *= (a[i]%P);
            res %= P;
        }
        // return the result in integer
        return (int) res;
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // Read value of N and P
        int N = in.nextInt();

        int P = in.nextInt();

        // Read value of a
        int[] a = new int[N];
        for (int i = 0; i < N; ++i) {
            a[i] = in.nextInt();
        }

        // using modulo multiplication from the method to get the answer, and then print it
        int ans = multiplyMod(N, P, a);
        out.println(ans);

        // don't forget to close/flush the output
        out.close();
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

// Romi Fadhurrohman Nabil
// 2006535016
// SDA-D