package fr.istic.taa.jaxrs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Schema(description = "Données pour la création d'un nouveau concert")
public class ConcertCreateDTO {
    @Schema(description = "Identifiant de l'organisateur (artiste)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    private Long organisateurId;

    @Schema(description = "Lieu du concert", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zénith de Rennes")
    @NotNull
    @NotEmpty
    private String lieu;

    @Schema(description = "Capacité maximale du public", requiredMode = Schema.RequiredMode.REQUIRED, example = "5000")
    @NotNull
    @Positive
    private Long capacite;

    @Schema(description = "Description facultative du concert", example = "Scène principale du festival d'été")
    private String description;

    @Schema(description = "Date et heure du concert (ISO-8601)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2026-07-14T20:00:00")
    @NotNull
    private LocalDateTime dateTime;

    @Schema(description = "Score de popularité entre 1 et 5", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "1", maximum = "5", example = "3")
    @NotNull
    @Min(1)
    @Max(5)
    private Integer popularite;

    // region Generated code
    public Integer getPopularite() {
        return popularite;
    }

    public void setPopularite(Integer popularite) {
        this.popularite = popularite;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }


    public Long getCapacite() {
        return capacite;
    }

    public void setCapacite(Long capacite) {
        this.capacite = capacite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrganisateurId() {
        return organisateurId;
    }

    public void setOrganisateurId(Long organisateurId) {
        this.organisateurId = organisateurId;
    }
    // endregion
}
