package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.old_Artiste;
import fr.istic.taa.jaxrs.dto.ArtisteSearchDTO;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ArtisteDao extends AbstractJpaDao<Long, old_Artiste> {
    public ArtisteDao() {
        super(old_Artiste.class);
    }

    public List<old_Artiste> searchArtistes(ArtisteSearchDTO searchDTO) {
        var cb = entityManager.getCriteriaBuilder();
        var cr = cb.createQuery(old_Artiste.class);
        var root = cr.from(old_Artiste.class);

        cr.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (searchDTO.getNom() != null && !searchDTO.getNom().isEmpty()) {
            predicates.add(cb.like(root.get("nom"), "%" + searchDTO.getNom() + "%"));
        }

        if (searchDTO.getPrenom() != null && !searchDTO.getPrenom().isEmpty()) {
            predicates.add(cb.like(root.get("prenom"), "%" + searchDTO.getPrenom() + "%"));
        }

        if (searchDTO.getNomScene() != null && !searchDTO.getNomScene().isEmpty()) {
            predicates.add(cb.like(root.get("nomScene"), "%" + searchDTO.getNomScene() + "%"));
        }

        if (searchDTO.getNationalite() != null && !searchDTO.getNationalite().isEmpty()) {
            predicates.add(cb.equal(root.get("nationalite"), searchDTO.getNationalite()));
        }

        if (searchDTO.getPopularite() != null && searchDTO.getPopularite() > 0) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("popularite"), searchDTO.getPopularite()));
        }

        var query = cr.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
