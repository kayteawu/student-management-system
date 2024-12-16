package files;

import java.io.*;
import java.util.*;

import courses.Course;
import roles.Admin;
import roles.Professor;
import roles.Student;

/**
 * Reads and parses input files for the Student Management System
 * Loads data for admins, professors, students, and courses
 * @author Katie
 */
public class FileInfoReader {
		
	/**
	 * Reads and parses course info from the info file and loads data into courses list
	 * @param filePath to the courseInfo.txt file
	 * @throws IOException if an error occurs while reading the file
	 */
	public void loadCourses(String fileName, Map<String, Course> courses) {
		
		//tries to read the file
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			
			//reads through each line in the file
			while ((line = reader.readLine()) != null) {
				
				//splits the line into components
				String[] parts = line.split(";");
				
				//checks if the line has the correct number of components
				if (parts.length == 7) {
					
					//if valid, parses through each component
					String courseId = parts[0].trim();
					String courseName = parts[1].trim();
					String lecturer = parts[2].trim();
					String days = parts[3].trim();
					String startTime = parts[4].trim();
					String endTime = parts[5].trim();
					int capacity = Integer.parseInt(parts[6].trim());
					
					//check for missing fields and skips invalid entries
					if (courseId.isEmpty() || courseName.isEmpty() || lecturer.isEmpty() ||
						days.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
						System.out.println("Invalid course data: " + line);
						continue;
					}					
					
					//creates a new Course object with the parsed components and adds to map
					Course course = new Course(courseId, courseName, lecturer, days, startTime, endTime, capacity);
					courses.put(courseId, course);
				
                //if line has wrong number of components, prints a message saying data is invalid
				} else {
					System.out.println("Invalid course data: " + line);
				}
				
			}				
		
        //catches error when reading course file
		} catch (IOException e) {
			System.out.println("Error reading courses file: " + e.getMessage());

        //catches error when parsing course capacity
		} catch (NumberFormatException e) {
			System.out.println("Error parsing course capacity: " + e.getMessage());
		}
		
	}
	
	/**
	 * Reads and parses student info from the info file and loads data into students map
	 * @param filePath to the studentInfo.txt file
	 * @throws IOException if an error occurs while reading the file
	 */
	public void loadStudents(String fileName, Map<String, Student> students) {
		
		//tries to read the file
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			
			//reads through each line in the file
			while ((line = reader.readLine()) != null) {
				
				//splits the line into components
				String[] parts = line.split(";");
				
				//checks if the line has the correct number of fields
				if (parts.length >= 4) {
					
					//if valid, parses through each component
					String studentId = parts[0].trim(); 
					String studentName = parts[1].trim(); 
					String studentUsername = parts[2].trim(); 
					String studentPassword = parts[3].trim();
					
					//check for missing fields and skips invalid entries
					if (studentId.isEmpty() || studentName.isEmpty() ||
						studentUsername.isEmpty() || studentPassword.isEmpty()) {
						System.out.println("Invalid student data: " + line);
						continue;
					}
					
					//creates a new Student object using the parsed components
					Student student = new Student(studentId, studentName, studentUsername, studentPassword);
					
					//parses courses and grades if available
					if (parts.length > 4) {
						String[] coursesWithGrades = parts[4].split(",");
						
						//iterates through each course grade pair
						for (String courseGradePair : coursesWithGrades) {
							String[] courseGrade = courseGradePair.split(":");
							
                            //checks for the correct number of components and parses course ID and letter grade
							if (courseGrade.length == 2) {
								String courseId = courseGrade[0].trim();
								String grade = courseGrade[1].trim();

                                //adds the course to completed courses
								student.addCompletedCourse(courseId, grade);

                            //if wrong number of components, prints an error message
							} else {
								System.out.println("Invalid course-grade data for student: " + line);
							}
						}
					}
					
					//adds the Student to the map
					students.put(studentId, student);

                //if line has wrong number of fields, prints an error message		
				} else {
					System.out.println("Invalid student data: " + line);
				}
						
			}					

        //catches error when reading file	
		} catch (IOException e) {
			System.out.println("Error reading students file: " + e.getMessage());
		}
				
	}
	
	/**
	 * Reads and parses professor info from the info file and loads data into professors map
	 * @param filePath to the profInfo.txt file
	 * @throws IOException if an error occurs while reading the file
	 */
	public void loadProfessors(String fileName, Map<String, Professor> professors) {
		
		//tries to read the file
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			
			//reads through each line in the file
			while ((line = reader.readLine()) != null) {
				
				//splits the line into components
				String[] parts = line.split(";");
				
				//checks if the line has the correct number of fields
				if (parts.length == 4) {
					
					//if valid, parses through each component
					String profName = parts[0].trim();
					String profId = parts[1].trim();
					String profUsername = parts[2].trim();
					String profPassword = parts[3].trim();
					
					//check for missing fields and skips invalid entries
					if (profId.isEmpty() || profName.isEmpty() ||
						profUsername.isEmpty() || profPassword.isEmpty()) {
						System.out.println("Invalid professor data: " + line);
						continue;
					}
					
					//creates a new Professor object using the parsed components and adds to map
					Professor professor = new Professor(profId, profName, profUsername, profPassword);
					professors.put(profId, professor);					
				
                //if line has wrong number of fields, prints an error message
				} else {
					System.out.println("Invalid professor data: " + line);
				}
				
			}		
		
        //catches error when reading file
		} catch (IOException e) {
			System.out.println("Error reading professors file: " + e.getMessage());
		}
		
	}
	
	/**
	 * Reads and parses admin info from the info file and loads data into admins map
	 * @param filePath to the adminInfo.txt file
	 * @throws IOException if an error occurs while reading the file 
	 */
	public void loadAdmins(String fileName, Map<String, Admin> admins) {
		
		//tries to read the file
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			
			//reads through each line in the file
			while ((line = reader.readLine()) != null) {
				
				//splits the line into components
				String[] parts = line.split(";");
				
				//checks if the line has the correct number of fields
				if (parts.length == 4) {
					
					//if valid, parses through each component
					String adminId = parts[0].trim();
					String adminName = parts[1].trim();
					String adminUsername = parts[2].trim();
					String adminPassword = parts[3].trim();
					
					//check for missing fields and skips invalid entries
					if (adminId.isEmpty() || adminName.isEmpty() ||
						adminUsername.isEmpty() || adminPassword.isEmpty()) {
						System.out.println("Invalid admin data: " + line);
						continue;
					}
					
					//creates a new Admin object from the parsed components and adds to map
					Admin admin = new Admin(adminId, adminName, adminUsername, adminPassword);			
					admins.put(adminUsername, admin);
				
                //if line has wrong number of fields, prints an error message
				} else {
					System.out.println("Invalid admin data: " + line);
				}
				
			}	
		
        //catches error when reading file
		} catch (IOException e) {
			System.out.println("Error reading admins file: " + e.getMessage());
		}
		
	}	

}