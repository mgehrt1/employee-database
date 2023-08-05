public class EmployeeFD implements EmployeeInterface {

  private String name;
  private String jobTitle;
  private int salary;
  private int commencementDate;
  private int employeeID;

  public EmployeeFD(String name, String jobTitle, int salary, int commencementDate) {
    this.name = name;
    this.jobTitle = jobTitle;
    this.salary = salary;
    this.commencementDate = commencementDate;
    this.employeeID = commencementDate;
  }

  @Override
  public int getEmployeeID() {
    return employeeID;
  }

  @Override
  public int getCommencementDate() {
    return commencementDate;
  }

  @Override
  public int getSalary() {
    return salary;
  }

  @Override
  public String getJobTitle() {
    return jobTitle;
  }

  @Override
  public String getEmployeeName() {
    return name;
  }

  @Override
  public void setSalary(int newSalary) {
    this.salary = newSalary;
  }

  @Override
  public void setJobTitle(String newJobTitle) {
    this.jobTitle = newJobTitle;
  }

  @Override
  public int compareTo(EmployeeInterface other) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
  }

}
