package com.fontys.backend.services;

import com.fontys.backend.entities.Group;
import com.fontys.backend.entities.Order;
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
            List<User> users = Collections.singletonList(u.get());
            Group g = new Group(group.getName(), u.get(), users);
            try {
                return groupRepository.save(g);
            } catch (DataIntegrityViolationException e) {
                return null;
            }

        }
        return null;
    }

    public Group getById(Integer id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            group.get().getOwner().setHash(null);
            group.get().getOwner().setSalt(null);
            for (User u : group.get().getUsers()) {
                u.setHash(null);
                u.setSalt(null);
            }
            return group.get();
        }
        return null;
    }

    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    public Boolean addUserToGroup(Integer id, User user) {
        Group g = getById(id);
        if (g != null) {
            Optional<User> u = userRepository.findById(user.getId());
            if (u.isPresent() && !g.getUsers().contains(u.get())) {
                g.getUsers().add(u.get());
                groupRepository.save(g);
                return true;
            }
            return false;
        }
        return false;
    }

    public Boolean removeUserFromGroup(Integer id, User user) {
        Group g = getById(id);
        if (g != null) {
            Optional<User> u = userRepository.findById(user.getId());
            if (u.isPresent() && g.getUsers().contains(u.get())) {
                g.getUsers().remove(u.get());
                groupRepository.save(g);
                return true;
            }
            return false;
        }
        return false;
    }

    public void addOrder(Group group, Order order) {
        group.getOrders().add(order);
    }
}
