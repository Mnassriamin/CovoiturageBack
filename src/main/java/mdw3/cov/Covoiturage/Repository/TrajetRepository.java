package mdw3.cov.Covoiturage.Repository;

import mdw3.cov.Covoiturage.Entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrajetRepository extends JpaRepository<Trajet, Long> {
    List<Trajet> findByConducteurId(Long conducteurId);

    List<Trajet> findByLieuDepartAndLieuArrivee(String lieuDepart, String lieuArrivee);

}
