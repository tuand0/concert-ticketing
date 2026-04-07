package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class GenreMusical {
    @GeneratedValue
    @Id
    private Long genreId;

    private String libelle;

    @ManyToMany(mappedBy = "genres")
    private List<Artiste> artistes;

    // region Generated code
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }



    @Override
    public String toString() {
        return "GenreMusical{" +
                "genreId=" + genreId +
                ", libelle='" + libelle + '\'' +
                '}';
    }
    // endregion
}
