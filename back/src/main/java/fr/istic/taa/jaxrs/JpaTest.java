package fr.istic.taa.jaxrs;

import fr.istic.taa.jaxrs.dao.ConcertDao;
import fr.istic.taa.jaxrs.dao.OrganisateurDao;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.enums.*;
import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;

import java.time.LocalDateTime;
import java.util.List;

public class JpaTest {

    public static void main(String[] args) {

        ConcertDao concertDao = new ConcertDao();
        OrganisateurDao organisateurDao = new OrganisateurDao();

        try {
            // 🔹 Start transaction
            EntityManagerHelper.beginTransaction();

            // 🔹 Create Organisateur
            Organisateur org = new Organisateur();
            org.setNom("Dupont");
            org.setPrenom("Jean");
            org.setEmail("jean@test.com");
            org.setHashedPassword("1234");
            org.setTelephone("0600000000");

            organisateurDao.save(org);

            // 🔹 Create Concert
            Concert concert = new Concert();
            concert.setTitre("My First Concert");
            concert.setArtiste("Coldplay");
            concert.setLieu("Paris");
            concert.setVille("Paris");
            concert.setDate(LocalDateTime.now());
            concert.setCapacite(100);
            concert.setStatut(StatutConcertEnum.PUBLIE);
            concert.setOrganisateur(org);

            concertDao.save(concert);

            // 🔹 Commit transaction
            EntityManagerHelper.commit();

            System.out.println("✅ Concert saved in database!");

            // 🔹 Read data
            List<Concert> concerts = concertDao.findAll();

            System.out.println("📌 List of concerts:");
            for (Concert c : concerts) {
                System.out.println("- " + c.getTitre() + " in " + c.getVille());
            }

        } catch (Exception e) {
            System.out.println("❌ Error occurred, rollback");
            EntityManagerHelper.rollback();
            e.printStackTrace();
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }
}