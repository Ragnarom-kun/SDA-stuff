import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Stack;

public class StackTest {

	public static void main(String[] args) {
		// Implement stack using Stack class
		Stack<Integer> s1 = new Stack<Integer>();

		// push()
		for (int i = 0; i < 10; i++) {
			s1.push(i);
		}

		// top()
		System.out.println(s1.peek());

		// pop()
		while (!s1.isEmpty()) {
			System.out.print(s1.pop() + " ");
		}
		System.out.println();

		// Implement stack using ArrayList
		ArrayList<Integer> s2 = new ArrayList<Integer>();

		// push()
		for (int i = 0; i < 10; i++) {
			s2.add(i);
		}

		// top()
		System.out.println(s2.get(s2.size() - 1));

		// pop()
		while (!s2.isEmpty()) {
			System.out.print(s2.remove(s2.size() - 1) + " ");
		}
		System.out.println();

		// Implement stack using Deque
		Deque<Integer> s3 = new ArrayDeque<Integer>();

		// push()
		for (int i = 0; i < 10; i++) {
			s3.push(i);
		}

		// top()
		System.out.println(s3.peek());

		// pop()
		while (!s3.isEmpty()) {
			System.out.print(s3.pop() + " ");
		}
		System.out.println();
		
		String text = "(Hari ini (indah (untuk))) (bela(jar) (struktur data))))";
		System.out.println(isBalancedBracket(text) ? "Balanced" : "Not Balanced");
	}
	
	static boolean isBalancedBracket(String text) {
		Deque<Character> s = new ArrayDeque<Character>();
		
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			
			if (c == '(') {
				s.push(c);
			} else if (c == ')') {
				if (s.isEmpty()) return false;
				s.pop();
			} else {
				continue;
			}
		}
		
		return s.isEmpty();
	}

}
