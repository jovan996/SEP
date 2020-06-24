package rs.grgur.jovan.patike.config;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import rs.grgur.jovan.patike.service.KorisnikDetaljiService;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private KorisnikDetaljiService kirisnikDetaljiService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails ud, UsernamePasswordAuthenticationToken upat) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String string, UsernamePasswordAuthenticationToken upat) throws AuthenticationException {
        Object token = upat.getCredentials();
        return Optional
                .ofNullable(token)
                .map(String::valueOf)
                .flatMap(kirisnikDetaljiService::findByToken)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
    }
}
