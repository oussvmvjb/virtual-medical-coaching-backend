package user.service;

import user.model.User;
import user.model.Role;
import user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    
    public boolean telephoneExists(String telephone) { // Changé ici
        return userRepository.findByTelephone(telephone).isPresent(); // Changé ici
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public User updateUser(Long id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (userDetails.getNom() != null) {
                user.setNom(userDetails.getNom());
            }
            if (userDetails.getPrenom() != null) {
                user.setPrenom(userDetails.getPrenom());
            }
            if (userDetails.getEmail() != null) {
                if (!user.getEmail().equals(userDetails.getEmail()) && 
                    userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                    throw new RuntimeException("L'email existe déjà");
                }
                user.setEmail(userDetails.getEmail());
            }
            if (userDetails.getTelephone() != null) { // Changé ici
                if (!user.getTelephone().equals(userDetails.getTelephone()) && 
                    userRepository.findByTelephone(userDetails.getTelephone()).isPresent()) { // Changé ici
                    throw new RuntimeException("Le numéro de téléphone existe déjà");
                }
                user.setTelephone(userDetails.getTelephone()); // Changé ici
            }
            if (userDetails.getPsw() != null) {
                user.setPsw(userDetails.getPsw());
            }
            if (userDetails.getRole() != null) {
                user.setRole(userDetails.getRole());
            }
            
            return userRepository.save(user);
        }
        return null;
    }
    
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public List<User> getUsersByNom(String nom) {
        return userRepository.findByNom(nom);
    }
    
    public List<User> getUsersByPrenom(String prenom) {
        return userRepository.findByPrenom(prenom);
    }
    
    // Nouvelles méthodes pour les rôles
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }
    
    public List<User> getUsers() {
        return userRepository.findByRole(Role.USER);
    }
    
    public List<User> getCoachs() {
        return userRepository.findByRole(Role.COACH);
    }
    
    public List<User> searchUsersByRoleAndNom(Role role, String nom) {
        return userRepository.findByRoleAndNomContaining(role, nom);
    }
    
    public List<User> searchUsersByRoleAndPrenom(Role role, String prenom) {
        return userRepository.findByRoleAndPrenomContaining(role, prenom);
    }
}