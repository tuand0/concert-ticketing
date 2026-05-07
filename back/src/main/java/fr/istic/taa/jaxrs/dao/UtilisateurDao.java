package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Utilisateur;
import jakarta.persistence.NoResultException;

public class UtilisateurDao extends AbstractJpaDao<Long, Utilisateur> {
    public UtilisateurDao() {
        super(Utilisateur.class);
    }

    public Utilisateur findByEmail(String email) {
        try {
            return getEntityManager()
                    .createQuery("select p from Utilisateur p where p.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
