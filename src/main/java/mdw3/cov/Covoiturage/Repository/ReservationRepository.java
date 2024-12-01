package mdw3.cov.Covoiturage.Repository;

import mdw3.cov.Covoiturage.Entity.Reservation;
import mdw3.cov.Covoiturage.Entity.Trajet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCovoitureurId(Long covoitureurId);
    boolean existsByCovoitureurIdAndTrajetId(Long covoitureurId, Long trajetId);

    List<Reservation> findByTrajet(Trajet trajet);
    long countByTrajetAndConfirme(Trajet trajet, boolean confirme);

}