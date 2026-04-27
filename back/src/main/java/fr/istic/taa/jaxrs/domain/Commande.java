package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.istic.taa.jaxrs.domain.enums.ModePaiementEnum;
import fr.istic.taa.jaxrs.domain.enums.StatutCommandeEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Commande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCommande;

    private BigDecimal montantTotal;

    @Enumerated(EnumType.STRING)
    private StatutCommandeEnum statut;

    @Enumerated(EnumType.STRING)
    private ModePaiementEnum modePaiement;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

    public Commande() {
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
        this.statut = StatutCommandeEnum.PAYEE;
    }

    public void annuler() {
        this.statut = StatutCommandeEnum.ANNULEE;
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

    public StatutCommandeEnum getStatut() {
        return statut;
    }

    public void setStatut(StatutCommandeEnum statut) {
        this.statut = statut;
    }

    public ModePaiementEnum getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiementEnum modePaiement) {
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