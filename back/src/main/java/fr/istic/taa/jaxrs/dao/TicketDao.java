package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Client;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Ticket;

import java.util.List;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {
    public TicketDao() {
        super(Ticket.class);
    }

    public boolean existsByConcertAndPlace(String place, Concert concert) {
        return entityManager
                .createNamedQuery("Ticket.existsByPlaceAndConcert", Boolean.class)
                .setParameter("place", place)
                .setParameter("concert", concert)
                .getSingleResult();
    }

    public long countByConcert(Concert concert) {
        return entityManager.createQuery("select count(t) from Ticket t where t.concert = :concert", Long.class)
                .setParameter("concert", concert)
                .getSingleResult();
    }

    public List<Ticket> findByClient(Client client) {
        return entityManager.createQuery(
                        "select t from Ticket t where t.client = :client",
                        Ticket.class
                )
                .setParameter("client", client)
                .getResultList();
    }

    public List<Ticket> findByConcert(Concert concert) {
        return entityManager.createQuery(
                        "select t from Ticket t where t.concert = :concert",
                        Ticket.class
                )
                .setParameter("concert", concert)
                .getResultList();
    }

    public List<Ticket> findByCommandeId(Long commandeId) {
        return entityManager.createQuery(
                        "select t from Ticket t where t.commande.id = :commandeId",
                        Ticket.class
                )
                .setParameter("commandeId", commandeId)
                .getResultList();
    }
}
