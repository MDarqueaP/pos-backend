package ec.com.edimca.posbackend.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import ec.com.edimca.posbackend.model.User;
import ec.com.edimca.posbackend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {

        Iterable<User> userList = this.userRepository.findAll();

        List<User> converted = (List<User>) userList;
        converted.removeIf(user -> user.getId() == 1);

        if (CollectionUtils.isEmpty((List<User>) userList)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
        }

        return (List<User>) userList;
    }

    @Transactional(readOnly = true)
    public User getUser(int id) {

        Optional<User> userFound = this.userRepository.findById(id);
        if (!userFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return userFound.get();
    }

    @Transactional(readOnly = false)
    public Boolean newUser(User user) {

        Date date = new Date();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Optional<User> userFound = this.userRepository.findByEmail(user.getEmail());
        if (userFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.FOUND, "User already using this email");
        }

        String bcryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(bcryptPassword);
        user.setActive(Boolean.TRUE);
        user.setCreationDate(new Timestamp(date.getTime()));

        user.getRoles().forEach(userRole -> {
            userRole.setUser(user);
        });

        this.userRepository.save(user);

        return Boolean.TRUE;
    }

    @Transactional(readOnly = false)
    public Boolean updateUser(int userId, User user) {

        Date date = new Date();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Optional<User> userFound = this.userRepository.findById(userId);
        if (!userFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Optional<User> userRepeated = this.userRepository.findByEmail(user.getEmail());
        if (userRepeated.isPresent() && userRepeated.get().getId() != userFound.get().getId()) {
            throw new ResponseStatusException(HttpStatus.FOUND, "User already using this email");
        }

        userFound.get().setFirstName(user.getFirstName());
        userFound.get().setLastName(user.getLastName());
        userFound.get().setEmail(user.getEmail());

        if (!user.getPassword().equals("")) {
            String bcryptPassword = passwordEncoder.encode(user.getPassword());
            userFound.get().setPassword(bcryptPassword);
        }

        if (user.getRoles() != null) {
            user.getRoles().forEach(userRole -> {
                userRole.setUser(userFound.get());
            });
            userFound.get().setRoles(user.getRoles());
        }

        userFound.get().setUpdateDate(new Timestamp(date.getTime()));

        this.userRepository.save(userFound.get());

        return Boolean.TRUE;
    }

    @Transactional(readOnly = false)
    public Boolean changeActive(int id) {

        Optional<User> userFound = this.userRepository.findById(id);
        if (!userFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        userFound.get().setActive(!userFound.get().getActive());
        this.userRepository.save(userFound.get());

        return Boolean.TRUE;
    }

}
