import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;

public class BackendDeveloperTests {

    /**
     * Test loadCompanyData and constructor
     */
    @Test
    public void test1() {
        assertDoesNotThrow(() -> {

            // Test constructors
            assertDoesNotThrow(() -> {
                RedBlackTreeMultiBD<EmployeeInterface> tree = new RedBlackTreeMultiBD<EmployeeInterface>();
                EmployeeDatabaseBackendBD test = new EmployeeDatabaseBackendBD(tree, new EmployeeReaderBD());
                assertFalse(test == null);
            });

            // Create a tree and backend tester instance
            RedBlackTreeMultiBD<EmployeeInterface> tree = new RedBlackTreeMultiBD<EmployeeInterface>();
            EmployeeDatabaseBackendBD test = new EmployeeDatabaseBackendBD(tree, new EmployeeReaderBD());

            // Check handling of invalid file
            FileNotFoundException thrown = assertThrows(FileNotFoundException.class, () -> {
                test.loadCompanyData("notAFile.txt");
            });

            // Ensure that the error was thrown correctly
            assertFalse(thrown == null);

            // Check that it finds the test file
            assertDoesNotThrow(() -> {
                test.loadCompanyData("test.txt");
            });

            //// Loading the test file loads a list of three specific employees ////
            //// It is detailed and hard-coded in EmployeeReaderBD ////
        });
    }

    /**
     * Test addEmployees and removeEmployees
     */
    @Test
    public void test2() {
        assertDoesNotThrow(() -> {
            // Create a tree and backend tester instance
            RedBlackTreeMultiBD<EmployeeInterface> tree = new RedBlackTreeMultiBD<EmployeeInterface>();
            EmployeeDatabaseBackendBD test = new EmployeeDatabaseBackendBD(tree, new EmployeeReaderBD());

            // Load three employees
            test.loadCompanyData("test.txt");

            // Check for the correct initial company stats
            assertEquals(30, test.totalSalary);
            assertEquals(3, test.uniqueJobs);
            assertEquals(3, test.employeeCount);

            // Add two additional employees, but with no new unique jobs
            // Hash table should not add these to unique jobs list
            List<EmployeeInterface> list = new ArrayList<EmployeeInterface>();
            EmployeeBD employee1 = new EmployeeBD("Jack", "SWE", 50, 20220520);
            EmployeeBD employee2 = new EmployeeBD("Eric", "EE", 100, 20201116);
            list.add(employee1);
            list.add(employee2);
            test.addEmployees(list);

            // Check new stats
            assertEquals(180, test.totalSalary);
            assertEquals(3, test.uniqueJobs);
            assertEquals(5, test.employeeCount);

            // Remove an employee
            List<Integer> removeList = new ArrayList<Integer>();
            removeList.add(20220520);
            removeList.add(20201116);
            test.removeEmployees(removeList);

            // Check new stats
            assertEquals(30, test.totalSalary);
            assertEquals(3, test.uniqueJobs);
            assertEquals(3, test.employeeCount);
        });
    }

    /**
     * Test findEmployee
     */
    @Test
    public void test3() {
        assertDoesNotThrow(() -> {
            // Create a tree and backend tester instance
            RedBlackTreeMultiBD<EmployeeInterface> tree = new RedBlackTreeMultiBD<EmployeeInterface>();
            EmployeeDatabaseBackendBD test = new EmployeeDatabaseBackendBD(tree, new EmployeeReaderBD());

            // Try to find an employee that is in the list
            test.loadCompanyData("test.txt");

            // Find first employee
            assertEquals("John", test.findEmployee(20220320).getEmployeeName());
            assertEquals(20220320, test.findEmployee(20220320).getEmployeeID());
            assertEquals("SWE", test.findEmployee(20220320).getJobTitle());

            // Find second employee
            assertEquals("Master Chief", test.findEmployee(20210320).getEmployeeName());
            assertEquals(20210320, test.findEmployee(20210320).getEmployeeID());
            assertEquals("ME", test.findEmployee(20210320).getJobTitle());

            // Find third employee
            assertEquals("Fitz", test.findEmployee(20201016).getEmployeeName());
            assertEquals(20201016, test.findEmployee(20201016).getEmployeeID());
            assertEquals("EE", test.findEmployee(20201016).getJobTitle());

            // Try to find an employee that is not in the list
            int employee2ID = 20191118;
            assertEquals(null, test.findEmployee(employee2ID));
        });
    }

    /**
     * Test getEmployeesByMonthsWithCompany
     */
    @Test
    public void test4() {
        assertDoesNotThrow(() -> {
            // YYYYMMDD

            // Get current month for testing how long someone has been at company
            Date referenceDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(referenceDate);
            int month = c.get(Calendar.MONTH) + 1;

            // Create a tree and backend tester instance
            RedBlackTreeMultiBD<EmployeeInterface> tree = new RedBlackTreeMultiBD<EmployeeInterface>();
            EmployeeDatabaseBackendBD test = new EmployeeDatabaseBackendBD(tree, new EmployeeReaderBD());

            // Try to find employees based off varying months of experience
            test.loadCompanyData("test.txt");

            // Find first employee, test for March 2022
            assertEquals("John", test.getEmployeesByMonthsWithCompany(month + 9).get(0).getEmployeeName());
            assertEquals(20220320, test.getEmployeesByMonthsWithCompany(month + 9).get(0).getEmployeeID());
            assertEquals("SWE", test.getEmployeesByMonthsWithCompany(month + 9).get(0).getJobTitle());

            // Find second employee, test for March 2021
            assertEquals("Master Chief", test.getEmployeesByMonthsWithCompany(month + 21).get(0).getEmployeeName());
            assertEquals(20210320, test.getEmployeesByMonthsWithCompany(month + 21).get(0).getEmployeeID());
            assertEquals("ME", test.getEmployeesByMonthsWithCompany(month + 21).get(0).getJobTitle());

            // Find third employee, test for October 2020
            assertEquals("Fitz", test.getEmployeesByMonthsWithCompany(month + 26).get(0).getEmployeeName());
            assertEquals(20201016, test.getEmployeesByMonthsWithCompany(month + 26).get(0).getEmployeeID());
            assertEquals("EE", test.getEmployeesByMonthsWithCompany(month + 26).get(0).getJobTitle());
        });
    }

    /**
     * Tests the getCompanyStatistics
     */
    @Test
    public void test5() {
        RedBlackTreeMultiBD<EmployeeInterface> tree = new RedBlackTreeMultiBD<EmployeeInterface>();
        EmployeeDatabaseBackendBD test = new EmployeeDatabaseBackendBD(tree, new EmployeeReaderBD());
        List<EmployeeInterface> list = new ArrayList<EmployeeInterface>();

        // Make a list of three employees
        EmployeeBD employee1 = new EmployeeBD("John", "SWE", 10, 20220320);
        EmployeeBD employee2 = new EmployeeBD("Master Chief", "ME", 10, 20210320);
        EmployeeBD employee3 = new EmployeeBD("Fitz", "EE", 10, 20201016);
        list.add(employee1);
        list.add(employee2);
        list.add(employee3);

        // Add Employees
        test.addEmployees(list);

        // Check that each company stat is correct
        String expected = "Company Dataset Contains:\n" + "      " + 3 + " employees\n" + "      " + "$" + 30
                + " total annual wage expenses\n" + "       " + "$" + 10 + " average employee salary\n"
                + "        " + 3 + " total unique jobs\n";

        assertEquals(expected, test.getCompanyStatistics());

    }

    /**
     * Integration Test: Test that DW creates a correct list from loading a file of
     * employees and that FD is shows being successful in doing so.
     * 
     */
    @Test
    public void test6() {
        // Use data wrangler's code to load employee data
        EmployeeReaderInterface employeeReader = new EmployeeReader();
        // Use algorithm engineer's code to store and search for data
        RedBlackTreeMultiInterface<EmployeeInterface> redBlackTree;
        redBlackTree = new RedBlackTreeMultiAE<EmployeeInterface>();
        // Use the backend developer's code to manage all app specific processing
        EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(redBlackTree, employeeReader);

        // Make textUI Tester
        // TextUITester tester = new TextUITester("L\ntestEmployees.txt\n");

        // Use the frontend developer's code to drive the text-base user interface
        // Scanner scanner = new Scanner(System.in);

        // EmployeeDatabaseFrontendInterface frontend = new
        // EmployeeDatabaseFrontendFD(scanner, backend, employeeReader);

        // Run loop and try to get an empty company's statistics
        // frontend.runCommandLoop();

        assertDoesNotThrow(() -> {
            backend.loadCompanyData("testEmployees.txt");

        });
    }

    /**
     * Integration Test: Try Searching for an employee that doesn't exist
     * 
     * This tests how AE and BD handle a failed search
     *
     */
    @Test
    public void test7() {
        // Use data wrangler's code to load employee data
        EmployeeReaderInterface employeeReader = new EmployeeReader();
        // Use algorithm engineer's code to store and search for data
        RedBlackTreeMultiInterface<EmployeeInterface> redBlackTree;
        redBlackTree = new RedBlackTreeMultiAE<EmployeeInterface>();
        // Use the backend developer's code to manage all app specific processing
        EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(redBlackTree, employeeReader);

        // Make textUI Tester
        TextUITester tester = new TextUITester("L\ntestEmployees.txt\nE\n99999999\nQ\n");

        // Use the frontend developer's code to drive the text-base user interface
        Scanner scanner = new Scanner(System.in);

        EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scanner, backend, employeeReader);

        // Run loop and try to get an empty company's statistics
        frontend.runCommandLoop();

        assertDoesNotThrow(() -> {
            tester.checkOutput();
        });
    }

    /**
     * Tests that the Frontend Main menu prompt is correctly displaying
     */
    @Test
    public void CodeReviewOfFrontendDeveloper() {
        // Use data wrangler's code to load employee data
        EmployeeReaderInterface employeeReader = new EmployeeReader();
        // Use algorithm engineer's code to store and search for data
        RedBlackTreeMultiInterface<EmployeeInterface> redBlackTree;
        redBlackTree = new RedBlackTreeMultiAE<EmployeeInterface>();
        // Use the backend developer's code to manage all app specific processing
        EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(redBlackTree, employeeReader);

        // Make textUI Tester
        TextUITester tester = new TextUITester("D\nQ\n");

        // Use the frontend developer's code to drive the text-base user interface
        Scanner scanner = new Scanner(System.in);

        EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scanner, backend, employeeReader);

        // Run loop and try to get an empty company's statistics
        frontend.runCommandLoop();
        String actual = tester.checkOutput();
        String expected = "-------------------------------------------------------------------------------"
                + System.lineSeparator() + "Welcome to the Employee Database Application!" + System.lineSeparator()
                + "-------------------------------------------------------------------------------"
                + System.lineSeparator()
                + "Choose a command by entering in the corresponding letter in brackets:" + System.lineSeparator()
                + "    [L]oad Business" + System.lineSeparator() + "    [A]dd Employee(s)" + System.lineSeparator()
                + "    [R]emove Employee(s)" + System.lineSeparator() + "    Change [J]ob" + System.lineSeparator()
                + "    Change [S]alary" + System.lineSeparator() + "    Display [E]mployee" + System.lineSeparator()
                + "    [G]et Employees by Months With Company" + System.lineSeparator() + "    [D]isplay Statistics"
                + System.lineSeparator() + "    [Q]uit" + System.lineSeparator() + "Choose command: "
                + "Company Dataset Contains:\n" + "      " + "0" + " employees\n"
                + "      " + "$"
                + "0"
                + " total annual wage expenses\n" + "       " + "$" + "0"
                + " average employee salary\n"
                + "        " + "0" + " total unique jobs\n" + System.lineSeparator()
                + "Choose a command by entering in the corresponding letter in brackets:" + System.lineSeparator()
                + "    [L]oad Business" + System.lineSeparator() + "    [A]dd Employee(s)" + System.lineSeparator()
                + "    [R]emove Employee(s)" + System.lineSeparator() + "    Change [J]ob" + System.lineSeparator()
                + "    Change [S]alary" + System.lineSeparator() + "    Display [E]mployee" + System.lineSeparator()
                + "    [G]et Employees by Months With Company" + System.lineSeparator() + "    [D]isplay Statistics"
                + System.lineSeparator() + "    [Q]uit" + System.lineSeparator() + "Choose command: "
                + "-------------------------------------------------------------------------------"
                + System.lineSeparator()
                + "Thank you for using the Employee Database App!" + System.lineSeparator()
                + "-------------------------------------------------------------------------------"
                + System.lineSeparator();
        assertTrue(actual.contains(expected));
    }

    /**
     * Tests that the Frontend Prompt correctly displays an employee that exists
     */
    @Test
    public void CodeReviewOfFrontendDeveloper2() {
        // Use data wrangler's code to load employee data
        EmployeeReaderInterface employeeReader = new EmployeeReader();
        // Use algorithm engineer's code to store and search for data
        RedBlackTreeMultiInterface<EmployeeInterface> redBlackTree;
        redBlackTree = new RedBlackTreeMultiAE<EmployeeInterface>();
        // Use the backend developer's code to manage all app specific processing
        EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(redBlackTree, employeeReader);

        // Make textUI Tester
        TextUITester tester = new TextUITester("L\ntestEmployees.txt\nE\n20210101\nQ\n");

        // Use the frontend developer's code to drive the text-base user interface
        Scanner scanner = new Scanner(System.in);

        EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scanner, backend, employeeReader);

        // Run loop and try to get an empty company's statistics
        frontend.runCommandLoop();
        String actual = tester.checkOutput();
        String expected = "Enter the employee's ID: " + System.lineSeparator() + "Name: Joseph"
                + System.lineSeparator() + "Employee ID: 20210101" + System.lineSeparator()
                + "Job Title: software engineer"
                + System.lineSeparator() + "Salary: 150000" + System.lineSeparator() + "Commencement Date: 20210101"
                + System.lineSeparator();
        assertTrue(actual.contains(expected));
    }
}
