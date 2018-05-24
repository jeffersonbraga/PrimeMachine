package br.com.lsd.security;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.lsd.modelo.User;
import br.com.lsd.service.usuario.UsuarioService;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);

        Optional<User> userFromDatabase = Optional.ofNullable(new User())	  
                .map(user -> new UsuarioService().getByEmail(login));

        UserDetails resultUser = userFromDatabase.map(user -> {
            if (!user.getActivated()) {
                try {
					throw new Exception("User " + lowercaseLogin + " was not activated");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

            User userLogged = SecurityUtils.getCurrentUser();
            String password = user.getPassword();
            /*if (userLogged != null && userLogged.getType().equals('W')) {
            	password = userLogged.getPassword();
            }*/

            org.springframework.security.core.userdetails.User searchResult = 
            		new org.springframework.security.core.userdetails.User(lowercaseLogin,
            				password,
                grantedAuthorities);

            return searchResult;
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
        "database"));
        
        return resultUser;
    }
}
