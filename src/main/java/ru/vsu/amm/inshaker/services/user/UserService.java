package ru.vsu.amm.inshaker.services.user;

import org.dozer.Mapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.simple.UserSimpleDTO;
import ru.vsu.amm.inshaker.exceptions.AnonymousAuthenticationException;
import ru.vsu.amm.inshaker.model.user.Role;
import ru.vsu.amm.inshaker.model.user.User;
import ru.vsu.amm.inshaker.repositories.user.RoleRepository;
import ru.vsu.amm.inshaker.repositories.user.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       Mapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public void save(String username, String password, Set<String> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roleRepository.findAllByNameIn(roles));
        userRepository.save(user);
    }

    public void save(String username, String password) {
        save(username, password, new HashSet<>(Collections.singletonList("ROLE_USER")));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserSimpleDTO> getAllUsers() {
        return userRepository.findAllByRole("ROLE_USER")
                .stream()
                .map(t -> mapper.map(t, UserSimpleDTO.class))
                .collect(Collectors.toList());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AnonymousAuthenticationException(authentication.getDetails().toString());
        }
        return findByUsername(authentication.getName());
    }

    public UserSimpleDTO getCurrentUserDTO() {
        return mapper.map(getCurrentUser(), UserSimpleDTO.class);
    }

    public boolean userHasRole(User user, String roleName) {
        return user.getRoles().stream().map(Role::getName).anyMatch(x -> x.equals(roleName));
    }

}
