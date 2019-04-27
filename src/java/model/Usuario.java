package model;


public class Usuario {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String telefone;
    private String sexo;
    private String datadenascimento;

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(String nome, String email, String cpf, String telefone, String sexo, String datadenascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
        this.sexo = sexo;
        this.datadenascimento = datadenascimento;
        

    }
    
    public Usuario(String nome, String email, String senha, String cpf, String telefone, String sexo, String datadenascimento) {
        this(nome, email, cpf, telefone, sexo, datadenascimento);
        this.senha = senha;
    }

    public Usuario(Integer id, String nome, String email, String senha, String cpf, String telefone, String sexo, String datadenascimento) {
        this(nome, email, senha, cpf, telefone, sexo, datadenascimento);
        this.id = id;
    }
    
    public Usuario(Integer id, String nome, String email, String cpf, String telefone, String sexo, String datadenascimento) {
        this(nome, email, cpf, telefone, sexo, datadenascimento);
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
    
    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
        
    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
        
    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
        
    public String getDatadenascimento() {
        return this.datadenascimento;
    }

    public void setDatadenascimento(String datadenascimento) {
        this.datadenascimento = datadenascimento;
    }
    
}
