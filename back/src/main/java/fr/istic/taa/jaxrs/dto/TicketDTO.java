package fr.istic.taa.jaxrs.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketDTO {

    private Long id;
    private String numeroTicket;
    private BigDecimal prix;
    private LocalDateTime dateAchat;
    private String numeroPlace;
    private String statut;

    private Long concertId;
    private String concertTitre;
    private String concertArtiste;
    private LocalDateTime concertDate;
    private String concertLieu;
    private String concertVille;

    public Long getId() {
        return id;
    }

    public String getNumeroTicket() {
        return numeroTicket;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public LocalDateTime getDateAchat() {
        return dateAchat;
    }

    public String getNumeroPlace() {
        return numeroPlace;
    }

    public String getStatut() {
        return statut;
    }

    public Long getConcertId() {
        return concertId;
    }

    public String getConcertTitre() {
        return concertTitre;
    }

    public String getConcertArtiste() {
        return concertArtiste;
    }

    public LocalDateTime getConcertDate() {
        return concertDate;
    }

    public String getConcertLieu() {
        return concertLieu;
    }

    public String getConcertVille() {
        return concertVille;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumeroTicket(String numeroTicket) {
        this.numeroTicket = numeroTicket;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public void setDateAchat(LocalDateTime dateAchat) {
        this.dateAchat = dateAchat;
    }

    public void setNumeroPlace(String numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public void setConcertTitre(String concertTitre) {
        this.concertTitre = concertTitre;
    }

    public void setConcertArtiste(String concertArtiste) {
        this.concertArtiste = concertArtiste;
    }

    public void setConcertDate(LocalDateTime concertDate) {
        this.concertDate = concertDate;
    }

    public void setConcertLieu(String concertLieu) {
        this.concertLieu = concertLieu;
    }

    public void setConcertVille(String concertVille) {
        this.concertVille = concertVille;
    }
}