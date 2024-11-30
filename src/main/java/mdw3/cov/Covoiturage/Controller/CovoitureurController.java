package mdw3.cov.Covoiturage.Controller;

import mdw3.cov.Covoiturage.DTO.CovoitureurRegistrationDTO;
import mdw3.cov.Covoiturage.Entity.Covoitureur;
import mdw3.cov.Covoiturage.Repository.CovoitureurRepository;
import mdw3.cov.Covoiturage.Services.CovoitureurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/covoitureurs")
public class CovoitureurController {

    @Autowired
    private CovoitureurRepository covoitureurRepository;

    @Autowired
    private CovoitureurService covoitureurService;

    @PostMapping("/register")
    public ResponseEntity<Covoitureur> registerCovoitureur(@RequestBody CovoitureurRegistrationDTO dto) {
        try {
            Covoitureur covoitureur = covoitureurService.registerCovoitureur(dto);
            return new ResponseEntity<>(covoitureur, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Covoitureur> createCovoitureur(@RequestBody Covoitureur covoitureur) {
        Covoitureur newCovoitureur = covoitureurRepository.save(covoitureur);
        return new ResponseEntity<>(newCovoitureur, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Covoitureur> getCovoitureurById(@PathVariable Long id) {
        Optional<Covoitureur> covoitureur = covoitureurRepository.findById(id);
        if (covoitureur.isPresent()) {
            return new ResponseEntity<>(covoitureur.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Covoitureur>> getAllCovoitureurs() {
        List<Covoitureur> covoitureurs = covoitureurRepository.findAll();
        return new ResponseEntity<>(covoitureurs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Covoitureur> updateCovoitureur(@PathVariable Long id, @RequestBody Covoitureur updatedCovoitureur) {
        Optional<Covoitureur> existingCovoitureur = covoitureurRepository.findById(id);
        if (existingCovoitureur.isPresent()) {
            Covoitureur covoitureur = existingCovoitureur.get();
            covoitureur.setNom(updatedCovoitureur.getNom());
            covoitureur.setPrenom(updatedCovoitureur.getPrenom());
            covoitureur.setEmail(updatedCovoitureur.getEmail());
            covoitureur.setTelephone(updatedCovoitureur.getTelephone());
            Covoitureur savedCovoitureur = covoitureurRepository.save(covoitureur);
            return new ResponseEntity<>(savedCovoitureur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCovoitureur(@PathVariable Long id) {
        if (covoitureurRepository.existsById(id)) {
            covoitureurRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
