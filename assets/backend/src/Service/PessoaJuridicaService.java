package Service;

import java.util.Scanner;

public class PessoaJuridicaService {

    private final Scanner scanner = new Scanner(System.in);

    // ───────────────────────────────── CNPJ ─────────────────────────────────

    public String lerEValidarCNPJ() {
        while (true) {
            System.out.print("Digite o CNPJ (somente números ou com pontuação): ");
            String entrada = scanner.nextLine().trim();

            // Remove formatação: 00.000.000/0000-00 → 00000000000000
            String cnpj = entrada.replaceAll("[.\\-/]", "");

            if (cnpj.isEmpty()) {
                System.out.println("✗ CNPJ não pode ser vazio.");
                continue;
            }
            if (!cnpj.matches("\\d{14}")) {
                System.out.println("✗ CNPJ deve conter exatamente 14 dígitos numéricos.");
                continue;
            }
            if (!isCNPJValido(cnpj)) {
                System.out.println("✗ CNPJ inválido (dígitos verificadores incorretos).");
                continue;
            }

            // Retorna formatado: 00.000.000/0000-00
            String formatado = cnpj.replaceAll(
                "(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})",
                "$1.$2.$3/$4-$5"
            );
            System.out.println("✓ CNPJ válido: " + formatado);
            return formatado;
        }
    }

    /**
     * Valida os dois dígitos verificadores do CNPJ pelo algoritmo oficial da Receita Federal.
     * Rejeita sequências com todos os dígitos iguais (ex: 00000000000000, 11111111111111).
     */
    private boolean isCNPJValido(String cnpj) {

        // Rejeita sequências com todos os dígitos iguais
        if (cnpj.chars().distinct().count() == 1) return false;

        // ── Primeiro dígito verificador ──
        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        for (int i = 0; i < 12; i++)
            soma += (cnpj.charAt(i) - '0') * pesos1[i];

        int primeiro = soma % 11;
        primeiro = (primeiro < 2) ? 0 : 11 - primeiro;

        if (primeiro != (cnpj.charAt(12) - '0')) return false;

        // ── Segundo dígito verificador ──
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        soma = 0;
        for (int i = 0; i < 13; i++)
            soma += (cnpj.charAt(i) - '0') * pesos2[i];

        int segundo = soma % 11;
        segundo = (segundo < 2) ? 0 : 11 - segundo;

        return segundo == (cnpj.charAt(13) - '0');
    }
}