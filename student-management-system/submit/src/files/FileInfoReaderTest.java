package files;

import courses.Course;
import roles.Admin;
import roles.Professor;
import roles.Student;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileInfoReaderTest {
	
	private FileInfoReader fileInfoReader;
	private Map<String, Course> courses;
	private Map<String, Student> students;
	private Map<String, Professor> professors;
	private Map<String, Admin> admins;
	
	@BeforeEach
	void setUp() {
		fileInfoReader = new FileInfoReader();
		courses = new HashMap<>();
		students = new HashMap<>();
		professors = new HashMap<>();
		admins = new HashMap<>();
	}

	@Test
	void testLoadCourses() throws IOException {
		
		//create temporary file
		File tempFile = Files.createTempFile("testCourses", ".txt").toFile();
		
		//write test data to temp file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){
			
			//valid course data
			writer.write("CIS101; Intro to CS; John Doe; MW; 10:00; 11:30; 30\n");
			writer.write("CIS102; Data Structures; Jane Smith; TR; 12:00; 13:30; 50\n");
			
			//invalid course data
			writer.write("CIS103; Operating Systems; Bob Bobby; MW; 14:00; 15:30\n"); //missing capacity
			writer.write("CIS104;;; TR; 09:00; 10:30; 100\n"); //missing course name and lecturer
			writer.write("CIS105; Web Design; John Doe; MW; 13:00;; 100\n"); //missing end time 
			
		}
		
		//calls the loadCourses method
		fileInfoReader.loadCourses(tempFile.getAbsolutePath(), courses);
		
		//Test Case 1: Valid Data
		assertEquals(2, courses.size());
		assertTrue(courses.containsKey("CIS101"));
		assertTrue(courses.containsKey("CIS102"));
		
		Course course1 = courses.get("CIS101");
		assertEquals("Intro to CS", course1.getName());
		assertEquals("John Doe", course1.getLecturer());
		assertEquals(30, course1.getCapacity());
		
		Course course2 = courses.get("CIS102");
		assertEquals("Data Structures", course2.getName());
		assertEquals("Jane Smith", course2.getLecturer());
		assertEquals(50, course2.getCapacity());
		
		//Test Case 2: Invalid Data
		assertFalse(courses.containsKey("CIS103"));
		assertFalse(courses.containsKey("CIS104"));
		assertFalse(courses.containsKey("CIS105"));
		
		//clean up temp file
		tempFile.delete();
		
	}
	
	@Test
	void testLoadStudents() throws IOException {
		
		//create temporary file
		File tempFile = Files.createTempFile("testStudents", ".txt").toFile();
				
		//write test data to temp file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){
					
			//valid student data
			writer.write("001; John Doe; johndoe; password; CIS101:A, CIS102:B\n");
			writer.write("002; Jane Smith; janesmith; pass123; CIS103:A\n");
			writer.write("003; Bob Bobby; bobby; pass\n"); //no grades still valid
					
			//invalid student data			
			writer.write("004;; username; password; CIS104:C\n"); //missing student name
			writer.write(";; user1; pass1\n"); //missing student ID and name
					
		}
				
		//calls the loadStudents method
		fileInfoReader.loadStudents(tempFile.getAbsolutePath(), students);
				
		//Test Case 1: Valid Data
		assertEquals(3, students.size());
		assertTrue(students.containsKey("001"));
		assertTrue(students.containsKey("002"));
		assertTrue(students.containsKey("003"));
		
		Student student1 = students.get("001");
		assertEquals("John Doe", student1.getName());
		assertEquals("johndoe", student1.getUsername());
		assertEquals(2, student1.getCompletedCourses().size());
		
		Student student2 = students.get("003");
		assertEquals("Bob Bobby", student2.getName());
		assertEquals("bobby", student2.getUsername());
		assertEquals(0, student2.getCompletedCourses().size());
		
		//Test Case 2: Invalid Data
		assertFalse(students.containsKey("004"));
		assertFalse(students.containsKey(""));
		
		//clean up temp file
		tempFile.delete();		
		
	}
	
	@Test
	void testLoadProfessors () throws IOException {
		
		//create temporary file
		File tempFile = Files.createTempFile("testProfessors", ".txt").toFile();
				
		//write test data to temp file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){
					
			//valid professor data
			writer.write("John Doe; 001; johndoe; password\n");
			writer.write("Jane Smith; 002; janesmith; pass123\n");			
					
			//invalid professor data			
			writer.write("Bob Bobby; 003; bobby\n"); //missing password
			writer.write(";;; pass1\n"); //missing name, ID, and username
					
		}
				
		//calls the loadProfessors method
		fileInfoReader.loadProfessors(tempFile.getAbsolutePath(), professors);
				
		//Test Case 1: Valid Data
		assertEquals(2, professors.size());
		assertTrue(professors.containsKey("001"));
		assertTrue(professors.containsKey("002"));
		
		Professor professor1 = professors.get("001");
		assertEquals("John Doe", professor1.getName());
		assertEquals("001", professor1.getId());
		assertEquals("password", professor1.getPassword());
		
		Professor professor2 = professors.get("002");
		assertEquals("Jane Smith", professor2.getName());
		assertEquals("002", professor2.getId());
		assertEquals("pass123", professor2.getPassword());
		
		//Test Case 2: Invalid Data
		assertFalse(professors.containsKey("003"));
		assertFalse(professors.containsKey(""));
		
		//clean up temp file
		tempFile.delete();		
		
	}
	
	@Test
	void testLoadAdmins () throws IOException {
		
		//create temporary file
		File tempFile = Files.createTempFile("testAdmins", ".txt").toFile();
				
		//write test data to temp file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){
					
			//valid admin data
			writer.write("001; Admin John; admin01; adminpass\n");
			writer.write("002; Admin Jane; admin02; admin123\n");			
					
			//invalid admin data			
			writer.write("003; Admin Bob;; admin1\n"); //missing username
			writer.write(";; admin04;\n"); //missing name, ID, and password
					
		}
				
		//calls the loadAdmins method
		fileInfoReader.loadAdmins(tempFile.getAbsolutePath(), admins);
				
		//Test Case 1: Valid Data
		assertEquals(2, admins.size());
		assertTrue(admins.containsKey("admin01"));
		assertTrue(admins.containsKey("admin02"));
		
		Admin admin1 = admins.get("admin01");
		assertEquals("Admin John", admin1.getName());
		assertEquals("admin01", admin1.getUsername());
		assertEquals("adminpass", admin1.getPassword());
		
		Admin admin2 = admins.get("admin02");
		assertEquals("Admin Jane", admin2.getName());
		assertEquals("admin02", admin2.getUsername());
		assertEquals("admin123", admin2.getPassword());
		
		//Test Case 2: Invalid Data
		assertFalse(admins.containsKey(""));
		assertFalse(admins.containsKey("admin04"));
		
		//clean up temp file
		tempFile.delete();		
		
	}
}
