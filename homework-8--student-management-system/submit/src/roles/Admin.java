package roles;

import java.util.Map;
import java.util.Scanner;

import courses.Course;

/**
 * Represents an Admin user in the Student Management System
 * Admins can manage courses, professors, and students
 * @author Katie
 */
public class Admin extends User {

	//constructor
	
	/**
	 * Creates a new Admin user using superclass User constructor
	 * @param id of admin
	 * @param name of admin
	 * @param username for admin's login
	 * @param password for admin's login
	 */
	public Admin(String id, String name, String username, String password) {
		
		//calls the superclass constructor
		super(id, name, username, password);
		
	}
	
	/**
	 * Displays all available courses in the system
	 * @param courses map of all existing courses
	 */
	public void viewAllCourses(Map<String, Course> courses) {
		
		//checks if courses list is empty and prints a message
		if (courses == null || courses.isEmpty()) {
			System.out.println("No courses are currently available.");
			return;
		}
		
		//if not empty, goes through each course in the list and prints it
		System.out.println("All available courses: ");
		
		//calls the toString method to print each course
		for (Course course : courses.values()) {
			System.out.println(course);
		}	
		
	}
	
	/**
	 * Adds a new course to the system
	 * @param courses map of existing courses
	 * @param course to add
	 */
	public void addCourse(Map<String, Course> courses, Map<String, Professor> professors, Course course, Scanner scanner) {
		
		//checks if course map is null
		if (courses == null || professors == null || course == null || scanner == null) {
			throw new IllegalArgumentException("Courses map or course cannot be null.");
		}

         //extracts the lecturer's name from the course
        String lecturerName = course.getLecturer();
        String professorId = null;

        //checks if the professor exists in the map by matching the name
        for (Map.Entry<String, Professor> entry : professors.entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(lecturerName)) {
                professorId = entry.getKey(); // Get the professor's ID
                break;
            }
        }

        //if the professor doesn't exist, prompt the admin to add them
        if (professorId == null) {
            String newProfId;

            //keep prompting until a unique professor ID is entered
            while (true) {
                System.out.println("This professor isn't in the system. Please enter a unique ID: ");
                newProfId = scanner.nextLine().trim();

                //check if ID already exists
                if (professors.containsKey(newProfId)) {
                    System.out.println("Professor ID " + newProfId + " already exists.");
                } else {
                    break;
                }
            }

            System.out.println("Professor's name: ");
            String newProfName = scanner.nextLine().trim();

            System.out.println("Professor's username: ");
            String newProfUsername = scanner.nextLine().trim();

            System.out.println("Professor's password: ");
            String newProfPassword = scanner.nextLine().trim();

            //create a new Professor object and add professor to map
            Professor newProfessor = new Professor(newProfId, newProfName, newProfUsername, newProfPassword);
            professors.put(newProfId, newProfessor);
            System.out.println("Successfully added new professor: " + newProfName);

            //sets the ID for the new professor
            professorId = newProfId;
        }		
		
		//checks if course already exists in map
		if (courses.containsKey(course.getId())) {
            
            //if course exists, prints an error message
			System.out.println("Course ID " + course.getId() + " already exists.");
			return;
		}
		
		//checks for time conflicts with the lecturer's existing courses
		for (Course existingCourse : courses.values()) {
			
			//finds the lecturer and checks each course for time conflicts
			if (existingCourse.getLecturer().equals(course.getLecturer())) {

                //if time conflict exists, prints an error message
				if (existingCourse.hasTimeConflict(course)) {
					System.out.println("The new course has a time conflict with an existing course.");
					return;
				}
			}
		}
			
		//if course doesn't exist, adds it to the map	
		courses.put(course.getId(), course);
		System.out.println("Course ID " + course.getId() + " added successfully.");				
		
	}
	
	/**
	 * Deletes a course from the system
	 * @param courses map of existing courses
	 * @param courseId to remove
	 */
	public void deleteCourse(Map<String, Course> courses, String courseId) {
		
		//checks if course map or course ID is null
		if (courses == null || courseId == null || courseId.trim().isEmpty()) {
			throw new IllegalArgumentException("Courses map or course ID cannot be null or empty.");
		}
		
		//checks if course exists in the map and can be deleted
		if (courses.remove(courseId) != null) {		
			System.out.println("Course ID " + courseId + " has been deleted.");
		
		//prints an error message if course doesn't exist
		} else {
			System.out.println("Course ID " + courseId + " is not found.");
		}
			
	}
	
	/**
	 * Adds a new professor to the system
	 * @param professors map of existing professors
	 * @param professor to add
	 * @return true if professor is added successfully, false if not
	 */
	public boolean addProfessor(Map<String, Professor> professors, Professor professor) {
		
		//checks if professors map is null
		if (professors == null || professor == null) {
			throw new IllegalArgumentException("Professors map or professor cannot be null.");
		}
		
		//checks if professor is already on the list
		if (professors.containsKey(professor.getId())) {
			
			//prints a message if professor already exists and return false
			System.out.println("The professor already exists.");
			return false;
			
		}
		
		//otherwise, successfully adds the professor to the map
		professors.put(professor.getId(), professor);
		System.out.println("Professor added successfully.");
		return true;		
		
	}
	
	/**
	 * Deletes a professor from the system
	 * @param professors map of existing professors
	 * @param professorId to delete
	 */
	public void deleteProfessor(Map<String, Professor> professors, String professorId) {
		
		//checks if professors map or professor ID is null
		if (professors == null || professorId == null || professorId.trim().isEmpty()) {
			throw new IllegalArgumentException("Professors map or professor ID cannot be null or empty.");
		}
		
		//checks if professor exists on the map and can be deleted
		if (professors.remove(professorId) != null) {			
			System.out.println("Professor deleted successfully.");
			
		//prints an error message if professor does not exist
		} else {
			System.out.println("No professor found.");
		}	
		
	}	
	
	/**
	 * Adds a new student to the system
	 * @param students map of existing students
	 * @param student to add
	 * @return true if student is added successfully, false if not
	 */
	public boolean addStudent(Map<String, Student> students, Student student) {
		
		//checks if students map is null
		if (students == null || student == null) {
			throw new IllegalArgumentException("Students map or student cannot be null.");
		}
		
		//checks if student is already on the list
		if (students.containsKey(student.getId())) {
			
			//prints a message if student already exists and returns false
			System.out.println("The student already exists.");
			return false;
			
		}
		
		//otherwise, successfully adds the student to the map
		students.put(student.getId(), student);
		System.out.println("Student added successfully.");
		return true;	
		
	}
	
	/**
	 * Deletes a student from the system
	 * @param students map of existing students
	 * @param studentId to delete
	 */
	public void deleteStudent(Map<String, Student> students, String studentId) {
		
		//checks if students map or student ID is null
		if (students == null || studentId == null || studentId.trim().isEmpty()) {
			throw new IllegalArgumentException("Students map or student ID cannot be null or empty.");
		}
		
		//checks if student exists on the map and can be deleted
		if (students.remove(studentId) != null) {			
			System.out.println("Student deleted successfully.");
			
		//prints an error message if student does not exist
		} else {
			System.out.println("No student found.");
		}		
		
	}
	
	/**
	 * Displays the admin-specific menu
	 */
	@Override
	public void displayMenu() {		
		
		System.out.println();
		System.out.println("1 -- View all courses");
		System.out.println("2 -- Add new courses");
		System.out.println("3 -- Delete courses");
		System.out.println("4 -- Add new professor");
		System.out.println("5 -- Delete professor");
		System.out.println("6 -- Add new student");
		System.out.println("7 -- Delete student");
		System.out.println("8 -- Return to previous menu");
		
	}
	
}
