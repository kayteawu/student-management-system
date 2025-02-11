/**
 * Group name: Katie Wu
 * Penn ID: 62427079
 * Resources used: Course lectures, W3 Schools
 * Statement: I worked alone on this project.
 */
import java.util.HashMap;

import java.util.Map;
import java.util.Scanner;

import courses.Course;
import files.FileInfoReader;
import roles.Admin;
import roles.Professor;
import roles.Student;

public class Controller {

	public static void main (String[] args) {
		
		//initialize data into the system
		Map<String, Course> courses = new HashMap<>();
		Map<String, Admin> admins = new HashMap<>();
		Map<String, Professor> professors = new HashMap<>();
		Map<String, Student> students = new HashMap<>();
		
		//create FileInfoReader instance
		FileInfoReader fileReader = new FileInfoReader();
		
		//load data from files		
		fileReader.loadCourses("courseInfo.txt", courses);
		fileReader.loadAdmins("adminInfo.txt", admins);
		fileReader.loadProfessors("profInfo.txt", professors);
		fileReader.loadStudents("studentInfo.txt", students);		
		
		//launch the main menu
		Scanner scanner = new Scanner(System.in);
		handleMainMenu(admins, courses, professors, students, scanner);

	}
	
	/**
	 * Handles the main menu where users can log in as an Admin, Professor, or Student
	 * @param courses map of existing courses
	 * @param professors map of existing professors
	 * @param students map of existing students
	 * @param scanner for user input
	 */
	private static void handleMainMenu(Map<String, Admin> admins, Map<String, Course> courses, Map<String, Professor> professors, Map<String, Student> students, Scanner scanner) {
		
		while (true) {
			
			//displays the main menu
			System.out.println();
			System.out.println("-------------------------");
			System.out.println("Student Management System");
			System.out.println("-------------------------");
			System.out.println();
			System.out.println("1 -- Login as a student");
			System.out.println("2 -- Login as a professor");
			System.out.println("3 -- Login as an admin");
			System.out.println("4 -- Quit the system");
			System.out.println();
			System.out.println("Please enter your option, eg. '1': ");
			
			//prompts user to enter a choice
			int choice;
			
			//verifies that input is an integer between 1-4
			try {
				choice = Integer.parseInt(scanner.nextLine());
				
			//if not, reprompts user for a valid input
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number between 1 and 4.");
				continue;
			}			
			
			//uses switch statement to match user's choice to one of the available options
			switch (choice) {
			
				//if user enters '1', brings up the student menu
				case 1:
					handleStudentLogin(courses, students, scanner);
					break;
					
				//if user enters '2', brings up the professor menu
				case 2:
					handleProfessorLogin(courses, professors, students, scanner);
					break;
					
				//if user enters '3', brings up the admin menu
				case 3:
					handleAdminLogin(admins, courses, professors, students, scanner);
					break;
					
				//if user enters '4', prints a message and exits the system
				case 4:
					System.out.println("Exiting the system. Goodbye!");
					return;
				
				//if user enters anything other than 1-4, prompts for a valid input
				default:
					System.out.println("Invalid input. Please try again.");			
			
			}			
			
		}	
		
	}
	
	/**
	 * Handles the Student login where users can view course info, add/drop courses, and view grades
	 * @param courses map of courses
	 * @param professors map of professors
	 * @param students map of students
	 * @param scanner for user input
	 */
	private static void handleStudentLogin(Map<String, Course> courses, Map<String, Student> students, Scanner scanner) {
		
		//prompts user for username
		System.out.println("Please enter your username, or type 'q' to quit.");
		String username = scanner.nextLine().trim();
				
		//quits if user enters "q"
		if ("q".equalsIgnoreCase(username)) {
			return;
		}
		
		//prompts user for password
		System.out.println("Please enter your password, or type 'q' to quit.");
		String password = scanner.nextLine().trim();
		
		//quits if user enters "q"
		if ("q".equalsIgnoreCase(password)) {
			return;
		}
		
		//iterates through the list of existing students to authenticate the login
		for (Student student : students.values()) {
			
			//if authenticated, prints a welcome message for the student
			if (student.authenticate(username, password)) {
				System.out.println();
				System.out.println("-------------------------");
				System.out.println("Welcome, " + student.getName() + ":");
				System.out.println("-------------------------");
				handleStudentActions(student, courses, scanner);
				return;
			}
		}
		
		//if login cannot be authenticated, prompts user to retry username and password
		System.out.println("Invalid student credentials. Please try again.");		
		
	}
	
	/**
	 * Handles the different menu actions from the student menu
	 * @param student logged in
	 * @param courses map of existing courses
	 * @param scanner for user input
	 */
	private static void handleStudentActions(Student student, Map<String, Course> courses, Scanner scanner) {
		
		while (true) {
			
			//prompts user to select a choice from the student menu
			student.displayMenu();
			System.out.println();
			System.out.println("Please enter your option, eg. '1': ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			//uses switch statement to match user's input to one of the available options
			switch (choice) {
			
				//if user enters '1', shows them the available courses
				case 1:
					student.viewAllCourses(courses);
					break;
				
				//if user enters '2', allows them to add a course to their list
				case 2:
					
					System.out.println("Please select the course ID you want to add to your list, eg. 'CIT590'."
							+ "\nOr enter 'q' to return to the previous menu.");
					
					//stores user's input into a String and matches the course by ID
					String addCourseId = scanner.nextLine();
					
					//quits if user enters "q"
					if ("q".equalsIgnoreCase(addCourseId)) break;
					
					//if course ID is found, adds the course to student's list
					if (courses.containsKey(addCourseId)) {
						student.addCourse(courses.get(addCourseId));
						
					//if course ID is not found, prints an error message
					} else {
						System.out.println("Course " + addCourseId + " not found.");
					}					
					break;
					
				//if user enters '3', shows them their enrolled courses
				case 3:
					student.viewEnrolledCourses();
					break;
					
				//if user enters '4', allows them to drop a course from their list
				case 4:
					
					System.out.println("Please enter the ID of the course which you want to drop,  eg. 'CIT591'."
							+ "\nOr enter 'q' to return to the previous menu.");
					
					//stores user's input into a String and matches the course by ID
					String dropCourseId = scanner.nextLine();
					
					//quits if user enters "q"
					if ("q".equalsIgnoreCase(dropCourseId)) break;
					
					//if course ID is found, drops the course from the student's list
					if (courses.containsKey(dropCourseId)) {
						student.dropCourse(courses.get(dropCourseId));
						
					//if course ID is not found, prints an error message
					} else {
						System.out.println("Course " + dropCourseId + " not found.");
					}					
					break;
					
				//if user enters '5', shows them the courses they've taken along with their grades
				case 5:
					student.viewGrades();
					break;
					
				//if user enters '6', returns to the main menu
				case 6:
					System.out.println("Returning to the main menu.");
					return;
					
				//if user enters anything else, prompts for a valid input
				default:
					System.out.println("Invalid input. Please try again.");
			
			}			
			
		}
		
	}
	
	/**
	 * Handles the professor login where users can view course info or view student list
	 * @param courses map of existing courses
	 * @param professors map of existing professors
	 * @param students map of existing students
	 * @param scanner for user input
	 */
	private static void handleProfessorLogin(Map<String, Course> courses, Map<String, Professor> professors, Map<String, Student> students, Scanner scanner) {
		
		//prompts user for username
		System.out.println("Please enter your username, or type 'q' to quit.");
		String username = scanner.nextLine().trim();
		
		//quits if user enters "q"
		if ("q".equalsIgnoreCase(username)) {
			return;
		}
		
		//prompts user for password
		System.out.println("Please enter your password, or type 'q' to quit.");
		String password = scanner.nextLine().trim();
		
		//quits if user enters "q"
		if ("q".equalsIgnoreCase(password)) {
			return;
		}
		
		//iterates through the list of existing students to authenticate the login
		for (Professor professor : professors.values()) {
			
			//if authenticated, prints a welcome message for the professor
			if (professor.authenticate(username, password)) {
				System.out.println();
				System.out.println("-------------------------");
				System.out.println("Welcome, " + professor.getName() + ":");
				System.out.println("-------------------------");
				handleProfessorActions(professor, courses, students, scanner);
				return;
				
			}
			
		}
		
		//if login cannot be authenticated, prompts user to retry username and password
		System.out.println("Invalid professor credentials. Please try again.");
			
	}
	
	
	private static void handleProfessorActions(Professor professor, Map<String, Course> courses, Map<String, Student> students, Scanner scanner) {
		
		while (true) {
			
			//prompts user to select a choice from the professor menu
			professor.displayMenu();
			System.out.println();
			System.out.println("Please enter your option, eg. '1': ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			//uses switch statement to match user's input to one of the available options
			switch (choice) {
			
				//if user enters '1', shows them their courses
				case 1:
					professor.viewGivenCourses(courses);
					break;
					
				//if user enters '2', allows them to view the list of students for their course
				case 2:
				
					System.out.println("Please enter the course ID to view students."
							+ "\nOr enter 'q' to return to the previous menu.");
					
					//stores user's input into a String and matches the course by ID
					String courseId = scanner.nextLine().trim();
					Course course = courses.get(courseId);
					
					//quits if user enters "q"
					if ("q".equalsIgnoreCase(courseId)) break;
					
					//checks if course is null
					if (course == null) {
						System.out.println("Course not found. Please check the course ID.");
						
					//checks if professor is teaching the course
					} else if (!course.getLecturer().equals(professor.getName())) {
						System.out.println("You are not teaching this course.");
						
					//otherwise, shows the student list for the course
					} else {
						professor.viewStudentList(course, students);
					}
					
				//if user enters '3', returns to the main menu
				case 3:					
					System.out.println("Returning to the main menu.");
					return;
					
				//if user enters anything else, prompts for a valid input
				default:
					System.out.println("Invalid input. Please try again.");
		
			}
		}
	}	
	
	/**
	 * Handles the Admin login where users can modify course, student, and professor info
	 * @param courses map of existing courses
	 * @param professors map of existing professors
	 * @param students map of existing students
	 * @param scanner for user input
	 */
	private static void handleAdminLogin(Map<String, Admin> admins, Map<String, Course> courses, Map<String, Professor> professors, Map<String, Student> students, Scanner scanner) {
		
		//prompts user for username
		System.out.println("Please enter your username, or type 'q' to quit.");
		String username = scanner.nextLine().trim();
		
		//quits if user enters "q"
		if ("q".equalsIgnoreCase(username)) {
			return;
		}
				
		//prompts user for password
		System.out.println("Please enter your password, or type 'q' to quit.");
		String password = scanner.nextLine().trim();
		
		//quits if user enters "q"
		if ("q".equalsIgnoreCase(password)) {
			return;
		}
		
		//retrieves admin data from map
		Admin admin = admins.get(username);
		
		//authenticates admin login
		if (admin.authenticate(username, password)) {
			System.out.println();
			System.out.println("-------------------------");
			System.out.println("Welcome, " + admin.getName() + ":");
			System.out.println("-------------------------");
			handleAdminActions(admin, courses, professors, students, scanner);
			
		//otherwise, prints an error message if login can't be authenticated
		} else {
			System.out.println("Invalid admin credentials. Please try again.");
		}		
		
	}	
	
	/**
	 * Handles admin-specific menu
	 * @param admin
	 * @param courses
	 * @param professors
	 * @param students
	 * @param scanner
	 */
	private static void handleAdminActions(Admin admin, Map<String, Course> courses, Map<String, Professor> professors, Map<String, Student> students, Scanner scanner) {
		
		while (true) {
			
			//prompts user to select a choice from the admin menu
			admin.displayMenu();
			System.out.println();
			System.out.println("Please enter your option, eg. '1': ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			//uses switch statement to match user's input to one of the available options
			switch (choice) {
			
				//if user enters '1', shows them all courses
				case 1:
					admin.viewAllCourses(courses);
					break;
					
				//if user enters '2', allows them to add a course to the system
				case 2:
				
					//input course ID
					System.out.println("Enter the course ID of the course you want to add: ");
					String courseId = scanner.nextLine();
					
					//checks if the course already exists and exits the loop if it does
					if (courses.containsKey(courseId)) {
						System.out.println("This course already exists.");
						continue;
					}
					
					//input course name
					System.out.println("Course name: ");
					String courseName = scanner.nextLine();
					
					//input lecturer
					System.out.println("Lecturer: ");
					String lecturer = scanner.nextLine();
					
					//input days
					System.out.println("Days (e.g., MW): ");
					String days = scanner.nextLine();
					
					//input start time
					System.out.println("Start Time (HH:mm): ");
					String startTime = scanner.nextLine();
					
					//input end time
					System.out.println("End Time (HH:mm): ");
					String endTime = scanner.nextLine();
					
					//input capacity
					System.out.println("Capacity: ");
					int capacity = scanner.nextInt();
					scanner.nextLine();
					
					//creates a new Course object based on the info entered
					Course course = new Course(courseId, courseName, lecturer, days, startTime, endTime, capacity);
					admin.addCourse(courses, professors, course, scanner);
					break;
					
				//if user enters '3', allows them to delete a course from the system
				case 3:
					
					//prompts user for course ID to delete and stores it in a String
					System.out.println("Enter the course ID of the course you want to delete: ");
					String deleteCourseId = scanner.nextLine();
					admin.deleteCourse(courses, deleteCourseId);
					break;
					
				//if user enters '4', allows them to add a new professor to the system
				case 4:
					
					//input professor ID
					System.out.println("Enter the professor ID for the professor you want to add: ");
					String profId = scanner.nextLine();
					
					//checks if the professor already exists and exits the loop if it does
					if (professors.containsKey(profId)) {
						System.out.println("This professor already exists.");
						continue;
					}
					
					//input professor name
					System.out.println("Professor name: ");
					String profName = scanner.nextLine();
					
					//input professor username
					System.out.println("Professor username: ");
					String profUsername = scanner.nextLine();
					
					//input professor password
					System.out.println("Professor password: ");
					String profPassword = scanner.nextLine();
					
					//creates a new Professor object based on the info entered
					Professor professor = new Professor(profId, profName, profUsername, profPassword);
					admin.addProfessor(professors, professor);
					break;
					
				//if user enters '5', allows them to delete a professor from the system
				case 5:
					
					//prompts user for professor ID to delete and stores it in a String
					System.out.println("Enter the professor ID of the professor you want to delete: ");
					String deleteProfId = scanner.nextLine();
					admin.deleteProfessor(professors, deleteProfId);
					break;
					
				//if user enters '6', allows them to add a new student to the system
				case 6:
					
					//input student ID
					System.out.println("Enter the student ID for the student you want to add: ");
					String studentId = scanner.nextLine();
					
					//checks if the student already exists and exits the loop if it does
					if (students.containsKey(studentId)) {
						System.out.println("This student already exists.");
						continue;
					}
					
					//input student name
					System.out.println("Student name: ");
					String studentName = scanner.nextLine();
					
					//input student username
					System.out.println("Student username: ");
					String studentUsername = scanner.nextLine();
					
					//input student password
					System.out.println("Student password: ");
					String studentPassword = scanner.nextLine();
					
					//creates a new Student object based on the info entered
					Student student = new Student(studentId, studentName, studentUsername, studentPassword);
					admin.addStudent(students, student);
					break;
					
				//if user enters '7', allows them to delete a student from the system
				case 7:
					
					//prompts user for student ID to delete and stores it in a String
					System.out.println("Enter the student ID of the student you want to delete: ");
					String deleteStudentId = scanner.nextLine();
					admin.deleteStudent(students, deleteStudentId);
					break;
					
				//if user enters '8', returns to the main menu
				case 8:
					System.out.println("Returning to the main menu.");
					return;
					
				//if user enters anything else, prompts for a valid input
				default:
					System.out.println("Invalid input. Please try again.");					
					
			}
		}
	}
}
