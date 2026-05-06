package Model;

public class PessoaJuridica extends Pessoa {

    private String CNPJ;

    PessoaJuridica() {
        CNPJ = new String();
    }

    PessoaJuridica(Integer cod_Pessoa, String CPF, String nome, String email, String telefone, String CNPJ) {
        super(cod_Pessoa, CPF, nome, email, telefone);
        this.CNPJ = CNPJ;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String cNPJ) {
        CNPJ = cNPJ;
    }

}
