package user.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String prenom;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String psw;
    
    @Column(name = "telephone", nullable = false) // Correction ici
    private String telephone; // Changé de "tel" à "telephone"
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    
    // Constructeurs
    public User() {
        this.role = Role.USER;
    }
    
    public User(String nom, String prenom, String email, String psw, String telephone, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.psw = psw;
        this.telephone = telephone;
        this.role = role;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPsw() { return psw; }
    public void setPsw(String psw) { this.psw = psw; }
    
    public String getTelephone() { return telephone; } // Changé ici
    public void setTelephone(String telephone) { this.telephone = telephone; } // Changé ici
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}