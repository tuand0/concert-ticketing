package fr.istic.taa.jaxrs.mappers;

import fr.istic.taa.jaxrs.domain.Commande;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.dto.CommandeDTO;
import fr.istic.taa.jaxrs.dto.TicketDTO;

import java.util.ArrayList;
import java.util.List;

public class CommandeMapper {

    public CommandeDTO toDTO(Commande commande) {
        if (commande == null) {
            return null;
        }

        CommandeDTO dto = new CommandeDTO();

        dto.setId(commande.getId());
        dto.setDateCommande(commande.getDateCommande());
        dto.setMontantTotal(commande.getMontantTotal());

        if (commande.getStatut() != null) {
            dto.setStatut(commande.getStatut().name());
        }

        if (commande.getModePaiement() != null) {
            dto.setModePaiement(commande.getModePaiement().name());
        }

        if (commande.getTickets() != null) {
            dto.setTickets(
                    commande.getTickets()
                            .stream()
                            .map(this::toTicketDTO)
                            .toList()
            );
        } else {
            dto.setTickets(new ArrayList<>());
        }

        return dto;
    }

    private TicketDTO toTicketDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();

        dto.setId(ticket.getId());
        dto.setPrix(ticket.getPrix());
        dto.setDateAchat(ticket.getDateAchat());
        dto.setNumeroPlace(ticket.getNumeroPlace());

        if (ticket.getStatut() != null) {
            dto.setStatut(ticket.getStatut().name());
        }

        Concert concert = ticket.getConcert();

        if (concert != null) {
            dto.setConcertId(concert.getId());
            dto.setConcertTitre(concert.getTitre());
            dto.setConcertArtiste(concert.getArtiste());
            dto.setConcertDate(concert.getDate());
            dto.setConcertLieu(concert.getLieu());
            dto.setConcertVille(concert.getVille());
        }

        return dto;
    }

    public List<CommandeDTO> toDTOList(List<Commande> commandes) {
        if (commandes == null) {
            return new ArrayList<>();
        }

        return commandes
                .stream()
                .map(this::toDTO)
                .toList();
    }
}
