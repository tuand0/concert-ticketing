package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.istic.taa.jaxrs.domain.enums.GenreEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutConcertEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Concert implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String artiste;

    @Column(length = 1000)
    private String description;

    private LocalDateTime date;

    private String lieu;

    private String ville;

    private int capacite;

    private BigDecimal prix;

    @Enumerated(EnumType.STRING)
    private GenreEnum genre;

    @Enumerated(EnumType.STRING)
    private StatutConcertEnum statut;

    @ManyToOne
    @JoinColumn(name = "organisateur_id")
    private Organisateur organisateur;

    @ManyToOne
    @JoinColumn(name = "administrateur_id")
    private Administrateur administrateurValidateur;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

    public Concert() {
    }

    public Concert(String titre, String artiste, String description,
                   LocalDateTime date, String lieu, String ville,
                   int capacite, StatutConcertEnum statut,
                   GenreEnum genre, BigDecimal prix,
                   Organisateur organisateur) {
        this.titre = titre;
        this.artiste = artiste;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.ville = ville;
        this.capacite = capacite;
        this.statut = statut;
        this.organisateur = organisateur;
        this.genre = genre;
        this.prix = prix;
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

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public StatutConcertEnum getStatut() {
        return statut;
    }

    public void setStatut(StatutConcertEnum statut) {
        this.statut = statut;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    public void setGenre(GenreEnum genre) {
        this.genre = genre;
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