package mdw3.cov.Covoiturage.DTO;

import java.time.LocalDateTime;

public class TrajetDetailsDTO {
    private Long id;
    private String lieuDepart;
    private String lieuArrivee;
    private LocalDateTime dateHeure; // Ensure this matches the type of `trajet.getDateHeure()`
    private double prix;
    private String conducteurNom;
    private String conducteurPrenom;
    private String conducteurTelephone;

    // Constructor
    public TrajetDetailsDTO(Long id, String lieuDepart, String lieuArrivee, LocalDateTime dateHeure,
                            double prix, String conducteurNom, String conducteurPrenom, String conducteurTelephone) {
        this.id = id;
        this.lieuDepart = lieuDepart;
        this.lieuArrivee = lieuArrivee;
        this.dateHeure = dateHeure;
        this.prix = prix;
        this.conducteurNom = conducteurNom;
        this.conducteurPrenom = conducteurPrenom;
        this.conducteurTelephone = conducteurTelephone;
    }

    // Getters and setters (optional if using frameworks like Spring)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLieuDepart() {
        return lieuDepart;
    }

    public void setLieuDepart(String lieuDepart) {
        this.lieuDepart = lieuDepart;
    }

    public String getLieuArrivee() {
        return lieuArrivee;
    }

    public void setLieuArrivee(String lieuArrivee) {
        this.lieuArrivee = lieuArrivee;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getConducteurNom() {
        return conducteurNom;
    }

    public void setConducteurNom(String conducteurNom) {
        this.conducteurNom = conducteurNom;
    }

    public String getConducteurPrenom() {
        return conducteurPrenom;
    }

    public void setConducteurPrenom(String conducteurPrenom) {
        this.conducteurPrenom = conducteurPrenom;
    }

    public String getConducteurTelephone() {
        return conducteurTelephone;
    }

    public void setConducteurTelephone(String conducteurTelephone) {
        this.conducteurTelephone = conducteurTelephone;
    }
}
