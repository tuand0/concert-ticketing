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
import java.util.List;
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

            Concert concert1 = createConcert("Coldplay Live", "Coldplay", "Paris", 80, GenreEnum.ROCK, StatutConcertEnum.PUBLIE);
            Concert concert2 = createConcert("Imagine Dragons Tour", "Imagine Dragons", "Lyon", 60,  GenreEnum.ROCK, StatutConcertEnum.PUBLIE);
            Concert concert3 = createConcert("DJ Snake Night", "DJ Snake", "Marseille", 50,  GenreEnum.EDM, StatutConcertEnum.PUBLIE);
            Concert concert4 = createConcert("Rock Festival", "Various Artists", "Rennes", 40,  GenreEnum.ROCK, StatutConcertEnum.PUBLIE);
            Concert concert5 = createConcert("Jazz Evening", "John Doe Quartet", "Nice", 30,  GenreEnum.JAZZ, StatutConcertEnum.PUBLIE);

            Concert concert6 = createConcert("Pop Night", "Dua Lipa", "Paris", 75,  GenreEnum.POP, StatutConcertEnum.PUBLIE);
            Concert concert7 = createConcert("Electronic Vibes", "David Guetta", "Nice", 65, GenreEnum.EDM, StatutConcertEnum.PUBLIE);
            Concert concert8 = createConcert("Rap Session", "Orelsan", "Caen", 45,  GenreEnum.RAP, StatutConcertEnum.PUBLIE);
            Concert concert9 = createConcert("Symphonic Dreams", "Orchestre National", "Strasbourg", 55,  GenreEnum.POP, StatutConcertEnum.PUBLIE);
            Concert concert10 = createConcert("Indie Rock Live", "Arctic Monkeys", "Bordeaux", 70,  GenreEnum.ROCK, StatutConcertEnum.PUBLIE);

            Concert concert11 = createConcert("Reggae Sun", "Dub Inc", "Montpellier", 35,  GenreEnum.HIPHOP, StatutConcertEnum.PUBLIE);
            Concert concert12 = createConcert("Metal Storm", "Metallica Tribute", "Lille", 85,  GenreEnum.METAL_ROCK, StatutConcertEnum.PUBLIE);
            Concert concert13 = createConcert("Acoustic Evening", "Ben Howard", "Nantes", 25,  GenreEnum.POP, StatutConcertEnum.PUBLIE);
            Concert concert14 = createConcert("Hip Hop Arena", "Ninho", "Paris", 50,  GenreEnum.HIPHOP, StatutConcertEnum.PUBLIE);
            Concert concert15 = createConcert("Techno Warehouse", "Amelie Lens", "Lyon", 55,  GenreEnum.EDM, StatutConcertEnum.PUBLIE);

            Concert concert16 = createConcert("K-Pop Party", "Seoul Stars", "Paris", 70,  GenreEnum.KPOP, StatutConcertEnum.PUBLIE);
            Concert concert17 = createConcert("Opera Gala", "Opéra de Paris", "Paris", 90,  GenreEnum.POP, StatutConcertEnum.PUBLIE);
            Concert concert18 = createConcert("Punk Rock Show", "Green Day Tribute", "Toulouse", 40,  GenreEnum.ROCK, StatutConcertEnum.PUBLIE);
            Concert concert19 = createConcert("Afrobeat Night", "Fela Spirit", "Montpellier", 35,  GenreEnum.POP, StatutConcertEnum.PUBLIE);
            Concert concert20 = createConcert("Cinema Music Live", "Movie Orchestra", "Strasbourg", 65,  GenreEnum.POP, StatutConcertEnum.PUBLIE);

            Concert concert21 = createConcert("Country Roads", "Nashville Band", "Caen", 30,  GenreEnum.POP, StatutConcertEnum.PUBLIE);
            Concert concert22 = createConcert("Summer Festival", "Various Artists", "Nice", 95,  GenreEnum.POP, StatutConcertEnum.PUBLIE);
            Concert concert23 = createConcert("Urban Beats", "SCH", "Marseille", 60,  GenreEnum.RAP, StatutConcertEnum.PUBLIE);
            Concert concert24 = createConcert("Electro Sunset", "Martin Garrix", "Cannes", 85,  GenreEnum.EDM, StatutConcertEnum.PUBLIE);
            Concert concert25 = createConcert("Jazz & Blues Night", "Blue Note Trio", "Lyon", 35,  GenreEnum.JAZZ, StatutConcertEnum.PUBLIE);

            Concert concert26 = createConcert("Metal Legends", "Iron Maiden Tribute", "Grenoble", 80,  GenreEnum.METAL_ROCK, StatutConcertEnum.BROUILLON);
            Concert concert27 = createConcert("Korean Wave", "Black Seoul", "Paris", 90,  GenreEnum.KPOP, StatutConcertEnum.BROUILLON);
            Concert concert28 = createConcert("Street Flow", "PNL", "Toulouse", 55,  GenreEnum.HIPHOP, StatutConcertEnum.BROUILLON);
            Concert concert29 = createConcert("Classic Pop Hits", "The Pop Band", "Nantes", 45,  GenreEnum.POP, StatutConcertEnum.BROUILLON);
            Concert concert30 = createConcert("Rock Revolution", "Muse Tribute", "Lille", 75,  GenreEnum.ROCK, StatutConcertEnum.BROUILLON);

            List.of(
                    concert1, concert2, concert3, concert4, concert5,
                    concert6, concert7, concert8, concert9, concert10,
                    concert11, concert12, concert13, concert14, concert15,
                    concert16, concert17, concert18, concert19, concert20,
                    concert21, concert22, concert23, concert24, concert25,
                    concert26, concert27, concert28, concert29, concert30
            ).forEach(manager::persist);


            Commande commande = createCommande(client);
            manager.persist(commande);

            Ticket ticket = createTicket(concert1, commande);
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
        client.setNom("DUPONT");
        client.setPrenom("George");
        client.setTelephone("0902030405");
        client.setEmail("client@yopmail.com");
        // passw0rd
        client.setHashedPassword("$2a$12$W2npC1gc9w.bbOmdLdMF.O6zXvYKuWphkoqYjWPjF2/3Jnyq.yCz6");
        return client;
    }

    private Concert createConcert(String titre, String artiste, String ville, int prix, GenreEnum genre, StatutConcertEnum status) {
        Concert concert = new Concert();
        concert.setTitre(titre);
        concert.setArtiste(artiste);
        concert.setDescription("Concert de démonstration");
        concert.setDate(LocalDateTime.now().plusDays((int)(Math.random() * 30 + 1)));
        concert.setLieu("Salle principale");
        concert.setVille(ville);
        concert.setGenre(genre);
        concert.setPrix(BigDecimal.valueOf(prix));
        concert.setCapacite(5);
        concert.setStatut(status);
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

    private Ticket createTicket(Concert concert, Commande commande) {
        Ticket ticket = new Ticket();
        ticket.setConcert(concert);
        ticket.setCommande(commande);
        ticket.setDateAchat(LocalDateTime.now());
        ticket.setPrix(concert.getPrix());
        ticket.setStatut(StatutTicketEnum.ACTIF);
        return ticket;
    }
}