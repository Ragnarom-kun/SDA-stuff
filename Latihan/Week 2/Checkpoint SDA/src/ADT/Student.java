import java.util.Comparator;

public class Student implements Comparable<Student> {
	String name;
	int age;
	String department;

	public Student(String name, int age, String department) {
		this.name = name;
		this.age = age;
		this.department = department;
	}

	@Override
	public int compareTo(Student o) {
		return this.name.compareToIgnoreCase(o.name);
	}
	
	@Override
	public String toString() {
		return this.name + " - " + this.age + " - " + this.department;
	}
}

class CompareStudentByAge implements Comparator<Student> {

	@Override
	public int compare(Student s1, Student s2) {
		return s1.age - s2.age;
	}
	
}
