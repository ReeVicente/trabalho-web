package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.pojo.Aluguel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class AluguelDAO extends GenericDAO<Aluguel>{
    
    @Override
    public void save(Aluguel aluguel) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(aluguel);
        tx.commit();
        em.close();
    }
    
    @Override
    public List<Aluguel> getAll() {
        EntityManager em = this.getEntityManager();
        Query q = em.createQuery("select l from Aluguel l", Aluguel.class);
        List<Aluguel> alugueis = q.getResultList();
        em.close();
        return alugueis;
    }

    @Override
    public void delete(Aluguel aluguel) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        aluguel = em.getReference(Aluguel.class, aluguel.getId());
        tx.begin();
        em.remove(aluguel);
        tx.commit();
    }
    
    @Override
    public void update(Aluguel aluguel) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(aluguel);
        tx.commit();
        em.close();
    }

    @Override
    public Aluguel get(Long id) {
        EntityManager em = this.getEntityManager();
        Aluguel aluguel = em.find(Aluguel.class, id);
        em.close();
        return aluguel;
    }
}
