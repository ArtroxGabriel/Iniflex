package iniflex;

import iniflex.core.services.Actions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Actions actions = new Actions();
        actions.addDefaultEmployees();

        // 3.1 - Inserir funcionários
        actions.addDefaultEmployees();

        try (PrintStream printStream = new PrintStream("output.txt")) {
            // 3.2 - Remover funcionário "João"
            actions.removeEmployeeByName("João", printStream);

            // 3.3 - Imprimir todos os funcionários
            printStream.print("3.3 - Lista de funcionários:\n");
            actions.printEmployees(printStream);

            // 3.4 - Aumento de 10%
            actions.updatedSalaryOfEmployees(BigDecimal.valueOf(0.10), printStream);

            // 3.5 e 3.6 - Agrupar e imprimir por função
            printStream.print("\n3.6 - Funcionários agrupados por função:\n");
            actions.printEmployessByRole(printStream);

            // 3.8 - Imprimir aniversariantes de outubro e dezembro
            printStream.print("\n3.8 - Aniversariantes de outubro e dezembro:\n");
            printStream.println("Funcionarios agrupados durante a inserção inicial");
            actions.printEmployeesByBirthDateMonth(List.of(10, 12), printStream);

            // 3.9 - Funcionário mais velho
            printStream.print("\n3.9 - Funcionário mais velho:\n");
            actions.printOldestEmployee(printStream);

            // 3.10 - Ordenar por nome
            printStream.print("\n3.10 - Funcionários em ordem alfabética:\n");
            actions.printInOrderAlphabetic(printStream);

            // 3.11 - Total de salários
            printStream.print("\n3.11 - Total dos salários: ");
            actions.printPayrollFormatted(printStream);

            // 3.12 - Salário em salários mínimos
            printStream.print("\n3.12 - Salário em salários mínimos:\n");
            actions.printSalaryInMinimumBase(printStream);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}
