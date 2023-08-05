import java.util.ArrayList;
import java.util.List;

public class EmployeeReaderFD implements EmployeeReaderInterface {

  @Override
  public List<EmployeeInterface> readEmployeesFromFile(String filename) {
    List<EmployeeInterface> employees = new ArrayList<EmployeeInterface>();
    employees.add(new EmployeeFD("John Smith", "SWE", 10, 03252023));
    employees.add(new EmployeeFD("Ted Johnson", "HR", 10, 03262023));
    return employees;
  }

}
