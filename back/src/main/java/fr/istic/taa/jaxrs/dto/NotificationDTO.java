package fr.istic.taa.jaxrs.dto;

import java.time.LocalDateTime;

public class NotificationDTO {

    private Long id;
    private String titre;
    private String message;
    private String type;
    private LocalDateTime dateEnvoi;

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }
}