package roles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;

class AdminTest {
	
	private Admin admin;
	private Map<String, Course> courses;
	private Map<String, Professor> professors;
	private Map<String, Student> students;

	@BeforeEach
	void setUp() {
		
		admin = new Admin("001", "Admin John", "admin01", "password");
		courses = new HashMap<>();
		professors = new HashMap<>();
		students = new HashMap<>();
		
		Professor professor = new Professor("001", "John Doe", "johndoe", "pass1");
		professors.put(professor.getName(), professor);
		
	}

	@Test
	void testAddCourse() {
		
		Course course = new Course("CIS101", "Intro to CS", "John Doe", "MW", "10:00", "11:30", 30);
				
		//Test Case 1: Successfully add a new course
		admin.addCourse(courses, professors, course, new Scanner(System.in));
		assertEquals(1, courses.size());
		assertTrue(courses.containsKey(course.getId()));
		
		//Test Case 2: Cannot add a duplicate course
		admin.addCourse(courses, professors, course, new Scanner(System.in));
		assertEquals(1, courses.size());
		
		//Test Case 3: Cannot add a course with time conflict for lecturer
		Course conflictingCourse = new Course("CIS102", "Data Structures", "John Doe", "MW", "11:00", "12:30", 50);
		
		admin.addCourse(courses, professors, conflictingCourse, new Scanner(System.in));
		assertEquals(1, courses.size());	
		
	}
	
	@Test
	void testDeleteCourse() {
		
		Course course = new Course("CIS101", "Intro to CS", "John Doe", "MW", "10:00", "11:30", 30);
		courses.put(course.getId(), course);
		
		//Test Case 1: Successfully delete a course
		admin.deleteCourse(courses, course.getId());
		assertEquals(0, courses.size());
		
		//Test Case 2: Cannot delete a course that doesn't exist
		admin.deleteCourse(courses, "002");
		assertEquals(0, courses.size());		
		
	}
	
	@Test
	void testAddProfessor() {
		
		Professor professor2 = new Professor("Jane Smith", "002", "janesmith", "pass123");
		
		//Test Case 1: Successfully adds a new professor
		assertTrue(admin.addProfessor(professors, professor2));
		assertEquals(2, professors.size());
		assertTrue(professors.containsKey(professor2.getId()));
		
		//Test Case 2: Cannot add a duplicate professor
		assertFalse(admin.addProfessor(professors, professor2));
		assertEquals(2, professors.size());	
		
	}
	
	@Test
	void testDeleteProfessor() {
		
		Professor professor2 = new Professor("Jane Smith", "002", "janesmith", "pass123");
		professors.put(professor2.getId(), professor2);
		
		//Test Case 1: Successfully delete a professor
		admin.deleteProfessor(professors, professor2.getId());
		assertEquals(1, professors.size());
		
		//Test Case 2: Cannot delete a professor that doesn't exist
		admin.deleteProfessor(professors, "003");
		assertEquals(1, professors.size());	
		
	}
	
	@Test
	void testAddStudent() {
		
		Student student = new Student("001", "Bob Bobby", "bobby", "pass1");
		
		//Test Case 1: Successfully adds a new student
		assertTrue(admin.addStudent(students, student));
		assertEquals(1, students.size());
		assertTrue(students.containsKey(student.getId()));
		
		//Test Case 2: Cannot add a duplicate student
		assertFalse(admin.addStudent(students, student));
		assertEquals(1, students.size());		
		
	}
	
	@Test
	void testDeleteStudent() {
		
		Student student = new Student("001", "Bob Bobby", "bobby", "pass1");
		students.put(student.getId(), student);
		
		//Test Case 1: Successfully delete a student
		admin.deleteStudent(students, student.getId());
		assertEquals(0, students.size());
		
		//Test Case 2: Cannot delete a student that doesn't exist
		admin.deleteStudent(students, "002");
		assertEquals(0, students.size());		
		
	}
	
	
	
	

}
