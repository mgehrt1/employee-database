import java.io.IOException;

public interface EmployeeDatabaseFrontendInterface {
  // public EmployeeDatabaseFrontendXX(Scanner userInput,
  // EmployeeDatabaseBackendInterface backend, EmlpoyeeReaderInterface reader);
  public void runCommandLoop();

  public char mainMenuPrompt();

  public void loadBusinessCommand();

  public void addEmployeesCommand() throws IOException;

  public void removeEmployeesCommand();

  public void changeEmployeeJobTitleCommand();

  public void changeEmployeeSalaryCommand();

  public void displayEmployeeCommand();

  public void getEmployeesByMonthsCommand();

  public void displayStatisticsCommand();
}
