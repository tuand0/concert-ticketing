package fr.istic.taa.jaxrs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Données nécessaires pour créer un client")
public class ClientCreateDTO {

    @Schema(description = "Nom du client", requiredMode = Schema.RequiredMode.REQUIRED, example = "Pham")
    @NotBlank
    private String nom;

    @Schema(description = "Prénom du client", requiredMode = Schema.RequiredMode.REQUIRED, example = "Thuy")
    @NotBlank
    private String prenom;

    @Schema(description = "Nom d'utilisateur", requiredMode = Schema.RequiredMode.REQUIRED, example = "thuypham")
    @NotBlank
    private String userNom;

    @Schema(description = "Mot de passe hashé", requiredMode = Schema.RequiredMode.REQUIRED, example = "hashed_password")
    @NotBlank
    private String hashedPassword;

    @Schema(description = "Email du client", requiredMode = Schema.RequiredMode.REQUIRED, example = "thuy@example.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "Téléphone", example = "0601020304")
    private String telephone;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUserNom() {
        return userNom;
    }

    public void setUserNom(String userNom) {
        this.userNom = userNom;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}