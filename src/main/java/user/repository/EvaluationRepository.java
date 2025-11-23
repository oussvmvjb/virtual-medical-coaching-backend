package user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import user.model.Evaluation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    
    // Trouver toutes les évaluations d'un patient
    List<Evaluation> findByIdPatientOrderByDateEvaluationDesc(Long idPatient);
    
    // Évaluations par période
    List<Evaluation> findByDateEvaluationBetween(LocalDateTime start, LocalDateTime end);
    
    // Évaluations avec pensées à risque
    List<Evaluation> findByPenseesRisqueTrue();
    
    // Évaluations récentes d'un patient
    @Query("SELECT e FROM Evaluation e WHERE e.idPatient = :patientId ORDER BY e.dateEvaluation DESC LIMIT :limit")
    List<Evaluation> findRecentByPatientId(@Param("patientId") Long patientId, @Param("limit") int limit);
    
    // Statistiques d'humeur moyenne par patient
    @Query("SELECT AVG(e.humeur) FROM Evaluation e WHERE e.idPatient = :patientId AND e.dateEvaluation BETWEEN :start AND :end")
    Double findAverageHumeurByPatientAndPeriod(@Param("patientId") Long patientId, 
                                              @Param("start") LocalDateTime start, 
                                              @Param("end") LocalDateTime end);
}