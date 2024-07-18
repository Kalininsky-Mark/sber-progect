package services;

import data_transfer.UserDataTransfer;
import tables.Role;
import tables.User;
import lombok.AllArgsConstructor;
import repositories.RoleRepository;
import repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final String USER_ROLE_NAME = "USER";

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(UserDataTransfer userDataTransfer) {
        User user = new User();

        user.setUsername(userDataTransfer.getUsername());
        user.setPassword(passwordEncoder.encode(userDataTransfer.getPassword()));
        user.setRoles(List.of(roleRepository.
            findByName(USER_ROLE_NAME).
            orElseGet(this::checkRoleExist)
        ));

        userRepository.save(user);
    }

    private Role checkRoleExist() {
        Role role = new Role();

        role.setName(USER_ROLE_NAME);

        return roleRepository.save(role);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}