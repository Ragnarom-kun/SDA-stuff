//import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.*;
//
//// TODO - class untuk Lantai
//class Lantai {
//
//    Lantai next;
//    Lantai prev;
//    String namaLantai;
//
//    public Lantai(String nama, Lantai next, Lantai prev){
//        this.namaLantai = nama;
//        this.next = next;
//        this.prev = prev;
//    }
//
//    public String getValue(){
//        return namaLantai;
//    }
//
//}
//
//
//// TODO - class untuk Gedung
//class Gedung {
//    Lantai head;          // head of lantai's node
//    Lantai pointerAgen;
//    Lantai tail;
//
//    public Gedung() {
//        this.head = null;
//        this.pointerAgen = null;
//        this.tail = null;
//    }
//
//    public void bangun(String input){
//        // TODO - handle BANGUN
//        if (head==null) { // head == null or the first floor
//            head = new Lantai(input,null,null);
//            pointerAgen = head; tail = pointerAgen;
//        } else if (pointerAgen == head && head.next == null) {  // making second floor
//            head.next = new Lantai(input,null,head);
//            pointerAgen = head.next; tail = pointerAgen;
//        } else if (pointerAgen == tail){        // adding last element (exclude 1st & 2nd floor)
//            pointerAgen.next = new Lantai(input,null,pointerAgen);
//            pointerAgen = pointerAgen.next; tail = pointerAgen;
//        } else {    // inserting floor (nambah di tengah)
//            if (pointerAgen != null) {
//                Lantai temp = new Lantai(input,pointerAgen.next,pointerAgen);
//                if (pointerAgen.next != null) {
//                    pointerAgen.next.prev = temp;
//                }
//                pointerAgen.next = temp;
//                pointerAgen = pointerAgen.next;
//            }
//        }
//        //debug
//        String s = "";
//        Lantai temp = head;
//        while (temp != null) {
//            s += temp.getValue();
//            temp = temp.next;
//            if (temp != null)
//                s += (" ");
//        }
//        System.out.println(s);
//        if (tail != null) {
//            System.out.println("Tail : "+tail.getValue());
//        }
//    }
//
//    public String lift(String input){       // RTE harus handle null
//        // TODO - handle LIFT
//        if (pointerAgen != null && head != null) {
//            if (input.equals("ATAS")) {
//                if (pointerAgen.next != null) {
//                    pointerAgen = pointerAgen.next;
//                }
//            } else {
//                if (pointerAgen.prev != null) {
//                    pointerAgen = pointerAgen.prev;
//                }
//            }
//            return pointerAgen.getValue();
//        }
//        return "";
//    }
//
//    public String hancurkan(){
//        // TODO - handle HANCURKAN
//        String res = "";
//        boolean cekNull = pointerAgen != null;
//        if (pointerAgen==head && head != null && head.next==null && cekNull) {     // hanya 1 lantai
//            res = head.getValue();
//            head = null;
//            pointerAgen = null;
//            tail = null;
//        } else if (pointerAgen==tail && tail!=null && tail.prev != null && cekNull) {     // highest floor
//            res = pointerAgen.getValue();
//            pointerAgen = pointerAgen.prev;
//            pointerAgen.next = null;
//            tail = pointerAgen;
//        } else if (pointerAgen==head && cekNull) {     // lantai dasar
//            res = head.getValue();
//            pointerAgen = pointerAgen.next;
//            pointerAgen.prev = null;
//            head = pointerAgen;
//            head.prev = null;
//        } else {                            // tengah lantai
//            if (cekNull) {
//                res = pointerAgen.getValue();
//                Lantai next = pointerAgen.next;
//                pointerAgen = pointerAgen.prev;
//                if (pointerAgen != null) {
//                    pointerAgen.next = next;
//                }
//                if (next != null) {
//                    next.prev = pointerAgen;
//                }
//            }
//        }
//        return res;
//    }
//
//    public void timpa(Gedung input){
//        // TODO - handle TIMPA
//        if (tail != null && input.head != null) {
//            tail.next = input.head;
//            tail.next.prev = tail;
//            tail = input.tail;
//        }
//    }
//
//    public String sketsa(){
//        // TODO - handle SKETSA
//        StringBuilder sketsa = new StringBuilder();
//        Lantai temp = head;
//        while (temp != null) {
//            sketsa.append(temp.getValue());
//            temp = temp.next;
//        }
//        return sketsa.toString();
//    }
//
//
//}
//
//public class Debugging {
//    private static InputReader in;
//    public static PrintWriter out;
//    public static Gedung Gedung;
//    public static HashMap<String,Gedung> kumpulanGedung;
//
//    public static void main(String[] args) throws IOException {
//        InputStream inputStream = System.in;
//        in = new InputReader(inputStream);
//        OutputStream outputStream = System.out;
//        out = new PrintWriter(outputStream);
//
//        kumpulanGedung = new HashMap<>();
//        // N operations
//        int N = in.nextInt();
//        String cmd;
//
//        // TODO - handle inputs
//        for (int zz = 0; zz < N; zz++) {
//
//            cmd = in.next();
//
//
//            if(cmd.equals("FONDASI")){
//                String A = in.next();
//                Gedung = new Gedung();       // awal isinya msh null lantai
//                kumpulanGedung.put(A,Gedung);
//            }
//            else if(cmd.equals("BANGUN")){
//                String A = in.next();
//                String X = in.next();
//                // TODO
//                Gedung = kumpulanGedung.get(A);
//                Gedung.bangun(X);
//            }
//            else if(cmd.equals("LIFT")){
//                String A = in.next();
//                String X = in.next();
//                // TODO
//                Gedung = kumpulanGedung.get(A);
//                out.println(Gedung.lift(X));
//            }
//            else if(cmd.equals("SKETSA")){
//                String A = in.next();
//                // TODO
//                Gedung = kumpulanGedung.get(A);
//                out.println(Gedung.sketsa());
//            }
//            else if(cmd.equals("TIMPA")){
//                String A = in.next();
//                String B = in.next();
//                // TODO
//                Gedung = kumpulanGedung.get(A);
//                Gedung gedung2 = kumpulanGedung.get(B);
//                Gedung.timpa(gedung2);
//                kumpulanGedung.put(B,null);
//            }
//            else if(cmd.equals("HANCURKAN")){
//                String A = in.next();
//                // TODO
//                Gedung = kumpulanGedung.get(A);
//                out.println(Gedung.hancurkan());
//            }
//        }
//
//        // don't forget to close/flush the output
//        out.close();
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
//
//
