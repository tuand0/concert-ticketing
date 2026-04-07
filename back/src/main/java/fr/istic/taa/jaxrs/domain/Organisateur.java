package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.Entity;

@Entity
public class Organisateur extends Utilisateur {

    private String nomEntreprise;

    private String siret;

    private Boolean actif;

    // region Generated code
    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    @Override
    public String toString() {
        return "Organisateur{" +
                "nomEntreprise='" + nomEntreprise + '\'' +
                ", numeroSiret='" + siret + '\'' +
                ", actif=" + actif +
                ", personneId=" + id +
                '}';
    }

    // endregion
}
