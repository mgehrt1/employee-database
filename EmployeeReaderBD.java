import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeReaderBD implements EmployeeReaderInterface {

    @Override
    public List<EmployeeInterface> readEmployeesFromFile(String filename) throws FileNotFoundException {
        if (filename.equals("test.txt")) {
            List<EmployeeInterface> list = new ArrayList<EmployeeInterface>();
            EmployeeBD employee1 = new EmployeeBD("John", "SWE", 10, 20220320);
            EmployeeBD employee2 = new EmployeeBD("Master Chief", "ME", 10, 20210320);
            EmployeeBD employee3 = new EmployeeBD("Fitz", "EE", 10, 20201016);
            list.add(employee1);
            list.add(employee2);
            list.add(employee3);

            return list;
        }
        throw new FileNotFoundException();
    }

}
