package user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import user.model.Exercice;
import user.model.TypeTrouble;
import user.model.StatutExercice;
import user.service.ExerciceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercices")
@CrossOrigin(origins = "*")
public class ExerciceController {
    
    @Autowired
    private ExerciceService exerciceService;
    
    @PostMapping
    public ResponseEntity<?> createExercice(@RequestBody Exercice exercice) {
        try {
            System.out.println("💊 Creating exercise for patient: " + exercice.getIdPatient());
            
            Exercice savedExercice = exerciceService.createExercice(exercice);
            System.out.println("✅ Exercise created successfully - ID: " + savedExercice.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedExercice);
            
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Validation error: " + e.getMessage());
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            System.out.println("❌ Error creating exercise: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la création de l'exercice\"}");
        }
    }
    
    @GetMapping
    public List<Exercice> getAllExercices() {
        System.out.println("📋 Fetching all exercises");
        return exerciceService.getAllExercices();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Exercice> getExerciceById(@PathVariable Long id) {
        System.out.println("🔍 Fetching exercise ID: " + id);
        Optional<Exercice> exercice = exerciceService.getExerciceById(id);
        return exercice.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/patient/{patientId}")
    public List<Exercice> getExercicesByPatient(@PathVariable Long patientId) {
        System.out.println("👤 Fetching exercises for patient: " + patientId);
        return exerciceService.getExercicesByPatient(patientId);
    }
    
    @GetMapping("/coach/{coachId}")
    public List<Exercice> getExercicesByCoach(@PathVariable Long coachId) {
        System.out.println("🏋️ Fetching exercises by coach: " + coachId);
        return exerciceService.getExercicesByCoach(coachId);
    }
    
    @GetMapping("/patient/{patientId}/active")
    public List<Exercice> getActiveExercicesByPatient(@PathVariable Long patientId) {
        System.out.println("🎯 Fetching active exercises for patient: " + patientId);
        return exerciceService.getActiveExercicesByPatient(patientId);
    }
    
    @GetMapping("/patient/{patientId}/trouble/{troubleType}")
    public List<Exercice> getExercicesByPatientAndTroubleType(
            @PathVariable Long patientId,
            @PathVariable TypeTrouble troubleType) {
        System.out.println("🎯 Fetching exercises for patient " + patientId + " with trouble: " + troubleType);
        return exerciceService.getExercicesByPatientAndTroubleType(patientId, troubleType);
    }
    
    @GetMapping("/patient/{patientId}/active/count")
    public ResponseEntity<?> countActiveExercicesByPatient(@PathVariable Long patientId) {
        try {
            Long count = exerciceService.countActiveExercicesByPatient(patientId);
            return ResponseEntity.ok().body("{\"activeExercicesCount\": " + count + "}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors du comptage des exercices\"}");
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExercice(@PathVariable Long id, @RequestBody Exercice exerciceDetails) {
        try {
            System.out.println("✏️ Updating exercise ID: " + id);
            Exercice updatedExercice = exerciceService.updateExercice(id, exerciceDetails);
            if (updatedExercice != null) {
                System.out.println("✅ Exercise updated successfully: " + id);
                return ResponseEntity.ok(updatedExercice);
            }
            System.out.println("❌ Exercise not found for update: " + id);
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            System.out.println("❌ Error updating exercise: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la mise à jour\"}");
        }
    }
    
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> markAsCompleted(@PathVariable Long id) {
        try {
            System.out.println("✅ Marking exercise as completed: " + id);
            Exercice completedExercice = exerciceService.markAsCompleted(id);
            if (completedExercice != null) {
                System.out.println("✅ Exercise marked as completed: " + id);
                return ResponseEntity.ok(completedExercice);
            }
            System.out.println("❌ Exercise not found: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println("❌ Error marking exercise as completed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors du marquage comme complété\"}");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercice(@PathVariable Long id) {
        try {
            System.out.println("🗑️ Deleting exercise ID: " + id);
            if (exerciceService.deleteExercice(id)) {
                System.out.println("✅ Exercise deleted successfully: " + id);
                return ResponseEntity.ok().body("{\"message\": \"Exercice supprimé avec succès\"}");
            }
            System.out.println("❌ Exercise not found for deletion: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println("❌ Error deleting exercise: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la suppression\"}");
        }
    }
    
    // Endpoint pour les types de troubles disponibles
    @GetMapping("/trouble-types")
    public TypeTrouble[] getTroubleTypes() {
        return TypeTrouble.values();
    }
    
    // Endpoint pour les statuts disponibles
    @GetMapping("/statuts")
    public StatutExercice[] getStatuts() {
        return StatutExercice.values();
    }
    
    // Endpoint de santé
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Exercice API is running");
    }
}