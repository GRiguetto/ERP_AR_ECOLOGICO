package Model;

public class Pessoa {

    private Integer cod_Pessoa;
    private String CPF;
    private String nome;
    private String email;
    private String telefone;

    Pessoa() {
        cod_Pessoa = 0;
        CPF = new String();
        nome = new String();
        email = new String();
        telefone = new String();
    }

    Pessoa(Integer cod_Pessoa, String CPF, String nome, String email, String telefone) {
        this.cod_Pessoa = 0;
        this.CPF = new String();
        this.nome = new String();
        this.email = new String();
        this.telefone = new String();
    }

    public Integer getCod_Pessoa() {
        return cod_Pessoa;
    }

    public void setCod_Pessoa(Integer cod_Pessoa) {
        this.cod_Pessoa = cod_Pessoa;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
