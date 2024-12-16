package courses;

import java.util.ArrayList;
import java.util.List;

/**
* Represents a Course in the system
* Courses are composed of 7 components: Course ID, Course Name, Lecturer, Days, Start Time, End Time, Capacity
* @author Katie
*/
public class Course {
	
	//instance variables
	
	/**
	 * Unique course ID
	 */
	private String id;
	
	/**
	 * Name of course
	 */
	private String name;
	
	/**
	 * Name of lecturer
	 */
	private String lecturer;
	
	/**
	 * Days the course is held
	 */
	private String days;
	
	/**
	 * Time course starts
	 */
	private String startTime;
	
	/**
	 * Time course ends
	 */
	private String endTime;
	
	/**
	 * Maximum number of students in the course
	 */
	private int capacity;
	
	/**
	 * List of enrolled students
	 */
	private List<String> enrolledStudents;
	
	
	//constructor
	
	/**
	 * Creates a Course object with each component
	 * @param id of course
	 * @param name of course
	 * @param lecturer of course
	 * @param days course is held
	 * @param startTime of course
	 * @param endTime of course
	 * @param capacity of course
	 */
	public Course(String id, String name, String lecturer, String days, String startTime, String endTime, int capacity) {
		
		setId(id);
		setName(name);
		setLecturer(lecturer);
		setDays(days);
		setStartTime(startTime);
		setEndTime(endTime);
		setCapacity(capacity);

        //initializes array list for enrolled students
		this.enrolledStudents = new ArrayList<>();
	
	}


	//getters and setters
	
	/**
	 * Gets course ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets course ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets name of course
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name of course
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets lecturer of course
	 */
	public String getLecturer() {
		return lecturer;
	}
	
	/**
	 * Sets lecturer of course
	 */
	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	/**
	 * Gets days course is held
	 */
	public String getDays() {
		return days;
	}

	/**
	 * Sets days course is held
	 */
	public void setDays(String days) {
		this.days = days;
	}
	
	/**
	 * Gets course's start time
	 */
	public String getStartTime() {
		return startTime;
	}
	
	/**
	 * Sets course's start time
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets course's end time
	 */
	public String getEndTime() {
		return endTime;
	}
	
	/**
	 * Sets course's end time
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets capacity of course
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Sets capacity of course
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Gets enrolled students in course
	 */
	public List<String> getEnrolledStudents() {
		return enrolledStudents;
	}
	
	
	//helper methods
	
	/**
	 * Checks if the course is full
	 * @return true if course is full, false if not
	 */
	private boolean isFull() {
		return enrolledStudents.size() >= capacity;
	}
	
	/**
	 * Checks if a course overlaps days with another course
	 * @param otherDays the other course is held
	 * @return true if days of both courses overlap, false if not
	 */
	private boolean daysOverlap(String otherDays) {
		
		//iterates through each day each course is offered
		for (char day : this.days.toCharArray()) {
			
			//if both courses overlap days, return true
			if (otherDays.indexOf(day) != -1) {
				return true;
			}
		}
		
		return false;
		
	}	
	
	/**
	 * Parses a time string (i.e. 10:30) into an integer (i.e. 1030)
	 * @param time string to parse
	 * @return time as an integer
	 */
	private int parseTime(String time) {
		
		//splits the time into components based on the : separator
		String[] parts = time.split(":");
		
		//returns the time as a combined integer
		return Integer.parseInt(parts[0]) * 100 + Integer.parseInt(parts[1]);
		
	}
	
		
	//methods
	
	/**
	 * Adds a student to the course
	 * @param studentId of student to add
	 * @return true if student was added, false if not
	 */
	public boolean addStudent(String studentId) {
		
		//checks if student ID is null or empty
		if (studentId == null || studentId.trim().isEmpty()) {
			
			//prints error message if student ID is null or empty
			throw new IllegalArgumentException("Student ID cannot be null or empty.");
			
		}
		
		//checks if course is full
		if (isFull()) {
			
			//prints error message if course is full and student cannot be added
			System.out.println("Cannot add student " + studentId + " to course. Course is already full.");
			return false;
			
		}
		
		//checks if student is already enrolled in course
		if (enrolledStudents.contains(studentId)) {
			
			//prints error message if student is already enrolled and cannot be added again
			System.out.println("Cannot add student " + studentId + " to course. Student is already enrolled.");
			return false;
		
		}
		
		//if course is not full and student is not already enrolled, successfully enrolls student in course
		enrolledStudents.add(studentId);
		return true;	
		
	}
	
	/**
	 * Removes a student from the course
	 * @param studentId of student to remove
	 * @return true if student was removed, false if not
	 */
	public boolean removeStudent(String studentId) {
		
		//checks if student ID is null or empty
		if (studentId == null || studentId.trim().isEmpty()) {
			
			//prints error message if student ID is null or empty
			throw new IllegalArgumentException("Student ID cannot be null or empty.");
			
		}
		
		//checks if student is enrolled and can be removed
		if (enrolledStudents.remove(studentId)) {			
			return true;
			
		} else {
			
			//returns false if student is not enrolled in the course and can't be removed
			System.out.println("Cannot remove student " + studentId + " from course. Student is not enrolled.");
			return false;
			
		}		
		
	}
	
	/**
	 * Checks if a course has a time conflict with another course
	 * @param other course to check
	 * @return true if time conflict exists, false if not
	 */
	public boolean hasTimeConflict(Course other) {
		
		//checks if both courses are held on overlapping days
		if (!daysOverlap(other.getDays())) {
			
			//if courses don't overlap days, a time conflict cannot exist
			return false;
			
		}
		
		//converts times to comparable integers (i.e. "10:30" -> 1030)
		int thisStart = parseTime(this.startTime);
		int thisEnd = parseTime(this.endTime);
		int otherStart = parseTime(other.startTime);
		int otherEnd = parseTime(other.endTime);
		
		//check for time overlap
		return (thisStart < otherEnd && thisEnd > otherStart);
		
	}
		
	/**
	 * Returns the course as a string
	 */
	@Override
	public String toString() {
		
		return id + ": " + name + ", Professor " + lecturer + ", " + days +
				" from " + startTime + " to " + endTime +
				" (Capacity: " + enrolledStudents.size() + "/" + capacity + ")";
		
	}	

}
