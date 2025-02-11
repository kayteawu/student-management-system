package roles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import courses.Course;

/**
 * Represents a Professor user in the Student Management System
 * Professors can view their courses and enrolled students
 * @author Katie
 */
public class Professor extends User {
	
	//instance variables
	
	//initialize list of courses professor teaches
	private List<Course> coursesTaught;
	
	
	//constructor
	
	/**
	 * Creates a new Professor user using superclass User constructor
	 * @param id of professor
	 * @param name of professor
	 * @param username for professor's login
	 * @param password for professor's login
	 */
	public Professor(String id, String name, String username, String password) {
		
		//calls the superclass constructor
		super(id, name, username, password);

        //initializes array list for courses taught
		this.coursesTaught = new ArrayList<>();		
		
	}
	
	
	//getters and setters
	
	/**
	 * Gets the list of courses taught by the professor
	 */
	public List<Course> getCoursesTaught() {
		return coursesTaught;
	}
	
	
	//methods
	
	/**
	 * Adds a course to the professor's teaching list
	 * @param course to add
	 */
	public void addCourse(Course course) {
		
		//checks if course is null
		if (course == null) {
			throw new IllegalArgumentException("Course cannot be null.");
		}
		
		//if list doesn't contain given course, adds given course to teaching list
		if (!coursesTaught.contains(course)) {
			coursesTaught.add(course);
			System.out.println("Course added to teaching list.");
		
		//otherwise, prints a message alerting user that the course is already on the list
		} else {
			System.out.println("You are already teaching this course.");
		}
		
	}
	
	/**
	 * Displays all courses taught by the professor
	 */
	public void viewGivenCourses(Map<String, Course> courses) {
		
		//initializes boolean variable indicating whether a course is found
		boolean found = false;
		
		//iterates through each course in the map
		for (Course course : courses.values()) {
			
			//if a course is found with a matching lecturer ID, prints the course
			if (course.getLecturer().equals(this.getName())) {
				System.out.println(course);
				found = true;
			}
		}
		
		//if no matching courses are found, prints a message
		if (!found) {
			System.out.println("You are not currently teaching any courses.");
		}
		
	}
	
	/**
	 * Displays list of students enrolled in a given course
	 * @param course that students are enrolled in
	 */
	public void viewStudentList(Course course, Map<String, Student> students) {
		
		//checks if course is null
		if (course == null) {
			System.out.println("Course cannot be null.");
			return;
		}
		
		//checks if professor teaches the given course
		if (!course.getLecturer().equals(this.getName())) {
			
			//prints a message if professor doesn't teach the course
			System.out.println("You do not teach " + course.getName());
			return;
			
		}
		
		//initializes a list to store student info
		List<String> enrolledStudents = course.getEnrolledStudents();
		
		//otherwise, goes through the course's enrollment list and prints student IDs
		System.out.println("Students enrolled in " + course.getName() + ":");		
		
		//checks if list of enrolled students is empty
		if (enrolledStudents.isEmpty()) {
			System.out.println("No students are currently enrolled in this course.");
		} else {
			
			//if not empty, iterates through list of enrolled students
			for (String studentId : enrolledStudents) {
				
				//retrieves the student using the student ID
				Student student = students.get(studentId);
				
				//if student exists, prints the student's ID and name
				if (student != null) {
					System.out.println(studentId + " " + student.getName());
					
				//otherwise, prints a message if student does not exist
				} else {
					System.out.println("Student not found.");
				}
				
			}
			
		}
		
	}
	
	/**
	 * Displays the professor-specific menu
	 */
	@Override
	public void displayMenu() {		
		
		System.out.println();
		System.out.println("1 -- View given courses");
		System.out.println("2 -- View student list of the given courses");
		System.out.println("3 -- Return to the previous menu");
		
	}

}
