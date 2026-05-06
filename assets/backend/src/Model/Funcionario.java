package Model;

public class Funcionario extends Pessoa {

    private Double salario;

    Funcionario() {
        salario = 0.0;
    }

    Funcionario(Integer cod_Pessoa, String CPF, String nome, String email, String telefone, Double salario) {
        super(cod_Pessoa, CPF, nome, email, telefone);
        this.salario = salario;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

}
