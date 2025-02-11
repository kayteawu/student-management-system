package roles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;

class StudentTest {
	
	private Student student;
	private Course course1;
	private Course course2;

	@BeforeEach
	void setUp() {
		
		student = new Student("001", "Tom Hanks", "johndoe", "password");
		course1 = new Course("CIS101", "Intro to CS", "John Doe", "MW", "10:00", "11:30", 30);
		course2 = new Course("CIS102", "Data Structures", "Jane Smith", "TR", "12:00", "13:30", 50);	
		
	}

	@Test
	void testAddCompletedCourse() {
		
		//Test Case 1: Successfully add a completed course
		student.addCompletedCourse("CIS101", "A");
		assertEquals(1, student.getCompletedCourses().size());
		assertTrue(student.getCompletedCourses().containsKey("CIS101"));
		assertEquals("A", student.getCompletedCourses().get("CIS101"));
		
		//Test Case 2: Cannot add the same completed course again
		student.addCompletedCourse("CIS101", "A");
		assertEquals(1, student.getCompletedCourses().size());		
		
	}
	
	@Test
	void testAddCourse() {
		
		//Test Case 1: Successfully enroll in a course
		assertTrue(student.addCourse(course1));
		assertEquals(1, student.getEnrolledCourses().size());
		assertTrue(student.getEnrolledCourses().containsKey(course1));
		assertEquals("Not Graded", student.getEnrolledCourses().get(course1));
		
		//Test Case 2: Cannot enroll in a course already completed
		student.addCompletedCourse("CIS102", "A");
		assertFalse(student.addCourse(course2));
		
		//Test Case 3: Cannot enroll in a course with time conflict
		Course conflictingCourse = new Course("CIS103", "Operating Systems", "Bob Bobby", "MW", "11:00", "12:30", 50);
		assertFalse(student.addCourse(conflictingCourse));		
		
	}
	
	@Test
	void testDropCourse() {
		
		//Test Case 1: Successfully drop a course
		student.addCourse(course1);
		assertTrue(student.dropCourse(course1));
		assertEquals(0, student.getEnrolledCourses().size());
		
		//Test Case 2: Cannot drop a course not enrolled in
		assertFalse(student.dropCourse(course2));		
		
	}

}
