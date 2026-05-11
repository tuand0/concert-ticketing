package fr.istic.taa.jaxrs.services;

import fr.istic.taa.jaxrs.dao.ClientDao;
import fr.istic.taa.jaxrs.dao.CommandeDao;
import fr.istic.taa.jaxrs.dao.ConcertDao;
import fr.istic.taa.jaxrs.dao.TicketDao;
import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.Client;
import fr.istic.taa.jaxrs.domain.Commande;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.enums.ModePaiementEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutTicketEnum;
import jakarta.ws.rs.BadRequestException;
import fr.istic.taa.jaxrs.domain.enums.StatutCommandeEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CommandeService {

    private final ConcertDao concertDao = new ConcertDao();
    private final ClientDao clientDao = new ClientDao();
    private final CommandeDao commandeDao = new CommandeDao();
    private final TicketDao ticketDao = new TicketDao();

    private void validateClientId(Long clientId) {
        if (clientId == null) {
            throw new BadRequestException("clientId est obligatoire");
        }
    }

    public List<Commande> findByClientId(Long clientId) {
        try {
            EntityManagerHelper.beginTransaction();

            validateClientId(clientId);

            Client client = clientDao.findOne(clientId);
            if (client == null) {
                throw new BadRequestException("Client non trouvé");
            }

            List<Commande> commandes = commandeDao.findByClientId(clientId);

            EntityManagerHelper.commit();
            return commandes;

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    public Commande getCart(Long clientId) {
        try {
            EntityManagerHelper.beginTransaction();

            validateClientId(clientId);

            Commande commande = commandeDao.findPendingByClientId(clientId);

            if (commande == null) {
                commande = createEmptyCart(clientId);
            }

            EntityManagerHelper.commit();
            return commande;

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    public Commande addConcertToCart(Long clientId, Long concertId) {
        try {
            EntityManagerHelper.beginTransaction();

            validateClientId(clientId);

            Client client = clientDao.findOne(clientId);
            if (client == null) {
                throw new BadRequestException("Client non trouvé");
            }

            Concert concert = concertDao.findOne(concertId);
            if (concert == null) {
                throw new BadRequestException("Concert non trouvé");
            }

            Commande commande = commandeDao.findPendingByClientId(clientId);

            if (commande == null) {
                commande = new Commande();
                commande.setClient(client);
                commande.setDateCommande(LocalDateTime.now());
                commande.setStatut(StatutCommandeEnum.EN_ATTENTE);
                commande.setMontantTotal(BigDecimal.ZERO);
                commandeDao.save(commande);
            }

            Ticket ticket = new Ticket();
            ticket.setCommande(commande);
            ticket.setConcert(concert);
            ticket.setPrix(concert.getPrix());
            ticket.setDateAchat(LocalDateTime.now());
            ticket.setStatut(StatutTicketEnum.ACTIF);

            ticketDao.save(ticket);

            commande.getTickets().add(ticket);
            commande.setMontantTotal(
                    commande.getMontantTotal().add(concert.getPrix())
            );

            EntityManagerHelper.commit();
            return commande;

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    public Commande removeTicket(Long clientId, Long ticketId) {
        Commande commande = commandeDao.findPendingByClientId(clientId);

        if (commande == null) {
            throw new BadRequestException("Aucune commande en attente");
        }

        Ticket ticket = ticketDao.findByIdAndClientId(ticketId, clientId);

        if (ticket == null) {
            throw new BadRequestException("Ticket non trouvé dans le panier");
        }

        commande.getTickets().remove(ticket);
        commande.setMontantTotal(
                commande.getMontantTotal().subtract(ticket.getPrix())
        );

        ticketDao.delete(ticket);

        return commande;
    }

    public Commande confirmCart(Long clientId) {
        try {
            EntityManagerHelper.beginTransaction();

            validateClientId(clientId);

            Commande commande = commandeDao.findPendingByClientId(clientId);

            if (commande == null || commande.getTickets() == null || commande.getTickets().isEmpty()) {
                throw new BadRequestException("Le panier est vide");
            }

            for (Ticket ticket : commande.getTickets()) {
                Concert concert = ticket.getConcert();

                if (concert == null) {
                    throw new BadRequestException("Concert introuvable pour le ticket " + ticket.getId());
                }

                if (concert.getCapacite() <= 0) {
                    throw new BadRequestException("Plus de places disponibles pour le concert " + concert.getTitre());
                }

                concert.setCapacite(concert.getCapacite() - 1);
            }

            commande.setStatut(StatutCommandeEnum.PAYEE);
            commande.setModePaiement(ModePaiementEnum.CARTE_BANCAIRE);

            EntityManagerHelper.commit();
            return commande;

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    private Commande createEmptyCart(Long clientId) {
        Client client = clientDao.findOne(clientId);

        if (client == null) {
            throw new BadRequestException("Client non trouvé");
        }

        Commande commande = new Commande();
        commande.setClient(client);
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut(StatutCommandeEnum.EN_ATTENTE);
        commande.setMontantTotal(BigDecimal.ZERO);

        commandeDao.save(commande);

        return commande;
    }
}
