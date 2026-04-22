package fr.istic.taa.jaxrs;

import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class DataInitializer {
    private static final Logger logger = Logger.getLogger(DataInitializer.class.getName());

    private DataInitializer() {}

    public static void initialize() {
        new DataInitializer()._initialize();
    }

    private void _initialize() {
        EntityManager manager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            // Création d'un concert + 2 tickets
            Concert concert = createConcert();
            concert.setTickets(List.of(
                    createTicket("1",new BigDecimal("100"), concert),
                    createTicket("2",new BigDecimal("200"), concert)
            ));

            // Création utilisateurs
            manager.persist(createAdministrateur());
            manager.persist(createOrganisateur());
            manager.persist(createClient());
        } catch (Exception e) {
            tx.rollback();
            logger.severe(e.getMessage());
        }
        tx.commit();
    }

//    private Set<Artiste> createArtistes() {
//        Set<Artiste> artistes = new HashSet<>();
//
////        {
////            Artiste artiste = new Artiste();
////            artiste.setNomScene("Stellar");
////            artiste.setNom("Durand");
////            artiste.setPrenom("Lucas");
////            artiste.setNationalite("FR");
////            artiste.setPopularite(82);
////            artiste.setDateNaissance(LocalDate.of(1995, 4, 12));
////            artistes.add(artiste);
////        }
//        return artistes;
//    }

//    private Utilisateur createUtilisateur() {
//        Utilisateur util = new Utilisateur();
//        util.setNom("DUPONT");
//        util.setPrenom("Michel");
//        util.setDateNaissance(LocalDate.of(1990, 1, 1));
//        util.setEmail("michel.dupont@yopmail.com");
//        util.setDateInscription(LocalDate.now());
//        util.setCreditCompte(45.0d);
//        util.setPreferenceNotificationEmail(false);
//        util.setPreferenceNotificationPush(true);
//        return util;
//    }

    private Utilisateur createUtilisateur(){

    }

    private Administrateur createAdministrateur() {
        Administrateur admin = new Administrateur();
        admin.setUserNom("admin");
        admin.setHashedPassword("admin");
        admin.setNom("LECHEF");
        admin.setPrenom("Baptiste");
        admin.setTelephone("0102030405");
        admin.setEmail("baptiste.lechef@yopmail.com");
        admin.setActif(true);
        return admin;
    }

    private Client createClient() {
        Client client = new Client();
        client.setUserNom("client");
        client.setHashedPassword("client");
        client.setNom("DUPONT");
        client.setPrenom("George");
        client.setTelephone("0902030405");
        client.setEmail("george.dupont@yopmail.com");
        return client;
    }

    private Organisateur createOrganisateur() {
        Organisateur orga = new Organisateur();
        orga.setHashedPassword("organisateur");
        orga.setUserNom("organisateur");
        orga.setNom("COMBOURG");
        orga.setPrenom("Adeline");
        orga.setEmail("adeline.combourg2@yopmail.com");
        orga.setActif(true);
        orga.setNomEntreprise("Rock en scène");
        orga.setSiret("523 299 410 00531");
        return orga;
    }

    private Concert createConcert() {
        Concert concert = new Concert();
        concert.setTitre("Mythos");
        concert.setArtiste("Imagine Dragon");
        concert.setDate(LocalDateTime.now().plusDays(7));
        concert.setDescription("Super concert!");
        return concert;
    }

    private Ticket createTicket(String numeroPlace, BigDecimal prix, Concert concert) {
        Ticket ticket = new Ticket("1",BigDecimal.valueOf(100), concert);
        ticket.setPrix(prix);
        ticket.setNumeroPlace("1");
        ticket.setConcert(concert);
        return ticket;
    }
}


