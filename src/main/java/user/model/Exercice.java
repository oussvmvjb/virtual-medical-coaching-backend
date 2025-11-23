package user.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exercices")
public class Exercice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_patient", nullable = false)
    private Long idPatient;
    
    @Column(name = "id_coach", nullable = false)
    private Long idCoach;
    
    @Column(nullable = false)
    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeTrouble typeTrouble;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequence frequence;
    
    @Column(columnDefinition = "TEXT")
    private String instructions;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Duree duree;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NiveauRisque niveauRisque;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutExercice statut;
    
    // Constructeurs
    public Exercice() {
        this.dateCreation = LocalDateTime.now();
        this.statut = StatutExercice.ACTIF;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getIdPatient() { return idPatient; }
    public void setIdPatient(Long idPatient) { this.idPatient = idPatient; }
    
    public Long getIdCoach() { return idCoach; }
    public void setIdCoach(Long idCoach) { this.idCoach = idCoach; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public TypeTrouble getTypeTrouble() { return typeTrouble; }
    public void setTypeTrouble(TypeTrouble typeTrouble) { this.typeTrouble = typeTrouble; }
    
    public Frequence getFrequence() { return frequence; }
    public void setFrequence(Frequence frequence) { this.frequence = frequence; }
    
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    
    public Duree getDuree() { return duree; }
    public void setDuree(Duree duree) { this.duree = duree; }
    
    public NiveauRisque getNiveauRisque() { return niveauRisque; }
    public void setNiveauRisque(NiveauRisque niveauRisque) { this.niveauRisque = niveauRisque; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public StatutExercice getStatut() { return statut; }
    public void setStatut(StatutExercice statut) { this.statut = statut; }
}