package com.obra.restAPI.login.controllers;

import com.obra.restAPI.login.models.ERole;
import com.obra.restAPI.login.models.Role;
import com.obra.restAPI.login.models.User;
import com.obra.restAPI.login.payload.request.ChangeRoleRequest;
import com.obra.restAPI.login.payload.response.MessageResponse;
import com.obra.restAPI.login.repository.RoleRepository;
import com.obra.restAPI.login.repository.UserRepository;
import com.obra.restAPI.login.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;



    @PostMapping("/changeRoleTo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeRoleTo(@Valid @RequestBody ChangeRoleRequest changeRoleRequest){
        if (!userRepository.existsByUsername(changeRoleRequest.getLoginRequest().getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Usuario "+ changeRoleRequest.getLoginRequest().getUsername() +"não cadastro!"));
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(changeRoleRequest.getLoginRequest().getUsername(), changeRoleRequest.getLoginRequest().getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        Set<Role> roles = changeRole(changeRoleRequest);
        Optional<User> user = userRepository.findByUsername(changeRoleRequest.getLoginRequest().getUsername());
        user.get().setRoles(roles);
        userRepository.save(user.get());

        return ResponseEntity.ok(new MessageResponse("Usuário trocado para " + changeRoleRequest.getRole() + " com sucesso!"));
    }

    private Set<Role> changeRole(ChangeRoleRequest changeRoleRequest) {
        Set<Role> roles = new HashSet<>();
        switch (changeRoleRequest.getRole()){
            case("ADMIN"):
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
            case("MOD"):
                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
            case("USER"):
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
        }
        return roles;
    }
}
