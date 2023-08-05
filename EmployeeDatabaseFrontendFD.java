import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class runs the interactive loop for the Employee Database App
 * 
 * @author Matthew Gehrt
 */
public class EmployeeDatabaseFrontendFD implements EmployeeDatabaseFrontendInterface {
  private Scanner userInput;
  private EmployeeDatabaseBackendInterface backend;
  private EmployeeReaderInterface reader;

  /**
   * Constructor for the frontend of the Employee Database App
   * 
   * @param userInput - input from the user to be read from
   * @param backend   - the backend component of the app
   */
  EmployeeDatabaseFrontendFD(Scanner userInput, EmployeeDatabaseBackendInterface backend,
      EmployeeReaderInterface reader) {
    this.userInput = userInput;
    this.backend = backend;
    this.reader = reader;
  }

  /**
   * Helper method to display a a horizontal rule.
   */
  private void hr() {
    System.out.println("-------------------------------------------------------------------------------");
  }

  /**
   * A loops that provides the user with several functions that they can choose
   * from. Continues until the user chooses the 'quit' command
   */
  @Override
  public void runCommandLoop() {
    // display initial message upon launching the app
    hr();
    System.out.println("Welcome to the Employee Database Application!");
    hr();

    char command = '\0';
    while (command != 'Q') { // main loop continues until user chooses to quit
      command = this.mainMenuPrompt();
      switch (command) {
        case 'L': // Load a business
          loadBusinessCommand();
          break;
        case 'A': // Add employee(s)
          try {
            addEmployeesCommand();
          } catch (IOException e) {
            System.out.println("Error encountered while adding employees, please try again");
          }
          break;
        case 'R': // Remove Employee(s)
          removeEmployeesCommand();
          break;
        case 'J': // Change Job
          changeEmployeeJobTitleCommand();
          break;
        case 'S': // Change Salary
          changeEmployeeSalaryCommand();
          break;
        case 'E': // Display Employee
          displayEmployeeCommand();
          break;
        case 'G': // Get Employees by Months With Company
          getEmployeesByMonthsCommand();
          break;
        case 'D': // Display Statistics
          displayStatisticsCommand();
          break;
        case 'Q': // Quit the program
          // do nothing, containing loop condition will fail
          break;
        default:
          System.out.println(
              "Didn't recognize that command.  Please type one of the letters presented within []s to identify the command you would like to choose.");
          break;
      }
    }

    hr(); // thank user before ending this application
    System.out.println("Thank you for using the Employee Database App!");
    hr();
  }

  @Override
  public char mainMenuPrompt() {
    // display menu of choices
    System.out.println("Choose a command by entering in the corresponding letter in brackets:");
    System.out.println("    [L]oad Business");
    System.out.println("    [A]dd Employee(s)");
    System.out.println("    [R]emove Employee(s)");
    System.out.println("    Change [J]ob");
    System.out.println("    Change [S]alary");
    System.out.println("    Display [E]mployee");
    System.out.println("    [G]et Employees by Months With Company");
    System.out.println("    [D]isplay Statistics");
    System.out.println("    [Q]uit");

    // read in user's choice, and trim away any leading or trailing whitespace
    System.out.print("Choose command: ");
    String input = userInput.nextLine().trim();
    if (input.length() == 0) // if user's choice is blank, return null character
      return '\0';
    // otherwise, return an uppercase version of the first character in input
    return Character.toUpperCase(input.charAt(0));
  }

  /**
   * Prompt the user to enter the filename with the business's data
   */
  @Override
  public void loadBusinessCommand() {
    System.out.print("Enter the business' file name to load: ");
    String filename = userInput.nextLine().trim();
    try {
      backend.loadCompanyData(filename);
      System.out.println("Business data loaded successfully!");
    } catch (FileNotFoundException e) {
      System.out.println("Error: Could not find or load file: " + filename);
    }
  }

  /**
   * Creates a list of employee objects created by user input that will be added
   * to the database
   * 
   * @throws IOException
   */
  @Override
  public void addEmployeesCommand() throws IOException {
    File file = new File("NewEmployeeInfo.txt");
    FileWriter writer = new FileWriter(file);

    List<EmployeeInterface> employees = new ArrayList<EmployeeInterface>();
    List<String> nameList = new ArrayList<String>();
    String name;
    String jobTitle;
    int salary;
    int commencementDate;
    String command;
    // start a loop to create the new employee objects to add which exits when the
    // user enters "A" or "Q"
    while (true) {
      System.out.println("List of employees to add: " + nameList.toString());
      System.out.println("Employee's Name: "); // ask for name
      name = userInput.nextLine().trim();
      nameList.add(name);
      System.out.println("Employee's Job Title: "); // ask for job title
      jobTitle = userInput.nextLine().trim();
      // make a loop for salary to make sure a proper int was entered
      while (true) {
        System.out.println("Employee's Salary: ");
        try {
          salary = Integer.parseInt(userInput.nextLine().trim());
          break; // exit salary loop once valid salary is entered
        } catch (NumberFormatException e) {
          System.out.println("Invalid salary, please try again");
        }
      }
      while (true) {
        System.out.println("Employee's Commencement Date (YYYYMMDD): ");
        try {
          commencementDate = Integer.parseInt(userInput.nextLine().trim());
          break; // exit commencement date loop once valid date is entered
        } catch (NumberFormatException e) {
          System.out.println("Invalid date, please try again");
        }
      }

      // write the new employee's info to a file, so it can be read and turned into an
      // employee object by the data wrangler emlpoyee reader class
      writer.write(name + "," + jobTitle + "," + salary + "," + commencementDate);

      System.out.println("Enter \"Q\" to quit without adding, enter \"A\" to finish adding, "
          + "enter anything else to continue adding");
      command = userInput.nextLine().trim();
      if (command.toUpperCase().equals("A")) { // "A" ends the adding
        writer.close();
        // use employee reader to turn text file into employee objects
        employees = reader.readEmployeesFromFile(file.getName());
        backend.addEmployees(employees);
        System.out.println("Added:");
        for (EmployeeInterface employee : employees) {
          System.out.println(employee.getEmployeeName());
        }
        return;
      } else if (command.toUpperCase().equals("Q")) { // "Q" aborts the operation
        writer.close();
        return;
      }
    }
  }

  /**
   * Creates a list of employee ID's entered by the user that will remove the
   * employees corresponding to those ID's from the database
   */
  @Override
  public void removeEmployeesCommand() {
    List<Integer> idList = new ArrayList<Integer>();
    String command;
    // start a loop that ends when the user presses enters "R" or "Q"
    while (true) {
      System.out.println("ID list of employees to remove: " + idList.toString());
      System.out.println("Enter \"Q\" to quit without removing, enter \"R\" to finish removing, "
          + "or enter the ID of the next employee to remove (one at a time): ");
      command = userInput.nextLine().trim();
      if (command.toUpperCase().equals("R")) { // "R" ends the removing
        // remove all employee's in the idList
        backend.removeEmployees(idList);
        System.out.println("Removed:");
        for (int id : idList) {
          System.out.println(id);
        }
        return;
      } else if (command.toUpperCase().equals("Q")) { // "Q" aborts the operation
        return;
      } else {
        int id = Integer.parseInt(command);
        if (!idList.contains(id)) {
          // no duplicate id's since you can only remove an employee once
          idList.add(id);
        }
      }
    }
  }

  /**
   * Helper method to get a single employee from an input ID
   * 
   * @return the employee that corresponds to the ID input by the user
   */
  private EmployeeInterface getEmployeeByID() {
    System.out.println("Enter the employee's ID: "); // prompt for ID
    int employeeID;
    EmployeeInterface employee = null;

    try {
      employeeID = Integer.parseInt(userInput.nextLine().trim()); // get ID from user
      employee = backend.findEmployee(employeeID); // get the employee with the given ID
    } catch (NumberFormatException e) {
      System.out.println("Invalid employee ID");
    }

    return employee;
  }

  /**
   * Changes the job title of an employee if they get promoted or change positions
   */
  @Override
  public void changeEmployeeJobTitleCommand() {
    EmployeeInterface employee = getEmployeeByID(); // get the needed employee

    // if the employee is null, it was not found, so don't change any job titles
    if (employee != null) {
      System.out.println("New Job Title: ");
      String newJob = userInput.nextLine().trim(); // get the new job title from the user
      employee.setJobTitle(newJob);
      System.out.println(employee.getEmployeeName() + "'s job title was changed to " + newJob);
    } else {
      System.out.println("Employee ID not found");
    }
  }

  /**
   * Changes the salary of an employee if they get a pay increase of decrease
   */
  @Override
  public void changeEmployeeSalaryCommand() {
    EmployeeInterface employee = getEmployeeByID(); // get the needed employee

    // if the employee is null, it was not found, so don't change any salaries
    if (employee != null) {
      int newSalary;
      try {
        System.out.println("New Salary: ");
        newSalary = Integer.parseInt(userInput.nextLine().trim()); // get the new salary from the user
        employee.setSalary(newSalary);
        System.out.println(employee.getEmployeeName() + "'s salary was changed to " + newSalary);
      } catch (NumberFormatException e) {
        System.out.println("Invalid salary value");
      }
    } else {
      System.out.println("Employee ID not found");
    }
  }

  /**
   * Prints out a list of employees names and commencement dates who have been
   * with the company for a user determined number of months
   */
  @Override
  public void getEmployeesByMonthsCommand() {
    System.out.println("Number of months to search by: "); // prompt for number of months
    int months;

    try {
      months = Integer.parseInt(userInput.nextLine().trim()); // get numner of months from user
    } catch (NumberFormatException e) {
      System.out.println("Invalid number of months");
      return;
    }

    // get list of employees from backend
    List<EmployeeInterface> employees = backend.getEmployeesByMonthsWithCompany(months);
    System.out.println("Employees that have worked for " + months + " months:");
    // print out the name and commencement date of all employees found
    for (EmployeeInterface employee : employees) {
      System.out.println("Name: " + employee.getEmployeeName());
      System.out.println("Commencement Date: " + employee.getCommencementDate());
    }
  }

  /**
   * Displays all the information stored in the Employee Database about a certain
   * employee
   */
  @Override
  public void displayEmployeeCommand() {
    EmployeeInterface employee = getEmployeeByID(); // get the needed employee

    if (employee != null) {
      // print all info about the employee stored in the database
      System.out.println("Name: " + employee.getEmployeeName());
      System.out.println("Employee ID: " + employee.getEmployeeID());
      System.out.println("Job Title: " + employee.getJobTitle());
      System.out.println("Salary: " + employee.getSalary());
      System.out.println("Commencement Date: " + employee.getCommencementDate());
    } else {
      System.out.println("Employee ID not found");
    }
  }

  /**
   * Displays the Employee Databse statistics of the currently loaded busienss
   */
  @Override
  public void displayStatisticsCommand() {
    // print company statistics retrieved from the backend
    System.out.println(backend.getCompanyStatistics());
  }

}
