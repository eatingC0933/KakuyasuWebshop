package se.yitingchang.testwebshop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service

public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        userRepo.save(user);
    }

    public User login(String userName, String password) {
        User user = userRepo.findByUserName(userName);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) { 
            return user;
        }
        return null;

    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Optional<User> findById(int id) {
        return userRepo.findById(id);
    }

    public void save(User user) {

        if (user.getId() != 0) {
            Optional<User> existingUser = userRepo.findById(user.getId());
            if (existingUser.isPresent()) {
                
                if (!user.getPassword().equals(existingUser.get().getPassword())) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
            }
        } else {
            
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepo.save(user);
    }

    public void delete(int id) {
        userRepo.deleteById(id);
    }
}
