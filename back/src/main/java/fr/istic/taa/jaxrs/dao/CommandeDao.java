package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Client;
import fr.istic.taa.jaxrs.domain.Commande;

import java.util.List;

public class CommandeDao extends AbstractJpaDao<Long, Commande> {

    public CommandeDao() {
        super(Commande.class);
    }

    public List<Commande> findByClient(Client client) {
        return entityManager.createQuery(
                        "select c from Commande c where c.client = :client",
                        Commande.class
                )
                .setParameter("client", client)
                .getResultList();
    }
}