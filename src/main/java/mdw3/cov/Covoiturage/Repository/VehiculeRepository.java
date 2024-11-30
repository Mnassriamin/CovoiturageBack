package mdw3.cov.Covoiturage.Repository;

import mdw3.cov.Covoiturage.Entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    Optional<Vehicule> findByImmatriculation(String immatriculation);
}
