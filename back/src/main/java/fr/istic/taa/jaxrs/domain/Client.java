package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends Utilisateur implements Serializable {

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Commande> commandes = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

    public Client() {
        super();
    }

    public long getIdClient() {
        return id;
    }

    public void setIdClient(long id) {
        this.id = id;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}