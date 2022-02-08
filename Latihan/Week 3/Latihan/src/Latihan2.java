import java.util.Collections;
import java.util.PriorityQueue;

public class Latihan2 {

    public static void main(String[] args) {
        PriorityQueue<Mahasiswa> pQueue = new PriorityQueue<Mahasiswa>(new ComparingPriority());
        pQueue.add(new Mahasiswa(4, "Romi", 200601));
        pQueue.add(new Mahasiswa(2, "Rudi", 200602));
        pQueue.add(new Mahasiswa(3, "Sule", 200603));
        pQueue.add(new Mahasiswa(4, "Adi", 200601));
        pQueue.add(new Mahasiswa(4, "Budi", 200600));
        pQueue.add(new Mahasiswa(4, "Adi", 200500));

        while (!pQueue.isEmpty()) {
            System.out.println(pQueue.poll());
        }
    }
}

/*
    Nama = Romi Fadhurrohman Nabil
    NPM  = 2006535016
    Kelas = SDA - D
*/
