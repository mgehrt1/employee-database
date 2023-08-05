public interface EmployeeInterface extends Comparable<EmployeeInterface> {
  // public EmployeeInterface(String name, String jobTitle, int salary, int
  // commencementDate);
  public int getEmployeeID();

  public int getCommencementDate();

  public int getSalary();

  public String getJobTitle();

  public String getEmployeeName();

  public void setSalary(int newSalary);

  public void setJobTitle(String newJobTitle);

  public int compareTo(EmployeeInterface other);

}
