package user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import user.model.Evaluation;
import user.service.EvaluationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluations")
@CrossOrigin(origins = "*")
public class EvaluationController {
    
    @Autowired
    private EvaluationService evaluationService;
    
    @PostMapping
    public ResponseEntity<?> createEvaluation(@RequestBody Evaluation evaluation) {
        try {
            System.out.println("📊 Creating evaluation for patient: " + evaluation.getIdPatient());
            
            // Validation des données requises
            if (evaluation.getIdPatient() == null) {
                return ResponseEntity.badRequest().body("{\"message\": \"ID patient est obligatoire\"}");
            }
            
            Evaluation savedEvaluation = evaluationService.createEvaluation(evaluation);
            System.out.println("✅ Evaluation created successfully - ID: " + savedEvaluation.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvaluation);
            
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Validation error: " + e.getMessage());
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            System.out.println("❌ Error creating evaluation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la création de l'évaluation\"}");
        }
    }
    
    @GetMapping
    public List<Evaluation> getAllEvaluations() {
        System.out.println("📋 Fetching all evaluations");
        return evaluationService.getAllEvaluations();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable Long id) {
        System.out.println("🔍 Fetching evaluation ID: " + id);
        Optional<Evaluation> evaluation = evaluationService.getEvaluationById(id);
        return evaluation.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/patient/{patientId}")
    public List<Evaluation> getEvaluationsByPatient(@PathVariable Long patientId) {
        System.out.println("👤 Fetching evaluations for patient: " + patientId);
        return evaluationService.getEvaluationsByPatient(patientId);
    }
    
    @GetMapping("/patient/{patientId}/recent")
    public List<Evaluation> getRecentEvaluationsByPatient(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "5") int limit) {
        System.out.println("🕒 Fetching recent evaluations for patient: " + patientId);
        return evaluationService.getRecentEvaluationsByPatient(patientId, limit);
    }
    
    @GetMapping("/risk")
    public List<Evaluation> getEvaluationsWithRisk() {
        System.out.println("⚠️ Fetching evaluations with risk thoughts");
        return evaluationService.getEvaluationsWithRisk();
    }
    
    @GetMapping("/period")
    public List<Evaluation> getEvaluationsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        System.out.println("📅 Fetching evaluations from " + start + " to " + end);
        return evaluationService.getEvaluationsByPeriod(start, end);
    }
    
    @GetMapping("/stats/average-humeur")
    public ResponseEntity<?> getAverageHumeur(
            @RequestParam Long patientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            Double average = evaluationService.getAverageHumeurByPatientAndPeriod(patientId, start, end);
            return ResponseEntity.ok().body("{\"averageHumeur\": " + (average != null ? average : 0) + "}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors du calcul de la moyenne\"}");
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvaluation(@PathVariable Long id, @RequestBody Evaluation evaluationDetails) {
        try {
            System.out.println("✏️ Updating evaluation ID: " + id);
            Evaluation updatedEvaluation = evaluationService.updateEvaluation(id, evaluationDetails);
            if (updatedEvaluation != null) {
                System.out.println("✅ Evaluation updated successfully: " + id);
                return ResponseEntity.ok(updatedEvaluation);
            }
            System.out.println("❌ Evaluation not found for update: " + id);
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            System.out.println("❌ Error updating evaluation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la mise à jour\"}");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvaluation(@PathVariable Long id) {
        try {
            System.out.println("🗑️ Deleting evaluation ID: " + id);
            if (evaluationService.deleteEvaluation(id)) {
                System.out.println("✅ Evaluation deleted successfully: " + id);
                return ResponseEntity.ok().body("{\"message\": \"Évaluation supprimée avec succès\"}");
            }
            System.out.println("❌ Evaluation not found for deletion: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println("❌ Error deleting evaluation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la suppression\"}");
        }
    }
    
    // Endpoint pour vérifier la santé de l'API
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Evaluation API is running");
    }
}