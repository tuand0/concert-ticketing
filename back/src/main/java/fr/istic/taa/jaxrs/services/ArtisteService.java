package fr.istic.taa.jaxrs.services;

import fr.istic.taa.jaxrs.dao.generic.ArtisteDao;
import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.dto.ArtisteCreateDTO;
import fr.istic.taa.jaxrs.dto.ArtisteSearchDTO;

import java.util.List;

public class ArtisteService {
    private final ArtisteDao dao = new ArtisteDao();

    public List<Artiste> searchArtistes(ArtisteSearchDTO searchDTO) {
        return dao.searchArtistes(searchDTO);
    }

    public Artiste findOne(Long id) {
        return dao.findOne(id);
    }

    public Long create(ArtisteCreateDTO artisteDto) {
        // Mapping
        Artiste artiste = new Artiste();
        artiste.setNomScene(artisteDto.getNomScene());
        artiste.setDateNaissance(artisteDto.getDateNaissance());
        artiste.setPopularite(artisteDto.getPopularite());
        artiste.setNationalite(artisteDto.getNationalite());

        dao.save(artiste);
        return artiste.getArtisteId();
    }
}
