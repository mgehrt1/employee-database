public class EmployeeBD implements EmployeeInterface {
    private String name = null;
    private String jobTitle = null;
    private int salary = 0;
    private int commencementDate = 0;   
    public EmployeeBD(String name, String jobTitle, int salary, int commencementDate){
        this.name = name;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.commencementDate = commencementDate;
    }
    @Override
    public int getEmployeeID() {
        return commencementDate;
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

    }

    @Override
    public void setJobTitle(String newJobTitle) {

    }
    @Override
    public int compareTo(EmployeeInterface o) {
        return 0;
    }

}
