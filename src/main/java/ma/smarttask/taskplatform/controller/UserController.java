package ma.smarttask.taskplatform.controller;

import lombok.RequiredArgsConstructor;
import ma.smarttask.taskplatform.model.AppUser;
import ma.smarttask.taskplatform.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public AppUser create(@RequestBody AppUser user) {
        return userService.create(user);
    }

    @GetMapping
    public List<AppUser> getAll() {
        return userService.findAll();
    }
}