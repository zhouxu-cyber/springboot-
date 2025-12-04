package org.kob.backend.controller.user;


import org.kob.backend.mapper.UserMapper;
import org.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/user/all")

    public List<User> all() {
        return userMapper.selectList(null);
    }

    @GetMapping("/user/{id}/")
    public User getuser(@PathVariable int id) {
        return userMapper.selectById(id);
    }

    @GetMapping("user/add/{id}/{username}/{password}")
    public String adduser(@PathVariable int id,
                        @PathVariable String username,
                        @PathVariable String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(id, username, encodedPassword);
        userMapper.insert(user);
        return "add user successfully!";
    }

    @GetMapping("user/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userMapper.deleteById(id);
        return "delete user successfully!";
    }
}


