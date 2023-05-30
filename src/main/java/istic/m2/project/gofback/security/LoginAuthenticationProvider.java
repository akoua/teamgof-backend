package istic.m2.project.gofback.security;

import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.TeamGofAdmin;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.TeamGofAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CavalierRepository cavalierRepository;
    @Autowired
    private TeamGofAdminRepository teamGofAdminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String credential = authentication.getCredentials().toString();
        Cavalier cavalier = cavalierRepository.findCavalierByEmailIgnoreCase(username)
                .orElse(null);
//                .orElseThrow(() -> new UsernameNotFoundException(String.format("username %s not found", username)));

        if (null != cavalier && passwordEncoder.matches(credential, cavalier.getPwd())) {
            return createUser(authentication, cavalier.getRole().toString());
        } else {
            TeamGofAdmin teamGofAdmin = teamGofAdminRepository.findTeamGofAdminByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format(" %s not found", username)));
            if (passwordEncoder.matches(credential, teamGofAdmin.getPwd())) {
                return createUser(authentication, teamGofAdmin.getRole().toString());
            } else {
                throw new BadCredentialsException("username/password incorrect");
            }
        }
    }

    private UsernamePasswordAuthenticationToken createUser(Authentication authentication, String role) {

        return new UsernamePasswordAuthenticationToken(authentication.getName(),
                null, Collections.singletonList(new SimpleGrantedAuthority(role)));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
