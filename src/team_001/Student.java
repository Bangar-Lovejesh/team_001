package team_001;

import java.util.ArrayList;
import java.util.HashMap;

public class Student extends Client {
	private ArrayList<String> courseList;
	
	public Student(String email, String password, ArrayList<String> courseList) {
		super(email, password);
		this.courseList = courseList;
	}
}
