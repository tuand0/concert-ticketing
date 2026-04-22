package fr.istic.taa.jaxrs.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.core.MultivaluedMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Schema(description = "Filtres de recherche pour les concerts")
public class ConcertSearchDTO {

    @Schema(description = "Titre du concert", example = "Coldplay Live")
    private String titre;

    @Schema(description = "Artiste", example = "Coldplay")
    private String artiste;

    @Schema(description = "Ville", example = "Paris")
    private String ville;

    @Schema(description = "Lieu", example = "Accor Arena")
    private String lieu;

    @Schema(description = "Genre musical", example = "POP")
    private String genre;

    @Schema(description = "Statut du concert", example = "PUBLIE")
    private String statut;

    @Schema(description = "Date minimale", example = "2026-05-01T00:00:00")
    private LocalDateTime dateMin;

    @Schema(description = "Date maximale", example = "2026-06-01T00:00:00")
    private LocalDateTime dateMax;

    @Schema(description = "Prix minimum", example = "20")
    private Integer prixMin;

    @Schema(description = "Prix maximum", example = "100")
    private Integer prixMax;

    public ConcertSearchDTO(MultivaluedMap<String, String> queryParameters) {

        this.titre = queryParameters.getFirst("titre");
        this.artiste = queryParameters.getFirst("artiste");
        this.ville = queryParameters.getFirst("ville");
        this.lieu = queryParameters.getFirst("lieu");
        this.genre = queryParameters.getFirst("genre");
        this.statut = queryParameters.getFirst("statut");

        // dateMin
        try {
            String d = queryParameters.getFirst("dateMin");
            if (d != null) this.dateMin = LocalDateTime.parse(d);
        } catch (DateTimeParseException e) {
            this.dateMin = null;
        }

        // dateMax
        try {
            String d = queryParameters.getFirst("dateMax");
            if (d != null) this.dateMax = LocalDateTime.parse(d);
        } catch (DateTimeParseException e) {
            this.dateMax = null;
        }

        // prixMin
        try {
            String p = queryParameters.getFirst("prixMin");
            if (p != null) this.prixMin = Integer.parseInt(p);
        } catch (NumberFormatException e) {
            this.prixMin = null;
        }

        // prixMax
        try {
            String p = queryParameters.getFirst("prixMax");
            if (p != null) this.prixMax = Integer.parseInt(p);
        } catch (NumberFormatException e) {
            this.prixMax = null;
        }
    }

    public String getTitre() { return titre; }
    public String getArtiste() { return artiste; }
    public String getVille() { return ville; }
    public String getLieu() { return lieu; }
    public String getGenre() { return genre; }
    public String getStatut() { return statut; }
    public LocalDateTime getDateMin() { return dateMin; }
    public LocalDateTime getDateMax() { return dateMax; }
    public Integer getPrixMin() { return prixMin; }
    public Integer getPrixMax() { return prixMax; }
}
