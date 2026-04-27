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

//    public boolean existsByConcertAndPlace(String place, Concert concert) {
//        return getEntityManager()
//                .createNamedQuery("Ticket.existsByPlaceAndConcert", Boolean.class)
//                .setParameter("place", place)
//                .setParameter("concert", concert)
//                .getSingleResult();
//    }

    public boolean existsByConcertAndPlace(String place, Concert concert) {
        Long count = getEntityManager()
                .createQuery("""
                select count(t)
                from Ticket t
                where t.numeroPlace = :place
                and t.concert = :concert
            """, Long.class)
                .setParameter("place", place)
                .setParameter("concert", concert)
                .getSingleResult();

        return count > 0;
    }

    public long countByConcert(Concert concert) {
        return getEntityManager().createQuery("select count(t) from Ticket t where t.concert = :concert", Long.class)
                .setParameter("concert", concert)
                .getSingleResult();
    }

    public List<Ticket> findByClient(Client client) {
        return getEntityManager().createQuery(
                        "select t from Ticket t where t.commande.client = :client",
                        Ticket.class
                )
                .setParameter("client", client)
                .getResultList();
    }

    public List<Ticket> findByConcert(Concert concert) {
        return getEntityManager().createQuery(
                        "select t from Ticket t where t.concert = :concert",
                        Ticket.class
                )
                .setParameter("concert", concert)
                .getResultList();
    }

    public List<Ticket> findByCommandeId(Long commandeId) {
        return getEntityManager().createQuery(
                        "select t from Ticket t where t.commande.id = :commandeId",
                        Ticket.class
                )
                .setParameter("commandeId", commandeId)
                .getResultList();
    }
}
