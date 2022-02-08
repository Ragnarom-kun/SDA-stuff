/* Tuliskan Nama, NPM, dan Kelasmu di sini:
** Nama Lengkap: Romi Fadhurrohman Nabil
** NPM         : 2006535016
** Kelas       : D
*/
import java.util.*;

public class Soal2{

		/*TO DO*/
		/*Letakkan ketiga baris ini pada main method atau pada constructor,
		*sehingga program dapat berjalan (lolos test cases level 1)*/
		Scanner ss = new Scanner(System.in);
        while(ss.hasNext())
        processThis(ss.nextLine());

	/*TO DO
	*
	* Lanjutkan implementasi method-method berikut ini
	* (boleh memodifikasi lebih dari 1 method)
	*/
    void processThis(String stringinput) {
        String[] tokens = stringinput.split(" ");
        Stack<String> s = new Stack<String>();
        s.push("");
        for (String t : tokens) {
            if (isOperator(t)) {
                while (!isHigher(t, s.peek())) {
                    String v = s.pop();
                    System.out.print(v);
                }
                s.push(t);
            } else System.out.print(t);
        }
        while (!s.isEmpty())
            System.out.print(s.pop());
        System.out.println();
    }

    boolean isOperator(String t) {
        return t.equals("+") || t.equals("*");
    }

    boolean isHigher(String x, String y) {
        if (y.equals("")) return true;
        if (orderOf(x) > orderOf(y)) return true;
        return false;
    }

    int orderOf(String op) {
        if (op.equals("+")) return 1;
        if (op.equals("*")) return 2;
        return -99;
    }
}
