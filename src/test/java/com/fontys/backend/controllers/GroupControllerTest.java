package com.fontys.backend.controllers;

import com.fontys.backend.entities.Group;
import com.fontys.backend.entities.User;
import com.fontys.backend.repositories.GroupRepository;
import com.fontys.backend.repositories.UserRepository;
import com.fontys.backend.services.GroupService;
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

class GroupControllerTest {

    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private GroupService groupService;

    @BeforeEach
    void setUp()
    {
        groupRepository = Mockito.mock(GroupRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        groupService = new GroupService(userRepository,groupRepository);
    }

    @Test
    void createShouldReturnGroup()
    {
        User u = new User();
        Group g = new Group("Group", u, List.of(u));

        Mockito.when(userRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(u));
        Mockito.when(groupRepository.save(Mockito.any(Group.class))).thenReturn(g);

        assertEquals(g,groupService.create(g));
        Mockito.verify(groupRepository,times(1)).save(g);
    }

    @Test
    void getAllShouldReturnGroups()
    {
        List<Group> groupList = Collections.singletonList(new Group());

        Mockito.when(groupRepository.findAll()).thenReturn(groupList);

        assertEquals(groupList,groupService.getAll());
        Mockito.verify(groupRepository,times(1)).findAll();
    }

    @Test
    void getGroupByIdShouldReturnGroup()
    {
        Group g = new Group("Group", new User(), Collections.singletonList(new User()));

        Mockito.when(groupRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(g));

        assertEquals(g,groupService.getById(g.getId()));
        Mockito.verify(groupRepository,times(1)).findById(g.getId());
    }

    @Test
    void addUserToGroupShouldReturnGroupWithUser()
    {
        User u = new User();
        Group gWithout = new Group("Group", u, new ArrayList<>(List.of()));
        Group gWith = new Group("Group", u, new ArrayList<>(List.of(u)));

        Mockito.when(groupRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(gWithout));
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(u));

        assertTrue(groupService.addUserToGroup(gWith.getId(), u));
        Mockito.verify(groupRepository,times(1)).findById(gWithout.getId());
        Mockito.verify(userRepository,times(1)).findById(u.getId());
        Mockito.verify(groupRepository,times(1)).save(gWith);
    }

    @Test
    void removeUserFromGroupShouldReturnGroupWithoutUser()
    {
        User u = new User();
        Group gWithout = new Group("Group", u, new ArrayList<>(List.of()));
        Group gWith = new Group("Group", u, new ArrayList<>(List.of(u)));

        Mockito.when(groupRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(gWith));
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(u));

        assertTrue(groupService.removeUserFromGroup(gWith.getId(), u));
        Mockito.verify(groupRepository,times(1)).findById(gWith.getId());
        Mockito.verify(userRepository,times(1)).findById(u.getId());
        Mockito.verify(groupRepository,times(1)).save(gWithout);
    }
}
