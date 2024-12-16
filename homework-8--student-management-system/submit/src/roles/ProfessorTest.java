package roles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;

class ProfessorTest {
	
	private Professor professor;
	private Course course1;
	private Course course2;
	private Map<String, Course> courses;
	private Map<String, Student> students;

	@BeforeEach
	void setUp() {
		
		professor = new Professor("Orlando Bloom", "001", "obloom", "password");
		course1 = new Course("CIS101", "Intro to CS", "John Doe", "MW", "10:00", "11:30", 30);
		course2 = new Course("CIS102", "Data Structures", "Jane Smith", "TR", "12:00", "13:30", 50);
		
		courses = new HashMap<>();
		courses.put(course1.getId(), course1);
		courses.put(course2.getId(), course2);
		
		students = new HashMap<>();
		students.put("001", new Student("001", "Bob Bobby", "bobby", "pass123"));
		students.put("002", new Student("002", "Tom Hanks", "tomhanks", "pass1"));
		
	}

	@Test
	void testAddCourse() {
		
		//Test Case 1: Successfully add a course
		professor.addCourse(course1);
		assertEquals(1, professor.getCoursesTaught().size());
		assertTrue(professor.getCoursesTaught().contains(course1));
		
		//Test Case 2: Cannot add the same course again
		professor.addCourse(course1);
		assertEquals(1, professor.getCoursesTaught().size());	
		
	}
	
	@Test
	void testViewGivenCourses() {
		
		//Test Case 1: Professor has no courses
		professor.viewGivenCourses(courses);
		assertEquals(0, professor.getCoursesTaught().size());
				
		//Test Case 2: Add a course then view courses
		professor.addCourse(course1);
		assertEquals(1, professor.getCoursesTaught().size());
		assertTrue(courses.containsKey(course1.getId()));		
		
	}
	
	@Test
	void testViewStudentList() {
		
		//Test Case 1: Professor doesn't teach the course or no students enrolled
		professor.viewStudentList(course2, students);
		assertEquals(0, course2.getEnrolledStudents().size());
		
		professor.addCourse(course1);
		professor.viewStudentList(course1, students);
		assertEquals(0, course1.getEnrolledStudents().size());
		
		//Test Case 2: Professor teaches a course and views student list
		course1.addStudent("001");
		course1.addStudent("002");
		
		List<String> expectedStudents = Arrays.asList("001 Bob Bobby", "002 Tom Hanks");
		List<String> actualStudents = new ArrayList<>();
		
		for (String studentId : course1.getEnrolledStudents()) {
			
			Student student = students.get(studentId);
			if (student != null) {
				actualStudents.add(studentId + " " + student.getName());
			}
		}
		
		assertEquals(expectedStudents, actualStudents);		
		
	}	

}
