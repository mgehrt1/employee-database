import java.util.Scanner;

/**
 * Main entry point for starting and running the Employee App.
 * 
 * @author AlgorithmEngineer, Sampreeth Immidisetty.
 */
public class EmployeeApp {
    public static void main(String[] args) {
    // Use data wrangler's code to load employee data
    EmployeeReaderInterface employeeReader = new EmployeeReader();
    // Use algorithm engineer's code to store and search for data
    RedBlackTreeMultiInterface<EmployeeInterface> redBlackTree;
    redBlackTree = new RedBlackTreeMultiAE<EmployeeInterface>();
    // Use the backend developer's code to manage all app specific processing
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(redBlackTree, employeeReader);
    // Use the frontend developer's code to drive the text-base user interface
    Scanner scanner = new Scanner(System.in);
    
    EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scanner, backend, employeeReader);
    
    frontend.runCommandLoop();
  }
}


