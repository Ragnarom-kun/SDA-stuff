import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


class Lab2 {

    private static InputReader in;
    private static PrintWriter out;

    // TODO
    static int jumlah = 0;
    static Map<String, Integer> gengafter = new HashMap<String, Integer>();
    static Deque<Integer> myQ = new LinkedList<Integer>();
    static Deque<String> myQue = new LinkedList<String>();

    static private int handleDatang(String Gi, int Xi) {
        myQ.add(Xi);
        myQue.add(Gi);
        jumlah += Xi;
        return jumlah;
    }

    // TODO
    static private String handleLayani(int Yi) {
        jumlah -= Yi;
        while(Yi>0){
            int satu = 0;
            String hilang = "";

            if(Yi-myQ.peek()>0){
                satu = myQ.poll();
                hilang = myQue.poll();
                Yi-= satu;
            }
            
            else {
                int dua = myQ.poll()-Yi;
                myQ.offerFirst(dua);
                satu = Yi;
                hilang = myQue.peek();
                Yi=0;
            }

            if(gengafter.containsKey(hilang)){
                gengafter.put(hilang, gengafter.get(hilang)+satu);

            }
            else{
                gengafter.put(hilang, satu);
            }

        }
        
        return myQue.peek();
    }

    // TODO
    static private int handleTotal(String Gi) {
        try{
            return gengafter.get(Gi);
        }
        catch(NullPointerException e){
            return 0;
        }
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N;

        N = in.nextInt();

        for(int tmp=0;tmp<N;tmp++) {
            String event = in.next();

            if(event.equals("DATANG")) {
                String Gi = in.next();
                int Xi = in.nextInt();

                out.println(handleDatang(Gi, Xi));
            } else if(event.equals("LAYANI")) {
                int Yi = in.nextInt();
                
                out.println(handleLayani(Yi));
            } else {
                String Gi = in.next();

                out.println(handleTotal(Gi));
            }
        }

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