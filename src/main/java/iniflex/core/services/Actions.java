package iniflex.core.services;

import iniflex.core.model.Employee;

import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Actions {
    List<Employee> employees;

    Map<String, List<Employee>> employeesByRole;

    BigDecimal payroll;

    public Actions() {
        employees = new ArrayList<>();
        payroll = new BigDecimal("0.00");
        employeesByRole = new HashMap<>();
    }

    // Actions methods(except print)
    public void addDefaultEmployees() {
        List<Employee> employees = List.of(
                new Employee("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Employee("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Employee("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Employee("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Employee("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Employee("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Employee("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Employee("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Employee("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Employee("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        );

        employeesByRole = employees.stream().collect(Collectors.groupingBy(Employee::getRole));

        employees.forEach(employee -> {
            payroll = payroll.add(employee.getSalary());
        });

        this.employees.addAll(employees);
    }

    public void removeEmployeeByName(String name, PrintStream printStream) {
        Employee employee = employees.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
        if (employee == null) {
            System.out.println("\nEmployee " + name + " not found.\n");
            return;
        }

        employeesByRole.get(employee.getRole()).remove(employee);

        payroll = payroll.subtract(employee.getSalary());
        employees.remove(employee);
        printStream.println("\nEmployee " + employee.getName() + " removed successfully.\n");
    }

    public void updatedSalaryOfEmployees(BigDecimal percentage, PrintStream printStream) {
        printStream.println("Updating payroll by " + percentage.multiply(new BigDecimal("100")) + "%");
        employees.forEach(employee -> {
            BigDecimal newSalary = employee.getSalary().add(employee.getSalary().multiply(percentage));
            employee.setSalary(newSalary);
        });

        payroll = new BigDecimal("0.00");
        employees.forEach(employee -> {
            payroll = payroll.add(employee.getSalary());
        });
    }

    // Getters and Setters
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public BigDecimal getPayroll() {
        return payroll;
    }

    public void setPayroll(BigDecimal payroll) {
        this.payroll = payroll;
    }

    public Map<String, List<Employee>> getEmployeesByRole() {
        return employeesByRole;
    }

    public void setEmployeesByRole(Map<String, List<Employee>> employeesByRole) {
        this.employeesByRole = employeesByRole;
    }

    // Print methods
    public void printEmployees(PrintStream printStream) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat salaryFormatter = new DecimalFormat("#,##0.00#####", symbols);

       printStream.println(">>Starting employees list:");
        employees.forEach(employee -> {
           printStream.println("Nome: " + employee.getName());
           printStream.print("Data de Nascimento: " + dateFormatter.format(employee.getBirthDate()));
           printStream.print("\t| Salário: " + salaryFormatter.format(employee.getSalary()));
           printStream.println("\t| Função: " + employee.getRole());
           printStream.println("================================");
        });
    }

    public void printEmployessByRole(PrintStream printStream) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat salaryFormatter = new DecimalFormat("#,##0.00#####", symbols);

        employeesByRole.forEach((role, employees) -> {
           printStream.println(">>Starting employees list for role: " + role);
            employees.forEach(employee -> {
               printStream.println("Nome: " + employee.getName());
               printStream.print("Data de Nascimento: " + dateFormatter.format(employee.getBirthDate()));
               printStream.print("\t| Salário: " + salaryFormatter.format(employee.getSalary()));
               printStream.println("\t| Função: " + employee.getRole());
               printStream.println("================================");
            });
        });
    }

    public void printPayrollFormatted(PrintStream printStream) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat salaryFormatter = new DecimalFormat("#,##0.00#####", symbols);

       printStream.println(salaryFormatter.format(payroll));
    }

    public void printEmployeesByBirthDateMonth(List<Integer> months, PrintStream printStream) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat salaryFormatter = new DecimalFormat("#,##0.00#####", symbols);

       printStream.println(">>Starting employees list by birth date month:");
        employees.stream()
                .filter(employee -> months.contains(employee.getBirthDate().getMonthValue()))
                .forEach(employee -> {
                   printStream.println("Nome: " + employee.getName());
                   printStream.print("Data de Nascimento: " + dateFormatter.format(employee.getBirthDate()));
                   printStream.print("\t| Salário: " + salaryFormatter.format(employee.getSalary()));
                   printStream.println("\t| Função: " + employee.getRole());
                   printStream.println("================================");
                });
    }

    public void printOldestEmployee(PrintStream printStream) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        Employee oldestEmployee = employees.stream()
                .min((e1, e2) -> e1.getBirthDate().compareTo(e2.getBirthDate()))
                .orElse(null);

        if (oldestEmployee == null) {
           printStream.println("No employees found.");
            return;
        }

       printStream.println(">>Starting oldest employee:");
       printStream.println("Nome: " + oldestEmployee.getName());
       printStream.println("Idade: " + oldestEmployee.getBirthDate().until(LocalDate.now()).getYears());
       printStream.println("================================");
    }

    public void printInOrderAlphabetic(PrintStream printStream) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat salaryFormatter = new DecimalFormat("#,##0.00#####", symbols);

        var ordenedEmployees = employees.stream()
                .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
                .collect(Collectors.toList());

       printStream.println(">>Starting employees list in alphabetic order:");
        ordenedEmployees.forEach(employee -> {
           printStream.println("Nome: " + employee.getName());
           printStream.print("Data de Nascimento: " + dateFormatter.format(employee.getBirthDate()));
           printStream.print("\t| Salário: " + salaryFormatter.format(employee.getSalary()));
           printStream.println("\t| Função: " + employee.getRole());
           printStream.println("================================");
        });
    }

    public void printSalaryInMinimumBase(PrintStream printStream) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat salaryFormatter = new DecimalFormat("#,##0.00#####", symbols);

        BigDecimal minimumSalary = new BigDecimal("1212.00");

       printStream.println(">>Starting employees list by salary in minimum base:");
        employees.forEach(employee -> {
           printStream.println("Nome: " + employee.getName());
           printStream.println("Salarios minimo: "
                    + employee.getSalary().divide(minimumSalary, 2, RoundingMode.HALF_UP));
           printStream.println("================================");
        });
    }
}
