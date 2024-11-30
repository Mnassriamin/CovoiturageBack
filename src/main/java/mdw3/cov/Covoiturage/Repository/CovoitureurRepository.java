package mdw3.cov.Covoiturage.Repository;


import mdw3.cov.Covoiturage.Entity.Covoitureur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CovoitureurRepository extends JpaRepository<Covoitureur, Long> {
    Optional<Covoitureur> findByEmail(String email);
}
