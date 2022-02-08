import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MapTest {

	public static void main(String[] args) {
		Map<String, String> m1 = new HashMap<String, String>();
		m1.put("satu", "value1");
		m1.put("dua", "value2");
		m1.put("tiga", "value3");
		m1.put("empat", "value4");
		m1.put("lima", "value5");
		m1.put("tiga", "valuebaru");
		m1.remove("empat");
		
		System.out.println(m1);
		
		Map<String, String> m2 = new TreeMap<String, String>(m1);
		System.out.println(m2);
		
		Iterator<Map.Entry<String, String>> itr = m1.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next(); 
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		
		System.out.println();
		for (Map.Entry<String, String> entry : m1.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		
		System.out.println();
		for (String s : m1.keySet()) {
			System.out.print(s + " ");
		}
		System.out.println();
		
		System.out.println();
		for (String s : m1.values()) {
			System.out.print(s + " ");
		}
		System.out.println();
		
	}

}
