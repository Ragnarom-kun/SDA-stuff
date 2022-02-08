import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class Test1 {
    public static void main(String[] args) {
        // Creating a HashMap
        Map<Integer, String> foodTable = new HashMap<>();
//
//        // Inserting elements to the adobe HashMap
//        // Elements- Key value pairs using put() method
        foodTable.put(3, "Tiga");
        foodTable.put(2, "a");
        foodTable.put(1, "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
        foodTable.put(4, "Empat");
        foodTable.put(0, "Zero");
        foodTable.put(99, "mbuh");
        System.out.println(foodTable);

//        foodTable.put("Tiga",3);
//        foodTable.put("Dua",2);
//        foodTable.put("Satu",1);
//        foodTable.put("Empat",4);
//
//        for (String str : foodTable) {
//
//        }
//        Deque<String> str = new ArrayDeque<>();
//        str.add("Satu");
//        str.addLast("Dua");
//        str.add("Tiga");
//        str.addLast("Empat");
//        str.add("Enam");
//        str.addFirst("Tujuh");
//        System.out.println(str);
//        System.out.println(str.pollFirst());
//        System.out.println(str);

//        List<String> str = new LinkedList<>();
//        str.add("Satu");
//        str.add("Dua");
//        str.add("Tiga");
//        System.out.println(str);
//        System.out.println(str.get(1));
        // Iterating HashMap through for loop
//        for (Map.Entry<Integer, String> set : foodTable.entrySet()) {
//
//            // Printing all elements of a Map
//            System.out.println(set.getKey() + " = " + set.getValue());
//        }
    }
}

//                    String nama = dequeMurid.pollFirst();
//                    Murid murid = KumpulanMurid.get(nama);
//                    if (StatusPrint.get(nama) == null) {
//                        StatusPrint.put(nama,false);
//                    }
//                    if (!StatusPrint.get(nama)) {
//                        StatusPrint.put(nama,true);
//                        RankingMurid.add(nama);
//                        if (murid.getBanyakDitunjuk() >= maxSementara) {
//                            maxSementara = murid.getBanyakDitunjuk();
//                        }

//                    }