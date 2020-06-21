package ru.otus.spring.homework.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.domain.User;
import ru.otus.spring.homework.repository.UserRepositoryJpa;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositoryJpa userRepositoryJpa;

    @Autowired
    public UserDetailsServiceImpl(UserRepositoryJpa userRepositoryJpa) {
        this.userRepositoryJpa   = userRepositoryJpa;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepositoryJpa.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipalImpl(user);
    }
}