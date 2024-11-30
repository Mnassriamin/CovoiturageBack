package mdw3.cov.Covoiturage.Services;

import mdw3.cov.Covoiturage.Entity.Trajet;
import mdw3.cov.Covoiturage.Repository.TrajetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrajetService {

    @Autowired
    private TrajetRepository trajetRepository;

    public Trajet addTrajet(Trajet trajet) {
        return trajetRepository.save(trajet);
    }

    public List<Trajet> getAllTrajets() {
        return trajetRepository.findAll();
    }

    public List<Trajet> getTrajetsByConducteur(Long conducteurId) {
        return trajetRepository.findByConducteurId(conducteurId);
    }
}
