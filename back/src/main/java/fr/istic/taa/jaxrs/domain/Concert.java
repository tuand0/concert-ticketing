package fr.istic.taa.jaxrs.domain;

import fr.istic.taa.jaxrs.domain.enums.StatutConcert;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String artiste;

    @Column(length = 1000)
    private String description;

    private LocalDateTime dateConcert;

    private String lieu;

    private String ville;

    private int places;

    @Enumerated(EnumType.STRING)
    private StatutConcert statut;

    @ManyToOne
    @JoinColumn(name = "organisateur_id")
    private Organisateur organisateur;

    @ManyToOne
    @JoinColumn(name = "administrateur_id")
    private Administrateur administrateurValidateur;

    @OneToMany(mappedBy = "concert")
    private List<Ticket> tickets = new ArrayList<>();

    public Concert() {
    }

    public Concert(String titre, String artiste, String description,
                   LocalDateTime dateConcert, String lieu, String ville,
                   int places, StatutConcert statut, Organisateur organisateur) {
        this.titre = titre;
        this.artiste = artiste;
        this.description = description;
        this.dateConcert = dateConcert;
        this.lieu = lieu;
        this.ville = ville;
        this.places = places;
        this.statut = statut;
        this.organisateur = organisateur;
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

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateConcert() {
        return dateConcert;
    }

    public void setDateConcert(LocalDateTime dateConcert) {
        this.dateConcert = dateConcert;
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

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public StatutConcert getStatut() {
        return statut;
    }

    public void setStatut(StatutConcert statut) {
        this.statut = statut;
    }

    public Organisateur getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }

    public Administrateur getAdministrateurValidateur() {
        return administrateurValidateur;
    }

    public void setAdministrateurValidateur(Administrateur administrateurValidateur) {
        this.administrateurValidateur = administrateurValidateur;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}