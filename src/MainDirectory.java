import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class MainDirectory implements Directory {

	ArrayList<Employee> sorted = new ArrayList<>();

	@Override
	public void add(String str) {
		// TODO Auto-generated method stub

	}

	public void print() {
		// TODO Auto-generated method stub
		if (sorted.size() == 0) {
			System.out.println("<empty directory>");
		} else {
			for (Employee ee : sorted) {
				ee.print();
			}
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		sorted.clear();
		System.out.println("Empty sorted list");
	}

	void reconstruct(String s) {
		Gson g = new Gson();
		ArrayList<Employee> incoming = (g.fromJson(s, new TypeToken<Collection<Employee>>() {
		}.getType()));
		for (Employee emp : incoming) {
			// pp.print();
			addAndSort(emp);
		}

	}

	void addAndSort(Employee emp) {
		if (sorted.size() == 0) {
			sorted.add(emp);
		} else {
			int size = sorted.size();
			for (Employee cur : sorted) {
				if (emp.last.compareTo(cur.last) == 0) {
					if (emp.first.compareTo(cur.first) < 0) {
						sorted.add(sorted.indexOf(cur), emp);
						break;
					} else {
						sorted.add(sorted.indexOf(cur) + 1, emp);
						break;
					}
				}
				if (emp.last.compareTo(cur.last) < 0) {
					sorted.add(sorted.indexOf(cur), emp);
					break;
				}
			}
			if (size == sorted.size()) {
				sorted.add(sorted.size() - 1, emp);
			}
		}

	}

}
