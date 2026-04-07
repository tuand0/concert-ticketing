package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Artiste implements Serializable {
    @Id
    @GeneratedValue
    private Long artisteId;

    private String nomScene;

    private String nom;

    private String prenom;

    private LocalDate dateNaissance;

    private String nationalite;

    private String description;

    private int popularite;

    private String siteWeb;

    @JsonIgnore
    @ManyToMany(mappedBy = "artistes", fetch = FetchType.LAZY)
    private Set<Concert> concerts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "artistes_genres",
            joinColumns = @JoinColumn(name = "artiste_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<GenreMusical> genres = new HashSet<>();

    // region Generated code
    public Set<GenreMusical> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreMusical> genres) {
        this.genres = genres;
    }

    public Set<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(Set<Concert> concerts) {
        this.concerts = concerts;
    }

    @Override
    public String toString() {
        return "Artiste{" +
                "artisteId=" + artisteId +
                ", nomScene='" + nomScene + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", nationalite='" + nationalite + '\'' +
                ", description='" + description + '\'' +
                ", popularite=" + popularite +
                ", siteWeb='" + siteWeb + '\'' +
                ", concerts=" + concerts +
                ", genres=" + genres +
                '}';
    }

    public Long getArtisteId() {
        return artisteId;
    }

    public void setArtisteId(Long artisteId) {
        this.artisteId = artisteId;
    }

    public String getNomScene() {
        return nomScene;
    }

    public void setNomScene(String nomScene) {
        this.nomScene = nomScene;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPopularite() {
        return popularite;
    }

    public void setPopularite(int popularite) {
        this.popularite = popularite;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    // endregion
}
