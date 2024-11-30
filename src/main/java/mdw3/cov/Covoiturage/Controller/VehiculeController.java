package mdw3.cov.Covoiturage.Controller;

import mdw3.cov.Covoiturage.Entity.Vehicule;
import mdw3.cov.Covoiturage.Repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @PostMapping
    public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) {
        Vehicule newVehicule = vehiculeRepository.save(vehicule);
        return new ResponseEntity<>(newVehicule, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable Long id) {
        Optional<Vehicule> vehicule = vehiculeRepository.findById(id);
        return vehicule.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return new ResponseEntity<>(vehicules, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> updateVehicule(@PathVariable Long id, @RequestBody Vehicule updatedVehicule) {
        Optional<Vehicule> existingVehicule = vehiculeRepository.findById(id);
        if (existingVehicule.isPresent()) {
            Vehicule vehicule = existingVehicule.get();
            vehicule.setMarque(updatedVehicule.getMarque());
            vehicule.setModele(updatedVehicule.getModele());
            vehicule.setImmatriculation(updatedVehicule.getImmatriculation());
            Vehicule savedVehicule = vehiculeRepository.save(vehicule);
            return new ResponseEntity<>(savedVehicule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        if (vehiculeRepository.existsById(id)) {
            vehiculeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
