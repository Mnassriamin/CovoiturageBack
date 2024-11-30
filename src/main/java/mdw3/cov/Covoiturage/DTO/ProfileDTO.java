package mdw3.cov.Covoiturage.DTO;

public class ProfileDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    // Vehicle details (optional)
    private String marque;
    private String modele;
    private String immatriculation;
    private Integer sieges;

    // Constructor for generic Utilisateur
    public ProfileDTO(Long id, String nom, String prenom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    // Constructor for Conducteur with vehicle details
    public ProfileDTO(Long id, String nom, String prenom, String email, String telephone,
                      String marque, String modele, String immatriculation, Integer sieges) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.marque = marque;
        this.modele = modele;
        this.immatriculation = immatriculation;
        this.sieges = sieges;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Integer getSieges() {
        return sieges;
    }

    public void setSieges(Integer sieges) {
        this.sieges = sieges;
    }
    
}
