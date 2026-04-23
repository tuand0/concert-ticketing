package fr.istic.taa.jaxrs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.core.MultivaluedMap;

@Schema(description = "Filtres de recherche pour interroger les artistes")
public class ArtisteSearchDTO {
    @Schema(description = "Nom de scène de l'artiste", example = "Daft Punk")
    private String nomScene;

    @Schema(description = "Nom de famille de l'artiste", example = "Bangalter")
    private String nom;

    @Schema(description = "Prénom de l'artiste", example = "Thomas")
    private String prenom;

    @Schema(description = "Nationalité de l'artiste", example = "Française")
    private String nationalite;

    @Schema(description = "Score de popularité pour filtrer (1–5)", minimum = "1", maximum = "5", example = "4")
    private Integer popularite;

    public ArtisteSearchDTO(MultivaluedMap<String, String> queryParameters) {
        this.nomScene = queryParameters.getFirst("nomScene");
        this.nom = queryParameters.getFirst("nom");
        this.prenom = queryParameters.getFirst("prenom");
        this.nationalite = queryParameters.getFirst("nationalite");

        try {
            this.popularite = Integer.parseInt(queryParameters.getFirst("popularite"));
        } catch (NumberFormatException e) {
            this.popularite = null;
        }

    }

    // region Generated code
    public Integer getPopularite() {
        return popularite;
    }

    public void setPopularite(Integer popularite) {
        this.popularite = popularite;
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

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public void setPopularite(int popularite) {
        this.popularite = popularite;
    }

    // endregion
}
