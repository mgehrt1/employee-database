run: EmployeeApp.class
	java EmployeeApp

EmployeeApp.class: EmployeeApp.java EmployeeDatabaseFrontendFD.class EmployeeDatabaseBackendBD.class EmployeeReader.class RedBlackTreeMultiAE.class
	javac EmployeeApp.java

runDataWranglerTests: DataWranglerTests.class
	java -jar junit5.jar -cp . --select-class=DataWranglerTests
	
Employee.class: Employee.java EmployeeInterface.class
	javac Employee.java

EmployeeReader.class: EmployeeReader.java EmployeeReaderInterface.class
	javac EmployeeReader.java

EmployeeInterface.class: EmployeeInterface.java
	javac EmployeeInterface.java

EmployeeReaderInterface.class: EmployeeReaderInterface.java
	javac EmployeeReaderInterface.java

DataWranglerTests.class: DataWranglerTests.java Employee.class EmployeeReader.class
	javac -cp .:junit5.jar DataWranglerTests.java

#target to run backend tests
runBackendDeveloperTests: BackendDeveloperTests.class
	java -jar junit5.jar -cp . --select-class=BackendDeveloperTests
#target to run all tests
runTests: runBackendDeveloperTests runFrontendDeveloperTests runDataWranglerTests runAlgorithmEngineerTests 
	
# compiles and tests and placeholders
BackendDeveloperTests.class: BackendDeveloperTests.java EmployeeBD.class EmployeeDatabaseBackendBD.class EmployeeReaderBD.class RedBlackTreeMultiBD.class RedBlackTreeMultiAE.class EmployeeDatabaseFrontendFD.class EmployeeReader.class Employee.class 
	javac -cp .:junit5.jar BackendDeveloperTests.java

runAlgorithmEngineerTests: AlgorithmEngineerTests.class
	java -jar junit5.jar -cp . --select-class=AlgorithmEngineerTests        

RedBlackTreeMultiAE.class: RedBlackTree.class RedBlackTreeMultiAE.java RedBlackTreeMultiInterface.class 
	javac RedBlackTreeMultiAE.java 

RedBlackTree.class: RedBlackTree.java SortedCollectionInterface.java
	javac -cp .:junit5.jar RedBlackTree.java SortedCollectionInterface.java

RedBlackTreeMultiInterface.class: RedBlackTreeMultiInterface.java
	javac RedBlackTreeMultiInterface.java

AlgorithmEngineerTests.class: AlgorithmEngineerTests.java RedBlackTreeMultiAE.class
	javac -cp .:junit5.jar AlgorithmEngineerTests.java 

EmployeeBD.class: EmployeeBD.java EmployeeInterface.java
	javac EmployeeBD.java EmployeeInterface.java

EmployeeDatabaseBackendBD.class: EmployeeDatabaseBackendBD.java EmployeeDatabaseBackendInterface.java
	javac EmployeeDatabaseBackendBD.java EmployeeDatabaseBackendInterface.java

EmployeeReaderBD.class: EmployeeReaderBD.java EmployeeReaderInterface.java
	javac EmployeeReaderBD.java EmployeeReaderInterface.java

RedBlackTreeMultiBD.class: RedBlackTreeMultiBD.java RedBlackTreeMultiInterface.java SortedCollectionInterface.java RedBlackTree.java
	javac RedBlackTreeMultiBD.java RedBlackTreeMultiInterface.java SortedCollectionInterface.java RedBlackTree.java

# target to clean all .class files
clean:
	rm -f *.class
	rm -f *~

# target to run tests
runFrontendDeveloperTests: FrontendDeveloperTests.class
	java -jar junit5.jar -cp . --select-class=FrontendDeveloperTests

# compiles tests and placeholders
FrontendDeveloperTests.class: FrontendDeveloperTests.java EmployeeDatabaseFrontendFD.class EmployeeDatabaseBackendFD.class EmployeeReaderFD.class
	javac -cp .:junit5.jar FrontendDeveloperTests.java

EmployeeDatabaseFrontendFD.class: EmployeeDatabaseFrontendFD.java EmployeeDatabaseFrontendInterface.java
	javac EmployeeDatabaseFrontendFD.java EmployeeDatabaseFrontendInterface.java

EmployeeDatabaseBackendFD.class: EmployeeDatabaseBackendFD.java EmployeeDatabaseBackendInterface.java
	javac EmployeeDatabaseBackendFD.java EmployeeDatabaseBackendInterface.java

EmployeeReaderFD.class: EmployeeReaderFD.java EmployeeReaderInterface.java
	javac EmployeeReaderFD.java EmployeeReaderInterface.java

