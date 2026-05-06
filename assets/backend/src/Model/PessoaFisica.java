package Model;

public class PessoaFisica extends Pessoa {

    private String data_Nascimento;

    public PessoaFisica() {
        data_Nascimento = new String();
    }

    public PessoaFisica(Integer cod_Pessoa, String CPF, String nome, String email, String telefone,
            String data_Nascimento) {
        super(cod_Pessoa, CPF, nome, email, telefone);
        this.data_Nascimento = data_Nascimento;
    }

    public String getData_Nascimento() {
        return data_Nascimento;
    }

    public void setData_Nascimento(String data_Nascimento) {
        this.data_Nascimento = data_Nascimento;
    }

}
