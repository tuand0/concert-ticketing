package fr.istic.taa.jaxrs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Données nécessaires pour créer un ticket")
public class TicketCreateDTO {

    @Schema(description = "Identifiant du client", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    private Long clientId;

    @Schema(description = "Identifiant du concert", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull
    private Long concertId;

    @Schema(description = "Numéro de place", requiredMode = Schema.RequiredMode.REQUIRED, example = "A12")
    @NotBlank
    private String numeroPlace;

    @Schema(description = "Mode de paiement", example = "CARTE_BANCAIRE")
    private String modePaiement;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public String getNumeroPlace() {
        return numeroPlace;
    }

    public void setNumeroPlace(String numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }
}