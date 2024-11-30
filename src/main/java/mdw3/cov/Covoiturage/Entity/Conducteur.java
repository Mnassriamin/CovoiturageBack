package mdw3.cov.Covoiturage.Entity;

import jakarta.persistence.*;


@Entity
public class Conducteur extends Utilisateur {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Ensure EAGER fetch
    @JoinColumn(name = "vehicule_id", referencedColumnName = "id", nullable = true)
    private Vehicule vehicule;

    // Getters and setters
    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }
}
