package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.user.RoleEntity;
import com.codecool.solarwatch.model.user.UserDTO;
import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.codecool.solarwatch.model.user.UserEntity;

import java.util.*;

import static java.lang.String.format;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.
            getLogger(GeocodingService.class);
    private final RoleRepository roleRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            UserEntity userEntity = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            List<SimpleGrantedAuthority> roles = new ArrayList<>();
            for (RoleEntity roleEntity : userEntity.getRoles()) {
                roles.add(new SimpleGrantedAuthority(roleEntity.getRole()));
            }

            return new User(userEntity.getUsername(), userEntity.getPassword(), roles);
    }

    public void createUser (UserDTO userDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(userDTO.username());
        if (userEntityOptional.isPresent()) {
            logger.info(format("user %s already exists", userDTO.username()));
            throw new IllegalArgumentException(format("user %s already exists", userDTO.username()));
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.username());
        userEntity.setPassword(userDTO.password());
        Set<RoleEntity> roles = new HashSet<>();
        for (String role : userDTO.roles()) {
            RoleEntity roleEntity = roleRepository.findByName(role);
            roles.add(roleEntity);
        }
        userEntity.setRoles(new HashSet<>(roles));
        userRepository.save(userEntity);
    }

    public void setToAdmin(){
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        System.out.println(user);
        Optional<UserEntity> userEntityOpt = userRepository.findByUsername(user.getUsername());
        if (userEntityOpt.isEmpty()) {
            throw new UsernameNotFoundException(user.getUsername());
        }
        UserEntity userEntity = userEntityOpt.get();
        Set<RoleEntity> roles = userEntity.getRoles();
        RoleEntity role = roleRepository.findByName("ROLE_ADMIN");
        roles.add(role);
        userEntity.setRoles(roles);
        userRepository.save(userEntity);
    }
}
