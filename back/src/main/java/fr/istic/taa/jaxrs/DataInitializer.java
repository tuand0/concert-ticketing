package fr.istic.taa.jaxrs;

import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
                    createTicket("A001", 42.0d, concert),
                    createTicket("E450", 37.5d, concert)
            ));
            // Artistes
            var artistes = createArtistes();
            artistes.forEach(manager::persist);
            concert.getArtistes().addAll(artistes);
            manager.persist(concert);

            // Création utilisateurs
            manager.persist(createAdministrateur());
            manager.persist(createOrganisateur());
            manager.persist(createUtilisateur());
        } catch (Exception e) {
            tx.rollback();
            logger.severe(e.getMessage());
        }
        tx.commit();
    }

    private Set<Artiste> createArtistes() {
        Set<Artiste> artistes = new HashSet<>();

//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Stellar");
//            artiste.setNom("Durand");
//            artiste.setPrenom("Lucas");
//            artiste.setNationalite("FR");
//            artiste.setPopularite(82);
//            artiste.setDateNaissance(LocalDate.of(1995, 4, 12));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Luna Vox");
//            artiste.setNom("Moreau");
//            artiste.setPrenom("Emma");
//            artiste.setNationalite("CA");
//            artiste.setPopularite(74);
//            artiste.setDateNaissance(LocalDate.of(1998, 9, 3));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("DJ Nebula");
//            artiste.setNom("Schmidt");
//            artiste.setPrenom("Karl");
//            artiste.setNationalite("DE");
//            artiste.setPopularite(69);
//            artiste.setDateNaissance(LocalDate.of(1992, 1, 28));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Crimson Beat");
//            artiste.setNom("Rossi");
//            artiste.setPrenom("Marco");
//            artiste.setNationalite("IT");
//            artiste.setPopularite(77);
//            artiste.setDateNaissance(LocalDate.of(1990, 6, 14));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("EchoWave");
//            artiste.setNom("Smith");
//            artiste.setPrenom("Olivia");
//            artiste.setNationalite("US");
//            artiste.setPopularite(91);
//            artiste.setDateNaissance(LocalDate.of(1997, 11, 5));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Noir Silence");
//            artiste.setNom("Dubois");
//            artiste.setPrenom("Hugo");
//            artiste.setNationalite("BE");
//            artiste.setPopularite(63);
//            artiste.setDateNaissance(LocalDate.of(1988, 2, 19));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Velvet Pulse");
//            artiste.setNom("Garcia");
//            artiste.setPrenom("Sofia");
//            artiste.setNationalite("ES");
//            artiste.setPopularite(80);
//            artiste.setDateNaissance(LocalDate.of(1996, 7, 22));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Neon Karma");
//            artiste.setNom("Khan");
//            artiste.setPrenom("Ayaan");
//            artiste.setNationalite("PK");
//            artiste.setPopularite(71);
//            artiste.setDateNaissance(LocalDate.of(1993, 10, 30));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Solaris");
//            artiste.setNom("Ivanov");
//            artiste.setPrenom("Nikita");
//            artiste.setNationalite("RU");
//            artiste.setPopularite(66);
//            artiste.setDateNaissance(LocalDate.of(1991, 5, 9));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Aria Storm");
//            artiste.setNom("Nguyen");
//            artiste.setPrenom("Linh");
//            artiste.setNationalite("VN");
//            artiste.setPopularite(79);
//            artiste.setDateNaissance(LocalDate.of(1999, 12, 1));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Bass Phantom");
//            artiste.setNom("Brown");
//            artiste.setPrenom("Ethan");
//            artiste.setNationalite("GB");
//            artiste.setPopularite(73);
//            artiste.setDateNaissance(LocalDate.of(1987, 8, 17));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Sapphire Sky");
//            artiste.setNom("Lee");
//            artiste.setPrenom("Jisoo");
//            artiste.setNationalite("KR");
//            artiste.setPopularite(88);
//            artiste.setDateNaissance(LocalDate.of(1994, 3, 11));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Rhythm Rogue");
//            artiste.setNom("Silva");
//            artiste.setPrenom("Mateo");
//            artiste.setNationalite("BR");
//            artiste.setPopularite(76);
//            artiste.setDateNaissance(LocalDate.of(1992, 4, 27));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Crystal Note");
//            artiste.setNom("Martin");
//            artiste.setPrenom("Chloe");
//            artiste.setNationalite("FR");
//            artiste.setPopularite(84);
//            artiste.setDateNaissance(LocalDate.of(2000, 6, 6));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Urban Mirage");
//            artiste.setNom("Haddad");
//            artiste.setPrenom("Yanis");
//            artiste.setNationalite("MA");
//            artiste.setPopularite(67);
//            artiste.setDateNaissance(LocalDate.of(1995, 1, 2));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Zenith Flow");
//            artiste.setNom("Petrov");
//            artiste.setPrenom("Irina");
//            artiste.setNationalite("BG");
//            artiste.setPopularite(62);
//            artiste.setDateNaissance(LocalDate.of(1989, 9, 14));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Golden Vibe");
//            artiste.setNom("Okafor");
//            artiste.setPrenom("Chinedu");
//            artiste.setNationalite("NG");
//            artiste.setPopularite(81);
//            artiste.setDateNaissance(LocalDate.of(1993, 12, 21));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("NovaSoul");
//            artiste.setNom("Anderson");
//            artiste.setPrenom("Mia");
//            artiste.setNationalite("AU");
//            artiste.setPopularite(86);
//            artiste.setDateNaissance(LocalDate.of(1996, 5, 4));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Pulse Raider");
//            artiste.setNom("Kowalski");
//            artiste.setPrenom("Tomasz");
//            artiste.setNationalite("PL");
//            artiste.setPopularite(70);
//            artiste.setDateNaissance(LocalDate.of(1991, 10, 8));
//            artistes.add(artiste);
//        }
//
//        {
//            Artiste artiste = new Artiste();
//            artiste.setNomScene("Aurora Tone");
//            artiste.setNom("Johansson");
//            artiste.setPrenom("Elin");
//            artiste.setNationalite("SE");
//            artiste.setPopularite(83);
//            artiste.setDateNaissance(LocalDate.of(1997, 7, 19));
//            artistes.add(artiste);
//        }
        return artistes;
    }

    private Utilisateur createUtilisateur() {
        Utilisateur util = new Utilisateur();
        util.setNom("DUPONT");
        util.setPrenom("Michel");
        util.setDateNaissance(LocalDate.of(1990, 1, 1));
        util.setEmail("michel.dupont@yopmail.com");
        util.setDateInscription(LocalDate.now());
        util.setCreditCompte(45.0d);
        util.setPreferenceNotificationEmail(false);
        util.setPreferenceNotificationPush(true);
        return util;
    }

    private Administrateur createAdministrateur() {
        Administrateur admin = new Administrateur();
        admin.setNom("LECHEF");
        admin.setPrenom("Baptiste");
        admin.setDateNaissance(LocalDate.of(1980, 7, 25));
        admin.setEmail("baptiste.lechef@yopmail.com");

        admin.setActif(true);
        admin.setDateNomination(LocalDate.of(2025, 12, 31));
        return admin;
    }

    private Organisateur createOrganisateur() {
        Organisateur orga = new Organisateur();
        orga.setNom("COMBOURG");
        orga.setPrenom("Adeline");
        orga.setDateNaissance(LocalDate.of(1990, 3, 17));
        orga.setEmail("adeline.combourg2@yopmail.com");

        orga.setActif(true);
        orga.setNomStructure("Rock en scène");
        orga.setNumeroSiret("523 299 410 00531");
        orga.setAdresseSiege("18, chemin de Faivre, 89731 AUBERT");
        return orga;
    }

    private Concert createConcert() {
        Concert concert = new Concert();
        concert.setCapacite(50L);
        concert.setDate(LocalDateTime.now().plusDays(7));
        concert.setPopularite(3.5f);
        concert.setGenre("VARIETE");
        concert.setDescription("Super concert!");
        return concert;
    }

    private Ticket createTicket(String numeroPlace, Double prixUnitaire, Concert concert) {
        Ticket ticket = new Ticket("A001", 42.0d, concert);
        ticket.setNumeroPlace(numeroPlace);
        ticket.setPrixUnitaire(prixUnitaire);
        ticket.setStatut(StatutTicketEnum.ACHETE);
        ticket.setDateAchat(LocalDateTime.now());
        return ticket;
    }
}
