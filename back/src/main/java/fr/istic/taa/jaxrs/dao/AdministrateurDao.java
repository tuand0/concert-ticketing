package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Administrateur;

import java.util.List;

public class AdministrateurDao extends AbstractJpaDao<Long, Administrateur> {
    public AdministrateurDao() {
        super(Administrateur.class);
    }

    public List<Administrateur> findAllActive() {
        return getEntityManager()
                .createQuery("""
                SELECT a
                FROM Administrateur a
                WHERE a.actif = true
            """, Administrateur.class)
                .getResultList();
    }
}
