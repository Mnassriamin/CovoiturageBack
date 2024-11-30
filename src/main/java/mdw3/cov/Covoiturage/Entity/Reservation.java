package mdw3.cov.Covoiturage.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean confirme;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "covoitureur_id", nullable = false)
    private Covoitureur covoitureur;

    @ManyToOne
    @JoinColumn(name = "trajet_id", nullable = false)
    private Trajet trajet;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isConfirme() {
        return confirme;
    }

    public void setConfirme(boolean confirme) {
        this.confirme = confirme;
    }

    public Covoitureur getCovoitureur() {
        return covoitureur;
    }

    public void setCovoitureur(Covoitureur covoitureur) {
        this.covoitureur = covoitureur;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }
}
