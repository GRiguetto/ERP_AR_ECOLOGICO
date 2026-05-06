package Service;

import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class FuncionarioService {

    private final Scanner scanner = new Scanner(System.in);

    // ─────────────────────────────── SALÁRIO ────────────────────────────────

    public Double lerEValidarSalario() {
        // Salário mínimo vigente no Brasil (2025)
        final double SALARIO_MINIMO = 1518.00;
        final double SALARIO_MAXIMO = 999999.99;

        NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        while (true) {
            System.out.print("Digite o salário (ex: 2500.00 ou 2500,00): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("✗ Salário não pode ser vazio.");
                continue;
            }

            // Remove prefixos como "R$" e espaços
            entrada = entrada.replaceAll("[R$\\s]", "");

            // Detecta e normaliza separadores:
            // Caso "1.500,00" (formato BR) → "1500.00"
            // Caso "1,500.00" (formato EN) → "1500.00"
            // Caso "1500,00"               → "1500.00"
            // Caso "1500.00"               → mantém
            if (entrada.matches("\\d{1,3}(\\.\\d{3})+(,\\d{1,2})?")) {
                // Formato BR com milhar: 1.500,00
                entrada = entrada.replace(".", "").replace(",", ".");
            } else if (entrada.matches("\\d{1,3}(,\\d{3})+(\\.\\d{1,2})?")) {
                // Formato EN com milhar: 1,500.00
                entrada = entrada.replace(",", "");
            } else {
                // Sem separador de milhar: substitui vírgula decimal por ponto
                entrada = entrada.replace(",", ".");
            }

            // Valida se é um número decimal válido após normalização
            if (!entrada.matches("\\d+(\\.\\d{1,2})?")) {
                System.out.println("✗ Valor inválido. Digite apenas números (ex: 2500.00).");
                continue;
            }

            double salario;
            try {
                salario = Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("✗ Valor inválido. Não foi possível converter o salário.");
                continue;
            }

            // Não permite salário zerado ou negativo
            if (salario <= 0) {
                System.out.println("✗ Salário deve ser maior que zero.");
                continue;
            }

            // Valida salário mínimo
            if (salario < SALARIO_MINIMO) {
                System.out.printf("✗ Salário abaixo do mínimo legal. Mínimo permitido: %s%n",
                    moeda.format(SALARIO_MINIMO));
                continue;
            }

            // Valida teto máximo
            if (salario > SALARIO_MAXIMO) {
                System.out.printf("✗ Salário acima do limite permitido. Máximo: %s%n",
                    moeda.format(SALARIO_MAXIMO));
                continue;
            }

            // Arredonda para 2 casas decimais (evita floating point como 2500.0000000001)
            salario = Math.round(salario * 100.0) / 100.0;

            System.out.printf("✓ Salário válido: %s%n", moeda.format(salario));
            return salario;
        }
    }
}