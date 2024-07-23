package collaboration.groupe.collaboration.controller;

import collaboration.groupe.collaboration.entities.Roles;
import collaboration.groupe.collaboration.services.RoleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {

        private RoleImpl rolesService;

        @Autowired
        public RolesController(RoleImpl rolesService) {
            this.rolesService = rolesService;
        }

        @PostMapping(value="/create")
        public ResponseEntity<Roles> createRole(@RequestBody Roles role) {
               Roles createdRole = rolesService.createRole(role);
               return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
        }

        @GetMapping("/{idr}")
        public ResponseEntity<Roles> getRoleById(@PathVariable("idr") Long idRole) {
               Roles role = rolesService.getRoleById(idRole);
               if (role != null) {
                  return new ResponseEntity<>(role, HttpStatus.OK);
               }
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @GetMapping
        public ResponseEntity<List<Roles>> getAllRoles() {
               List<Roles> roles = rolesService.getAllRoles();
               return new ResponseEntity<>(roles, HttpStatus.OK);
        }

        @PutMapping("/{idr}")
        public ResponseEntity<Roles> updateRole(@PathVariable("idr") Long idRole, @RequestBody Roles role) {
               Roles updatedRole = rolesService.updateRole(idRole, role);
               if (updatedRole != null) {
                  return new ResponseEntity<>(updatedRole, HttpStatus.OK);
               }
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @DeleteMapping("/{idr}")
        public ResponseEntity<Void> deleteRole(@PathVariable("idr") Long idRole) {
               rolesService.deleteRole(idRole);
               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


    }


