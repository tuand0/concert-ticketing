package fr.istic.taa.jaxrs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Données nécessaires pour créer un concert")
public class ConcertCreateDTO {

    @Schema(description = "Identifiant de l'organisateur", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    private Long organisateurId;

    @Schema(description = "Titre du concert", requiredMode = Schema.RequiredMode.REQUIRED, example = "Coldplay Live")
    @NotBlank
    private String titre;

    @Schema(description = "Nom de l'artiste", requiredMode = Schema.RequiredMode.REQUIRED, example = "Coldplay")
    @NotBlank
    private String artiste;

    @Schema(description = "Lieu du concert", requiredMode = Schema.RequiredMode.REQUIRED, example = "Accor Arena")
    @NotBlank
    private String lieu;

    @Schema(description = "Ville du concert", requiredMode = Schema.RequiredMode.REQUIRED, example = "Paris")
    @NotBlank
    private String ville;

    @Schema(description = "Description du concert", example = "Concert exceptionnel en tournée mondiale")
    private String description;

    @Schema(description = "Date et heure du concert", requiredMode = Schema.RequiredMode.REQUIRED, example = "2026-06-15T20:00:00")
    @NotNull
    private LocalDateTime dateTime;

    @Schema(description = "Prix du concert", requiredMode = Schema.RequiredMode.REQUIRED, example = "49.90")
    @NotNull
    @Positive
    private BigDecimal prix;

    @Schema(description = "Capacité du concert", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @Positive
    private int capacite;

    @Schema(description = "Genre musical", requiredMode = Schema.RequiredMode.REQUIRED, example = "ROCK")
    @NotBlank
    private String genre;

    public Long getOrganisateurId() {
        return organisateurId;
    }

    public void setOrganisateurId(Long organisateurId) {
        this.organisateurId = organisateurId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}