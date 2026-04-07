package fr.istic.taa.jaxrs.domain;

import fr.istic.taa.jaxrs.domain.enums.ModePaiement;
import fr.istic.taa.jaxrs.domain.enums.StatutCommande;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCommande;

    private BigDecimal montantTotal;

    @Enumerated(EnumType.STRING)
    private StatutCommande statut;

    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "commande")
    private List<Ticket> tickets = new ArrayList<>();

    public Commande() {
    }

    public Commande(LocalDateTime dateCommande, BigDecimal montantTotal,
                    StatutCommande statut, ModePaiement modePaiement, Client client) {
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.modePaiement = modePaiement;
        this.client = client;
    }

    public BigDecimal calculerTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Ticket ticket : tickets) {
            if (ticket.getPrix() != null) {
                total = total.add(ticket.getPrix());
            }
        }
        this.montantTotal = total;
        return total;
    }

    public void confirmer() {
        this.statut = StatutCommande.PAYEE;
    }

    public void annuler() {
        this.statut = StatutCommande.ANNULEE;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public StatutCommande getStatut() {
        return statut;
    }

    public void setStatut(StatutCommande statut) {
        this.statut = statut;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}