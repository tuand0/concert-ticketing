package fr.istic.taa.jaxrs;

import fr.istic.taa.jaxrs.dao.ClientDao;
import fr.istic.taa.jaxrs.dao.ConcertDao;
import fr.istic.taa.jaxrs.dao.OrganisateurDao;
import fr.istic.taa.jaxrs.dao.TicketDao;
import fr.istic.taa.jaxrs.domain.Client;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.enums.*;
import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class JpaTest {

    public static void main(String[] args) {

        ConcertDao concertDao = new ConcertDao();
        OrganisateurDao organisateurDao = new OrganisateurDao();
        ClientDao clientDao = new ClientDao();
        TicketDao ticketDao = new TicketDao();

        try {
            //Start transaction
            EntityManagerHelper.beginTransaction();

            //Create Organisateur
            Organisateur org = new Organisateur();
            org.setNom("Dupont");
            org.setPrenom("Jean");
            org.setEmail("jean@test.com");
            org.setHashedPassword("1234");
            org.setTelephone("0600000000");

            organisateurDao.save(org);

            //Create Client
            Client client = new Client();
            client.setNom("Do");
            client.setPrenom("Tuan");
            client.setEmail("tuan@test.com");

            clientDao.save(client);

            // Create Concert
            Concert concert = new Concert();
            concert.setTitre("Coldplay Live");
            concert.setArtiste("Coldplay");
            concert.setVille("Paris");
            concert.setLieu("Accor Arena");
            concert.setDate(LocalDateTime.now().plusDays(10));
            concert.setGenre(GenreEnum.EDM);
            concert.setPrix(BigDecimal.valueOf(50));
            concert.setCapacite(100);
            concert.setStatut(StatutConcertEnum.PUBLIE);

            concertDao.save(concert);

            // Create Concert
            Ticket ticket = new Ticket();
            ticket.setClient(client);
            ticket.setConcert(concert);
            ticket.setNumeroPlace("A12");
            ticket.setPrix(concert.getPrix());
            ticket.setDateAchat(LocalDateTime.now());
            ticket.setStatut(StatutTicketEnum.ACTIF);
            ticketDao.save(ticket);

            //Commit transaction
            EntityManagerHelper.commit();

            System.out.println("✅ Concert saved in database!");

            //Read data
            List<Concert> concerts = concertDao.findAll();

            System.out.println("Tickets for concert: " + ticketDao.countByConcert(concert));
            System.out.println("Seat A12 exists: " + ticketDao.existsByConcertAndPlace("A12", concert));
            System.out.println("Tickets for client: " + ticketDao.findByClient(client).size());

        } catch (Exception e) {
            System.out.println("❌ Error occurred, rollback");
            EntityManagerHelper.rollback();
            e.printStackTrace();
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }
}