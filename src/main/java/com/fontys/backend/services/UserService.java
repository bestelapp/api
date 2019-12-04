package com.fontys.backend.services;

import com.fontys.backend.entities.User;
import com.fontys.backend.repositories.UserRepository;
import com.fontys.backend.utils.JwtTokenUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static com.google.common.hash.Hashing.sha256;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User register(User user) {
        if (userRepository.findByName(user.getName()).isEmpty()) {
            if (!user.getName().isEmpty() && !user.getHash().isEmpty()) {
                User u = userRepository.save(new User(user.getName()));
                if (setSaltAndHash(u.getId(), user.getHash())) {
                    return new User(u.getId(),u.getName());
                }
            }
        }
        return null;
    }

    public String login(User user) {
        Optional<User> u = userRepository.findByName(user.getName());
        if (u.isPresent()) {
            //get salt and hash
            String salt = u.get().getSalt();
            String hash = sha256().hashString(salt + user.getHash() + salt, StandardCharsets.UTF_8).toString();
            if (hash.equals(u.get().getHash())) {
                //reset salt and hash
                setSaltAndHash(u.get().getId(), user.getHash());
                return JwtTokenUtil.generateToken(u.get());
            }
        }
        return null;
    }

    private Boolean setSaltAndHash(Integer userId, String password) {
        Optional<User> u = userRepository.findById(userId);
        if (u.isPresent()) {
            String salt = RandomString.make(8);
            String hash = sha256().hashString(salt + password + salt, StandardCharsets.UTF_8).toString();
            u.get().setSalt(salt);
            u.get().setHash(hash);
            userRepository.save(u.get());
            return true;
        }
        return false;
    }

    public User validateUser(String token) {
        if (token != null) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
                User user = JwtTokenUtil.decodeToken(token);
                if (user != null) {
                    User u = getById(user.getId());
                    if (u != null) {
                        return user;
                    }
                }
            }
        }
        return null;
    }
}
