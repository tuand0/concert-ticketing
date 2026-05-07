package fr.istic.taa.jaxrs.domain;

import fr.istic.taa.jaxrs.domain.enums.RoleEnum;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("administrateur")
public class Administrateur extends Utilisateur {
    private Boolean actif;

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Administrateur(){}

    @Transient
    @Override
    public RoleEnum getRole() {
        return RoleEnum.ADMINISTRATEUR;
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                ", actif=" + actif +
                ", id=" + id +
                '}';
    }
}