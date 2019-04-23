package model;

public class Locadora {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String cidade;
    private int cnpj;

    public Locadora(Integer id) {
        this.id = id;
    }

    public Locadora(String nome, String email, String senha, int cnpj, String cidade) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cnpj = cnpj;
        this.cidade = cidade;

    }

    public Locadora(Integer id, String nome, String email, String senha, int cnpj, String cidade) {
        this(nome, email, senha, cnpj, cidade);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getCnpj() {
        return nome;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
