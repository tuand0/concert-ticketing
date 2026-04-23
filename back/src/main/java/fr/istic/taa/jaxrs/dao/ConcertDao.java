package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.enums.GenreEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutConcertEnum;
import fr.istic.taa.jaxrs.dto.ConcertSearchDTO;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ConcertDao extends AbstractJpaDao<Long, Concert> {
    public ConcertDao() {
        super(Concert.class);
    }

    public List<Concert> searchConcerts(ConcertSearchDTO searchDTO) {
        var cb = getEntityManager().getCriteriaBuilder();
        var cr = cb.createQuery(Concert.class);
        var root = cr.from(Concert.class);

        cr.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (searchDTO.getTitre() != null && !searchDTO.getTitre().isEmpty()) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("titre")),
                            "%" + searchDTO.getTitre().toLowerCase() + "%"
                    )
            );
        }

        if (searchDTO.getArtiste() != null && !searchDTO.getArtiste().isEmpty()) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("artiste")),
                            "%" + searchDTO.getArtiste().toLowerCase() + "%"
                    )
            );
        }

        if (searchDTO.getVille() != null && !searchDTO.getVille().isEmpty()) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("ville")),
                            "%" + searchDTO.getVille().toLowerCase() + "%"
                    )
            );
        }

        if (searchDTO.getLieu() != null && !searchDTO.getLieu().isEmpty()) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("lieu")),
                            "%" + searchDTO.getLieu().toLowerCase() + "%"
                    )
            );
        }

        if (searchDTO.getGenre() != null && !searchDTO.getGenre().isEmpty()) {
            try {
                GenreEnum genre = GenreEnum.valueOf(searchDTO.getGenre().toUpperCase());
                predicates.add(cb.equal(root.get("genre"), genre));
            } catch (IllegalArgumentException e) {
                // valeur invalide -> on ignore le filtre
            }
        }

        if (searchDTO.getStatut() != null && !searchDTO.getStatut().isEmpty()) {
            try {
                StatutConcertEnum statut = StatutConcertEnum.valueOf(searchDTO.getStatut().toUpperCase());
                predicates.add(cb.equal(root.get("statut"), statut));
            } catch (IllegalArgumentException e) {
                // valeur invalide -> on ignore le filtre
            }
        }

        if (searchDTO.getDateMin() != null) {
            predicates.add(
                    cb.greaterThanOrEqualTo(root.get("date"), searchDTO.getDateMin())
            );
        }

        if (searchDTO.getDateMax() != null) {
            predicates.add(
                    cb.lessThanOrEqualTo(root.get("date"), searchDTO.getDateMax())
            );
        }

        if (searchDTO.getPrixMin() != null) {
            predicates.add(
                    cb.greaterThanOrEqualTo(root.get("prix"), searchDTO.getPrixMin())
            );
        }

        if (searchDTO.getPrixMax() != null) {
            predicates.add(
                    cb.lessThanOrEqualTo(root.get("prix"), searchDTO.getPrixMax())
            );
        }

        var query = cr.where(predicates.toArray(new Predicate[0]));

        return getEntityManager().createQuery(query).getResultList();
    }
}
