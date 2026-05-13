package fr.istic.taa.jaxrs.domain;

import fr.istic.taa.jaxrs.domain.enums.TypeNotificationEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    private TypeNotificationEnum type;

    private LocalDateTime dateEnvoi;

    @ManyToMany(mappedBy = "notifications")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    public Notification() {
    }

    public Notification(String titre,
                        String message,
                        TypeNotificationEnum type,
                        LocalDateTime dateEnvoi) {
        this.titre = titre;
        this.message = message;
        this.type = type;
        this.dateEnvoi = dateEnvoi;
    }

    public void envoyer() {
        this.dateEnvoi = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TypeNotificationEnum getType() {
        return type;
    }

    public void setType(TypeNotificationEnum type) {
        this.type = type;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}