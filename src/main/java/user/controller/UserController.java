package user.controller;

import user.model.User;
import user.repository.UserRepository;
import user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;   
   
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.emailExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("Erreur: L'email existe déjà");
        }
        
   
        
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String password = loginRequest.get("psw");
            
            System.out.println(" Login attempt for: " + email);
            
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.badRequest().body("{\"message\": \"Email et mot de passe sont requis\"}");
            }
            
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                System.out.println(" User not found: " + email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"Email ou mot de passe incorrect\"}");
            }
            
            User user = userOptional.get();
            if (!user.getPsw().equals(password)) {
                System.out.println("Invalid password for: " + email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"Email ou mot de passe incorrect\"}");
            }
            
            System.out.println("Login successful for: " + email);
            return ResponseEntity.ok(user);
            
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la connexion\"}");
        }
    }
    
    @GetMapping("/check-email/{email}")
    public ResponseEntity<?> checkEmailExists(@PathVariable String email) {
        try {
            boolean exists = userService.emailExists(email);
            System.out.println("Email check for " + email + ": " + exists);
            return ResponseEntity.ok().body("{\"exists\": " + exists + "}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la vérification\"}");
        }
    }
    
    @GetMapping("/search/nom")
    public List<User> getUsersByNom(@RequestParam String nom) {
        return userService.getUsersByNom(nom);
    }
    
    @GetMapping("/search/prenom")
    public List<User> getUsersByPrenom(@RequestParam String prenom) {
        return userService.getUsersByPrenom(prenom);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            System.out.println(" Updating user ID: " + id);
            User updatedUser = userService.updateUser(id, userDetails);
            if (updatedUser != null) {
                System.out.println("User updated successfully: " + updatedUser.getEmail());
                return ResponseEntity.ok(updatedUser);
            }
            System.out.println(" User not found for update: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(" Error updating user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la mise à jour\"}");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            System.out.println(" Deleting user ID: " + id);
            if (userService.deleteUser(id)) {
                System.out.println("✅ User deleted successfully: " + id);
                return ResponseEntity.ok().body("{\"message\": \"Utilisateur supprimé avec succès\"}");
            }
            System.out.println(" User not found for deletion: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(" Error deleting user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur lors de la suppression\"}");
        }
    }
    
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String newPassword = request.get("newPassword");
            
            System.out.println("📧 Received password update request for: " + email);
            
            if (email == null || email.isEmpty() || newPassword == null || newPassword.isEmpty()) {
                System.out.println(" Missing email or password");
                return ResponseEntity.badRequest().body("{\"message\": \"Email et nouveau mot de passe sont requis\"}");
            }
            
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                System.out.println(" User not found: " + email);
                return ResponseEntity.badRequest().body("{\"message\": \"Utilisateur non trouvé\"}");
            }
            
            User user = userOptional.get();
            System.out.println(" Found user: " + user.getNom() + " " + user.getPrenom());
            
            user.setPsw(newPassword);
            userRepository.save(user);
            
            System.out.println(" Password updated successfully for: " + email);
            return ResponseEntity.ok("{\"message\": \"Mot de passe mis à jour avec succès\"}");
            
        } catch (Exception e) {
            System.out.println(" Error in updatePassword: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"Erreur: " + e.getMessage() + "\"}");
        }
    }
}