package mdw3.cov.Covoiturage.Controller;

import mdw3.cov.Covoiturage.Entity.Covoitureur;
import mdw3.cov.Covoiturage.Entity.Reservation;
import mdw3.cov.Covoiturage.Entity.Trajet;
import mdw3.cov.Covoiturage.Repository.CovoitureurRepository;
import mdw3.cov.Covoiturage.Repository.ReservationRepository;
import mdw3.cov.Covoiturage.Repository.TrajetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private CovoitureurRepository covoitureurRepository;

    @PostMapping("/apply")
    public ResponseEntity<?> applyForReservation(
            @RequestParam Long covoitureurId,
            @RequestParam Long trajetId) {
        Optional<Covoitureur> covoitureur = covoitureurRepository.findById(covoitureurId);
        Optional<Trajet> trajet = trajetRepository.findById(trajetId);

        if (covoitureur.isEmpty() || trajet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Covoitureur or Trajet not found"));
        }

        // Check for duplicate application
        boolean alreadyApplied = reservationRepository.existsByCovoitureurIdAndTrajetId(covoitureurId, trajetId);
        if (alreadyApplied) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "message", "You have already applied for this trajet."
            ));
        }

        Reservation reservation = new Reservation();
        reservation.setCovoitureur(covoitureur.get());
        reservation.setTrajet(trajet.get());
        reservation.setConfirme(false); // Initial state is not confirmed
        reservationRepository.save(reservation);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "message", "Reservation created successfully!",
            "reservationId", reservation.getId()
        ));
    }


    @GetMapping("/{covoitureurId}")
    public ResponseEntity<List<Reservation>> getReservationsByCovoitureur(@PathVariable Long covoitureurId) {
        List<Reservation> reservations = reservationRepository.findByCovoitureurId(covoitureurId);
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/confirm/{reservationId}")
    public ResponseEntity<Map<String, String>> confirmReservation(@PathVariable Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
    
        if (reservation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Reservation not found"));
        }
    
        Reservation updatedReservation = reservation.get();
        updatedReservation.setConfirme(true);
        reservationRepository.save(updatedReservation);
    
        return ResponseEntity.ok(Map.of("message", "Reservation confirmed successfully!"));
    }
}

