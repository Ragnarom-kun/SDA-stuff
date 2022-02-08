import java.io.*;
import java.util.*;

public class Lab1 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    /**
     * The main method that reads input, calls the function 
     * for each question's query, and output the results.
     * @param args Unused.
     * @return Nothing.
     */
    public static void main(String[] args) {

        int N = in.nextInt();   // banyak bintang
        int M = in.nextInt();   // panjang sequence

        List<String> sequence = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            String temp = in.next();
            sequence.add(temp);
        }

        int maxMoney = getMaxMoney(N, M, sequence);
        out.println(maxMoney);
        out.close();
    }

    /* sumber inspirasi :
        https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/
        https://medium.com/@rsinghal757/kadanes-algorithm-dynamic-programming-how-and-why-does-it-work-3fd8849ed73d
        PPT/Slide materi week 1
    */
    public static int getMaxMoney(int N, int M, List<String> sequence) {
        List<Integer> nums = new ArrayList<>();
        int temp = 0;

        // Program akan parse arraylist lama ke arraylist baru dengan elemen barunya mewakilkan sum bilangan di antara '* *'
        for (int i=1; i<M; i++) {
            if (sequence.get(i).equals("*")) {
                nums.add(temp);
                temp = 0;
                continue;
            }
            temp += Integer.parseInt(sequence.get(i));
        }

        // Maximum Contiguous Subsequence Sum algorithm dgn kompleksitas O(n)
        int localMax = 0;
        int maxSum = nums.get(0);

        for (int i=0; i<nums.size(); i++) {
            localMax = Math.max(nums.get(i), nums.get(i) + localMax);
            maxSum = Math.max(localMax, maxSum);
        }

        return maxSum;
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