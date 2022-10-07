package ec.com.edimca.posbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ec.com.edimca.posbackend.model.Role;
import ec.com.edimca.posbackend.model.User;
import ec.com.edimca.posbackend.service.RoleService;
import ec.com.edimca.posbackend.service.UserService;
import ec.com.edimca.posbackend.validation.UserValidation;

@Controller
public class UserAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAll() {

        List<User> result = this.userService.getAllUsers();
        return new ResponseEntity<List<User>>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Boolean> newUser(@Validated(value = UserValidation.New.class) @RequestBody User user) {

        Boolean result = this.userService.newUser(user);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {

        User result = this.userService.getUser(id);
        return new ResponseEntity<User>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable("id") int id,
            @Validated(value = UserValidation.Update.class) @RequestBody User user) {

        Boolean result = this.userService.updateUser(id, user);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/users/changeActive/{id}")
    public ResponseEntity<Boolean> changeActive(@PathVariable("id") int id) {

        Boolean result = this.userService.changeActive(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> getAllRoles() {

        List<Role> result = this.roleService.getAllRoles();
        return new ResponseEntity<List<Role>>(result, HttpStatus.OK);
    }

}
