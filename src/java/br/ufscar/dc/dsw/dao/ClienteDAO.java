package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.pojo.Cliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class ClienteDAO extends GenericDAO<Cliente>{
    
    @Override
    public void save(Cliente cliente) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(cliente);
        tx.commit();
        em.close();
    }
    
    @Override
    public List<Cliente> getAll() {
        EntityManager em = this.getEntityManager();
        Query q = em.createQuery("select e from Cliente e", Cliente.class);
        List<Cliente> clientes = q.getResultList();
        em.close();
        return clientes;
    }
    
    

    @Override
    public void delete(Cliente cliente) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        cliente = em.getReference(Cliente.class, cliente.getId());
        tx.begin();
        em.remove(cliente);
        tx.commit();
    }
    
    @Override
    public void update(Cliente cliente) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(cliente);
        tx.commit();
        em.close();
    }

    @Override
    public Cliente get(Long id) {
        EntityManager em = this.getEntityManager();
        Cliente cliente = em.find(Cliente.class, id);
        em.close();
        return cliente;
    }
    
    public Cliente getByNome(String nome) {
        EntityManager em = this.getEntityManager();
        String sql = "SELECT e FROM Cliente e WHERE e.nome = :nome";
        TypedQuery<Cliente> q = em.createQuery(sql, Cliente.class);
        q.setParameter("nome", nome);
        return q.getSingleResult();
    }
    
    public Cliente getByEmail(String email) {
        EntityManager em = this.getEntityManager();
        String sql = "SELECT e FROM Cliente e WHERE e.email = :email";
        TypedQuery<Cliente> q = em.createQuery(sql, Cliente.class);
        q.setParameter("email", email);
        return q.getSingleResult();
    }
}
