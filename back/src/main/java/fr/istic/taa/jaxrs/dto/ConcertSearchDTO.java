package fr.istic.taa.jaxrs.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.core.MultivaluedMap;

import java.math.BigDecimal;
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

    @Schema(description = "Afficher uniquement les concerts avec capacité disponible", example = "true")
    private Boolean inStockOnly;

    @Schema(description = "Prix minimum", example = "20")
    private BigDecimal prixMin;

    @Schema(description = "Prix maximum", example = "100")
    private BigDecimal prixMax;

    public ConcertSearchDTO() {
    }

    public ConcertSearchDTO(MultivaluedMap<String, String> queryParameters) {

        this.titre = queryParameters.getFirst("titre");
        this.artiste = queryParameters.getFirst("artiste");
        this.ville = queryParameters.getFirst("ville");
        this.lieu = queryParameters.getFirst("lieu");
        this.genre = queryParameters.getFirst("genre");
        this.statut = queryParameters.getFirst("statut");

        String inStock = queryParameters.getFirst("inStockOnly");
        if (inStock != null) {
            this.inStockOnly = Boolean.parseBoolean(inStock);
        }
        
        // prixMin
        try {
            String p = queryParameters.getFirst("prixMin");
            if (p != null) this.prixMin = new BigDecimal(p);
        } catch (NumberFormatException e) {
            this.prixMin = null;
        }

        // prixMax
        try {
            String p = queryParameters.getFirst("prixMax");
            if (p != null) this.prixMax = new BigDecimal(p);
        } catch (NumberFormatException e) {
            this.prixMax = null;
        }
    }

    public String getTitre() { return titre; }
    public String getArtiste() { return artiste; }
    public String getVille() { return ville; }
    public String getLieu() { return lieu; }
    public String getGenre() { return genre; }
    public Boolean getInStockOnly() {
        return inStockOnly;
    }
    public BigDecimal getPrixMin() { return prixMin; }
    public BigDecimal getPrixMax() { return prixMax; }
}
