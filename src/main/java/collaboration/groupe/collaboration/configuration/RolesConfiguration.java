package collaboration.groupe.collaboration.configuration;

import collaboration.groupe.collaboration.entities.Utilisateur;
import collaboration.groupe.collaboration.repository.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration
public class RolesConfiguration {
    private static final String ADMIN_EMAIL_PATTERN = "^admin_.*@example.com$";
    private RoleRepository roleRepository;
    @Bean
    public RoleAssignmentStrategy roleAssignmentStrategy() {
        return this::assignRole;
    }

    private Role assignRole(Utilisateur utilisateur) {
        if (utilisateur.getEmail().matches(ADMIN_EMAIL_PATTERN)) {
            return roleRepository.findByName(RoleType.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + RoleType.ADMIN));
        } else {
            return roleRepository.findByName(RoleType.USER) //roleType ou TypeRole sont la meme chose c'est l'interface
                    .orElseThrow(() -> new RuntimeException("Role not found: " + RoleType.USER));
        }
    }
    public interface RoleAssignmentStrategy {
        Role assignRole(Utilisateur utilisateur);
    }


//    @Configuration
//    public class RoleConfiguration {
//        private final UtilisateurRepository utilisateurRepository;
//        private final RoleRepository roleRepository;
//
//        public RoleConfiguration(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository) {
//            this.utilisateurRepository = utilisateurRepository;
//            this.roleRepository = roleRepository;
//        }
//
//        @Bean
//        public RoleAssignmentStrategy roleAssignmentStrategy() {
//            return this::assignRole;
//        }
//
//        private Role assignRole(Utilisateur utilisateur) {
//            long userCount = utilisateurRepository.count();
//            if (userCount == 0) {
//                return roleRepository.findByName(RoleType.ADMIN)
//                        .orElseThrow(() -> new RuntimeException("Role not found: " + RoleType.ADMIN));
//            } else {
//                return roleRepository.findByName(RoleType.USER)
//                        .orElseThrow(() -> new RuntimeException("Role not found: " + RoleType.USER));
//            }
//        }
//    }
//@Service
//public class AdminService {
//    private final UtilisateurRepository utilisateurRepository;
//    private final RoleRepository roleRepository;
//
//    public AdminService(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository) {
//        this.utilisateurRepository = utilisateurRepository;
//        this.roleRepository = roleRepository;
//    }
//
//    public Utilisateur updateUserRole(Long userId, String roleType) {
//        Utilisateur utilisateur = utilisateurRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("Utilisateur not found: " + userId));
//
//        Role newRole = roleRepository.findByName(roleType)
//                .orElseThrow(() -> new RuntimeException("Role not found: " + roleType));
//
//        utilisateur.setRole(newRole);
//        return utilisateurRepository.save(utilisateur);
//    }

//    @RestController
//    @RequestMapping("/admin")
//    public class AdminController {
//        private final AdminService adminService;
//
//        public AdminController(AdminService adminService) {
//            this.adminService = adminService;
//        }
//
//        @PreAuthorize("hasRole('ADMIN')")
//        @PostMapping("/users/{userId}/role")
//        public Utilisateur updateUserRole(@PathVariable Long userId, @RequestParam String roleType) {
//            return adminService.updateUserRole(userId, roleType);
//        }
}
