package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Client;

import java.util.List;

public class ClientDao extends AbstractJpaDao<Long, Client> {
    public ClientDao() {
        super(Client.class);
    }

    public List<Client> findAll() {
        return getEntityManager()
                .createQuery("SELECT c FROM Client c", Client.class)
                .getResultList();
    }
}