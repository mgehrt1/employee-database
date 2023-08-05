
public class Employee implements EmployeeInterface, Comparable<EmployeeInterface> {

	private String employeeName;
	private String employeeJobTitle;
	private int employeeSalary;
	private int employeeCommencementDate; // stored in the form year/month/day in a single integer,
	// for example, 20021115 stands for November 15th 2002

	public int employeeID;  

	/**
	 * Constructor for employee object 
	 * @param name
	 * @param jobTitle
	 * @param salary
	 * @param commencementDate
	 */
	public Employee(String name, String jobTitle, int salary, int commencementDate) {
		this.employeeName = name;
		this.employeeJobTitle = jobTitle;
		this.employeeSalary = salary;
		this.employeeCommencementDate = commencementDate;

	}

	/**
	 * getter method for the employeeID
	 * @return the employee's ID
	 */
	@Override
	public int getEmployeeID() {
		// TODO Auto-generated method stub
		return this.employeeCommencementDate;
	}

	/**
	 * getter method for the employeeCommencementDate
	 * @return the employee's commencement date
	 */
	@Override
	public int getCommencementDate() {
		// TODO Auto-generated method stub
		return this.employeeCommencementDate;
	}

	/**
	 * getter method for the employeeSalary
	 * @return the employee's salary
	 */
	@Override
	public int getSalary() {
		// TODO Auto-generated method stub
		return this.employeeSalary;
	}

	/**
	 * getter method for the employeeJobTitle
	 * @return the employee's job title
	 */
	@Override
	public String getJobTitle() {
		// TODO Auto-generated method stub
		return this.employeeJobTitle;
	}

	/**
	 * getter method for the employeeName
	 * @return the employee's name
	 */
	@Override
	public String getEmployeeName() {
		// TODO Auto-generated method stub
		return this.employeeName;
	}

	/**
	 * setter method for the employeeSalary
	 */
	@Override
	public void setSalary(int newSalary) {
		// TODO Auto-generated method stub
		this.employeeSalary = newSalary;
	}

	/**
	 * setter method for the employeeJobTitle
	 */
	@Override
	public void setJobTitle(String newJobTitle) {
		// TODO Auto-generated method stub
		this.employeeJobTitle = newJobTitle;
	}

	/**
	 * method that compares two employees based on their employeeID. The employeeID consists of their
	 * commencement date and and employee count. The method compares the year, month, and day of commencement
	 * along with the employee count at the end to determine whether the employee joined sooner or later
	 * than the other employee
	 * @return The method returns a negative integer if the calling object (this) is less than the argument object, 
	 * zero if the calling object is equal to the argument object, and a positive integer if the 
	 * calling object is greater than the argument object.
	 */
	@Override
	public int compareTo(EmployeeInterface other) {
		int result;
		// Compare year
		int thisYear = this.employeeCommencementDate / 10000;
		int otherYear = other.getCommencementDate() / 10000;
		result = Integer.compare(thisYear, otherYear);
		if (result != 0) {
			return result;
		}
		// Compare month
		int thisMonth = (this.employeeCommencementDate / 100) % 100;
		int otherMonth = (other.getCommencementDate() / 100) % 100;
		result = Integer.compare(thisMonth, otherMonth);
		if (result != 0) {
			return result;
		}
		// Compare day
		int thisDay = this.employeeCommencementDate % 100;
		int otherDay = other.getCommencementDate() % 100;
		result = Integer.compare(thisDay, otherDay);
		if (result != 0) {
			return result;
		}

		return 0;
	}

	

	


}

