package org.example.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(HttpServletRequest request,@RequestBody LoginRequest loginRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // Set authentication to the security context
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        // Fetch authenticated user's details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Extract user roles
        List<String> userRoles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Prepare the response with user details
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("email", userDetails.getUsername());
        userResponse.put("role", userRoles.get(0)); // Assuming only one role for now

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication Object: " + authentication);
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            System.out.println("hi");
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                System.out.println(userDetails);
                String username = userDetails.getUsername();
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                Map<String, Object> userDetailsMap = new HashMap<>();
                userDetailsMap.put("username", username);
                userDetailsMap.put("roles", authorities);

                return ResponseEntity.ok(userDetailsMap);
            } else {
                return ResponseEntity.badRequest().body("Unable to retrieve user details");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
    }
    @GetMapping("/session")
    public String getSessionId(HttpSession session) {
        return "Session ID: " + session.getId();
    }


}
