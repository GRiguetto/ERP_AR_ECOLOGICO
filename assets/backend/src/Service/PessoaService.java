package Service;

import java.util.Scanner;

public class PessoaService {

    private final Scanner scanner = new Scanner(System.in);

    // ───────────────────────────── COD_PESSOA ─────────────────────────────

    public Integer lerEValidarCodPessoa() {
        while (true) {
            System.out.print("Digite o código da pessoa: ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("✗ Código não pode ser vazio.");
                continue;
            }
            try {
                int cod = Integer.parseInt(entrada);
                if (cod <= 0) {
                    System.out.println("✗ Código deve ser um número positivo.");
                    continue;
                }
                return cod;
            } catch (NumberFormatException e) {
                System.out.println("✗ Código inválido. Digite apenas números inteiros.");
            }
        }
    }

    // ───────────────────────────────── CPF ─────────────────────────────────

    public String lerEValidarCPF() {
        while (true) {
            System.out.print("Digite o CPF (somente números ou com pontuação): ");
            String entrada = scanner.nextLine().trim();
            String cpf = entrada.replaceAll("[.\\-]", ""); // remove 000.000.000-00 → 00000000000

            if (!cpf.matches("\\d{11}")) {
                System.out.println("✗ CPF deve conter exatamente 11 dígitos numéricos.");
                continue;
            }
            if (!isCPFValido(cpf)) {
                System.out.println("✗ CPF inválido (dígitos verificadores incorretos).");
                continue;
            }
            // Retorna formatado: 000.000.000-00
            return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
    }

    /**
     * Valida os dois dígitos verificadores do CPF pelo algoritmo oficial da Receita Federal.
     * Rejeita sequências óbvias como "00000000000", "11111111111" etc.
     */
    private boolean isCPFValido(String cpf) {
        // Rejeita sequências com todos os dígitos iguais
        if (cpf.chars().distinct().count() == 1) return false;

        int soma = 0;
        for (int i = 0; i < 9; i++)
            soma += (cpf.charAt(i) - '0') * (10 - i);
        int primeiro = 11 - (soma % 11);
        if (primeiro >= 10) primeiro = 0;
        if (primeiro != (cpf.charAt(9) - '0')) return false;

        soma = 0;
        for (int i = 0; i < 10; i++)
            soma += (cpf.charAt(i) - '0') * (11 - i);
        int segundo = 11 - (soma % 11);
        if (segundo >= 10) segundo = 0;
        return segundo == (cpf.charAt(10) - '0');
    }

    // ──────────────────────────────── NOME ─────────────────────────────────

    public String lerEValidarNome() {
        while (true) {
            System.out.print("Digite o nome completo: ");
            String nome = scanner.nextLine().trim();

            if (nome.isEmpty()) {
                System.out.println("✗ Nome não pode ser vazio.");
                continue;
            }
            if (nome.length() < 3) {
                System.out.println("✗ Nome muito curto (mínimo 3 caracteres).");
                continue;
            }
            if (nome.length() > 100) {
                System.out.println("✗ Nome muito longo (máximo 100 caracteres).");
                continue;
            }
            // Permite letras (incluindo acentos), espaços e hífen (nome composto)
            if (!nome.matches("[\\p{L}\\s\\-']+")) {
                System.out.println("✗ Nome deve conter apenas letras, espaços e hífen.");
                continue;
            }
            // Exige ao menos duas palavras (nome + sobrenome)
            if (nome.trim().split("\\s+").length < 2) {
                System.out.println("✗ Informe nome e sobrenome.");
                continue;
            }
            return nome;
        }
    }

    // ─────────────────────────────── EMAIL ─────────────────────────────────

    public String lerEValidarEmail() {
        while (true) {
            System.out.print("Digite o e-mail: ");
            String email = scanner.nextLine().trim().toLowerCase();

            if (email.isEmpty()) {
                System.out.println("✗ E-mail não pode ser vazio.");
                continue;
            }
            /*
             * Regex cobre:
             *  - parte local: letras, números e os especiais permitidos pelo RFC 5321
             *  - @ obrigatório
             *  - domínio: letras/números/hífen
             *  - pelo menos um ponto no domínio
             *  - extensão entre 2 e 10 caracteres (ex: .com, .com.br, .museum)
             */
            if (!email.matches("^[a-z0-9._%+\\-]+@[a-z0-9.\\-]+\\.[a-z]{2,10}$")) {
                System.out.println("✗ E-mail inválido. Exemplo válido: usuario@email.com");
                continue;
            }
            // Rejeita múltiplos pontos consecutivos
            if (email.contains("..")) {
                System.out.println("✗ E-mail não pode conter pontos consecutivos.");
                continue;
            }
            return email;
        }
    }

    // ─────────────────────────────── TELEFONE ──────────────────────────────

    public String lerEValidarTelefone() {
        while (true) {
            System.out.print("Digite o telefone (com DDD, ex: (11) 91234-5678): ");
            String entrada = scanner.nextLine().trim();

            // Remove tudo exceto dígitos para facilitar a validação
            String digitos = entrada.replaceAll("[^\\d]", "");

            if (digitos.isEmpty()) {
                System.out.println("✗ Telefone não pode ser vazio.");
                continue;
            }
            // Aceita 10 dígitos (fixo: DDD + 8) ou 11 dígitos (celular: DDD + 9)
            if (digitos.length() != 10 && digitos.length() != 11) {
                System.out.println("✗ Telefone deve ter 10 dígitos (fixo) ou 11 dígitos (celular) incluindo o DDD.");
                continue;
            }

            String ddd = digitos.substring(0, 2);
            if (!isDDDValido(ddd)) {
                System.out.println("✗ DDD inválido: " + ddd + ". Informe um DDD brasileiro válido (11-99).");
                continue;
            }

            // Celular com 11 dígitos: 9º dígito deve ser 9
            if (digitos.length() == 11 && digitos.charAt(2) != '9') {
                System.out.println("✗ Celular com 11 dígitos deve começar com 9 após o DDD.");
                continue;
            }

            // Retorna formatado
            return formatarTelefone(digitos);
        }
    }

    /**
     * Valida DDDs brasileiros atribuídos pela Anatel (11–99, excluindo inválidos).
     */
    private boolean isDDDValido(String ddd) {
        int[] dddsValidos = {
            11, 12, 13, 14, 15, 16, 17, 18, 19, // SP
            21, 22, 24,                           // RJ
            27, 28,                               // ES
            31, 32, 33, 34, 35, 37, 38,           // MG
            41, 42, 43, 44, 45, 46,               // PR
            47, 48, 49,                           // SC
            51, 53, 54, 55,                       // RS
            61,                                   // DF
            62, 64,                               // GO
            63,                                   // TO
            65, 66,                               // MT
            67,                                   // MS
            68,                                   // AC
            69,                                   // RO
            71, 73, 74, 75, 77,                   // BA
            79,                                   // SE
            81, 87,                               // PE
            82,                                   // AL
            83,                                   // PB
            84,                                   // RN
            85, 88,                               // CE
            86, 89,                               // PI
            91, 93, 94,                           // PA
            92, 97,                               // AM
            95,                                   // RR
            96,                                   // AP
            98, 99                                // MA
        };
        int dddInt;
        try { dddInt = Integer.parseInt(ddd); } catch (NumberFormatException e) { return false; }
        for (int d : dddsValidos) if (d == dddInt) return true;
        return false;
    }

    private String formatarTelefone(String digitos) {
        String ddd = digitos.substring(0, 2);
        String numero = digitos.substring(2);
        if (numero.length() == 9)
            return "(" + ddd + ") " + numero.substring(0, 5) + "-" + numero.substring(5);
        else
            return "(" + ddd + ") " + numero.substring(0, 4) + "-" + numero.substring(4);
    }
}