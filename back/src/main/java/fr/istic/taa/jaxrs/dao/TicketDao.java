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

    public boolean existsByConcertId(Long concertId) {
        Long count = getEntityManager()
                .createQuery("""
                SELECT COUNT(t)
                FROM Ticket t
                WHERE t.concert.id = :concertId
            """, Long.class)
                .setParameter("concertId", concertId)
                .getSingleResult();

        return count > 0;
    }

    public long countByConcert(Concert concert) {
        return getEntityManager().createQuery("select count(t) from Ticket t where t.concert = :concert", Long.class)
                .setParameter("concert", concert)
                .getSingleResult();
    }

    public Ticket findByIdAndClientId(Long ticketId, Long clientId) {
        return getEntityManager()
                .createQuery("""
                SELECT t FROM Ticket t
                JOIN t.commande c
                WHERE t.id = :ticketId
                AND c.client.id = :clientId
            """, Ticket.class)
                .setParameter("ticketId", ticketId)
                .setParameter("clientId", clientId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<Ticket> findByConcertId(Long concertId) {
        return getEntityManager().createQuery("""
                                SELECT t
                                FROM Ticket t
                                WHERE t.concert.id = :concertId""",
                        Ticket.class
                )
                .setParameter("concertId", concertId)
                .getResultList();
    }

    public List<Client> findClientsByConcertId(Long concertId) {
        return getEntityManager()
                .createQuery("""
                SELECT DISTINCT c.client
                FROM Ticket t
                JOIN t.commande c
                WHERE t.concert.id = :concertId
            """, Client.class)
                .setParameter("concertId", concertId)
                .getResultList();
    }

//    public List<Ticket> findByCommandeId(Long commandeId) {
//        return getEntityManager().createQuery(
//                        "select t from Ticket t where t.commande.id = :commandeId",
//                        Ticket.class
//                )
//                .setParameter("commandeId", commandeId)
//                .getResultList();
//    }
}
