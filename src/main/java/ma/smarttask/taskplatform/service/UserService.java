package ma.smarttask.taskplatform.service;

import ma.smarttask.taskplatform.model.AppUser;

import java.util.List;

public interface UserService {
    AppUser create(AppUser user);
    List<AppUser> findAll();
    AppUser findById(Long id);
}
