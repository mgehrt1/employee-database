import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class runs several tests to check the funcionality of the
 * EmployeeDatabaseFrontend class
 * 
 * @author Matthew Gehrt
 */
public class FrontendDeveloperTests {

  /**
   * Tests the main command loop and main menu prompt
   */
  @Test
  public void test1() {
    // use DW placeholder code to load employees
    EmployeeReaderInterface reader = new EmployeeReaderFD();
    // use AE placeholder code to make a RBT
    RedBlackTreeMultiInterface<EmployeeInterface> tree = new RedBlackTreeMultiFD<>();
    // use BD placeholder code for the backend
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendFD(tree, reader);
    // make a TextUITester to test system output
    TextUITester tester = new TextUITester("Q\n");
    Scanner scnr = new Scanner(System.in);
    // Create a frontend instance to test its methods on
    EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    String output = tester.checkOutput();
    scnr.close();

    // test that the command loop and main menu prompt display the correct text
    assertEquals(output, "-------------------------------------------------------------------------------"
        + System.lineSeparator() + "Welcome to the Employee Database Application!" + System.lineSeparator()
        + "-------------------------------------------------------------------------------" + System.lineSeparator()
        + "Choose a command by entering in the corresponding letter in brackets:" + System.lineSeparator()
        + "    [L]oad Business" + System.lineSeparator() + "    [A]dd Employee(s)" + System.lineSeparator()
        + "    [R]emove Employee(s)" + System.lineSeparator() + "    Change [J]ob" + System.lineSeparator()
        + "    Change [S]alary" + System.lineSeparator() + "    Display [E]mployee" + System.lineSeparator()
        + "    [G]et Employees by Months With Company" + System.lineSeparator() + "    [D]isplay Statistics"
        + System.lineSeparator() + "    [Q]uit" + System.lineSeparator() + "Choose command: "
        + "-------------------------------------------------------------------------------" + System.lineSeparator()
        + "Thank you for using the Employee Database App!" + System.lineSeparator()
        + "-------------------------------------------------------------------------------"
        + System.lineSeparator());
  }

  /**
   * Tests adding employees
   */
  @Test
  public void test2() {
    // use DW placeholder code to load employees
    EmployeeReaderInterface reader = new EmployeeReaderFD();
    // use AE placeholder code to make a RBT
    RedBlackTreeMultiInterface<EmployeeInterface> tree = new RedBlackTreeMultiFD<>();
    // use BD placeholder code for the backend
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendFD(tree, reader);
    // make a TextUITester to test system output
    TextUITester tester = new TextUITester(
        "A\nJohn Smith\nSWE\n10\n20230325\nC\nTed Johnson\nHR\n10\n20230326\nA\nQ\n");
    Scanner scnr = new Scanner(System.in);
    // Create a frontend instance to test its methods on
    EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    String output = tester.checkOutput();
    scnr.close();

    // case with correctly adding employees
    // check that the proper output sequence was output to the console
    assertTrue(output.contains("List of employees to add: []" + System.lineSeparator() + "Employee's Name: "
        + System.lineSeparator() + "Employee's Job Title: "
        + System.lineSeparator() + "Employee's Salary: " + System.lineSeparator()
        + "Employee's Commencement Date (YYYYMMDD): " + System.lineSeparator()
        + "Enter \"Q\" to quit without adding, enter \"A\" to finish adding, enter anything else to continue adding"
        + System.lineSeparator() + "List of employees to add: [John Smith]"
        + System.lineSeparator() + "Employee's Name: "
        + System.lineSeparator() + "Employee's Job Title: "
        + System.lineSeparator() + "Employee's Salary: " + System.lineSeparator()
        + "Employee's Commencement Date (YYYYMMDD): " + System.lineSeparator()
        + "Enter \"Q\" to quit without adding, enter \"A\" to finish adding, enter anything else to continue adding"
        + System.lineSeparator() + "Added:"
        + System.lineSeparator() + "John Smith" + System.lineSeparator() + "Ted Johnson" + System.lineSeparator()));

    // case with starting to add employees but then aborting the operation
    tester = new TextUITester("A\nJohn Smith\nSWE\n10\n20230325\nQ\nQ\n");
    scnr = new Scanner(System.in);
    frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    output = tester.checkOutput();
    scnr.close();
    // check that the proper output sequence was output to the console
    assertTrue(output.contains("List of employees to add: []" + System.lineSeparator() + "Employee's Name: "
        + System.lineSeparator() + "Employee's Job Title: "
        + System.lineSeparator() + "Employee's Salary: " + System.lineSeparator()
        + "Employee's Commencement Date (YYYYMMDD): " + System.lineSeparator()
        + "Enter \"Q\" to quit without adding, enter \"A\" to finish adding, enter anything else to continue adding"
        + System.lineSeparator()));

    // make sure no employees were actually added because "Added:" is only printed
    // before the employees are actually added
    assertFalse(output.contains("Added:"));

  }

  /**
   * Tests removing employees
   */
  @Test
  public void test3() {
    // use DW placeholder code to load employees
    EmployeeReaderInterface reader = new EmployeeReaderFD();
    // use AE placeholder code to make a RBT
    RedBlackTreeMultiInterface<EmployeeInterface> tree = new RedBlackTreeMultiFD<>();
    // use BD placeholder code for the backend
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendFD(tree, reader);
    // make a TextUITester to test system output
    TextUITester tester = new TextUITester("R\n20230325\n20230326\nR\nQ\n");
    Scanner scnr = new Scanner(System.in);
    // Create a frontend instance to test its methods on
    EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    String output = tester.checkOutput();
    scnr.close();

    // case with correctly removing employees
    // check that the proper output sequence was output to the console
    assertTrue(output.contains("ID list of employees to remove: []" + System.lineSeparator()
        + "Enter \"Q\" to quit without removing, enter \"R\" to finish removing, "
        + "or enter the ID of the next employee to remove (one at a time): " + System.lineSeparator()
        + "ID list of employees to remove: [20230325]" + System.lineSeparator()
        + "Enter \"Q\" to quit without removing, enter \"R\" to finish removing, "
        + "or enter the ID of the next employee to remove (one at a time): " + System.lineSeparator()
        + "ID list of employees to remove: [20230325, 20230326]" + System.lineSeparator()
        + "Enter \"Q\" to quit without removing, enter \"R\" to finish removing, "
        + "or enter the ID of the next employee to remove (one at a time): " + System.lineSeparator()
        + "Removed:" + System.lineSeparator() + "20230325" + System.lineSeparator() + "20230326"
        + System.lineSeparator()));

    // case with starting to remove employees but then aborting the operation
    tester = new TextUITester("R\n20230325\nQ\nQ\n");
    scnr = new Scanner(System.in);
    frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    output = tester.checkOutput();
    scnr.close();
    // check that the proper output sequence was output to the console
    assertTrue(output.contains("ID list of employees to remove: []" + System.lineSeparator()
        + "Enter \"Q\" to quit without removing, enter \"R\" to finish removing, "
        + "or enter the ID of the next employee to remove (one at a time): " + System.lineSeparator()
        + "ID list of employees to remove: [20230325]" + System.lineSeparator()
        + "Enter \"Q\" to quit without removing, enter \"R\" to finish removing, "
        + "or enter the ID of the next employee to remove (one at a time): " + System.lineSeparator()));

    // make sure no employees were actually removed because "Removed:" is only
    // printed before the employees are actually removed
    assertFalse(output.contains("Removed:"));

  }

  /**
   * Tests changing an employee's job title and salary
   */
  @Test
  public void test4() {
    // use DW placeholder code to load employees
    EmployeeReaderInterface reader = new EmployeeReaderFD();
    // use AE placeholder code to make a RBT
    RedBlackTreeMultiInterface<EmployeeInterface> tree = new RedBlackTreeMultiFD<>();
    // use BD placeholder code for the backend
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendFD(tree, reader);
    // make a TextUITester to test system output
    TextUITester tester = new TextUITester("J\n20231025\nHR\nQ\n");
    Scanner scnr = new Scanner(System.in);
    // Create a frontend instance to test its methods on
    EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    String output = tester.checkOutput();
    scnr.close();

    // case where you change the job title of a valid employee
    // check that the proper output sequence was output to the console
    assertTrue(output.contains("Enter the employee's ID: " + System.lineSeparator() + "New Job Title: "
        + System.lineSeparator() + "John Smith's job title was changed to HR" + System.lineSeparator()));

    // case where you change the job title of an invalid employee
    tester = new TextUITester("J\n1111\nQ\n");
    scnr = new Scanner(System.in);
    frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    output = tester.checkOutput();
    scnr.close();

    // invalid employee id should cause the "Employee ID not found" message
    assertTrue(output.contains(
        "Enter the employee's ID: " + System.lineSeparator() + "Employee ID not found" + System.lineSeparator()));

    // case where you change the salary of a valid employee
    tester = new TextUITester("S\n20231025\n50\nQ\n");
    scnr = new Scanner(System.in);
    frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    output = tester.checkOutput();
    scnr.close();
    // check that the proper output sequence was output to the console
    assertTrue(output.contains("Enter the employee's ID: " + System.lineSeparator() + "New Salary: "
        + System.lineSeparator() + "John Smith's salary was changed to 50" + System.lineSeparator()));

    // case where you change the salary of an invalid employee
    tester = new TextUITester("J\n1111\nQ\n");
    scnr = new Scanner(System.in);
    frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    output = tester.checkOutput();
    scnr.close();

    // invalid employee id should cause the "Employee ID not found" message
    assertTrue(output.contains(
        "Enter the employee's ID: " + System.lineSeparator() + "Employee ID not found" + System.lineSeparator()));

  }

  /**
   * Tests the displaying employees command
   */
  @Test
  public void test5() {
    // use DW placeholder code to load employees
    EmployeeReaderInterface reader = new EmployeeReaderFD();
    // use AE placeholder code to make a RBT
    RedBlackTreeMultiInterface<EmployeeInterface> tree = new RedBlackTreeMultiFD<>();
    // use BD placeholder code for the backend
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendFD(tree, reader);
    // make a TextUITester to test system output
    TextUITester tester = new TextUITester("E\n20231025\nQ\n");
    Scanner scnr = new Scanner(System.in);
    // Create a frontend instance to test its methods on
    EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    String output = tester.checkOutput();
    scnr.close();

    // case where the employee exists
    // check that the proper output sequence was output to the console
    assertTrue(output.contains("Enter the employee's ID: " + System.lineSeparator() + "Name: John Smith"
        + System.lineSeparator() + "Employee ID: 20231025" + System.lineSeparator() + "Job Title: SWE"
        + System.lineSeparator() + "Salary: 10" + System.lineSeparator() + "Commencement Date: 20231025"
        + System.lineSeparator()));

    // case where the employee doesn't exist
    tester = new TextUITester("E\n1111\nQ\n");
    scnr = new Scanner(System.in);
    frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    output = tester.checkOutput();
    scnr.close();
    // invalid employee id should cause the "Employee ID not found" message
    assertTrue(output.contains(
        "Enter the employee's ID: " + System.lineSeparator() + "Employee ID not found" + System.lineSeparator()));

  }

  /**
   * Tests the integration of the FD and BD roles by trying to load a buisiness'
   * employee data. This makes sure that the file taken in by the frontend is
   * correctly loaded with the backend
   * 
   */
  @Test
  public void integration1() {
    // Use data wrangler's code to load employee data
    EmployeeReaderInterface employeeReader = new EmployeeReader();
    // Use algorithm engineer's code to store and search for data
    RedBlackTreeMultiInterface<EmployeeInterface> redBlackTree;
    redBlackTree = new RedBlackTreeMultiAE<EmployeeInterface>();
    // Use the backend developer's code to manage all app specific processing
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(redBlackTree, employeeReader);
    // use TextUITester to ensure a specific usage of the app
    TextUITester tester = new TextUITester("L\ntestEmployees.txt\nE\n20210101\nE\20210613\nE\20150206\nQ\n");
    // Use the frontend developer's code to drive the text-base user interface
    Scanner scanner = new Scanner(System.in);
    EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scanner, backend, employeeReader);
    frontend.runCommandLoop();
    String output = tester.checkOutput();
    scanner.close();

    // make sure that the employees displayed are the ones that were suppose to be
    // laoded
    assertTrue(output.contains("Name: Joseph" + System.lineSeparator() + "Employee ID: 20210101"
        + System.lineSeparator() + "Job Title: software engineer" + System.lineSeparator() + "Salary: 150000"
        + System.lineSeparator() + "Commencement Date: 20210101" + System.lineSeparator()));

    assertTrue(output.contains("Name: Otis" + System.lineSeparator() + "Employee ID: 20210613"
        + System.lineSeparator() + "Job Title: artist" + System.lineSeparator() + "Salary: 60000"
        + System.lineSeparator() + "Commencement Date: 20210613" + System.lineSeparator()));

    assertTrue(output.contains("Name: Amanda" + System.lineSeparator() + "Employee ID: 20150206"
        + System.lineSeparator() + "Job Title: doctor" + System.lineSeparator() + "Salary: 350000"
        + System.lineSeparator() + "Commencement Date: 20150206" + System.lineSeparator()));

  }

  /**
   * Tests the integration of the FD and DW roles by trying to change the salary
   * and job title of an employee. This makes sure that the inputs taken with the
   * frontend are correctly changed on the DW's Employee object
   */
  @Test
  public void integration2() {
    // Use data wrangler's code to load employee data
    EmployeeReaderInterface employeeReader = new EmployeeReader();
    // Use algorithm engineer's code to store and search for data
    RedBlackTreeMultiInterface<EmployeeInterface> redBlackTree;
    redBlackTree = new RedBlackTreeMultiAE<EmployeeInterface>();
    // Use the backend developer's code to manage all app specific processing
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(redBlackTree, employeeReader);
    // use TextUITester to ensure a simulation of the user changing an employee's
    // salary and job title
    TextUITester tester = new TextUITester(
        "L\ntestEmployees.txt\nS\n20210101\n50000\nJ\n20210101\nIntern\nE\n20210101\nQ\n");
    // Use the frontend developer's code to drive the text-base user interface
    Scanner scanner = new Scanner(System.in);
    EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scanner, backend, employeeReader);
    frontend.runCommandLoop();
    String output = tester.checkOutput();
    scanner.close();

    // check to see that the salary and job title were changed correctly
    assertTrue(output.contains("Job Title: Intern"));
    assertTrue(output.contains("Salary: 50000"));

  }

  /**
   * Tests the BD at adding employees
   */
  @Test
  public void codeReviewOfBackendDeveloper1() {
    // Use data wrangler's code to load employee data
    EmployeeReaderInterface employeeReader = new EmployeeReader();
    // Use algorithm engineer's code to store and search for data
    RedBlackTreeMultiInterface<EmployeeInterface> redBlackTree;
    redBlackTree = new RedBlackTreeMultiAE<EmployeeInterface>();
    // Use the backend developer's code to manage all app specific processing
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(redBlackTree, employeeReader);

    // try adding some employees
    List<EmployeeInterface> employees = new ArrayList<EmployeeInterface>();
    employees.add(new Employee("John Smith", "SWE", 10, 20230101));
    employees.add(new Employee("Tim Johnson", "HR", 20, 20230202));
    backend.addEmployees(employees);

    // get the employees that were added
    EmployeeInterface testEmployee1 = backend.findEmployee(20230101);
    EmployeeInterface testEmployee2 = backend.findEmployee(20230202);

    // make sure the employees that were added were all correct
    assertEquals(testEmployee1.getEmployeeName(), "John Smith");
    assertEquals(testEmployee1.getSalary(), 10);
    assertEquals(testEmployee1.getJobTitle(), "SWE");
    assertEquals(testEmployee1.getCommencementDate(), 20230101);

    assertEquals(testEmployee2.getEmployeeName(), "Tim Johnson");
    assertEquals(testEmployee2.getSalary(), 20);
    assertEquals(testEmployee2.getJobTitle(), "HR");
    assertEquals(testEmployee2.getCommencementDate(), 20230202);

  }

  /**
   * Tests BD at getting employees by months with the company
   */
  @Test
  public void codeReviewOfBackendDeveloper2() {
    // Use data wrangler's code to load employee data
    EmployeeReaderInterface employeeReader = new EmployeeReader();
    // Use algorithm engineer's code to store and search for data
    RedBlackTreeMultiInterface<EmployeeInterface> redBlackTree;
    redBlackTree = new RedBlackTreeMultiAE<EmployeeInterface>();
    // Use the backend developer's code to manage all app specific processing
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(redBlackTree, employeeReader);

    try {
      // load test file
      backend.loadCompanyData("testEmployees.txt");
    } catch (FileNotFoundException e) {
      fail();
    }

    // try getting employees with the company for 28 months
    List<EmployeeInterface> employees = backend.getEmployeesByMonthsWithCompany(28);

    assertEquals(employees.size(), 2); // make sure correct # of employees returned

    EmployeeInterface testEmployee1 = employees.get(0);
    EmployeeInterface testEmployee2 = employees.get(1);

    assertEquals(testEmployee1.getEmployeeName(), "Joseph");
    assertEquals(testEmployee1.getSalary(), 150000);
    assertEquals(testEmployee1.getJobTitle(), "software engineer");
    assertEquals(testEmployee1.getCommencementDate(), 20210101);

    assertEquals(testEmployee2.getEmployeeName(), "Amanda");
    assertEquals(testEmployee2.getSalary(), 100000);
    assertEquals(testEmployee2.getJobTitle(), "pilot");
    assertEquals(testEmployee2.getCommencementDate(), 20210111);

  }

}
