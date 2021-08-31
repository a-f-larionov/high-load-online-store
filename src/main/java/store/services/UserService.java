package store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import store.entities.Role;
import store.entities.User;
import store.repositories.RoleRepository;
import store.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean registerUser(User user) {

        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return true;
    }

    public void userAddRole(User user, String roleName) {

        Role role = roleRepository.findByName(roleName);

        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }

    /**
     * Возвращает текущего авторизованного пользователя.
     *
     * @return User текущий авторизованный пользователь или пустой объект User.
     */
    public User getCurrentUser() {
        User user;

        Object object = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (object instanceof User) {
            user = (User) object;
        } else {
            user = new User();
        }

        // По причинам безопасности
        user.setPassword(null);

        return user;
    }

    public boolean currentUserHasRole(String roleName) {
        Authentication object = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (object != null && object.getAuthorities().stream().anyMatch(a ->

                a.getAuthority().equals(roleName))) {

            return true;
        } else {
            return false;
        }
    }
}