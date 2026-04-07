package fr.istic.taa.jaxrs.services;

import fr.istic.taa.jaxrs.dao.ConcertDao;
import fr.istic.taa.jaxrs.dao.TicketDao;
import fr.istic.taa.jaxrs.dao.UtilisateurDao;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.StatutTicketEnum;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.dto.TicketCreateDTO;
import fr.istic.taa.jaxrs.errors.ConflictException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class TicketService {
    private final ConcertDao concertDao = new ConcertDao();
    private final TicketDao ticketDao = new TicketDao();
    private final UtilisateurDao utilisateurDao = new UtilisateurDao();

    public Ticket findOne(Long id) {
        return ticketDao.findOne(id);
    }

    public List<Ticket> findAll() {
        return ticketDao.findAll();
    }

    public long create(final TicketCreateDTO dto) throws ClientErrorException {
        // Contrôle métier

        // Est-ce que l'id de l'utilisateur fourni est un organisateur ?
        var utilisateur = utilisateurDao.findOne(dto.getUtilisateurId());
        if (utilisateur == null) {
            throw new BadRequestException("Utilisateur non trouvé");
        }

        // Le concert existe-t-il ?
        var concert = concertDao.findOne(dto.getConcertId());
        if (concert == null) {
            throw new NotFoundException("Le concert n'existe pas");
        }

        // Le concert est à venir ?
        if (!concert.getDate().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Le concert a déjà eu lieu");
        }

        // Reste-t-il des places
        long placesRestantes = ticketDao.countByConcert(concert);
        if (placesRestantes >= concert.getCapacite()) {
            throw new ConflictException("Le concert est complet");
        }

        // La place est-elle disponible ?
        if (ticketDao.existsByConcertAndPlace(dto.getNumeroPlace(), concert)) {
            throw new ConflictException("La place " + dto.getNumeroPlace() + " n'est plus disponible");
        }

        // Création de l'entité - Mapping
        Double prixUnitaire = calculPrixUnitaire(concert, dto.getNumeroPlace());
        Ticket ticket = new Ticket();
        ticket.setConcert(concert);
        ticket.setUtilisateur(utilisateur);
        ticket.setDateAchat(LocalDateTime.now());
        ticket.setStatut(StatutTicketEnum.ACHETE);
        ticket.setPrixUnitaire(prixUnitaire);
        ticket.setNumeroPlace(dto.getNumeroPlace());
        concert.setCapacite(concert.getCapacite() - 1);

        ticketDao.save(ticket);
        concertDao.save(concert);
        return ticket.getTicketId();
    }

    private Double calculPrixUnitaire(Concert concert, String numeroPlace) {
        // TODO Calcul en fonction du concert, du genre musical, de la popularité des artistes, etc.
        // TODO + numéro de place
        return 42.0d;
    }
}
