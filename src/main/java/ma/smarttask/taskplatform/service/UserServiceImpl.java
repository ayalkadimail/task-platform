package ma.smarttask.taskplatform.service;

import lombok.RequiredArgsConstructor;
import ma.smarttask.taskplatform.exception.ResourceNotFoundException;
import ma.smarttask.taskplatform.model.AppUser;
import ma.smarttask.taskplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public AppUser create(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public AppUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
    }
}