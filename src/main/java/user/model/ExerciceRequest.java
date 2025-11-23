package user.model;

import java.util.List;

public class ExerciceRequest {
    private Long idPatient;
    private Long idCoach;
    private String titre;
    private String description;
    private TypeTrouble typeTrouble;
    private Frequence frequence;
    private String instructions;
    private Duree duree;
    private NiveauRisque niveauRisque;
    private List<String> exercicesSelectionnes;
    private String exercicePersoNom;
    private String exercicePersoDesc;
    
    // Getters et Setters
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
    
    public List<String> getExercicesSelectionnes() { return exercicesSelectionnes; }
    public void setExercicesSelectionnes(List<String> exercicesSelectionnes) { this.exercicesSelectionnes = exercicesSelectionnes; }
    
    public String getExercicePersoNom() { return exercicePersoNom; }
    public void setExercicePersoNom(String exercicePersoNom) { this.exercicePersoNom = exercicePersoNom; }
    
    public String getExercicePersoDesc() { return exercicePersoDesc; }
    public void setExercicePersoDesc(String exercicePersoDesc) { this.exercicePersoDesc = exercicePersoDesc; }
}