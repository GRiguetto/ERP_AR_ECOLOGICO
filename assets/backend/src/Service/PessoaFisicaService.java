package Service;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PessoaFisicaService {

    private final Scanner scanner = new Scanner(System.in);

    // ────────────────────────── DATA DE NASCIMENTO ──────────────────────────

    public String lerEValidarDataNascimento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.print("Digite a data de nascimento (dd/MM/yyyy): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("✗ Data de nascimento não pode ser vazia.");
                continue;
            }

            // Valida o formato antes de tentar parsear
            if (!entrada.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("✗ Formato inválido. Use dd/MM/yyyy (ex: 25/03/1990).");
                continue;
            }

            LocalDate dataNascimento;
            try {
                // withResolverStyle STRICT rejeita datas inexistentes como 31/02/2000
                dataNascimento = LocalDate.parse(entrada,
                    formatter.withResolverStyle(java.time.format.ResolverStyle.STRICT)
                        .withChronology(java.time.chrono.IsoChronology.INSTANCE));

                // Usa formato com u (ano) em vez de y (era) para o STRICT funcionar
                dataNascimento = LocalDate.parse(entrada,
                    DateTimeFormatter.ofPattern("dd/MM/uuuu")
                        .withResolverStyle(java.time.format.ResolverStyle.STRICT));

            } catch (DateTimeParseException e) {
                System.out.println("✗ Data inexistente (ex: 31/02 não existe). Verifique dia e mês.");
                continue;
            }

            LocalDate hoje = LocalDate.now();

            // Não permite data futura
            if (dataNascimento.isAfter(hoje)) {
                System.out.println("✗ Data de nascimento não pode ser no futuro.");
                continue;
            }

            // Calcula a idade
            int idade = Period.between(dataNascimento, hoje).getYears();

            // Idade mínima: 15 anos (recém-nascido)
            if (idade < 15) {
                System.out.println("✗ Data inválida.");
                continue;
            }

            // Idade máxima razoável: 150 anos
            if (idade > 150) {
                System.out.println("✗ Data de nascimento inválida (idade superior a 150 anos).");
                continue;
            }

            System.out.println("✓ Data válida. Idade: " + idade + " ano(s).");
            return dataNascimento.format(formatter); // Retorna no formato dd/MM/yyyy
        }
    }
}