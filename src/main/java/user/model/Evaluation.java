package user.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluations")
public class Evaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_patient", nullable = false)
    private Long idPatient;
    
    @Column(nullable = false)
    private Integer humeur;
    
    @Column(nullable = false)
    private Integer stress;
    
    @Column(nullable = false)
    private Integer energie;
    
    @Column(nullable = false)
    private Integer motivation;
    
    @Column(nullable = false)
    private Integer sommeil;
    
    @Column(columnDefinition = "TEXT")
    private String symptomes;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Appetit appetit;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "activite_physique", nullable = false)
    private ActivitePhysique activitePhysique;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "interactions_sociales", nullable = false)
    private InteractionSociale interactionsSociales;
    
    @Column(name = "pensees_risque")
    private Boolean penseesRisque = false;
    
    @Column(name = "details_risque", columnDefinition = "TEXT")
    private String detailsRisque;
    
    @Column(columnDefinition = "TEXT")
    private String commentaire;
    
    @Column(name = "date_evaluation")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateEvaluation;
    
    // Constructeurs
    public Evaluation() {
        this.dateEvaluation = LocalDateTime.now();
    }
    
    public Evaluation(Long idPatient, Integer humeur, Integer stress, Integer energie, 
                     Integer motivation, Integer sommeil, Appetit appetit, 
                     ActivitePhysique activitePhysique, InteractionSociale interactionsSociales) {
        this();
        this.idPatient = idPatient;
        this.humeur = humeur;
        this.stress = stress;
        this.energie = energie;
        this.motivation = motivation;
        this.sommeil = sommeil;
        this.appetit = appetit;
        this.activitePhysique = activitePhysique;
        this.interactionsSociales = interactionsSociales;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getIdPatient() { return idPatient; }
    public void setIdPatient(Long idPatient) { this.idPatient = idPatient; }
    
    public Integer getHumeur() { return humeur; }
    public void setHumeur(Integer humeur) { this.humeur = humeur; }
    
    public Integer getStress() { return stress; }
    public void setStress(Integer stress) { this.stress = stress; }
    
    public Integer getEnergie() { return energie; }
    public void setEnergie(Integer energie) { this.energie = energie; }
    
    public Integer getMotivation() { return motivation; }
    public void setMotivation(Integer motivation) { this.motivation = motivation; }
    
    public Integer getSommeil() { return sommeil; }
    public void setSommeil(Integer sommeil) { this.sommeil = sommeil; }
    
    public String getSymptomes() { return symptomes; }
    public void setSymptomes(String symptomes) { this.symptomes = symptomes; }
    
    public Appetit getAppetit() { return appetit; }
    public void setAppetit(Appetit appetit) { this.appetit = appetit; }
    
    public ActivitePhysique getActivitePhysique() { return activitePhysique; }
    public void setActivitePhysique(ActivitePhysique activitePhysique) { this.activitePhysique = activitePhysique; }
    
    public InteractionSociale getInteractionsSociales() { return interactionsSociales; }
    public void setInteractionsSociales(InteractionSociale interactionsSociales) { this.interactionsSociales = interactionsSociales; }
    
    public Boolean getPenseesRisque() { return penseesRisque; }
    public void setPenseesRisque(Boolean penseesRisque) { this.penseesRisque = penseesRisque; }
    
    public String getDetailsRisque() { return detailsRisque; }
    public void setDetailsRisque(String detailsRisque) { this.detailsRisque = detailsRisque; }
    
    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    
    public LocalDateTime getDateEvaluation() { return dateEvaluation; }
    public void setDateEvaluation(LocalDateTime dateEvaluation) { this.dateEvaluation = dateEvaluation; }
}

// Enums pour les options
enum Appetit {
    NORMAL, DIMINUE, AUGMENTE
}

enum ActivitePhysique {
    AUCUNE, FAIBLE, MODEREE, BONNE
}

enum InteractionSociale {
    AUCUNE, FAIBLE, NORMALE, ACTIVE
}