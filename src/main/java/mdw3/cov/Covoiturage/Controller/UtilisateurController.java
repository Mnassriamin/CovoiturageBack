package mdw3.cov.Covoiturage.Controller;

import mdw3.cov.Covoiturage.DTO.ConducteurRegistrationDTO;
import mdw3.cov.Covoiturage.DTO.ProfileDTO;
import mdw3.cov.Covoiturage.Entity.Conducteur;
import mdw3.cov.Covoiturage.Entity.Covoitureur;
import mdw3.cov.Covoiturage.Entity.Utilisateur;
import mdw3.cov.Covoiturage.Entity.Vehicule;
import mdw3.cov.Covoiturage.Repository.ConducteurRepository;
import mdw3.cov.Covoiturage.Repository.CovoitureurRepository;
import mdw3.cov.Covoiturage.Repository.UtilisateurRepository;
import mdw3.cov.Covoiturage.Repository.VehiculeRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private ConducteurRepository conducteurRepository;

    @Autowired
    private CovoitureurRepository covoitureurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    // Register Conducteur
    @Transactional
   @PostMapping("/register-conducteur")
public ResponseEntity<?> inscrireConducteur(@RequestBody ConducteurRegistrationDTO dto) {
    // Check for existing email
    if (conducteurRepository.findByEmail(dto.getEmail()).isPresent()) {
        return new ResponseEntity<>("Un conducteur avec cet email existe déjà", HttpStatus.BAD_REQUEST);
    }

    // Encode password
    String hashedPassword = passwordEncoder.encode(dto.getPassword());

    // Create Vehicule
    Vehicule vehicule = new Vehicule();
    vehicule.setMarque(dto.getMarque());
    vehicule.setModele(dto.getModele());
    vehicule.setImmatriculation(dto.getImmatriculation());
    vehicule.setSieges(dto.getSieges());
    Vehicule savedVehicule = vehiculeRepository.save(vehicule);

    // Create Conducteur
    Conducteur conducteur = new Conducteur();
    conducteur.setNom(dto.getNom());
    conducteur.setPrenom(dto.getPrenom());
    conducteur.setEmail(dto.getEmail());
    conducteur.setTelephone(dto.getTelephone());
    conducteur.setPassword(hashedPassword);
    conducteur.setVehicule(savedVehicule);

    // Save Conducteur
    Conducteur savedConducteur = conducteurRepository.save(conducteur);

    return new ResponseEntity<>(savedConducteur, HttpStatus.CREATED);
}


    // Register Covoitureur
    @PostMapping("/register-covoitureur")
    public ResponseEntity<?> inscrireCovoitureur(@RequestBody Covoitureur covoitureur) {
        if (covoitureurRepository.findByEmail(covoitureur.getEmail()).isPresent()) {
            return new ResponseEntity<>("Un covoitureur avec cet email existe déjà", HttpStatus.BAD_REQUEST);
        }
        String Hpassword = passwordEncoder.encode(covoitureur.getPassword());
        covoitureur.setPassword(Hpassword);
        return new ResponseEntity<>(covoitureurRepository.save(covoitureur), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilisateur utilisateur) {
        
        Utilisateur storedUser = utilisateurRepository.findByEmail(utilisateur.getEmail()).orElse(null);
        
        if (storedUser == null) {
            System.out.println("Login failed: Invalid email - " + utilisateur.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email");
        }
        
        boolean isPasswordMatch = passwordEncoder.matches(utilisateur.getPassword(), storedUser.getPassword());
        
        if (!isPasswordMatch) {
            System.out.println("Login failed: Invalid password for email - " + utilisateur.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
        
        System.out.println("Login successful for email - " + utilisateur.getEmail());
        return ResponseEntity.ok(storedUser);
    }

  @GetMapping("/profile")
public ResponseEntity<?> getProfile(@RequestParam Long userId) {
    // First, check if the user is a Conducteur
    Optional<Conducteur> conducteurOptional = conducteurRepository.findById(userId);

    if (conducteurOptional.isPresent()) {
        // User is a Conducteur
        Conducteur conducteur = conducteurOptional.get();

        System.out.println("Retrieved Conducteur: " + conducteur);

        if (conducteur.getVehicule() != null) {
            System.out.println("Vehicule: " + conducteur.getVehicule());
        } else {
            System.out.println("Vehicule is null");
        }

        // Use ProfileDTO to structure the response
        ProfileDTO profileDTO = new ProfileDTO(
                conducteur.getId(),
                conducteur.getNom(),
                conducteur.getPrenom(),
                conducteur.getEmail(),
                conducteur.getTelephone(),
                conducteur.getVehicule() != null ? conducteur.getVehicule().getMarque() : "N/A",
                conducteur.getVehicule() != null ? conducteur.getVehicule().getModele() : "N/A",
                conducteur.getVehicule() != null ? conducteur.getVehicule().getImmatriculation() : "N/A",
                conducteur.getVehicule() != null ? conducteur.getVehicule().getSieges() : null
        );

        return ResponseEntity.ok(profileDTO);
    }

    // If not a Conducteur, check if the user is a Utilisateur (e.g., Covoitureur)
    Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(userId);

    if (utilisateurOptional.isPresent()) {
        // User is a generic Utilisateur (likely a Covoitureur)
        Utilisateur utilisateur = utilisateurOptional.get();

        System.out.println("Retrieved Utilisateur: " + utilisateur);

        // Use ProfileDTO without vehicle details
        ProfileDTO profileDTO = new ProfileDTO(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getTelephone()
        );

        return ResponseEntity.ok(profileDTO);
    }

    // If no user is found
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
}


}
