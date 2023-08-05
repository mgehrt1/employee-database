import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

/**
 * This class implements the Backend Section of the Employee ID Database
 * 
 * @author Will Fitzgerald
 */
public class EmployeeDatabaseBackendBD implements EmployeeDatabaseBackendInterface {
    protected int totalSalary = 0;
    protected int uniqueJobs = 0;
    protected int employeeCount = 0;
    private EmployeeReaderInterface employeeReader;
    private RedBlackTreeMultiInterface<EmployeeInterface> tree;
    private Hashtable<String, String> table = new Hashtable<String, String>();

    public EmployeeDatabaseBackendBD(RedBlackTreeMultiInterface<EmployeeInterface> tree,
            EmployeeReaderInterface employeeReader) {
        this.employeeReader = employeeReader;
        this.tree = tree;
    }

    /**
     * Loads Copmany data from Data Wrangler
     * 
     * @params filename: file that includes employees for company
     */
    @Override
    public void loadCompanyData(String filename) throws FileNotFoundException {
        List<EmployeeInterface> employees = employeeReader.readEmployeesFromFile(filename);
        addEmployees(employees);
    }

    /**
     * Searches tree for a specific employee based off a passed employee ID
     *
     * @params employeeID: Employee's ID we are searching for
     * @return Employee that was found in tree
     */
    @Override
    public EmployeeInterface findEmployee(int employeeID) {

        // Get the employee from tree
        EmployeeInterface employee = tree.get(new Employee(null, null, 0, employeeID));

        // Check if employee was found or not
        return employee;
    }

    /**
     * Takes a list of ID's of employees to remove from the company
     * 
     * @params List of ID's of the employees to be removed from the tree
     */
    @Override
    public void removeEmployees(List<Integer> employeeIDs) {
        // Search and create a list of employees with these IDs
        List<EmployeeInterface> employees = new ArrayList<EmployeeInterface>();
        for (int i = 0; i < employeeIDs.size(); i++) {
            if (this.findEmployee((employeeIDs.get(i))) != null) {
                employees.add(this.findEmployee((employeeIDs.get(i))));
            }
        }
        // Remove this list of employees
        tree.removeMultiple(employees);
        for (EmployeeInterface employee : employees) {
            totalSalary -= employee.getSalary();
            employeeCount--;
        }
    }

    /**
     * Takes a list of Employees to add to the company
     * 
     * @params List of Employees to be added to the tree
     */
    @Override
    public void addEmployees(List<EmployeeInterface> employees) {
        tree.putMultiple(employees);

        // Get salary and unique job statistics
        for (EmployeeInterface employee : employees) {
            totalSalary += employee.getSalary();
            employeeCount++;
        }

        for (EmployeeInterface employee : employees) {
            String job = employee.getJobTitle();
            if (!table.contains(job)) {
                table.put(job, job);
                uniqueJobs++;
            }
        }
    }

    /**
     * Calculates and Searches tree for employees that have ID's
     * 
     * @params number of months at the company
     * @return List of employees that have been at the company for the given amount
     *         of months
     */
    @Override
    public List<EmployeeInterface> getEmployeesByMonthsWithCompany(int monthsWithCompany) {
        // YYYYMMDD
        String dateStr = "";

        // Get current month and year
        Date referenceDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(referenceDate);
        c.add(Calendar.MONTH, -monthsWithCompany);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);

        // Create list of all possible dates that are valid for given months
        List<Integer> employeeIDs = new ArrayList<Integer>();
        for (int i = 1; i < 32; i++) {
            if (i < 10) {
                if (month < 10) {
                    dateStr = year + "0" + month + "0" + i;
                } else {
                    dateStr = "" + year + month + "0" + i;
                }
            } else {
                if (month < 10) {
                    dateStr = year + "0" + month + i;
                } else {
                    dateStr = "" + year + month + i;
                }
            }

            // Create the valid IDs
            int dateInt = Integer.parseInt(dateStr);
            employeeIDs.add(dateInt);
        }
        // Search Tree for all valid IDs
        List<EmployeeInterface> employees = new ArrayList<EmployeeInterface>();
        for (int i = 0; i < employeeIDs.size(); i++) {
            if (this.findEmployee((employeeIDs.get(i))) != null) {
                // Add to list if found on tree
                employees.add(this.findEmployee((employeeIDs.get(i))));
            }
        }
        return employees;
    }

    /**
     * Gathers this copmany's statistics into a string for the FD
     * 
     * @return string of company statistics
     */
    @Override
    public String getCompanyStatistics() {
        employeeCount = tree.getNumberOfNodes();
        int averageSalary = 0;
        if (employeeCount != 0) {
            averageSalary = totalSalary / employeeCount;
        }

        String str = "Company Dataset Contains:\n" + "      " + employeeCount + " employees\n" + "      " + "$"
                + totalSalary
                + " total annual wage expenses\n" + "       " + "$" + averageSalary + " average employee salary\n"
                + "        " + uniqueJobs + " total unique jobs\n";

        return str;
    }
}
