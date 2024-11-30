package mdw3.cov.Covoiturage.Controller;

import mdw3.cov.Covoiturage.DTO.ConducteurRegistrationDTO;
import mdw3.cov.Covoiturage.Entity.Conducteur;
import mdw3.cov.Covoiturage.Repository.ConducteurRepository;
import mdw3.cov.Covoiturage.Services.ConducteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/conducteurs")
public class ConducteurController {

    @Autowired
    private ConducteurRepository conducteurRepository;
    @Autowired
    private ConducteurService conducteurService;

    @PostMapping("/register")
    public ResponseEntity<Conducteur> registerConducteur(@RequestBody ConducteurRegistrationDTO dto) {
        try {
            Conducteur conducteur = conducteurService.registerConducteur(dto);
            return new ResponseEntity<>(conducteur, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Conducteur> createConducteur(@RequestBody Conducteur conducteur) {
        Conducteur newConducteur = conducteurRepository.save(conducteur);
        return new ResponseEntity<>(newConducteur, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conducteur> getConducteurById(@PathVariable Long id) {
        Optional<Conducteur> conducteur = conducteurRepository.findById(id);
        if (conducteur.isPresent()) {
            return new ResponseEntity<>(conducteur.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Conducteur>> getAllConducteurs() {
        List<Conducteur> conducteurs = conducteurRepository.findAll();
        return new ResponseEntity<>(conducteurs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conducteur> updateConducteur(@PathVariable Long id, @RequestBody Conducteur updatedConducteur) {
        Optional<Conducteur> existingConducteur = conducteurRepository.findById(id);
        if (existingConducteur.isPresent()) {
            Conducteur conducteur = existingConducteur.get();
            conducteur.setNom(updatedConducteur.getNom());
            conducteur.setPrenom(updatedConducteur.getPrenom());
            conducteur.setEmail(updatedConducteur.getEmail());
            conducteur.setTelephone(updatedConducteur.getTelephone());
            Conducteur savedConducteur = conducteurRepository.save(conducteur);
            return new ResponseEntity<>(savedConducteur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConducteur(@PathVariable Long id) {
        if (conducteurRepository.existsById(id)) {
            conducteurRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
