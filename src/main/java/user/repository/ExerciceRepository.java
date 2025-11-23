package user.repository;

import user.model.Exercice;
import user.model.TypeTrouble;
import user.model.StatutExercice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciceRepository extends JpaRepository<Exercice, Long> {
    
    List<Exercice> findByIdPatientOrderByDateCreationDesc(Long idPatient);
    List<Exercice> findByIdCoachOrderByDateCreationDesc(Long idCoach);
    List<Exercice> findByIdPatientAndTypeTrouble(Long idPatient, TypeTrouble typeTrouble);
    List<Exercice> findByIdPatientAndStatut(Long idPatient, StatutExercice statut);
    List<Exercice> findByIdCoachAndStatut(Long idCoach, StatutExercice statut);
    
    @Query("SELECT e FROM Exercice e WHERE e.idPatient = :patientId AND e.statut = 'ACTIF'")
    List<Exercice> findActiveExercicesByPatient(@Param("patientId") Long patientId);
    
    @Query("SELECT COUNT(e) FROM Exercice e WHERE e.idPatient = :patientId AND e.statut = 'ACTIF'")
    Long countActiveExercicesByPatient(@Param("patientId") Long patientId);
    
    @Query("SELECT e FROM Exercice e WHERE e.idCoach = :coachId AND e.typeTrouble = :typeTrouble")
    List<Exercice> findByCoachAndTroubleType(@Param("coachId") Long coachId, @Param("typeTrouble") TypeTrouble typeTrouble);
}