package pl.pelikan.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pelikan.model.User;
import pl.pelikan.model.UserRole;
import pl.pelikan.repository.UserRepository;
import pl.pelikan.repository.UserRoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void changeCredentials(User user) {
        User toUpdate = findCurrentUser();
        toUpdate.setFirstName(user.getFirstName());
        toUpdate.setLastName(user.getLastName());
        toUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        updateUser(toUpdate);
    }

    public void changeUserRole(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            if (!userHasAdminRole(user)) {
               user.getRoles().add(roleRepository.findByRole("ROLE_ADMIN"));
            } else {
                user.getRoles().remove(roleRepository.findByRole("ROLE_ADMIN"));
            }
            updateUser(user);
        }
    }

    public boolean userHasAdminRole(User user) {
        return user.getUserRoles().contains("ROLE_ADMIN");
    }

    public User findCurrentUser() {
        return findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public List<User> findAllWithoutCurrentUser() {
        User user = findCurrentUser();
        return userRepository.findAll().stream()
                .filter(userInRepo -> !userInRepo.equals(user))
                .collect(Collectors.toList());
    }
}
