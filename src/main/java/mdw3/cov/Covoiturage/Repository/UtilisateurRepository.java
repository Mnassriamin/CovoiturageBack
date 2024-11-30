package mdw3.cov.Covoiturage.Repository;

import mdw3.cov.Covoiturage.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
     Optional<Utilisateur> findByEmail(String email);
}