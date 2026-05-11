package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Client;
import fr.istic.taa.jaxrs.domain.Commande;
import fr.istic.taa.jaxrs.domain.enums.StatutCommandeEnum;

import java.util.List;

public class CommandeDao extends AbstractJpaDao<Long, Commande> {

    public CommandeDao() {
        super(Commande.class);
    }

    public List<Commande> findByClientId(Long clientId) {
        return getEntityManager()
                .createQuery("""
                SELECT DISTINCT c FROM Commande c
                LEFT JOIN FETCH c.tickets t
                LEFT JOIN FETCH t.concert
                WHERE c.client.id = :clientId
                ORDER BY c.dateCommande DESC
            """, Commande.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }

    public Commande findPendingByClientId(Long clientId) {
        List<Commande> commandes = getEntityManager()
                .createQuery("""
                SELECT DISTINCT c FROM Commande c
                LEFT JOIN FETCH c.tickets t
                LEFT JOIN FETCH t.concert
                WHERE c.client.id = :clientId
                AND c.statut = :statut
                ORDER BY c.dateCommande DESC
            """, Commande.class)
                .setParameter("clientId", clientId)
                .setParameter("statut", StatutCommandeEnum.EN_ATTENTE)
                .getResultList();

        return commandes.isEmpty() ? null : commandes.get(0);
    }
}