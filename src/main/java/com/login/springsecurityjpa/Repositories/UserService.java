package com.login.springsecurityjpa.Repositories;

import com.login.springsecurityjpa.Models.User;

import java.util.List;

public interface UserService {
    public void saveUser(User user);
    public boolean isUserAlreadyPresent(User user);
    public User isEmailAlreadyPresent(User user);
    public List<User> listAll();
    public User get(Long id);
    public void delete(Long id);
    public void saveEditUser(User user);
}
