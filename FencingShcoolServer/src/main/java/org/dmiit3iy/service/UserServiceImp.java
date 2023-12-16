package org.dmiit3iy.service;

import org.dmiit3iy.model.User;
import org.dmiit3iy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void add(User user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("This user already added!");
        }
    }

    //Нужно ли делать проверку на null????!!!
    @Override
    public User get(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    @Override
    public User get(long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User does not exists!"));
    }

    @Override
    public User delete(long id) {
        User user= this.get(id);
        userRepository.deleteById(id);
        return user;
    }
}
