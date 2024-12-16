package courses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseTest {
	
	private Course course1;
	private Course course2;
	
	@BeforeEach
	void setUp() {
		course1 = new Course("CIS101", "Intro to CS", "John Doe", "MW", "10:00", "11:30", 30);
		course2 = new Course("CIS102", "Data Structures", "Jane Smith", "TR", "14:00", "15:30", 1);
	}

	@Test
	void testAddStudent() {
		
		//Test Case 1: Add a student successfully
		assertTrue(course1.addStudent("001"));
		assertEquals(1, course1.getEnrolledStudents().size());
		assertTrue(course1.getEnrolledStudents().contains("001"));
		
		//Test Case 2: Cannot add the same student twice
		assertFalse(course1.addStudent("001"));
		assertEquals(1, course1.getEnrolledStudents().size());
		
		//Test Case 3, Cannot add another student when course is at capacity
		assertTrue(course2.addStudent("002"));
		assertFalse(course2.addStudent("003"));
		assertEquals(1, course2.getEnrolledStudents().size());		
		
	}
	
	@Test
	void testRemoveStudent() {
		
		//Test Case 1: Remove a student successfully
		course1.addStudent("001");
		assertTrue(course1.removeStudent("001"));
		assertFalse(course1.getEnrolledStudents().contains("001"));
		assertEquals(0, course1.getEnrolledStudents().size());
		
		//Test Case 2: Cannot remove a student who isn't enrolled
		assertFalse(course1.removeStudent("001"));	
		
	}
	
	@Test
	void testHasTimeConflict() {
		
		//Test Case 1: Overlapping days and times
		Course conflictingCourse = new Course("CIS103", "Operating Systems", "Bob Bobby", "MW", "11:00", "12:30", 50);
		assertTrue(course1.hasTimeConflict(conflictingCourse));
		
		//Test Case 2: Overlapping days but not overlapping times
		Course nonConflictingTime = new Course("CIS104", "Algorithms", "James Bond", "TR", "09:00", "10:30", 50);
		assertFalse(course2.hasTimeConflict(nonConflictingTime));
		
		//Test Case 3: No overlapping days or times
		Course nonConflictingCourse = new Course("CIS105", "Web Design", "John Doe", "TR", "13:00", "14:30", 50);
		assertFalse(course1.hasTimeConflict(nonConflictingCourse));
		
	}

}
