package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        log.info("Получен GET-запрос /users для вывода списка всех пользователей");
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("Получен GET-запрос /users для вывода пользователя с Id = {}", id);
        return userService.getById(id);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        log.info("Получен GET-запрос /users для вывода списка друзей пользователя с Id = {}", id);
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getOtherFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Получен GET-запрос /users для вывода списка общих друзей друзей пользователя с Id = {}", id);
        return userService.getCommonFriends(id, otherId);
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        log.info("Получен POST-запрос /users, чтобы добавить пользователя");
        return userService.createUser(user);
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) {
        log.info("Получен PUT-запрос /users, чтобы обновить пользователя с ID={}", user.getId());
        return userService.updateUser(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Получен PUT-запрос /users, чтобы добавить пользователя с ID={}", friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}")
    public User delete(@PathVariable Integer id) {
        log.info("Получен DELETE-запрос /users, чтобы удалить фильм с ID={}", id);
        return userService.deleteUser(id);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Получен PUT-запрос /users, чтобы удалить пользователя с ID={}", friendId);
        userService.deleteFriend(id, friendId);
    }
}
