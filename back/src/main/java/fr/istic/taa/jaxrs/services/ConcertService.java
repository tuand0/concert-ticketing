package fr.istic.taa.jaxrs.services;

import fr.istic.taa.jaxrs.dao.ConcertDao;
import fr.istic.taa.jaxrs.dao.OrganisateurDao;
import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.enums.GenreEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutConcertEnum;
import fr.istic.taa.jaxrs.dto.ConcertCreateDTO;
import fr.istic.taa.jaxrs.dto.ConcertSearchDTO;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;

public class ConcertService {
    private final ConcertDao concertDao = new ConcertDao();
    private final OrganisateurDao organisateurDao = new OrganisateurDao();

    public List<Concert> searchConcerts(ConcertSearchDTO searchDTO) {
        return concertDao.searchPublishedConcerts(searchDTO);
    }

    public Concert findOne(Long id) {
        Concert concert = concertDao.findOne(id);
        if (concert == null) {
            throw new NotFoundException("Concert non trouvé");
        }
        return concert;
    }

    public long create(ConcertCreateDTO dto) {
        try {
            EntityManagerHelper.beginTransaction();

            validateCreateDTO(dto);
            var organisateur = organisateurDao.findOne(dto.getOrganisateurId());

            if (organisateur == null) {
                throw new BadRequestException("Organisateur non trouvé");
            }

            Concert concert = new Concert();
            concert.setOrganisateur(organisateur);
            concert.setLieu(dto.getLieu());
            concert.setDescription(dto.getDescription());
            concert.setCapacite(dto.getCapacite());
            concert.setDate(dto.getDateTime());
            concert.setTitre(dto.getTitre());
            concert.setArtiste(dto.getArtiste());
            concert.setVille(dto.getVille());
            concert.setPrix(dto.getPrix());
            concert.setGenre(parseGenre(dto.getGenre()));
            concert.setStatut(StatutConcertEnum.BROUILLON);

            concertDao.save(concert);

            EntityManagerHelper.commit();
            return concert.getId();

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    private void validateCreateDTO(ConcertCreateDTO dto) {
        if (dto == null) {
            throw new BadRequestException("La requête est vide");
        }

        if (dto.getOrganisateurId() == null) {
            throw new BadRequestException("organisateurId est obligatoire");
        }

        if (dto.getTitre() == null || dto.getTitre().isBlank()) {
            throw new BadRequestException("Le titre est obligatoire");
        }

        if (dto.getArtiste() == null || dto.getArtiste().isBlank()) {
            throw new BadRequestException("L'artiste est obligatoire");
        }

        if (dto.getLieu() == null || dto.getLieu().isBlank()) {
            throw new BadRequestException("Le lieu est obligatoire");
        }

        if (dto.getVille() == null || dto.getVille().isBlank()) {
            throw new BadRequestException("La ville est obligatoire");
        }

        if (dto.getDateTime() == null) {
            throw new BadRequestException("La date du concert est obligatoire");
        }

        if (!dto.getDateTime().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Le concert doit se tenir à une date future");
        }

        if (dto.getPrix() == null || dto.getPrix().signum() <= 0) {
            throw new BadRequestException("Le prix doit être strictement positif");
        }

        if (dto.getCapacite() <= 0) {
            throw new BadRequestException("La capacité doit être strictement positive");
        }

        if (dto.getGenre() == null || dto.getGenre().isBlank()) {
            throw new BadRequestException("Le genre est obligatoire");
        }
    }

    public List<Concert> findByStatut(StatutConcertEnum statut) {
        try {
            EntityManagerHelper.beginTransaction();

            List<Concert> concerts = concertDao.findByStatut(statut);

            EntityManagerHelper.commit();
            return concerts;

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    public Concert updateStatut(Long concertId, String statutValue) {
        try {
            EntityManagerHelper.beginTransaction();

            Concert concert = concertDao.findOne(concertId);

            if (concert == null) {
                throw new NotFoundException("Concert non trouvé");
            }

            StatutConcertEnum statut;

            try {
                statut = StatutConcertEnum.valueOf(statutValue.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Statut invalide");
            }

            if (statut != StatutConcertEnum.PUBLIE && statut != StatutConcertEnum.ANNULE) {
                throw new BadRequestException("Statut autorisé: PUBLIE ou ANNULE");
            }

            concert.setStatut(statut);

            EntityManagerHelper.commit();
            return concert;

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    public void deleteConcert(Long concertId) {
        try {
            EntityManagerHelper.beginTransaction();

            Concert concert = concertDao.findOne(concertId);

            if (concert == null) {
                throw new NotFoundException("Concert non trouvé");
            }

            concertDao.delete(concert);

            EntityManagerHelper.commit();

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    private GenreEnum parseGenre(String genre) {
        try {
            return GenreEnum.valueOf(genre.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Genre invalide");
        }
    }
}
