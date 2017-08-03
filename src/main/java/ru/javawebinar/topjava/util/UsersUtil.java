package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> USERS = Arrays.asList(
      new User(1, "John Snow", "john.snow@gmail.com", "iknwnothing", Role.ROLE_ADMIN, Role.ROLE_USER),
      new User(2, "Daenerys Targaryen", "targaryen@gmail.com", "motherofdragons", Role.ROLE_ADMIN, Role.ROLE_USER),
      new User(3, "Varys", "Varys@gmail.com", "spridersandbirds", Role.ROLE_USER),
      new User(4, "Grey Worm", "grey.worm@gmail.com", "Missandei", Role.ROLE_USER)
    );


}
