package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.pojo.Loja;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class LojaDAO extends GenericDAO<Loja>{
    
    @Override
    public void save(Loja loja) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(loja);
        tx.commit();
        em.close();
    }
    
    @Override
    public List<Loja> getAll() {
        EntityManager em = this.getEntityManager();
        Query q = em.createQuery("select e from Loja e", Loja.class);
        List<Loja> lojas = q.getResultList();
        em.close();
        return lojas;
    }

    @Override
    public void delete(Loja loja) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        loja = em.getReference(Loja.class, loja.getId());
        tx.begin();
        em.remove(loja);
        tx.commit();
    }
    
    @Override
    public void update(Loja loja) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(loja);
        tx.commit();
        em.close();
    }

    @Override
    public Loja get(Long id) {
        EntityManager em = this.getEntityManager();
        Loja loja = em.find(Loja.class, id);
        em.close();
        return loja;
    }
    
    public Loja getByNome(String nome) {
        EntityManager em = this.getEntityManager();
        String sql = "SELECT e FROM Loja e WHERE e.nome = :nome";
        TypedQuery<Loja> q = em.createQuery(sql, Loja.class);
        q.setParameter("nome", nome);
        return q.getSingleResult();
    }
}
