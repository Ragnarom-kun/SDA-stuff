public class Test1 {

    static int jumlah = 0;

    public static void main(String[] args) {
        cetakBintang(16);
        System.out.println(jumlah);
    }

    static void cetakBintang(int n) {
        if (n>1) {
            cetakBintang(n/2);
            cetakBintang(n/2);
        }
        System.out.println("* ");
        jumlah++;
    }
}
