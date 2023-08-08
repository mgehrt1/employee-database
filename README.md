# Employee Database

This project allows for the user to load in data from a business and create a database of the employees storing all of their information sorted by their employee number, which will be based on when the employee was hired, meaning employees will be sorted by time working for the company. This is an application that would be used by business administrators to better keep track of all of their employees who work for them. It would make it easy for them to access data such as name, job title, salary, and commencement date. This project makes use of the Red-Black Tree data structure by using an implementation of the RBT to store the employees in an ordered data structure by their employee numbers, and also allows for an employee to be efficiently added or removed if they are hired or get fired/quit.

## How to use

### 1) Clone the repository (Must have git installed)

```
git clone https://github.com/mgehrt1/employee-database.git
```

### 2) Must be able to run make files.

Linux: Should be able to run by default

Windows: https://stackoverflow.com/questions/2532234/how-to-run-a-makefile-in-windows

### 3) Run the project

```
make run
```

### 4) Load a database

You can use testEmployees.txt for an example file

Select the "L" command, and enter testEmployees.txt

### 5) Use the app!

Run any commands and try different features using the CLI

### 6) (Optional) Run jUnit tests

```
make runTests
```
