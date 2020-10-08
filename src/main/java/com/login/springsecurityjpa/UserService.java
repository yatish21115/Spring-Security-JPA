package com.login.springsecurityjpa;

import com.login.springsecurityjpa.Models.User;

public interface UserService {
    public void saveUser(User user);
    public boolean isUserAlreadyPresent(User user);
}
