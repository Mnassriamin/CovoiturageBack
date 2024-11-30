package mdw3.cov.Covoiturage.Services;

import mdw3.cov.Covoiturage.DTO.CovoitureurRegistrationDTO;
import mdw3.cov.Covoiturage.Entity.Covoitureur;
import mdw3.cov.Covoiturage.Repository.CovoitureurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CovoitureurService {

    @Autowired
    private CovoitureurRepository covoitureurRepository;



    public Covoitureur registerCovoitureur(CovoitureurRegistrationDTO dto) {
        Covoitureur covoitureur = new Covoitureur();
        covoitureur.setNom(dto.getNom());
        covoitureur.setPrenom(dto.getPrenom());
        covoitureur.setEmail(dto.getEmail());
        covoitureur.setTelephone(dto.getTelephone());
        covoitureur.setPassword(dto.getPassword());

        return covoitureurRepository.save(covoitureur);
    }
}
