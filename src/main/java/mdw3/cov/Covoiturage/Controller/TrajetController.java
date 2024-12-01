package mdw3.cov.Covoiturage.Controller;

import mdw3.cov.Covoiturage.DTO.TrajetDetailsDTO;
import mdw3.cov.Covoiturage.Entity.Conducteur;
import mdw3.cov.Covoiturage.Entity.Reservation;
import mdw3.cov.Covoiturage.Entity.Trajet;
import mdw3.cov.Covoiturage.Repository.ConducteurRepository;
import mdw3.cov.Covoiturage.Repository.ReservationRepository;
import mdw3.cov.Covoiturage.Repository.TrajetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/trajets")
public class TrajetController {

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private ConducteurRepository conducteurRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping("/with-conducteur")
    public ResponseEntity<Trajet> createTrajetWithConducteur(
            @RequestBody Trajet trajet,
            @RequestParam Long conducteurId) {
        Optional<Conducteur> conducteur = conducteurRepository.findById(conducteurId);
        if (conducteur.isPresent()) {
            trajet.setConducteur(conducteur.get());
            Trajet newTrajet = trajetRepository.save(trajet);
            return new ResponseEntity<>(newTrajet, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrajetWithConducteur(@PathVariable Long id) {
        Optional<Trajet> trajetOpt = trajetRepository.findById(id);

        if (trajetOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trajet not found");
        }

        Trajet trajet = trajetOpt.get();
        TrajetDetailsDTO detailsDTO = new TrajetDetailsDTO(
                trajet.getId(),
                trajet.getLieuDepart(),
                trajet.getLieuArrivee(),
                trajet.getDateHeure(),
                trajet.getPrix(),
                trajet.getConducteur().getNom(),
                trajet.getConducteur().getPrenom(),
                trajet.getConducteur().getTelephone()
        );

        return ResponseEntity.ok(detailsDTO);
    }

    @GetMapping
public ResponseEntity<List<TrajetDetailsDTO>> getAllTrajets() {
    List<Trajet> trajets = trajetRepository.findAll();
    List<TrajetDetailsDTO> trajetDTOs = trajets.stream().map(trajet -> new TrajetDetailsDTO(
            trajet.getId(),
            trajet.getLieuDepart(),
            trajet.getLieuArrivee(),
            trajet.getDateHeure(),
            trajet.getPrix(),
            trajet.getConducteur().getNom(),
            trajet.getConducteur().getPrenom(),
            trajet.getConducteur().getTelephone()
    )).toList();
    return new ResponseEntity<>(trajetDTOs, HttpStatus.OK);
}

    @GetMapping("/conducteur/{conducteurId}")
    public ResponseEntity<?> getTrajetsByConducteur(@PathVariable Long conducteurId) {
        Optional<Conducteur> conducteurOpt = conducteurRepository.findById(conducteurId);

        if (conducteurOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conducteur not found");
        }

        List<Trajet> trajets = trajetRepository.findByConducteurId(conducteurId);
        List<TrajetDetailsDTO> trajetDTOs = trajets.stream().map(trajet -> new TrajetDetailsDTO(
                trajet.getId(),
                trajet.getLieuDepart(),
                trajet.getLieuArrivee(),
                trajet.getDateHeure(),
                trajet.getPrix(),
                trajet.getConducteur().getNom(),
                trajet.getConducteur().getPrenom(),
                trajet.getConducteur().getTelephone()
        )).toList();
        return ResponseEntity.ok(trajetDTOs);
    }
   
    
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getTrajetDetailsWithCovoitureurs(@PathVariable Long id) {
        Optional<Trajet> trajetOpt = trajetRepository.findById(id);
    
        if (trajetOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trajet not found");
        }
    
        Trajet trajet = trajetOpt.get();
        Conducteur conducteur = trajet.getConducteur();
    
        // Calculate available seats
        int totalSeats = conducteur.getVehicule() != null ? conducteur.getVehicule().getSieges() : 0;
        long confirmedReservations = reservationRepository.countByTrajetAndConfirme(trajet, true);
    
        // Debug logs
        System.out.println("Total Seats: " + totalSeats);
        System.out.println("Confirmed Reservations: " + confirmedReservations);
    
        int seatsAvailable = Math.max(0, totalSeats - (int) confirmedReservations);
    
        // Debug available seats
        System.out.println("Seats Available: " + seatsAvailable);
    
        // Fetch reservations and map data
        List<Map<String, Object>> reservations = reservationRepository.findByTrajet(trajet).stream().map(reservation -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", reservation.getId());
            map.put("confirme", reservation.isConfirme());
            map.put("covoitureur", Map.of(
                "nom", reservation.getCovoitureur().getNom(),
                "prenom", reservation.getCovoitureur().getPrenom(),
                "email", reservation.getCovoitureur().getEmail(),
                "telephone", reservation.getCovoitureur().getTelephone()
            ));
            return map;
        }).toList();
    
        // Build response
        Map<String, Object> response = new HashMap<>();
        response.put("trajet", trajet);
        response.put("reservations", reservations);
        response.put("seatsAvailable", seatsAvailable);
    
        return ResponseEntity.ok(response);
    }
    


    

    


    @PutMapping("/{id}")
    public ResponseEntity<Trajet> updateTrajet(@PathVariable Long id, @RequestBody Trajet updatedTrajet) {
        Optional<Trajet> existingTrajet = trajetRepository.findById(id);
        if (existingTrajet.isPresent()) {
            Trajet trajet = existingTrajet.get();
            trajet.setLieuDepart(updatedTrajet.getLieuDepart());
            trajet.setLieuArrivee(updatedTrajet.getLieuArrivee());
            trajet.setDateHeure(updatedTrajet.getDateHeure());
            trajet.setPrix(updatedTrajet.getPrix());
            Trajet savedTrajet = trajetRepository.save(trajet);
            return new ResponseEntity<>(savedTrajet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajet(@PathVariable Long id) {
        if (trajetRepository.existsById(id)) {
            trajetRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
