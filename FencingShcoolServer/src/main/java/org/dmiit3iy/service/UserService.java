package org.dmiit3iy.service;

import org.dmiit3iy.model.User;

public interface UserService {
    void add(User user);
    User get(String login, String password);
    User get(long id);
    User delete(long id);

}
