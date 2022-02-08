import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueTest {

	public static void main(String[] args) {
		// Implement queue using ArrayDeque
		Queue<Integer> q1 = new ArrayDeque<Integer>();

		// enqueue()
		for (int i = 0; i < 10; i++) {
			q1.add(i);
		}

		// front()
		System.out.println(q1.peek());

		// dequeue()
		while (!q1.isEmpty()) {
			System.out.print(q1.poll() + " ");
		}
		System.out.println();

		// Implement queue using ArrayList
		ArrayList<Integer> q2 = new ArrayList<Integer>();

		// enqueue()
		for (int i = 0; i < 10; i++) {
			q2.add(i);
		}

		// front()
		System.out.println(q2.get(0));

		// dequeue()
		while (!q2.isEmpty()) {
			System.out.print(q2.remove(0) + " ");
		}
		System.out.println();

		PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>(Collections.reverseOrder());
		pQueue.add(9);
		pQueue.add(4);
		pQueue.add(6);
		pQueue.add(3);
		pQueue.add(10);
		pQueue.add(18);
		pQueue.add(14);

		// dequeue()
		while (!pQueue.isEmpty()) {
			System.out.print(pQueue.poll() + " ");
		}
		System.out.println();
	}

}
