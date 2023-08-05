import java.io.FileNotFoundException;
import java.util.List;

public class EmployeeDatabaseBackendFD implements EmployeeDatabaseBackendInterface {

  private RedBlackTreeMultiInterface<EmployeeInterface> tree;
  private EmployeeReaderInterface reader;

  public EmployeeDatabaseBackendFD(RedBlackTreeMultiInterface<EmployeeInterface> tree, EmployeeReaderInterface reader) {
    this.tree = tree;
    this.reader = reader;
  }

  @Override
  public void loadCompanyData(String filename) throws FileNotFoundException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'loadCompanyData'");
  }

  @Override
  public EmployeeInterface findEmployee(int employeeID) {
    if (employeeID == 20231025) {
      return new EmployeeFD("John Smith", "SWE", 10, 20231025);
    }
    return null;
  }

  @Override
  public void removeEmployees(List<Integer> employeeIDs) {
    return;
  }

  @Override
  public void addEmployees(List<EmployeeInterface> employees) {
    return;
  }

  @Override
  public List<EmployeeInterface> getEmployeesByMonthsWithCompany(int monthsWithCompany) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEmployeesByMonthsWithCompany'");
  }

  @Override
  public String getCompanyStatistics() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCompanyStatistics'");
  }

}
