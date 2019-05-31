package br.ufscar.dc.dsw.pojo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Aluguel implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String data;
    private String hora;
    
    @ManyToOne
    private Loja loja;
    @ManyToOne
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Loja getLoja() {
        return loja;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}