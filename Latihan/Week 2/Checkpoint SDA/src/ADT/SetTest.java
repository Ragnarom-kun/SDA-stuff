import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SetTest {

	public static void main(String[] args) {
		int[] arr = {10, 20, 15, 40, 13, 25, 15, 15, 15};
		
		Set<Integer> hSet = new HashSet<Integer>();
		for (int i : arr) {
			hSet.add(i);
		}
		System.out.println(hSet);
		
		Set<Integer> tSet = new TreeSet<Integer>(hSet);
		System.out.println(tSet);
		
		hSet.remove(15);
		System.out.println(hSet);

		hSet.remove(65);
		System.out.println(hSet);
		
		for (int a : hSet) {
			System.out.print(a + " ");
		}
		System.out.println();
		
		Iterator<Integer> itr = hSet.iterator();
		while (itr.hasNext()) {
			int a = itr.next();
			if (a < 25) {
				itr.remove();
			}
		}
		
		System.out.println(hSet);
	}

}
