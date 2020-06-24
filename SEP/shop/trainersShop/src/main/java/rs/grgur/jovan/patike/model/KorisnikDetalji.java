package rs.grgur.jovan.patike.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class KorisnikDetalji extends Korisnik implements UserDetails {

    public KorisnikDetalji(final Korisnik korisnik) {
        super(korisnik);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getUloge()
                .stream()
                .map(uloga -> new SimpleGrantedAuthority("ROLE_" + uloga.getUloga()))
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getLozinka();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
