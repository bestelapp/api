package com.fontys.backend.services;

import com.fontys.backend.entities.Group;
import com.fontys.backend.entities.User;
import com.fontys.backend.repositories.GroupRepository;
import com.fontys.backend.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public GroupService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public Group create(Group group) {
        Optional<User> u = userRepository.findById(group.getOwner().getId());
        if (u.isPresent()) {
            Group g = new Group(group.getName(), group.getOwner());
            try {
                return groupRepository.save(g);
            } catch (DataIntegrityViolationException e) {
                return null;
            }

        }
        return null;
    }

    public Group getById(Integer id) {
        return groupRepository.findById(id).orElse(null);
    }

    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    public Boolean addUserToGroup(Group group) {
        Group g = getById(group.getId());
        if (g != null) {
            for (User u : group.getUsers()) {
                Optional<User> user = userRepository.findById(u.getId());
                if (user.isPresent()) {
                    g.getUsers().add(user.get());
                    groupRepository.save(g);
                }
            }
            return true;
        }
        return false;
    }

}
