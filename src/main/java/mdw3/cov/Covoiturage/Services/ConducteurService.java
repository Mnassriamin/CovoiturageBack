package mdw3.cov.Covoiturage.Services;

import mdw3.cov.Covoiturage.DTO.ConducteurRegistrationDTO;
import mdw3.cov.Covoiturage.Entity.Conducteur;
import mdw3.cov.Covoiturage.Entity.Vehicule;
import mdw3.cov.Covoiturage.Repository.ConducteurRepository;
import mdw3.cov.Covoiturage.Repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConducteurService {

    @Autowired
    private ConducteurRepository conducteurRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    public Conducteur registerConducteur(ConducteurRegistrationDTO dto) {
        // Create and save Vehicule
        Vehicule vehicule = new Vehicule();
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setSieges(dto.getSieges());
    
        Vehicule savedVehicule = vehiculeRepository.save(vehicule);
        System.out.println("Saved Vehicule ID: " + savedVehicule.getId()); // Debug log
    
        // Create and save Conducteur
        Conducteur conducteur = new Conducteur();
        conducteur.setNom(dto.getNom());
        conducteur.setPrenom(dto.getPrenom());
        conducteur.setEmail(dto.getEmail());
        conducteur.setTelephone(dto.getTelephone());
        conducteur.setPassword(dto.getPassword());
        conducteur.setVehicule(savedVehicule); 
    
        Conducteur savedConducteur = conducteurRepository.save(conducteur);
        System.out.println("Saved Conducteur ID: " + savedConducteur.getId()); // Debug log
    
        return savedConducteur;
    }
}
