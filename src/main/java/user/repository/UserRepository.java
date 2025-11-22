package user.repository;

import user.model.User;
import user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByTelephone(String telephone); // Changé ici
    List<User> findByNom(String nom);
    List<User> findByPrenom(String prenom);
    List<User> findByRole(Role role);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone); // Changé ici
    
    // Méthodes pour rechercher par rôle
    List<User> findByRoleAndNomContaining(Role role, String nom);
    List<User> findByRoleAndPrenomContaining(Role role, String prenom);
}