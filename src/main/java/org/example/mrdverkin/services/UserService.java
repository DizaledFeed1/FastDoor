package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void hintsRevers(User user) {
        user.setHints(!user.isHints());
        userRepository.save(user);
    }

    /**
     * Метод проверяет, принадлежит ли заказ юзеру который хочет его удалить
     * @param user
     * @param order
     */
    public void checkDeletedUser(UserDetails user, Order order) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SELLER"))) {
            if (!order.getUser().getUsername().equals(user.getUsername())) {
                throw new RuntimeException("Юзер не принадлежит данному заказу");
            }
        }
    }
}