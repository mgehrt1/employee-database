import java.io.FileNotFoundException;
import java.util.List;

public interface EmployeeReaderInterface {
    // public EmployeeReaderInterface();

	public List<EmployeeInterface> readEmployeesFromFile(String filename) throws FileNotFoundException;
}
