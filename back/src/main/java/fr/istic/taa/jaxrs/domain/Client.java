package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.istic.taa.jaxrs.domain.enums.RoleEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("client")
public class Client extends Utilisateur implements Serializable {

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Commande> commandes = new ArrayList<>();
//
//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Ticket> tickets = new ArrayList<>();

    @Transient
    @Override
    public RoleEnum getRole() {
        return RoleEnum.CLIENT;
    }

    public Client() {
        super();
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }
//
//    public List<Ticket> getTickets() {
//        return tickets;
//    }
//
//    public void setTickets(List<Ticket> tickets) {
//        this.tickets = tickets;
//    }
}