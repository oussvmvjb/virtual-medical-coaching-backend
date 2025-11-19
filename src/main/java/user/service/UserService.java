package user.service;

import user.model.User;
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
    
    public boolean telExists(String tel) {
        return userRepository.findByTel(tel).isPresent();
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
            if (userDetails.getTel() != null) {
                if (!user.getTel().equals(userDetails.getTel()) && 
                    userRepository.findByTel(userDetails.getTel()).isPresent()) {
                    throw new RuntimeException("Le numéro de téléphone existe déjà");
                }
                user.setTel(userDetails.getTel());
            }
            if (userDetails.getPsw() != null) {
                user.setPsw(userDetails.getPsw());
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
}