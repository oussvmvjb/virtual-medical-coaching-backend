package user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.model.Evaluation;
import user.repository.EvaluationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {
    
    @Autowired
    private EvaluationRepository evaluationRepository;
    
    public Evaluation createEvaluation(Evaluation evaluation) {
        // Validation des scores
        validateScores(evaluation);
        
        // S'assurer que la date est définie
        if (evaluation.getDateEvaluation() == null) {
            evaluation.setDateEvaluation(LocalDateTime.now());
        }
        
        return evaluationRepository.save(evaluation);
    }
    
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }
    
    public Optional<Evaluation> getEvaluationById(Long id) {
        return evaluationRepository.findById(id);
    }
    
    public List<Evaluation> getEvaluationsByPatient(Long patientId) {
        return evaluationRepository.findByIdPatientOrderByDateEvaluationDesc(patientId);
    }
    
    public List<Evaluation> getRecentEvaluationsByPatient(Long patientId, int limit) {
        return evaluationRepository.findRecentByPatientId(patientId, limit);
    }
    
    public List<Evaluation> getEvaluationsWithRisk() {
        return evaluationRepository.findByPenseesRisqueTrue();
    }
    
    public List<Evaluation> getEvaluationsByPeriod(LocalDateTime start, LocalDateTime end) {
        return evaluationRepository.findByDateEvaluationBetween(start, end);
    }
    
    public Evaluation updateEvaluation(Long id, Evaluation evaluationDetails) {
        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(id);
        
        if (optionalEvaluation.isPresent()) {
            Evaluation evaluation = optionalEvaluation.get();
            
            // Mettre à jour les champs
            evaluation.setHumeur(evaluationDetails.getHumeur());
            evaluation.setStress(evaluationDetails.getStress());
            evaluation.setEnergie(evaluationDetails.getEnergie());
            evaluation.setMotivation(evaluationDetails.getMotivation());
            evaluation.setSommeil(evaluationDetails.getSommeil());
            evaluation.setSymptomes(evaluationDetails.getSymptomes());
            evaluation.setAppetit(evaluationDetails.getAppetit());
            evaluation.setActivitePhysique(evaluationDetails.getActivitePhysique());
            evaluation.setInteractionsSociales(evaluationDetails.getInteractionsSociales());
            evaluation.setPenseesRisque(evaluationDetails.getPenseesRisque());
            evaluation.setDetailsRisque(evaluationDetails.getDetailsRisque());
            evaluation.setCommentaire(evaluationDetails.getCommentaire());
            
            validateScores(evaluation);
            return evaluationRepository.save(evaluation);
        }
        
        return null;
    }
    
    public boolean deleteEvaluation(Long id) {
        if (evaluationRepository.existsById(id)) {
            evaluationRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Double getAverageHumeurByPatientAndPeriod(Long patientId, LocalDateTime start, LocalDateTime end) {
        return evaluationRepository.findAverageHumeurByPatientAndPeriod(patientId, start, end);
    }
    
    private void validateScores(Evaluation evaluation) {
        validateScore(evaluation.getHumeur(), "Humeur");
        validateScore(evaluation.getStress(), "Stress");
        validateScore(evaluation.getEnergie(), "Energie");
        validateScore(evaluation.getMotivation(), "Motivation");
        validateScore(evaluation.getSommeil(), "Sommeil");
    }
    
    private void validateScore(Integer score, String fieldName) {
        if (score == null || score < 1 || score > 10) {
            throw new IllegalArgumentException(fieldName + " doit être entre 1 et 10");
        }
    }
}