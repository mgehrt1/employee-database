import java.io.FileNotFoundException;
import java.util.List;

public interface EmployeeDatabaseBackendInterface {
    public void loadCompanyData(String filename) throws FileNotFoundException;
        public EmployeeInterface findEmployee(int employeeID);
        public void removeEmployees(List<Integer> employeeIDs);
        public void addEmployees(List<EmployeeInterface> employees);
        public List<EmployeeInterface> getEmployeesByMonthsWithCompany(int monthsWithCompany);
        public String getCompanyStatistics();
}
