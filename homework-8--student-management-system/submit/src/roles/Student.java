package roles;

import java.util.HashMap;
import java.util.Map;

import courses.Course;

/**
 * Represents a Student user in the Student Management System
 * Students can manage their enrolled courses and view grades
 * @author Katie
 */
public class Student extends User {
	
	//instance variables

	//initializes map of student's enrolled courses and grades
	private Map<Course, String> enrolledCourses;
	
	//initializes map of student's completed courses and grades
	private Map<String, String> completedCourses;
	
	
	//constructor
	
	/**
	 * Creates a new Student user using superclass User constructor
	 * @param id of student
	 * @param name of student
	 * @param username for student's login
	 * @param password for student's login
	 */
	public Student(String id, String name, String username, String password) {
		
		//calls the superclass constructor
		super(id, name, username, password);

        //initializes hashmaps for enrolled and completed courses
		enrolledCourses = new HashMap<>();
		completedCourses = new HashMap<>();
		
	}
	
	
	//getters and setters
	
	/**
	 * Gets enrolled courses
	 */
	public Map<Course, String> getEnrolledCourses() {
		return enrolledCourses;
	}

	/**
	 * Sets enrolled courses
	 */
	public void setEnrolledCourses(Map<Course, String> enrolledCourses) {
		this.enrolledCourses = enrolledCourses;
	}

	/**
	 * Gets completed courses
	 */
	public Map<String, String> getCompletedCourses() {
		return completedCourses;
	}

	/**
	 * Sets completed courses
	 */
	public void setCompletedCourses(Map<String, String> completedCourses) {
		this.completedCourses = completedCourses;
	}

	
	
	//methods
	
	/**
	 * Adds a completed course with the corresponding grade to the student's record
	 * @param courseId of the completed course
	 * @param grade achieved in the completed course
	 */
	public void addCompletedCourse(String courseId, String grade) {
		
		//check if course is already in completed courses
		if (completedCourses.containsKey(courseId)) {

            //if already completed, prints a message
			System.out.println(courseId + " is already marked as completed.");
			return;

		}
		
		//otherwise, adds the course and grade to the completed courses map
		completedCourses.put(courseId, grade);
		
	}			
	
	/**
	 * Displays all available courses in the system
	 * @param courses map of all courses
	 */
	public void viewAllCourses(Map<String, Course> courses) {
		
		//prints a message if there are no available courses for the student
		if (courses == null || courses.isEmpty()) {
			System.out.println("No courses are currently available.");
			return;
		}
		
		//otherwise, goes through all available courses and prints them
		System.out.println("Available Courses:");
		
		//calls the toString method to print each course
		for (Course course : courses.values()) {
			System.out.println(course);
		}	
		
	}
	
	
	/**
	 * Enrolls the student in a course
	 * @param course to add
	 * @return true if successfully added, false if not
	 */
	public boolean addCourse(Course course) {
		
		//checks if course is null and throws exception if yes
		if (course == null) {
			throw new IllegalArgumentException("Course cannot be null.");
		}		
		
		//checks if student is already enrolled in the course
		if (enrolledCourses.containsKey(course)) {
			
			//if student is already enrolled, prints a message alerting the user
			System.out.println("You are already enrolled in " + course.getName());
			return false;
			
		}
		
		//checks if student has already completed the course
		if (completedCourses.containsKey(course.getId())) {
			
			//if student has already completed the course, does not let them re-enroll
			System.out.println("You have already completed " + course.getName());
			return false;
			
		}
		
		//checks for time conflicts with already enrolled courses
		for (Course enrolledCourse : enrolledCourses.keySet()) {
					
			//if time conflict exists, does not allow student to add course
			if (enrolledCourse.hasTimeConflict(course)) {
				System.out.println("Time conflict. Cannot enroll in " + course.getName());
				return false;
				}
		}
		
		//checks if course is available
		if (course.addStudent(getId())) {
			
			//if available, successfully adds the course for the student
			enrolledCourses.put(course, "Not Graded");
			System.out.println("Course " + course.getName() + " added successfully.");
			return true;
			
		//if full or unavailable, prints a message to alert the student
		} else {
			System.out.println("Course is full or unavailable.");
			return false;
		}		
		
	}
	
	/**
	 * Displays all courses that student is currently enrolled in
	 */
	public void viewEnrolledCourses() {
		
		//checks if list of enrolled courses is empty
		if (enrolledCourses.isEmpty()) {

            //if list is empty, prints a message
			System.out.println("You are not enrolled in any courses.");
			return;
            
		}
		
		//otherwise, goes through all enrolled courses and prints them
		System.out.println("The courses in your list:");
		
		//calls the toString method to print each course
		for (Course course : enrolledCourses.keySet()) {
			System.out.println(course);
		}		
		
	}
	
	/**
	 * Drops the student from a course
	 * @param course to drop
	 * @return true if course is successfully dropped, false if not
	 */
	public boolean dropCourse(Course course) {
		
		//checks if course is null
		if (course == null) {
			throw new IllegalArgumentException("Course cannot be null.");
		}
		
		//checks if student is enrolled in the course
		if (!enrolledCourses.containsKey(course)) {
			
			//if not enrolled, prints a message and returns false
			System.out.println("You are not enrolled in " + course.getName());
			return false;
			
		}
		
		//if enrolled, successfully drops the course
		enrolledCourses.remove(course);
		course.removeStudent(getId());
		System.out.println("Course " + course.getName() + " dropped successfully.");
		return true;	
		
	}
	
	/**
	 * Displays the student's completed courses and corresponding grades
	 */
	public void viewGrades() {
		
		//checks if completed courses is empty
		if (completedCourses.isEmpty()) {
			System.out.println("You have not completed any courses.");
			return;
		}
		
		//otherwise, goes through each course in completedCourses and prints the course and grade
		System.out.println("Here are the courses you've already taken with your grade in letter format:");
		for (Map.Entry<String, String> entry : completedCourses.entrySet()) {
			System.out.println("Course ID: " + entry.getKey() + ", Grade: " + entry.getValue());
		}		
		
	}
	
	/**
	 * Displays the student-specific menu
	 */
	@Override
	public void displayMenu() {		
		
		System.out.println();
		System.out.println("1 -- View all courses");
		System.out.println("2 -- Add courses to your list");
		System.out.println("3 -- View enrolled courses");
		System.out.println("4 -- Drop courses in your list");
		System.out.println("5 -- View grades");
		System.out.println("6 -- Return to previous menu");
		
	}
	
	
}
