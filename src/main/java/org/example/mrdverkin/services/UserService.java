package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void hintsRevers(User user) {
        user.setHints(!user.isHints());
        userRepository.save(user);
    }
}
