package user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.model.Exercice;
import user.model.TypeTrouble;
import user.model.StatutExercice;
import user.repository.ExerciceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciceService {
    
    @Autowired
    private ExerciceRepository exerciceRepository;
    
    public Exercice createExercice(Exercice exercice) {
        validateExercice(exercice);
        return exerciceRepository.save(exercice);
    }
    
    public List<Exercice> getAllExercices() {
        return exerciceRepository.findAll();
    }
    
    public Optional<Exercice> getExerciceById(Long id) {
        return exerciceRepository.findById(id);
    }
    
    public List<Exercice> getExercicesByPatient(Long patientId) {
        return exerciceRepository.findByIdPatientOrderByDateCreationDesc(patientId);
    }
    
    public List<Exercice> getExercicesByCoach(Long coachId) {
        return exerciceRepository.findByIdCoachOrderByDateCreationDesc(coachId);
    }
    
    public List<Exercice> getActiveExercicesByPatient(Long patientId) {
        return exerciceRepository.findActiveExercicesByPatient(patientId);
    }
    
    public List<Exercice> getExercicesByPatientAndTroubleType(Long patientId, TypeTrouble typeTrouble) {
        return exerciceRepository.findByIdPatientAndTypeTrouble(patientId, typeTrouble);
    }
    
    public Exercice updateExercice(Long id, Exercice exerciceDetails) {
        Optional<Exercice> optionalExercice = exerciceRepository.findById(id);
        
        if (optionalExercice.isPresent()) {
            Exercice exercice = optionalExercice.get();
            
            // Mettre à jour les champs modifiables
            exercice.setTitre(exerciceDetails.getTitre());
            exercice.setDescription(exerciceDetails.getDescription());
            exercice.setTypeTrouble(exerciceDetails.getTypeTrouble());
            exercice.setFrequence(exerciceDetails.getFrequence());
            exercice.setInstructions(exerciceDetails.getInstructions());
            exercice.setDuree(exerciceDetails.getDuree());
            exercice.setNiveauRisque(exerciceDetails.getNiveauRisque());
            exercice.setStatut(exerciceDetails.getStatut());
            
            validateExercice(exercice);
            return exerciceRepository.save(exercice);
        }
        
        return null;
    }
    
    public boolean deleteExercice(Long id) {
        if (exerciceRepository.existsById(id)) {
            exerciceRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Exercice markAsCompleted(Long id) {
        Optional<Exercice> optionalExercice = exerciceRepository.findById(id);
        if (optionalExercice.isPresent()) {
            Exercice exercice = optionalExercice.get();
            exercice.setStatut(StatutExercice.COMPLETE);
            return exerciceRepository.save(exercice);
        }
        return null;
    }
    
    public Long countActiveExercicesByPatient(Long patientId) {
        return exerciceRepository.countActiveExercicesByPatient(patientId);
    }
    
    private void validateExercice(Exercice exercice) {
        if (exercice.getIdPatient() == null) {
            throw new IllegalArgumentException("ID patient est obligatoire");
        }
        if (exercice.getIdCoach() == null) {
            throw new IllegalArgumentException("ID coach est obligatoire");
        }
        if (exercice.getTitre() == null || exercice.getTitre().trim().isEmpty()) {
            throw new IllegalArgumentException("Titre est obligatoire");
        }
        if (exercice.getTypeTrouble() == null) {
            throw new IllegalArgumentException("Type de trouble est obligatoire");
        }
    }
}