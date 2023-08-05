import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeeReader implements EmployeeReaderInterface {

	@Override
	public List<EmployeeInterface> readEmployeesFromFile(String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub

		ArrayList<EmployeeInterface> employees = new ArrayList<>();
		Scanner sc = new Scanner(new File(filename));
		//Map<Integer, Integer> countMap = new HashMap<>(); // to keep track of employee count for each commencement date

		while (sc.hasNextLine()) {
			// for each line in the file being read:
			String line = sc.nextLine();
			// split that line into parts around the commas
			String[] parts = line.split(",");

			// lines should have all four parts
			if (parts.length == 4) {
				int salary =Integer.parseInt(parts[2]);  
				int commencementDate = Integer.parseInt(parts[3]);  

				//				 // check if employee with same commencement date already exists
				//                if (countMap.containsKey(commencementDate)) {
				//                    int count = countMap.get(commencementDate) + 1; // increment count
				//                    countMap.put(commencementDate, count); // update count in map
				//                    String tempID = String.format("%08d", commencementDate) + String.format("%02d", count);
				//                    int employeeID = Integer.valueOf(tempID);
				//                    Employee e = new Employee(parts[0], parts[1], salary, commencementDate);
				//                    e.employeeID = employeeID;
				//                    employees.add(e);
				//                    
				//                } else {
				//                    countMap.put(commencementDate, 1); // add commencement date to map with count 1
				//                    String tempID = String.format("%08d", commencementDate) + "01";
				//                    int employeeID = Integer.valueOf(tempID);
				//                    Employee e = new Employee(parts[0], parts[1], salary, commencementDate);
				//                    e.employeeID = employeeID;
				//                    employees.add(e);
				//                }
				//				

				employees.add(new Employee(parts[0], parts[1], salary,commencementDate));

			}
		}



		//closing the scanner before returning the list of employees
		sc.close();
		return employees;
	}


}
