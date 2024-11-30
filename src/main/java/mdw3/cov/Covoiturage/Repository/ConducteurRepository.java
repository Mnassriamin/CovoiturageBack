package mdw3.cov.Covoiturage.Repository;

import mdw3.cov.Covoiturage.Entity.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {
    Optional<Conducteur> findByEmail(String email);
    @Query("SELECT c FROM Conducteur c LEFT JOIN FETCH c.vehicule WHERE c.id = :id")
    Optional<Conducteur> findByIdWithVehicule(@Param("id") Long id);
}
