package com.fontys.backend.controllers;

import com.fontys.backend.entities.Group;
import com.fontys.backend.services.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping("/all")
    public List<Group> getAll() {
        return groupService.getAll();
    }

    @RequestMapping("/{id}")
    public Group getById(@PathVariable("id") int id) {
        return groupService.getById(id);
    }

    @PostMapping("/create")
    public Group create(@RequestBody Group group) {
        return groupService.create(group);
    }

    @PostMapping
    public Boolean addUserToGroup(@RequestBody Group group) {
        return groupService.addUserToGroup(group);
    }
}
