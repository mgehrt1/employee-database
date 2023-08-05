import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

class DataWranglerTests {

	/**
	 * test to check if file can be loaded without errors
	 */
	@Test
	void test1() {
		EmployeeReader e = new EmployeeReader();
		try {
			List<EmployeeInterface> employees = e.readEmployeesFromFile("Employee.txt");
			

		} catch (FileNotFoundException e1) {
			// if error is thrown, file is not being loaded correctly
		    fail("exception is thrown");
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * test to check if employees are loaded correctly from the file into a list of employees
	 */
	@Test
	void test2() {
		EmployeeReader e = new EmployeeReader();
		try {
			List<EmployeeInterface> employees = e.readEmployeesFromFile("Employee.txt");
			// ensuring all the employees are loaded in the correct order
			assertEquals("employee1", employees.get(0).getEmployeeName(),"Employee name is incorrect"); 
			assertEquals("employee2", employees.get(1).getEmployeeName(),"Employee name is incorrect"); 
			assertEquals("employee3", employees.get(2).getEmployeeName(),"Employee name is incorrect"); 


			
		} catch (FileNotFoundException e1) {
		    fail("exception is thrown");
			e1.printStackTrace();
		}
	}
	/**
	 * testing getter methods 
	 */
	@Test
	void test3() {
		EmployeeReader e = new EmployeeReader();
		try {
			// checking if all the getter methods return the correct values based on the input file
			List<EmployeeInterface> employees = e.readEmployeesFromFile("Employee.txt");
			assertEquals("employee1", employees.get(0).getEmployeeName(),"Employee name is incorrect"); 
			assertEquals("software engineer", employees.get(0).getJobTitle(),"Employee job title is incorrect"); 
			assertEquals(100000, employees.get(0).getSalary(),"Employee salary is incorrect");
			assertEquals(20210101, employees.get(0).getCommencementDate(),"Employee commencement date is incorrect");

			
		} catch (FileNotFoundException e1) { 
		    fail("exception is thrown");
			e1.printStackTrace();
		}
	}
	
	/**
	 * testing setter methods 
	 */
	@Test
	void test4() {
		EmployeeReader e = new EmployeeReader();
		try {
			// testing if the setter methods work
			List<EmployeeInterface> employees = e.readEmployeesFromFile("Employee.txt");
			employees.get(0).setJobTitle("data wrangler");
			assertEquals("data wrangler", employees.get(0).getJobTitle(),"Employee job title is incorrect"); 
			
			employees.get(0).setSalary(110000);
			assertEquals(110000, employees.get(0).getSalary(),"Employee salary is incorrect");
			
		} catch (FileNotFoundException e1) {
		    fail("exception is thrown");
			e1.printStackTrace();
		}
	}
	
	/**
	 * testing compareTo method 
	 */
	@Test
	void test5() {
		EmployeeReader e = new EmployeeReader();
		try {
			List<EmployeeInterface> employees = e.readEmployeesFromFile("Employee.txt");
			// testing whether the compareTo() method outputs 1 when this employee joined after the other employee,
			// and -1 in the other case
			assertEquals(-1, employees.get(0).compareTo((Employee) employees.get(1)),"CompareTo() is incorrect");
			assertEquals(-1, employees.get(0).compareTo((Employee) employees.get(2)),"CompareTo() is incorrect");
			assertEquals(1, employees.get(2).compareTo((Employee) employees.get(1)),"CompareTo() is incorrect");

			
		} catch (FileNotFoundException e1) {
		    fail("exception is thrown");
			e1.printStackTrace();
		}
	}
	
	
	}





