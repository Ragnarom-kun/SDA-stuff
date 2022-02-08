import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

// TODO - class untuk Lantai
class Lantai {
    String data;
    Lantai prev;
    Lantai next;

    public Lantai(String data){
        this.data = data;
    }

    Lantai(){
    }

    public String getValue(){
        return this.data;
    }

}


// TODO - class untuk Gedung
class Gedung {
    Lantai head;
    Lantai current;
    //tambah tail
    Lantai tail;

    public Gedung() {
        head = null;
        current = null;
        //tambah
        tail = null;
    }

    public void bangun(String input){
        // TODO - handle BANGUN
        Lantai newlantai = new Lantai(input);
        if(head==null){
            head = newlantai;
            current = newlantai;
            //tambah
            tail = newlantai;
            //s
            head.prev = null;
            current.next = null;
        }
        else if (head==current && current.next == null){
            current.next = newlantai;
            newlantai.prev = current;
            current = newlantai;
            current.next=null;
            tail = current;
        } else if (tail == current && current.next == null) {
            current.next = newlantai;
            newlantai.prev = current;
            current = newlantai;
            current.next = null;
            ta
        }
    }

    public String lift(String input){
        // TODO - handle LIFT
        String hasil = "";
        if(input.equals("ATAS")){
            if(current.next==null){
                hasil = head.getValue();
            }
            else{
                Lantai atas = current.next;
                current = atas;
                hasil = current.getValue();
            }
        }
        else{
            if(current.prev==null){
               hasil = head.getValue();
            }
            else{
                Lantai bawah = current.prev;
                current = bawah;
                hasil = current.getValue();
            }
        }
        return hasil;

    }

    public String hancurkan(){
        // TODO - handle HANCURKAN
        String hasil = "";
        if(head != null){
            if(head.next == null){
                hasil = head.getValue();
                head = null;
            }
            else{
                Lantai temp = new Lantai();
                temp = head;
                while(temp.next.next != null){
                    temp = temp.next;
                }
                Lantai last = temp.next;
                hasil = last.getValue();
                temp.next = null;
                last = null;
            }
        }
        return hasil;
    }

    public void timpa(String input){
        // TODO - handle TIMPA
        if(current.next != null){
            Lantai temp = new Lantai();
            temp = current;
            while(temp.next != null){
                temp = temp.next;
            }
            current = temp;
            current.prev = temp.prev;
            current.next = null;
        }
        bangun(input);
        
    }

    public String sketsa(){
        // TODO - handle SKETSA
        String hasil = "";
        if(current == null){
            hasil = "";
        }
        else{
            Lantai tunjuk = head;
            while(tunjuk != null){
                hasil += tunjuk.getValue();
                tunjuk = tunjuk.next;
            } 
        }
        return hasil;
    }

}

public class Ainun {
    private static InputReader in;
    public static PrintWriter out;
    public static Gedung Gedung;
    public static Map<String, Gedung> pondasi;
    
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // N operations
        int N = in.nextInt();
        String cmd;

        Map<String, Gedung> pondasi = new HashMap<>();

        // TODO - handle inputs
        for (int zz = 0; zz < N; zz++) {
            
            cmd = in.next();
            
            
            if(cmd.equals("FONDASI")){
                String A = in.next();
                Gedung = new Gedung();
                pondasi.put(A, Gedung);

            }
            else if(cmd.equals("BANGUN")){
                String A = in.next();
                String X = in.next();
                // TODO
                Gedung ged = pondasi.get(A);
                ged.bangun(X); 

            }
            else if(cmd.equals("LIFT")){
                String A = in.next();
                String X = in.next();
                // TODO
                Gedung ged = pondasi.get(A);
                out.println(ged.lift(X));

            }
            else if(cmd.equals("SKETSA")){
                String A = in.next();
                // TODO
                Gedung ged = pondasi.get(A);
                out.println(ged.sketsa());

            }
            else if(cmd.equals("TIMPA")){
                String A = in.next();
                String B = in.next();
                // TODO
                Gedung ged = pondasi.get(A);
                Gedung b = pondasi.get(B);
                String sket = b.sketsa();
                for(int i =0; i<sket.length(); i++){
                    char a = sket.charAt(i);
                    ged.timpa(a+"");
                }


            }
            else if(cmd.equals("HANCURKAN")){
                String A = in.next();
                // TODO
                Gedung ged = pondasi.get(A);
                out.println(ged.hancurkan());
            }
        }
     
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