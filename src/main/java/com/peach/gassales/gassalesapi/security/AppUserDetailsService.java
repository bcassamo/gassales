package com.peach.gassales.gassalesapi.security;
/*

import com.peach.gassales.gassalesapi.model.Utilizador;
import com.peach.gassales.gassalesapi.repository.UtilizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
*/

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
/*

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Utilizador> utilizadorOptional = utilizadorRepository.findByEmail(email);
        Utilizador utilizador = utilizadorOptional.orElseThrow(() -> new UsernameNotFoundException("Utilizador e/ou senha incorretos"));
        return new User(email, utilizador.getSenha(), getPermissoes(utilizador));
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Utilizador utilizador) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        utilizador.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
        return authorities;
    }
}
*/
