package istic.m2.project.gofback.security;

import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.repositories.CavalierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CavalierRepository cavalierRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        Cavalier cavalier = cavalierRepository.findCavalierByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("username %s not found", username)));
        if (passwordEncoder.matches(authentication.getCredentials().toString(), cavalier.getPwd())) {
            return new UsernamePasswordAuthenticationToken(authentication.getName(),
                    null, null);
        } else {
            throw new BadCredentialsException("username/password incorrect");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
