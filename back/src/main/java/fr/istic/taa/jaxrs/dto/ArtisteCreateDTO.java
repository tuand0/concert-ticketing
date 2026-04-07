package fr.istic.taa.jaxrs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Schema(description = "Données pour la création d'un nouvel artiste")
public class ArtisteCreateDTO {
    @Schema(description = "Nom de scène de l'artiste", requiredMode = Schema.RequiredMode.REQUIRED, example = "Daft Punk")
    @NotEmpty
    private String nomScene;

    @Schema(description = "Date de naissance de l'artiste (ISO-8601)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1974-09-08")
    @NotNull
    private LocalDate dateNaissance;

    @Schema(description = "Score de popularité entre 0 et 100", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0", maximum = "100", example = "85")
    @Min(0)
    @Max(100)
    private int popularite;

    @Schema(description = "Code pays ISO 3166-1 alpha-2 (2 lettres)", example = "FR")
    @Length(min = 2, max = 2)
    private String nationalite;

    public String getNomScene() {
        return nomScene;
    }

    public void setNomScene(String nomScene) {
        this.nomScene = nomScene;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getPopularite() {
        return popularite;
    }

    public void setPopularite(int popularite) {
        this.popularite = popularite;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }
}
