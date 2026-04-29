package fr.istic.taa.jaxrs;

import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.Administrateur;
import fr.istic.taa.jaxrs.domain.Client;
import fr.istic.taa.jaxrs.domain.Commande;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.enums.GenreEnum;
import fr.istic.taa.jaxrs.domain.enums.ModePaiementEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutCommandeEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutConcertEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutTicketEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class DataInitializer {

    private static final Logger logger = Logger.getLogger(DataInitializer.class.getName());

    private DataInitializer() {
    }

    public static void initialize() {
        new DataInitializer().init();
    }

    private void init() {
        EntityManager manager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = manager.getTransaction();

        try {
            tx.begin();

            Administrateur admin = createAdministrateur();
            Organisateur organisateur = createOrganisateur();
            Client client = createClient();

            manager.persist(admin);
            manager.persist(organisateur);
            manager.persist(client);

            Concert concert1 = createConcert("Coldplay Live", "Coldplay", "Paris", 80, 5000);
            Concert concert2 = createConcert("Imagine Dragons Tour", "Imagine Dragons", "Lyon", 60, 3000);
            Concert concert3 = createConcert("DJ Snake Night", "DJ Snake", "Marseille", 50, 2000);
            Concert concert4 = createConcert("Rock Festival", "Various Artists", "Rennes", 40, 1000);
            Concert concert5 = createConcert("Jazz Evening", "John Doe Quartet", "Nice", 30, 500);

            manager.persist(concert1);
            manager.persist(concert2);
            manager.persist(concert3);
            manager.persist(concert4);
            manager.persist(concert5);


            Commande commande = createCommande(client);
            manager.persist(commande);

            Ticket ticket = createTicket("A12", concert1, commande);
            manager.persist(ticket);

            commande.getTickets().add(ticket);
            commande.calculerTotal();
            manager.merge(commande);

            tx.commit();
            logger.info("Données d'initialisation créées avec succès.");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            logger.severe("Erreur lors de l'initialisation des données : " + e.getMessage());
            e.printStackTrace();
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    private Administrateur createAdministrateur() {
        Administrateur admin = new Administrateur();
        admin.setUserNom("admin");
        admin.setHashedPassword("admin");
        admin.setNom("LECHEF");
        admin.setPrenom("Baptiste");
        admin.setTelephone("0102030405");
        admin.setEmail("admin@yopmail.com");
        // passw0rd
        admin.setHashedPassword("$2a$12$W2npC1gc9w.bbOmdLdMF.O6zXvYKuWphkoqYjWPjF2/3Jnyq.yCz6");
        admin.setActif(true);
        return admin;
    }

    private Organisateur createOrganisateur() {
        Organisateur orga = new Organisateur();
        orga.setUserNom("organisateur");
        orga.setHashedPassword("organisateur");
        orga.setNom("COMBOURG");
        orga.setPrenom("Adeline");
        orga.setEmail("orga@yopmail.com");
        // passw0rd
        orga.setHashedPassword("$2a$12$W2npC1gc9w.bbOmdLdMF.O6zXvYKuWphkoqYjWPjF2/3Jnyq.yCz6");
        orga.setTelephone("0600000000");
        orga.setActif(true);
        orga.setNomEntreprise("Rock en scène");
        orga.setSiret("52329941000531");
        return orga;
    }

    private Client createClient() {
        Client client = new Client();
        client.setUserNom("client");
        client.setHashedPassword("client");
        client.setNom("DUPONT");
        client.setPrenom("George");
        client.setTelephone("0902030405");
        client.setEmail("client@yopmail.com");
        // passw0rd
        client.setHashedPassword("$2a$12$W2npC1gc9w.bbOmdLdMF.O6zXvYKuWphkoqYjWPjF2/3Jnyq.yCz6");
        return client;
    }

//    private Concert createConcert() {
//        Concert concert = new Concert();
//        concert.setTitre("Mythos");
//        concert.setArtiste("Imagine Dragons");
//        concert.setDescription("Super concert de démonstration");
//        concert.setDate(LocalDateTime.now().plusDays(7));
//        concert.setLieu("Liberté");
//        concert.setVille("Rennes");
//        concert.setGenre(GenreEnum.ROCK);
//        concert.setPrix(new BigDecimal("49.90"));
//        concert.setCapacite(100);
//        concert.setStatut(StatutConcertEnum.PUBLIE);
//        return concert;
//    }
    private Concert createConcert(String titre, String artiste, String ville, int prix, int capacite) {
        Concert concert = new Concert();
        concert.setTitre(titre);
        concert.setArtiste(artiste);
        concert.setDescription("Concert de démonstration");
        concert.setDate(LocalDateTime.now().plusDays((int)(Math.random() * 30 + 1)));
        concert.setLieu("Salle principale");
        concert.setVille(ville);
        concert.setGenre(GenreEnum.ROCK);
        concert.setPrix(BigDecimal.valueOf(prix));
        concert.setCapacite(capacite);
        concert.setStatut(StatutConcertEnum.PUBLIE);
        return concert;
    }

    private Commande createCommande(Client client) {
        Commande commande = new Commande();
        commande.setClient(client);
        commande.setDateCommande(LocalDateTime.now());
        commande.setModePaiement(ModePaiementEnum.CARTE_BANCAIRE);
        commande.setStatut(StatutCommandeEnum.PAYEE);
        commande.setMontantTotal(BigDecimal.ZERO);
        return commande;
    }

    private Ticket createTicket(String numeroPlace, Concert concert, Commande commande) {
        Ticket ticket = new Ticket();
        ticket.setNumeroPlace(numeroPlace);
        ticket.setConcert(concert);
        ticket.setCommande(commande);
        ticket.setDateAchat(LocalDateTime.now());
        ticket.setPrix(concert.getPrix());
        ticket.setStatut(StatutTicketEnum.ACTIF);
        return ticket;
    }
}