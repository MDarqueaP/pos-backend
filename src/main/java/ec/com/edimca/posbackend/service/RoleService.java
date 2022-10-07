package ec.com.edimca.posbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import ec.com.edimca.posbackend.model.Role;
import ec.com.edimca.posbackend.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {

        Iterable<Role> roleList = this.roleRepository.findAll();

        List<Role> converted = (List<Role>) roleList;
        converted.removeIf(role -> role.getId() == 1);

        if (CollectionUtils.isEmpty((List<Role>) converted)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No roles found");
        }

        return (List<Role>) converted;
    }

}
