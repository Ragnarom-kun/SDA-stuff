import java.util.PriorityQueue;

public class PriorityQueueTest {

	public static void main(String[] args) {
		PriorityQueue<Student> pQueue = new PriorityQueue<Student>(new CompareStudentByAge());
		pQueue.add(new Student("Ali", 19, "IT"));
		pQueue.add(new Student("Ami", 17, "Science"));
		pQueue.add(new Student("Aji", 20, "Economy"));
		
		while (!pQueue.isEmpty()) {
			System.out.println(pQueue.poll());
		}
	}

}
