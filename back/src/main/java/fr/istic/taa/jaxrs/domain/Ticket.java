package fr.istic.taa.jaxrs.domain;

import fr.istic.taa.jaxrs.domain.enums.StatutTicket;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateAchat;

    private BigDecimal prix;

    @Enumerated(EnumType.STRING)
    private StatutTicket statut;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Ticket() {
    }

    public Ticket(LocalDateTime dateAchat, BigDecimal prix,
                  StatutTicket statut, Commande commande, Concert concert, Client client) {
        this.dateAchat = dateAchat;
        this.prix = prix;
        this.statut = statut;
        this.commande = commande;
        this.concert = concert;
        this.client = client;
    }

    public void annuler() {
        this.statut = StatutTicket.ANNULE;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDateTime dateAchat) {
        this.dateAchat = dateAchat;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public StatutTicket getStatut() {
        return statut;
    }

    public void setStatut(StatutTicket statut) {
        this.statut = statut;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}