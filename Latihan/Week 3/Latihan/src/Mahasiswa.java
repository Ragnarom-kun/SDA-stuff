import java.util.Comparator;

public class Mahasiswa {
    int IPK;
    String nama;
    int NPM;

    public Mahasiswa(int IPK, String nama, int NPM) {
        this.IPK = IPK;
        this.nama = nama;
        this.NPM = NPM;
    }

    @Override
    public String toString() {
        return this.nama + " - " + this.IPK + " - " + this.NPM;
    }

}

class ComparingPriority implements Comparator<Mahasiswa> {
    @Override
    public int compare(Mahasiswa o1, Mahasiswa o2) {
        if (!(o1.IPK == o2.IPK)) {
            return o2.IPK - o1.IPK;
        }
        else {
            if (!(o1.nama.equals(o2.nama))) {
                return o1.nama.compareToIgnoreCase(o2.nama);
            } else {
                return o1.NPM - o2.NPM;
            }
        }
    }
}