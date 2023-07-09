package jardin.empresa.auth.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jardin.empresa.auth.dto.AuthenticationRequest;
import jardin.empresa.auth.dto.UserDTO;
import jardin.empresa.auth.entity.UserEntity;
import jardin.empresa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;


    public UserDetailsCustomService(@Autowired @Lazy PasswordEncoder passwordEncoder, @Autowired @Lazy UserRepository userRepository ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        //this.emailService = emailService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var foundUser = userRepository.findByUsername(username);
        Collection<GrantedAuthority> authorities = foundUser.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                .collect(Collectors.toList());
        return new User(
                foundUser.getUsername(),
                foundUser.getPassword(),
                authorities
        );
    }


    public boolean save(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity = this.userRepository.save(userEntity);
        return userEntity != null;
    }

    public void put(Long id, UserEntity userEntity) {
        UserEntity entity = userRepository.findById(id).get();
        entity.setUsername(userEntity.getUsername());
        entity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity saved = userRepository.save(entity);
    }

    public ResponseEntity<String> editPass(UserEntity userEntity) {
        Optional<UserEntity> optionalUser = Optional.ofNullable(userRepository.findByUsername(userEntity.getUsername()));

        if (optionalUser.isPresent()) {
            UserEntity encontrado = optionalUser.get();
            encontrado.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userRepository.save(encontrado);
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}