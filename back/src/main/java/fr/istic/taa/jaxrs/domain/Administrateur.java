package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Administrateur extends Utilisateur {
    private Boolean actif;

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Administrateur(){}

    @Override
    public String toString() {
        return "Administrateur{" +
                ", actif=" + actif +
                ", id=" + id +
                '}';
    }
}