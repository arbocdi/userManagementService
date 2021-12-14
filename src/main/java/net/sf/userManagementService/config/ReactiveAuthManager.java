package net.sf.userManagementService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ReactiveAuthManager extends AbstractUserDetailsReactiveAuthenticationManager {
    @Autowired
    private ManagementUserDetailsService userDetailsService;

    @Override
    protected Mono<UserDetails> retrieveUser(String username) {
        return userDetailsService.findByUsername(username);
    }

}
