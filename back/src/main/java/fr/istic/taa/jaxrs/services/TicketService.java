package fr.istic.taa.jaxrs.services;

import fr.istic.taa.jaxrs.dao.*;
import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.Client;
import fr.istic.taa.jaxrs.domain.Commande;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.enums.ModePaiementEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutCommandeEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutConcertEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutTicketEnum;
import fr.istic.taa.jaxrs.dto.TicketCreateDTO;
import fr.istic.taa.jaxrs.errors.ConflictException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class TicketService {

    private final ClientDao clientDao = new ClientDao();
    private final ConcertDao concertDao = new ConcertDao();
    private final TicketDao ticketDao = new TicketDao();
    private final CommandeDao commandeDao = new CommandeDao();

    public Ticket findOne(Long id) {
        Ticket ticket = ticketDao.findOne(id);
        if (ticket == null) {
            throw new NotFoundException("Ticket non trouvé");
        }
        return ticket;
    }

    public List<Ticket> findAll() {
        return ticketDao.findAll();
    }

    public List<Ticket> findByClient(Long clientId) {
        Client client = clientDao.findOne(clientId);
        if (client == null) {
            throw new NotFoundException("Client non trouvé");
        }
        return ticketDao.findByClient(client);
    }

    public long create(final TicketCreateDTO dto) throws ClientErrorException {
        try {
            EntityManagerHelper.beginTransaction();

            validateCreateDTO(dto);

            Client client = clientDao.findOne(dto.getClientId());
            if (client == null) {
                throw new NotFoundException("Client non trouvé");
            }

            Concert concert = concertDao.findOne(dto.getConcertId());
            if (concert == null) {
                throw new NotFoundException("Concert non trouvé");
            }

            String numeroPlace = dto.getNumeroPlace().trim();

            validateConcert(concert);
            validateSeatAvailability(concert, numeroPlace);

            Commande commande = buildCommande(client, dto.getModePaiement());
            commandeDao.save(commande);

            Ticket ticket = buildTicket(client, concert, commande, dto.getNumeroPlace());
            ticketDao.save(ticket);

            commande.getTickets().add(ticket);
            commande.calculerTotal();
            commandeDao.update(commande);

            EntityManagerHelper.commit();
            return ticket.getId();

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    private void validateCreateDTO(TicketCreateDTO dto) {
        if (dto == null) {
            throw new BadRequestException("La requête est vide");
        }

        if (dto.getClientId() == null) {
            throw new BadRequestException("clientId est obligatoire");
        }

        if (dto.getConcertId() == null) {
            throw new BadRequestException("concertId est obligatoire");
        }

        if (dto.getNumeroPlace() == null || dto.getNumeroPlace().isBlank()) {
            throw new BadRequestException("Le numéro de place est obligatoire");
        }
    }

    private void validateConcert(Concert concert) {
        if (concert.getStatut() != StatutConcertEnum.PUBLIE) {
            throw new BadRequestException("Le concert n'est pas publié");
        }

        if (concert.getDate() == null) {
            throw new BadRequestException("Le concert n'a pas de date définie");
        }

        if (!concert.getDate().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Le concert a déjà eu lieu");
        }

        if (concert.getPrix() == null) {
            throw new BadRequestException("Le prix du concert n'est pas défini");
        }

        if (concert.getCapacite() <= 0) {
            throw new BadRequestException("La capacité du concert est invalide");
        }
    }

    private void validateSeatAvailability(Concert concert, String numeroPlace) {
        long nbTicketsVendus = ticketDao.countByConcert(concert);
        if (nbTicketsVendus >= concert.getCapacite()) {
            throw new ConflictException("Le concert est complet");
        }

        if (ticketDao.existsByConcertAndPlace(numeroPlace, concert)) {
            throw new ConflictException("La place " + numeroPlace + " n'est plus disponible");
        }
    }

    private Commande buildCommande(Client client, String modePaiementAsString) {
        Commande commande = new Commande();
        commande.setClient(client);
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut(StatutCommandeEnum.PAYEE);
        commande.setModePaiement(parseModePaiementEnum(modePaiementAsString));
        return commande;
    }

    private Ticket buildTicket(Client client, Concert concert, Commande commande, String numeroPlace) {
        Ticket ticket = new Ticket();
        ticket.setClient(client);
        ticket.setConcert(concert);
        ticket.setCommande(commande);
        ticket.setNumeroPlace(numeroPlace.trim());
        ticket.setDateAchat(LocalDateTime.now());
        ticket.setPrix(concert.getPrix());
        ticket.setStatut(StatutTicketEnum.ACTIF);
        return ticket;
    }

    private ModePaiementEnum parseModePaiementEnum(String modePaiement) {
        if (modePaiement == null || modePaiement.isBlank()) {
            return ModePaiementEnum.CARTE_BANCAIRE;
        }

        try {
            return ModePaiementEnum.valueOf(modePaiement.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Mode de paiement invalide");
        }
    }

}
