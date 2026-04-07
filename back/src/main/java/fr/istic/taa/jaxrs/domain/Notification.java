package fr.istic.taa.jaxrs.domain;

import fr.istic.taa.jaxrs.domain.enums.TypeNotification;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    private TypeNotification type;

    private LocalDateTime dateEnvoi;

    private boolean estLue;

    @ManyToMany(mappedBy = "notifications")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    public Notification() {
    }

    public Notification(String titre, String message, TypeNotification type, LocalDateTime dateEnvoi, boolean estLue) {
        this.titre = titre;
        this.message = message;
        this.type = type;
        this.dateEnvoi = dateEnvoi;
        this.estLue = estLue;
    }

    public void envoyer() {
        this.dateEnvoi = LocalDateTime.now();
    }

    public void marquerCommeLue() {
        this.estLue = true;
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

    public TypeNotification getType() {
        return type;
    }

    public void setType(TypeNotification type) {
        this.type = type;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public boolean isEstLue() {
        return estLue;
    }

    public void setEstLue(boolean estLue) {
        this.estLue = estLue;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}